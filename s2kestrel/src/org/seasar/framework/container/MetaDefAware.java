package org.seasar.framework.container;


/**
 * @author higa
 *
 * MetaDef�̐ݒ肪�\�ɂȂ�܂��B
 */
public interface MetaDefAware {
	
	public void addMetaDef(MetaDef metaDef);
	
	public int getMetaDefSize();
	
	public MetaDef getMetaDef(int index);
	
	public MetaDef getMetaDef(String name);
	
	public MetaDef[] getMetaDefs(String name);
}
