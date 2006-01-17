package org.seasar.extension.dataset.states;

import javax.sql.DataSource;

import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.RowState;

/**
 * @author higa
 *
 */
public class UnchangedState implements RowState {
	
	/**
	 * @see org.seasar.extension.dataset.RowState#update(javax.sql.DataSource, org.seasar.extension.dataset.DataRow)
	 */
	public void update(DataSource dataSource, DataRow row) {
	}

	public String toString() {
		return "UNCHANGED";
	}
}
