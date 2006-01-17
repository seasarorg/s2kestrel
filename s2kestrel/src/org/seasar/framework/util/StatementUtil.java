package org.seasar.framework.util;

import java.sql.SQLException;
import java.sql.Statement;

import org.seasar.framework.exception.SQLRuntimeException;

/**
 * @author higa
 *
 */
public final class StatementUtil {

	private StatementUtil() {
	}

	public static void setFetchSize(Statement statement, int fetchSize) {
		try {
			statement.setFetchSize(fetchSize);
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
	
	public static void setMaxRows(Statement statement, int maxRows) {
		try {
			statement.setMaxRows(maxRows);
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
	
	public static void close(Statement statement) {
		if (statement == null) {
			return;
		}
		try {
			statement.close();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
}
