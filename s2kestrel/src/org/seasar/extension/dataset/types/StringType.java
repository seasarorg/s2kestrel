package org.seasar.extension.dataset.types;

import org.seasar.framework.util.StringConversionUtil;

/**
 * @author higa
 *
 */
public class StringType extends ObjectType {

	StringType() {
	}
	
	public Object convert(Object value, String formatPattern) {
		String s = StringConversionUtil.toString(value, formatPattern);
		if (s != null) {
			s = s.trim();
		}
		if ("".equals(s)) {
			s = null;
		}
		return s;
	}
}
