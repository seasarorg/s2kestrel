package org.seasar.framework.container;


/**
 * @author higa
 *
 * PropertyDef�̐ݒ肪�\�ɂȂ�܂��B
 */
public interface PropertyDefAware {
	
	public void addPropertyDef(PropertyDef propertyDef);
	
	public int getPropertyDefSize();
	
	public PropertyDef getPropertyDef(int index);
	
	public PropertyDef getPropertyDef(String propertyName);
	
	public boolean hasPropertyDef(String propertyName);
}
