package org.seasar.extension.tx;

import javax.transaction.TransactionManager;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author higa
 *
 */
public class RequiredInterceptor extends AbstractTxInterceptor {
	
	public RequiredInterceptor(TransactionManager transactionManager) {
		super(transactionManager);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		boolean began = false;
		if (!hasTransaction()) {
			begin();
			began = true;
		} 
		Object ret = null;
		try {
			ret = invocation.proceed();
			if (began) {
				commit();
			}
			return ret;
		} catch (Throwable t) {
			if (began) {
				complete(t);
			}
			throw t;
		}
	}
}