package org.seasar.extension.dbcp.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Map;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

import org.seasar.extension.dbcp.ConnectionPool;
import org.seasar.extension.dbcp.ConnectionWrapper;
import org.seasar.framework.exception.SSQLException;
import org.seasar.framework.log.Logger;
import org.seasar.kestrel.dbcp.Assert;

/**
 * 
 * @author higa
 * @author Satoshi Kimura
 */
public final class ConnectionWrapperImpl implements ConnectionWrapper {

	private static Logger logger_ =
		Logger.getLogger(ConnectionWrapperImpl.class);
	private XAConnection xaConnection_;
	private Connection physicalConnection_;
	private XAResource xaResource_;
	private ConnectionPool connectionPool_;
	private boolean closed_ = false;
	private boolean localTx_;

	public ConnectionWrapperImpl(
		XAConnection xaConnection,
		ConnectionPool connectionPool,
		boolean localTx)
		throws SQLException {

		xaConnection_ = xaConnection;
		physicalConnection_ = xaConnection.getConnection();
		xaResource_ =
			new XAResourceWrapperImpl(xaConnection.getXAResource(), this);
		connectionPool_ = connectionPool;
		localTx_ = localTx;
	}

	public Connection getPhysicalConnection() {
		return physicalConnection_;
	}

	public XAResource getXAResource() {
		return xaResource_;
	}

	public XAConnection getXAConnection() {
		return xaConnection_;
	}

	public void init(boolean localTx) {
		closed_ = false;
		localTx_ = localTx;
	}

	public void cleanup() {
		closed_ = true;
	}

	public void closeReally() {
		if (xaConnection_ == null) {
			return;
		}
		closed_ = true;
		try {
			xaConnection_.close();
			logger_.log("DSSR0001", null);
		} catch (SQLException ex) {
			logger_.log(ex);
		} finally {
			xaConnection_ = null;
		}
		try {
			if (!physicalConnection_.isClosed()) {
				physicalConnection_.close();
			}
		} catch (SQLException ex) {
			logger_.log(ex);
		}

	}

	private void assertOpened() throws SQLException {
		if (closed_) {
			throw new SSQLException("ESSR0062", null);
		}
	}

	private void assertLocalTx() throws SQLException {
		if (!localTx_) {
			throw new SSQLException("ESSR0366", null);
		}
	}

	public void release() throws SQLException {
		connectionPool_.release(this);
	}

	public Statement createStatement() throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.createStatement();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.prepareStatement(sql);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.prepareCall(sql);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public String nativeSQL(String sql) throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.nativeSQL(sql);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public boolean isClosed() throws SQLException {
		return closed_;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.getMetaData();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		assertOpened();
		try {
			physicalConnection_.setReadOnly(readOnly);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public boolean isReadOnly() throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.isReadOnly();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public void setCatalog(String catalog) throws SQLException {
		assertOpened();
		try {
			physicalConnection_.setCatalog(catalog);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public String getCatalog() throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.getCatalog();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public void close() throws SQLException {
		if (closed_) {
			return;
		}
		if (localTx_) {
			connectionPool_.checkIn(this);
		}
		logger_.log("DSSR0002", null);
	}

	public void setTransactionIsolation(int level) throws SQLException {
		assertOpened();
		try {
		    Assert.assertDefineTransactionIsolationLevel(level);
			physicalConnection_.setTransactionIsolation(level);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public int getTransactionIsolation() throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.getTransactionIsolation();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public SQLWarning getWarnings() throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.getWarnings();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public void clearWarnings() throws SQLException {
		assertOpened();
		try {
			physicalConnection_.clearWarnings();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public void commit() throws SQLException {
		assertOpened();
		assertLocalTx();
		try {
			physicalConnection_.commit();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public void rollback() throws SQLException {
		assertOpened();
		assertLocalTx();
		try {
			physicalConnection_.rollback();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		assertOpened();
		if (autoCommit) {
			assertLocalTx();
		}
		try {
			physicalConnection_.setAutoCommit(autoCommit);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public boolean getAutoCommit() throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.getAutoCommit();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public Statement createStatement(
		int resultSetType,
		int resultSetConcurrency)
		throws SQLException {

		assertOpened();
		try {
			return physicalConnection_.createStatement(
				resultSetType,
				resultSetConcurrency);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public Map getTypeMap() throws SQLException {
		assertOpened();
		try {
			return physicalConnection_.getTypeMap();
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public void setTypeMap(final Map map) throws SQLException {
		assertOpened();
		try {
			physicalConnection_.setTypeMap(map);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public PreparedStatement prepareStatement(
		String sql,
		int resultSetType,
		int resultSetConcurrency)
		throws SQLException {

		assertOpened();
		try {
			return physicalConnection_.prepareStatement(
				sql,
				resultSetType,
				resultSetConcurrency);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

	public CallableStatement prepareCall(
		String sql,
		int resultSetType,
		int resultSetConcurrency)
		throws SQLException {

		assertOpened();
		try {
			return physicalConnection_.prepareCall(
				sql,
				resultSetType,
				resultSetConcurrency);
		} catch (SQLException ex) {
			release();
			throw ex;
		}
	}

}