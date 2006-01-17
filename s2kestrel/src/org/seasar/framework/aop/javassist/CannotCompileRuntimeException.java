package org.seasar.framework.aop.javassist;

import javassist.CannotCompileException;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author koichik
 */
public class CannotCompileRuntimeException extends SRuntimeException {
    /**
     * @param cause
     */
    public CannotCompileRuntimeException(final CannotCompileException cause) {
        super("ESSR0017", new Object[] { cause }, cause);
    }
}