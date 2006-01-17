package org.seasar.framework.container.impl;

import org.seasar.framework.container.InitMethodDef;

/**
 * @author higa
 *
 */
public class InitMethodDefImpl extends MethodDefImpl implements InitMethodDef {

	public InitMethodDefImpl() {
	}
	
	public InitMethodDefImpl(String methodName) {
		super(methodName);
	}

}
