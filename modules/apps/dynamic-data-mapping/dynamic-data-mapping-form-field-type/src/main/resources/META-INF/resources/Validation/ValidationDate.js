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

import ValidationCheckbox from './ValidationCheckbox';

const ValidationDate = ({label, readOnly, spritemap, visible}) => {
	const [enabled, setEnabled] = useState(false);

	return (
		<ValidationCheckbox
			label={label}
			onChange={(event, value) => setEnabled(value)}
			readOnly={readOnly}
			spritemap={spritemap}
			value={enabled}
			visible={visible}
		>
			{enabled ? <div /> : <></>}
		</ValidationCheckbox>
	);
};

export default ValidationDate;
