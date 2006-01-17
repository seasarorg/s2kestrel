package org.seasar.extension.jdbc.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.seasar.extension.jdbc.ResultSetHandler;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.ValueTypes;

public class ObjectResultSetHandler implements ResultSetHandler {

	public ObjectResultSetHandler() {
	}

	public Object handle(ResultSet rs) throws SQLException {
		if (rs.next()) {
			ResultSetMetaData rsmd = rs.getMetaData();
			ValueType valueType = ValueTypes
					.getValueType(rsmd.getColumnType(1));
			return valueType.getValue(rs, 1);
		} else {
			return null;
		}
	}
}