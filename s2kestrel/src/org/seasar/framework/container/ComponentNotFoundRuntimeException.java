package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * コンポーネントが見つからなかったときの実行時例外
 */
public class ComponentNotFoundRuntimeException extends SRuntimeException {

	private Object componentKey_;
	
	/**
	 * @param componentKey
	 */
	public ComponentNotFoundRuntimeException(Object componentKey) {
		super("ESSR0046", new Object[] { componentKey});
		componentKey_ = componentKey;
	}
	
	public Object getComponentKey() {
		return componentKey_;
	}
}