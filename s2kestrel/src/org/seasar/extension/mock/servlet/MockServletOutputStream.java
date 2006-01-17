package org.seasar.extension.mock.servlet;

import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;

/**
 * @author Satoshi Kimura
 */
public abstract class MockServletOutputStream extends ServletOutputStream {
    public abstract PrintWriter getPrintWriter();
    public abstract void setPrintWriter(PrintWriter writer);

}
