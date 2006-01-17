package org.seasar.framework.util;

import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author higa
 *
 */
public final class DecimalFormatUtil {
	
	private DecimalFormatUtil() {
	}

	public static String normalize(String s) {
		return normalize(s, Locale.getDefault());
	}

	public static String normalize(String s, Locale locale) {
		if (s == null) {
			return null;
		}
		DecimalFormatSymbols symbols = DecimalFormatSymbolsUtil.getDecimalFormatSymbols(locale);
		char decimalSep = symbols.getDecimalSeparator();
		char groupingSep = symbols.getGroupingSeparator();
		StringBuffer buf = new StringBuffer(20);
		for (int i = 0; i < s.length(); ++i) {
			char c = s.charAt(i);
			if (c == groupingSep) {
				continue;
			} else if (c == decimalSep) {
				c = '.';
			}
			buf.append(c);
		}
		return buf.toString();
	}
}
