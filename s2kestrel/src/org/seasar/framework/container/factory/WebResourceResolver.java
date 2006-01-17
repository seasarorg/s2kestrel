package org.seasar.framework.container.factory;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.seasar.framework.util.URLUtil;

/**
 * @author koichik
 */
public class WebResourceResolver implements ResourceResolver {
    protected ResourceResolver parent_;

    public WebResourceResolver() {
        this(new ClassPathResourceResolver());
    }

    public WebResourceResolver(final ResourceResolver parent) {
        parent_ = parent;
    }

    public InputStream getInputStream(final String path) {
        try {
            if (parent_ != null) {
                InputStream is = parent_.getInputStream(path);
                if (is != null) {
                    return is;
                }
            }

            URL url = SingletonS2ContainerFactory.getServletContext().getResource(path);
            if (url == null) {
                final StringBuffer buf = new StringBuffer(path.length() + 10);
                buf.append("/WEB-INF");
                if (!path.startsWith("/")) {
                    buf.append("/");
                }
                buf.append(path);
                url = SingletonS2ContainerFactory.getServletContext().getResource(new String(buf));
            }
            if (url == null) {
                return null;
            }
            return URLUtil.openStream(url);
        }
        catch (final MalformedURLException e) {
            return null;
        }
    }
}
