package org.seasar.framework.xml;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class TagHandlerRule implements Serializable {

	static final long serialVersionUID = 1L;

	private Map tagHandlers_ = new HashMap();

	public TagHandlerRule() {
	}

	public final void addTagHandler(String path, TagHandler tagHandler) {
		tagHandlers_.put(path, tagHandler);
	}

	public final TagHandler getTagHandler(String path) {
		return (TagHandler) tagHandlers_.get(path);
	}
}
