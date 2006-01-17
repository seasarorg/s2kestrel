package org.seasar.framework.container.factory;

/**
 * @author koichik
 */
public interface PathResolver {
    String resolvePath(String context, String path);
}
