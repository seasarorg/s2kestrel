package org.seasar.extension.dataset.impl;

import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.TableNotFoundRuntimeException;
import org.seasar.framework.util.ArrayMap;
import org.seasar.framework.util.CaseInsensitiveMap;

/**
 * @author higa
 *
 */
public class DataSetImpl implements DataSet {

	private ArrayMap tables_ = new CaseInsensitiveMap();

	public DataSetImpl() {
	}

	/**
	 * @see org.seasar.extension.dataset.DataSet#getTableSize()
	 */
	public int getTableSize() {
		return tables_.size();
	}
	
	/**
	 * @see org.seasar.extension.dataset.DataSet#getTableName(int)
	 */
	public String getTableName(int index) {
		return getTable(index).getTableName();
	}

	/**
	 * @see org.seasar.extension.dataset.DataSet#getTable(int)
	 */
	public DataTable getTable(int index) {
		return (DataTable) tables_.get(index);
	}

	/**
	 * @see org.seasar.extension.dataset.DataSet#getTable(java.lang.String)
	 */
	public DataTable getTable(String tableName)
		throws TableNotFoundRuntimeException {

		DataTable table = (DataTable) tables_.get(tableName);
		if (table == null) {
			throw new TableNotFoundRuntimeException(tableName);
		}
		return table;
	}

	/**
	 * @see org.seasar.extension.dataset.DataSet#addTable(java.lang.String)
	 */
	public DataTable addTable(String tableName) {
		return addTable(new DataTableImpl(tableName));
	}

	/**
	 * @see org.seasar.extension.dataset.DataSet#addTable(org.seasar.extension.dataset.DataTable)
	 */
	public DataTable addTable(DataTable table) {
		tables_.put(table.getTableName(), table);
		return table;
	}

	/**
	 * @see org.seasar.extension.dataset.DataSet#removeTable(org.seasar.extension.dataset.DataTable)
	 */
	public DataTable removeTable(DataTable table) {
		return removeTable(table.getTableName());
	}

	/**
	 * @see org.seasar.extension.dataset.DataSet#removeTable(int)
	 */
	public DataTable removeTable(int index) {
		return (DataTable) tables_.remove(index);
	}

	/**
	 * @see org.seasar.extension.dataset.DataSet#removeTable(java.lang.String)
	 */
	public DataTable removeTable(String tableName) {
		DataTable table = (DataTable) tables_.remove(tableName);
		if (table == null) {
			throw new TableNotFoundRuntimeException(tableName);
		}
		return table;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer(100);
		for (int i = 0; i < getTableSize(); ++i) {
			buf.append(getTable(i));
			buf.append("\n");
		}
		return buf.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof DataSet)) {
			return false;
		}
		DataSet other = (DataSet) o;
		if (getTableSize() != other.getTableSize()) {
			return false;
		}
		for (int i = 0; i < getTableSize(); ++i) {
			if (!getTable(i).equals(other.getTable(i))) {
				return false;
			}
		}
		return true;
	}
}
