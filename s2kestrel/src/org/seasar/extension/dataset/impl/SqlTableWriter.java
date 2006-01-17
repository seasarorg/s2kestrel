package org.seasar.extension.dataset.impl;

import java.sql.Connection;

import javax.sql.DataSource;

import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.RowState;
import org.seasar.extension.dataset.TableWriter;
import org.seasar.framework.util.ConnectionUtil;
import org.seasar.framework.util.DataSourceUtil;

/**
 * @author higa
 *
 */
public class SqlTableWriter implements TableWriter {

	private DataSource dataSource_;

	public SqlTableWriter(DataSource dataSource) {
		dataSource_ = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource_;
	}

	/**
	 * @see org.seasar.extension.dataset.TableWriter#write(org.seasar.extension.dataset.DataTable)
	 */
	public void write(DataTable table) {
		if (!table.hasMetaData()) {
			setupMetaData(table);
		}
		doWrite(table);
	}
	
	protected void doWrite(DataTable table) {
		for (int i = 0; i < table.getRowSize(); ++i) {
			DataRow row = table.getRow(i);
			RowState state = row.getState();
			state.update(dataSource_, row);
		}
	}
	
	private void setupMetaData(DataTable table) {
		Connection con = DataSourceUtil.getConnection(dataSource_);
		try {
			table.setupMetaData(ConnectionUtil.getMetaData(con));
		} finally {
			ConnectionUtil.close(con);
		}
	}
}