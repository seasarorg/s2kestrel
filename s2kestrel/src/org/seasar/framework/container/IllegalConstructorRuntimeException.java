package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * �R���|�[�l���g�̃R���X�g���N�^�����̐ݒ�Ɏ��s�Ƃ��̎��s����O
 */
public class IllegalConstructorRuntimeException
	extends SRuntimeException {

	private Class componentClass_;

	public IllegalConstructorRuntimeException(
		Class componentClass,
		Throwable cause) {
		super(
			"ESSR0058",
			new Object[] { componentClass.getName(), cause },
			cause);
		componentClass_ = componentClass;
	}

	public Class getComponentClass() {
		return componentClass_;
	}
}