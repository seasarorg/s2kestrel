package org.seasar.extension.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.seasar.extension.jdbc.PropertyType;

public class BeanListResultSetHandler extends AbstractBeanResultSetHandler {

	public BeanListResultSetHandler(Class beanClass) {
		super(beanClass);
	}

	/**
	 * @see org.seasar.extension.jdbc.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	public Object handle(ResultSet rs) throws SQLException {
		PropertyType[] propertyTypes = createPropertyTypes(rs.getMetaData());
		List list = new ArrayList();
		while (rs.next()) {
			Object row = createRow(rs, propertyTypes);
			list.add(row);
		}
		return list;
	}
}