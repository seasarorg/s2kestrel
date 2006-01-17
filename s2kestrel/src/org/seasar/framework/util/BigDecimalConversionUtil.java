package org.seasar.framework.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public final class BigDecimalConversionUtil {

	private BigDecimalConversionUtil() {
	}

	public static BigDecimal toBigDecimal(Object o) {
		return toBigDecimal(o, null);
	}
	
	public static BigDecimal toBigDecimal(Object o, String pattern) {
		if (o == null) {
			return null;
		} else if (o instanceof BigDecimal) {
			return (BigDecimal) o;
		} else if (o instanceof Integer) {
			return new BigDecimal(((Integer) o).intValue());
		} else if (o instanceof String) {
			return new BigDecimal((String) o);
		} else if (o instanceof Double) {
			return new BigDecimal(((Double) o).doubleValue());
		} else if (o instanceof Long) {
			return new BigDecimal(((Long) o).longValue());
		} else if (o instanceof Short) {
			return new BigDecimal(((Short) o).shortValue());
		} else if (o instanceof Float) {
			return new BigDecimal(((Float) o).floatValue());
		} else if (o instanceof java.util.Date) {
			if (pattern != null) {
				return new BigDecimal(new SimpleDateFormat(pattern).format(o));
			} else {
				return new BigDecimal(((java.util.Date) o).getTime());
			}
		} else {
			return new BigDecimal(o.toString());
		}
	}	
}
