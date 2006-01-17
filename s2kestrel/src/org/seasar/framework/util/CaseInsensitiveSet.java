package org.seasar.framework.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CaseInsensitiveSet
	extends AbstractSet
	implements Set, Serializable {
	
	static final long serialVersionUID = 0L;
	private transient Map map;
	private static final Object PRESENT = new Object();

	public CaseInsensitiveSet() {
		map = new CaseInsensitiveMap();
	}

	public CaseInsensitiveSet(Collection c) {
		map = new CaseInsensitiveMap(Math.max((int) (c.size() / .75f) + 1, 16));
		addAll(c);
	}

	public CaseInsensitiveSet(int initialCapacity) {
		map = new CaseInsensitiveMap(initialCapacity);
	}

	public Iterator iterator() {
		return map.keySet().iterator();
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	public boolean add(Object o) {
		return map.put(o, PRESENT) == null;
	}

	public boolean remove(Object o) {
		return map.remove(o) == PRESENT;
	}

	public void clear() {
		map.clear();
	}
}
