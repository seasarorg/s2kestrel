package org.seasar.extension.dataset;

/**
 * @author higa
 *
 */
public interface DataColumn {

	public String getColumnName();
	
	public int getColumnIndex();
	
	public ColumnType getColumnType();
	
	public boolean isPrimaryKey();
	
	public void setPrimaryKey(boolean primaryKey);
	
	public boolean isWritable();
	
	public void setWritable(boolean writable);
	
	public String getFormatPattern();
	
	public void setFormatPattern(String formatPattern);
	
	public Object convert(Object value);
}
