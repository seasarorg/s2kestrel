package org.seasar.framework.container.factory;

import org.seasar.framework.aop.Pointcut;
import org.seasar.framework.aop.impl.PointcutImpl;
import org.seasar.framework.container.AspectDef;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.AspectDefImpl;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.xml.TagHandler;
import org.seasar.framework.xml.TagHandlerContext;
import org.xml.sax.Attributes;

/**
 * @author higa
 *
 */
public class AspectTagHandler extends TagHandler {

	/**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#start(org.seasar.framework.xml.sax.handler.TagHandlerContext, org.xml.sax.Attributes)
	 */
	public void start(TagHandlerContext context, Attributes attributes) {
		AspectDef aspectDef = null;
		String pointcutStr = attributes.getValue("pointcut");
		if (pointcutStr != null) {
			String[] methodNames = StringUtil.split(pointcutStr, ", ");
			aspectDef = createAspectDef(createPointcut(methodNames));
		} else {
			aspectDef = createAspectDef();
		}
		context.push(aspectDef);
	}

    /**
	 * @see org.seasar.framework.xml.sax.handler.TagHandler#end(org.seasar.framework.xml.sax.handler.TagHandlerContext, java.lang.String)
	 */
	public void end(TagHandlerContext context, String body) {
		AspectDef aspectDef = (AspectDef) context.pop();
		if (!StringUtil.isEmpty(body)) {
			aspectDef.setExpression(body);
		}
		ComponentDef componentDef = (ComponentDef) context.peek();
		componentDef.addAspectDef(aspectDef);
	}

	protected AspectDefImpl createAspectDef() {
		return new AspectDefImpl();
	}

	protected AspectDefImpl createAspectDef(Pointcut pointcut) {
		return new AspectDefImpl(pointcut);
	}

	protected Pointcut createPointcut(String[] methodNames) {
		return new PointcutImpl(methodNames);
	}
}
