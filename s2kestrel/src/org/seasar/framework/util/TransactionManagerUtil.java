package org.seasar.framework.util;

import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.seasar.framework.exception.SystemRuntimeException;

/**
 * @author higa
 *
 */
public final class TransactionManagerUtil {

	private TransactionManagerUtil() {
	}

	public static Transaction getTransaction(TransactionManager tm) {
		try {
			return tm.getTransaction();
		} catch (SystemException e) {
			throw new SystemRuntimeException(e);
		}
	}
}
