package org.seasar.framework.aop.interceptors;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author higa
 *
 */
public class SyncInterceptor extends AbstractInterceptor {

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		synchronized (invocation.getThis()) {
			return invocation.proceed();
		}
	}

}
