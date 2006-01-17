package org.seasar.extension.jta.xa;

import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public class DefaultXAResource extends AbstractXAResource {

	public DefaultXAResource() {
	}

	protected void doSuccess(Xid xid) throws XAException {
	}

	protected void doFail(Xid xid) throws XAException {
	}

	protected void doResume(Xid xid) throws XAException {
	}

	protected void doBegin(Xid xid) throws XAException {
	}

	protected int doPrepare(Xid xid) throws XAException {
		return XA_OK;
	}

	protected void doRollback(Xid xid) throws XAException {
	}

	protected void doSuspend(Xid xid) throws XAException {
	}

	protected void doForget(Xid xid) throws XAException {
	}

	protected void doCommit(Xid xid, boolean onePhase) throws XAException {
	}
}