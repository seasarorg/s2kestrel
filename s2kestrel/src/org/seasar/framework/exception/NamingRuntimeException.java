package org.seasar.framework.exception;

import javax.naming.NamingException;

/**
 * @author higa
 *
 * NamingException�����b�v������s����O�ł��B
 */
public final class NamingRuntimeException extends SRuntimeException {

	public NamingRuntimeException(NamingException cause) {
		super("ESSR0066", new Object[] { cause }, cause);
	}
}
