package org.seasar.framework.exception;

import ognl.OgnlException;

/**
 * @author higa
 *
 * OgnlExceptionをラップする実行時例外です。
 */
public final class OgnlRuntimeException extends SRuntimeException {

	public OgnlRuntimeException(OgnlException cause) {
		super("ESSR0073", new Object[] { cause }, cause);
	}
}
