package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * ��`���ꂽ�R���|�[�l���g�̃N���X�Ǝ��ۂ̃R���|�[�l���g�̃N���X
 * ���قȂ�Ƃ��̎��s����O
 */
public class ClassUnmatchRuntimeException extends SRuntimeException {

	private Class componentClass_;
	private Class realComponentClass_;
	
	/**
	 * @param componentKey
	 */
	public ClassUnmatchRuntimeException(
		Class componentClass,
		Class realComponentClass) {
		super("ESSR0069", new Object[] { componentClass.getName(),
			realComponentClass != null ? realComponentClass.getName() : "null"});
		componentClass_ = componentClass;
		realComponentClass_ = realComponentClass;
	}
	
	public Class getComponentClass() {
		return componentClass_;
	}
	
	public Class getRealComponentClass() {
		return realComponentClass_;
	}
}