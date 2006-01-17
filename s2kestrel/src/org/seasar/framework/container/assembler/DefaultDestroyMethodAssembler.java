package org.seasar.framework.container.assembler;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.IllegalMethodRuntimeException;
import org.seasar.framework.container.MethodDef;

/**
 * @author higa
 *
 */
public class DefaultDestroyMethodAssembler extends AbstractMethodAssembler {

	/**
	 * @param componentDef
	 */
	public DefaultDestroyMethodAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	public void assemble(Object component)
		throws IllegalMethodRuntimeException {

		BeanDesc beanDesc = getBeanDesc(component);
		int size = getComponentDef().getDestroyMethodDefSize();
		for (int i = 0; i < size; ++i) {
			MethodDef methodDef = getComponentDef().getDestroyMethodDef(i);
			invoke(beanDesc, component, methodDef);
		}
	}

}
