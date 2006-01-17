package org.seasar.framework.container;


/**
 * @author higa
 *
 * PropertyDef‚Ìİ’è‚ª‰Â”\‚É‚È‚è‚Ü‚·B
 */
public interface PropertyDefAware {
	
	public void addPropertyDef(PropertyDef propertyDef);
	
	public int getPropertyDefSize();
	
	public PropertyDef getPropertyDef(int index);
	
	public PropertyDef getPropertyDef(String propertyName);
	
	public boolean hasPropertyDef(String propertyName);
}
