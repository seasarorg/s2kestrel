package org.seasar.framework.exception;

import java.io.IOException;


/**
 * @author higa
 *
 * IOExceptionをラップする実行時例外です。
 */
public class IORuntimeException extends SRuntimeException {

	public IORuntimeException(IOException cause) {
		super("ESSR0040", new Object[]{cause}, cause);
	}
}
