package org.seasar.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.seasar.framework.exception.IORuntimeException;

/**
 * @author higa
 *
 */
public class JarFileUtil {

	private JarFileUtil() {
	}

	public static InputStream getInputStream(JarFile file, ZipEntry entry) {
		try {
			return file.getInputStream(entry);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}
}
