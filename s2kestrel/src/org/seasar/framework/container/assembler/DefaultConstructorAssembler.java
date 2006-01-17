package org.seasar.framework.container.assembler;

import org.seasar.framework.beans.ConstructorNotFoundRuntimeException;
import org.seasar.framework.container.ComponentDef;

/**
 * @author higa
 *
 */
public class DefaultConstructorAssembler
	extends AbstractConstructorAssembler {

	/**
	 * @param componentDef
	 */
	public DefaultConstructorAssembler(ComponentDef componentDef) {
		super(componentDef);
	}

	public Object assemble() throws ConstructorNotFoundRuntimeException {
		return assembleDefault();
	}
}
