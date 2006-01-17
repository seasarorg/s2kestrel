package org.seasar.extension.mock.servlet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author higa
 *  
 */
public final class MockHeaderUtil {

	private static SimpleDateFormat[] DATE_FORMATTERS = {
			new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US),
			new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US),
			new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy/MM/dd") };

	private MockHeaderUtil() {
	}

	public static long getDateValue(String value) {
		if (value == null) {
			return -1L;
		}
		for (int i = 0; i < DATE_FORMATTERS.length; i++) {
			try {
				return DATE_FORMATTERS[i].parse(value).getTime();
			} catch (ParseException ignore) {
			}
		}
		throw new IllegalArgumentException(value);
	}

	public static int getIntValue(String value) {
		if (value == null) {
			return -1;
		} else {
			return Integer.parseInt(value);
		}
	}

	public static String getDateValue(long value) {
		return DATE_FORMATTERS[0].format(new Date(value));
	}
}