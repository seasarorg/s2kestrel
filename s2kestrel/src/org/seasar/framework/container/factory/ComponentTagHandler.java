package org.seasar.framework.container.factory;

import org.seasar.framework.container.ArgDef;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.impl.ComponentDefImpl;
import org.seasar.framework.container.util.InstanceModeUtil;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class ComponentTagHandler extends TagHandler {

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
	 */
	public void start(TagHandlerContext context, Attributes attributes) {
		String className = attributes.getValue("class");
		Class componentClass = null;
		if (className != null) {
			componentClass = ClassUtil.forName(className);
		}
		String name = attributes.getValue("name");
		ComponentDef componentDef = createComponentDef(componentClass, name);
		String instanceMode = attributes.getValue("instance");
		if (instanceMode != null) {
			componentDef.setInstanceMode(instanceMode);
		}
		String autoBindingMode = attributes.getValue("autoBinding");
		if (autoBindingMode != null) {
			componentDef.setAutoBindingMode(autoBindingMode);
		}
		context.push(componentDef);
	}

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#end(org.seasar.framework.xml.sax.handler.TagHandlerContext, java.lang.String)
	 */
	public void end(TagHandlerContext context, String body) {
		ComponentDef componentDef = (ComponentDef) context.pop();
		String expression = null;
		if (body != null) {
			expression = body.trim();
			if (!StringUtil.isEmpty(expression)) {
				componentDef.setExpression(expression);
			} else {
				expression = null;
			}
		}
		if (componentDef.getComponentClass() == null
			&& !InstanceModeUtil.isOuter(componentDef.getInstanceMode())
			&& expression == null) {
			throw new TagAttributeNotDefinedRuntimeException(
				"component",
				"class");
		}
		if (context.peek() instanceof S2Container) {
			S2Container container = (S2Container) context.peek();
			container.register(componentDef);
		} else {
			ArgDef argDef = (ArgDef) context.peek();
			argDef.setChildComponentDef(componentDef);
		}
	}

	protected ComponentDef createComponentDef(Class componentClass, String name) {
		return new ComponentDefImpl(componentClass, name);
	}
}
