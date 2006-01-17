package org.seasar.framework.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.transaction.SystemException;

import org.apache.commons.lang.exception.Nestable;
import org.apache.commons.lang.exception.NestableDelegate;
import org.seasar.framework.message.MessageFormatter;

/**
 * @author higa
 * @author Satoshi Kimura
 *
 */
public class SSystemException extends SystemException implements Nestable {

    private NestableDelegate delegate = new NestableDelegate(this);
    private Throwable cause;
	private String messageCode_;
	private Object[] args_;
	
	public SSystemException(String messageCode, Object[] args) {
		this(messageCode, args, null);
	}

	public SSystemException(String messageCode, Object[] args, Throwable cause) {
		super(MessageFormatter.getMessage(messageCode, args));
		messageCode_ = messageCode;
		args_ = args;
		this.initCause(cause);
	}

	public String getMessageCode() {
		return messageCode_;
	}
	
	public Object[] getArgs() {
		return args_;
	}


    public Throwable getCause() {
        return cause;
    }

    public Throwable initCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public String getMessage() {
        if (super.getMessage() != null) {
            return super.getMessage();
        } else if (cause != null) {
            return cause.toString();
        } else {
            return null;
        }
    }

    public String getMessage(int index) {
        if (index == 0) {
            return super.getMessage();
        } else {
            return delegate.getMessage(index);
        }
    }

    public String[] getMessages() {
        return delegate.getMessages();
    }

    public Throwable getThrowable(int index) {
        return delegate.getThrowable(index);
    }

    public int getThrowableCount() {
        return delegate.getThrowableCount();
    }

    public Throwable[] getThrowables() {
        return delegate.getThrowables();
    }

    public int indexOfThrowable(Class type) {
        return delegate.indexOfThrowable(type, 0);
    }

    public int indexOfThrowable(Class type, int fromIndex) {
        return delegate.indexOfThrowable(type, fromIndex);
    }

    public void printStackTrace() {
        delegate.printStackTrace();
    }

    public void printStackTrace(PrintStream out) {
        delegate.printStackTrace(out);
    }

    public void printStackTrace(PrintWriter out) {
        delegate.printStackTrace(out);
    }

    public final void printPartialStackTrace(PrintWriter out) {
        super.printStackTrace(out);
    }
}
