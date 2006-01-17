package org.seasar.framework.util;

import java.text.SimpleDateFormat;

public final class IntegerConversionUtil {

	private IntegerConversionUtil() {
	}

	public static Integer toInteger(Object o) {
		return toInteger(o, null);
	}
	
	public static Integer toInteger(Object o, String pattern) {
		if (o == null) {
			return null;
		} else if (o instanceof Integer) {
			return (Integer) o;
		} else if (o instanceof Number) {
			return new Integer(((Number) o).intValue());
		} else if (o instanceof String) {
			return toInteger((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return new Integer(new SimpleDateFormat(pattern).format(o));
			} else {
				return new Integer((int) ((java.util.Date) o).getTime());
			}
		} else if (o instanceof Boolean) {
			return ((Boolean) o).booleanValue() ? new Integer(1) : new Integer(0);
		} else {
			return toInteger(o.toString());
		}
	}
	
	private static Integer toInteger(String s) {
		return new Integer(DecimalFormatUtil.normalize(s));
	}
	
	public static int toPrimitiveInt(Object o) {
		return toPrimitiveInt(o, null);
	}

	public static int toPrimitiveInt(Object o, String pattern) {
		if (o == null) {
			return 0;
		} else if (o instanceof Number) {
			return ((Number) o).intValue();
		} else if (o instanceof String) {
			return toPrimitiveInt((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return Integer.parseInt(new SimpleDateFormat(pattern).format(o));
			} else {
				return (int) ((java.util.Date) o).getTime();
			}
		} else if (o instanceof Boolean) {
			return ((Boolean) o).booleanValue() ? 1 : 0;
		} else {
			return toPrimitiveInt(o.toString());
		}
	}
	
	private static int toPrimitiveInt(String s) {
		return Integer.parseInt(DecimalFormatUtil.normalize(s));
	}
}
