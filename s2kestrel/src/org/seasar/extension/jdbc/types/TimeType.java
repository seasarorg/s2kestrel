package org.seasar.extension.jdbc.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.seasar.extension.jdbc.ValueType;
import org.seasar.framework.util.TimeConversionUtil;

/**
 * @author higa
 *  
 */
public class TimeType implements ValueType {

	/**
	 * @see org.seasar.extension.jdbc.ValueType#getValue(java.sql.ResultSet,
	 *      int)
	 */
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return resultSet.getTime(index);
	}

	/**
	 * @see org.seasar.extension.jdbc.ValueType#getValue(java.sql.ResultSet,
	 *      java.lang.String)
	 */
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {

		return resultSet.getTime(columnName);
	}

	/**
	 * @see org.seasar.extension.jdbc.ValueType#bindValue(java.sql.PreparedStatement,
	 *      int, java.lang.Object)
	 */
	public void bindValue(PreparedStatement ps, int index, Object value)
			throws SQLException {

		if (value == null) {
			ps.setNull(index, Types.TIME);
		} else {
			ps.setTime(index, TimeConversionUtil.toTime(value));
		}
	}

}