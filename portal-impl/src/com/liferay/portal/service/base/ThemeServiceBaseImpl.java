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

package com.liferay.portal.service.base;

import com.liferay.counter.kernel.service.persistence.CounterPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.service.BaseServiceImpl;
import com.liferay.portal.kernel.service.ThemeService;
import com.liferay.portal.kernel.service.persistence.PluginSettingPersistence;
import com.liferay.portal.kernel.util.InfrastructureUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the theme remote service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.ThemeServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.ThemeServiceImpl
 * @see com.liferay.portal.kernel.service.ThemeServiceUtil
 * @generated
 */
public abstract class ThemeServiceBaseImpl extends BaseServiceImpl
	implements ThemeService, IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portal.kernel.service.ThemeServiceUtil} to access the theme remote service.
	 */

	/**
	 * Returns the theme local service.
	 *
	 * @return the theme local service
	 */
	public com.liferay.portal.kernel.service.ThemeLocalService getThemeLocalService() {
		return themeLocalService;
	}

	/**
	 * Sets the theme local service.
	 *
	 * @param themeLocalService the theme local service
	 */
	public void setThemeLocalService(
		com.liferay.portal.kernel.service.ThemeLocalService themeLocalService) {
		this.themeLocalService = themeLocalService;
	}

	/**
	 * Returns the theme remote service.
	 *
	 * @return the theme remote service
	 */
	public ThemeService getThemeService() {
		return themeService;
	}

	/**
	 * Sets the theme remote service.
	 *
	 * @param themeService the theme remote service
	 */
	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the counter persistence.
	 *
	 * @return the counter persistence
	 */
	public CounterPersistence getCounterPersistence() {
		return counterPersistence;
	}

	/**
	 * Sets the counter persistence.
	 *
	 * @param counterPersistence the counter persistence
	 */
	public void setCounterPersistence(CounterPersistence counterPersistence) {
		this.counterPersistence = counterPersistence;
	}

	/**
	 * Returns the layout template local service.
	 *
	 * @return the layout template local service
	 */
	public com.liferay.portal.kernel.service.LayoutTemplateLocalService getLayoutTemplateLocalService() {
		return layoutTemplateLocalService;
	}

	/**
	 * Sets the layout template local service.
	 *
	 * @param layoutTemplateLocalService the layout template local service
	 */
	public void setLayoutTemplateLocalService(
		com.liferay.portal.kernel.service.LayoutTemplateLocalService layoutTemplateLocalService) {
		this.layoutTemplateLocalService = layoutTemplateLocalService;
	}

	/**
	 * Returns the plugin setting local service.
	 *
	 * @return the plugin setting local service
	 */
	public com.liferay.portal.kernel.service.PluginSettingLocalService getPluginSettingLocalService() {
		return pluginSettingLocalService;
	}

	/**
	 * Sets the plugin setting local service.
	 *
	 * @param pluginSettingLocalService the plugin setting local service
	 */
	public void setPluginSettingLocalService(
		com.liferay.portal.kernel.service.PluginSettingLocalService pluginSettingLocalService) {
		this.pluginSettingLocalService = pluginSettingLocalService;
	}

	/**
	 * Returns the plugin setting remote service.
	 *
	 * @return the plugin setting remote service
	 */
	public com.liferay.portal.kernel.service.PluginSettingService getPluginSettingService() {
		return pluginSettingService;
	}

	/**
	 * Sets the plugin setting remote service.
	 *
	 * @param pluginSettingService the plugin setting remote service
	 */
	public void setPluginSettingService(
		com.liferay.portal.kernel.service.PluginSettingService pluginSettingService) {
		this.pluginSettingService = pluginSettingService;
	}

	/**
	 * Returns the plugin setting persistence.
	 *
	 * @return the plugin setting persistence
	 */
	public PluginSettingPersistence getPluginSettingPersistence() {
		return pluginSettingPersistence;
	}

	/**
	 * Sets the plugin setting persistence.
	 *
	 * @param pluginSettingPersistence the plugin setting persistence
	 */
	public void setPluginSettingPersistence(
		PluginSettingPersistence pluginSettingPersistence) {
		this.pluginSettingPersistence = pluginSettingPersistence;
	}

	public void afterPropertiesSet() {
	}

	public void destroy() {
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return ThemeService.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = InfrastructureUtil.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.portal.kernel.service.ThemeLocalService.class)
	protected com.liferay.portal.kernel.service.ThemeLocalService themeLocalService;
	@BeanReference(type = ThemeService.class)
	protected ThemeService themeService;
	@BeanReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@BeanReference(type = CounterPersistence.class)
	protected CounterPersistence counterPersistence;
	@BeanReference(type = com.liferay.portal.kernel.service.LayoutTemplateLocalService.class)
	protected com.liferay.portal.kernel.service.LayoutTemplateLocalService layoutTemplateLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.PluginSettingLocalService.class)
	protected com.liferay.portal.kernel.service.PluginSettingLocalService pluginSettingLocalService;
	@BeanReference(type = com.liferay.portal.kernel.service.PluginSettingService.class)
	protected com.liferay.portal.kernel.service.PluginSettingService pluginSettingService;
	@BeanReference(type = PluginSettingPersistence.class)
	protected PluginSettingPersistence pluginSettingPersistence;
}