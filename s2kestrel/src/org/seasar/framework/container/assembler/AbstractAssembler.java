package org.seasar.framework.container.assembler;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.log.Logger;

/**
 * @author higa
 *
 */
public abstract class AbstractAssembler {
	
	private static Logger logger_ = Logger.getLogger(AbstractAssembler.class);

	private ComponentDef componentDef_;

	public AbstractAssembler(ComponentDef componentDef) {
		componentDef_ = componentDef;
	}

	protected final ComponentDef getComponentDef() {
		return componentDef_;
	}

	protected final BeanDesc getBeanDesc() {
		return BeanDescFactory.getBeanDesc(
			getComponentDef().getComponentClass());
	}
	
	protected final BeanDesc getBeanDesc(Object component) {
		return BeanDescFactory.getBeanDesc(
			getComponentClass(component));
	}
	
	protected final Class getComponentClass(Object component) {
		Class clazz = componentDef_.getComponentClass();
		if (clazz != null) {
			return clazz;
		} else {
			return component.getClass();
		}
	}
	
	protected Object[] getArgs(Class[] argTypes) {
		Object[] args = new Object[argTypes.length];
		for (int i = 0; i < argTypes.length; ++i) {
			try {
				args[i] =
					getComponentDef().getContainer().getComponent(argTypes[i]);
			} catch (ComponentNotFoundRuntimeException ex) {
				logger_.log("WSSR0007",
						new Object[] {
							getComponentDef().getComponentClass().getName(),
							ex.getComponentKey()});
				args[i] = null;
			}
		}
		return args;
	}
}