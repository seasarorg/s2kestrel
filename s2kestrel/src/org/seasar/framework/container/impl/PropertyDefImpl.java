package org.seasar.framework.container.impl;

import org.seasar.framework.container.PropertyDef;

/**
 * @author higa
 *
 */
public class PropertyDefImpl extends ArgDefImpl implements PropertyDef {

	private String propertyName_;
	
	public PropertyDefImpl(String propertyName) {
		propertyName_ = propertyName;
	}
		
	public PropertyDefImpl(String propertyName, Object value) {
		super(value);
		propertyName_ = propertyName;
	}

	/**
	 * @see org.seasar.framework.container.PropertyDef#getPropertyName()
	 */
	public String getPropertyName() {
		return propertyName_;
	}
}
