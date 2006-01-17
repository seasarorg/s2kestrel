package org.seasar.framework.container.assembler;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.AutoBindingUtil;
import org.seasar.framework.log.Logger;

/**
 * @author higa
 *  
 */
public class AutoPropertyAssembler extends AbstractPropertyAssembler {

	private static Logger logger_ = Logger
			.getLogger(AutoPropertyAssembler.class);

	/**
	 * @param componentDef
	 */
	public AutoPropertyAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	public void assemble(Object component) {
		BeanDesc beanDesc = getBeanDesc(component);
		S2Container container = getComponentDef().getContainer();
		for (int i = 0; i < beanDesc.getPropertyDescSize(); ++i) {
			Object value = null;
			PropertyDesc propDesc = beanDesc.getPropertyDesc(i);
			String propName = propDesc.getPropertyName();
			if (getComponentDef().hasPropertyDef(propName)) {
				PropertyDef propDef = getComponentDef()
						.getPropertyDef(propName);
				value = getValue(propDef, component);
				setValue(propDesc, component, value);
			} else if (propDesc.getWriteMethod() != null
					&& AutoBindingUtil.isSuitable(propDesc.getPropertyType())) {

				try {
					value = container.getComponent(propDesc.getPropertyType());
				} catch (ComponentNotFoundRuntimeException cause) {
					if (propDesc.getReadMethod() != null
							&& propDesc.getValue(component) != null) {
						continue;
					}
					logger_.log("WSSR0008", new Object[] {
							getComponentClass(component).getName(), propName });
					continue;
				}
				setValue(propDesc, component, value);
			}
		}
	}
}