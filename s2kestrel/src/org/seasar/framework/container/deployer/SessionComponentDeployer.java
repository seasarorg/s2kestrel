package org.seasar.framework.container.deployer;

import javax.servlet.http.HttpSession;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * @author higa
 *  
 */
public class SessionComponentDeployer extends AbstractComponentDeployer {

	/**
	 * @param componentDef
	 */
	public SessionComponentDeployer(ComponentDef componentDef) {
		super(componentDef);
	}

	/**
	 * @see org.seasar.framework.container.deployer.ComponentDeployer#deploy()
	 */
	public Object deploy() {
		ComponentDef cd = getComponentDef();
		HttpSession session = cd.getContainer().getRoot().getSession();
		if (session == null) {
			throw new EmptyRuntimeException("session");
		}
		String componentName = cd.getComponentName();
		if (componentName == null) {
			throw new EmptyRuntimeException("componentName");
		}
		Object component = session.getAttribute(componentName);
		if (component != null) {
			return component;
		}
		component = getConstructorAssembler().assemble();
		session.setAttribute(componentName, component);
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