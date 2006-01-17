package org.seasar.kestrel.dbcp;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Satoshi Kimura
 */
public class Assert {
    public static void assertDefineTransactionIsolationLevel(int level) throws SQLException {
        if (level == Connection.TRANSACTION_NONE) {
        } else if (level == Connection.TRANSACTION_NONE) {
        } else if (level == Connection.TRANSACTION_READ_COMMITTED) {
        } else if (level == Connection.TRANSACTION_READ_UNCOMMITTED) {
        } else if (level == Connection.TRANSACTION_REPEATABLE_READ) {
        } else if (level == Connection.TRANSACTION_SERIALIZABLE) {
        } else {
            throw new SQLException("TransactionIsolationLevel is not defined. argLevel=" + level);
        }
    }

}
