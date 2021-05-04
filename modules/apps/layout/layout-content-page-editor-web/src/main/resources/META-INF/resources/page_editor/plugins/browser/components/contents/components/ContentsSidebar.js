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

import React, {useMemo} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/editableFragmentEntryProcessor';
import {EDITABLE_TYPES} from '../../../../../app/config/constants/editableTypes';
import selectLanguageId from '../../../../../app/selectors/selectLanguageId';
import {useSelector} from '../../../../../app/store/index';
import SidebarPanelContent from '../../../../../common/components/SidebarPanelContent';
import NoPageContents from './NoPageContents';
import PageContents from './PageContents';

const getEditableTitle = (editable, languageId) => {
	if (editable.type === EDITABLE_TYPES.text) {
		return editable[languageId];
	}

	const div = document.createElement('div');

	div.innerHTML = editable[languageId];

	return div.textContent;
};

const getEditableValues = (fragmentEntryLinks, languageId) =>
	Object.values(fragmentEntryLinks)
		.filter(
			(fragmentEntryLink) =>
				!fragmentEntryLink.masterLayout &&
				fragmentEntryLink.editableValues &&
				!fragmentEntryLink.removed
		)
		.map((fragmentEntryLink) => {
			const editableValues = Object.entries(
				fragmentEntryLink.editableValues[
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				]
			);

			return editableValues
				.filter(
					([key]) =>
						fragmentEntryLink.editableTypes[key] ===
							EDITABLE_TYPES.text ||
						fragmentEntryLink.editableTypes[key] ===
							EDITABLE_TYPES['rich-text']
				)
				.map(([key, value]) => ({
					...value,
					editableId: `${fragmentEntryLink.fragmentEntryLinkId}-${key}`,
				}));
		})
		.reduce(
			(editableValuesA, editableValuesB) => [
				...editableValuesA,
				...editableValuesB,
			],
			[]
		)
		.filter((editable) => editable[languageId]);

const normalizeEditableValues = (editable, languageId) => {
	return {
		...editable,
		icon: 'align-left',
		title: getEditableTitle(editable, languageId),
	};
};

const normalizePageContents = (pageContents) =>
	pageContents.reduce(
		(acc, content) =>
			acc[content.type]
				? {...acc, [content.type]: [...acc[content.type], content]}
				: {...acc, [content.type]: [content]},
		{}
	);

export default function ContentsSidebar() {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const languageId = useSelector(selectLanguageId);
	const pageContents = useSelector((state) => state.pageContents);

	const inlineTextContents = useMemo(
		() =>
			getEditableValues(fragmentEntryLinks, languageId).map((editable) =>
				normalizeEditableValues(editable, languageId)
			),
		[fragmentEntryLinks, languageId]
	);

	const contents = normalizePageContents(pageContents);

	const contentsWithInlineText = {
		...contents,
		...(inlineTextContents.length && {
			[Liferay.Language.get('inline-text')]: inlineTextContents,
		}),
	};

	const view = Object.keys(contentsWithInlineText).length ? (
		<PageContents pageContents={contentsWithInlineText} />
	) : (
		<NoPageContents />
	);

	return (
		<>
			<SidebarPanelContent
				className="page-editor__page-contents"
				padded={false}
			>
				{view}
			</SidebarPanelContent>
		</>
	);
}
