package org.seasar.extension.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.seasar.extension.jdbc.PropertyType;
import org.seasar.extension.jdbc.ResultSetHandler;
import org.seasar.framework.util.CaseInsensitiveMap;

public abstract class AbstractMapResultSetHandler implements ResultSetHandler {

	public AbstractMapResultSetHandler() {
	}

	protected Map createRow(ResultSet rs, PropertyType[] propertyTypes)
			throws SQLException {

		Map row = new CaseInsensitiveMap();
		for (int i = 0; i < propertyTypes.length; ++i) {
			Object value = propertyTypes[i].getValueType().getValue(rs, i + 1);
			row.put(propertyTypes[i].getPropertyName(), value);
		}
		return row;
	}
}