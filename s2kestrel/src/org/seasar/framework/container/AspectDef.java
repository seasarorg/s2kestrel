package org.seasar.framework.container;

import org.seasar.framework.aop.Aspect;

/**
 * @author higa
 *
 * Aspect‚ğ’è‹`‚µ‚Ü‚·B
 */
public interface AspectDef extends ArgDef {

	public Aspect getAspect();
}