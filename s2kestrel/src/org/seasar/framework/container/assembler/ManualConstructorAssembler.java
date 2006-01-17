package org.seasar.framework.container.assembler;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.IllegalConstructorRuntimeException;

/**
 * @author higa
 *
 */
public class ManualConstructorAssembler
	extends AbstractConstructorAssembler {

	/**
	 * @param componentDef
	 */
	public ManualConstructorAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	public Object assemble()
		throws IllegalConstructorRuntimeException {

		Object[] args = new Object[getComponentDef().getArgDefSize()];
		for (int i = 0; i < args.length; ++i) {
			try {
				args[i] = getComponentDef().getArgDef(i).getValue();
			} catch (ComponentNotFoundRuntimeException cause) {
				throw new IllegalConstructorRuntimeException(
					getComponentDef().getComponentClass(),
					cause);
			}

		}
		BeanDesc beanDesc =
			BeanDescFactory.getBeanDesc(getComponentDef().getConcreteClass());
		return beanDesc.newInstance(args);
	}
}
