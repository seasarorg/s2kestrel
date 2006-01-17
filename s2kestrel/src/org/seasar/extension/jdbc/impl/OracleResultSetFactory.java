package org.seasar.extension.jdbc.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OracleResultSetFactory extends BasicResultSetFactory {

	/**
	 * @see org.seasar.extension.jdbc.ResultSetFactory#createResultSet(java.sql.PreparedStatement)
	 */
	public ResultSet createResultSet(PreparedStatement ps) {
		return new OracleResultSet(super.createResultSet(ps));
	}

}
