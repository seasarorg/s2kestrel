package org.seasar.extension.dbcp;

import java.sql.SQLException;

/**
 * @author higa
 *
 */
public interface ConnectionPool {

	public ConnectionWrapper checkOut() throws SQLException;
	
	public void checkIn(ConnectionWrapper connectionWrapper);
	
	public void release(ConnectionWrapper connectionWrapper);
	
	public void close();
	
	public int getActivePoolSize();
	
	public int getTxActivePoolSize();
	
	public int getFreePoolSize();
	
	public int getMaxPoolSize();
}
