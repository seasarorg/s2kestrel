package org.seasar.framework.util;

import java.util.Map;

import org.seasar.framework.exception.OgnlRuntimeException;

import ognl.Ognl;
import ognl.OgnlException;

/**
 * @author higa
 *
 */
public final class OgnlUtil {

	private OgnlUtil() {
	}

	public static Object getValue(Object exp, Object root) {
		try {
			return Ognl.getValue(exp, root);
		} catch (OgnlException ex) {
			throw new OgnlRuntimeException(ex);
		}
	}
	
	public static Object getValue(Object exp, Map ctx, Object root) {
		try {
			return Ognl.getValue(exp, ctx, root);
		} catch (OgnlException ex) {
			throw new OgnlRuntimeException(ex);
		}
	}
	
	public static Object parseExpression(String expression) {
		try {
			return Ognl.parseExpression(expression);
		} catch (OgnlException ex) {
			System.err.println("parseExpression[" + expression + "]");
			throw new OgnlRuntimeException(ex);
		}
	}
}
