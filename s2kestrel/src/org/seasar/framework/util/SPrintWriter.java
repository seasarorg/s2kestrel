package org.seasar.framework.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SPrintWriter extends PrintWriter {

	public SPrintWriter() {
		super(new StringWriter());
	}

	public String toString() {
		return out.toString();
	}
}