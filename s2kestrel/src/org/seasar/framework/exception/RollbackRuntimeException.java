package org.seasar.framework.exception;

import javax.transaction.RollbackException;


/**
 * @author higa
 *
 * RollbackException�����b�v������s����O�ł��B
 */
public final class RollbackRuntimeException extends SRuntimeException {

	public RollbackRuntimeException(RollbackException cause) {
		super("ESSR0063", new Object[]{cause}, cause);
	}
}
