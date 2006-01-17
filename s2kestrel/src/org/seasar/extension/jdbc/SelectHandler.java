package org.seasar.extension.jdbc;

import org.seasar.framework.exception.SQLRuntimeException;

/**
 * @author higa
 *  
 */
public interface SelectHandler {

	public Object execute(Object[] args) throws SQLRuntimeException;

	public Object execute(Object[] args, Class[] argTypes)
			throws SQLRuntimeException;
}