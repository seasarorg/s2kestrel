package org.seasar.framework.exception;

import java.text.ParseException;


/**
 * @author higa
 *
 * ParseExceptionをラップする実行時例外です。
 */
public final class ParseRuntimeException extends SRuntimeException {

	public ParseRuntimeException(ParseException cause) {
		super("ESSR0050", new Object[]{cause}, cause);
	}
}
