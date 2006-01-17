package org.seasar.extension.mock.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author higa
 *  
 */
public class MockRequestDispatcherImpl implements MockRequestDispatcher {

	/**
	 *  
	 */
	public MockRequestDispatcherImpl() {
	}

	/**
	 * @see javax.servlet.RequestDispatcher#forward(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse)
	 */
	public void forward(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * @see javax.servlet.RequestDispatcher#include(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse)
	 */
	public void include(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
	}

}