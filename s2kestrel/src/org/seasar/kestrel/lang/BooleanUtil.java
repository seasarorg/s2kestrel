package org.seasar.kestrel.lang;

/**
 * @author Satoshi Kimura
 * @author Koichi ITO
 */
public class BooleanUtil {

    public static final java.lang.Boolean TRUE = java.lang.Boolean.TRUE;
    public static final java.lang.Boolean FALSE = java.lang.Boolean.FALSE;
    
    public static java.lang.Boolean valueOf(String value) {
        return value.equalsIgnoreCase("true") ? TRUE : FALSE;
    }

    public static java.lang.Boolean valueOf(boolean value) {
        return value ? TRUE : FALSE;
    }

}

