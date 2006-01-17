package org.seasar.framework.container.impl;

import org.seasar.framework.container.DestroyMethodDef;

/**
 * @author higa
 *
 */
public class DestroyMethodDefImpl
	extends MethodDefImpl
	implements DestroyMethodDef {

	public DestroyMethodDefImpl() {
	}

	public DestroyMethodDefImpl(String methodName) {
		super(methodName);
	}

}
