package org.seasar.framework.util;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * リソースが取得できない場合の実行時例外です。
 */
public class ResourceNotFoundRuntimeException extends SRuntimeException {

	private String path_;
	
	public ResourceNotFoundRuntimeException(String path) {
		super("ESSR0055", new Object[]{path});
		path_ = path;
	}
	
	public String getPath() {
		return path_;
	}
}
