package org.seasar.framework.util;

import java.io.IOException;

import javax.xml.parsers.SAXParser;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.SAXRuntimeException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author higa
 *
 */
public final class SAXParserUtil {

	private SAXParserUtil() {
	}

	public static void parse(
		SAXParser parser,
		InputSource inputSource,
		DefaultHandler handler) {

		try {
			parser.parse(inputSource, handler);
		} catch (SAXException e) {
			throw new SAXRuntimeException(e);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
