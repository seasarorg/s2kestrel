package org.seasar.framework.exception;

import org.xml.sax.SAXException;

/**
 * @author higa
 *
 * SAXException�����b�v������s����O�ł��B
 */
public final class SAXRuntimeException extends SRuntimeException {

	public SAXRuntimeException(SAXException cause) {
		super("ESSR0054", new Object[] { cause }, cause);
	}
}
