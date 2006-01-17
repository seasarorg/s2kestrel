package org.seasar.framework.util;

import java.sql.Time;

public final class TimeConversionUtil {

	private TimeConversionUtil() {
	}

	public static Time toTime(Object o) {
		return toTime(o, null);
	}
	
	public static Time toTime(Object o, String pattern) {
		if (o instanceof Time) {
			return (Time) o;
		} else {
			java.util.Date date = DateConversionUtil.toDate(o, pattern);
			if (date != null) {
				return new Time(date.getTime());
			} else {
				return null;
			}
		}
	}
}
