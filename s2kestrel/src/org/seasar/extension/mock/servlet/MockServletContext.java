package org.seasar.extension.mock.servlet;

import javax.servlet.ServletContext;


/**
 * @author Satoshi Kimura
 */
public interface MockServletContext extends ServletContext {
    void addMimeType(String file, String type);
    void setInitParameter(String name, String value);
    MockHttpServletRequestImpl createRequest(String path);
}
