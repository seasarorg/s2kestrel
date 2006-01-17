package org.seasar.extension.dataset.types;

import org.seasar.extension.dataset.ColumnType;

/**
 * @author higa
 *
 */
public class ObjectType implements ColumnType {

	ObjectType() {
	}
	
	public Object convert(Object value, String formatPattern) {
		return value;
	}
	
	public boolean equals(Object arg1, Object arg2) {
		if (arg1 == null) {
			return arg2 == null;
		}
		return doEquals(arg1, arg2);
	}
	
	protected boolean doEquals(Object arg1, Object arg2) {
		try {
			arg1 = convert(arg1, null);
		} catch (Throwable t) {
			return false;
		}
		try {
			arg2 = convert(arg2, null);
		} catch (Throwable t) {
			return false;
		}
		return arg1.equals(arg2);
	}
}
