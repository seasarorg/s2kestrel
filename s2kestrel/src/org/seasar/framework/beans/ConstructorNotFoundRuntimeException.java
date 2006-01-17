package org.seasar.framework.beans;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * �Ώۂ̃N���X�ɓK�p�\�ȃR���X�g���N�^��������Ȃ������ꍇ�̎��s����O�ł��B
 */
public class ConstructorNotFoundRuntimeException extends SRuntimeException {

	private Class targetClass_;
	private Object[] methodArgs_;

	/**
	 * @param targetClass
	 */
	public ConstructorNotFoundRuntimeException(Class targetClass,
		Object[] methodArgs) {
		super("ESSR0048", new Object[]{targetClass.getName(),
			getSignature(methodArgs)});
		
		targetClass_ = targetClass;
		methodArgs_ = methodArgs;
	}
	
	public Class getTargetClass() {
		return targetClass_;
	}
	
	public Object[] getMethodArgs() {
		return methodArgs_;
	}
	
	private static String getSignature(Object[] methodArgs) {
		StringBuffer buf = new StringBuffer(100);
		if (methodArgs != null) {
			for (int i = 0; i < methodArgs.length; ++i) {
				if (i > 0) {
					buf.append(", ");
				}
				if (methodArgs[i] != null) {
					buf.append(methodArgs[i].getClass().getName());
				} else {
					buf.append("null"); 
				}
			}
		}
		return buf.toString();
	}
}
