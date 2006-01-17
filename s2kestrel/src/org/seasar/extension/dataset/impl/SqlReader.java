package org.seasar.extension.dataset.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.seasar.extension.dataset.DataReader;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.TableReader;

/**
 * @author higa
 *
 */
public class SqlReader implements DataReader {

	private DataSource dataSource_;
	private List tableReaders_ = new ArrayList();
	
	public SqlReader(DataSource dataSource) {
		dataSource_ = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource_;
	}
	
	public void addTable(String tableName) {
		addTable(tableName, null);
	}

	public void addTable(String tableName, String condition) {
		SqlTableReader reader = new SqlTableReader(dataSource_);
		reader.setTable(tableName, condition);
		tableReaders_.add(reader);
	}
	
	public void addSql(String sql, String tableName) {
		SqlTableReader reader = new SqlTableReader(dataSource_);
		reader.setSql(sql, tableName);
		tableReaders_.add(reader);
	}

	/**
	 * @see org.seasar.extension.dataset.DataReader#read()
	 */
	public DataSet read() {
		DataSet dataSet = new DataSetImpl();
		for (int i = 0; i < tableReaders_.size(); ++i) {
			TableReader reader = (TableReader) tableReaders_.get(i);
			dataSet.addTable(reader.read());
		}
		return dataSet;
	}

}
