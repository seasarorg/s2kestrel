package org.seasar.framework.container.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.seasar.framework.container.AspectDef;
import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 * AspectDefの設定をサポートします
 */
public final class AspectDefSupport {

	private List aspectDefs_ = Collections.synchronizedList(new ArrayList());
	private S2Container container_;

	public AspectDefSupport() {
	}

	public void addAspectDef(AspectDef aspectDef) {
		if (container_ != null) {
			aspectDef.setContainer(container_);
		}
		aspectDefs_.add(aspectDef);
	}

	public int getAspectDefSize() {
		return aspectDefs_.size();
	}

	public AspectDef getAspectDef(int index) {
		return (AspectDef) aspectDefs_.get(index);
	}

	public void setContainer(S2Container container) {
		container_ = container;
		for (int i = 0; i < getAspectDefSize(); ++i) {
			getAspectDef(i).setContainer(container);
		}
	}
}