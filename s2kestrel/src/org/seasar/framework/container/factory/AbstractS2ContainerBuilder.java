package org.seasar.framework.container.factory;

import java.io.InputStream;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.ResourceNotFoundRuntimeException;

/**
 * @author koichik
 */
public abstract class AbstractS2ContainerBuilder implements S2ContainerBuilder {
    protected ResourceResolver resourceResolver_ = new ClassPathResourceResolver();

    public ResourceResolver getResourceResolver() {
        return resourceResolver_;
    }

    public void setResourceResolver(final ResourceResolver resourceResolver) {
        resourceResolver_ = resourceResolver;
    }

    public S2Container build(final String path, final ClassLoader classLoader) {
        final ClassLoader oldLoader = Thread.currentThread().getContextClassLoader();
        try {
            if (classLoader != null) {
                Thread.currentThread().setContextClassLoader(classLoader);
            }
            return build(path);
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldLoader);
        }
    }

    protected InputStream getInputStream(final String path) {
        final InputStream is = resourceResolver_.getInputStream(path);
        if (is == null) {
            throw new ResourceNotFoundRuntimeException(path);
        }
        return is;
    }
}
