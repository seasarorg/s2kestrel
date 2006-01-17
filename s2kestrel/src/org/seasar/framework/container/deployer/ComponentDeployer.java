package org.seasar.framework.container.deployer;

/**
 * @author higa
 *
 */
public interface ComponentDeployer {

	public Object deploy();
	
	public void injectDependency(Object outerComponent);
	
	public void init();
	
	public void destroy();
}
