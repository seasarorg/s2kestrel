package org.seasar.framework.container.impl;

import javax.servlet.http.HttpSession;

import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *  
 */
public class SessionComponentDef extends SimpleComponentDef {

	private S2Container container_;

	public SessionComponentDef(S2Container container) {
		super(HttpSession.class);
		container_ = container;
	}

	public S2Container getRoot() {
		return container_.getRoot();
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#getComponent()
	 */
	public Object getComponent() {
		return getRoot().getSession();
	}
}