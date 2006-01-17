package org.seasar.framework.exception;

import java.io.IOException;

import org.seasar.framework.message.MessageFormatter;

/**
 * @author higa
 *
 */
public class SIOException extends IOException {

	private String messageCode_;
	private Object[] args_;

	public SIOException(String messageCode, Object[] args) {
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
