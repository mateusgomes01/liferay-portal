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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Bruno Oliveira
 * @author Carolina Barbosa
 */
public class FutureDatesFunction
	implements DDMExpressionFunction.Function2<Object, Object, Boolean> {

	public static final String NAME = "futureDates";

	@Override
	public Boolean apply(Object object1, Object object2) {
		if (object1 == null) {
			return false;
		}

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
				"MM/dd/yyyy");

			LocalDate date = LocalDate.parse(object1.toString(), formatter);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				object2.toString());

			if (jsonObject.has("startsFrom")) {
				String startsFrom = jsonObject.getString("startsFrom");

				LocalDate currentDate = LocalDate.now();

				if (StringUtil.equals(startsFrom, "responseDate") &&
					!date.isAfter(currentDate)) {

					return false;
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return true;
	}

	@Override
	public String getName() {
		return NAME;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FutureDatesFunction.class);

}