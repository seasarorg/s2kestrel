package org.seasar.framework.container.factory;

import org.seasar.framework.container.MethodDef;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.TagHandler;

/**
 * @author higa
 *
 */
public abstract class MethodTagHandler extends TagHandler {

	protected void processExpression(
		MethodDef methodDef,
		String expression,
		String tagName) {

		String expr = expression;
		if (expr != null) {
			expr = expr.trim();
			if (!StringUtil.isEmpty(expr)) {
				methodDef.setExpression(expr);
			} else {
				expr = null;
			}
		}
		if (methodDef.getMethodName() == null && expr == null) {
			throw new TagAttributeNotDefinedRuntimeException(tagName, "name");
		}
	}

}
