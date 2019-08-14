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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalEditDDMStructuresDisplayContext {

	public JournalEditDDMStructuresDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_journalWebConfiguration =
			(JournalWebConfiguration)httpServletRequest.getAttribute(
				JournalWebConfiguration.class.getName());
	}

	public boolean autogenerateDDMStructureKey() {
		return _journalWebConfiguration.autogenerateDDMStructureKey();
	}

	public boolean changeableDefaultLanguage() {
		return _journalWebConfiguration.changeableDefaultLanguage();
	}

	public String getAvailableFields() {
		return "Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE";
	}

	public Locale[] getAvailableLocales() {
		DDMForm ddmForm = getDDMForm();

		if (ddmForm == null) {
			return new Locale[] {LocaleUtil.getSiteDefault()};
		}

		Set<Locale> ddmFormAvailableLocales = ddmForm.getAvailableLocales();

		return ddmFormAvailableLocales.toArray(new Locale[0]);
	}

	public String getAvailableLocalesJSONArrayString() {
		JSONArray availableLocalesJSONArray = JSONFactoryUtil.createJSONArray();

		for (Locale availableLocale : getAvailableLocales()) {
			availableLocalesJSONArray.put(
				LanguageUtil.getLanguageId(availableLocale));
		}

		if (availableLocalesJSONArray.length() > 0) {
			return availableLocalesJSONArray.toString();
		}

		return StringPool.BLANK;
	}

	public DDMForm getDDMForm() {
		try {
			return DDMUtil.getDDMForm(getScript());
		}
		catch (Exception e) {
		}

		return null;
	}

	public DDMStructure getDDMStructure() {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		_ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			getDDMStructureId());

		return _ddmStructure;
	}

	public long getDDMStructureId() {
		if (_ddmStructureId != null) {
			return _ddmStructureId;
		}

		_ddmStructureId = ParamUtil.getLong(
			_httpServletRequest, "ddmStructureId");

		return _ddmStructureId;
	}

	public String getFields() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		if (ddmStructure == null) {
			return StringPool.BLANK;
		}

		JSONArray fieldsJSONArray = DDMUtil.getDDMFormFieldsJSONArray(
			ddmStructure.getLatestStructureVersion(), getScript());

		if (fieldsJSONArray != null) {
			return fieldsJSONArray.toString();
		}

		return StringPool.BLANK;
	}

	public String getLocalesMap() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONObject localesMapJSONObject = JSONFactoryUtil.createJSONObject();

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(
					themeDisplay.getSiteGroupId())) {

			localesMapJSONObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				availableLocale.getDisplayName(themeDisplay.getLocale()));
		}

		return localesMapJSONObject.toString();
	}

	public long getParentDDMStructureId() {
		if (_parentDDMStructureId != null) {
			return _parentDDMStructureId;
		}

		long defaultParentDDMStructureId =
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID;

		DDMStructure ddmStructure = getDDMStructure();

		if (ddmStructure != null) {
			defaultParentDDMStructureId = ddmStructure.getParentStructureId();
		}

		_parentDDMStructureId = ParamUtil.getLong(
			_httpServletRequest, "parentDDMStructureId",
			defaultParentDDMStructureId);

		return _parentDDMStructureId;
	}

	public String getParentDDMStructureName() {
		if (_parentDDMStructureName != null) {
			return _parentDDMStructureName;
		}

		DDMStructure parentDDMStructure =
			DDMStructureLocalServiceUtil.fetchStructure(
				getParentDDMStructureId());

		if (parentDDMStructure != null) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			_parentDDMStructureName = parentDDMStructure.getName(
				themeDisplay.getLocale());
		}

		return _parentDDMStructureName;
	}

	public String getSaveButtonLabel() throws PortalException {
		return "save";
	}

	public String getScript() throws PortalException {
		if (_script != null) {
			DDMStructure ddmStructure = getDDMStructure();

			_script = BeanParamUtil.getString(
				ddmStructure.getLatestStructureVersion(), _httpServletRequest,
				"definition");

			return _script;
		}

		_script = ParamUtil.getString(_httpServletRequest, "definition");

		return _script;
	}

	public String getStorageType() {
		String storageType = StorageType.JSON.getValue();

		try {
			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			storageType =
				journalServiceConfiguration.journalArticleStorageType();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return storageType;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalEditDDMStructuresDisplayContext.class);

	private DDMStructure _ddmStructure;
	private Long _ddmStructureId;
	private final HttpServletRequest _httpServletRequest;
	private final JournalWebConfiguration _journalWebConfiguration;
	private Long _parentDDMStructureId;
	private String _parentDDMStructureName;
	private String _script;

}