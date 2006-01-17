package org.seasar.framework.util;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author higa
 *
 */
public final class TextUtil {

	private TextUtil() {
	}

	public static String readText(String path) {
		InputStream is = ResourceUtil.getResourceAsStream(path);
		Reader reader = InputStreamReaderUtil.create(is);
		return ReaderUtil.readText(reader);
	}

}
