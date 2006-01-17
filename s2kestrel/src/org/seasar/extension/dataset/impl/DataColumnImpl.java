package org.seasar.extension.dataset.impl;

import org.seasar.extension.dataset.ColumnType;
import org.seasar.extension.dataset.DataColumn;

/**
 * @author higa
 *
 */
public class DataColumnImpl implements DataColumn {

	private String columnName_;
	private ColumnType columnType_;
	private int columnIndex_;
	private boolean primaryKey_ = false;
	private boolean writable_ = true;
	private String formatPattern_;
	
	public DataColumnImpl(String columnName, ColumnType columnType,
			int columnIndex) {
		
		columnName_ = columnName;
		columnType_ = columnType;
		columnIndex_ = columnIndex;
	}

	/**
	 * @see org.seasar.extension.dataset.DataColumn#getColumnName()
	 */
	public String getColumnName() {
		return columnName_;
	}
	
	/**
	 * @see org.seasar.extension.dataset.DataColumn#getColumnType()
	 */
	public ColumnType getColumnType() {
		return columnType_;
	}
	
	/**
	 * @see org.seasar.extension.dataset.DataColumn#getColumnIndex()
	 */
	public int getColumnIndex() {
		return columnIndex_;
	}
	
	/**
	 * @see org.seasar.extension.dataset.DataColumn#isPrimaryKey()
	 */
	public boolean isPrimaryKey() {
		return primaryKey_;
	}
	
	/**
	 * @see org.seasar.extension.dataset.DataColumn#setPrimaryKey(boolean)
	 */
	public void setPrimaryKey(boolean primaryKey) {
		primaryKey_ = primaryKey;
	}
	
	/**
	 * @see org.seasar.extension.dataset.DataColumn#isWritable()
	 */
	public boolean isWritable() {
		return writable_;
	}
	
	/**
	 * @see org.seasar.extension.dataset.DataColumn#setWritable(boolean)
	 */
	public void setWritable(boolean writable) {
		writable_ = writable;
	}
	
	/**
	 * @see org.seasar.extension.dataset.DataColumn#getFormatPattern()
	 */
	public String getFormatPattern() {
		return formatPattern_;
	}
	
	/**
	 * @see org.seasar.extension.dataset.DataColumn#setFormatPattern(java.lang.String)
	 */
	public void setFormatPattern(String formatPattern) {
		formatPattern_ = formatPattern;
	}
	
	public Object convert(Object value) {
		return columnType_.convert(value, formatPattern_);
	}
}