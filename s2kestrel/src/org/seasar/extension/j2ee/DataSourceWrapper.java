package org.seasar.extension.j2ee;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * @author higa
 *
 */
public class DataSourceWrapper implements DataSource {

	private DataSource dataSource_;

	public DataSourceWrapper() {
	}

	public DataSourceWrapper(DataSource dataSource) {
		setPhysicalDataSource(dataSource);
	}
	
	public DataSource getPhysicalDataSource() {
		return dataSource_;
	}
	
	protected void setPhysicalDataSource(DataSource dataSource) {
		dataSource_ = dataSource;
	}

	/**
	 * @see javax.sql.DataSource#getLoginTimeout()
	 */
	public int getLoginTimeout() throws SQLException {
		return dataSource_.getLoginTimeout();
	}

	/**
	 * @see javax.sql.DataSource#setLoginTimeout(int)
	 */
	public void setLoginTimeout(int seconds) throws SQLException {
		dataSource_.setLoginTimeout(seconds);
	}

	/**
	 * @see javax.sql.DataSource#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource_.getLogWriter();
	}

	/**
	 * @see javax.sql.DataSource#setLogWriter(java.io.PrintWriter)
	 */
	public void setLogWriter(PrintWriter out) throws SQLException {
		dataSource_.setLogWriter(out);
	}

	/**
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		return dataSource_.getConnection();
	}

	/**
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	public Connection getConnection(String username, String password)
		throws SQLException {

		return dataSource_.getConnection(username, password);
	}

}
