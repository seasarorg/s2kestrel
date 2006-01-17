package org.seasar.framework.container;


/**
 * @author higa
 *
 * AspectDef�̐ݒ肪�\�ɂȂ�܂��B
 */
public interface AspectDefAware {
	
	public void addAspectDef(AspectDef aspectDef);
	
	public int getAspectDefSize();
	
	public AspectDef getAspectDef(int index);
}
