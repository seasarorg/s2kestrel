package org.seasar.framework.beans;

import java.lang.reflect.Method;

/**
 * @author higa
 *
 */
public interface PropertyDesc {

	public String getPropertyName();

	public Class getPropertyType();

	public Method getReadMethod();

	public void setReadMethod(Method readMethod);
	
	public boolean hasReadMethod();

	public Method getWriteMethod();

	public void setWriteMethod(Method writeMethod);
	
	public boolean hasWriteMethod();

	public Object getValue(Object target);

	public void setValue(Object target, Object value);
	
	public Object convertIfNeed(Object value);
}
