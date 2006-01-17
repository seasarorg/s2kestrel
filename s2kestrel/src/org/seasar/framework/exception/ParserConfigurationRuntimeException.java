package org.seasar.framework.exception;

import javax.xml.parsers.ParserConfigurationException;


/**
 * @author higa
 *
 * ParserConfigurationException�����b�v������s����O�ł��B
 */
public final class ParserConfigurationRuntimeException
	extends SRuntimeException {

	public ParserConfigurationRuntimeException(ParserConfigurationException cause) {
		super("ESSR0053", new Object[] { cause }, cause);
	}
}
