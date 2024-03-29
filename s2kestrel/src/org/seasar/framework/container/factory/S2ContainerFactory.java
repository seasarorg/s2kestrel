package org.seasar.framework.container.factory;

import java.util.HashSet;
import java.util.Set;

import org.seasar.framework.container.ExtensionNotFoundRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.assembler.AssemblerFactory;
import org.seasar.framework.container.deployer.ComponentDeployerFactory;
import org.seasar.framework.container.impl.S2ContainerBehavior;
import org.seasar.framework.util.ResourceUtil;

/**
 * @author higa
 * @author Satoshi Kimura
 *  
 */
public final class S2ContainerFactory {
    public static final String FACTORY_CONFIG_KEY = "org.seasar.framework.container.factory.config";
    public static final String FACTORY_CONFIG_PATH = "s2container.dicon";
    public static final String DEFAULT_BUILDER_NAME = "defaultBuilder";

    protected static S2Container configurationContainer_;
    protected static Provider provider_ = new DefaultProvider();
    protected static S2ContainerBuilder defaultBuilder_ = new XmlS2ContainerBuilder();
    protected static ThreadLocal processingPaths_ = new ThreadLocal() {
        protected Object initialValue() {
            return new HashSet();
        }
    };

    static {
        configure();
    }

    public static Provider getProvider() {
        return provider_;
    }

    public static void setProvider(final Provider provider) {
        provider_ = provider;
    }

    public static S2ContainerBuilder getDefaultBuilder() {
        return defaultBuilder_;
    }

    public static void setDefaultBuilder(final S2ContainerBuilder defaultBuilder) {
        defaultBuilder_ = defaultBuilder;
    }

    public static S2Container create(final String path) {
        return getProvider().create(path);
    }

    public static S2Container create(final String path, final ClassLoader classLoader) {
        return getProvider().create(path, classLoader);
    }

    public static S2Container include(final S2Container parent, final String path) {
        return getProvider().include(parent, path);
    }

    protected static void configure() {
        final String configFile = System.getProperty(FACTORY_CONFIG_KEY, FACTORY_CONFIG_PATH);
        if (ResourceUtil.isExist(configFile)) {
            final S2ContainerBuilder builder = new XmlS2ContainerBuilder();
            configurationContainer_ = builder.build(configFile);
            Configurator configurator;
            if (configurationContainer_.hasComponentDef(Configurator.class)) {
                configurator = (Configurator) configurationContainer_.getComponent(Configurator.class);
            }
            else {
                configurator = new DefaultConfigurator();
            }
            configurator.configure(configurationContainer_);
        }
    }

    protected static void enter(final String path) {
        final Set paths = (Set) processingPaths_.get();
        if (paths.contains(path)) {
            throw new CircularIncludeRuntimeException(path, paths);
        }
        paths.add(path);
    }

    protected static void leave(final String path) {
        final Set paths = (Set) processingPaths_.get();
        paths.remove(path);
    }

    public interface Provider {
        S2Container create(String path);

        S2Container create(String path, ClassLoader classLoader);

        S2Container include(S2Container parent, String path);
    }

    public static class DefaultProvider implements Provider {
        protected PathResolver pathResolver_ = new SimplePathResolver();

        public PathResolver getPathResolver() {
            return pathResolver_;
        }

        public void setPathResolver(final PathResolver pathResolver) {
            pathResolver_ = pathResolver;
        }

        public S2Container create(final String path) {
            final String realPath = pathResolver_.resolvePath(null, path);
            enter(realPath);
            try {
                final String ext = getExtension(realPath);
                final S2Container container = getBuilder(ext).build(realPath);
                return container;
            }
            finally {
                leave(realPath);
            }
        }

        public S2Container create(final String path, final ClassLoader classLoader) {
            final String realPath = pathResolver_.resolvePath(null, path);
            enter(realPath);
            try {
                final String ext = getExtension(realPath);
                final S2Container container = getBuilder(ext).build(realPath, classLoader);
                return container;
            }
            finally {
                leave(realPath);
            }
        }

        public S2Container include(final S2Container parent, final String path) {
            final String realPath = pathResolver_.resolvePath(parent.getPath(), path);
            enter(realPath);
            try {
                final S2Container root = parent.getRoot();
                S2Container child = null;
                synchronized (root) {
                    if (root.hasDescendant(realPath)) {
                        child = root.getDescendant(realPath);
                        parent.include(child);
                    }
                    else {
                        final String ext = getExtension(realPath);
                        final S2ContainerBuilder builder = getBuilder(ext);
                        child = builder.include(parent, realPath);
                        root.registerDescendant(child);
                    }
                }
                return child;
            }
            finally {
                leave(realPath);
            }
        }

        protected String getExtension(final String path) {
            final String ext = ResourceUtil.getExtension(path);
            if (ext == null) {
                throw new ExtensionNotFoundRuntimeException(path);
            }
            return ext;
        }

        protected S2ContainerBuilder getBuilder(final String ext) {
            if (configurationContainer_ != null && configurationContainer_.hasComponentDef(ext)) {
                return (S2ContainerBuilder) configurationContainer_.getComponent(ext);
            }
            return defaultBuilder_;
        }
    }

    public interface Configurator {
        void configure(S2Container bootstrapContainer);
    }

    public static class DefaultConfigurator implements Configurator {
        public void configure(final S2Container bootstrapContainer) {
            if (configurationContainer_.hasComponentDef(Provider.class)) {
                provider_ = (Provider) configurationContainer_.getComponent(Provider.class);
            }
            else if (configurationContainer_.hasComponentDef(PathResolver.class)
                    && provider_ instanceof DefaultProvider) {
                ((DefaultProvider) provider_)
                        .setPathResolver((PathResolver) configurationContainer_
                                .getComponent(PathResolver.class));
            }

            if (configurationContainer_.hasComponentDef(DEFAULT_BUILDER_NAME)) {
                defaultBuilder_ = (S2ContainerBuilder) configurationContainer_
                        .getComponent(DEFAULT_BUILDER_NAME);
            }
            else if (configurationContainer_.hasComponentDef(ResourceResolver.class)
                    && defaultBuilder_ instanceof AbstractS2ContainerBuilder) {
                ((AbstractS2ContainerBuilder) defaultBuilder_)
                        .setResourceResolver((ResourceResolver) configurationContainer_
                                .getComponent(ResourceResolver.class));
            }

            if (configurationContainer_.hasComponentDef(S2ContainerBehavior.Provider.class)) {
                S2ContainerBehavior
                        .setProvider((S2ContainerBehavior.Provider) configurationContainer_
                                .getComponent(S2ContainerBehavior.Provider.class));
            }

            if (configurationContainer_.hasComponentDef(ComponentDeployerFactory.Provider.class)) {
                ComponentDeployerFactory
                        .setProvider((ComponentDeployerFactory.Provider) configurationContainer_
                                .getComponent(ComponentDeployerFactory.Provider.class));
            }

            if (configurationContainer_.hasComponentDef(AssemblerFactory.Provider.class)) {
                AssemblerFactory.setProvider((AssemblerFactory.Provider) configurationContainer_
                        .getComponent(AssemblerFactory.Provider.class));
            }
        }
    }
}
