package org.seasar.framework.util;

import java.text.SimpleDateFormat;

public final class FloatConversionUtil {

	private FloatConversionUtil() {
	}

	public static Float toFloat(Object o) {
		return toFloat(o, null);
	}
	
	public static Float toFloat(Object o, String pattern) {
		if (o == null) {
			return null;
		} else if (o instanceof Float) {
			return (Float) o;
		} else if (o instanceof Number) {
			return new Float(((Number) o).floatValue());
		} else if (o instanceof String) {
			return toFloat((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return new Float(new SimpleDateFormat(pattern).format(o));
			} else {
				return new Float(((java.util.Date) o).getTime());
			}
		} else {
			return toFloat(o.toString());
		}
	}
	
	private static Float toFloat(String s) {
		return new Float(DecimalFormatUtil.normalize(s));
	}
	
	public static float toPrimitiveFloat(Object o) {
		return toPrimitiveFloat(o, null);
	}

	public static float toPrimitiveFloat(Object o, String pattern) {
		if (o == null) {
			return 0;
		} else if (o instanceof Number) {
			return ((Number) o).floatValue();
		} else if (o instanceof String) {
			return toPrimitiveFloat((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return Float.parseFloat(new SimpleDateFormat(pattern).format(o));
			} else {
				return ((java.util.Date) o).getTime();
			}
		} else {
			return toPrimitiveFloat(o.toString());
		}
	}
	
	private static float toPrimitiveFloat(String s) {
		return Float.parseFloat(DecimalFormatUtil.normalize(s));
	}
}
