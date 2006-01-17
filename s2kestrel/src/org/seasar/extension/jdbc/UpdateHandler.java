package org.seasar.extension.jdbc;

import org.seasar.framework.exception.SQLRuntimeException;

/**
 * @author higa
 *  
 */
public interface UpdateHandler {

	public int execute(Object[] args) throws SQLRuntimeException;

	public int execute(Object[] args, Class[] argTypes)
			throws SQLRuntimeException;
}