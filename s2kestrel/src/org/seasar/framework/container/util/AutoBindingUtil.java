package org.seasar.framework.container.util;

import org.seasar.framework.container.ContainerConstants;

/**
 * @author higa
 *
 */
public final class AutoBindingUtil implements ContainerConstants {

	private AutoBindingUtil() {
	}

	public static final boolean isSuitable(Class clazz) {
		return clazz.isInterface();
	}

	public static final boolean isSuitable(Class[] classes) {
		for (int i = 0; i < classes.length; ++i) {
			if (!isSuitable(classes[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static final boolean isAuto(String mode) {
		return AUTO_BINDING_AUTO.equalsIgnoreCase(mode);
	}
	
	public static final boolean isConstructor(String mode) {
		return AUTO_BINDING_CONSTRUCTOR.equalsIgnoreCase(mode);
	}
	
	public static final boolean isProperty(String mode) {
		return AUTO_BINDING_PROPERTY.equalsIgnoreCase(mode);
	}
	
	public static final boolean isNone(String mode) {
		return AUTO_BINDING_NONE.equalsIgnoreCase(mode);
	}
}
