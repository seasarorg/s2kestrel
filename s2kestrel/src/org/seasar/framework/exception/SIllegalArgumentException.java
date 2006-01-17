package org.seasar.framework.exception;

import org.seasar.framework.message.MessageFormatter;

/**
 * @author koichik
 */
public class SIllegalArgumentException extends IllegalArgumentException {
    private String messageCode_;
    private Object[] args_;

    public SIllegalArgumentException(String messageCode, Object[] args) {
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
