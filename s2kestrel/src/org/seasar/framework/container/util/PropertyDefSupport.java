package org.seasar.framework.container.util;

import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.CaseInsensitiveMap;

/**
 * @author higa
 *
 * PropertyDefの設定をサポートします
 */
public final class PropertyDefSupport {

	private CaseInsensitiveMap propertyDefs_ = new CaseInsensitiveMap();
	private S2Container container_;

	public PropertyDefSupport() {
	}

	public synchronized void addPropertyDef(PropertyDef propertyDef) {
		if (container_ != null) {
			propertyDef.setContainer(container_);
		}
		propertyDefs_.put(propertyDef.getPropertyName(), propertyDef);
	}

	public synchronized int getPropertyDefSize() {
		return propertyDefs_.size();
	}

	public synchronized PropertyDef getPropertyDef(int index) {
		return (PropertyDef) propertyDefs_.get(index);
	}
	
	public synchronized PropertyDef getPropertyDef(String propertyName) {
		return (PropertyDef) propertyDefs_.get(propertyName);
	}
	
	public synchronized boolean hasPropertyDef(String propertyName) {
		return propertyDefs_.containsKey(propertyName);
	}

	public void setContainer(S2Container container) {
		container_ = container;
		for (int i = 0; i < getPropertyDefSize(); ++i) {
			getPropertyDef(i).setContainer(container);
		}
	}
}