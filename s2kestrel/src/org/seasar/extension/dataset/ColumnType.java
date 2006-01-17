package org.seasar.extension.dataset;

/**
 * @author higa
 *
 */
public interface ColumnType {
	
	public Object convert(Object value, String formatPattern);
	
	public boolean equals(Object arg1, Object arg2);

}
