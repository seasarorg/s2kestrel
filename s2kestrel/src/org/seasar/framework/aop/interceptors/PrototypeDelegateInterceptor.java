package org.seasar.framework.aop.interceptors;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.util.MethodUtil;

/**
 * @author koichik
 */
public class PrototypeDelegateInterceptor extends AbstractInterceptor {
    private S2Container container;
    private String targetName;
    private BeanDesc beanDesc;
    private Map methodNameMap = new HashMap();

    public PrototypeDelegateInterceptor(final S2Container container) {
        this.container = container;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(final String targetName) {
        this.targetName = targetName;
    }

    public void addMethodNameMap(final String methodName, final String targetMethodName) {
        methodNameMap.put(methodName, targetMethodName);
    }

    /**
     * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (targetName == null) {
            throw new EmptyRuntimeException("targetName");
        }

        final Method method = invocation.getMethod();
        if (!MethodUtil.isAbstract(method)) {
            return invocation.proceed();
        }

        String methodName = method.getName();
        if (methodNameMap.containsKey(methodName)) {
            methodName = (String) methodNameMap.get(methodName);
        }

        final Object target = container.getComponent(targetName);
        if (beanDesc == null) {
            beanDesc = BeanDescFactory.getBeanDesc(target.getClass());
        }

        if (!beanDesc.hasMethod(methodName)) {
            throw new MethodNotFoundRuntimeException(getTargetClass(invocation), methodName,
                    invocation.getArguments());
        }

        return beanDesc.invoke(target, methodName, invocation.getArguments());
    }
}