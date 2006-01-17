package org.seasar.extension.jdbc;

import java.util.List;

import org.seasar.framework.exception.SQLRuntimeException;

/**
 * @author higa
 *  
 */
public interface BatchHandler {

	public int execute(List list) throws SQLRuntimeException;

	public int execute(List list, Class[] argTypes) throws SQLRuntimeException;
}