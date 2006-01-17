package org.seasar.extension.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author higa
 *
 */
public interface ResultSetHandler {

	public Object handle(ResultSet resultSet) throws SQLException;
}
