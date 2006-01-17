package org.seasar.framework.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.seasar.framework.exception.SQLRuntimeException;

/**
 * @author higa
 *
 */
public final class ConnectionUtil {

	private ConnectionUtil() {
	}

	public static void close(Connection connection) {
		if (connection == null) {
			return;
		}
		try {
			connection.close();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
	
	public static PreparedStatement prepareStatement(
		Connection connection,
		String sql) {

		try {
			return connection.prepareStatement(sql);
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
	
	public static CallableStatement prepareCall(
			Connection connection,
			String sql) {

			try {
				return connection.prepareCall(sql);
			} catch (SQLException ex) {
				throw new SQLRuntimeException(ex);
			}
		}
	
	public static DatabaseMetaData getMetaData(Connection connection) {
		try {
			return connection.getMetaData();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
}
