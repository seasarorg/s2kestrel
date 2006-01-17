package org.seasar.framework.aop.impl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.seasar.framework.aop.S2MethodInvocation;

/**
 * @author koichik
 */
public class NestedMethodInvocation implements S2MethodInvocation {
    //instance fields
    private final S2MethodInvocation parent;
    private final MethodInterceptor[] interceptors;
    private int interceptorsIndex;

    public NestedMethodInvocation(final S2MethodInvocation parent,
            final MethodInterceptor[] interceptors) {
        this.parent = parent;
        this.interceptors = interceptors;
    }

    public Object proceed() throws Throwable {
        if (interceptorsIndex < interceptors.length) {
            return interceptors[interceptorsIndex++].invoke(this);
        }
        return parent.proceed();
    }

    public Object getThis() {
        return parent.getThis();
    }

    public Object[] getArguments() {
        return parent.getArguments();
    }

    public Method getMethod() {
        return parent.getMethod();
    }

    public AccessibleObject getStaticPart() {
        return parent.getStaticPart();
    }

    public Class getTargetClass() {
        return parent.getTargetClass();
    }

    public Object getParameter(final String name) {
        return parent.getParameter(name);
    }
}