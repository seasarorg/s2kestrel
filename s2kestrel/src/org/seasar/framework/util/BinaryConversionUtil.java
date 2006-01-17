package org.seasar.framework.util;

public final class BinaryConversionUtil {

	private BinaryConversionUtil() {
	}
	
	public static byte[] toBinary(Object o) {
		if (o instanceof byte[]) {
			return (byte[])o;
		} else if (o == null) {
			return null;
		} else {
			if (o instanceof String) {
				return ((String) o).getBytes();
			} else {
				throw new IllegalArgumentException(o.getClass().toString());
			}
		}
	}
}