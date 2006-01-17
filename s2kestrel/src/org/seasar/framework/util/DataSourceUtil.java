package org.seasar.framework.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.seasar.framework.exception.SQLRuntimeException;

/**
 * @author higa
 *
 */
public final class DataSourceUtil {

	private DataSourceUtil() {
	}

	public static Connection getConnection(DataSource dataSource) {
		try {
			return dataSource.getConnection();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
}
