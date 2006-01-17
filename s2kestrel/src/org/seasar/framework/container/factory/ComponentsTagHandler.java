package org.seasar.framework.container.factory;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class ComponentsTagHandler extends TagHandler {
	protected Class containerImplClass_ = S2ContainerImpl.class;
	
	public Class getContainerImplClass() {
		return containerImplClass_;
	}
	
	public void setContainerImplClass(Class containerImplClass) {
		containerImplClass_ = containerImplClass;
	}
	
	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
	 */
	public void start(TagHandlerContext context, Attributes attributes) {
		S2Container container = createContainer();
		String path = (String) context.getParameter("path");
		container.setPath(path);
		String namespace = attributes.getValue("namespace");
		if (!StringUtil.isEmpty(namespace)) {
			container.setNamespace(namespace); 
		}

		S2Container parent = (S2Container) context.getParameter("parent");
		if (parent != null) {
			container.setRoot(parent.getRoot());
		}
		context.push(container);
	}

	protected S2Container createContainer() {
		return (S2Container) ClassUtil.newInstance(containerImplClass_);
	}
}
