package org.seasar.framework.beans;

import org.seasar.framework.exception.SRuntimeException;
import org.seasar.framework.util.MethodUtil;

/**
 * @author higa
 *
 * 対象のクラスに適用可能なメソッドが見つからなかった場合の実行時例外です。
 */
public final class MethodNotFoundRuntimeException extends SRuntimeException {

	private Class targetClass_;
	private String methodName_;
	private Class[] methodArgClasses_;

	public MethodNotFoundRuntimeException(
		Class targetClass,
		String methodName,
		Object[] methodArgs) {

		super(
			"ESSR0049",
			new Object[] {
				targetClass.getName(),
				MethodUtil.getSignature(methodName, methodArgs)});
		targetClass_ = targetClass;
		methodName_ = methodName;
		if (methodArgs != null) {
			methodArgClasses_ = new Class[methodArgs.length];
			for (int i = 0; i < methodArgs.length; ++i) {
				if (methodArgs[i] != null) {
					methodArgClasses_[i] = methodArgs[i].getClass();
				}
			}
		}

	}

	public MethodNotFoundRuntimeException(
		Class targetClass,
		String methodName,
		Class[] methodArgClasses) {

		super(
			"ESSR0049",
			new Object[] {
				targetClass.getName(),
				MethodUtil.getSignature(methodName, methodArgClasses)});
		targetClass_ = targetClass;
		methodName_ = methodName;
		methodArgClasses_ = methodArgClasses;
	}

	public Class getTargetClass() {
		return targetClass_;
	}

	public String getMethodName() {
		return methodName_;
	}

	public Class[] getMethodArgClasses() {
		return methodArgClasses_;
	}

}
