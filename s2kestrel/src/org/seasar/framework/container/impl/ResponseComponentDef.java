package org.seasar.framework.container.impl;

import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 */
public class ResponseComponentDef extends SimpleComponentDef {

	private S2Container container_;
	
	public ResponseComponentDef(S2Container container) {
		super(HttpServletResponse.class);
		container_ = container;
	}
	
	public S2Container getRoot() {
		return container_.getRoot();
	}
	
	/**
	 * @see org.seasar.framework.container.ComponentDef#getComponent()
	 */
	public Object getComponent() {
		return getRoot().getResponse();
	}
}
