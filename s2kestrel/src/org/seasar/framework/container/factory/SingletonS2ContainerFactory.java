package org.seasar.framework.container.factory;

import javax.servlet.ServletContext;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.EmptyRuntimeException;

public final class SingletonS2ContainerFactory {

	private static String configPath_ = "app.dicon";
	private static ServletContext servletContext_;
	private static S2Container container_;
	
	private SingletonS2ContainerFactory() {
	}
	
	public static String getConfigPath() {
		return configPath_;
	}
	
	public static void setConfigPath(String configPath) {
		configPath_ = configPath;
	}
	
	public static ServletContext getServletContext() {
		return servletContext_;
	}
	
	public static void setServletContext(ServletContext servletContext) {
		servletContext_ = servletContext;
	}

	public static void init() {
		container_ = S2ContainerFactory.create(configPath_);
		container_.setServletContext(servletContext_);
		container_.init();
	}
	
	public static void destroy() {
		container_.destroy();
		container_ = null;
	}
	
	public static S2Container getContainer() {
		if (container_ == null) {
			throw new EmptyRuntimeException("S2Container");
		}
		return container_;
	}
	
	public static void setContainer(S2Container container) {
		container_ = container;
	}
	
	public static boolean hasContainer() {
		return container_ != null;
	}
}
