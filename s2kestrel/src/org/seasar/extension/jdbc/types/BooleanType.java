package org.seasar.extension.jdbc.types;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.seasar.extension.jdbc.ValueType;
import org.seasar.framework.util.BooleanConversionUtil;

/**
 * @author higa
 * @author Satoshi Kimura
 */
public class BooleanType implements ValueType {

	/**
	 * @see org.seasar.extension.jdbc.ValueType#getValue(java.sql.ResultSet,
	 *      int)
	 */
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return BooleanConversionUtil.toBoolean(resultSet.getObject(index));
	}

	/**
	 * @see org.seasar.extension.jdbc.ValueType#getValue(java.sql.ResultSet,
	 *      java.lang.String)
	 */
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {

		return BooleanConversionUtil.toBoolean(resultSet.getObject(columnName));
	}

	/**
	 * @see org.seasar.extension.jdbc.ValueType#bindValue(java.sql.PreparedStatement,
	 *      int, java.lang.Object)
	 */
	public void bindValue(PreparedStatement ps, int index, Object value)
			throws SQLException {

		if (value == null) {
			//ps.setNull(index, Types.BOOLEAN);
		    ps.setNull(index, Types.OTHER);
		} else {
			ps.setBoolean(index, BooleanConversionUtil.toPrimitiveBoolean(value));
		}
	}

}