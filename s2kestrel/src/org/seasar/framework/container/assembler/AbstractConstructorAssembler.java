package org.seasar.framework.container.assembler;

import java.lang.reflect.Constructor;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ConstructorUtil;

/**
 * @author higa
 *  
 */
public abstract class AbstractConstructorAssembler extends AbstractAssembler
		implements ConstructorAssembler {

	public AbstractConstructorAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	protected Object assembleDefault() {
		Class clazz = getComponentDef().getConcreteClass();
		Constructor constructor = ClassUtil.getConstructor(clazz, null);
		return ConstructorUtil.newInstance(constructor, null);
	}
}