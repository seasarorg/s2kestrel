package org.seasar.framework.util;

/**
 * @author higa
 *
 */
public class EmptyEnumeration extends EnumerationAdapter {

	public EmptyEnumeration() {
		super(new EmptyIterator());
	}
}
