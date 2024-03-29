package org.seasar.extension.jdbc.types;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.seasar.extension.jdbc.ValueType;

/**
 * 
 * @author higa
 * @author Satoshi Kimura
 */
public final class ValueTypes {

	public final static ValueType STRING = new StringType();
	public final static ValueType SHORT = new ShortType();
	public final static ValueType INTEGER = new IntegerType();
	public final static ValueType LONG = new LongType();
	public final static ValueType FLOAT = new FloatType();
	public final static ValueType DOUBLE = new DoubleType();
	public final static ValueType BIGDECIMAL = new BigDecimalType();
	public final static ValueType TIME = new TimeType();
	public final static ValueType SQLDATE = new SqlDateType();
	public final static ValueType TIMESTAMP = new TimestampType();
	public final static ValueType BINARY = new BinaryType();
	public final static ValueType BOOLEAN = new BooleanType();
	public final static ValueType OBJECT = new ObjectType();

	private static final Class BYTE_ARRAY_CLASS = new byte[0].getClass(); 
	private static Map types_ = new HashMap();

	static {
		registerValueType(String.class, STRING);
		registerValueType(short.class, SHORT);
		registerValueType(Short.class, SHORT);
		registerValueType(int.class, INTEGER);
		registerValueType(Integer.class, INTEGER);
		registerValueType(long.class, LONG);
		registerValueType(Long.class, LONG);
		registerValueType(float.class, FLOAT);
		registerValueType(Float.class, FLOAT);
		registerValueType(double.class, DOUBLE);
		registerValueType(Double.class, DOUBLE);
		registerValueType(BigDecimal.class, BIGDECIMAL);
		registerValueType(java.sql.Date.class, SQLDATE);
		registerValueType(java.sql.Time.class, TIME);
		registerValueType(java.util.Date.class, TIMESTAMP);
		registerValueType(Timestamp.class, TIMESTAMP);
		registerValueType(Calendar.class, TIMESTAMP);
		registerValueType(BYTE_ARRAY_CLASS, BINARY);
		registerValueType(boolean.class, BOOLEAN);
		registerValueType(Boolean.class, BOOLEAN);
	}

	private ValueTypes() {
	}
	
	public static void registerValueType(Class clazz, ValueType valueType) {
		synchronized (types_) {
			types_.put(clazz, valueType);
		}
	}

	public static ValueType getValueType(Object obj) {
		if (obj == null) {
			return OBJECT;
		}
		return getValueType(obj.getClass());
	}

	public static ValueType getValueType(Class clazz) {
		if (clazz == null) {
			return OBJECT;
		}
		ValueType valueType = getValueType0(clazz);
		if (valueType != null) {
			return valueType;
		}
		return OBJECT;
	}
	
	private static ValueType getValueType0(Class clazz) {
		synchronized (types_) {
			return (ValueType) types_.get(clazz);
		}
	}

	public static ValueType getValueType(int type) {
		switch (type) {
			case Types.TINYINT :
			case Types.SMALLINT :
				return getValueType(Short.class);
			case Types.INTEGER :
				return getValueType(Integer.class);
			case Types.BIGINT :
				return getValueType(Long.class);
			case Types.REAL :
			case Types.FLOAT :
				return getValueType(Float.class);
			case Types.DOUBLE :
				return getValueType(Double.class);
			case Types.DECIMAL :
			case Types.NUMERIC :
				return getValueType(BigDecimal.class);
			case Types.DATE :
				return getValueType(Timestamp.class);
			case Types.TIME :
				return getValueType(java.sql.Time.class);
			case Types.TIMESTAMP :
				return getValueType(Timestamp.class);
			case Types.BINARY :
			case Types.VARBINARY :
			case Types.LONGVARBINARY :
				return getValueType(BYTE_ARRAY_CLASS);
			case Types.CHAR :
			case Types.LONGVARCHAR :
			case Types.VARCHAR :
				return getValueType(String.class);
			//case Types.BOOLEAN :
			//	return getValueType(Boolean.class);
			default :
				return OBJECT;
		}
	}
}
