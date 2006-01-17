package org.seasar.framework.container;


/**
 * @author higa
 *
 * MetaDef‚Ìİ’è‚ª‰Â”\‚É‚È‚è‚Ü‚·B
 */
public interface MetaDefAware {
	
	public void addMetaDef(MetaDef metaDef);
	
	public int getMetaDefSize();
	
	public MetaDef getMetaDef(int index);
	
	public MetaDef getMetaDef(String name);
	
	public MetaDef[] getMetaDefs(String name);
}
