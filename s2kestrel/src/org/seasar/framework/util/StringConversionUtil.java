package org.seasar.framework.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public final class StringConversionUtil {

	private StringConversionUtil() {
	}

	public static String toString(Object value) {
		return toString(value, null);
	}

	public static String toString(Object value, String pattern) {
		if (value == null) {
			return null;
		} else if (value instanceof String) {
			return (String) value;
		} else if (value instanceof java.util.Date) {
			return toString((java.util.Date) value, pattern);
		} else if (value instanceof Number) {
			return toString((Number) value, pattern);
		} else if (value instanceof byte[]) {
			return Base64Util.encode((byte[])value);
		} else {
			return value.toString();
		}
	}

	public static String toString(Number value, String pattern) {
		if (value != null) {
			if (pattern != null) {
				return new DecimalFormat(pattern).format(value);
			} else {
				return value.toString();
			}
		} else {
			return null;
		}
	}

	public static String toString(java.util.Date value, String pattern) {
		if (value != null) {
			if (pattern != null) {
				return new SimpleDateFormat(pattern).format(value);
			} else {
				return value.toString();
			}
		} else {
			return null;
		}
	}
}
