package org.seasar.framework.aop.javassist;

/**
 * @author koichik
 */
public class TryBlockSupport {
    protected static final int STATUS_TRY = 0;
    protected static final int STATUS_CATCH = 1;
    protected static final int STATUS_FINALLY = 2;
    
    protected int status;
    protected StringBuffer codeBuf = new StringBuffer(500);
    
    public TryBlockSupport(final String src) {
        codeBuf.append("try {").append(src).append("}");
        status = STATUS_TRY;
    }

    public void addCatchBlock(final Class exceptionType, final String src) {
        if (!Throwable.class.isAssignableFrom(exceptionType)) {
            throw new IllegalArgumentException("exceptionType must be Throwable.");
        }
        if (status != STATUS_TRY && status != STATUS_CATCH) {
            throw new IllegalStateException("could't append catch block after finally block.");
        }
        
        codeBuf.append("catch (").append(exceptionType.getName()).append(" e) {").append(src).append("}");
        status = STATUS_CATCH;
    }
    
    public void setFinallyBlock(final String src) {
        if (status != STATUS_TRY && status != STATUS_CATCH) {
            throw new IllegalStateException("finally block is already appended.");
        }
        
        codeBuf.append("finally {").append(src).append("}");
        status = STATUS_FINALLY;
    }
    
    public String getSourceCode() {
        if (status != STATUS_CATCH && status != STATUS_FINALLY) {
            throw new IllegalStateException("must set catch block or finally block.");
        }
        
        return new String(codeBuf);
    }
}
