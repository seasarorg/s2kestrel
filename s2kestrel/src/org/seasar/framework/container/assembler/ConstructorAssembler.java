package org.seasar.framework.container.assembler;

import org.seasar.framework.container.IllegalConstructorRuntimeException;

/**
 * @author higa
 *
 */
public interface ConstructorAssembler {

	public Object assemble()
		throws
			IllegalConstructorRuntimeException;
}
