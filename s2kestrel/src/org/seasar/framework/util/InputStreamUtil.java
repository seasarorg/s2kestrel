package org.seasar.framework.util;

import java.io.IOException;
import java.io.InputStream;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author higa
 *  
 */
public final class InputStreamUtil {

	private InputStreamUtil() {
	}

	public static void close(InputStream is) {
		try {
			is.close();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}