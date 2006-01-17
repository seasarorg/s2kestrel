package org.seasar.kestrel.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Satoshi Kimura
 */
public class ResultSetWrapper extends org.seasar.extension.jdbc.impl.ResultSetWrapper {
    private Statement statement;

    /**
     * @param original
     * @param statement
     */
    public ResultSetWrapper(ResultSet original, Statement statement) {
        super(original);
        this.statement = statement;
    }

    public Statement getStatement() {
        return statement;
    }

}
