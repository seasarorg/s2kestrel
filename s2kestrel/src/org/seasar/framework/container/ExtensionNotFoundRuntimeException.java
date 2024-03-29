package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * 拡張子が見つからなかったときの実行時例外
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