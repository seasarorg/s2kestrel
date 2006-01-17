package org.seasar.framework.container;


/**
 * @author higa
 *
 * ArgDefの設定が可能になります。
 */
public interface ArgDefAware {
	
	public void addArgDef(ArgDef argDef);
	
	public int getArgDefSize();
	
	public ArgDef getArgDef(int index);
}
