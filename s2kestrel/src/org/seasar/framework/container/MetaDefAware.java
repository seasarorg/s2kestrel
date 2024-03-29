package org.seasar.framework.container;


/**
 * @author higa
 *
 * MetaDefの設定が可能になります。
 */
public interface MetaDefAware {
	
	public void addMetaDef(MetaDef metaDef);
	
	public int getMetaDefSize();
	
	public MetaDef getMetaDef(int index);
	
	public MetaDef getMetaDef(String name);
	
	public MetaDef[] getMetaDefs(String name);
}
