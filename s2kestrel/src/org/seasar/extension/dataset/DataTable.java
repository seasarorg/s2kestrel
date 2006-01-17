package org.seasar.extension.dataset;

import java.sql.DatabaseMetaData;

import org.seasar.extension.jdbc.ColumnNotFoundRuntimeException;

/**
 * @author higa
 *
 */
public interface DataTable {

	public String getTableName();
	
	public void setTableName(String tableName);
	
	public int getRowSize();
	
	public DataRow getRow(int index);
	
	public DataRow addRow();
	
	public int getRemovedRowSize();
	
	public DataRow getRemovedRow(int index);

	public DataRow[] removeRows();
	
	public int getColumnSize();
	
	public DataColumn getColumn(int index);
	
	public DataColumn getColumn(String columnName)
		throws ColumnNotFoundRuntimeException;
		
	public boolean hasColumn(String columnName);
	
	public String getColumnName(int index);
	
	public ColumnType getColumnType(int index);
	
	public ColumnType getColumnType(String columnName);
	
	public DataColumn addColumn(String columnName);

	public DataColumn addColumn(String columnName, ColumnType columnType);
	
	public boolean hasMetaData();
	
	public void setupMetaData(DatabaseMetaData dbMetaData);
	
	public void setupColumns(Class beanClass);
	
	public void copyFrom(Object source);
}
