package org.seasar.kestrel.regex;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author Satoshi Kimura
 */
public class MalformedPatternRuntimeException extends NestableRuntimeException {

    /**
     * 
     */
    public MalformedPatternRuntimeException() {
        super();
    }

    /**
     * @param message
     */
    public MalformedPatternRuntimeException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public MalformedPatternRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public MalformedPatternRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
