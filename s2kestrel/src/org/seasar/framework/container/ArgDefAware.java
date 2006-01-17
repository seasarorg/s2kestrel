package org.seasar.framework.container;


/**
 * @author higa
 *
 * ArgDef‚Ìİ’è‚ª‰Â”\‚É‚È‚è‚Ü‚·B
 */
public interface ArgDefAware {
	
	public void addArgDef(ArgDef argDef);
	
	public int getArgDefSize();
	
	public ArgDef getArgDef(int index);
}
