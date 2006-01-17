package org.seasar.extension.dataset.states;

import javax.sql.DataSource;

import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.RowState;
import org.seasar.extension.jdbc.UpdateHandler;
import org.seasar.extension.jdbc.impl.BasicUpdateHandler;

/**
 * @author higa
 *
 */
public abstract class AbstractRowState implements RowState {
	
	AbstractRowState() {
	}
	
	/**
	 * @see org.seasar.extension.dataset.RowState#update(javax.sql.DataSource, org.seasar.extension.dataset.DataRow)
	 */
	public void update(DataSource dataSource, DataRow row) {
		String sql = getSql(row.getTable());
		Object[] args = getArgs(row);
		UpdateHandler handler = new BasicUpdateHandler(dataSource, sql);
		execute(handler, args);
	}

	protected abstract String getSql(DataTable table);
	
	protected abstract Object[] getArgs(DataRow row);

	protected void execute(UpdateHandler handler, Object[] args) {
		handler.execute(args);
	}
}