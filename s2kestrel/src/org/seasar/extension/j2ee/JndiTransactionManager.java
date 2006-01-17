package org.seasar.extension.j2ee;

import javax.naming.InitialContext;
import javax.transaction.TransactionManager;

import org.seasar.framework.util.InitialContextUtil;

/**
 * @author higa
 *
 */
public final class JndiTransactionManager
	extends TransactionManagerWrapper {

	private InitialContext initialContext_;
	private String jndiName_;

	public JndiTransactionManager(
		InitialContext initialContext,
		String jndiName) {

		initialContext_ = initialContext;
		jndiName_ = jndiName;
		setPhysicalTransactionManager(
			(TransactionManager) InitialContextUtil.lookup(
				initialContext,
				jndiName));
	}
	
	public InitialContext getInitialContext() {
		return initialContext_;
	}
	
	public String getJndiName() {
		return jndiName_;
	}
}
