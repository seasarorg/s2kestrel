package org.seasar.extension.jdbc.types;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.seasar.extension.jdbc.ValueType;

/**
 * @author higa
 *  
 */
public class BinaryType implements ValueType {

	/**
	 * @see org.seasar.extension.jdbc.ValueType#getValue(java.sql.ResultSet,
	 *      int)
	 */
	public Object getValue(ResultSet resultSet, int index) throws SQLException {
		return resultSet.getBytes(index);
	}

	/**
	 * @see org.seasar.extension.jdbc.ValueType#getValue(java.sql.ResultSet, java.lang.String)
	 */
	public Object getValue(ResultSet resultSet, String columnName)
			throws SQLException {

		return resultSet.getBytes(columnName);
	}

	/**
	 * @see org.seasar.extension.jdbc.ValueType#bindValue(java.sql.PreparedStatement,
	 *      int, java.lang.Object)
	 */
	public void bindValue(PreparedStatement ps, int index, Object value)
			throws SQLException {

		if (value == null) {
			ps.setNull(index, Types.BINARY);
		} else if (value instanceof byte[]) {
			byte[] ba = (byte[]) value;
			InputStream in = new ByteArrayInputStream(ba);
			ps.setBinaryStream(index, in, ba.length);
		} else {
			ps.setObject(index, value);
		}
	}

}