package org.seasar.framework.container;


/**
 * @author higa
 *
 * AspectDef‚Ìİ’è‚ª‰Â”\‚É‚È‚è‚Ü‚·B
 */
public interface AspectDefAware {
	
	public void addAspectDef(AspectDef aspectDef);
	
	public int getAspectDefSize();
	
	public AspectDef getAspectDef(int index);
}
