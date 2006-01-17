package org.seasar.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author higa
 *  
 */
public final class InputStreamReaderUtil {

	private InputStreamReaderUtil() {
	}

	public static InputStreamReader create(InputStream is) {
		return create(is, "JISAutoDetect");
	}

	public static InputStreamReader create(InputStream is, String encoding) {
		try {
			return new InputStreamReader(is, encoding);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}