package org.seasar.framework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author higa
 *
 * プログラムに適用する関心を定義します。<br />
 * AroundAdviceとPointcutで構成されます。
 */
public interface Aspect {
	
	public MethodInterceptor getMethodInterceptor();
	
	public Pointcut getPointcut();
	
	public void setPointcut(Pointcut pointcut);

}
