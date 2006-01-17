package org.seasar.framework.exception;

import java.sql.SQLException;

/**
 * @author higa
 *
 * SQLException�����b�v������s����O�ł��B
 */
public final class SQLRuntimeException extends SRuntimeException {

	public SQLRuntimeException(SQLException cause) {
		super("ESSR0071", new Object[] { cause }, cause);
	}
}
