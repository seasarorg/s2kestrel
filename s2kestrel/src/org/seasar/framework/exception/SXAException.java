package org.seasar.framework.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.transaction.xa.XAException;

import org.apache.commons.lang.exception.Nestable;
import org.apache.commons.lang.exception.NestableDelegate;
import org.seasar.framework.message.MessageFormatter;

/**
 * @author higa
 * @author Satoshi Kimura
 *
 */
public class SXAException extends XAException implements Nestable {

    private NestableDelegate delegate = new NestableDelegate(this);
    private Throwable cause;
	private String messageCode_;
	private Object[] messageArgs_;
	
	public SXAException(Throwable t) {
		this("ESSR0017", new Object[]{t}, t);
	}

	public SXAException(String messageCode, Object[] messageArgs) {
		this(messageCode, messageArgs, null);
	}

	public SXAException(String messageCode, Object[] messageArgs,
		Throwable t) {

		super(MessageFormatter.getMessage(messageCode, messageArgs));
		messageCode_ = messageCode;
		messageArgs_ = messageArgs;
		initCause(t);
	}

	public String getMessageCode() {
		return messageCode_;
	}
	
	public Object[] getMessageArgs() {
		return messageArgs_;
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
