package org.seasar.framework.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.SAXRuntimeException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author higa
 *  
 */
public final class DocumentBuilderUtil {

	private DocumentBuilderUtil() {
	}

	public static Document parse(DocumentBuilder builder, InputStream is) {
		try {
			return builder.parse(is);
		} catch (SAXException e) {
			throw new SAXRuntimeException(e);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}