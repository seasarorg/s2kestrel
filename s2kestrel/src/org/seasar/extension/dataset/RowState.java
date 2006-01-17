package org.seasar.extension.dataset;

import javax.sql.DataSource;

/**
 * @author higa
 *
 */
public interface RowState {

	public void update(DataSource dataSource, DataRow row);
}
