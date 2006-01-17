package org.seasar.framework.exception;

import java.text.ParseException;


/**
 * @author higa
 *
 * ParseException�����b�v������s����O�ł��B
 */
public final class ParseRuntimeException extends SRuntimeException {

	public ParseRuntimeException(ParseException cause) {
		super("ESSR0050", new Object[]{cause}, cause);
	}
}
