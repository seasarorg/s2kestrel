package org.seasar.framework.aop.interceptors;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author higa
 *  
 */
public class MockInterceptor extends AbstractInterceptor {

	private Map returnValueMap_ = new HashMap();

	private Map throwableMap_ = new HashMap();

	private Map invokedMap_ = new HashMap();

	private Map argsMap_ = new HashMap();

	public MockInterceptor() {
	}
	
	public MockInterceptor(Object value) {
		setReturnValue(value);
	}

	public void setReturnValue(Object returnValue) {
		setReturnValue(null, returnValue);
	}
	
	public void setReturnValue(String methodName, Object returnValue) {
		returnValueMap_.put(methodName, returnValue);
	}

	public void setThrowable(Throwable throwable) {
		setThrowable(null, throwable);
	}
	
	public void setThrowable(String methodName, Throwable throwable) {
		throwableMap_.put(methodName, throwable);
	}

	public boolean isInvoked(String methodName) {
		return invokedMap_.containsKey(methodName);
	}

	public Object[] getArgs(String methodName) {
		return (Object[]) argsMap_.get(methodName);
	}

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String methodName = invocation.getMethod().getName(); 
		invokedMap_.put(invocation.getMethod().getName(), Boolean.TRUE);
		argsMap_.put(methodName, invocation
				.getArguments());
		if (throwableMap_.containsKey(methodName)) {
			throw (Throwable) throwableMap_.get(methodName);
		} else if (throwableMap_.containsKey(null)) {
			throw (Throwable) throwableMap_.get(null);
		} else if (returnValueMap_.containsKey(methodName)) {
			return returnValueMap_.get(methodName);
		} else {
			return returnValueMap_.get(null);
		}
	}
}