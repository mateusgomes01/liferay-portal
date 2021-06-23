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

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Bruno Oliveira
 * @author Carolina Barbosa
 */
public class FutureDatesFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApplyFalse1() {
		FutureDatesFunction futureDatesFunction = new FutureDatesFunction();

		Assert.assertFalse(
			futureDatesFunction.apply(
				"06/21/2021", "{'startsFrom':'responseDate'}"));
	}

	@Test
	public void testApplyFalse2() {
		FutureDatesFunction futureDatesFunction = new FutureDatesFunction();

		Assert.assertFalse(
			futureDatesFunction.apply(null, "{'startsFrom':'responseDate'}"));
	}

	@Test
	public void testApplyTrue1() {
		FutureDatesFunction futureDatesFunction = new FutureDatesFunction();

		Assert.assertTrue(
			futureDatesFunction.apply(
				"06/22/2100", "{'startsFrom':'responseDate'}"));
	}

}