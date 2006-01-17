package org.seasar.extension.j2ee;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.seasar.framework.util.InitialContextUtil;

/**
 * @author higa
 *
 */
public final class JndiDataSource extends DataSourceWrapper {

	private InitialContext initialContext_;
	private String jndiName_;

	public JndiDataSource(InitialContext initialContext, String jndiName) {
		initialContext_ = initialContext;
		jndiName_ = jndiName;
		setPhysicalDataSource(
			(DataSource) InitialContextUtil.lookup(initialContext, jndiName));
	}
	
	public InitialContext getInitialContext() {
		return initialContext_;
	}

	public String getJndiName() {
		return jndiName_;
	}
}