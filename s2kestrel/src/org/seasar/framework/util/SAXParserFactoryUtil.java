package org.seasar.framework.util;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.seasar.framework.exception.ParserConfigurationRuntimeException;
import org.seasar.framework.exception.SAXRuntimeException;
import org.xml.sax.SAXException;

/**
 * @author higa
 *
 */
public final class SAXParserFactoryUtil {

	private SAXParserFactoryUtil() {
	}

	public static SAXParserFactory newInstance() {
		return SAXParserFactory.newInstance();
	}

	public static SAXParser newSAXParser() {
		return newSAXParser(newInstance());
	}
	
	public static SAXParser newSAXParser(SAXParserFactory factory) {
		try {
			return factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			throw new ParserConfigurationRuntimeException(e);
		} catch (SAXException e) {
			throw new SAXRuntimeException(e);
		}
	}
}
