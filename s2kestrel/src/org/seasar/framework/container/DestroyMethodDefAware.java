package org.seasar.framework.container;


/**
 * @author higa
 *
 * DestroyMethodDefの設定が可能になります。
 */
public interface DestroyMethodDefAware {
	
	public void addDestroyMethodDef(DestroyMethodDef methodDef);
	
	public int getDestroyMethodDefSize();
	
	public DestroyMethodDef getDestroyMethodDef(int index);
}
