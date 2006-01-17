package org.seasar.extension.mock.servlet;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.seasar.framework.util.EnumerationAdapter;

public class MockServletConfigImpl implements MockServletConfig, Serializable {

	private String servletName_;

	private ServletContext servletContext_;

	private Map initParameters_ = new HashMap();

	public MockServletConfigImpl() {
	}

	/**
	 * @see javax.servlet.ServletConfig#getServletName()
	 */
	public String getServletName() {
		return servletName_;
	}

	public void setServletName(String servletName) {
		servletName_ = servletName;
	}

	/**
	 * @see javax.servlet.ServletConfig#getServletContext()
	 */
	public ServletContext getServletContext() {
		return servletContext_;
	}

	public void setServletContext(ServletContext servletContext) {
		servletContext_ = servletContext;
	}

	/**
	 * @see javax.servlet.ServletConfig#getInitParameter(java.lang.String)
	 */
	public String getInitParameter(String name) {
		return (String) initParameters_.get(name);
	}

	public void setInitParameter(String name, final String value) {
		initParameters_.put(name, value);
	}

	/**
	 * @see javax.servlet.ServletConfig#getInitParameterNames()
	 */
	public Enumeration getInitParameterNames() {
		return new EnumerationAdapter(initParameters_.keySet().iterator());
	}
}