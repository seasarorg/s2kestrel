package org.seasar.extension.dataset;

import org.seasar.extension.jdbc.ColumnNotFoundRuntimeException;

/**
 * @author higa
 *
 */
public interface DataRow {

	public Object getValue(int index);

	public Object getValue(String columnName)
		throws ColumnNotFoundRuntimeException;
	
	public void setValue(int index, Object value);
	
	public void setValue(String columnName, Object value)
		throws ColumnNotFoundRuntimeException;
		
	public void remove();
		
	public DataTable getTable();
	
	public RowState getState();
	
	public void setState(RowState rowState);
	
	public void copyFrom(Object source);
}
