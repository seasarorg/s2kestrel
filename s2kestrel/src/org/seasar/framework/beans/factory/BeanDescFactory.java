package org.seasar.framework.beans.factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.impl.BeanDescImpl;

/**
 * @author higa
 *
 * BeanDescÇçÏê¨ÇµÇ‹Ç∑ÅB
 */
public final class BeanDescFactory {

	private static Map beanDescCache_ = Collections.synchronizedMap(new HashMap());

	/**
	 * SingletonÇÃÇΩÇﬂprivate
	 */
	private BeanDescFactory() {
	}

	public static BeanDesc getBeanDesc(Class clazz) {
		BeanDesc beanDesc = (BeanDesc) beanDescCache_.get(clazz);
		if (beanDesc == null) {
			beanDesc = new BeanDescImpl(clazz);
			beanDescCache_.put(clazz, beanDesc);
		}
		return beanDesc;
	}
}
