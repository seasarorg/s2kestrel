package org.seasar.extension.tx;

import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author higa
 *
 */
public class RequiresNewInterceptor extends AbstractTxInterceptor {
	
	public RequiresNewInterceptor(TransactionManager transactionManager) {
		super(transactionManager);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Transaction tx = null;
		if (hasTransaction()) {
			tx = suspend();
		}
		Object ret = null;
		try {
			begin();
			try {
				ret = invocation.proceed();
				commit();
			} catch (Throwable t) {
				complete(t);
				throw t;
			}
		} finally {
			if (tx != null) {
				resume(tx);
			}
		}
		return ret;
		
	}
}