package org.seasar.framework.beans;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * クラスのプロパティが見つからなかったときの実行時例外
 */
public class PropertyNotFoundRuntimeException
	extends SRuntimeException {

	private Class targetClass_;
	private String propertyName_;

	public PropertyNotFoundRuntimeException(
		Class componentClass,
		String propertyName) {
		super(
			"ESSR0065",
			new Object[] { componentClass.getName(), propertyName});
		targetClass_ = componentClass;
		propertyName_ = propertyName;
	}

	public Class getTargetClass() {
		return targetClass_;
	}
	
	public String getPropertyName() {
		return propertyName_;
	}
}