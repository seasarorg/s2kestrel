package org.seasar.framework.exception;

/**
 * @author higa
 *
 * NoSuchFieldException�����b�v������s����O�ł��B
 */
public class NoSuchFieldRuntimeException extends SRuntimeException {

	private Class targetClass_;
	private String fieldName_;

	public NoSuchFieldRuntimeException(
		Class targetClass,
		String fieldName,
		NoSuchFieldException cause) {

		super(
			"ESSR0070",
			new Object[] { targetClass.getName(), fieldName },
			cause);
		targetClass_ = targetClass;
		fieldName_ = fieldName;
	}

	public Class getTargetClass() {
		return targetClass_;
	}

	public String getFieldName() {
		return fieldName_;
	}
}
