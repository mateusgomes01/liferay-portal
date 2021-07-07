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

import {act, cleanup, /* userEvent, */ render} from '@testing-library/react';
import userEvent from '@testing-library/user-event'
import {FormProvider} from 'data-engine-js-components-web';
import React from 'react';

import Validation from '../../../src/main/resources/META-INF/resources/Validation/Validation';
import {VALIDATIONS} from '../utils/validations';

const globalLanguageDirection = Liferay.Language.direction;

const spritemap = 'icons.svg';

const defaultValue = {
	errorMessage: {},
	expression: {},
	parameter: {},
};

const ValidationWithProvider = (props) => (
	<FormProvider value={{validations: VALIDATIONS}}>
		<Validation {...props} />
	</FormProvider>
)

describe('Validation', () => {

	beforeAll(() => {
		Liferay.Language.direction = {
			en_US: 'rtl',
		};
	});

	afterAll(() => {
		Liferay.Language.direction = globalLanguageDirection;
	});

	afterEach(cleanup);

	it('renders checkbox to enable Validation', () => {

		const {container} = render(
			<ValidationWithProvider
				dataType="string"
				label="Validator"
				name="validation"
				onChange={()=>{}}
				spritemap={spritemap}
				value={defaultValue}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('enables validation after click on toogle', () => {

		const {container} = render(
			<ValidationWithProvider
				dataType="string"
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				expression={{}}
				label="Validator"
				name="validation"
				onChange={()=>{}}
				spritemap={spritemap}
				validation={{
					dataType: 'string',
					fieldName: 'textfield',
				}}
				value={defaultValue}
			/>
		);

		const inputCheckbox = container.querySelector('input[type="checkbox"]');

		userEvent.click(inputCheckbox);

		act(() => {
			jest.runAllTimers();
		});

		expect(onChange).toHaveBeenCalledWith(expect.any(Object), {
			enableValidation: true,
			errorMessage: {
				en_US: undefined,
			},
			expression: {
				name: 'contains',
				value: 'contains(textfield, "{parameter}")',
			},
			parameter: {
				en_US: undefined,
			},
		});
	});

	it('renders parameter field with Numeric element', () => {

		const {container} = render(
			<ValidationWithProvider
				dataType="numeric"
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				expression={{}}
				label="Validator"
				name="validation"
				onChange={()=>{}}
				spritemap={spritemap}
				validation={{
					dataType: 'integer',
					fieldName: 'numericfield',
				}}
				value={defaultValue}
			/>
		);

		const inputCheckbox = container.querySelector('input[type="checkbox"]');

		userEvent.click(inputCheckbox);

		act(() => {
			jest.runAllTimers();
		});

		expect(onChange).toHaveBeenCalledWith(expect.any(Object), {
			enableValidation: true,
			errorMessage: {
				en_US: undefined,
			},
			expression: {
				name: 'eq',
				value: 'numericfield=={parameter}',
			},
			parameter: {
				en_US: undefined,
			},
		});
	});

	it('renders parameter field with Date element', () => {

		const {container} = render(
			<ValidationWithProvider
				dataType="date"
				defaultLanguageId="en_US"
				editingLanguageId="en_US"
				expression={{}}
				label="Validator"
				name="validation"
				onChange={()=>{}}
				spritemap={spritemap}
				validation={{
					dataType: 'date',
					fieldName: 'dateField',
				}}
				value={defaultValue}
			/>
		);

		const inputCheckbox = container.querySelector('input[type="checkbox"]');

		userEvent.click(inputCheckbox);

		act(() => {
			jest.runAllTimers();
		});

		expect(onChange).toHaveBeenCalledWith(expect.any(Object), {
			enableValidation: true,
			errorMessage: {
				en_US: undefined,
			},
			expression: {
				name: 'futureDates',
				value: 'futureDates(dateField, "{parameter}")',
			},
			parameter: {
				en_US: undefined,
			},
		});
	});
});
