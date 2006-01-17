package org.seasar.framework.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author higa
 *
 */
public final class ReaderUtil {

	private static final int BUF_SIZE = 8192;
	
	private ReaderUtil() {
	}

	public static String readText(Reader reader) {
		BufferedReader in = new BufferedReader(reader);
		StringBuffer out = new StringBuffer(100);
		try {
			try {
				char[] buf = new char[BUF_SIZE];
				int n;
				while ((n = in.read(buf)) >= 0) {
					out.append(buf, 0, n);
				}
			} finally {
				in.close();
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
		return out.toString();
	}

}
