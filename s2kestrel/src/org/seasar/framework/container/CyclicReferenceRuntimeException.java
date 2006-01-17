package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * コンポーネントの循環参照が起きたときの実行時例外
 */
public class CyclicReferenceRuntimeException extends SRuntimeException {

	private Class componentClass_;
	
	/**
	 * @param componentClasses
	 */
	public CyclicReferenceRuntimeException(
		Class componentClass) {
		super(
			"ESSR0047",
			new Object[] {
				componentClass.getName()});
		componentClass_ = componentClass;
	}
	
	public Class getComponentClass() {
		return componentClass_;
	}
}