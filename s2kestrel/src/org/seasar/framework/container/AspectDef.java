package org.seasar.framework.container;

import org.seasar.framework.aop.Aspect;

/**
 * @author higa
 *
 * Aspect���`���܂��B
 */
public interface AspectDef extends ArgDef {

	public Aspect getAspect();
}