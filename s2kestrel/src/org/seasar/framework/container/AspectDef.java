package org.seasar.framework.container;

import org.seasar.framework.aop.Aspect;

/**
 * @author higa
 *
 * Aspectを定義します。
 */
public interface AspectDef extends ArgDef {

	public Aspect getAspect();
}