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

import {ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

import './StartEndDate.scss';

const StartEndDate: React.FC<IProps> = ({
	dateFieldOptions,
	label,
	name,
	onChange,
	options,
	selectedOption,
	tooltip,
}) => {
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
			<div className="ddm__validation-date-start-end">
				<label>{label}</label>
				<ClayTooltipProvider>
					<div data-tooltip-align="top" title={tooltip}>
						<ClayIcon
							className="ddm__validation-date-start-end-icon"
							symbol="question-circle-full"
						/>
					</div>
				</ClayTooltipProvider>
			</div>

			<ClayDropDownWithItems items={items} trigger={select} />
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
	selectedOption: string;
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
