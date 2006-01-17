package org.seasar.framework.util;

import java.text.SimpleDateFormat;

public final class ShortConversionUtil {

	private ShortConversionUtil() {
	}

	public static Short toShort(Object o) {
		return toShort(o, null);
	}

	public static Short toShort(Object o, String pattern) {
		if (o == null) {
			return null;
		} else if (o instanceof Short) {
			return (Short) o;
		} else if (o instanceof Number) {
			return new Short(((Number) o).shortValue());
		} else if (o instanceof String) {
			return toShort((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return new Short(new SimpleDateFormat(pattern).format(o));
			} else {
				return new Short((short) ((java.util.Date) o).getTime());
			}
		} else if (o instanceof Boolean) {
			return ((Boolean) o).booleanValue() ? new Short((short) 1)
					: new Short((short) 0);
		} else {
			return toShort(o.toString());
		}
	}

	private static Short toShort(String s) {
		return new Short(DecimalFormatUtil.normalize(s));
	}

	public static short toPrimitiveShort(Object o) {
		return toPrimitiveShort(o, null);
	}

	public static short toPrimitiveShort(Object o, String pattern) {
		if (o == null) {
			return 0;
		} else if (o instanceof Number) {
			return ((Number) o).shortValue();
		} else if (o instanceof String) {
			return toPrimitiveShort((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return Short
						.parseShort(new SimpleDateFormat(pattern).format(o));
			} else {
				return (short) ((java.util.Date) o).getTime();
			}
		} else if (o instanceof Boolean) {
			return ((Boolean) o).booleanValue() ? (short) 1 : (short) 0;
		} else {
			return toPrimitiveShort(o.toString());
		}
	}

	private static short toPrimitiveShort(String s) {
		return Short.parseShort(DecimalFormatUtil.normalize(s));
	}
}