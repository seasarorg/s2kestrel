package org.seasar.framework.container.deployer;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ClassUnmatchRuntimeException;

/**
 * @author higa
 *
 */
public class OuterComponentDeployer extends AbstractComponentDeployer {

	/**
	 * @param componentDef
	 */
	public OuterComponentDeployer(ComponentDef componentDef) {
		super(componentDef);
	}

	/**
	 * @see org.seasar.framework.container.deployer.ComponentDeployer#deploy()
	 */
	public Object deploy() {
		throw new UnsupportedOperationException("deploy");
	}
	
	public void injectDependency(Object outerComponent) {
		checkComponentClass(outerComponent);
		getPropertyAssembler().assemble(outerComponent);
		getInitMethodAssembler().assemble(outerComponent);
	}
	
	private void checkComponentClass(Object outerComponent)
		throws ClassUnmatchRuntimeException {
			
		Class componentClass = getComponentDef().getComponentClass();
		if (componentClass == null) {
			return;
		}
		if (!componentClass.isInstance(outerComponent)) {
			throw new ClassUnmatchRuntimeException(componentClass,
				outerComponent.getClass());
		}
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
