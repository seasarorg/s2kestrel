package org.seasar.framework.container.factory;

import org.seasar.framework.container.ArgDef;
import org.seasar.framework.container.ArgDefAware;
import org.seasar.framework.container.impl.ArgDefImpl;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class ArgTagHandler extends TagHandler {

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
	 */
	public void start(TagHandlerContext context, Attributes attributes) {
		context.push(new ArgDefImpl());
	}

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#end(org.seasar.framework.xml.sax.handler.TagHandlerContext, java.lang.String)
	 */
	public void end(TagHandlerContext context, String body) {
		ArgDef argDef = (ArgDef) context.pop();
		if (!StringUtil.isEmpty(body)) {
			argDef.setExpression(body);
		}
		ArgDefAware argDefAware = (ArgDefAware) context.peek();
		argDefAware.addArgDef(argDef);
	}

}
