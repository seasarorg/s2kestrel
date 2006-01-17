package org.seasar.extension.jdbc;

import org.seasar.framework.beans.PropertyDesc;

/**
 * @author higa
 *
 */
public interface PropertyType {

	public PropertyDesc getPropertyDesc();

	public ValueType getValueType();
	
	public String getPropertyName();
	
	public String getColumnName();
	
	public void setColumnName(String columnName);
	
	public boolean isPrimaryKey();
	
	public void setPrimaryKey(boolean primaryKey);
	
	public boolean isPersistent();
	
	public void setPersistent(boolean persistent);
	
	
}
