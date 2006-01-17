package org.seasar.framework.container.assembler;

import org.seasar.framework.container.ClassUnmatchRuntimeException;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.IllegalConstructorRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.OgnlUtil;
/**
 * @author higa
 *
 */
public class ExpressionConstructorAssembler
	extends AbstractConstructorAssembler {

	/**
	 * @param componentDef
	 */
	public ExpressionConstructorAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	public Object assemble()
		throws IllegalConstructorRuntimeException {

		ComponentDef cd = getComponentDef();
		S2Container container = cd.getContainer();
		String expression = cd.getExpression();
		Class componentClass = cd.getComponentClass();
		Object component = null;
		Object exp = OgnlUtil.parseExpression(expression);
		component = OgnlUtil.getValue(exp, container);
		if (componentClass != null) {
			if (!componentClass.isInstance(component)) {
				throw new ClassUnmatchRuntimeException(componentClass,
					component != null ? component.getClass() : null);
			}
		}
		return component;

	}
}
