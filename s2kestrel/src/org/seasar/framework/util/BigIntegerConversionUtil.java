package org.seasar.framework.util;

import java.math.BigInteger;

public final class BigIntegerConversionUtil {

	private BigIntegerConversionUtil() {
	}

	public static BigInteger toBigInteger(Object o) {
		return toBigInteger(o, null);
	}
	
	public static BigInteger toBigInteger(Object o, String pattern) {
		if (o == null) {
			return null;
		} else if (o instanceof BigInteger) {
			return (BigInteger) o;
		} else {
			long l = LongConversionUtil.toLong(o, pattern).longValue();
			return BigInteger.valueOf(l);
		}
	}	
}
