package org.seasar.framework.util;

import java.text.SimpleDateFormat;

public final class DoubleConversionUtil {

	private DoubleConversionUtil() {
	}

	public static Double toDouble(Object o) {
		return toDouble(o, null);
	}
	
	public static Double toDouble(Object o, String pattern) {
		if (o == null) {
			return null;
		} else if (o instanceof Double) {
			return (Double) o;
		} else if (o instanceof Number) {
			return new Double(((Number) o).doubleValue());
		} else if (o instanceof String) {
			return toDouble((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return new Double(new SimpleDateFormat(pattern).format(o));
			} else {
				return new Double(((java.util.Date) o).getTime());
			}
		} else {
			return toDouble(o.toString());
		}
	}
	
	private static Double toDouble(String s) {
		return new Double(DecimalFormatUtil.normalize(s));
	}
	
	public static double toPrimitiveDouble(Object o) {
		return toPrimitiveDouble(o, null);
	}

	public static double toPrimitiveDouble(Object o, String pattern) {
		if (o == null) {
			return 0;
		} else if (o instanceof Number) {
			return ((Number) o).doubleValue();
		} else if (o instanceof String) {
			return toPrimitiveDouble((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return Double.parseDouble(new SimpleDateFormat(pattern).format(o));
			} else {
				return ((java.util.Date) o).getTime();
			}
		} else {
			return toPrimitiveDouble(o.toString());
		}
	}
	
	private static double toPrimitiveDouble(String s) {
		return Double.parseDouble(DecimalFormatUtil.normalize(s));
	}
}
