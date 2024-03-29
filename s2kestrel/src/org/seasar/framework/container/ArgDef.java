package org.seasar.framework.container;

/**
 * @author higa
 *
 * 引数を定義します。
 */
public interface ArgDef extends MetaDefAware {
	
	public Object getValue();
	
	public S2Container getContainer();
	
	public void setContainer(S2Container container);
	
	public String getExpression();

	public void setExpression(String str);
	
	public void setChildComponentDef(ComponentDef componentDef);

}
