package org.seasar.framework.container.assembler;

import java.lang.reflect.Constructor;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.IllegalConstructorRuntimeException;
import org.seasar.framework.container.util.AutoBindingUtil;
import org.seasar.framework.util.ConstructorUtil;

/**
 * @author higa
 *
 */
public class AutoConstructorAssembler
	extends AbstractConstructorAssembler {

	/**
	 * @param componentDef
	 */
	public AutoConstructorAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	public Object assemble() throws IllegalConstructorRuntimeException {
		Constructor constructor = getSuitableConstructor();
		if (constructor == null) {
			return assembleDefault();
		}
		Object[] args = getArgs(constructor.getParameterTypes());
		return ConstructorUtil.newInstance(constructor, args);
	}

	private Constructor getSuitableConstructor() {
		int argSize = -1;
		Constructor constructor = null;
		Constructor[] constructors =
			getComponentDef().getConcreteClass().getConstructors();
		for (int i = 0; i < constructors.length; ++i) {
			int tempArgSize = constructors[i].getParameterTypes().length;
			if (tempArgSize == 0) {
				return null;
			}
			if (tempArgSize > argSize
				&& AutoBindingUtil.isSuitable(
					constructors[i].getParameterTypes())) {
				constructor = constructors[i];
				argSize = tempArgSize;
			}
		}
		return constructor;
	}
}
