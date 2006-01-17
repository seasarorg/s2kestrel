package org.seasar.framework.container.factory;

import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 */
public interface S2ContainerBuilder {

	public S2Container build(String path);
	
	public S2Container build(String path, ClassLoader classLoader);
	
	public S2Container include(S2Container parent, String path);
}
