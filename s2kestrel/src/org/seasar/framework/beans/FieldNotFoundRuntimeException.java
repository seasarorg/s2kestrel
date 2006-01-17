package org.seasar.framework.beans;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * �N���X��publid static�ȃt�B�[���h��������Ȃ������Ƃ��̎��s����O
 */
public class FieldNotFoundRuntimeException
	extends SRuntimeException {

	private Class targetClass_;
	private String fieldName_;

	public FieldNotFoundRuntimeException(
		Class componentClass,
		String fieldName) {
		super(
			"ESSR0070",
			new Object[] { componentClass.getName(), fieldName});
		targetClass_ = componentClass;
		fieldName_ = fieldName;
	}

	public Class getTargetClass() {
		return targetClass_;
	}
	
	public String getFieldName() {
		return fieldName_;
	}
}