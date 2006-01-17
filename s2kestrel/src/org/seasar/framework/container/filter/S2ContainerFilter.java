package org.seasar.framework.container.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class S2ContainerFilter implements Filter {

	//private FilterConfig config_ = null;

	public S2ContainerFilter() {
		super();
	}

	public void init(FilterConfig config) throws ServletException {
		//config_ = config;
	}

	public void destroy() {
		//config_ = null;
	}

	/**
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		S2Container container = SingletonS2ContainerFactory.getContainer();
		container.setRequest((HttpServletRequest) request);
		container.setResponse((HttpServletResponse) response);

		try {
			chain.doFilter(request, response);
		} finally {
			container.setRequest(null);
			container.setResponse(null);
		}
	}
}