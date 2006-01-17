package org.seasar.extension.dbcp.impl;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.seasar.extension.dbcp.ConnectionPool;
import org.seasar.framework.log.Logger;

public final class DataSourceImpl implements DataSource, Serializable {

	static final long serialVersionUID = 1L;
	private static final Logger logger_ = Logger.getLogger(DataSourceImpl.class);
	private ConnectionPool connectionPool_;

	public DataSourceImpl(ConnectionPool connectionPool) {
		connectionPool_ = connectionPool;
	}

	public ConnectionPool getConnectionPool() {
		return connectionPool_;
	}

	public Connection getConnection() throws SQLException {
		Connection con = connectionPool_.checkOut();
		logger_.log("DSSR0007", null);
		return con;
	}

	public Connection getConnection(String user, String password)
		throws SQLException {

		return getConnection();
	}

	public void setLoginTimeout(int loginTimeout) throws SQLException {
	}

	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	public void setLogWriter(PrintWriter logWriter) throws SQLException {
	}

	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}
}