package org.seasar.framework.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Matcher;
import org.seasar.kestrel.regex.RegexUtil;

/**
 * 
 * @author higa
 * @author Satoshi Kimura
 */
public final class LikeUtil {

	private static Map patterns_ = Collections.synchronizedMap(new HashMap());

	private LikeUtil() {
	}

	public static final boolean match(String patternStr, String value) {
		if (StringUtil.isEmpty(patternStr)) {
			return false;
		}
		Pattern pattern = (Pattern) patterns_.get(patternStr);
		if (pattern == null) {
			String regexp = StringUtil.replace(patternStr, "_", ".");
			regexp = StringUtil.replace(regexp, "%", ".*");
            pattern = RegexUtil.compile(regexp);
			patterns_.put(patternStr, pattern);
		}
		return new Perl5Matcher().matches(value, pattern);
	}
}