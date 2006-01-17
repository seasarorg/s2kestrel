package org.seasar.framework.util;

import java.util.Iterator;
import java.util.Map;

public final class CaseInsensitiveMap extends ArrayMap {

	public CaseInsensitiveMap() {
		super();
	}

	public CaseInsensitiveMap(int capacity) {
		super(capacity);
	}

	public final boolean containsKey(String key) {
		return super.containsKey(convertKey(key));
	}

	public final Object get(Object key) {
		return super.get(convertKey(key));
	}

	public final Object put(Object key, Object value) {
		return super.put(convertKey(key), value);
	}

	public final void putAll(Map map) {
		for (Iterator i = map.entrySet().iterator(); i.hasNext();) {
			Map.Entry entry = (Map.Entry) i.next();
			put(convertKey(entry.getKey()), entry.getValue());
		}
	}

	public final Object remove(Object key) {
		return super.remove(convertKey(key));
	}
	
	public boolean containsKey(Object key) {
		return super.containsKey(convertKey(key));
	}

	private static String convertKey(Object key) {
		return ((String) key).toLowerCase();
	}

}
