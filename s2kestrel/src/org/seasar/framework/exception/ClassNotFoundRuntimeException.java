package org.seasar.framework.exception;


/**
 * @author higa
 *
 * ClassNotFoundException�����b�v������s����O�ł��B
 */
public class ClassNotFoundRuntimeException extends SRuntimeException {

	/**
	 * @param messageCode
	 */
	public ClassNotFoundRuntimeException(ClassNotFoundException cause) {
		super("ESSR0044", new Object[]{cause}, cause);
	}
}
