package org.seasar.framework.container.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.seasar.framework.container.InitMethodDef;
import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 * InitMethodDefの設定をサポートします
 */
public final class InitMethodDefSupport {

	private List methodDefs_ = Collections.synchronizedList(new ArrayList());
	private S2Container container_;

	public InitMethodDefSupport() {
	}

	public void addInitMethodDef(InitMethodDef methodDef) {
		if (container_ != null) {
			methodDef.setContainer(container_);
		}
		methodDefs_.add(methodDef);
	}

	public int getInitMethodDefSize() {
		return methodDefs_.size();
	}

	public InitMethodDef getInitMethodDef(int index) {
		return (InitMethodDef) methodDefs_.get(index);
	}

	public void setContainer(S2Container container) {
		container_ = container;
		for (int i = 0; i < getInitMethodDefSize(); ++i) {
			getInitMethodDef(i).setContainer(container);
		}
	}
}