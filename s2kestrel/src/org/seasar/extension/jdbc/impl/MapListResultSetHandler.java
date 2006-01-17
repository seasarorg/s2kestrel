package org.seasar.extension.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.seasar.extension.jdbc.PropertyType;

public class MapListResultSetHandler extends AbstractMapResultSetHandler {

	public MapListResultSetHandler() {
	}

	/**
	 * @see org.seasar.extension.jdbc.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	public Object handle(ResultSet resultSet) throws SQLException {
		PropertyType[] propertyTypes = PropertyTypeUtil
				.createPropertyTypes(resultSet.getMetaData());
		List list = new ArrayList();
		while (resultSet.next()) {
			list.add(createRow(resultSet, propertyTypes));
		}
		return list;
	}
}