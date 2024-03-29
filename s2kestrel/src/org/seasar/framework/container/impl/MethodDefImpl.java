package org.seasar.framework.container.impl;

import org.seasar.framework.container.ArgDef;
import org.seasar.framework.container.MethodDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.ArgDefSupport;

/**
 * @author higa
 *
 */
public abstract class MethodDefImpl implements MethodDef {

	private String methodName_;
	private ArgDefSupport argDefSupport_ = new ArgDefSupport();
	private S2Container container_;
	private String expression_;

	public MethodDefImpl() {
	}

	public MethodDefImpl(String methodName) {
		methodName_ = methodName;
	}

	/**
	 * @see org.seasar.framework.container.MethodDef#getMethodName()
	 */
	public String getMethodName() {
		return methodName_;
	}

	/**
	 * @see org.seasar.framework.container.MethodDef#addArgDef(org.seasar.framework.container.ArgDef)
	 */
	public void addArgDef(ArgDef argDef) {
		argDefSupport_.addArgDef(argDef);
	}

	/**
	 * @see org.seasar.framework.container.ArgDefAware#getArgDefSize()
	 */
	public int getArgDefSize() {
		return argDefSupport_.getArgDefSize();
	}

	/**
	 * @see org.seasar.framework.container.ArgDefAware#getArgDef(int)
	 */
	public ArgDef getArgDef(int index) {
		return argDefSupport_.getArgDef(index);
	}

	/**
	 * @see org.seasar.framework.container.MethodDef#getArgs()
	 */
	public Object[] getArgs() {
		Object[] args = new Object[getArgDefSize()];
		for (int i = 0; i < getArgDefSize(); ++i) {
			args[i] = getArgDef(i).getValue();
		}
		return args;
	}

	/**
	 * @see org.seasar.framework.container.MethodDef#getContainer()
	 */
	public S2Container getContainer() {
		return container_;
	}

	/**
	 * @see org.seasar.framework.container.MethodDef#setContainer(org.seasar.framework.container.S2Container)
	 */
	public void setContainer(S2Container container) {
		container_ = container;
		argDefSupport_.setContainer(container);
	}

	/**
	 * @see org.seasar.framework.container.MethodDef#getExpression()
	 */
	public String getExpression() {
		return expression_;
	}

	/**
	 * @see org.seasar.framework.container.MethodDef#setExpression(java.lang.String)
	 */
	public void setExpression(String expression) {
		expression_ = expression;
	}

}
