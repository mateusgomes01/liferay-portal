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

import ClayButton from '@clayui/button';
import {
	EVENT_TYPES,
	useConfig,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import React from 'react';

import EmptyState from '../../../js/components/empty-state/EmptyState.es';
import FieldType from '../../../js/components/field-types/FieldType.es';
import {DRAG_FIELDSET_ADD} from '../../../js/drag-and-drop/dragTypes.es';
import {getDataDefinitionFieldSet} from '../../../js/utils/dataConverter.es';
import {getLocalizedValue, getPluralMessage} from '../../../js/utils/lang.es';
import {getSearchRegex} from '../../../js/utils/search.es';

function getSortedFieldsets(fieldsets) {
	return fieldsets.sort((a, b) => {
		const localizedValueA = getLocalizedValue(a.defaultLanguageId, a.name);
		const localizedValueB = getLocalizedValue(b.defaultLanguageId, b.name);

		return localizedValueA.localeCompare(localizedValueB);
	});
}

function getFilteredFieldsets(fieldsets, keywords) {
	const regex = getSearchRegex(keywords);
	const filteredFieldsets = fieldsets.filter(({defaultLanguageId, name}) =>
		regex.test(getLocalizedValue(defaultLanguageId, name))
	);

	return getSortedFieldsets(filteredFieldsets);
}

export default function FieldSetList({searchTerm}) {
	const {
		activePage,
		availableLanguageIds,
		defaultLanguageId,
		editingLanguageId,
		fieldSets,
		pages,
	} = useFormState();

	const {allowInvalidAvailableLocalesForProperty, fieldTypes} = useConfig();

	const dispatch = useForm();

	const filteredFieldsets = getFilteredFieldsets(fieldSets, searchTerm);

	const onDoubleClick = ({fieldSet}) => {
		dispatch({
			payload: {
				indexes: {
					columnIndex: 0,
					pageIndex: activePage,
					rowIndex: pages[activePage].rows.length,
				},
				...getDataDefinitionFieldSet({
					allowInvalidAvailableLocalesForProperty,
					availableLanguageIds,
					defaultLanguageId,
					editingLanguageId,
					fieldSet,
					fieldTypes,
				}),
			},
			type: EVENT_TYPES.FIELD_SET.ADD,
		});
	};

	const onClickCreateNewFieldset = () => null;
	const CreateNewFieldsetButton = () => (
		<ClayButton
			block
			className="add-fieldset"
			displayType="secondary"
			onClick={onClickCreateNewFieldset}
		>
			{Liferay.Language.get('create-new-fieldset')}
		</ClayButton>
	);

	return (
		<>
			{filteredFieldsets.length ? (
				<>
					<CreateNewFieldsetButton />

					<div className="mt-3">
						{filteredFieldsets.map((fieldSet) => {
							const fieldSetName = getLocalizedValue(
								fieldSet.defaultLanguageId,
								fieldSet.name
							);

							return (
								<FieldType
									actions={[]}
									description={getPluralMessage(
										Liferay.Language.get('x-field'),
										Liferay.Language.get('x-fields'),
										fieldSet.dataDefinitionFields.length
									)}
									dragType={DRAG_FIELDSET_ADD}
									fieldSet={fieldSet}
									icon="forms"
									key={fieldSet.dataDefinitionKey}
									label={fieldSetName}
									onDoubleClick={onDoubleClick}
								/>
							);
						})}
					</div>
				</>
			) : (
				<div className="mt-2">
					<EmptyState
						emptyState={{
							button: CreateNewFieldsetButton,

							description: Liferay.Language.get(
								'there-are-no-fieldsets-description'
							),
							title: Liferay.Language.get(
								'there-are-no-fieldsets'
							),
						}}
						keywords={searchTerm}
						small
					/>
				</div>
			)}
		</>
	);
}
