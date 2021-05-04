<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
Group siteGroup = themeDisplay.getSiteGroup();

Group liveGroup = null;

if (siteGroup.isStagingGroup()) {
	liveGroup = siteGroup.getLiveGroup();
}
else {
	liveGroup = siteGroup;
}
%>

<portlet:actionURL name="/site_admin/edit_documents_and_media" var="editDocumentsAndMediaURL">
	<portlet:param name="mvcRenderCommandName" value="/configuration_admin/view_configuration_screen" />
	<portlet:param name="configurationScreenKey" value="site-configuration-documents-and-media" />
</portlet:actionURL>

<liferay-frontend:edit-form
	action="<%= editDocumentsAndMediaURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroup.getGroupId() %>" />

	<liferay-frontend:edit-form-body>
		<aui:input helpMessage='<%= LanguageUtil.format(request, "directory-indexing-help", new Object[] {HtmlUtil.escape(siteGroup.getDescriptiveName(themeDisplay.getLocale())), themeDisplay.getPortalURL() + "/documents" + siteGroup.getFriendlyURL()}, false) %>' inlineLabel="right" label="enable-directory-indexing" labelCssClass="simple-toggle-switch" name="TypeSettingsProperties--directoryIndexingEnabled--" type="toggle-switch" value='<%= PropertiesParamUtil.getBoolean(liveGroup.getTypeSettingsProperties(), request, "directoryIndexingEnabled") %>' />
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href='<%= ParamUtil.getString(request, "redirect") %>' type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>