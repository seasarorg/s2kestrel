package org.seasar.framework.aop.javassist;

import javassist.NotFoundException;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author koichik
 */
public class NotFoundRuntimeException extends SRuntimeException {
    /**
     * @param cause
     */
    public NotFoundRuntimeException(final NotFoundException cause) {
        super("ESSR0017", new Object[] { cause }, cause);
    }
}