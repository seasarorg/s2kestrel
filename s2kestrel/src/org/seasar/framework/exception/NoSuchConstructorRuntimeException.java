package org.seasar.framework.exception;

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.MethodUtil;

/**
 * @author higa
 *
 * �R���X�g���N�^��������Ȃ��ꍇ��NoSuchMethodException��
 * ���b�v������s����O�ł��B
 */
public class NoSuchConstructorRuntimeException extends SRuntimeException {

	private Class targetClass_;
	private Class[] argTypes_;

	public NoSuchConstructorRuntimeException(
		Class targetClass,
		Class[] argTypes,
		NoSuchMethodException cause) {

		super(
			"ESSR0064",
			new Object[] { targetClass.getName(),
				MethodUtil.getSignature(ClassUtil.getShortClassName(targetClass), argTypes), cause},
			cause);
		targetClass_ = targetClass;
		argTypes_ = argTypes;
	}
	
	public Class getTargetClass() {
		return targetClass_;
	}
	
	public Class[] getArgTypes() {
		return argTypes_;
	}
}
