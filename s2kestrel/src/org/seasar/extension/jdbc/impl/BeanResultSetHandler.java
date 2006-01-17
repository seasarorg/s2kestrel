package org.seasar.extension.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanResultSetHandler extends AbstractBeanResultSetHandler {

	public BeanResultSetHandler(Class beanClass) {
		super(beanClass);
	}

	/**
	 * @see org.seasar.extension.jdbc.ResultSetHandler#handle(java.sql.ResultSet)
	 */
	public Object handle(ResultSet rs) throws SQLException {
		if (rs.next()) {
			return createRow(rs, createPropertyTypes(rs.getMetaData()));
		} else {
			return null;
		}
	}
}