package org.seasar.framework.exception;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * 対象が設定されていない場合の実行時例外です。
 */
public final class EmptyRuntimeException extends SRuntimeException {

	private String targetName_;
	/**
	 * @param targetName
	 */
	public EmptyRuntimeException(String targetName) {
		super("ESSR0007", new Object[]{targetName});
		targetName_ = targetName;
	}
	
	public String getTargetName() {
		return targetName_;
	}
}
