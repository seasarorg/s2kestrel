package org.seasar.extension.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.framework.util.ConnectionUtil;

/**
 * @author higa
 *
 */
public class BooleanToIntStatementFactory implements StatementFactory {
	
	public static final StatementFactory INSTANCE = new BooleanToIntStatementFactory();
	
	public PreparedStatement createPreparedStatement(Connection con, String sql) {
		return new BooleanToIntPreparedStatement(ConnectionUtil.prepareStatement(con, sql));
	}
	
	public CallableStatement createCallableStatement(Connection con, String sql) {
		return new BooleanToIntCallableStatement(ConnectionUtil.prepareCall(con, sql));
	}
}
