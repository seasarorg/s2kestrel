package org.seasar.framework.container.factory;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.PropertyDef;
import org.seasar.framework.container.impl.PropertyDefImpl;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class PropertyTagHandler extends TagHandler {

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
	 */
	public void start(TagHandlerContext context, Attributes attributes) {
		String name = attributes.getValue("name");
		if (name == null) {
			throw new TagAttributeNotDefinedRuntimeException(
				"property",
				"name");
		}
		context.push(createPropertyDef(name));
	}

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#end(org.seasar.framework.xml.sax.handler.TagHandlerContext, java.lang.String)
	 */
	public void end(TagHandlerContext context, String body) {
		PropertyDef propertyDef = (PropertyDef) context.pop();
		if (!StringUtil.isEmpty(body)) {
			propertyDef.setExpression(body);
		}
		ComponentDef componentDef = (ComponentDef) context.peek();
		componentDef.addPropertyDef(propertyDef);
	}

	protected PropertyDefImpl createPropertyDef(String name) {
		return new PropertyDefImpl(name);
	}
}
