package org.seasar.extension.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleResultSet extends ResultSetWrapper {

	public static final int WAVE_DASH = 0x301c;

	public static final int FULLWIDTH_TILDE = 0xff5e;

	public OracleResultSet(ResultSet resultSet) {
		super(resultSet);
	}

	public String getString(int columnIndex) throws SQLException {
		return convert(super.getString(columnIndex));
	}

	public String getString(String columnName) throws SQLException {
		return convert(super.getString(columnName));
	}

	protected String convert(String source) {
		if (source == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		char ch;

		for (int i = 0; i < source.length(); i++) {
			ch = source.charAt(i);

			switch (ch) {
			case WAVE_DASH: // ` WAVE DASH -> FULLWIDTH TILDE
				ch = FULLWIDTH_TILDE;
				break;
			default:
				break;
			}

			result.append(ch);
		}

		return result.toString();
	}
}