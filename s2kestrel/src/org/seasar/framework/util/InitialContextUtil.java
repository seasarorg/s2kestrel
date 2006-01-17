package org.seasar.framework.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.seasar.framework.exception.NamingRuntimeException;

/**
 * @author higa
 *
 */
public final class InitialContextUtil {

	private InitialContextUtil() {
	}

	public static final Object lookup(InitialContext ctx, String jndiName)
		throws NamingRuntimeException {

		try {
			return ctx.lookup(jndiName);
		} catch (NamingException ex) {
			throw new NamingRuntimeException(ex);
		}
	}
}
