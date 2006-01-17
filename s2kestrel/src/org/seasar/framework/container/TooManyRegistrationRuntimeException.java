package org.seasar.framework.container;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *  
 */
public final class TooManyRegistrationRuntimeException extends SRuntimeException {

    private Object key_;
    private Class[] componentClasses_;

    /**
     * @param componentClasses
     */
    public TooManyRegistrationRuntimeException(Object key, Class[] componentClasses) {
        super("ESSR0045", new Object[] { key, getClassNames(componentClasses) });
        key_ = key;
        componentClasses_ = componentClasses;
    }

    public Object getKey() {
        return key_;
    }

    public Class[] getComponentClasses() {
        return componentClasses_;
    }

    private static String getClassNames(Class[] componentClasses) {
        StringBuffer buf = new StringBuffer(255);
        for (int i = 0; i < componentClasses.length; ++i) {
            if (componentClasses[i] != null) {
                buf.append(componentClasses[i].getName());
            }
            else {
                buf.append("<unknown>");
            }
            buf.append(", ");
        }
        buf.setLength(buf.length() - 2);
        return buf.toString();
    }
}