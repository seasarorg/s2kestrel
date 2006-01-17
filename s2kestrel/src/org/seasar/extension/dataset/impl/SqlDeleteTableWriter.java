package org.seasar.extension.dataset.impl;

import javax.sql.DataSource;

import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.states.RowStates;

/**
 * @author higa
 *
 */
public class SqlDeleteTableWriter extends SqlTableWriter {

	public SqlDeleteTableWriter(DataSource dataSource) {
		super(dataSource);
	}

	protected void doWrite(DataTable table) {
		for (int i = 0; i < table.getRowSize(); ++i) {
			DataRow row = table.getRow(i);
			RowStates.REMOVED.update(getDataSource(), row);
		}
	}
}