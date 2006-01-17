package org.seasar.framework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author higa
 *
 * �v���O�����ɓK�p����֐S���`���܂��B<br />
 * AroundAdvice��Pointcut�ō\������܂��B
 */
public interface Aspect {
	
	public MethodInterceptor getMethodInterceptor();
	
	public Pointcut getPointcut();
	
	public void setPointcut(Pointcut pointcut);

}
