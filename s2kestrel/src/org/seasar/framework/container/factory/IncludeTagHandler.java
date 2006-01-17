package org.seasar.framework.container.factory;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class IncludeTagHandler extends TagHandler {

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
	 */
	public void start(TagHandlerContext context, Attributes attributes) {
		String path = attributes.getValue("path");
		if (path == null) {
			throw new TagAttributeNotDefinedRuntimeException("include", "path");
		}
		S2Container container = (S2Container) context.peek();
		S2ContainerFactory.include(container, path);
	}
}
