package org.seasar.framework.message;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author higa
 *
 * メッセージコードと引数をプロパティに登録されている
 * パターンに適用し、メッセージを組み立てます。<br />
 * メッセージコードは、8桁で構成され
 * 最初の1桁がメッセージの種別で、E:エラー、W:ワーニング、I:インフォメーションで構成されます。<br />
 * 次の3桁がシステム名でSeasarの場合は、SSRになります。<br />
 * 最後の4桁は、連番です。<br />
 * メッセージ定義ファイルは、システム名 + Messages.propertiesになります。<br /
 * SSRMessages_ja.propertiesなどを用意することで多言語に対応できます。
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