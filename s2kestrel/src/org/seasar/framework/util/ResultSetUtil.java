package org.seasar.framework.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.seasar.framework.exception.SQLRuntimeException;

/**
 * @author higa
 *
 */
public final class ResultSetUtil {

	private ResultSetUtil() {
	}

	public static void close(ResultSet resultSet) {
		if (resultSet == null) {
			return;
		}
		try {
			resultSet.close();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
}