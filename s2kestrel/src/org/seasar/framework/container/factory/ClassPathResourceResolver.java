package org.seasar.framework.container.factory;

import java.io.InputStream;
import java.net.URL;

import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.URLUtil;

/**
 * @author koichik
 */
public class ClassPathResourceResolver implements ResourceResolver {
    public ClassPathResourceResolver() {
    }

    public InputStream getInputStream(final String path) {
        final URL url = ResourceUtil.getResourceNoException(path);
        if (url == null) {
            return null;
        }
        return URLUtil.openStream(url);
    }
}
