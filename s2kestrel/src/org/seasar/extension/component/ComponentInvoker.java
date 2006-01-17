package org.seasar.extension.component;

public interface ComponentInvoker {

	public Object invoke(String componentName, String methodName, Object[] args)
			throws Throwable;
}