package org.seasar.framework.container;


/**
 * @author higa
 *
 * ���\�b�h���`���܂��B
 */
public interface MethodDef extends ArgDefAware {
	
	public String getMethodName();
	
	public Object[] getArgs();
	
	public S2Container getContainer();
	
	public void setContainer(S2Container container);
	
	public String getExpression();
	
	public void setExpression(String expression);
}
