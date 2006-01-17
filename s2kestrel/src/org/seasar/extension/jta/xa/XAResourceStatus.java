package org.seasar.extension.jta.xa;

/**
 * @author higa
 *
 */
public interface XAResourceStatus {

	public int RS_NONE = 0;
	public int RS_ACTIVE = 1;
	public int RS_SUSPENDED = 2;
	public int RS_FAIL = 3;
	public int RS_SUCCESS = 4;
	public int RS_PREPARED = 5;
}
