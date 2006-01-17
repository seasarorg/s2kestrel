package org.seasar.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author higa
 *  
 */
public final class FileInputStreamUtil {

	private FileInputStreamUtil() {
	}

	public static FileInputStream create(File file) {
		try {
			return new FileInputStream(file);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}