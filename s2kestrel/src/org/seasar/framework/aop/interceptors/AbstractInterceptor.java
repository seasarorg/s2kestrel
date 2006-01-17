package org.seasar.framework.aop.interceptors;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.Aspect;
import org.seasar.framework.aop.S2MethodInvocation;
import org.seasar.framework.aop.impl.AspectImpl;
import org.seasar.framework.aop.impl.PointcutImpl;
import org.seasar.framework.aop.proxy.AopProxy;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ContainerConstants;

/**
 * @author higa
 *  
 */
public abstract class AbstractInterceptor implements MethodInterceptor,
		Serializable {

	static final long serialVersionUID = 0L;

	public Object createProxy(Class proxyClass) {
		Aspect aspect = new AspectImpl(this, new PointcutImpl(
				new String[] { ".*" }));
		return new AopProxy(proxyClass, new Aspect[] { aspect }).create();
	}

	protected Class getTargetClass(MethodInvocation invocation) {
		if (invocation instanceof S2MethodInvocation) {
			return ((S2MethodInvocation) invocation).getTargetClass();
		}
		Class thisClass = invocation.getThis().getClass();
		Class superClass = thisClass.getSuperclass();
		if (superClass == Object.class) {
			return thisClass.getInterfaces()[0];
		} else {
			return superClass;
		}
	}

	protected ComponentDef getComponentDef(MethodInvocation invocation) {
		if (invocation instanceof S2MethodInvocation) {
		    S2MethodInvocation impl = (S2MethodInvocation) invocation;
			return (ComponentDef) impl
					.getParameter(ContainerConstants.COMPONENT_DEF_NAME);
		}
		return null;
	}
}