package org.seasar.framework.container.impl;

import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.container.ArgDef;
import org.seasar.framework.container.AspectDef;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.ContainerConstants;
import org.seasar.framework.container.DestroyMethodDef;
import org.seasar.framework.container.InitMethodDef;
import org.seasar.framework.container.MetaDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.deployer.ComponentDeployer;
import org.seasar.framework.container.deployer.ComponentDeployerFactory;
import org.seasar.framework.container.util.AopProxyUtil;
import org.seasar.framework.container.util.ArgDefSupport;
import org.seasar.framework.container.util.AspectDefSupport;
import org.seasar.framework.container.util.AutoBindingUtil;
import org.seasar.framework.container.util.DestroyMethodDefSupport;
import org.seasar.framework.container.util.InitMethodDefSupport;
import org.seasar.framework.container.util.InstanceModeUtil;
import org.seasar.framework.container.util.MetaDefSupport;
import org.seasar.framework.container.util.PropertyDefSupport;

/**
 * @author higa
 *  
 */
public class ComponentDefImpl implements ComponentDef, ContainerConstants {

	private Class componentClass_;

	private String componentName_;

	private Class concreteClass_;

	private S2Container container_;

	private String expression_;

	private ArgDefSupport argDefSupport_ = new ArgDefSupport();

	private PropertyDefSupport propertyDefSupport_ = new PropertyDefSupport();

	private InitMethodDefSupport initMethodDefSupport_ = new InitMethodDefSupport();

	private DestroyMethodDefSupport destroyMethodDefSupport_ = new DestroyMethodDefSupport();

	private AspectDefSupport aspectDefSupport_ = new AspectDefSupport();
	
	private MetaDefSupport metaDefSupport_ = new MetaDefSupport();

	private String instanceMode_ = INSTANCE_SINGLETON;

	private String autoBindingMode_ = AUTO_BINDING_AUTO;

	private ComponentDeployer componentDeployer_;

	public ComponentDefImpl() {
	}

	public ComponentDefImpl(Class componentClass) {
		this(componentClass, null);
	}

	public ComponentDefImpl(Class componentClass, String componentName) {
		componentClass_ = componentClass;
		componentName_ = componentName;
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#getComponent()
	 */
	public Object getComponent() {
		return getComponentDeployer().deploy();
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#injectDependency(java.lang.Object)
	 */
	public void injectDependency(Object outerComponent) {
		getComponentDeployer().injectDependency(outerComponent);
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#getComponentClass()
	 */
	public final Class getComponentClass() {
		return componentClass_;
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#getComponentName()
	 */
	public final String getComponentName() {
		return componentName_;
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#getConcreteClass()
	 */
	public synchronized final Class getConcreteClass() {
		if (concreteClass_ == null) {
		    concreteClass_ = AopProxyUtil.getConcreteClass(this);
		}
		return concreteClass_;
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#getContainer()
	 */
	public final S2Container getContainer() {
		return container_;
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#setContainer(org.seasar.framework.container.S2Container)
	 */
	public final void setContainer(S2Container container) {
		container_ = container;
		argDefSupport_.setContainer(container);
		metaDefSupport_.setContainer(container);
		propertyDefSupport_.setContainer(container);
		initMethodDefSupport_.setContainer(container);
		destroyMethodDefSupport_.setContainer(container);
		aspectDefSupport_.setContainer(container);
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#addArgDef(org.seasar.framework.container.ArgDef)
	 */
	public void addArgDef(ArgDef argDef) {
		argDefSupport_.addArgDef(argDef);
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#addPropertyDef(org.seasar.framework.container.PropertyDef)
	 */
	public void addPropertyDef(PropertyDef propertyDef) {
		propertyDefSupport_.addPropertyDef(propertyDef);
	}

	/**
	 * @see org.seasar.framework.container.InitMethodDefAware#addInitMethodDef(org.seasar.framework.container.InitMethodDef)
	 */
	public void addInitMethodDef(InitMethodDef methodDef) {
		initMethodDefSupport_.addInitMethodDef(methodDef);
	}

	/**
	 * @see org.seasar.framework.container.DestroyMethodDefAware#addDestroyMethodDef(org.seasar.framework.container.DestroyMethodDef)
	 */
	public void addDestroyMethodDef(DestroyMethodDef methodDef) {
		destroyMethodDefSupport_.addDestroyMethodDef(methodDef);
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#addAspectDef(org.seasar.framework.container.AspectDef)
	 */
	public synchronized void addAspectDef(AspectDef aspectDef) {
		aspectDefSupport_.addAspectDef(aspectDef);
		concreteClass_ = null;
	}

	/**
	 * @see org.seasar.framework.container.ArgDefAware#getArgDefSize()
	 */
	public int getArgDefSize() {
		return argDefSupport_.getArgDefSize();
	}

	/**
	 * @see org.seasar.framework.container.PropertyDefAware#getPropertyDefSize()
	 */
	public int getPropertyDefSize() {
		return propertyDefSupport_.getPropertyDefSize();
	}

	/**
	 * @see org.seasar.framework.container.InitMethodDefAware#getInitMethodDefSize()
	 */
	public int getInitMethodDefSize() {
		return initMethodDefSupport_.getInitMethodDefSize();
	}

	/**
	 * @see org.seasar.framework.container.DestroyMethodDefAware#getDestroyMethodDefSize()
	 */
	public int getDestroyMethodDefSize() {
		return destroyMethodDefSupport_.getDestroyMethodDefSize();
	}

	/**
	 * @see org.seasar.framework.container.AspectDefAware#getAspectDefSize()
	 */
	public int getAspectDefSize() {
		return aspectDefSupport_.getAspectDefSize();
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#getInstanceMode()
	 */
	public String getInstanceMode() {
		return instanceMode_;
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#setInstanceMode(java.lang.String)
	 */
	public void setInstanceMode(String instanceMode) {
		if (InstanceModeUtil.isSingleton(instanceMode)
				|| InstanceModeUtil.isPrototype(instanceMode)
				|| InstanceModeUtil.isRequest(instanceMode)
				|| InstanceModeUtil.isSession(instanceMode)
				|| InstanceModeUtil.isOuter(instanceMode)) {

			instanceMode_ = instanceMode;
		} else {
			throw new IllegalArgumentException(instanceMode);
		}
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#getAutoBindingMode()
	 */
	public String getAutoBindingMode() {
		return autoBindingMode_;
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#setAutoBindingMode(java.lang.String)
	 */
	public void setAutoBindingMode(String autoBindingMode) {
		if (AutoBindingUtil.isAuto(autoBindingMode)
				|| AutoBindingUtil.isConstructor(autoBindingMode)
				|| AutoBindingUtil.isProperty(autoBindingMode)
				|| AutoBindingUtil.isNone(autoBindingMode)) {

			autoBindingMode_ = autoBindingMode;
		} else {
			throw new IllegalArgumentException(autoBindingMode);
		}
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#init()
	 */
	public void init() {
		getComponentDeployer().init();
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#destroy()
	 */
	public void destroy() {
		getComponentDeployer().destroy();
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#getExpression()
	 */
	public String getExpression() {
		return expression_;
	}

	/**
	 * @see org.seasar.framework.container.ComponentDef#setExpression(java.lang.String)
	 */
	public void setExpression(String expression) {
		expression_ = expression;
	}

	/**
	 * @see org.seasar.framework.container.ArgDefAware#getArgDef(int)
	 */
	public ArgDef getArgDef(int index) {
		return argDefSupport_.getArgDef(index);
	}

	/**
	 * @see org.seasar.framework.container.PropertyDefAware#getPropertyDef(int)
	 */
	public PropertyDef getPropertyDef(int index) {
		return propertyDefSupport_.getPropertyDef(index);
	}

	/**
	 * @see org.seasar.framework.container.PropertyDefAware#getPropertyDef(java.lang.String)
	 */
	public PropertyDef getPropertyDef(String propertyName) {
		if (hasPropertyDef(propertyName)) {
			return propertyDefSupport_.getPropertyDef(propertyName);
		} else {
			throw new PropertyNotFoundRuntimeException(componentClass_,
					propertyName);
		}
	}

	/**
	 * @see org.seasar.framework.container.PropertyDefAware#hasPropertyDef(java.lang.String)
	 */
	public boolean hasPropertyDef(String propertyName) {
		return propertyDefSupport_.hasPropertyDef(propertyName);
	}

	/**
	 * @see org.seasar.framework.container.InitMethodDefAware#getInitMethodDef(int)
	 */
	public InitMethodDef getInitMethodDef(int index) {
		return initMethodDefSupport_.getInitMethodDef(index);
	}

	/**
	 * @see org.seasar.framework.container.DestroyMethodDefAware#getDestroyMethodDef(int)
	 */
	public DestroyMethodDef getDestroyMethodDef(int index) {
		return destroyMethodDefSupport_.getDestroyMethodDef(index);
	}

	/**
	 * @see org.seasar.framework.container.AspectDefAware#getAspectDef(int)
	 */
	public AspectDef getAspectDef(int index) {
		return aspectDefSupport_.getAspectDef(index);
	}

	/**
	 * @see org.seasar.framework.container.MetaDefAware#addMetaDef(org.seasar.framework.container.MetaDef)
	 */
	public void addMetaDef(MetaDef metaDef) {
		metaDefSupport_.addMetaDef(metaDef);
	}
	
	/**
	 * @see org.seasar.framework.container.MetaDefAware#getMetaDef(int)
	 */
	public MetaDef getMetaDef(int index) {
		return metaDefSupport_.getMetaDef(index);
	}
	
	/**
	 * @see org.seasar.framework.container.MetaDefAware#getMetaDef(java.lang.String)
	 */
	public MetaDef getMetaDef(String name) {
		return metaDefSupport_.getMetaDef(name);
	}
	
	/**
	 * @see org.seasar.framework.container.MetaDefAware#getMetaDefs(java.lang.String)
	 */
	public MetaDef[] getMetaDefs(String name) {
		return metaDefSupport_.getMetaDefs(name);
	}
	
	/**
	 * @see org.seasar.framework.container.MetaDefAware#getMetaDefSize()
	 */
	public int getMetaDefSize() {
		return metaDefSupport_.getMetaDefSize();
	}

	private synchronized ComponentDeployer getComponentDeployer() {
		if (componentDeployer_ == null) {
			componentDeployer_ = ComponentDeployerFactory.create(this);
		}
		return componentDeployer_;
	}
}