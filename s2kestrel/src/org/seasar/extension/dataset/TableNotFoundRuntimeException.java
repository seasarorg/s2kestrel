package org.seasar.extension.dataset;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * �e�[�u����������Ȃ������Ƃ��̎��s����O
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