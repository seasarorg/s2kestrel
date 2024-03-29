package org.seasar.framework.container;


/**
 * @author higa
 *
 * AspectDefの設定が可能になります。
 */
public interface AspectDefAware {
	
	public void addAspectDef(AspectDef aspectDef);
	
	public int getAspectDefSize();
	
	public AspectDef getAspectDef(int index);
}
