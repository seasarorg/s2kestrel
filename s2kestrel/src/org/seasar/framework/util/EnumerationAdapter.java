package org.seasar.framework.util;

import java.util.Enumeration;
import java.util.Iterator;

public class EnumerationAdapter implements Enumeration {

	private Iterator iterator_;

	public EnumerationAdapter(Iterator iterator) {
		iterator_ = iterator;
	}

	public boolean hasMoreElements() {
		return iterator_.hasNext();
	}

	public Object nextElement() {
		return iterator_.next();
	}
}