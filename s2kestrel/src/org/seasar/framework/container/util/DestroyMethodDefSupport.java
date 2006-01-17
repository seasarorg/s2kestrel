package org.seasar.framework.container.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.seasar.framework.container.DestroyMethodDef;
import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 * DestroyMethodDefの設定をサポートします
 */
public final class DestroyMethodDefSupport {

	private List methodDefs_ = Collections.synchronizedList(new ArrayList());
	private S2Container container_;

	public DestroyMethodDefSupport() {
	}

	public void addDestroyMethodDef(DestroyMethodDef methodDef) {
		if (container_ != null) {
			methodDef.setContainer(container_);
		}
		methodDefs_.add(methodDef);
	}

	public int getDestroyMethodDefSize() {
		return methodDefs_.size();
	}

	public DestroyMethodDef getDestroyMethodDef(int index) {
		return (DestroyMethodDef) methodDefs_.get(index);
	}

	public void setContainer(S2Container container) {
		container_ = container;
		for (int i = 0; i < getDestroyMethodDefSize(); ++i) {
			getDestroyMethodDef(i).setContainer(container);
		}
	}
}