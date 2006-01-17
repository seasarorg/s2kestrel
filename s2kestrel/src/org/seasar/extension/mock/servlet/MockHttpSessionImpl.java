package org.seasar.extension.mock.servlet;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.seasar.framework.util.EnumerationAdapter;

public class MockHttpSessionImpl implements MockHttpSession, Serializable {

	private final long creationTime_ = System.currentTimeMillis();

	private long lastAccessedTime_ = creationTime_;

	private ServletContext servletContext_;

	private String id_;

	private boolean new_ = true;

	private boolean valid_ = true;

	private int maxInactiveInterval_ = -1;

	private Map attributes_ = new HashMap();

	public MockHttpSessionImpl(ServletContext servletContext) {
		servletContext_ = servletContext;
		id_ = "id/" + hashCode();
	}

	/**
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	public long getCreationTime() {
		return creationTime_;
	}

	/**
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	public String getId() {
		return id_;
	}

	/**
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	public long getLastAccessedTime() {
		return lastAccessedTime_;
	}

	public void access() {
		new_ = false;
		lastAccessedTime_ = System.currentTimeMillis();
	}

	/**
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	public ServletContext getServletContext() {
		return servletContext_;
	}

	/**
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	public void setMaxInactiveInterval(int maxInactiveInterval) {
		maxInactiveInterval_ = maxInactiveInterval;
	}

	/**
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	public int getMaxInactiveInterval() {
		return maxInactiveInterval_;
	}

	/**
	 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
	 */
	public Object getAttribute(String name) {
		return attributes_.get(name);
	}

	/**
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String,
	 *      java.lang.Object)
	 */
	public void setAttribute(String name, Object value) {
		attributes_.put(name, value);
	}

	/**
	 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
	 */
	public void removeAttribute(String name) {
		attributes_.remove(name);
	}

	/**
	 * @deprecated @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	public javax.servlet.http.HttpSessionContext getSessionContext() {
		return null;
	}

	/**
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	public Object getValue(String name) {
		return getAttribute(name);
	}

	/**
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	public Enumeration getAttributeNames() {
		return new EnumerationAdapter(attributes_.keySet().iterator());
	}

	/**
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	public String[] getValueNames() {
		return (String[]) attributes_.keySet().toArray(
				new String[attributes_.size()]);
	}

	/**
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String,
	 *      java.lang.Object)
	 */
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	/**
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	public void removeValue(String name) {
		removeAttribute(name);
	}

	/**
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	public void invalidate() {
		if (!valid_) {
			return;
		}
		attributes_.clear();
		valid_ = false;
	}

	/**
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	public boolean isNew() {
		return new_;
	}
	
	public boolean isValid() {
		return valid_;
	}
	
	public void setValid(boolean valid) {
		valid_ = valid;
	}
}