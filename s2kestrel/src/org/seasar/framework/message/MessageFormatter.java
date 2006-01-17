package org.seasar.framework.message;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author higa
 *
 * ���b�Z�[�W�R�[�h�ƈ������v���p�e�B�ɓo�^����Ă���
 * �p�^�[���ɓK�p���A���b�Z�[�W��g�ݗ��Ă܂��B<br />
 * ���b�Z�[�W�R�[�h�́A8���ō\������
 * �ŏ���1�������b�Z�[�W�̎�ʂŁAE:�G���[�AW:���[�j���O�AI:�C���t�H���[�V�����ō\������܂��B<br />
 * ����3�����V�X�e������Seasar�̏ꍇ�́ASSR�ɂȂ�܂��B<br />
 * �Ō��4���́A�A�Ԃł��B<br />
 * ���b�Z�[�W��`�t�@�C���́A�V�X�e���� + Messages.properties�ɂȂ�܂��B<br /
 * SSRMessages_ja.properties�Ȃǂ�p�ӂ��邱�Ƃő�����ɑΉ��ł��܂��B
 */

public final class MessageFormatter {

	private static final String MESSAGES = "Messages";

	private MessageFormatter() {
	}

	public static String getMessage(String messageCode, Object[] args) {
		if (messageCode == null) {
			messageCode = "";
		}
		return "[" + messageCode + "]" + getSimpleMessage(messageCode, args);
	}

	public static String getSimpleMessage(
		String messageCode,
		Object[] arguments) {
			
		try {
			String pattern = getPattern(messageCode);
			if (pattern != null) {
				return MessageFormat.format(pattern, arguments);
			}
		} catch (Throwable ignore) {
		}
		return getNoPatternMessage(arguments);
	}
	
	private static String getPattern(String messageCode) {
		ResourceBundle resourceBundle =
			getMessages(getSystemName(messageCode));
		if (resourceBundle != null) {
			return resourceBundle.getString(messageCode);
		}
		return null;
	}
	
	private static String getSystemName(String messageCode) {
		return messageCode.substring(1, Math.min(4, messageCode.length()));
	}
	
	private static ResourceBundle getMessages(String systemName) {
		return ResourceBundle.getBundle(systemName + MESSAGES);
	}
	
	private static String getNoPatternMessage(Object[] args) {
		if (args == null || args.length == 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			buffer.append(args[i] + ", ");
		}
		buffer.setLength(buffer.length() - 2);
		return buffer.toString();
	}
}