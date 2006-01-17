package org.seasar.framework.util;

import java.sql.Timestamp;
import java.util.Date;

public final class TimestampConversionUtil {

	private TimestampConversionUtil() {
	}

	public static Timestamp toTimestamp(Object o) {
		return toTimestamp(o, null);
	}
	
	public static Timestamp toTimestamp(Object o, String pattern) {
		if (o instanceof Timestamp) {
			return (Timestamp) o;
		} else {
			Date date = DateConversionUtil.toDate(o, pattern);
			if (date != null) {
				return new Timestamp(date.getTime());
			} else {
				return null;
			}
		}
	}
}
