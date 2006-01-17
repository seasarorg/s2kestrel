package org.seasar.extension.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author higa
 *  
 */
public interface ValueType {

	public Object getValue(ResultSet resultSet, int index) throws SQLException;

	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException;

	public void bindValue(PreparedStatement ps, int index, Object value)
			throws SQLException;
}