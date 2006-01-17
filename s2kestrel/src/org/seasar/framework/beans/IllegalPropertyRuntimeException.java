package org.seasar.framework.beans;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 */
public class IllegalPropertyRuntimeException
	extends SRuntimeException {

	private Class componentClass_;
	private String propertyName_;

	public IllegalPropertyRuntimeException(
		Class componentClass,
		String propertyName,
		Throwable cause) {
		super(
			"ESSR0059",
			new Object[] { componentClass.getName(), propertyName, cause },
			cause);
		componentClass_ = componentClass;
		propertyName_ = propertyName;
	}

	public Class getComponentClass() {
		return componentClass_;
	}
	
	public String getPropertyName() {
		return propertyName_;
	}
}