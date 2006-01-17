package org.seasar.framework.aop.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.aop.S2MethodInvocation;
import org.seasar.framework.aop.impl.NestedMethodInvocation;
import org.seasar.framework.util.ArrayUtil;

/**
 * @author higa
 *
 */
public class InterceptorChain extends AbstractInterceptor {
    
	private MethodInterceptor[] interceptors = new MethodInterceptor[0];

    public void add(MethodInterceptor interceptor) {
        interceptors = (MethodInterceptor[]) ArrayUtil.add(interceptors, interceptor);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInvocation nestInvocation = new NestedMethodInvocation((S2MethodInvocation) invocation,
                interceptors);
        return nestInvocation.proceed();
    }
}