package org.seasar.framework.util;

/**
 * 
 * @author higa
 * @author Satoshi Kimura
 */
public final class BooleanConversionUtil {

	private BooleanConversionUtil() {
	}

	public static Boolean toBoolean(Object o) {
		if (o == null) {
			return null;
		} else if (o instanceof Boolean) {
			return (Boolean) o;
		} else if (o instanceof Number) {
			int num = ((Number) o).intValue();
			return new Boolean(num != 0);
		} else if (o instanceof String) {
			String s = (String) o;
			if ("true".equalsIgnoreCase(s)) {
				return Boolean.TRUE;
			} else if ("false".equalsIgnoreCase(s)) {
				return Boolean.FALSE;
			} else if (s.equals("0")) {
				return Boolean.FALSE;
			} else {
				return Boolean.TRUE;
			}
		} else {
			return Boolean.TRUE;
		}
	}

	public static boolean toPrimitiveBoolean(Object o) {
		Boolean b = toBoolean(o);
		if (b != null) {
			return b.booleanValue();
		} else {
			return false;
		}
	}
}