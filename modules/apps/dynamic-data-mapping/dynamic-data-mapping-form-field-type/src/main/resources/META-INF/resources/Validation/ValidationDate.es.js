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


import React, {useState} from 'react';
import Text from '../Text/Text.es';
import ClayForm from '@clayui/form';
import Checkbox from '../Checkbox/Checkbox.es';
import Select from '../Select/Select.es';

const ACCEPTED_DATE_OPTIONS = [
    {
        label: Liferay.Language.get('future-dates'),
        name: 'future-dates',
        value: 'future-dates'
    }
];

const STARTS_FROM_OPTIONS = [
    {
        label: Liferay.Language.get('response-date'),
        name: 'response-date',
        value: JSON.stringify({
            startsFrom: 'responseDate'
        })
    }
];

const NEXT_STEP = {
    'future-dates': STARTS_FROM_OPTIONS
}

const ValidationDate = ({
    errorMessage,
    label,
    name,
    readOnly,
    spritemap,
    visible,
}) => {
    const [enableValidation, setEnableValidation] = useState(false);
    const [nextStep, setNextStep] = useState('');

    return (
        <ClayForm.Group className="lfr-ddm-form-field-validation">
			<Checkbox
				label={label}
				name="enableValidation"
				onChange={(event, value) => setEnableValidation(value)}
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
                        label={Liferay.Language.get('accepted-dates')}
                        name={ACCEPTED_DATE_OPTIONS[0].name}
                        onChange={(event, value) => {
                            console.log('nextStep', value);
                            setNextStep(value);
                        }}
                        options={ACCEPTED_DATE_OPTIONS}
                        placeholder={Liferay.Language.get('choose-an-option')}
                        readOnly={readOnly}
                        showEmptyOption={false}
                        spritemap={spritemap}
                        visible={visible}
                        value={[ACCEPTED_DATE_OPTIONS[0].name]}
                    />

                    {!!nextStep && (
                        <Select
                            disableEmptyOption
                            label={Liferay.Language.get('starts-from')}
                            name={NEXT_STEP[nextStep][0].name}
                            onChange={(event, value) => {
                                console.log('nextStep', value);
                            }}
                            options={NEXT_STEP[nextStep]}
                            placeholder={Liferay.Language.get('choose-an-option')}
                            readOnly={readOnly}
                            showEmptyOption={false}
                            spritemap={spritemap}
                            visible={visible}
                            value={[NEXT_STEP[nextStep][0].name]}
                        />
                    )}
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
		</ClayForm.Group>
    )
}

export default ValidationDate;