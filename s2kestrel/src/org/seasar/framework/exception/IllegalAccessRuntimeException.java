package org.seasar.framework.exception;


/**
 * @author higa
 *
 * IllegalAccessExceptionをラップする実行時例外です。
 */
public final class IllegalAccessRuntimeException extends SRuntimeException {

	private Class targetClass_;

	public IllegalAccessRuntimeException(
		Class targetClass,
		IllegalAccessException cause) {

		super("ESSR0042", new Object[] { targetClass.getName(), cause }, cause);
		targetClass_ = targetClass;
	}

	public Class getTargetClass() {
		return targetClass_;
	}
}
