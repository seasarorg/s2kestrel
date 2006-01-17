package org.seasar.framework.aop;

/**
 * @author higa
 *
 * プログラム中のどこに特定のコード(Advice)を挿入するのかを定義します。<br />
 */
public interface Pointcut {
	
	/**
	 * このメソッドがtrueを返したメソッドに対してAdviceが適用されます。
	 * @param methodName
	 * @return Adviceが適用されるかどうかを返します。
	 */
	public boolean isApplied(String methodName);
}
