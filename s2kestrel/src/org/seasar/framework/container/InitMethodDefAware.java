package org.seasar.framework.container;


/**
 * @author higa
 *
 * InitMethodDef�̐ݒ肪�\�ɂȂ�܂��B
 */
public interface InitMethodDefAware {
	
	public void addInitMethodDef(InitMethodDef methodDef);
	
	public int getInitMethodDefSize();
	
	public InitMethodDef getInitMethodDef(int index);
}
