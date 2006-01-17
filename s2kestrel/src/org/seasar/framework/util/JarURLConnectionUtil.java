package org.seasar.framework.util;

import java.io.IOException;
import java.net.JarURLConnection;
import java.util.jar.JarFile;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author higa
 *
 */
public class JarURLConnectionUtil {

	private JarURLConnectionUtil() {
	}

	public static JarFile getJarFile(JarURLConnection conn) {
		try {
			return conn.getJarFile();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
