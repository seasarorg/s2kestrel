package org.seasar.framework.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.seasar.framework.message.MessageFormatter;

/**
 * @author higa
 * @author Satoshi Kimura
 *
 * Seasarの実行時例外のベースとなるクラスです。<br />
 * メッセージコードによって例外を詳細に特定できます。<br />
 * @see org.seasar.framework.message.MessageFormatter
 */
public class SRuntimeException extends NestableRuntimeException {

	private String messageCode_;
	private Object[] args_;
	private String message_;
	private String simpleMessage_;

	public SRuntimeException(String messageCode) {
		this(messageCode, null, null);
	}

	public SRuntimeException(String messageCode, Object[] args) {
		this(messageCode, args, null);
	}

	public SRuntimeException(
		String messageCode,
		Object[] args,
		Throwable cause) {

		super(cause);
		messageCode_ = messageCode;
		args_ = args;
		simpleMessage_ = MessageFormatter.getSimpleMessage(messageCode_, args_);
		message_ = "[" + messageCode + "]" + simpleMessage_;
	}

	public final String getMessageCode() {
		return messageCode_;
	}

	public final Object[] getArgs() {
		return args_;
	}

	public final String getMessage() {
		return message_;
	}
	
	protected void setMessage(String message) {
		message_ = message;
	}
	
	public final String getSimpleMessage() {
		return simpleMessage_;
	}
}