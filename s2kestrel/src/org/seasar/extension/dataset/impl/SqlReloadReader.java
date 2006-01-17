package org.seasar.extension.dataset.impl;

import javax.sql.DataSource;

import org.seasar.extension.dataset.DataReader;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.TableReader;

/**
 * @author higa
 *  
 */
public class SqlReloadReader implements DataReader {

	private DataSource dataSource_;

	private DataSet dataSet_;

	public SqlReloadReader(DataSource dataSource, DataSet dataSet) {
		dataSource_ = dataSource;
		dataSet_ = dataSet;
	}

	public DataSource getDataSource() {
		return dataSource_;
	}

	public DataSet getDataSet() {
		return dataSet_;
	}

	/**
	 * @see org.seasar.extension.dataset.DataReader#read()
	 */
	public DataSet read() {
		DataSet newDataSet = new DataSetImpl();
		for (int i = 0; i < dataSet_.getTableSize(); ++i) {
			TableReader reader = new SqlReloadTableReader(dataSource_, dataSet_
					.getTable(i));
			newDataSet.addTable(reader.read());
		}
		return newDataSet;
	}

}