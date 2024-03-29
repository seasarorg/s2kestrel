package org.seasar.framework.container.assembler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ComponentNotFoundRuntimeException;
import org.seasar.framework.container.IllegalMethodRuntimeException;
import org.seasar.framework.container.MethodDef;
import org.seasar.framework.container.util.AutoBindingUtil;
import org.seasar.framework.util.MethodUtil;
import org.seasar.framework.util.OgnlUtil;

/**
 * @author higa
 *
 */
public abstract class AbstractMethodAssembler
	extends AbstractAssembler
	implements MethodAssembler {

	public AbstractMethodAssembler(ComponentDef componentDef) {
		super(componentDef);
	}
	
	protected void invoke(
		BeanDesc beanDesc,
		Object component,
		MethodDef methodDef)
		throws IllegalMethodRuntimeException {

		String expression = methodDef.getExpression();
		String methodName = methodDef.getMethodName();
		if (methodName != null) {
			Object[] args = null;
			Method method = null;
			try {
				if (methodDef.getArgDefSize() > 0) {
					args = methodDef.getArgs();
				} else {
					Method[] methods = beanDesc.getMethods(methodName);
					method = getSuitableMethod(methods);
					if (method != null) {
						args = getArgs(method.getParameterTypes());
					}
				}
			} catch (ComponentNotFoundRuntimeException cause) {
				throw new IllegalMethodRuntimeException(
					getComponentClass(component),
					methodName,
					cause);
			}
			if (method != null) {
				MethodUtil.invoke(method, component, args);
			} else {
				invoke(beanDesc, component, methodName, args);
			}
		} else {
			invokeExpression(component, expression);
		}
	}
	
	private void invokeExpression(Object component, String expression) {
		Object exp = OgnlUtil.parseExpression(expression);
		Map ctx = new HashMap();
		ctx.put("self", component);
		ctx.put("out", System.out);
		ctx.put("err", System.err);
		OgnlUtil.getValue(exp, ctx, getComponentDef().getContainer());
	}
	
	private Method getSuitableMethod(Method[] methods) {
		int argSize = -1;
		Method method = null;
		for (int i = 0; i < methods.length; ++i) {
			int tempArgSize = methods[i].getParameterTypes().length;
			if (tempArgSize > argSize
				&& AutoBindingUtil.isSuitable(
					methods[i].getParameterTypes())) {
				method = methods[i];
				argSize = tempArgSize;
			}
		}
		return method;
	}

	private void invoke(
		BeanDesc beanDesc,
		Object component,
		String methodName,
		Object[] args)
		throws IllegalMethodRuntimeException {

		try {
			beanDesc.invoke(component, methodName, args);
		} catch (NumberFormatException ex) {
			throw new IllegalMethodRuntimeException(
				getComponentDef().getComponentClass(),
				methodName,
				ex);
		}
	}
}
