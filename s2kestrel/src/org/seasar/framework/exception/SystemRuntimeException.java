package org.seasar.framework.exception;

import javax.transaction.SystemException;


/**
 * @author higa
 *
 * SystemException�����b�v������s����O�ł��B
 */
public final class SystemRuntimeException extends SRuntimeException {

	public SystemRuntimeException(SystemException cause) {
		super("ESSR0061", new Object[]{cause}, cause);
	}
}
