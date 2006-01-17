package org.seasar.framework.util;

import java.math.BigDecimal;

public final class MathUtil {

	private MathUtil() {
	}

	public static Number add(Number arg1, Number arg2) {
		if (arg1 == null || arg2 == null) {
			return null;
		}
		if (arg1 instanceof Integer) {
			Integer i1 = (Integer) arg1;
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Integer(i1.intValue() + i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(i1.intValue()).add(b2);
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(i1.intValue() + l2.longValue());
			}
		}
		if (arg1 instanceof Long) {
			Long l1 = (Long) arg1;
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(l1.longValue() + l2.longValue());
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Long(l1.longValue() + i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(l1.longValue()).add(b2);
			}
		}
		if (arg1 instanceof BigDecimal) {
			BigDecimal b1 = (BigDecimal) arg1;
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return b1.add(b2);
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return b1.add(new BigDecimal(i2.intValue()));
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return b1.add(new BigDecimal(l2.longValue()));
			}
		}
		return new BigDecimal(arg1.doubleValue() + arg2.doubleValue());
	}

	public static Number subtract(Number arg1, Number arg2) {
		if (arg1 == null || arg2 == null) {
			return null;
		}
		if (arg1 instanceof Integer) {
			Integer i1 = (Integer) arg1;
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Integer(i1.intValue() - i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(i1.intValue()).subtract(b2);
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(i1.intValue() - l2.longValue());
			}
		}
		if (arg1 instanceof Long) {
			Long l1 = (Long) arg1;
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(l1.longValue() - l2.longValue());
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Long(l1.longValue() - i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(l1.longValue()).subtract(b2);
			}
		}
		if (arg1 instanceof BigDecimal) {
			BigDecimal b1 = (BigDecimal) arg1;
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return b1.subtract(b2);
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return b1.subtract(new BigDecimal(i2.intValue()));
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return b1.subtract(new BigDecimal(l2.longValue()));
			}
		}
		return new BigDecimal(arg1.doubleValue() - arg2.doubleValue());
	}

	public static Number multiply(Number arg1, Number arg2) {
		if (arg1 == null || arg2 == null) {
			return null;
		}
		if (arg1 instanceof Integer) {
			Integer i1 = (Integer) arg1;
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Integer(i1.intValue() * i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(i1.intValue()).multiply(b2);
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(i1.intValue() * l2.longValue());
			}
		}
		if (arg1 instanceof Long) {
			Long l1 = (Long) arg1;
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(l1.longValue() * l2.longValue());
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Long(l1.longValue() * i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(l1.longValue()).multiply(b2);
			}
		}
		if (arg1 instanceof BigDecimal) {
			BigDecimal b1 = (BigDecimal) arg1;
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return b1.multiply(b2);
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return b1.multiply(new BigDecimal(i2.intValue()));
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return b1.multiply(new BigDecimal(l2.longValue()));
			}
		}
		return new BigDecimal(arg1.doubleValue() * arg2.doubleValue());
	}

	public static Number divide(Number arg1, Number arg2) {
		if (arg1 == null || arg2 == null) {
			return null;
		}
		if (arg1 instanceof Integer) {
			Integer i1 = (Integer) arg1;
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Integer(i1.intValue() / i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(i1.intValue() / b2.doubleValue());
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(i1.intValue() / l2.longValue());
			}
		}
		if (arg1 instanceof Long) {
			Long l1 = (Long) arg1;
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(l1.longValue() / l2.longValue());
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Long(l1.longValue() / i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(l1.longValue() / b2.doubleValue());
			}
		}
		if (arg1 instanceof BigDecimal) {
			BigDecimal b1 = (BigDecimal) arg1;
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(b1.doubleValue() / b2.doubleValue());
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new BigDecimal(b1.doubleValue() / i2.intValue());
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new BigDecimal(b1.doubleValue() / l2.longValue());
			}
		}
		return new BigDecimal(arg1.doubleValue() / arg2.doubleValue());
	}

	public static Number mod(Number arg1, Number arg2) {
		if (arg1 == null || arg2 == null) {
			return null;
		}
		if (arg1 instanceof Integer) {
			Integer i1 = (Integer) arg1;
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Integer(i1.intValue() % i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(i1.intValue() % b2.doubleValue());
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(i1.intValue() % l2.longValue());
			}
		}
		if (arg1 instanceof Long) {
			Long l1 = (Long) arg1;
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new Long(l1.longValue() % l2.longValue());
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new Long(l1.longValue() % i2.intValue());
			}
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(l1.longValue() % b2.doubleValue());
			}
		}
		if (arg1 instanceof BigDecimal) {
			BigDecimal b1 = (BigDecimal) arg1;
			if (arg2 instanceof BigDecimal) {
				BigDecimal b2 = (BigDecimal) arg2;
				return new BigDecimal(b1.doubleValue() % b2.doubleValue());
			}
			if (arg2 instanceof Integer) {
				Integer i2 = (Integer) arg2;
				return new BigDecimal(b1.doubleValue() % i2.intValue());
			}
			if (arg2 instanceof Long) {
				Long l2 = (Long) arg2;
				return new BigDecimal(b1.doubleValue() % l2.longValue());
			}
		}
		return new BigDecimal(arg1.doubleValue() % arg2.doubleValue());
	}
}
