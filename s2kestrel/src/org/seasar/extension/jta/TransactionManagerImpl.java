package org.seasar.extension.jta;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAException;

import org.seasar.framework.exception.SIllegalStateException;
import org.seasar.framework.exception.SNotSupportedException;
import org.seasar.framework.exception.SSystemException;

public final class TransactionManagerImpl
	implements TransactionManager, UserTransaction {

	private ThreadLocal threadAttachTx_ = new ThreadLocal();

	public TransactionManagerImpl() {
	}

	public void begin() throws NotSupportedException, SystemException {
		TransactionImpl tx = getCurrent();
		if (tx != null) {
			throw new SNotSupportedException("ESSR0316", null);
		}
		tx = attachTransaction();
		tx.begin();
	}

	public void commit()
		throws
			RollbackException,
			HeuristicMixedException,
			HeuristicRollbackException,
			SecurityException,
			IllegalStateException,
			SystemException {

		TransactionImpl tx = getCurrent();
		if (tx == null) {
			throw new SIllegalStateException("ESSR0311", null);
		}
		tx.commit();
	}

	public Transaction suspend() throws SystemException {
		TransactionImpl tx = getCurrent();
		if (tx == null) {
			throw new SIllegalStateException("ESSR0311", null);
		}
		try {
			tx.suspend();
		} catch (XAException ex) {
			throw new SSystemException("ESSR0363", new Object[] { ex }, ex);
		}

		setCurrent(null);
		return tx;
	}

	public void resume(Transaction resumeTx)
		throws InvalidTransactionException, IllegalStateException, SystemException {

		TransactionImpl tx = getCurrent();
		if (tx != null) {
			throw new SIllegalStateException("ESSR0317", null);
		}
		setCurrent((TransactionImpl) resumeTx);
		try {
			((TransactionImpl) resumeTx).resume();
		} catch (XAException ex) {
			throw new SSystemException("ESSR0364", new Object[] { ex }, ex);
		}
	}

	public void rollback()
		throws IllegalStateException, SecurityException, SystemException {

		TransactionImpl tx = getCurrent();
		if (tx == null) {
			throw new SIllegalStateException("ESSR0311", null);
		}
		tx.rollback();
	}

	public void setRollbackOnly()
		throws IllegalStateException, SystemException {

		Transaction tx = getTransaction();
		if (tx == null) {
			throw new SIllegalStateException("ESSR0311", null);
		}
		tx.setRollbackOnly();
	}

	public void setTransactionTimeout(final int timeout)
		throws SystemException {
	}

	public int getStatus() {
		TransactionImpl tx = getCurrent();
		if (tx != null) {
			return tx.getStatus();
		} else {
			return Status.STATUS_NO_TRANSACTION;
		}
	}

	public Transaction getTransaction() {
		return getCurrent();
	}

	private TransactionImpl getCurrent() {
		TransactionImpl tx = (TransactionImpl) threadAttachTx_.get();
		if (tx != null && tx.getStatus() == Status.STATUS_NO_TRANSACTION) {
			return null;
		}
		return tx;
	}

	private void setCurrent(TransactionImpl current) {
		threadAttachTx_.set(current);
	}

	private TransactionImpl attachTransaction() {
		TransactionImpl tx = (TransactionImpl) threadAttachTx_.get();
		if (tx == null) {
			tx = new TransactionImpl();
			setCurrent(tx);
		}
		return tx;
	}
}