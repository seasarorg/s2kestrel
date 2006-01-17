package org.seasar.extension.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.seasar.extension.jdbc.PropertyType;

public class MapResultSetHandler extends AbstractMapResultSetHandler {

	public MapResultSetHandler() {
	}

	/**
	 * @see org.seasar.extension.jdbc.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	public Object handle(ResultSet resultSet) throws SQLException {
		if (resultSet.next()) {
			PropertyType[] propertyTypes = PropertyTypeUtil
					.createPropertyTypes(resultSet.getMetaData());
			return createRow(resultSet, propertyTypes);
		} else {
			return null;
		}
	}
}