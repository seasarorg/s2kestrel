package org.seasar.framework.aop.impl;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInterceptor;
import org.seasar.framework.aop.Aspect;
import org.seasar.framework.aop.Pointcut;

/**
 * @author higa
 *
 */
public class AspectImpl implements Aspect, Serializable {

	static final long serialVersionUID = 0L;

	private MethodInterceptor methodInterceptor_;
	private Pointcut pointcut_;

	public AspectImpl(MethodInterceptor methodInterceptor) {
		this(methodInterceptor, null);
	}
	
	public AspectImpl(MethodInterceptor methodInterceptor, Pointcut pointcut) {
		methodInterceptor_ = methodInterceptor;
		pointcut_ = pointcut;
	}

	/**
	 * @see org.seasar.framework.aop.Aspect#getMethodInterceptor()
	 */
	public MethodInterceptor getMethodInterceptor() {
		return methodInterceptor_;
	}

	/**
	 * @see org.seasar.framework.aop.Aspect#getPointcut()
	 */
	public Pointcut getPointcut() {
		return pointcut_;
	}

	/**
	 * @see org.seasar.framework.aop.Aspect#setPointcut(org.seasar.framework.aop.Pointcut)
	 */
	public void setPointcut(Pointcut pointcut) {
		pointcut_ = pointcut;
	}

}
