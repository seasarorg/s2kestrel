package org.seasar.framework.container.factory;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.InputStreamUtil;
import org.seasar.framework.util.SAXParserFactoryUtil;
import org.seasar.framework.xml.SaxHandler;
import org.seasar.framework.xml.SaxHandlerParser;
import org.seasar.framework.xml.TagHandlerContext;

/**
 * @author higa
 *  
 */
public class XmlS2ContainerBuilder extends AbstractS2ContainerBuilder {
    public static final String PUBLIC_ID = "-//SEASAR//DTD S2Container//EN";
    public static final String PUBLIC_ID21 = "-//SEASAR2.1//DTD S2Container//EN";
    public static final String DTD_PATH = "org/seasar/framework/container/factory/components.dtd";
    public static final String DTD_PATH21 = "org/seasar/framework/container/factory/components21.dtd";

    protected S2ContainerTagHandlerRule rule_ = new S2ContainerTagHandlerRule();

    public S2ContainerTagHandlerRule getRule() {
        return rule_;
    }

    public void setRule(final S2ContainerTagHandlerRule rule) {
        rule_ = rule;
    }

    public S2Container build(final String path) {
        return parse(null, path);
    }

    public S2Container include(final S2Container parent, final String path) {
        final S2Container child = parse(parent, path);
        parent.include(child);
        return child;
    }

    protected S2Container parse(final S2Container parent, final String path) {
        final SaxHandlerParser parser = createSaxHandlerParser(parent, path);
        final InputStream is = getInputStream(path);
        try {
            return (S2Container) parser.parse(is);
        }
        finally {
            InputStreamUtil.close(is);
        }
    }

    protected SaxHandlerParser createSaxHandlerParser(final S2Container parent, final String path) {
        final SAXParserFactory factory = SAXParserFactoryUtil.newInstance();
        factory.setValidating(true);

        final SAXParser saxParser = SAXParserFactoryUtil.newSAXParser(factory);

        final SaxHandler handler = new SaxHandler(rule_);
        handler.registerDtdPath(PUBLIC_ID, DTD_PATH);
        handler.registerDtdPath(PUBLIC_ID21, DTD_PATH21);

        final TagHandlerContext ctx = handler.getTagHandlerContext();
        ctx.addParameter("parent", parent);
        ctx.addParameter("path", path);

        return new SaxHandlerParser(handler, saxParser);
    }
}
