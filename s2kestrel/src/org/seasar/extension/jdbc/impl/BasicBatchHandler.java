package org.seasar.extension.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.seasar.extension.jdbc.BatchHandler;
import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ConnectionUtil;
import org.seasar.framework.util.PreparedStatementUtil;
import org.seasar.framework.util.StatementUtil;

/**
 * @author higa
 *  
 */
public class BasicBatchHandler extends BasicHandler implements BatchHandler {

	private static Logger logger_ = Logger.getLogger(BasicBatchHandler.class);

	private int batchSize_ = -1;

	public BasicBatchHandler() {
	}

	public BasicBatchHandler(DataSource dataSource, String sql, int batchSize) {
		this(dataSource, sql, batchSize, BasicStatementFactory.INSTANCE);
	}

	public BasicBatchHandler(DataSource dataSource, String sql, int batchSize,
			StatementFactory statementFactory) {

		setDataSource(dataSource);
		setSql(sql);
		setBatchSize(batchSize);
		setStatementFactory(statementFactory);
	}

	public int getBatchSize() {
		return batchSize_;
	}

	public void setBatchSize(int batchSize) {
		batchSize_ = batchSize;
	}

	public int execute(List list) throws SQLRuntimeException {
		if (list.size() == 0) {
			return 0;
		}
		Object[] args = (Object[]) list.get(0);
		return execute(list, getArgTypes(args));
	}

	public int execute(List list, Class[] argTypes) throws SQLRuntimeException {
		Connection connection = getConnection();
		try {
			return execute(connection, list, argTypes);
		} finally {
			ConnectionUtil.close(connection);
		}
	}

	protected int execute(Connection connection, List list, Class[] argTypes) {
		PreparedStatement ps = prepareStatement(connection);
		int batchSize = batchSize_ > 0 ? batchSize_ : list.size();
		try {
			for (int i = 0, j = 0; i < list.size(); ++i) {
				Object[] args = (Object[]) list.get(i);
				if (logger_.isDebugEnabled()) {
					logger_.debug(getCompleteSql(args));
				}
				bindArgs(ps, args, argTypes);
				PreparedStatementUtil.addBatch(ps);
				if (j == batchSize - 1 || i == list.size() - 1) {
					PreparedStatementUtil.executeBatch(ps);
					j = 0;
				} else {
					++j;
				}
			}
			return list.size();
		} finally {
			StatementUtil.close(ps);
		}
	}
}