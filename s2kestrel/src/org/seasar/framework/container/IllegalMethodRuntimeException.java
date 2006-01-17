package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * コンポーネントのメソッドの引数の設定に失敗ときの実行時例外
 */
public class IllegalMethodRuntimeException
	extends SRuntimeException {

	private Class componentClass_;
	private String methodName_;

	public IllegalMethodRuntimeException(
		Class componentClass,
		String methodName,
		Throwable cause) {
		super(
			"ESSR0060",
			new Object[] { componentClass.getName(), methodName, cause },
			cause);
		componentClass_ = componentClass;
		methodName_ = methodName;
	}

	public Class getComponentClass() {
		return componentClass_;
	}
	
	public String getMethodName() {
		return methodName_;
	}
}