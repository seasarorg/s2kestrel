package org.seasar.framework.util;

import java.sql.Date;

public final class SqlDateConversionUtil {

	private SqlDateConversionUtil() {
	}

	public static Date toDate(Object o) {
		return toDate(o, null);
	}
	
	public static Date toDate(Object o, String pattern) {
		if (o instanceof Date) {
			return (Date) o;
		} else {
			java.util.Date date = DateConversionUtil.toDate(o, pattern);
			if (date != null) {
				return new Date(date.getTime());
			} else {
				return null;
			}
		}
	}
}
