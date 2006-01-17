package org.seasar.extension.tx;

import javax.transaction.TransactionManager;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.exception.SIllegalStateException;

/**
 * @author higa
 *
 */
public class MandatoryInterceptor extends AbstractTxInterceptor {
	
	public MandatoryInterceptor(TransactionManager transactionManager) {
		super(transactionManager);
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (!hasTransaction()) {
			throw new SIllegalStateException("ESSR0311", null);
		} 
		return invocation.proceed();
	}
}