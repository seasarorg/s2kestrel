package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 */
public class ContainerNotRegisteredRuntimeException extends SRuntimeException {

	private String path_;
	
	public ContainerNotRegisteredRuntimeException(String path) {
		super("ESSR0075", new Object[] { path });
		path_ = path;
	}
	
	public String getPath() {
		return path_;
	}
}