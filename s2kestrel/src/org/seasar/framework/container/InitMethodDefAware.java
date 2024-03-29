package org.seasar.framework.container;


/**
 * @author higa
 *
 * InitMethodDefの設定が可能になります。
 */
public interface InitMethodDefAware {
	
	public void addInitMethodDef(InitMethodDef methodDef);
	
	public int getInitMethodDefSize();
	
	public InitMethodDef getInitMethodDef(int index);
}
