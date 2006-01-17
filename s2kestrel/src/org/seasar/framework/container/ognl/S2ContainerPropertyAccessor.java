package org.seasar.framework.container.ognl;

import java.util.Map;

import ognl.ObjectPropertyAccessor;
import ognl.OgnlException;

import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 */
public class S2ContainerPropertyAccessor extends ObjectPropertyAccessor {

	public Object getProperty(Map cx, Object target, Object name)
		throws OgnlException {

		S2Container container = (S2Container) target;
		String componentName = name.toString();
		return container.getComponent(componentName);
	}

}
