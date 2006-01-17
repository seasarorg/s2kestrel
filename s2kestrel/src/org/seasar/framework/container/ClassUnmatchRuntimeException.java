package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * 定義されたコンポーネントのクラスと実際のコンポーネントのクラス
 * が異なるときの実行時例外
 */
public class ClassUnmatchRuntimeException extends SRuntimeException {

	private Class componentClass_;
	private Class realComponentClass_;
	
	/**
	 * @param componentKey
	 */
	public ClassUnmatchRuntimeException(
		Class componentClass,
		Class realComponentClass) {
		super("ESSR0069", new Object[] { componentClass.getName(),
			realComponentClass != null ? realComponentClass.getName() : "null"});
		componentClass_ = componentClass;
		realComponentClass_ = realComponentClass;
	}
	
	public Class getComponentClass() {
		return componentClass_;
	}
	
	public Class getRealComponentClass() {
		return realComponentClass_;
	}
}