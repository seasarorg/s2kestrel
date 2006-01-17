package org.seasar.extension.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author higa
 *
 */
public interface ResultSetFactory {

	public ResultSet createResultSet(PreparedStatement ps);
}
