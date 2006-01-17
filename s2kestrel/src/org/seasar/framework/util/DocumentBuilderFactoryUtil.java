package org.seasar.framework.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.seasar.framework.exception.ParserConfigurationRuntimeException;

/**
 * @author higa
 *  
 */
public final class DocumentBuilderFactoryUtil {

	private DocumentBuilderFactoryUtil() {
	}

	public static DocumentBuilderFactory newInstance() {
		return DocumentBuilderFactory.newInstance();
	}

	public static DocumentBuilder newDocumentBuilder() {
		try {
			return newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ParserConfigurationRuntimeException(e);
		}
	}
}