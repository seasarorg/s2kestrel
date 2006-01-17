package org.seasar.framework.container.util;

import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.aop.Aspect;
import org.seasar.framework.aop.proxy.AopProxy;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ContainerConstants;

/**
 * @author koichik
 */
public class AopProxyUtil {
    private AopProxyUtil() {
    }

    public static Class getConcreteClass(final ComponentDef componentDef) {
        if (componentDef.getAspectDefSize() == 0) {
            return componentDef.getComponentClass();
        }

        final Map parameters = new HashMap();
        parameters.put(ContainerConstants.COMPONENT_DEF_NAME, componentDef);
        AopProxy proxy = new AopProxy(componentDef.getComponentClass(), getAspects(componentDef),
                parameters);
        return proxy.getEnhancedClass();
    }

    private static Aspect[] getAspects(final ComponentDef componentDef) {
        final int size = componentDef.getAspectDefSize();
        Aspect[] aspects = new Aspect[size];
        for (int i = 0; i < size; ++i) {
            aspects[i] = componentDef.getAspectDef(i).getAspect();
        }
        return aspects;
    }
}