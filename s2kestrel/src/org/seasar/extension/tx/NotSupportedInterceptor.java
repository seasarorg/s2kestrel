package org.seasar.extension.tx;

import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author taichi S.
 */
public class NotSupportedInterceptor extends AbstractTxInterceptor {
	
	public NotSupportedInterceptor(TransactionManager transactionManager) {
		super(transactionManager);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Transaction tx = null;
		if (hasTransaction()) {
			tx = suspend();
		}
		try {
		    return invocation.proceed();
		} finally {
			if (tx != null) {
				resume(tx);
			}
		}
	}
}