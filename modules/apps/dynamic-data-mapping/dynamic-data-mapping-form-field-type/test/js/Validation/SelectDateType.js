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

import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import {FormProvider} from 'data-engine-js-components-web';
import React from 'react';

import SelectDateType from '../../../src/main/resources/META-INF/resources/Validation/SelectDateType.tsx';

const validations = [
	{
		checked: false,
		label: 'Future Dates',
		name: 'futureDates',
		parameterMessage: '',
		template: 'futureDates({name}, "{parameter}")',
		value: 'futureDates',
	},
	{
		checked: false,
		label: 'Past Dates',
		name: 'pastDates',
		parameterMessage: '',
		template: 'pastDates({name}, "{parameter}")',
		value: 'pastDates',
	},
	{
		checked: false,
		label: 'Range',
		name: 'dateRange',
		parameterMessage: '',
		template: 'dateRange({name}, "{parameter}")',
		value: 'dateRange',
	},
];

const SelectDateTypeProvider = ({builderPages = [], ...props}) => (
	<FormProvider
		initialState={{builderPages}}
	>
		<SelectDateType {...props} />
	</FormProvider>
);

describe('SelectDateType', () => {

    it("doesn't show repeatable date field from form builder", () => {
        const fields = [
            {
                fieldName: 'Date12345678',
                label: 'Date Field A',
                type: 'date',
                repeatable: 'true'
            },
        ]

        const options = [
            {
                label: 'Response Date',
                name: 'responseDate',
                value: 'responseDate'
            },
            {
                label: 'Custom Date',
                name: 'customDate',
                value: 'customDate'
            }
        ]
        
        const parameter = {
            en_US: {
                endsOn: {
                    date: 'responseDate',
                    type: 'responseDate',
                    quantity: 1,
                    unit: 'days',
                },
            },
        };
    
        const localizedValue = jest.fn(() => parameter['en_US']);
    
        const {getAllByText} = render(
            <SelectDateTypeProvider
                // builderPages={builderPages}
                dateFieldOptions={fields}
                localizedValue={localizedValue}
                name="validationDate"
                onChange={() => {}}
                options={options}
                parameter={parameter}
                selectedValidation={{
                    label: '',
                    name: 'pastDates',
                    parameterMessage: '',
                    template: 'pastDates({name}, "{parameter}")',
                }}
                validations={validations}
                visible={true}
            />
        );
    
        const [firstOption] = getAllByText('x');
    
        expect(firstOption).toHaveClass('option-selected');
    })
});
