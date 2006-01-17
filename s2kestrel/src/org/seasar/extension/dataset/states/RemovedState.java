package org.seasar.extension.dataset.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.seasar.extension.dataset.DataColumn;
import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataTable;

/**
 * @author higa
 *
 */
public class RemovedState extends AbstractRowState {

	private static Map sqlCache_ = Collections.synchronizedMap(new WeakHashMap());

	public String toString() {
		return "REMOVED";
	}
	
	protected String getSql(DataTable table) {
		String sql = (String) sqlCache_.get(table);
		if (sql == null) {
			sql = createSql(table);
			sqlCache_.put(table, sql);
		}
		return sql;
	}

	private static String createSql(DataTable table) {
		StringBuffer buf = new StringBuffer(100);
		buf.append("DELETE FROM ");
		buf.append(table.getTableName());
		buf.append(" WHERE ");
		for (int i = 0; i < table.getColumnSize(); ++i) {
			DataColumn column = table.getColumn(i);
			if (column.isPrimaryKey()) {
				buf.append(column.getColumnName());
				buf.append(" = ? AND ");
			}
		}
		buf.setLength(buf.length() - 5);
		return buf.toString();
	}

	protected Object[] getArgs(DataRow row) {
		DataTable table = row.getTable();
		List bindVariables = new ArrayList();
		for (int i = 0; i < table.getColumnSize(); ++i) {
			DataColumn column = table.getColumn(i);
			if (column.isPrimaryKey()) {
				bindVariables.add(row.getValue(i));
			}
		}
		return bindVariables.toArray();
	}
}