package org.seasar.extension.mock.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author Satoshi Kimura
 */
public interface MockServletConfig extends ServletConfig {
    void setServletName(String servletName);
    void setServletContext(ServletContext servletContext);
    void setInitParameter(String name, final String value);
}
