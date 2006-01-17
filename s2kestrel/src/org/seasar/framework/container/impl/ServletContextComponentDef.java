package org.seasar.framework.container.impl;

import javax.servlet.ServletContext;

import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 */
public class ServletContextComponentDef extends SimpleComponentDef {

	private S2Container container_;
	
	public ServletContextComponentDef(S2Container container) {
		super(ServletContext.class);
		container_ = container;
	}
	
	public S2Container getRoot() {
		return container_.getRoot();
	}
	
	/**
	 * @see org.seasar.framework.container.ComponentDef#getComponent()
	 */
	public Object getComponent() {
		return getRoot().getServletContext();
	}
}
