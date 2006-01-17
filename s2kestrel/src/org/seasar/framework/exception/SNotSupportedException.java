package org.seasar.framework.exception;

import javax.transaction.NotSupportedException;

import org.seasar.framework.message.MessageFormatter;

/**
 * @author higa
 *
 */
public class SNotSupportedException extends NotSupportedException {

	private String messageCode_;
	private Object[] args_;
	
	public SNotSupportedException(String messageCode, Object[] args) {
		super(MessageFormatter.getMessage(messageCode, args));
		messageCode_ = messageCode;
		args_ = args;
	}

	public String getMessageCode() {
		return messageCode_;
	}
	
	public Object[] getArgs() {
		return args_;
	}
}
