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
public class BasicStatementFactory implements StatementFactory {
	
	public static final StatementFactory INSTANCE = new BasicStatementFactory();
	
	public PreparedStatement createPreparedStatement(Connection con, String sql) {
		return ConnectionUtil.prepareStatement(con, sql);
	}
	
	public CallableStatement createCallableStatement(Connection con, String sql) {
		return ConnectionUtil.prepareCall(con, sql);
	}
}
