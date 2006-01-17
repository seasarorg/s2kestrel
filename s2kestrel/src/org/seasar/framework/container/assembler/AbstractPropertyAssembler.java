package org.seasar.framework.container.assembler;

import org.seasar.framework.beans.IllegalPropertyRuntimeException;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.PropertyDef;

/**
 * @author higa
 *
 */
public abstract class AbstractPropertyAssembler
	extends AbstractAssembler
	implements PropertyAssembler {

	public AbstractPropertyAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	protected Object getValue(PropertyDef propertyDef, Object component) {
		try {
			return propertyDef.getValue();
		} catch (ComponentNotFoundRuntimeException cause) {
			throw new IllegalPropertyRuntimeException(
				getComponentClass(component),
				propertyDef.getPropertyName(),
				cause);
		}

	}

	protected void setValue(
		PropertyDesc propertyDesc,
		Object component,
		Object value)
		throws IllegalPropertyRuntimeException {
		
		if (value == null) {
			return;
		}
		try {
			propertyDesc.setValue(component, value);
		} catch (NumberFormatException ex) {
			throw new IllegalPropertyRuntimeException(
				getComponentDef().getComponentClass(),
				propertyDesc.getPropertyName(),
				ex);
		}
	}
}