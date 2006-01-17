package org.seasar.framework.aop;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author koichik
 */
public interface S2MethodInvocation extends MethodInvocation {
    Class getTargetClass();

    Object getParameter(String name);
}