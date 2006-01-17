package org.seasar.framework.exception;

import java.lang.reflect.InvocationTargetException;


/**
 * @author higa
 *
 * InvocationTargetException�����b�v������s����O�ł��B
 */
public class InvocationTargetRuntimeException extends SRuntimeException {

	private Class targetClass_;

	public InvocationTargetRuntimeException(
		Class targetClass,
		InvocationTargetException cause) {

		super(
			"ESSR0043",
			new Object[] { targetClass.getName(), cause.getTargetException()},
			cause.getTargetException());
		targetClass_ = targetClass;
	}
	
	public Class getTargetClass() {
		return targetClass_;
	}
}
