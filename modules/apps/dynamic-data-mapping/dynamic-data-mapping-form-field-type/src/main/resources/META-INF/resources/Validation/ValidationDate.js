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

import ClayForm from '@clayui/form';
import React, {useEffect, useState} from 'react';

import Checkbox from '../Checkbox/Checkbox.es';
import Select from '../Select/Select.es';
import Text from '../Text/Text.es';
import {getSelectedValidation} from './transform.es';
import {usePrevious} from '@liferay/frontend-js-react-web';

// const FUTURE_DATE = 'future-date';

// const ACCEPTED_DATE = {
//     [FUTURE_DATE]: {
//         label: Liferay.Language.get('future-dates'),
//         value: FUTURE_DATE,
//         nextStep: {
//             title: Liferay.Language.get('starts-from'),
//             options: [
//                 {
//                     label: Liferay.Language.get('response-date'),
//                     value: 'response-date'
//                 }
//             ]
//         }
//     }
// };

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
    readOnly,
    selectedValidation: initialSelectedValidation,
    spritemap,
    validations,
    value,
    visible,
}) => {
    const [
		{enableValidation, errorMessage, parameter, selectedValidation, startsFrom},
		setState,
	] = useState({
        enableValidation: initialEnableValidation,
		errorMessage: initialErrorMessage,
		parameter: initialParameter || JSON.stringify({
            startsFrom: 'responseDate'
        }),
		selectedValidation: initialSelectedValidation,
        startsFrom: [
            {
                checked: true,
                label: Liferay.Language.get('response-date'),
                value: 'responseDate',
            }
        ],
    });

    const transformSelectedValidation = getSelectedValidation(validations);

    const prevEditingLanguageId = usePrevious(editingLanguageId);

    const handleChange = (key, newValue) => {
		setState((prevState) => {
			const newState = {
				...prevState,
				[key]: newValue,
			};

			onChange({
				enableValidation: newState.enableValidation,
				errorMessage: {
					...value.errorMessage,
					[editingLanguageId]: newState.errorMessage,
				}
			});

			return newState;
		});
	};

    useEffect(() => {
		if (readOnly || !visible) {
			handleChange('enableValidation', false);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [readOnly, visible]);

    
    useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId) {
			setState((prevState) => {
				const {errorMessage = {}} = value;

				return {
					...prevState,
					errorMessage:
						errorMessage[editingLanguageId] !== undefined
							? errorMessage[editingLanguageId]
							: errorMessage[defaultLanguageId],
				};
			});
		}
	}, [defaultLanguageId, editingLanguageId, prevEditingLanguageId, value]);

    return (
        <ClayForm.Group className="lfr-ddm-form-field-validation">
			<Checkbox
				label={label}
				name="enableValidation"
				onChange={(event, value) => {
                    handleChange('enableValidation', value)
                }}
				readOnly={readOnly}
				showAsSwitcher
				spritemap={spritemap}
				value={enableValidation}
				visible={visible}
			/>

			{enableValidation && (
                <>
                    <Select
                        disableEmptyOption
                        label={Liferay.Language.get('accept-date')}
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
                        label={Liferay.Language.get('starts-from')}
                        name={startsFrom.find(({checked}) => checked)}
                        options={startsFrom}
                        placeholder={Liferay.Language.get('choose-an-option')}
                        readOnly={readOnly}
                        showEmptyOption={false}
                        spritemap={spritemap}
                        value={[startsFrom.find(({checked}) => checked).value]}
                        visible={visible}
                    />

                    <input name={`${name}_parameter`} type="hidden" value={parameter} />

                    <Text
						label={Liferay.Language.get('error-message')}
						name={`${name}_errorMessage`}
						onChange={(event) => {
                            handleChange('errorMessage', event.target.value)
                        }}
						placeholder={Liferay.Language.get('error-message')}
						readOnly={readOnly}
						required={false}
						spritemap={spritemap}
						value={errorMessage}
						visible={visible}
					/>
                </>
            )}
		</ClayForm.Group>
    )
}

export default ValidationDate;