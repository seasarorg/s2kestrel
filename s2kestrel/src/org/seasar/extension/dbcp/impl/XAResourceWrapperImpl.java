package org.seasar.extension.dbcp.impl;

import java.sql.SQLException;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.seasar.extension.dbcp.ConnectionWrapper;
import org.seasar.framework.exception.SXAException;

/**
 * @author higa
 *
 */
public class XAResourceWrapperImpl implements XAResource {

	private XAResource physicalXAResource_;
	private ConnectionWrapper connectionWrapper_;

	public XAResourceWrapperImpl(XAResource physicalXAResource,
		ConnectionWrapper connectionWrapper) {

		physicalXAResource_ = physicalXAResource;
		connectionWrapper_ = connectionWrapper;
	}
	
	public XAResource getPhysicalXAResource() {
		return physicalXAResource_;
	}
	
	public ConnectionWrapper getConnectionWrapper() {
		return connectionWrapper_;
	}
	
	private void release() throws XAException {
		try {
			getConnectionWrapper().release();
		} catch (SQLException ex) {
			throw new SXAException(ex);
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#getTransactionTimeout()
	 */
	public int getTransactionTimeout() throws XAException {
		try {
			return getPhysicalXAResource().getTransactionTimeout();
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#setTransactionTimeout(int)
	 */
	public boolean setTransactionTimeout(int arg0) throws XAException {
		try {
			return getPhysicalXAResource().setTransactionTimeout(arg0);
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#isSameRM(javax.transaction.xa.XAResource)
	 */
	public boolean isSameRM(XAResource arg0) throws XAException {
		try {
			return getPhysicalXAResource().isSameRM(arg0);
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#recover(int)
	 */
	public Xid[] recover(int arg0) throws XAException {
		try {
			return getPhysicalXAResource().recover(arg0);
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#prepare(javax.transaction.xa.Xid)
	 */
	public int prepare(Xid arg0) throws XAException {
		try {
			return getPhysicalXAResource().prepare(arg0);
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#forget(javax.transaction.xa.Xid)
	 */
	public void forget(Xid arg0) throws XAException {
		try {
			getPhysicalXAResource().forget(arg0);
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#rollback(javax.transaction.xa.Xid)
	 */
	public void rollback(Xid arg0) throws XAException {
		try {
			getPhysicalXAResource().rollback(arg0);
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#end(javax.transaction.xa.Xid, int)
	 */
	public void end(Xid arg0, int arg1) throws XAException {
		try {
			getPhysicalXAResource().end(arg0, arg1);
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#start(javax.transaction.xa.Xid, int)
	 */
	public void start(Xid arg0, int arg1) throws XAException {
		try {
			getPhysicalXAResource().start(arg0, arg1);
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

	/**
	 * @see javax.transaction.xa.XAResource#commit(javax.transaction.xa.Xid, boolean)
	 */
	public void commit(Xid arg0, boolean arg1) throws XAException {
		try {
			getPhysicalXAResource().commit(arg0, arg1);
		} catch (XAException ex) {
			release();
			throw ex;
		}
	}

}
