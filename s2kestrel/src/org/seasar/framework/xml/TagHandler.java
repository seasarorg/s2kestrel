package org.seasar.framework.xml;

import java.io.Serializable;
import org.xml.sax.Attributes;

public class TagHandler implements Serializable {

    static final long serialVersionUID = 1L;
    
    public void start(TagHandlerContext context, Attributes attributes) {
    }

	public void appendBody(TagHandlerContext context, String body) {
    }
    
    public void end(TagHandlerContext context, String body) {
    }
}
