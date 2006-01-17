package org.seasar.framework.container;

/**
 * @author higa
 *
 * コンポーネントを定義します。
 */
public interface ComponentDef
	extends
		ArgDefAware,
		PropertyDefAware,
		InitMethodDefAware,
		DestroyMethodDefAware,
		AspectDefAware,
		MetaDefAware {

	public Object getComponent()
		throws TooManyRegistrationRuntimeException, CyclicReferenceRuntimeException;
		
	public void injectDependency(Object outerComponent);

	public S2Container getContainer();

	public void setContainer(S2Container container);

	public Class getComponentClass();

	public String getComponentName();

	public Class getConcreteClass();

	public String getAutoBindingMode();

	public void setAutoBindingMode(String mode);

	public String getInstanceMode();

	public void setInstanceMode(String mode);
	
	public String getExpression();

	public void setExpression(String expression);

	public void init();

	public void destroy();
}
