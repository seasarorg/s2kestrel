package org.seasar.extension.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author higa
 *  
 */
public interface StatementFactory {

	public PreparedStatement createPreparedStatement(Connection con, String sql);
	
	public CallableStatement createCallableStatement(Connection con, String sql);
}