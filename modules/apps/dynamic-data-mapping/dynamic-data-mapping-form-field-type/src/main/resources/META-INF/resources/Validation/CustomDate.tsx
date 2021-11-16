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

import {ClayInput} from '@clayui/form';
import {PagesVisitor, useFormState} from 'data-engine-js-components-web';
import React, {useMemo, useState} from 'react';

import Select from '../Select/Select.es';
import StartEndDate from './StartEndDate';

import './ValidationDate.scss';
import {EVENT_TYPES} from './validationReducer';

const CustomDates = ({
	dateFieldOptions,
	label,
	name,
	onChange,
	options,
	parameters,
	tooltip,
	date,
	eventType,
	handleChangeParameters,
	localizationMode,
	name,
	operation,
	quantity,
	readOnly,
	setDate,
	setOperation,
	setQuantity,
	setUnit,
	unit,
	visible,
}) => {

    const customDateitems: {
		items?: {
			onClick: () => void;
			label: string;
			name: string;
		}[];
		onClick?: () => void;
		name?: 'responseDate';
		value?: 'responseDate';
		label?: string;
		type?: 'group' | 'divider';
	}[] = [
		
		{
			...(options.find(({name}) => name === 'responseDate') as {
				label: string;
				name: 'responseDate';
				value: 'responseDate';
			}),
			onClick: () => {
				handleChange('type', name);
			}
		},
		{
			type: 'divider',
		},
		{
			items: dateFieldOptions.map((option) => ({
				...option,
				onClick: () => {
					handleChange('type', 'dateField', {dateFieldName: option.name});
				},
			})),
			label: Liferay.Language.get('date-fields'),
			type: 'group',
		},
	];

	return (
		<>
			<Select
				label={Liferay.Language.get('date')}
				name={`selectedDate_${eventType}`}
				onChange={(event, value) => {
					setDate(value[0]);
					handleChangeParameters(value[0], eventType, 'date');
				}}
				options={[responseDateOption]}
				readOnly={readOnly || localizationMode}
				showEmptyOption={false}
				value={date}
				visible={visible}
			/>
			<div className="align-items-end d-flex position-relative">
				<div className="ddm-form-field-type__validation-date pr-2">
					<Select
						label={Liferay.Language.get('operation')}
						name={`selectedOperation_${eventType}`}
						onChange={(event, value) => {
							setOperation(value[0]);
							handleChangeParameters(
								value[0],
								eventType,
								'quantity'
							);
						}}
						options={operationsOptions}
						readOnly={readOnly || localizationMode}
						showEmptyOption={false}
						value={operation}
						visible={visible}
					/>
				</div>
				<div className="ddm-form-field-type__validation-date pr-2">
					<div className="form-group">
						<label htmlFor={`${name}_validation_date_quantity`}>
							{Liferay.Language.get('quantity')}
						</label>
						<ClayInput
							className="ddm-field-text"
							disabled={readOnly}
							id={`${name}_validation_date_quantity`}
							max={MAX_QUANTITY}
							name={`inputedQuantity_${eventType}`}
							onBlur={(event) => {
								let {value: newValue} = event.target;

								newValue = limitValue({
									defaultValue: MIN_QUANTITY,
									max: MAX_QUANTITY,
									min: MIN_QUANTITY,
									value: newValue,
								});

								newValue =
									operation === 'minus'
										? (newValue * -1).toString()
										: newValue;

								setQuantity(newValue);
								handleChangeParameters(
									newValue,
									eventType,
									'quantity'
								);
							}}
							onChange={({target: {value}}) => {
								setQuantity(value);
							}}
							type="number"
							value={quantity === '' ? '' : Math.abs(quantity)}
						/>
					</div>
				</div>
				<div className="ddm-form-field-type__validation-date">
					<Select
						label={Liferay.Language.get('unit')}
						name={`selectedUnit_${eventType}`}
						onChange={(event, value) => {
							setUnit(value[0]);
							handleChangeParameters(value[0], eventType, 'unit');
						}}
						options={unitOptions}
						readOnly={readOnly || localizationMode}
						showEmptyOption={false}
						value={unit}
						visible={visible}
					/>
				</div>
			</div>
		</>
	);
};

export default CustomDate;

interface IProps {
	label: string;
	name: string;
	options: IOptions[];
	onChange: any; //(value: string, typeName: string, type: DateType) => void;
	tooltip: string;
	parameters: any;
	dateFieldOptions: IDateFieldOption[];
}

interface IDateFieldOption {
	label: string;
	name: string;
}

interface IOptions {
	label: string;
	name: 'customDate' | 'responseDate';
	value: 'customDate' | 'responseDate';
}

type DateType = 'customDate' | 'responseDate' | 'dateFieldName';
