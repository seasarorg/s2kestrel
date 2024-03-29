package org.seasar.framework.container;


/**
 * @author higa
 *
 * PropertyDefの設定が可能になります。
 */
public interface PropertyDefAware {
	
	public void addPropertyDef(PropertyDef propertyDef);
	
	public int getPropertyDefSize();
	
	public PropertyDef getPropertyDef(int index);
	
	public PropertyDef getPropertyDef(String propertyName);
	
	public boolean hasPropertyDef(String propertyName);
}
