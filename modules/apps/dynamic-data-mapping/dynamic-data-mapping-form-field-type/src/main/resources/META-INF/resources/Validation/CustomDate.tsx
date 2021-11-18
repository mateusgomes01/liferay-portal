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

import {ClayInput, ClaySelectWithOption} from '@clayui/form';
// import ClayIcon from '@clayui/icon';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import React, {useMemo, FocusEvent, useState} from 'react';

// @ts-ignore

import Select from '../Select/Select.es';
import {limitValue} from '../util/numericalOperations';
// import './ValidationDate.scss';

// // @ts-ignore

// import {EVENT_TYPES} from './validationReducer';

const MAX_QUANTITY = 999;
const MIN_QUANTITY = 1;

const minusLabel = Liferay.Language.get('minus');
const plusLabel = Liferay.Language.get('plus');
// const daysLabel = Liferay.Language.get('days');
// const monthsLabel = Liferay.Language.get('months');
// const yearsLabel = Liferay.Language.get('years');

const operationsOptions: ISelectOptions[] = [
	{
		label: plusLabel,
		value: 'plus',
	},
	{
		label: minusLabel,
		value: 'minus',
	},
];

// const unitOptions = [
// 	{
// 		checked: false,
// 		label: daysLabel,
// 		name: 'days',
// 		value: 'days',
// 	},
// 	{
// 		checked: false,
// 		label: monthsLabel,
// 		name: 'months',
// 		value: 'months',
// 	},
// 	{
// 		checked: false,
// 		label: yearsLabel,
// 		name: 'years',
// 		value: 'years',
// 	},
// ];

// const getOperation = (quantity) => {
// 	return quantity < 0 ? 'minus' : 'plus';
// };

const CustomDate:React.FC<IProps> = ({
// 	dateFieldOptions,
// 	label,
	name,
	onChange,
// 	options,
	parameters: parametersInit,
// 	date,
	eventType,
// 	handleChangeParameters,
// 	localizationMode,
// 	operation,
// 	quantity,
	readOnly,
// 	setDate,
// 	setOperation,
// 	setQuantity,
// 	setUnit,
// 	unit,
	visible,
}) => {

    const [parameters, setParameters] = useState(parametersInit);
	
// 	const handleChange = (key: string, value: string, options?: {}) => {

// 		onChange(name,{
// 			...parameters,
// 			...options,
// 			[key]: value,
// 		});
// 	}
	
// 	const customDateSelectedOption = useMemo(() => { 
// 		if(parameters?.type === "dateField"){
// 			const date = dateFieldOptions.find(({name}) => 
// 				parameters.dateFieldName === name
// 			) as IDateFieldOption;

// 			return date.label;
// 		}

// 		const option = options?.find(({value}) => 
// 			value === parameters?.type
// 		);

// 		return option?.label;
// 	}, [])
	
// 	const customDateSelect = (
// 		<div className="form-builder-select-field input-group-container">
// 			<div className="form-control results-chosen select-field-trigger">
// 				<div className="option-selected">{customDateSelectedOption}</div>
// 				<a className="select-arrow-down-container">
// 					<ClayIcon symbol="caret-double" />
// 				</a>
// 			</div>
// 		</div>
// 	);

//     const customDateitems: {
// 		items?: {
// 			onClick: () => void;
// 			label: string;
// 			name: string;
// 		}[];
// 		onClick?: () => void;
// 		name?: 'responseDate';
// 		value?: 'responseDate';
// 		label?: string;
// 		type?: 'group' | 'divider';
// 	}[] = [
		
// 		{
// 			...(options.find(({name}) => name === 'responseDate') as {
// 				label: string;
// 				name: 'responseDate';
// 				value: 'responseDate';
// 			}),
// 			onClick: () => {
// 				handleChange('type', name);
// 			}
// 		},
// 		{
// 			type: 'divider',
// 		},
// 		{
// 			items: dateFieldOptions.map((option) => ({
// 				...option,
// 				onClick: () => {
// 					handleChange('type', 'dateField', {dateFieldName: option.name});
// 				},
// 			})),
// 			label: Liferay.Language.get('date-fields'),
// 			type: 'group',
// 		},
// 	];

	const handleBlur:(event: FocusEvent<HTMLInputElement>) => void = ({target: {value}}) => {
		const limit = limitValue({
			defaultValue: MIN_QUANTITY,
			max: MAX_QUANTITY,
			min: MIN_QUANTITY,
			value: parseInt(value,10),
		});

		const newValue = parameters.quantity < 0 ? limit * -1 : limit;

		if(newValue !== parameters.quantity) {
			onChange('quantity', newValue);
		}

	}

	return (
		<>
{/* // 			<Select
// 				label={Liferay.Language.get('date')}
// 				name={`selectedDate_${eventType}`}
// 				onChange={(event, value) => {
// 					setDate(value[0]);
// 					handleChangeParameters(value[0], eventType, 'date');
// 				}}
// 				options={customDateitems}
// 				readOnly={readOnly || localizationMode}
// 				showEmptyOption={false}
// 				value={date}
// 				visible={visible}
// 			/>
// 			<div className="align-items-end d-flex position-relative"> */}
				<div className="ddm-form-field-type__validation-date pr-2">
					<label>
						{Liferay.Language.get('operation')}
						<ClaySelectWithOption
							name={`selectedOperation_${eventType}`}
							onChange={({target: {}}) => {
								// setOperation(value);
							}}
							options={operationsOptions}
							// readOnly={readOnly}
							// showEmptyOption={false}
							value={['minus']}
							// visible={visible}
						/>
					</label>
				</div>
				<div className="ddm-form-field-type__validation-date pr-2">
					<div className="form-group">
						<label>
							{Liferay.Language.get('quantity')}
							<ClayInput
								className="ddm-field-text"
								disabled={readOnly}
								max={MAX_QUANTITY}
								min={MIN_QUANTITY}
								name={`inputedQuantity_${eventType}`}
								onBlur={handleBlur}
								onChange={({target: {value}}) => {
									onChange('quantity', parseInt(value,10));
								}}
								type="number"
								value={Math.abs(parameters.quantity)}
							/>
						</label>
						
					</div>
				</div>
				{/*<div className="ddm-form-field-type__validation-date">
// 					<Select
// 						label={Liferay.Language.get('unit')}
// 						name={`selectedUnit_${eventType}`}
// 						onChange={(event, value) => {
// 							setUnit(value[0]);
// 							handleChangeParameters(value[0], eventType, 'unit');
// 						}}
// 						options={unitOptions}
// 						readOnly={readOnly || localizationMode}
// 						showEmptyOption={false}
// 						value={unit}
// 						visible={visible}
// 					/>
// 				</div>
						// 			</div>*/}
		</>
	);
};

export default CustomDate;

interface IProps {
    eventType: 'startsFrom' | 'endsOn';
    readOnly?: boolean;
// 	label: string;
	name: string;
// 	options: IOptions[];
	onChange: (key: string, value: string | number) => void; //(value: string, typeName: string, type: DateType) => void;
// 	tooltip: string;
	parameters: {
		quantity: number;
	};
// 	dateFieldOptions: IDateFieldOption[];
	visible: boolean;
}

// interface IDateFieldOption {
// 	label: string;
// 	name: string;
// }

interface IOptions {
	label: string;
	name: 'customDate' | 'responseDate';
	value: 'customDate' | 'responseDate';
}

interface ISelectOptions {
	label: string;
	value: 'minus' | 'plus';
}

// type DateType = 'customDate' | 'responseDate' | 'dateFieldName';
