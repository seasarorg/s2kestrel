package org.seasar.framework.exception;


/**
 * @author higa
 *
 * ClassNotFoundExceptionをラップする実行時例外です。
 */
public class ClassNotFoundRuntimeException extends SRuntimeException {

	/**
	 * @param messageCode
	 */
	public ClassNotFoundRuntimeException(ClassNotFoundException cause) {
		super("ESSR0044", new Object[]{cause}, cause);
	}
}
