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

package com.liferay.site.memberships.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Rodrigo Paulino
 */
@ExtendedObjectClassDefinition(category = "web-experience")
@Meta.OCD(
	id = "com.liferay.site.memberships.web.internal.configuration.SiteMembershipsConfiguration",
	localization = "content/Language",
	name = "site-memberships-configuration-name"
)
public interface SiteMembershipsConfiguration {

	@Meta.AD(
		description = "enable-assign-unassign-roles-of-user-groups-actions-help",
		name = "enable-assign-unassign-roles-of-user-groups-actions",
		required = false
	)
	public boolean enableAssignUnassignUserGroupsRolesActions();

}