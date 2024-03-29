package org.seasar.framework.util;

import java.lang.reflect.Array;

import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * @author higa
 *  
 */
public class ArrayUtil {

	private ArrayUtil() {
	}

	public static Object[] add(Object[] array, Object obj) {
		if (array == null) {
			throw new EmptyRuntimeException("array");
		}
		Object[] newArray = (Object[]) Array.newInstance(array.getClass()
				.getComponentType(), array.length + 1);
		System.arraycopy(array, 0, newArray, 0, array.length);
		newArray[array.length] = obj;
		return newArray;
	}
	
	public static Object[] add(final Object a[], final Object[] b) {
        if (a != null && b != null) {
            if (a.length != 0 && b.length != 0) {
                Object[] array = (Object[]) Array.newInstance(
                    a.getClass().getComponentType(), a.length + b.length);
                System.arraycopy(a, 0, array, 0, a.length);
                System.arraycopy(b, 0, array, a.length, b.length);
                return array;
            } else if (b.length == 0) {
                return a;
            } else {
                return b;
            }
        } else if (b == null) {
            return a;
        } else {
            return b;
        }
    }

	public static int indexOf(Object[] array, Object obj) {
		if (array != null) {
			for (int i = 0; i < array.length; ++i) {
				Object o = array[i];
				if (o != null) {
					if (o.equals(obj)) {
						return i;
					}
				} else if (obj == null) {
					return i;

				}
			}
		}
		return -1;
	}

	public static Object[] remove(Object[] array, Object obj) {
		int index = indexOf(array, obj);
		if (index < 0) {
			return array;
		}
		Object[] newArray = (Object[]) Array.newInstance(array.getClass()
				.getComponentType(), array.length - 1);
		if (index > 0) {
			System.arraycopy(array, 0, newArray, 0, index);
		}
		if (index < array.length - 1) {
			System.arraycopy(array, index + 1, newArray, index, newArray.length
					- index);
		}
		return newArray;
	}
}