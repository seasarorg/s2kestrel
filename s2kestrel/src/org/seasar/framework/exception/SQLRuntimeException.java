package org.seasar.framework.exception;

import java.sql.SQLException;

/**
 * @author higa
 *
 * SQLExceptionをラップする実行時例外です。
 */
public final class SQLRuntimeException extends SRuntimeException {

	public SQLRuntimeException(SQLException cause) {
		super("ESSR0071", new Object[] { cause }, cause);
	}
}
