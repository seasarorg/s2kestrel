package org.seasar.framework.container;


/**
 * @author higa
 *
 * DestroyMethodDef‚Ìİ’è‚ª‰Â”\‚É‚È‚è‚Ü‚·B
 */
public interface DestroyMethodDefAware {
	
	public void addDestroyMethodDef(DestroyMethodDef methodDef);
	
	public int getDestroyMethodDefSize();
	
	public DestroyMethodDef getDestroyMethodDef(int index);
}
