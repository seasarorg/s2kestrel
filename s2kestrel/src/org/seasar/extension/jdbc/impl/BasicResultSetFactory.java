package org.seasar.extension.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.seasar.extension.jdbc.ResultSetFactory;
import org.seasar.framework.util.PreparedStatementUtil;

/**
 * @author higa
 *
 */
public class BasicResultSetFactory implements ResultSetFactory {
	
	public static final ResultSetFactory INSTANCE = new BasicResultSetFactory();

	/**
	 * @see org.seasar.extension.jdbc.ResultSetFactory#createResultSet(java.sql.PreparedStatement)
	 */
	public ResultSet createResultSet(PreparedStatement ps) {
		return PreparedStatementUtil.executeQuery(ps);
	}

}
