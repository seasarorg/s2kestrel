package org.seasar.extension.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {

	public static String ENCODING = "encoding";

	public static String DEFAULT_ENCODING = "Windows-31j";

	//private FilterConfig config_ = null;
	private String encoding_;

	public EncodingFilter() {
		super();
	}

	public void init(FilterConfig config) throws ServletException {
		//config_ = config;
		encoding_ = config.getInitParameter(ENCODING);
		if (encoding_ == null) {
			encoding_ = DEFAULT_ENCODING;
		}
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

		if (request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(encoding_);
		}
		chain.doFilter(request, response);
	}
}