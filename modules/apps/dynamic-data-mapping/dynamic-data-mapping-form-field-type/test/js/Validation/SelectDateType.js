import '@testing-library/jest-dom/extend-expect';
import {render} from '@testing-library/react';
import React from 'react';

import SelectDateType from '../../../src/main/resources/META-INF/resources/Validation/SelectDateType';

const SelectDateTypeProvider = ({builderPages = [], ...props}) => (
	<FormProvider
		initialState={{builderPages}}
	>
		<SelectDateType {...props} />
	</FormProvider>
);

it('shows date field from form builder', () => {
    const builderPages = [
        {
            rows: [
                {
                    columns: [
                        {
                            fields: [
                                {
                                    fieldName: 'Date12345678',
                                    label: 'Date Field A',
                                    type: 'date',
                                },
                            ],
                        },
                    ],
                },
            ],
        },
    ];
    
    const parameter = {
        en_US: {
            endsOn: {
                date: 'responseDate',
                dateFieldName: 'Date12345678',
                quantity: -1,
                type: 'dateField',
                unit: 'days',
            },
        },
    };

    const localizedValue = jest.fn(() => parameter['en_US']);

    const {getAllByText} = render(
        <ValidationDateProvider
            builderPages={builderPages}
            localizedValue={localizedValue}
            name="validationDate"
            onChange={() => {}}
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

    const [firstOption] = getAllByText('Date Field A');

    expect(firstOption).toHaveClass('option-selected');
})