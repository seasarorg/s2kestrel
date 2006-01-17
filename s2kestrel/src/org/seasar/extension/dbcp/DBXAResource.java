package org.seasar.extension.dbcp;

import java.sql.Connection;

import javax.transaction.xa.XAResource;

/**
 * @author higa
 *
 */
public interface DBXAResource extends XAResource {

	public Connection getConnection();
}
