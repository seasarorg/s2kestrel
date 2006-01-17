package org.seasar.extension.dataset.types;

import org.seasar.framework.util.BigDecimalConversionUtil;

/**
 * @author higa
 *
 */
public class BigDecimalType extends ObjectType {

	BigDecimalType() {
	}

	public Object convert(Object value, String formatPattern) {
		return BigDecimalConversionUtil.toBigDecimal(value, formatPattern);
	}
}
