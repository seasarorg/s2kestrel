package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * Šg’£q‚ªŒ©‚Â‚©‚ç‚È‚©‚Á‚½‚Æ‚«‚ÌÀs—áŠO
 */
public class ExtensionNotFoundRuntimeException extends SRuntimeException {

	private String path_;
	
	public ExtensionNotFoundRuntimeException(String path) {
		super("ESSR0074", new Object[] { path});
		path_ = path;
	}
	
	public String getPath() {
		return path_;
	}
}