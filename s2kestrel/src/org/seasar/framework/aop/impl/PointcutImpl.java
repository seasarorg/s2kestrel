package org.seasar.framework.aop.impl;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Matcher;
import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.exception.EmptyRuntimeException;
import org.seasar.kestrel.regex.RegexUtil;

/**
 * @author higa
 * @author Satoshi Kimura
 *
 */
public final class PointcutImpl implements Pointcut, Serializable {

	static final long serialVersionUID = 0L;
	private String[] methodNames_;
	private Pattern[] patterns_;

	public PointcutImpl(Class targetClass)
		throws EmptyRuntimeException {

		if (targetClass == null) {
			throw new EmptyRuntimeException("targetClass");
		}
		setMethodNames(getMethodNames(targetClass));
	}

	public PointcutImpl(String[] methodNames)
		throws EmptyRuntimeException {

		if (methodNames == null || methodNames.length == 0) {
			throw new EmptyRuntimeException("methodNames");
		}
		setMethodNames(methodNames);
	}

	public boolean isApplied(String methodName) {
		for (int i = 0; i < patterns_.length; ++i) {
			if (new Perl5Matcher().matches(methodName,patterns_[i])) {
				return true;
			}
		}
		return false;
	}
	
	public String[] getMethodNames() {
		return methodNames_;
	}
	
	private void setMethodNames(String[] methodNames) {
		methodNames_ = methodNames;
		patterns_ = new Pattern[methodNames.length];
		for (int i = 0; i < patterns_.length; ++i) {
            patterns_[i] = RegexUtil.compile(methodNames[i]);
		}
	}

	private static String[] getMethodNames(Class targetClass) {
		Set methodNameSet = new HashSet();
		if (targetClass.isInterface()) {
			addInterfaceMethodNames(methodNameSet, targetClass);
		}
		for (Class clazz = targetClass;
			clazz != Object.class && clazz != null;
			clazz = clazz.getSuperclass()) {
			Class[] interfaces = clazz.getInterfaces();
			for (int i = 0; i < interfaces.length; ++i) {
				addInterfaceMethodNames(methodNameSet, interfaces[i]);
			}
		}
		return (String[]) methodNameSet.toArray(
			new String[methodNameSet.size()]);
	
	}
	
	private static void addInterfaceMethodNames(Set methodNameSet, Class interfaceClass) {	
		Method[] methods = interfaceClass.getDeclaredMethods();
		for (int j = 0; j < methods.length; j++) {
			methodNameSet.add(methods[j].getName());
		}
		Class[] interfaces = interfaceClass.getInterfaces();
		for (int i = 0; i < interfaces.length; ++i) {
			addInterfaceMethodNames(methodNameSet, interfaces[i]);
		}
	}
}
