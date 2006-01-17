package org.seasar.extension.dbcp;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

/**
 * @author higa
 *
 */
public interface ConnectionWrapper extends Connection {

	public void closeReally();
	
	public void release() throws SQLException;
	
	public void init(boolean localTx);
	
	public void cleanup();
	
	public Connection getPhysicalConnection();
	
	public XAResource getXAResource();
	
	public XAConnection getXAConnection();
}
