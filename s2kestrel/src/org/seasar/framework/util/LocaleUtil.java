package org.seasar.framework.util;

import java.util.Locale;

/**
 * @author higa
 * @author Satoshi Kimura
 *
 */
public class LocaleUtil {

	private LocaleUtil() {
	}

	public static Locale getLocale(String localeStr) {
		Locale locale = Locale.getDefault();
		if (localeStr != null) {
			int index = localeStr.indexOf('_');
			if (index < 0) {
				locale = new Locale(localeStr, "");
			} else {
				String language = localeStr.substring(0, index);
				String country = localeStr.substring(index + 1);
				locale = new Locale(language, country);
			}
		}
		return locale;
	}
}
