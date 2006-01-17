package org.seasar.extension.dataset.impl;

import javax.sql.DataSource;

import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataWriter;
import org.seasar.extension.dataset.TableWriter;

/**
 * @author higa
 *
 */
public class SqlWriter implements DataWriter {

	private DataSource dataSource_;
	
	public SqlWriter(DataSource dataSource) {
		dataSource_ = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource_;
	}

	/**
	 * @see org.seasar.extension.dataset.DataWriter#write(org.seasar.extension.dataset.DataSet)
	 */
	public void write(DataSet dataSet) {
		TableWriter writer = new SqlTableWriter(getDataSource());
		for (int i = 0; i < dataSet.getTableSize(); ++i) {
			writer.write(dataSet.getTable(i));
		}
	}

}
