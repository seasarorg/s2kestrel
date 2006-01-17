package org.seasar.framework.aop.interceptors;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.log.Logger;

/**
 * @author higa
 *
 */
public class TraceThrowsInterceptor extends ThrowsInterceptor {

	private static Logger logger_ = Logger.getLogger(TraceThrowsInterceptor.class);
	
	public void handleThrowable(Throwable t, MethodInvocation invocation) throws Throwable {
		logger_.log(t);
		throw t;
	}

}
