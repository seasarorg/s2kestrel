package org.seasar.extension.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.seasar.extension.jdbc.StatementFactory;
import org.seasar.extension.jdbc.ValueType;
import org.seasar.extension.jdbc.types.ValueTypes;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.framework.exception.SQLRuntimeException;
import org.seasar.framework.util.DataSourceUtil;

/**
 * @author higa
 *  
 */
public class BasicHandler {

	private DataSource dataSource_;

	private String sql_;

	private StatementFactory statementFactory_ = BasicStatementFactory.INSTANCE;

	public BasicHandler() {
	}

	public BasicHandler(DataSource ds, String sql) {
		this(ds, sql, BasicStatementFactory.INSTANCE);
	}

	public BasicHandler(DataSource ds, String sql,
			StatementFactory statementFactory) {

		setDataSource(ds);
		setSql(sql);
		setStatementFactory(statementFactory);
	}

	public DataSource getDataSource() {
		return dataSource_;
	}

	public void setDataSource(DataSource dataSource) {
		dataSource_ = dataSource;
	}

	public String getSql() {
		return sql_;
	}

	public void setSql(String sql) {
		sql_ = sql;
	}

	public StatementFactory getStatementFactory() {
		return statementFactory_;
	}

	public void setStatementFactory(StatementFactory statementFactory) {
		statementFactory_ = statementFactory;
	}

	protected Connection getConnection() {
		if (dataSource_ == null) {
			throw new EmptyRuntimeException("dataSource");
		}
		return DataSourceUtil.getConnection(dataSource_);
	}

	protected PreparedStatement prepareStatement(Connection connection) {
		if (sql_ == null) {
			throw new EmptyRuntimeException("sql");
		}
		return statementFactory_.createPreparedStatement(connection,
				sql_);
	}

	protected void bindArgs(PreparedStatement ps, Object[] args,
			Class[] argTypes) {

		if (args == null) {
			return;
		}
		for (int i = 0; i < args.length; ++i) {
			ValueType valueType = ValueTypes.getValueType(argTypes[i]);
			try {
				valueType.bindValue(ps, i + 1, args[i]);
			} catch (SQLException ex) {
				throw new SQLRuntimeException(ex);
			}
		}
	}

	protected Class[] getArgTypes(Object[] args) {
		if (args == null) {
			return null;
		}
		Class[] argTypes = new Class[args.length];
		for (int i = 0; i < args.length; ++i) {
			Object arg = args[i];
			if (arg != null) {
				argTypes[i] = arg.getClass();
			}
		}
		return argTypes;
	}

	protected String getCompleteSql(Object[] args) {
		if (args == null || args.length == 0) {
			return sql_;
		}
		StringBuffer buf = new StringBuffer(200);
		int pos = 0;
		int pos2 = 0;
		int index = 0;
		while (true) {
			pos = sql_.indexOf('?', pos2);
			if (pos > 0) {
				buf.append(sql_.substring(pos2, pos));
				buf.append(getBindVariableText(args[index++]));
				pos2 = pos + 1;
			} else {
				buf.append(sql_.substring(pos2));
				break;
			}
		}
		return buf.toString();
	}

	protected String getBindVariableText(Object bindVariable) {
		if (bindVariable instanceof String) {
			return "'" + bindVariable + "'";
		} else if (bindVariable instanceof Number) {
			return bindVariable.toString();
		} else if (bindVariable instanceof Timestamp) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
			return "'" + sdf.format((java.util.Date) bindVariable) + "'";
		} else if (bindVariable instanceof java.util.Date) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return "'" + sdf.format((java.util.Date) bindVariable) + "'";
		} else if (bindVariable instanceof Boolean) {
			return bindVariable.toString();
		} else if (bindVariable == null) {
			return "null";
		} else {
			return "'" + bindVariable.toString() + "'";
		}
	}
}