package org.seasar.extension.mock.servlet;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Satoshi Kimura
 */
public interface MockHttpServletRequest extends HttpServletRequest {
    void addParameter(String name, String value);
    void addParameter(String name, String[] values);
    void addCookie(Cookie cookie);
    void addHeader(String name, String value);
    void setAuthType(String authType);
    void addDateHeader(String name, long value);
    void addIntHeader(String name, int value);
    void setPathInfo(String pathInfo);
    void setPathTranslated(String pathTranslated);
    void setQueryString(String queryString);
    void setContentLength(int contentLength);
    void setContentType(String contentType);
    void setParameter(String name, String value);
    void setParameter(String name, String[] values);
    void setProtocol(String protocol);
    void setScheme(String scheme);
    void setServerName(String serverName);
    void setServerPort(int serverPort);
    void setRemoteAddr(String remoteAddr);
    void setRemoteHost(String remoteHost);
    void setLocale(Locale locale);
}