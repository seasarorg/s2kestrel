package org.seasar.extension.dataset;

/**
 * @author higa
 *
 */
public interface DataSet {

	public int getTableSize();
	
	public String getTableName(int index);
	
	public DataTable getTable(String tableName)
		throws TableNotFoundRuntimeException;
	
	public DataTable getTable(int index);
	
	public DataTable addTable(String tableName);
	
	public DataTable addTable(DataTable table);
	
	public DataTable removeTable(String tableName)
		throws TableNotFoundRuntimeException;
		
	public DataTable removeTable(int index);
	
	public DataTable removeTable(DataTable table)
		throws TableNotFoundRuntimeException;
}
