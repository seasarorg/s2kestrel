package org.seasar.extension.mock.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author higa
 *
 */
public class MockServlet extends GenericServlet {

	/**
	 * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
	}
	
	public void log(String msg) {
	}


}
