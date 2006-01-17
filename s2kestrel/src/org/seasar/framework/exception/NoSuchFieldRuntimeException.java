package org.seasar.framework.exception;

/**
 * @author higa
 *
 * NoSuchFieldExceptionをラップする実行時例外です。
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
