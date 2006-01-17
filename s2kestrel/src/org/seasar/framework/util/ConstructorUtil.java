package org.seasar.framework.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.seasar.framework.exception.IllegalAccessRuntimeException;
import org.seasar.framework.exception.InstantiationRuntimeException;
import org.seasar.framework.exception.InvocationTargetRuntimeException;

/**
 * @author higa
 *
 */
public final class ConstructorUtil {
 
	/**
	 * 
	 */
	private ConstructorUtil() {
	}

	public static Object newInstance(Constructor constructor, Object[] args)
		throws
			InstantiationRuntimeException,
			IllegalAccessRuntimeException,
			InvocationTargetRuntimeException {

		try {
			return constructor.newInstance(args);
		} catch (InstantiationException ex) {
			throw new InstantiationRuntimeException(
				constructor.getDeclaringClass(), ex);
		} catch (IllegalAccessException ex) {
			throw new IllegalAccessRuntimeException(
				constructor.getDeclaringClass(), ex);
		} catch (InvocationTargetException ex) {
			throw new InvocationTargetRuntimeException(
				constructor.getDeclaringClass(), ex);
		}
	}
}
