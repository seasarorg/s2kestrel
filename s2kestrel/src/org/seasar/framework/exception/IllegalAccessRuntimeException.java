package org.seasar.framework.exception;


/**
 * @author higa
 *
 * IllegalAccessException�����b�v������s����O�ł��B
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
