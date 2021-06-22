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

import {useFormState} from 'data-engine-js-components-web';
import React, {useState} from 'react';

import Select from '../Select/Select.es';
import Text from '../Text/Text.es';
import ValidationCheckbox from './ValidationCheckbox';
import {transformValidations} from './transform.es';

const ValidationDate = ({dataType, defaultLanguageId, editingLanguageId, label, name, readOnly, value, visible}) => {
	const {errorMessage: initialErrorMessage, parameter: initialParameter} = value;

	const {validations: initialValidations} = useFormState();
	const [enabled, setEnabled] = useState(false);
	const [errorMessage, setErrorMessage] = useState(() => {
		if (initialErrorMessage[editingLanguageId] !== undefined) {
			return initialErrorMessage[editingLanguageId]
		}

		return initialErrorMessage[defaultLanguageId]
	});
	
	const parameter = JSON.stringify(initialParameter || {startsFrom: 'responseDate'});

	const validations = transformValidations(initialValidations, dataType);
	const selectedValidation = validations[0];

	const startsFromOptions = [
		{
			checked: false,
			label: Liferay.Language.get('response-date'),
			name: 'responseDate',
			value: 'responseDate'
		}
	];
	const selectedStartsFromOptions = startsFromOptions[0];

	return (
		<ValidationCheckbox
			label={label}
			onChange={(event, value) => setEnabled(value)}
			readOnly={readOnly}
			value={enabled}
			visible={visible}
		>
			{enabled ? (
				<>
					<Select
                        disableEmptyOption
                        label={Liferay.Language.get('accepted-dates')}
                        name="selectedValidation"
                        onChange={() => {}}
                        options={validations}
                        placeholder={Liferay.Language.get('choose-an-option')}
                        readOnly={readOnly}
                        showEmptyOption={false}
                        value={[selectedValidation.name]}
                        visible={visible}
                    />

					<Select
                        disableEmptyOption
                        label={Liferay.Language.get('starts-from')}
                        name="responseDate"
                        onChange={() => {}}
                        options={startsFromOptions}
                        placeholder={Liferay.Language.get('choose-an-option')}
                        readOnly={readOnly}
                        showEmptyOption={false}
                        value={[selectedStartsFromOptions.name]}
                        visible={visible}
                    />

					<input name={`${name}_parameter`} type="hidden" value={parameter} />

					<Text
						label={Liferay.Language.get('error-message')}
						name={`${name}_errorMessage`}
						onChange={(event) => {
                            setErrorMessage(event.target.value);
                        }}
						placeholder={Liferay.Language.get('error-message')}
						readOnly={readOnly}
						required={false}
						value={errorMessage}
						visible={visible}
					/>
				</>
			) : null}
		</ValidationCheckbox>
	);
};

export default ValidationDate;
