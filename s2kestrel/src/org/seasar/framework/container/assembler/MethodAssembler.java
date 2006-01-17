package org.seasar.framework.container.assembler;

import org.seasar.framework.container.IllegalMethodRuntimeException;

/**
 * @author higa
 *
 */
public interface MethodAssembler {

	public void assemble(Object component)
		throws IllegalMethodRuntimeException;
}
