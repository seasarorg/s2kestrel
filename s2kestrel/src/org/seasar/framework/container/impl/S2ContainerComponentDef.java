package org.seasar.framework.container.impl;

import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 */
public class S2ContainerComponentDef extends SimpleComponentDef {

	public S2ContainerComponentDef(S2Container container, String name) {
		super(container, name);
	}
	
	public S2Container getContainer() {
		return (S2Container) super.getComponent();
	}
	
	/**
	 * @see org.seasar.framework.container.ComponentDef#getComponent()
	 */
	public Object getComponent() {
		return getContainer();
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#init()
	 */
	public void init() {
		getContainer().init();
	}
	
	/**
	 * @see org.seasar.framework.container.ComponentDef#destroy()
	 */
	public void destroy() {
		getContainer().destroy();
	}
}
