package org.seasar.framework.container.impl;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 */
public class RequestComponentDef extends SimpleComponentDef {

	private S2Container container_;
	
	public RequestComponentDef(S2Container container) {
		super(HttpServletRequest.class);
		container_ = container;
	}
	
	public S2Container getRoot() {
		return container_.getRoot();
	}
	
	/**
	 * @see org.seasar.framework.container.ComponentDef#getComponent()
	 */
	public Object getComponent() {
		return getRoot().getRequest();
	}
}
