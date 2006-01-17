package org.seasar.extension.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author higa
 *  
 */
public class BooleanToIntCallableStatement extends CallableStatementWrapper {

	/**
	 * @param original
	 */
	public BooleanToIntCallableStatement(CallableStatement original) {
		super(original);
	}

	/**
	 * @see java.sql.PreparedStatement#setBoolean(int, boolean)
	 */
	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		setInt(parameterIndex, x ? 1 : 0);
	}

	/**
	 * @see java.sql.PreparedStatement#setNull(int, int, java.lang.String)
	 */
	public void setNull(int paramIndex, int sqlType, String typeName)
			throws SQLException {

		super.setNull(paramIndex, changeSqlTypeIfBoolean(sqlType), typeName);
	}

	/**
	 * @see java.sql.PreparedStatement#setNull(int, int)
	 */
	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		super.setNull(parameterIndex, changeSqlTypeIfBoolean(sqlType));
	}

	protected int changeSqlTypeIfBoolean(int sqlType) {
		return sqlType == org.seasar.kestrel.sql.Types.BOOLEAN ? Types.INTEGER : sqlType;
	}
}