package org.seasar.framework.container.factory;

import org.seasar.framework.container.MetaDef;
import org.seasar.framework.container.MetaDefAware;
import org.seasar.framework.container.impl.MetaDefImpl;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class MetaTagHandler extends TagHandler {

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
	 */
	public void start(TagHandlerContext context, Attributes attributes) {
		String name = attributes.getValue("name");
		context.push(createMetaDef(name));
	}

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#end(org.seasar.framework.xml.sax.handler.TagHandlerContext, java.lang.String)
	 */
	public void end(TagHandlerContext context, String body) {
		MetaDef metaDef = (MetaDef) context.pop();
		if (!StringUtil.isEmpty(body)) {
			metaDef.setExpression(body);
		}
		MetaDefAware metaDefAware = (MetaDefAware) context.peek();
		metaDefAware.addMetaDef(metaDef);
	}

	protected MetaDefImpl createMetaDef(String name) {
		return new MetaDefImpl(name);
	}
}
