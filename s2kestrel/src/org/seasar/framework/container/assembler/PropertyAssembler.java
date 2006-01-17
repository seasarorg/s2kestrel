package org.seasar.framework.container.assembler;

import org.seasar.framework.beans.IllegalPropertyRuntimeException;

/**
 * @author higa
 *
 */
public interface PropertyAssembler {

	public void assemble(Object component)
		throws IllegalPropertyRuntimeException;
}
