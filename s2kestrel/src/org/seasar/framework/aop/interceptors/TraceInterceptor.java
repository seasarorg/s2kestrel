package org.seasar.framework.aop.interceptors;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.log.Logger;

/**
 * @author higa
 *
 */
public class TraceInterceptor extends AbstractInterceptor {

	private static Logger logger_ = Logger.getLogger(TraceInterceptor.class);
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		StringBuffer buf = new StringBuffer(100);
		buf.append(getTargetClass(invocation).getName());
		buf.append("#");
		buf.append(invocation.getMethod().getName());
		buf.append("(");
		Object[] args = invocation.getArguments(); 
		if (args != null && args.length > 0) { 
			for (int i = 0; i < args.length; ++i) {
				buf.append(args[i]);
				buf.append(", ");
			}
			buf.setLength(buf.length() - 2);
		}
		buf.append(")");
		Object ret = null;
		Throwable cause = null;
		logger_.debug("BEGIN " + buf);
		try {
			ret = invocation.proceed();
			buf.append(" : ");
			buf.append(ret);
		} catch (Throwable t) {
			buf.append(" Throwable:");
			buf.append(t);
			cause = t;
		}
		logger_.debug("END " + buf);
		if (cause == null) {
			return ret;
		} else {
			throw cause;
		}
	}
}