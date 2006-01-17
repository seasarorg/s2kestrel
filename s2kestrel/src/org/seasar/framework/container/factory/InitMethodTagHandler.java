package org.seasar.framework.container.factory;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.InitMethodDef;
import org.seasar.framework.container.impl.InitMethodDefImpl;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class InitMethodTagHandler extends MethodTagHandler {

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
	 */
	public void start(TagHandlerContext context, Attributes attributes) {
		String name = attributes.getValue("name");
		context.push(createInitMethodDef(name));
	}

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#end(org.seasar.framework.xml.sax.handler.TagHandlerContext, java.lang.String)
	 */
	public void end(TagHandlerContext context, String body) {
		InitMethodDef methodDef = (InitMethodDef) context.pop();
		processExpression(methodDef, body, "initMethod");
		ComponentDef componentDef = (ComponentDef) context.peek();
		componentDef.addInitMethodDef(methodDef);
	}

	protected InitMethodDefImpl createInitMethodDef(String name) {
		return new InitMethodDefImpl(name);
    }
}
