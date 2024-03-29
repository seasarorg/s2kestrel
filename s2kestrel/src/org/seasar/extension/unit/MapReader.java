package org.seasar.extension.unit;

import java.util.Iterator;
import java.util.Map;

import org.seasar.extension.dataset.ColumnType;
import org.seasar.extension.dataset.DataColumn;
import org.seasar.extension.dataset.DataReader;
import org.seasar.extension.dataset.DataRow;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.dataset.impl.DataSetImpl;
import org.seasar.extension.dataset.states.RowStates;
import org.seasar.extension.dataset.types.ColumnTypes;

public class MapReader implements DataReader {

	private DataSet dataSet_ = new DataSetImpl();
	private DataTable table_ = dataSet_.addTable("Map");

	protected MapReader() {
	}
	
	public MapReader(Map map) {
		setupColumns(map);
		setupRow(map);
	}

	protected void setupColumns(Map map) {
		for (Iterator i = map.keySet().iterator(); i.hasNext(); ) {
			String key = (String) i.next();
			table_.addColumn(key);
		}
	}

	protected void setupRow(Map map) {
		DataRow row = table_.addRow();
		for (int i = 0; i < table_.getColumnSize(); ++i) {
			DataColumn column = table_.getColumn(i);
			Object value = map.get(column.getColumnName());
			if (value != null) {
				ColumnType ct = ColumnTypes.getColumnType(value.getClass());
				row.setValue(column.getColumnName(), ct.convert(value, null));
			}
		}
		row.setState(RowStates.UNCHANGED);
	}

	public DataSet read() {
		return dataSet_;
	}

}