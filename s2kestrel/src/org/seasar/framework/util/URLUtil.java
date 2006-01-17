package org.seasar.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author higa
 *
 */
public class URLUtil {

	private URLUtil() {
	}

	public static InputStream openStream(URL url) {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
	
	public static URLConnection openConnection(URL url) {
		try {
			return url.openConnection();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
	
	public static URL create(String spec) {
		try {
			return new URL(spec);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
