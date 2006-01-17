package org.seasar.extension.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.extension.jdbc.UpdateHandler;
import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ConnectionUtil;
import org.seasar.framework.util.PreparedStatementUtil;
import org.seasar.framework.util.StatementUtil;

/**
 * @author higa
 *
 */
public class BasicUpdateHandler extends BasicHandler implements UpdateHandler {

	private static Logger logger_ = Logger.getLogger(BasicUpdateHandler.class);
	
	public BasicUpdateHandler() {
	}

	public BasicUpdateHandler(DataSource dataSource, String sql) {
		super(dataSource, sql);
	}
	
	public BasicUpdateHandler(DataSource dataSource, String sql,
			StatementFactory statementFactory) {
		
		super(dataSource, sql, statementFactory);
	}

	/**
	 * @see org.seasar.extension.jdbc.SelectHandler#execute(java.lang.Object[])
	 */
	public int execute(Object[] args) throws SQLRuntimeException {
		return execute(args, getArgTypes(args));
	}
	
	public int execute(Object[] args, Class[] argTypes) throws SQLRuntimeException {
		if (logger_.isDebugEnabled()) {
			logger_.debug(getCompleteSql(args));
		}
		Connection connection = getConnection();
		try {
			return execute(connection, args, argTypes);
		} finally {
			ConnectionUtil.close(connection);
		}
	}

	protected int execute(Connection connection, Object[] args, Class[] argTypes) {
		PreparedStatement ps = prepareStatement(connection);
		try {
			bindArgs(ps, args, argTypes);
			return PreparedStatementUtil.executeUpdate(ps);
		} finally {
			StatementUtil.close(ps);
		}
	}
}