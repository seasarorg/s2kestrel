package org.seasar.framework.exception;

import ognl.OgnlException;

/**
 * @author higa
 *
 * OgnlException�����b�v������s����O�ł��B
 */
public final class OgnlRuntimeException extends SRuntimeException {

	public OgnlRuntimeException(OgnlException cause) {
		super("ESSR0073", new Object[] { cause }, cause);
	}
}
