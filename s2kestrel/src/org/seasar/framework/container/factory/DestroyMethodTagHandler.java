package org.seasar.framework.container.factory;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.DestroyMethodDef;
import org.seasar.framework.container.impl.DestroyMethodDefImpl;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class DestroyMethodTagHandler extends MethodTagHandler {

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
	 */
	public void start(TagHandlerContext context, Attributes attributes) {
	    String name = attributes.getValue("name");
		context.push(createDestroyMethodDef(name));
	}

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#end(org.seasar.framework.xml.sax.handler.TagHandlerContext, java.lang.String)
	 */
	public void end(TagHandlerContext context, String body) {
		DestroyMethodDef methodDef = (DestroyMethodDef) context.pop();
		processExpression(methodDef, body, "destroyMethod");
		ComponentDef componentDef = (ComponentDef) context.peek();
		componentDef.addDestroyMethodDef(methodDef);
	}

	protected DestroyMethodDefImpl createDestroyMethodDef(String name) {
        return new DestroyMethodDefImpl(name);
    }
}
