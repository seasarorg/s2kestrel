package org.seasar.extension.dataset.types;

import java.util.Arrays;

import org.seasar.framework.util.BinaryConversionUtil;

/**
 * @author higa
 *
 */
public class BinaryType extends ObjectType {

	BinaryType() {
	}

	public Object convert(Object value, String formatPattern) {
		return BinaryConversionUtil.toBinary(value);
	}
	
	protected boolean doEquals(Object arg1, Object arg2) {
		if (arg1 instanceof byte[] && arg2 instanceof byte[]) {
			return Arrays.equals((byte[]) arg1, (byte[]) arg2);
		} else {
			return false;
		}
	}
}
