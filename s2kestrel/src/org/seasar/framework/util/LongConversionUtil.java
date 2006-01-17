package org.seasar.framework.util;

import java.text.SimpleDateFormat;

public final class LongConversionUtil {

	private LongConversionUtil() {
	}

	public static Long toLong(Object o) {
		return toLong(o, null);
	}
	
	public static Long toLong(Object o, String pattern) {
		if (o == null) {
			return null;
		} else if (o instanceof Long) {
			return (Long) o;
		} else if (o instanceof Number) {
			return new Long(((Number) o).longValue());
		} else if (o instanceof String) {
			return toLong((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return new Long(new SimpleDateFormat(pattern).format(o));
			} else {
				return new Long(((java.util.Date) o).getTime());
			}
		} else if (o instanceof Boolean) {
			return ((Boolean) o).booleanValue() ? new Long(1) : new Long(0);
		} else {
			return toLong(o.toString());
		}
	}
	
	private static Long toLong(String s) {
		return new Long(DecimalFormatUtil.normalize(s));
	}
	
	public static long toPrimitiveLong(Object o) {
		return toPrimitiveLong(o, null);
	}

	public static long toPrimitiveLong(Object o, String pattern) {
		if (o == null) {
			return 0;
		} else if (o instanceof Number) {
			return ((Number) o).longValue();
		} else if (o instanceof String) {
			return toPrimitiveLong((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return Long.parseLong(new SimpleDateFormat(pattern).format(o));
			} else {
				return ((java.util.Date) o).getTime();
			}
		} else if (o instanceof Boolean) {
			return ((Boolean) o).booleanValue() ? 1 : 0;
		} else {
			return toPrimitiveLong(o.toString());
		}
	}
	
	private static long toPrimitiveLong(String s) {
		return Long.parseLong(DecimalFormatUtil.normalize(s));
	}
}
