/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.admin.content.internal.resource.v1_0;

import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.headless.admin.content.internal.dto.v1_0.extension.ExtensionStructuredContent;
import com.liferay.headless.admin.content.internal.dto.v1_0.util.VersionUtil;
import com.liferay.headless.admin.content.internal.odata.entity.v1_0.StructuredContentEntityModel;
import com.liferay.headless.admin.content.resource.v1_0.StructuredContentResource;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.headless.delivery.search.aggregation.AggregationUtil;
import com.liferay.headless.delivery.search.sort.SortUtil;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.EntityExtensionUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/structured-content.properties",
	scope = ServiceScope.PROTOTYPE, service = StructuredContentResource.class
)
public class StructuredContentResourceImpl
	extends BaseStructuredContentResourceImpl implements EntityModelResource {

	@Override
	public void deleteStructuredContentByVersion(
			Long structuredContentId, Double version)
		throws Exception {

		JournalArticle journalArticle =
			_journalArticleLocalService.getLatestArticle(
				structuredContentId, WorkflowConstants.STATUS_ANY, false);

		_journalArticleService.deleteArticle(
			journalArticle.getGroupId(), journalArticle.getArticleId(), version,
			journalArticle.getUrlTitle(), new ServiceContext());
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<StructuredContent> getSiteStructuredContentsPage(
			Long siteId, Boolean flatten, String search,
			Aggregation aggregation, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		boolean permissionToManageVersions = _hasPermission(siteId);

		return SearchUtil.search(
			HashMapBuilder.put(
				"get",
				addAction(
					"VIEW", "getSiteStructuredContentsPage",
					"com.liferay.journal", siteId)
			).build(),
			booleanQuery -> {
				if (!GetterUtil.getBoolean(flatten)) {
					BooleanFilter booleanFilter =
						booleanQuery.getPreBooleanFilter();

					booleanFilter.add(
						new TermFilter(
							Field.FOLDER_ID,
							String.valueOf(
								JournalFolderConstants.
									DEFAULT_PARENT_FOLDER_ID)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, JournalArticle.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ARTICLE_ID, Field.SCOPE_GROUP_ID,
				Field.ROOT_ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);

				if (permissionToManageVersions) {
					searchContext.setAttribute(
						Field.STATUS, WorkflowConstants.STATUS_ANY);
					searchContext.setAttribute("latest", Boolean.TRUE);
				}
				else {
					searchContext.setAttribute(
						Field.STATUS, WorkflowConstants.STATUS_APPROVED);
					searchContext.setAttribute("head", Boolean.TRUE);
				}

				searchContext.setCompanyId(contextCompany.getCompanyId());

				searchContext.setGroupIds(new long[] {siteId});

				SearchRequestBuilder searchRequestBuilder =
					_searchRequestBuilderFactory.builder(searchContext);

				AggregationUtil.processVulcanAggregation(
					_aggregations, _ddmIndexer, _queries, searchRequestBuilder,
					aggregation);

				SortUtil.processSorts(
					_ddmIndexer, searchRequestBuilder, searchContext.getSorts(),
					_queries, _sorts);
			},
			sorts,
			document -> {
				JournalArticle journalArticle;

				if (permissionToManageVersions) {
					journalArticle =
						_journalArticleLocalService.getLatestArticle(
							GetterUtil.getLong(
								document.get(Field.ROOT_ENTRY_CLASS_PK)),
							WorkflowConstants.STATUS_ANY, false);
				}
				else {
					journalArticle =
						_journalArticleLocalService.getLatestArticle(
							GetterUtil.getLong(
								document.get(Field.ROOT_ENTRY_CLASS_PK)),
							WorkflowConstants.STATUS_APPROVED, true);
				}

				return _toExtensionStructuredContent(journalArticle);
			});
	}

	@Override
	public StructuredContent getStructuredContentByVersion(
			Long structuredContentId, Double version)
		throws Exception {

		JournalArticle journalArticle =
			_journalArticleLocalService.getLatestArticle(
				structuredContentId, WorkflowConstants.STATUS_ANY, false);

		return _toExtensionStructuredContent(
			_journalArticleService.getArticle(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				version));
	}

	@Override
	public Page<StructuredContent> getStructuredContentsVersionsPage(
			Long structuredContentId)
		throws Exception {

		JournalArticle journalArticle =
			_journalArticleLocalService.getLatestArticle(
				structuredContentId, WorkflowConstants.STATUS_ANY, false);

		if (_hasPermission(journalArticle.getGroupId())) {
			return Page.of(
				transform(
					_journalArticleService.getArticlesByArticleId(
						journalArticle.getGroupId(),
						journalArticle.getArticleId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null),
					this::_toExtensionStructuredContent));
		}

		return Page.of(
			transform(
				_journalArticleService.search(
					journalArticle.getCompanyId(), journalArticle.getGroupId(),
					Collections.singletonList(journalArticle.getFolderId()),
					journalArticle.getClassNameId(),
					journalArticle.getArticleId(), null, null, null, null,
					(String)null, null, null, null, null,
					WorkflowConstants.STATUS_APPROVED, true, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null),
				this::_toExtensionStructuredContent));
	}

	private boolean _hasPermission(Long siteId) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.hasPermission(
			siteId, JournalPortletKeys.JOURNAL, 0,
			ActionKeys.ACCESS_IN_CONTROL_PANEL);
	}

	private StructuredContent _toExtensionStructuredContent(
			JournalArticle journalArticle)
		throws Exception {

		DTOConverter<JournalArticle, StructuredContent> dtoConverter =
			(DTOConverter<JournalArticle, StructuredContent>)
				_dtoConverterRegistry.getDTOConverter(
					JournalArticle.class.getName());

		if (dtoConverter == null) {
			return null;
		}

		StructuredContent structuredContent = dtoConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				Collections.emptyMap(), _dtoConverterRegistry,
				contextHttpServletRequest, journalArticle.getResourcePrimKey(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			journalArticle);

		return EntityExtensionUtil.extend(
			structuredContent, StructuredContent.class,
			ExtensionStructuredContent.class,
			ExtensionStructuredContent -> ExtensionStructuredContent.setVersion(
				VersionUtil.toVersion(contextAcceptLanguage, journalArticle)));
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private DDMIndexer _ddmIndexer;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	private final EntityModel _entityModel = new StructuredContentEntityModel();

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private Queries _queries;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Reference
	private Sorts _sorts;

}