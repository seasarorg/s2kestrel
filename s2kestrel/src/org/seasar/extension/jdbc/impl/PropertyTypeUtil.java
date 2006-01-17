package org.seasar.extension.jdbc.impl;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.seasar.extension.jdbc.PropertyType;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.ValueTypes;

public final class PropertyTypeUtil {

	private PropertyTypeUtil() {
	}

	public static PropertyType[] createPropertyTypes(ResultSetMetaData rsmd)
			throws SQLException {

		int count = rsmd.getColumnCount();
		PropertyType[] propertyTypes = new PropertyType[count];
		for (int i = 0; i < count; ++i) {
			String propertyName = rsmd.getColumnLabel(i + 1);
			ValueType valueType = ValueTypes.getValueType(rsmd
					.getColumnType(i + 1));
			propertyTypes[i] = new PropertyTypeImpl(propertyName, valueType);
		}
		return propertyTypes;
	}
}