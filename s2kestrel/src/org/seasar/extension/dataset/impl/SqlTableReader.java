package org.seasar.extension.dataset.impl;

import javax.sql.DataSource;

import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.TableReader;
import org.seasar.extension.jdbc.SelectHandler;
import org.seasar.extension.jdbc.impl.BasicSelectHandler;

/**
 * @author higa
 *
 */
public class SqlTableReader implements TableReader {

	private DataSource dataSource_;
	private String tableName_;
	private String sql_;
	
	public SqlTableReader(DataSource dataSource) {
		dataSource_ = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource_;
	}
	
	public String getTableName() {
		return tableName_;
	}
	
	public String getSql() {
		return sql_;
	}
	
	public void setTable(String tableName) {
		setTable(tableName, null);
	}
	
	public void setTable(String tableName, String condition) {
		tableName_ = tableName;
		StringBuffer sqlBuf = new StringBuffer(100);
		sqlBuf.append("SELECT * FROM ");
		sqlBuf.append(tableName);
		if (condition != null) {
			sqlBuf.append(" WHERE ");
			sqlBuf.append(condition);
		}
		sql_ = sqlBuf.toString();
	}
	
	public void setSql(String sql, String tableName) {
		sql_ = sql;
		tableName_ = tableName;
		
	}

	/**
	 * @see org.seasar.extension.dataset.TableReader#read()
	 */
	public DataTable read() {
		SelectHandler selectHandler = new BasicSelectHandler(
			dataSource_, sql_, new DataTableResultSetHandler(tableName_));
		return (DataTable) selectHandler.execute(null);
	}

}
