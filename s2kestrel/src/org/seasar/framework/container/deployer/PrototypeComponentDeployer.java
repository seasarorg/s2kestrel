package org.seasar.framework.container.deployer;

import org.seasar.framework.container.ComponentDef;

/**
 * @author higa
 *
 */
public class PrototypeComponentDeployer extends AbstractComponentDeployer {

	/**
	 * @param componentDef
	 */
	public PrototypeComponentDeployer(ComponentDef componentDef) {
		super(componentDef);
	}

	/**
	 * @see org.seasar.framework.container.deployer.ComponentDeployer#deploy()
	 */
	public Object deploy() {
		Object component = getConstructorAssembler().assemble();
		getPropertyAssembler().assemble(component);
		getInitMethodAssembler().assemble(component);
		return component;
	}
	
	public void injectDependency(Object component) {
		throw new UnsupportedOperationException("injectDependency");
	}
	
	/**
	 * @see org.seasar.framework.container.deployer.ComponentDeployer#init()
	 */
	public void init() {
	}
	
	/**
	 * @see org.seasar.framework.container.deployer.ComponentDeployer#destroy()
	 */
	public void destroy() {
	}
}
