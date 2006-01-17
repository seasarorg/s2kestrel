package org.seasar.extension.component.impl;

import org.seasar.extension.component.ComponentInvoker;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.InvocationTargetRuntimeException;

public class ComponentInvokerImpl implements ComponentInvoker {

	private S2Container container;

	public Object invoke(String componentName, String methodName, Object[] args)
			throws Throwable {

		Object component = container.getComponent(componentName);
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(component.getClass());
		try {
			return beanDesc.invoke(component, methodName, args);
		} catch (InvocationTargetRuntimeException e) {
			throw e.getCause();
		}
	}

	public void setContainer(S2Container container) {
		this.container = container;
	}
}