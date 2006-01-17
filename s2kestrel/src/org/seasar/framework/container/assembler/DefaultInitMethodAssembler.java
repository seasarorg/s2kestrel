package org.seasar.framework.container.assembler;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.IllegalMethodRuntimeException;
import org.seasar.framework.container.MethodDef;

/**
 * @author higa
 *
 */
public class DefaultInitMethodAssembler extends AbstractMethodAssembler {

	/**
	 * @param componentDef
	 */
	public DefaultInitMethodAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	public void assemble(Object component)
		throws IllegalMethodRuntimeException {

		BeanDesc beanDesc = getBeanDesc(component);
		int size = getComponentDef().getInitMethodDefSize();
		for (int i = 0; i < size; ++i) {
			MethodDef methodDef = getComponentDef().getInitMethodDef(i);
			invoke(beanDesc, component, methodDef);
		}
	}

}
