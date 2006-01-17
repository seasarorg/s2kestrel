package org.seasar.framework.exception;

import javax.transaction.RollbackException;


/**
 * @author higa
 *
 * RollbackExceptionをラップする実行時例外です。
 */
public final class RollbackRuntimeException extends SRuntimeException {

	public RollbackRuntimeException(RollbackException cause) {
		super("ESSR0063", new Object[]{cause}, cause);
	}
}
