package org.seasar.extension.dataset;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * テーブルが見つからなかったときの実行時例外
 */
public final class TableNotFoundRuntimeException extends SRuntimeException {

	private String tableName_;
	
	/**
	 * @param componentKey
	 */
	public TableNotFoundRuntimeException(String tableName) {
		super("ESSR0067", new Object[] { tableName });
		tableName_ = tableName;
	}
	
	public String getTableName() {
		return tableName_;
	}
}