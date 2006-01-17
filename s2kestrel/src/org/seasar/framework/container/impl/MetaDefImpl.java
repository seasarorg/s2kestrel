package org.seasar.framework.container.impl;

import org.seasar.framework.container.MetaDef;

/**
 * @author higa
 *
 */
public class MetaDefImpl extends ArgDefImpl implements MetaDef {

	private String name_;
	
	public MetaDefImpl() {
	}
	
	public MetaDefImpl(String name) {
		name_ = name;
	}
		
	public MetaDefImpl(String name, Object value) {
		super(value);
		name_ = name;
	}
	
	/**
	 * @see org.seasar.framework.container.MetaDef#getName()
	 */
	public String getName() {
		return name_;
	}
}