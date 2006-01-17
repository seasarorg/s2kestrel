package org.seasar.extension.dataset.types;

import org.seasar.framework.util.TimestampConversionUtil;

/**
 * @author higa
 *  
 */
public class TimestampType extends ObjectType {

	TimestampType() {
	}

	public Object convert(Object value, String formatPattern) {
		return TimestampConversionUtil.toTimestamp(value, formatPattern);
	}
}