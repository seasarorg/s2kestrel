package org.seasar.framework.exception;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * �Ώۂ��ݒ肳��Ă��Ȃ��ꍇ�̎��s����O�ł��B
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
