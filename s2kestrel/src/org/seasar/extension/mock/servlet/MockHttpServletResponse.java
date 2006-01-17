package org.seasar.extension.mock.servlet;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Satoshi Kimura
 */
public interface MockHttpServletResponse extends HttpServletResponse {
    Cookie[] getCookies();
    int getStatus();
    String getMessage();
    Enumeration getHeaders(String name);
    String getHeader(String name);
    Enumeration getHeaderNames();
    int getIntHeader(String name);
    void setCharacterEncoding(String characterEncoding);
    int getContentLength();
    String getContentType();
}