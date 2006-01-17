package org.seasar.framework.util;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.seasar.framework.exception.SQLRuntimeException;

/**
 * @author higa
 *  
 */
public final class DatabaseMetaDataUtil {

	private DatabaseMetaDataUtil() {
	}

	public static String[] getPrimaryKeys(DatabaseMetaData dbMetaData,
			String tableName) {

		Set set = getPrimaryKeySet(dbMetaData, tableName);
		return (String[]) set.toArray(new String[set.size()]);
	}

	public static Set getPrimaryKeySet(DatabaseMetaData dbMetaData,
			String tableName) {

		String schema = null;
		int index = tableName.indexOf('.');
		if (index >= 0) {
			schema = tableName.substring(0, index);
			tableName = tableName.substring(index + 1);
		}
		String convertedTableName = convertIdentifier(dbMetaData, tableName); 
		Set set = new CaseInsensitiveSet();
		addPrimaryKeys(dbMetaData, convertIdentifier(dbMetaData, schema),
				convertedTableName, set);
		if (set.size() == 0) {
			addPrimaryKeys(dbMetaData, schema, tableName, set);
		}
		if (set.size() == 0 && schema != null) {
			addPrimaryKeys(dbMetaData, null, convertedTableName, set);
			if (set.size() == 0) {
				addPrimaryKeys(dbMetaData, null, tableName, set);
			}
		}
		return set;
	}
	
	private static void addPrimaryKeys(DatabaseMetaData dbMetaData, String schema, String tableName, Set set) {
		try {
			ResultSet rs = dbMetaData.getPrimaryKeys(null, schema, tableName);
			while (rs.next()) {
				set.add(rs.getString(4));
			}
			rs.close();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}

	public static String[] getColumns(DatabaseMetaData dbMetaData,
			String tableName) {

		Set set = getColumnSet(dbMetaData, tableName);
		return (String[]) set.toArray(new String[set.size()]);
	}

	public static Set getColumnSet(DatabaseMetaData dbMetaData, String tableName) {
		String schema = null;
		int index = tableName.indexOf('.');
		if (index >= 0) {
			schema = tableName.substring(0, index);
			tableName = tableName.substring(index + 1);
		}
		String convertedTableName = convertIdentifier(dbMetaData, tableName);
		Set set = new CaseInsensitiveSet();
		addColumns(dbMetaData, convertIdentifier(dbMetaData, schema), convertedTableName, set);
		if (set.size() == 0) {
			addColumns(dbMetaData, schema, tableName, set);
		}
		if (set.size() == 0 && schema != null) {
			addColumns(dbMetaData, null, convertedTableName, set);
			if (set.size() == 0) {
				addColumns(dbMetaData, null, tableName, set);
			}
		}
		return set;
	}
	
	private static void addColumns(DatabaseMetaData dbMetaData, String schema, String tableName, Set set) {
		try {
			ResultSet rs = dbMetaData.getColumns(null, schema, tableName, null);
			while (rs.next()) {
				set.add(rs.getString(4));
			}
			rs.close();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}

	public static String convertIdentifier(DatabaseMetaData dbMetaData,
			String identifier) {

		if (identifier == null) {
			return null;
		}
		if (!supportsMixedCaseIdentifiers(dbMetaData)) {
			if (storesUpperCaseIdentifiers(dbMetaData)) {
				return identifier.toUpperCase();
			} else {
				return identifier.toLowerCase();
			}
		} else {
			return identifier;
		}
	}

	public static boolean supportsMixedCaseIdentifiers(
			DatabaseMetaData dbMetaData) {

		try {
			return dbMetaData.supportsMixedCaseIdentifiers();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}

	public static boolean storesUpperCaseIdentifiers(DatabaseMetaData dbMetaData) {
		try {
			return dbMetaData.storesUpperCaseIdentifiers();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}

	public static String getDatabaseProductName(DatabaseMetaData dbMetaData) {
		try {
			return dbMetaData.getDatabaseProductName();
		} catch (SQLException ex) {
			throw new SQLRuntimeException(ex);
		}
	}
}