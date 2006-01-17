package org.seasar.framework.container;


/**
 * @author higa
 *
 * DestroyMethodDef�̐ݒ肪�\�ɂȂ�܂��B
 */
public interface DestroyMethodDefAware {
	
	public void addDestroyMethodDef(DestroyMethodDef methodDef);
	
	public int getDestroyMethodDefSize();
	
	public DestroyMethodDef getDestroyMethodDef(int index);
}
