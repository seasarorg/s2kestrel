package org.seasar.extension.jdbc;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 */
public final class ColumnNotFoundRuntimeException extends SRuntimeException {

	private String tableName_;
	private String columnName_;
	
	/**
	 * @param componentKey
	 */
	public ColumnNotFoundRuntimeException(String tableName, String columnName) {
		super("ESSR0068", new Object[] { tableName, columnName });
		tableName_ = tableName;
		columnName_ = columnName;
	}
	
	public String getTableName() {
		return tableName_;
	}
	
	public String getColumnName() {
		return columnName_;
	}
}