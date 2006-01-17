package org.seasar.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author higa
 *
 */
public final class PropertiesUtil {

	private PropertiesUtil() {
	}

	public static void load(Properties props, InputStream in) {
		try {
			props.load(in);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
