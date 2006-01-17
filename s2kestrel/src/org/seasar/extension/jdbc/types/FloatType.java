package org.seasar.extension.jdbc.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.seasar.extension.jdbc.ValueType;
import org.seasar.framework.util.FloatConversionUtil;

/**
 * @author higa
 *  
 */
public class FloatType implements ValueType {

	/**
	 * @see org.seasar.extension.jdbc.ValueType#getValue(java.sql.ResultSet,
	 *      int)
	 */
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return FloatConversionUtil.toFloat(resultSet.getObject(index));
	}

	/**
	 * @see org.seasar.extension.jdbc.ValueType#getValue(java.sql.ResultSet,
	 *      java.lang.String)
	 */
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {

		return FloatConversionUtil.toFloat(resultSet.getObject(columnName));
	}

	/**
	 * @see org.seasar.extension.jdbc.ValueType#bindValue(java.sql.PreparedStatement,
	 *      int, java.lang.Object)
	 */
	public void bindValue(PreparedStatement ps, int index, Object value)
			throws SQLException {

		if (value == null) {
			ps.setNull(index, Types.FLOAT);
		} else {
			ps.setFloat(index, FloatConversionUtil.toPrimitiveFloat(value));
		}
	}

}