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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useMemo} from 'react';

import Select from '../Select/Select.es';

import './StartEndDate.scss';

const StartEndDate: React.FC<IProps> = ({
	dateFieldOptions,
	label,
	name,
	onChange,
	options,
	parameters,
	tooltip,
}) => {

	const selectedOption = useMemo(()=>{
		if(parameters.type === "dateField"){
			const date = dateFieldOptions.find(({name}) => 
				parameters.dateFieldName === name
			)
			return date?.label ?? "Date Undefined";
		}

		const option = options.find(({value}) => 
			value === parameters.type
		);

		return option?.label ?? "Response Date";
	},[parameters]);

	const select = (
		<div className="form-builder-select-field input-group-container">
			<div className="form-control results-chosen select-field-trigger">
				<div className="option-selected">{selectedOption}</div>
				<a className="select-arrow-down-container">
					<ClayIcon symbol="caret-double" />
				</a>
			</div>
		</div>
	);

	const items: {
		items?: {
			onClick: () => void;
			label: string;
			name: string;
		}[];
		onClick?: () => void;
		name?: 'customDate' | 'responseDate';
		value?: 'customDate' | 'responseDate';
		label?: string;
		type?: 'group' | 'divider';
	}[] = [
		...options.map((option) => ({
			...option,
			onClick: () => {
				onChange(option.name, name, option.value);
			},
		})),
		{
			type: 'divider',
		},
		{
			items: dateFieldOptions.map((dateFieldOption) => ({
				...dateFieldOption,
				onClick: () => {
					onChange(dateFieldOption.name, name, 'dateFieldName');
				},
			})),
			label: Liferay.Language.get('date-fields'),
			type: 'group',
		},
	];

	return (
		<>
			{/* <div className="align-items-end d-flex position-relative">
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
			</div> */}
		</>
	);
};

export default StartEndDate;

interface IProps {
	label: string;
	name: string;
	options: IOptions[];
	onChange: (value: string, typeName: string, type: DateType) => void;
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
