package org.seasar.extension.j2ee;

import java.lang.reflect.Method;

import javax.transaction.TransactionManager;

import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.MethodUtil;

/**
 * @author higa
 *
 */
public final class SingletonTransactionManager
	extends TransactionManagerWrapper {

	private String className_;
	private String methodName_;

	public SingletonTransactionManager(String className, String methodName) {
		className_ = className;
		methodName_ = methodName;
		Class clazz = ClassUtil.forName(className);
		Method method = ClassUtil.getMethod(clazz, methodName, null);
		setPhysicalTransactionManager(
			(TransactionManager) MethodUtil.invoke(method, clazz, null));
	}

	public String getClassName() {
		return className_;
	}

	public String getMethodName() {
		return methodName_;
	}
}
