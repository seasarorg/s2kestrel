package org.seasar.framework.container.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.StringUtil;

public class S2ContainerServlet extends HttpServlet {

	public static final String CONFIG_PATH_KEY = "configPath";
	public static final String DEBUG_KEY = "debug";
	public static final String COMMAND = "command";
	public static final String RESTART = "restart";
	private static S2ContainerServlet instance_;
	private boolean debug_;
	
	public S2ContainerServlet() {
		instance_ = this;
	}
	
	public static S2ContainerServlet getInstance() {
		return instance_;
	}

	public void init() {
		String configPath = null;
		String debugStr = null;
		ServletConfig servletConfig = getServletConfig();
		if (servletConfig != null) {
			configPath = servletConfig.getInitParameter(CONFIG_PATH_KEY);
			debugStr = servletConfig.getInitParameter(DEBUG_KEY);
		}
		if (!StringUtil.isEmpty(configPath)) {
			SingletonS2ContainerFactory.setConfigPath(configPath);
		}
		if (!StringUtil.isEmpty(debugStr)) {
			debug_ = Boolean.valueOf(debugStr).booleanValue();
		}
		SingletonS2ContainerFactory.setServletContext(getServletContext());
		SingletonS2ContainerFactory.init();
	}

	public void destroy() {
		SingletonS2ContainerFactory.destroy();
	}

	public static S2Container getContainer() {
		return SingletonS2ContainerFactory.getContainer();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String command = request.getParameter(COMMAND);
		if (debug_ && command != null && RESTART.equalsIgnoreCase(command)) {
			destroy();
			init();
			response.getWriter().write("S2ContainerServlet is restarted.");
		} else {
			response.getWriter().write("S2ContainerServlet is running.");
		}
	}
}