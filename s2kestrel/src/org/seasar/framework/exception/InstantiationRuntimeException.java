package org.seasar.framework.exception;


/**
 * @author higa
 *
 * InstantiationException�����b�v������s����O�ł��B
 */
public class InstantiationRuntimeException extends SRuntimeException {

	private Class targetClass_;

	public InstantiationRuntimeException(
		Class targetClass,
		InstantiationException cause) {

		super("ESSR0041", new Object[] { targetClass.getName(), cause }, cause);
		targetClass_ = targetClass;
	}

	public Class getTargetClass() {
		return targetClass_;
	}
}
