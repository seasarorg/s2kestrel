package org.seasar.framework.exception;

import org.seasar.framework.util.MethodUtil;

/**
 * @author higa
 *
 * NoSuchMethodExceptionをラップする実行時例外です。
 */
public class NoSuchMethodRuntimeException extends SRuntimeException {

	private Class targetClass_;
	private String methodName_;
	private Class[] argTypes_;

	public NoSuchMethodRuntimeException(
		Class targetClass,
		String methodName,
		Class[] argTypes,
		NoSuchMethodException cause) {

		super(
			"ESSR0057",
			new Object[] { targetClass.getName(),
				MethodUtil.getSignature(methodName, argTypes), cause},
			cause);
		targetClass_ = targetClass;
		methodName_ = methodName;
		argTypes_ = argTypes;
	}
	
	public Class getTargetClass() {
		return targetClass_;
	}
	
	public String getMethodName() {
		return methodName_;
	}
	
	public Class[] getArgTypes() {
		return argTypes_;
	}
	
}
