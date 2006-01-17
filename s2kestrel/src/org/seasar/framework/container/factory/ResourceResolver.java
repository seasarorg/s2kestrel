package org.seasar.framework.container.factory;

import java.io.InputStream;

/**
 * @author koichik
 */
public interface ResourceResolver {
    InputStream getInputStream(String path);
}
