package org.seasar.framework.container.deployer;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;

/**
 * @author higa
 *  
 */
public class RequestComponentDeployer extends AbstractComponentDeployer {

	private static Logger logger_ = Logger.getLogger(RequestComponentDeployer.class);
	/**
	 * @param componentDef
	 */
	public RequestComponentDeployer(ComponentDef componentDef) {
		super(componentDef);
	}

	/**
	 * @see org.seasar.framework.container.deployer.ComponentDeployer#deploy()
	 */
	public Object deploy() {
		ComponentDef cd = getComponentDef();
		HttpServletRequest request = cd.getContainer().getRoot().getRequest();
		if (request == null) {
			RuntimeException re = new EmptyRuntimeException("request");
			logger_.log(re);
			throw re;
		}
		String componentName = cd.getComponentName();
		if (componentName == null) {
			componentName = ClassUtil.getShortClassName(cd.getComponentClass());
			componentName = StringUtil.decapitalize(componentName);
		}
		Object component = request.getAttribute(componentName);
		if (component != null) {
			return component;
		}
		component = getConstructorAssembler().assemble();
		request.setAttribute(componentName, component);
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