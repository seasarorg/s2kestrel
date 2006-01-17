package org.seasar.framework.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.kestrel.jdbc.ResultSetWrapper;

/**
 * @author higa
 * @author Satoshi Kimura
 */
public final class PreparedStatementUtil {

	private PreparedStatementUtil() {
	}

	public static ResultSet executeQuery(PreparedStatement ps) {
		try {
			return new ResultSetWrapper(ps.executeQuery(), ps);
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
	
	public static int executeUpdate(PreparedStatement ps) {
		try {
			return ps.executeUpdate();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
	
	public static int[] executeBatch(PreparedStatement ps) {
		try {
			return ps.executeBatch();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
	
	public static void addBatch(PreparedStatement ps) {
		try {
			ps.addBatch();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
}
