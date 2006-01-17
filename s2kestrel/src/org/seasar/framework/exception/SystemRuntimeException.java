package org.seasar.framework.exception;

import javax.transaction.SystemException;


/**
 * @author higa
 *
 * SystemExceptionをラップする実行時例外です。
 */
public final class SystemRuntimeException extends SRuntimeException {

	public SystemRuntimeException(SystemException cause) {
		super("ESSR0061", new Object[]{cause}, cause);
	}
}
