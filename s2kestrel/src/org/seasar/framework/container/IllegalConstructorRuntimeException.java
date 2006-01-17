package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * コンポーネントのコンストラクタ引数の設定に失敗ときの実行時例外
 */
public class IllegalConstructorRuntimeException
	extends SRuntimeException {

	private Class componentClass_;

	public IllegalConstructorRuntimeException(
		Class componentClass,
		Throwable cause) {
		super(
			"ESSR0058",
			new Object[] { componentClass.getName(), cause },
			cause);
		componentClass_ = componentClass;
	}

	public Class getComponentClass() {
		return componentClass_;
	}
}