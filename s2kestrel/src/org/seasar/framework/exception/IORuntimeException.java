package org.seasar.framework.exception;

import java.io.IOException;


/**
 * @author higa
 *
 * IOException�����b�v������s����O�ł��B
 */
public class IORuntimeException extends SRuntimeException {

	public IORuntimeException(IOException cause) {
		super("ESSR0040", new Object[]{cause}, cause);
	}
}
