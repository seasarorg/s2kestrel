package org.seasar.framework.exception;

import javax.naming.NamingException;

/**
 * @author higa
 *
 * NamingExceptionをラップする実行時例外です。
 */
public final class NamingRuntimeException extends SRuntimeException {

	public NamingRuntimeException(NamingException cause) {
		super("ESSR0066", new Object[] { cause }, cause);
	}
}
