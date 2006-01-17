package org.seasar.extension.dbcp.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

import org.seasar.extension.dbcp.DBXAResource;
import org.seasar.extension.jta.xa.DefaultXAResource;
import org.seasar.framework.exception.SXAException;

public class DBXAResourceImpl
	extends DefaultXAResource
	implements DBXAResource {

	private Connection connection_;

	public DBXAResourceImpl(Connection connection) {
		connection_ = connection;
	}
	
	public Connection getConnection() {
		return connection_;
	}

	protected void doBegin(Xid xid) throws XAException {
		try {
			if (connection_.getAutoCommit()) {
				connection_.setAutoCommit(false);
			}
		} catch (SQLException ex) {
			throw new SXAException(ex);
		}
	}

	protected void doCommit(Xid xid, boolean onePhase) throws XAException {
		try {
			connection_.commit();
			connection_.setAutoCommit(true);
		} catch (SQLException ex) {
			throw new SXAException(ex);
		}
	}

	protected int doPrepare(Xid xid) throws XAException {
		try {
			if (connection_.isClosed()) {
				return XA_RDONLY;
			} else {
				return XA_OK;
			}
		} catch (SQLException ex) {
			throw new SXAException(ex);
		}
	}

	protected void doRollback(Xid xid) throws XAException {
		try {
			connection_.rollback();
			connection_.setAutoCommit(true);
		} catch (SQLException ex) {
			throw new SXAException(ex);
		}
	}
}