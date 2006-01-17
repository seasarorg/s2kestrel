package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * �R���|�[�l���g�̏z�Q�Ƃ��N�����Ƃ��̎��s����O
 */
public class CyclicReferenceRuntimeException extends SRuntimeException {

	private Class componentClass_;
	
	/**
	 * @param componentClasses
	 */
	public CyclicReferenceRuntimeException(
		Class componentClass) {
		super(
			"ESSR0047",
			new Object[] {
				componentClass.getName()});
		componentClass_ = componentClass;
	}
	
	public Class getComponentClass() {
		return componentClass_;
	}
}