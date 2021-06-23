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

import {usePrevious} from '@liferay/frontend-js-react-web';
import React, {useEffect, useState} from 'react';

import Select from '../Select/Select.es';
import Text from '../Text/Text.es';
import {subWords} from '../util/strings.es';
import ValidationCheckbox from './ValidationCheckbox';
import {getSelectedValidation} from './transform.es';

const parameters = {
	futureDates: {
		label: Liferay.Language.get('starts-from'),
		name: 'startsFrom',
		options: [
			{
				checked: false,
				label: Liferay.Language.get('response-date'),
				name: 'responseDate',
				value: 'responseDate',
			},
		],
	},
};

const ValidationDate = ({
	defaultLanguageId,
	editingLanguageId,
	enableValidation: initialEnableValidation,
	errorMessage: initialErrorMessage,
	label,
	localizationMode,
	name,
	onChange,
	parameter: initialParameter,
	parameterMessage,
	readOnly,
	selectedValidation: initialSelectedValidation,
	spritemap,
	validation,
	validations,
	value,
	visible,
}) => {
	const [
		{enableValidation, errorMessage, selectedValidation},
		setState,
	] = useState({
		enableValidation: initialEnableValidation,
		errorMessage: initialErrorMessage,
		parameter: initialParameter,
		selectedValidation: initialSelectedValidation,
	});

	const transformSelectedValidation = getSelectedValidation(validations);
	const prevEditingLanguageId = usePrevious(editingLanguageId);
	const selectedParameter = parameters[selectedValidation.name];

	const handleChange = (key, newValue) => {
		setState((prevState) => {
			const newState = {
				...prevState,
				[key]: newValue,
			};

			let expression = {};

			if (newState.enableValidation) {
				expression = {
					name: newState.selectedValidation.name,
					value: subWords(newState.selectedValidation.template, {
						name: validation?.fieldName,
					}),
				};
			}

			onChange({
				enableValidation: newState.enableValidation,
				errorMessage: {
					...value.errorMessage,
					[editingLanguageId]: newState.errorMessage,
				},
				expression,
				parameter: {
					...value.parameter,
					[editingLanguageId]: !value.expression
						? parameterMessage
						: newState.parameter,
				},
			});

			return newState;
		});
	};

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId) {
			setState((prevState) => {
				const {errorMessage = {}, parameter = {}} = value;

				return {
					...prevState,
					errorMessage:
						errorMessage[editingLanguageId] !== undefined
							? errorMessage[editingLanguageId]
							: errorMessage[defaultLanguageId],
					parameter:
						parameter[editingLanguageId] !== undefined
							? parameter[editingLanguageId]
							: parameter[defaultLanguageId],
				};
			});
		}
	}, [defaultLanguageId, editingLanguageId, prevEditingLanguageId, value]);

	useEffect(() => {
		if (readOnly || !visible) {
			handleChange('enableValidation', false);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [readOnly, visible]);

	return (
		<ValidationCheckbox
			label={label}
			onChange={(event, value) => {
				handleChange('enableValidation', value);
			}}
			readOnly={readOnly}
			spritemap={spritemap}
			value={enableValidation}
			visible={visible}
		>
			{enableValidation && (
				<>
					<Select
						disableEmptyOption
						label={Liferay.Language.get('accepted-date')}
						name="selectedValidation"
						onChange={(event, value) =>
							handleChange(
								'selectedValidation',
								transformSelectedValidation(value)
							)
						}
						options={validations}
						placeholder={Liferay.Language.get('choose-an-option')}
						readOnly={readOnly || localizationMode}
						showEmptyOption={false}
						spritemap={spritemap}
						value={[selectedValidation.name]}
						visible={visible}
					/>

					<Select
						disableEmptyOption
						label={selectedParameter.label}
						name="selectedParameter"
						onChange={(event, value) => {
							handleChange('parameter', {
								[selectedParameter.name]: value[0],
							});
						}}
						options={selectedParameter.options}
						placeholder={Liferay.Language.get('choose-an-option')}
						readOnly={readOnly}
						showEmptyOption={false}
						value={selectedParameter.options[0].name}
						visible={visible}
					/>

					<Text
						label={Liferay.Language.get('error-message')}
						name={`${name}_errorMessage`}
						onChange={(event) =>
							handleChange('errorMessage', event.target.value)
						}
						placeholder={Liferay.Language.get('error-message')}
						readOnly={readOnly}
						required={false}
						spritemap={spritemap}
						value={errorMessage}
						visible={visible}
					/>
				</>
			)}
		</ValidationCheckbox>
	);
};

export default ValidationDate;
