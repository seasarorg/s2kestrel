package org.seasar.extension.dbcp.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.ConnectionEventListener;
import javax.sql.XAConnection;
import javax.transaction.xa.XAResource;

public final class XAConnectionImpl implements XAConnection {

    private Connection connection_;
    private XAResource xaResource_;
    private List _listeners = new ArrayList();

    public XAConnectionImpl(Connection connection) {
        connection_ = connection;
        xaResource_ = new DBXAResourceImpl(connection);
    }

    public XAResource getXAResource() {
        return xaResource_;
    }

    public Connection getConnection() throws SQLException {
        return connection_;
    }

    public void close() throws SQLException {
        if (connection_ == null) {
            return;
        }
        if (!connection_.isClosed()) {
			connection_.close();
        }
        connection_ = null;
    }

    public synchronized void addConnectionEventListener(final ConnectionEventListener listener) {
        _listeners.add(listener);
    }

    public synchronized void removeConnectionEventListener(final ConnectionEventListener listener) {
        _listeners.remove(listener);
    }
}