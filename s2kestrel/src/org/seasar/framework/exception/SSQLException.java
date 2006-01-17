package org.seasar.framework.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.SQLException;

import org.apache.commons.lang.exception.Nestable;
import org.apache.commons.lang.exception.NestableDelegate;
import org.seasar.framework.message.MessageFormatter;

/**
 * @author higa
 * @author Satoshi Kimura
 *
 */
public class SSQLException extends SQLException implements Nestable {

    private NestableDelegate delegate = new NestableDelegate(this);
    private Throwable cause;
	private String messageCode_;
	private Object[] args_;
	
	public SSQLException(String messageCode, Object[] args) {
	    this(messageCode, args, null, 0, null);
	}

	public SSQLException(String messageCode, Object[] args, Throwable cause) {
	    this(messageCode, args, null, 0, cause);
	}

	public SSQLException(String messageCode, Object[] args, String sqlState) {
	    this(messageCode, args, sqlState, 0, null);
	}

	public SSQLException(String messageCode, Object[] args, String sqlState, Throwable cause) {
	    this(messageCode, args, sqlState, 0, cause);
	}

	public SSQLException(String messageCode, Object[] args, String sqlState, int vendorCode) {
	    this(messageCode, args, sqlState, vendorCode, null);
	}

	public SSQLException(String messageCode, Object[] args, String sqlState, int vendorCode, Throwable cause) {
		super(MessageFormatter.getMessage(messageCode, args), sqlState, vendorCode);
		messageCode_ = messageCode;
		args_ = args;
		this.initCause(cause);
		if (cause instanceof SQLException) {
		    setNextException((SQLException) cause);
		}
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
