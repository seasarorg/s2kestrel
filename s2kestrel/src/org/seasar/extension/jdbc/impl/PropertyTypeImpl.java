package org.seasar.extension.jdbc.impl;

import org.seasar.extension.jdbc.PropertyType;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.ValueTypes;
import org.seasar.framework.beans.PropertyDesc;

public class PropertyTypeImpl implements PropertyType {

	private PropertyDesc propertyDesc_;
	private String propertyName_;
	private String columnName_;
	private ValueType valueType_;
	private boolean primaryKey_ = false;
	private boolean persistent_ = true;

	public PropertyTypeImpl(PropertyDesc propertyDesc) {
		this(propertyDesc, ValueTypes.OBJECT, propertyDesc.getPropertyName());
	}
	
	public PropertyTypeImpl(PropertyDesc propertyDesc, ValueType valueType) {
		this(propertyDesc, valueType, propertyDesc.getPropertyName());
	}

	public PropertyTypeImpl(PropertyDesc propertyDesc, ValueType valueType,
			String columnName) {

		propertyDesc_ = propertyDesc;
		propertyName_ = propertyDesc.getPropertyName();
		valueType_ = valueType;
		columnName_ = columnName;	
	}
	
	public PropertyTypeImpl(String propertyName, ValueType valueType) {
		propertyName_ = propertyName;
		valueType_ = valueType;
		columnName_ = propertyName;
	}

	public PropertyDesc getPropertyDesc() {
		return propertyDesc_;
	}

	public String getPropertyName() {
		return propertyName_;
	}

	public String getColumnName() {
		return columnName_;
	}
	
	public void setColumnName(String columnName) {
		columnName_ = columnName;
	}

	public ValueType getValueType() {
		return valueType_;
	}

	public boolean isPrimaryKey() {
		return primaryKey_;
	}

	public void setPrimaryKey(boolean primaryKey) {
		primaryKey_ = primaryKey;
	}

	public boolean isPersistent() {
		return persistent_;
	}

	public void setPersistent(boolean persistent) {
		persistent_ = persistent;
	}
}