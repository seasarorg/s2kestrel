package org.seasar.framework.container.factory;

/**
 * @author koichik
 */
public class SimplePathResolver implements PathResolver {
    public String resolvePath(String context, String path) {
        return path;
    }
}
