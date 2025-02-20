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

package com.liferay.segments.model.impl;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.model.SegmentsExperimentRelModel;
import com.liferay.segments.model.SegmentsExperimentRelSoap;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Blob;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the SegmentsExperimentRel service. Represents a row in the &quot;SegmentsExperimentRel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>SegmentsExperimentRelModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SegmentsExperimentRelImpl}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentRelImpl
 * @generated
 */
@JSON(strict = true)
public class SegmentsExperimentRelModelImpl
	extends BaseModelImpl<SegmentsExperimentRel>
	implements SegmentsExperimentRelModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a segments experiment rel model instance should use the <code>SegmentsExperimentRel</code> interface instead.
	 */
	public static final String TABLE_NAME = "SegmentsExperimentRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"segmentsExperimentRelId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"segmentsExperimentId", Types.BIGINT},
		{"segmentsExperienceId", Types.BIGINT}, {"split", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("segmentsExperimentRelId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("segmentsExperimentId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("segmentsExperienceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("split", Types.DOUBLE);
	}

	public static final String TABLE_SQL_CREATE =
		"create table SegmentsExperimentRel (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,segmentsExperimentRelId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,segmentsExperimentId LONG,segmentsExperienceId LONG,split DOUBLE,primary key (segmentsExperimentRelId, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
		"drop table SegmentsExperimentRel";

	public static final String ORDER_BY_JPQL =
		" ORDER BY segmentsExperimentRel.segmentsExperimentRelId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY SegmentsExperimentRel.segmentsExperimentRelId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long SEGMENTSEXPERIENCEID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long SEGMENTSEXPERIMENTID_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long SEGMENTSEXPERIMENTRELID_COLUMN_BITMASK = 4L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static SegmentsExperimentRel toModel(
		SegmentsExperimentRelSoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		SegmentsExperimentRel model = new SegmentsExperimentRelImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setCtCollectionId(soapModel.getCtCollectionId());
		model.setSegmentsExperimentRelId(
			soapModel.getSegmentsExperimentRelId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setSegmentsExperimentId(soapModel.getSegmentsExperimentId());
		model.setSegmentsExperienceId(soapModel.getSegmentsExperienceId());
		model.setSplit(soapModel.getSplit());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static List<SegmentsExperimentRel> toModels(
		SegmentsExperimentRelSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<SegmentsExperimentRel> models =
			new ArrayList<SegmentsExperimentRel>(soapModels.length);

		for (SegmentsExperimentRelSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public SegmentsExperimentRelModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _segmentsExperimentRelId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSegmentsExperimentRelId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _segmentsExperimentRelId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return SegmentsExperimentRel.class;
	}

	@Override
	public String getModelClassName() {
		return SegmentsExperimentRel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<SegmentsExperimentRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<SegmentsExperimentRel, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SegmentsExperimentRel, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((SegmentsExperimentRel)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<SegmentsExperimentRel, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<SegmentsExperimentRel, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(SegmentsExperimentRel)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<SegmentsExperimentRel, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<SegmentsExperimentRel, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, SegmentsExperimentRel>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			SegmentsExperimentRel.class.getClassLoader(),
			SegmentsExperimentRel.class, ModelWrapper.class);

		try {
			Constructor<SegmentsExperimentRel> constructor =
				(Constructor<SegmentsExperimentRel>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<SegmentsExperimentRel, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<SegmentsExperimentRel, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<SegmentsExperimentRel, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<SegmentsExperimentRel, Object>>();
		Map<String, BiConsumer<SegmentsExperimentRel, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<SegmentsExperimentRel, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", SegmentsExperimentRel::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<SegmentsExperimentRel, Long>)
				SegmentsExperimentRel::setMvccVersion);
		attributeGetterFunctions.put(
			"ctCollectionId", SegmentsExperimentRel::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<SegmentsExperimentRel, Long>)
				SegmentsExperimentRel::setCtCollectionId);
		attributeGetterFunctions.put(
			"segmentsExperimentRelId",
			SegmentsExperimentRel::getSegmentsExperimentRelId);
		attributeSetterBiConsumers.put(
			"segmentsExperimentRelId",
			(BiConsumer<SegmentsExperimentRel, Long>)
				SegmentsExperimentRel::setSegmentsExperimentRelId);
		attributeGetterFunctions.put(
			"groupId", SegmentsExperimentRel::getGroupId);
		attributeSetterBiConsumers.put(
			"groupId",
			(BiConsumer<SegmentsExperimentRel, Long>)
				SegmentsExperimentRel::setGroupId);
		attributeGetterFunctions.put(
			"companyId", SegmentsExperimentRel::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<SegmentsExperimentRel, Long>)
				SegmentsExperimentRel::setCompanyId);
		attributeGetterFunctions.put(
			"userId", SegmentsExperimentRel::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<SegmentsExperimentRel, Long>)
				SegmentsExperimentRel::setUserId);
		attributeGetterFunctions.put(
			"userName", SegmentsExperimentRel::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<SegmentsExperimentRel, String>)
				SegmentsExperimentRel::setUserName);
		attributeGetterFunctions.put(
			"createDate", SegmentsExperimentRel::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<SegmentsExperimentRel, Date>)
				SegmentsExperimentRel::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", SegmentsExperimentRel::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<SegmentsExperimentRel, Date>)
				SegmentsExperimentRel::setModifiedDate);
		attributeGetterFunctions.put(
			"segmentsExperimentId",
			SegmentsExperimentRel::getSegmentsExperimentId);
		attributeSetterBiConsumers.put(
			"segmentsExperimentId",
			(BiConsumer<SegmentsExperimentRel, Long>)
				SegmentsExperimentRel::setSegmentsExperimentId);
		attributeGetterFunctions.put(
			"segmentsExperienceId",
			SegmentsExperimentRel::getSegmentsExperienceId);
		attributeSetterBiConsumers.put(
			"segmentsExperienceId",
			(BiConsumer<SegmentsExperimentRel, Long>)
				SegmentsExperimentRel::setSegmentsExperienceId);
		attributeGetterFunctions.put("split", SegmentsExperimentRel::getSplit);
		attributeSetterBiConsumers.put(
			"split",
			(BiConsumer<SegmentsExperimentRel, Double>)
				SegmentsExperimentRel::setSplit);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_ctCollectionId = ctCollectionId;
	}

	@JSON
	@Override
	public long getSegmentsExperimentRelId() {
		return _segmentsExperimentRelId;
	}

	@Override
	public void setSegmentsExperimentRelId(long segmentsExperimentRelId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_segmentsExperimentRelId = segmentsExperimentRelId;
	}

	@JSON
	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_groupId = groupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getSegmentsExperimentId() {
		return _segmentsExperimentId;
	}

	@Override
	public void setSegmentsExperimentId(long segmentsExperimentId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_segmentsExperimentId = segmentsExperimentId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalSegmentsExperimentId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("segmentsExperimentId"));
	}

	@JSON
	@Override
	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	@Override
	public void setSegmentsExperienceId(long segmentsExperienceId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_segmentsExperienceId = segmentsExperienceId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalSegmentsExperienceId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("segmentsExperienceId"));
	}

	@JSON
	@Override
	public double getSplit() {
		return _split;
	}

	@Override
	public void setSplit(double split) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_split = split;
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (!Objects.equals(
					entry.getValue(), getColumnValue(entry.getKey()))) {

				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), SegmentsExperimentRel.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public SegmentsExperimentRel toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, SegmentsExperimentRel>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		SegmentsExperimentRelImpl segmentsExperimentRelImpl =
			new SegmentsExperimentRelImpl();

		segmentsExperimentRelImpl.setMvccVersion(getMvccVersion());
		segmentsExperimentRelImpl.setCtCollectionId(getCtCollectionId());
		segmentsExperimentRelImpl.setSegmentsExperimentRelId(
			getSegmentsExperimentRelId());
		segmentsExperimentRelImpl.setGroupId(getGroupId());
		segmentsExperimentRelImpl.setCompanyId(getCompanyId());
		segmentsExperimentRelImpl.setUserId(getUserId());
		segmentsExperimentRelImpl.setUserName(getUserName());
		segmentsExperimentRelImpl.setCreateDate(getCreateDate());
		segmentsExperimentRelImpl.setModifiedDate(getModifiedDate());
		segmentsExperimentRelImpl.setSegmentsExperimentId(
			getSegmentsExperimentId());
		segmentsExperimentRelImpl.setSegmentsExperienceId(
			getSegmentsExperienceId());
		segmentsExperimentRelImpl.setSplit(getSplit());

		segmentsExperimentRelImpl.resetOriginalValues();

		return segmentsExperimentRelImpl;
	}

	@Override
	public SegmentsExperimentRel cloneWithOriginalValues() {
		SegmentsExperimentRelImpl segmentsExperimentRelImpl =
			new SegmentsExperimentRelImpl();

		segmentsExperimentRelImpl.setMvccVersion(
			this.<Long>getColumnOriginalValue("mvccVersion"));
		segmentsExperimentRelImpl.setCtCollectionId(
			this.<Long>getColumnOriginalValue("ctCollectionId"));
		segmentsExperimentRelImpl.setSegmentsExperimentRelId(
			this.<Long>getColumnOriginalValue("segmentsExperimentRelId"));
		segmentsExperimentRelImpl.setGroupId(
			this.<Long>getColumnOriginalValue("groupId"));
		segmentsExperimentRelImpl.setCompanyId(
			this.<Long>getColumnOriginalValue("companyId"));
		segmentsExperimentRelImpl.setUserId(
			this.<Long>getColumnOriginalValue("userId"));
		segmentsExperimentRelImpl.setUserName(
			this.<String>getColumnOriginalValue("userName"));
		segmentsExperimentRelImpl.setCreateDate(
			this.<Date>getColumnOriginalValue("createDate"));
		segmentsExperimentRelImpl.setModifiedDate(
			this.<Date>getColumnOriginalValue("modifiedDate"));
		segmentsExperimentRelImpl.setSegmentsExperimentId(
			this.<Long>getColumnOriginalValue("segmentsExperimentId"));
		segmentsExperimentRelImpl.setSegmentsExperienceId(
			this.<Long>getColumnOriginalValue("segmentsExperienceId"));
		segmentsExperimentRelImpl.setSplit(
			this.<Double>getColumnOriginalValue("split"));

		return segmentsExperimentRelImpl;
	}

	@Override
	public int compareTo(SegmentsExperimentRel segmentsExperimentRel) {
		long primaryKey = segmentsExperimentRel.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SegmentsExperimentRel)) {
			return false;
		}

		SegmentsExperimentRel segmentsExperimentRel =
			(SegmentsExperimentRel)object;

		long primaryKey = segmentsExperimentRel.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<SegmentsExperimentRel> toCacheModel() {
		SegmentsExperimentRelCacheModel segmentsExperimentRelCacheModel =
			new SegmentsExperimentRelCacheModel();

		segmentsExperimentRelCacheModel.mvccVersion = getMvccVersion();

		segmentsExperimentRelCacheModel.ctCollectionId = getCtCollectionId();

		segmentsExperimentRelCacheModel.segmentsExperimentRelId =
			getSegmentsExperimentRelId();

		segmentsExperimentRelCacheModel.groupId = getGroupId();

		segmentsExperimentRelCacheModel.companyId = getCompanyId();

		segmentsExperimentRelCacheModel.userId = getUserId();

		segmentsExperimentRelCacheModel.userName = getUserName();

		String userName = segmentsExperimentRelCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			segmentsExperimentRelCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			segmentsExperimentRelCacheModel.createDate = createDate.getTime();
		}
		else {
			segmentsExperimentRelCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			segmentsExperimentRelCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			segmentsExperimentRelCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		segmentsExperimentRelCacheModel.segmentsExperimentId =
			getSegmentsExperimentId();

		segmentsExperimentRelCacheModel.segmentsExperienceId =
			getSegmentsExperienceId();

		segmentsExperimentRelCacheModel.split = getSplit();

		return segmentsExperimentRelCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<SegmentsExperimentRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<SegmentsExperimentRel, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SegmentsExperimentRel, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("\"");
			sb.append(attributeName);
			sb.append("\": ");

			Object value = attributeGetterFunction.apply(
				(SegmentsExperimentRel)this);

			if (value == null) {
				sb.append("null");
			}
			else if (value instanceof Blob || value instanceof Date ||
					 value instanceof Map || value instanceof String) {

				sb.append(
					"\"" + StringUtil.replace(value.toString(), "\"", "'") +
						"\"");
			}
			else {
				sb.append(value);
			}

			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<SegmentsExperimentRel, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<SegmentsExperimentRel, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<SegmentsExperimentRel, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((SegmentsExperimentRel)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, SegmentsExperimentRel>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _segmentsExperimentRelId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _segmentsExperimentId;
	private long _segmentsExperienceId;
	private double _split;

	public <T> T getColumnValue(String columnName) {
		Function<SegmentsExperimentRel, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((SegmentsExperimentRel)this);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put("mvccVersion", _mvccVersion);
		_columnOriginalValues.put("ctCollectionId", _ctCollectionId);
		_columnOriginalValues.put(
			"segmentsExperimentRelId", _segmentsExperimentRelId);
		_columnOriginalValues.put("groupId", _groupId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put(
			"segmentsExperimentId", _segmentsExperimentId);
		_columnOriginalValues.put(
			"segmentsExperienceId", _segmentsExperienceId);
		_columnOriginalValues.put("split", _split);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("mvccVersion", 1L);

		columnBitmasks.put("ctCollectionId", 2L);

		columnBitmasks.put("segmentsExperimentRelId", 4L);

		columnBitmasks.put("groupId", 8L);

		columnBitmasks.put("companyId", 16L);

		columnBitmasks.put("userId", 32L);

		columnBitmasks.put("userName", 64L);

		columnBitmasks.put("createDate", 128L);

		columnBitmasks.put("modifiedDate", 256L);

		columnBitmasks.put("segmentsExperimentId", 512L);

		columnBitmasks.put("segmentsExperienceId", 1024L);

		columnBitmasks.put("split", 2048L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private SegmentsExperimentRel _escapedModel;

}