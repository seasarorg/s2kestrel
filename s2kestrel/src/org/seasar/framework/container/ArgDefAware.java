package org.seasar.framework.container;


/**
 * @author higa
 *
 * ArgDef�̐ݒ肪�\�ɂȂ�܂��B
 */
public interface ArgDefAware {
	
	public void addArgDef(ArgDef argDef);
	
	public int getArgDefSize();
	
	public ArgDef getArgDef(int index);
}
