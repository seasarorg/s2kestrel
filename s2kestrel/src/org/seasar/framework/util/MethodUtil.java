package org.seasar.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.seasar.framework.exception.IllegalAccessRuntimeException;
import org.seasar.framework.exception.InstantiationRuntimeException;
import org.seasar.framework.exception.InvocationTargetRuntimeException;

/**
 * @author higa
 * @author Satoshi Kimura
 *
 */
public final class MethodUtil {

	/**
	 * 
	 */
	private MethodUtil() {
	}

	public static Object invoke(Method method, Object target, Object[] args)
		throws InstantiationRuntimeException, IllegalAccessRuntimeException {

		try {
			return method.invoke(target, args);
		} catch (InvocationTargetException ex) {
			Throwable t = ex.getTargetException();
			if (t instanceof RuntimeException) {
				throw (RuntimeException) t;
			}
			if (t instanceof Error) {
				throw (Error) t;
			}
			throw new InvocationTargetRuntimeException(
				method.getDeclaringClass(),
				ex);
		} catch (IllegalAccessException ex) {
			throw new IllegalAccessRuntimeException(
				method.getDeclaringClass(),
				ex);
		}
	}
	
	public static boolean isAbstract(Method method) {
		int mod = method.getModifiers();
		return Modifier.isAbstract(mod);
	}

	public static String getSignature(String methodName, Class[] argTypes) {
		StringBuffer buf = new StringBuffer(100);
		buf.append(methodName);
		buf.append("(");
		if (argTypes != null) {
			for (int i = 0; i < argTypes.length; ++i) {
				if (i > 0) {
					buf.append(", ");
				}
				buf.append(argTypes[i].getName());
			}
		}
		buf.append(")");
		return buf.toString();
	}

	public static String getSignature(String methodName, Object[] methodArgs) {
		StringBuffer buf = new StringBuffer(100);
		buf.append(methodName);
		buf.append("(");
		if (methodArgs != null) {
			for (int i = 0; i < methodArgs.length; ++i) {
				if (i > 0) {
					buf.append(", ");
				}
				if (methodArgs[i] != null) {
					buf.append(methodArgs[i].getClass().getName());
				} else {
					buf.append("null"); 
				}
			}
		}
		buf.append(")");
		return buf.toString();
	}
}
