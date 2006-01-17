package org.seasar.framework.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author higa
 *  
 */
public class ResourceBundleUtil {

	private ResourceBundleUtil() {
	}

	public static final ResourceBundle getBundle(String name, Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		try {
			return ResourceBundle.getBundle(name, locale);
		} catch (MissingResourceException ignore) {
			return null;
		}
	}
	
	public static final Map convertMap(ResourceBundle bundle) {
		Map ret = new HashMap();
		for (Enumeration e = bundle.getKeys(); e.hasMoreElements(); ) {
			String key = (String) e.nextElement();
			String value = bundle.getString(key);
			ret.put(key, value);
		}
		return ret;
	}
}