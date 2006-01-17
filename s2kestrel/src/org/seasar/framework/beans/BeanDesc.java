package org.seasar.framework.beans;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author higa
 *
 */
public interface BeanDesc {

	public Class getBeanClass();
	
	public boolean hasPropertyDesc(String propertyName);

	public PropertyDesc getPropertyDesc(String propertyName)
		throws PropertyNotFoundRuntimeException;
	
	public PropertyDesc getPropertyDesc(int index);
	
	public int getPropertyDescSize();
	
	public boolean hasField(String fieldName);
	
	public Field getField(String fieldName) throws FieldNotFoundRuntimeException;
	
	public Object newInstance(Object[] args)
		throws ConstructorNotFoundRuntimeException;
		
	public Constructor getSuitableConstructor(Object[] args)
			throws ConstructorNotFoundRuntimeException;
	
	public Object invoke(Object target, String methodName, Object[] args)
		throws MethodNotFoundRuntimeException;
		
	public Method[] getMethods(String methodName)
		throws MethodNotFoundRuntimeException;
	
	public boolean hasMethod(String methodName);
		
	public String[] getMethodNames();
}
