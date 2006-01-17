package org.seasar.framework.util;

import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

import org.seasar.framework.exception.RollbackRuntimeException;
import org.seasar.framework.exception.SystemRuntimeException;

/**
 * @author higa
 *
 */
public final class TransactionUtil {

	private TransactionUtil() {
	}

	public static void enlistResource(Transaction tx, XAResource xaResource) {
		try {
			tx.enlistResource(xaResource);
		} catch (SystemException e) {
			throw new SystemRuntimeException(e);
		} catch (RollbackException e) {
			throw new RollbackRuntimeException(e);
		}
	}

	public static void registerSynchronization(
		Transaction tx,
		Synchronization sync) {

		try {
			tx.registerSynchronization(sync);
		} catch (SystemException e) {
			throw new SystemRuntimeException(e);
		} catch (RollbackException e) {
			throw new RollbackRuntimeException(e);
		}
	}
}
