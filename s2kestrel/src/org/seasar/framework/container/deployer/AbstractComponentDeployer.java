package org.seasar.framework.container.deployer;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.assembler.AssemblerFactory;
import org.seasar.framework.container.assembler.ConstructorAssembler;
import org.seasar.framework.container.assembler.MethodAssembler;
import org.seasar.framework.container.assembler.PropertyAssembler;

/**
 * @author higa
 *
 */
public abstract class AbstractComponentDeployer implements ComponentDeployer {
	private ComponentDef componentDef_;
	private ConstructorAssembler constructorAssembler_;
	private PropertyAssembler propertyAssembler_;
	private MethodAssembler initMethodAssembler_;
	private MethodAssembler destroyMethodAssembler_;

	public AbstractComponentDeployer(ComponentDef componentDef) {
		componentDef_ = componentDef;
		setupAssembler();
	}

	protected final ComponentDef getComponentDef() {
		return componentDef_;
	}

	protected final ConstructorAssembler getConstructorAssembler() {
		return constructorAssembler_;
	}

	protected final PropertyAssembler getPropertyAssembler() {
		return propertyAssembler_;
	}

	protected final MethodAssembler getInitMethodAssembler() {
		return initMethodAssembler_;
	}

	protected final MethodAssembler getDestroyMethodAssembler() {
		return destroyMethodAssembler_;
	}

	protected void setupAssembler() {
		constructorAssembler_ = AssemblerFactory.createConstructorAssembler(componentDef_);
		propertyAssembler_ = AssemblerFactory.createPropertyAssembler(componentDef_);
		initMethodAssembler_ = AssemblerFactory.createInitMethodAssembler(componentDef_);
		destroyMethodAssembler_ = AssemblerFactory.createDestroyMethodAssembler(componentDef_);
	}
}
