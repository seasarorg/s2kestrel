package org.seasar.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author higa
 *
 */
public final class FileOutputStreamUtil {

	private FileOutputStreamUtil() {
	}

	public static FileOutputStream create(File file) {
		try {
			return new FileOutputStream(file);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
