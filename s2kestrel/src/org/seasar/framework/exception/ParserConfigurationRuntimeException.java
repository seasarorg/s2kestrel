package org.seasar.framework.exception;

import javax.xml.parsers.ParserConfigurationException;


/**
 * @author higa
 *
 * ParserConfigurationExceptionをラップする実行時例外です。
 */
public final class ParserConfigurationRuntimeException
	extends SRuntimeException {

	public ParserConfigurationRuntimeException(ParserConfigurationException cause) {
		super("ESSR0053", new Object[] { cause }, cause);
	}
}
