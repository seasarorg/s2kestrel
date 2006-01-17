package org.seasar.framework.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import org.seasar.framework.exception.IORuntimeException;

/**
 * 
 * @author higa
 * @author Satoshi Kimura
 */
public final class ResourceUtil {

	private ResourceUtil() {
	}

	public static String getResourcePath(String path, String extension) {
		if (extension == null) {
			return path;
		}
		extension = "." + extension;
		if (path.endsWith(extension)) {
			return path;
		}
		return path.replace('.', '/') + extension;
	}
	
	public static String getResourcePath(Class clazz) {
		return clazz.getName().replace('.', '/') + ".class";
	}
	
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static URL getResource(String path) {
		return getResource(path, null);
	}

	public static URL getResource(String path, String extension)
		throws ResourceNotFoundRuntimeException {

		URL url = getResourceNoException(path, extension);
		if (url != null) {
			return url;
		} else {
			throw new ResourceNotFoundRuntimeException(
				getResourcePath(path, extension));
		}
	}
	
	public static URL getResourceNoException(String path) {
		return getResourceNoException(path, null);
	}

	public static URL getResourceNoException(String path, String extension) {
		path = getResourcePath(path, extension);
		return getClassLoader().getResource(path);
	}
	
	public static InputStream getResourceAsStream(String path) {
		return getResourceAsStream(path, null);
	}
		
	public static InputStream getResourceAsStream(String path, String extension) {
		URL url = getResource(path, extension);
		return URLUtil.openStream(url);
	}

	public static boolean isExist(String path) {
		return getResourceNoException(path) != null;
	}
	
	public static Properties getProperties(String path) {
		Properties props = new Properties();
		InputStream is = getResourceAsStream(path);
		try {
			props.load(is);
			return props;
		} catch (IOException ex) {
			throw new IORuntimeException(ex);
		}
	}
	
	public static String getExtension(String path) {
		int extPos = path.lastIndexOf(".");
		if (extPos >= 0) {
			return path.substring(extPos + 1);
		} else {
			return null;
		}
	}
	
	public static String removeExtension(String path) {
		int extPos = path.lastIndexOf(".");
		if (extPos >= 0) {
			return path.substring(0, extPos);
		} else {
			return path;
		}
	}

	public static File getBuildDir(Class clazz) {
		URL url = getResource(getResourcePath(clazz));
		int num = StringUtil.split(clazz.getName(), ".").length;
		File file = new File(getFileName(url));
		for (int i = 0; i < num; ++i, file = file.getParentFile()) {
		}
		return file;
	}
	
	public static String toExternalForm(URL url) {
		String s = url.toExternalForm();
		return URLDecoder.decode(s);
	}
	
	public static String getFileName(URL url) {
		String s = url.getFile();
		return URLDecoder.decode(s);
	}
	
	public static File getFile(URL url) {
		return new File(getFileName(url));
	}
	
	public static File getResourceAsFile(String path) {
		return getResourceAsFile(path, null);
	}
	
	public static File getResourceAsFile(String path, String extension) {
		return getFile(getResource(path, extension));
	}
}