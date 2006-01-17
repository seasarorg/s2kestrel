package org.seasar.framework.util;

import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author higa
 *
 */
public final class DecimalFormatSymbolsUtil {

	private static Map cache_ = Collections.synchronizedMap(new HashMap());
	
	private DecimalFormatSymbolsUtil() {
	}

	public static DecimalFormatSymbols getDecimalFormatSymbols() {
		return getDecimalFormatSymbols(Locale.getDefault());
	}

	public static DecimalFormatSymbols getDecimalFormatSymbols(Locale locale) {
		DecimalFormatSymbols symbols = (DecimalFormatSymbols) cache_.get(locale);
		if (symbols == null) {
			symbols = new DecimalFormatSymbols(locale);
			cache_.put(locale, symbols);
		}
		return symbols;
	}
}
