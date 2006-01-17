package org.seasar.framework.util;

import java.text.SimpleDateFormat;

public final class ByteConversionUtil {

	private ByteConversionUtil() {
	}

	public static Byte toByte(Object o) {
		return toByte(o, null);
	}
	
	public static Byte toByte(Object o, String pattern) {
		if (o == null) {
			return null;
		} else if (o instanceof Byte) {
			return (Byte) o;
		} else if (o instanceof Number) {
			return new Byte(((Number) o).byteValue());
		} else if (o instanceof String) {
			return toByte((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return new Byte(new SimpleDateFormat(pattern).format(o));
			} else {
				return new Byte((byte) ((java.util.Date) o).getTime());
			}
		} else if (o instanceof Boolean) {
			return ((Boolean) o).booleanValue() ? new Byte((byte) 1) : new Byte((byte) 0);
		} else {
			return toByte(o.toString());
		}
	}
	
	private static Byte toByte(String s) {
		return new Byte(DecimalFormatUtil.normalize(s));
	}
	
	public static byte toPrimitiveByte(Object o) {
		return toPrimitiveByte(o, null);
	}

	public static byte toPrimitiveByte(Object o, String pattern) {
		if (o == null) {
			return 0;
		} else if (o instanceof Number) {
			return ((Number) o).byteValue();
		} else if (o instanceof String) {
			return toPrimitiveByte((String) o);
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return Byte.parseByte(new SimpleDateFormat(pattern).format(o));
			} else {
				return (byte) ((java.util.Date) o).getTime();
			}
		} else if (o instanceof Boolean) {
			return ((Boolean) o).booleanValue() ? (byte) 1 : (byte) 0;
		} else {
			return toPrimitiveByte(o.toString());
		}
	}
	
	private static byte toPrimitiveByte(String s) {
		return Byte.parseByte(DecimalFormatUtil.normalize(s));
	}
}
