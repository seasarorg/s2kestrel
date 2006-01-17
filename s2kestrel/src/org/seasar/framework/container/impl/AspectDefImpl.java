package org.seasar.framework.container.impl;

import org.aopalliance.intercept.MethodInterceptor;
import org.seasar.framework.aop.Aspect;
import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.aop.impl.AspectImpl;
import org.seasar.framework.container.AspectDef;

/**
 * @author higa
 *
 */
public class AspectDefImpl extends ArgDefImpl implements AspectDef {

	private Pointcut pointcut_;

	public AspectDefImpl() {
	}

	public AspectDefImpl(Pointcut pointcut) {
		pointcut_ = pointcut;
	}
	
	public AspectDefImpl(MethodInterceptor interceptor) {
		setValue(interceptor);
	}

	public AspectDefImpl(MethodInterceptor interceptor, Pointcut pointcut) {
		setValue(interceptor);
		pointcut_ = pointcut;
	}

	/**
	 * @see org.seasar.framework.container.AspectDef#getAspect()
	 */
	public Aspect getAspect() {
		MethodInterceptor interceptor = (MethodInterceptor) getValue();
		return new AspectImpl(interceptor, pointcut_);
	}
}
