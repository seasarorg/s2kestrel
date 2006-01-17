package org.seasar.framework.container.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.seasar.framework.container.ArgDef;
import org.seasar.framework.container.S2Container;

/**
 * @author higa
 *
 * ArgDefの設定をサポートします
 */
public final class ArgDefSupport {

	private List argDefs_ = Collections.synchronizedList(new ArrayList());
	private S2Container container_;
	
	public ArgDefSupport() {
	}

	public void addArgDef(ArgDef argDef) {
		if (container_ != null) {
			argDef.setContainer(container_);
		}
		argDefs_.add(argDef);
	}
	
	public int getArgDefSize() {
		return argDefs_.size();
	}
	
	public ArgDef getArgDef(int index) {
		return (ArgDef) argDefs_.get(index);
	}
	
	public void setContainer(S2Container container) {
		container_ = container;
		for (int i = 0; i < getArgDefSize(); ++i) {
			getArgDef(i).setContainer(container);
		}
	}
}
