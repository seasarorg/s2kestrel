package org.seasar.extension.mock.servlet;

import java.io.IOException;
import java.io.PrintWriter;

public class MockServletOutputStreamImpl extends MockServletOutputStream {

	private PrintWriter writer_;
	
	public MockServletOutputStreamImpl(PrintWriter writer) {
		setPrintWriter(writer);
	}

	public PrintWriter getPrintWriter() {
		return writer_;
	}

	public void setPrintWriter(PrintWriter writer) {
		writer_ = writer;
	}

	public void write(int b) throws IOException {
		writer_.write(b);
	}
	
	public String toString() {
		if (writer_ != null) {
			return writer_.toString();
		}
		return super.toString();
	}
}