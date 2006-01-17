package org.seasar.framework.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author higa
 *  
 */
public class EmptyIterator implements Iterator {

	public EmptyIterator() {
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new IllegalStateException();
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return false;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
		throw new NoSuchElementException();
	}

}