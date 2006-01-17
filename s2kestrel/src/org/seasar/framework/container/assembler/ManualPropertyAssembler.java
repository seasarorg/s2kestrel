package org.seasar.framework.container.assembler;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.PropertyDef;

/**
 * @author higa
 *
 */
public class ManualPropertyAssembler extends AbstractPropertyAssembler {

	/**
	 * @param componentDef
	 */
	public ManualPropertyAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	public void assemble(Object component) {
		BeanDesc beanDesc = getBeanDesc(component);
		int size = getComponentDef().getPropertyDefSize();
		for (int i = 0; i < size; ++i) {
			PropertyDef propDef = getComponentDef().getPropertyDef(i);
			Object value = getValue(propDef, component);
			PropertyDesc propDesc =
				beanDesc.getPropertyDesc(propDef.getPropertyName());
			setValue(propDesc, component, value);
		}
	}
}