package org.seasar.framework.container.deployer;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.CyclicReferenceRuntimeException;

/**
 * @author higa
 *
 */
public class SingletonComponentDeployer extends AbstractComponentDeployer {

	private Object component_;
	private boolean instantiating_ = false;

	/**
	 * @param componentDef
	 */
	public SingletonComponentDeployer(ComponentDef componentDef) {
		super(componentDef);
	}

	/**
	 * @see org.seasar.framework.container.deployer.ComponentDeployer#deploy()
	 */
	public synchronized Object deploy() {
		if (component_ == null) {
			assemble();
		}
		return component_;
	}
	
	public void injectDependency(Object component) {
		throw new UnsupportedOperationException("injectDependency");
	}

	private void assemble() {
		if (instantiating_) {
			throw new CyclicReferenceRuntimeException(
				getComponentDef().getComponentClass());
		}
		instantiating_ = true;
		try {
			component_ = getConstructorAssembler().assemble();
		} finally {
			instantiating_ = false;
		}
		getPropertyAssembler().assemble(component_);
		getInitMethodAssembler().assemble(component_);
	}
	
	/**
	 * @see org.seasar.framework.container.deployer.ComponentDeployer#init()
	 */
	public void init() {
		deploy();
	}
	
	/**
	 * @see org.seasar.framework.container.deployer.ComponentDeployer#destroy()
	 */
	public void destroy() {
		if (component_ == null) {
			return;
		}
		getDestroyMethodAssembler().assemble(component_);
		component_ = null;
	}
}
