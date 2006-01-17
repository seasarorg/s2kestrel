package org.seasar.framework.util;

import java.lang.reflect.Field;

import org.seasar.framework.exception.IllegalAccessRuntimeException;

/**
 * @author higa
 *
 */
public final class FieldUtil {

	private FieldUtil() {
	}

	public static Object get(Field field, Object target)
		throws IllegalAccessRuntimeException {

		try {
			return field.get(target);
		} catch (IllegalAccessException ex) {
			throw new IllegalAccessRuntimeException(
				field.getDeclaringClass(),
				ex);
		}
	}
	
	public static int getInt(Field field, Object target)
		throws IllegalAccessRuntimeException {
	
		try {
			return field.getInt(target);
		} catch (IllegalAccessException ex) {
			throw new IllegalAccessRuntimeException(
				field.getDeclaringClass(),
				ex);
		}
	
	}
	
	public static void set(Field field, Object target, Object value)
		throws IllegalAccessRuntimeException {

		try {
			field.set(target, value);
		} catch (IllegalAccessException ex) {
			throw new IllegalAccessRuntimeException(
				field.getDeclaringClass(),
				ex);
		}

	}
}
