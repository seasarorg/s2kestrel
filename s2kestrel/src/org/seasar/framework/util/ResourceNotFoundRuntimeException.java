package org.seasar.framework.util;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * ���\�[�X���擾�ł��Ȃ��ꍇ�̎��s����O�ł��B
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
