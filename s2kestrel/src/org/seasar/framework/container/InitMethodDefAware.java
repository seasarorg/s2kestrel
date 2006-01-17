package org.seasar.framework.container;


/**
 * @author higa
 *
 * InitMethodDef‚Ìİ’è‚ª‰Â”\‚É‚È‚è‚Ü‚·B
 */
public interface InitMethodDefAware {
	
	public void addInitMethodDef(InitMethodDef methodDef);
	
	public int getInitMethodDefSize();
	
	public InitMethodDef getInitMethodDef(int index);
}
