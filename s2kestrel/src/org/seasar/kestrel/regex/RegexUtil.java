package org.seasar.kestrel.regex;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.StringSubstitution;
import org.apache.oro.text.regex.Substitution;
import org.apache.oro.text.regex.Util;

/**
 * @author Satoshi Kimura
 */
public class RegexUtil {

    private RegexUtil() {
    }

    public static String replaceAll(String original, char oldChar, char newChar) {
        return replaceAll(original, "\\" + oldChar, String.valueOf(newChar));
    }

    public static String replaceAll(String original, String regex, String replacement) {
        return replace(original, regex, replacement, original.length());
    }

    public static String replace(String original, String regex, String replacement, int numSubs) {
        Pattern pattern = compile(regex);
        PatternMatcher matcher = new Perl5Matcher();
        Substitution substitution = new StringSubstitution(replacement);
        return Util.substitute(matcher, pattern, substitution, original, numSubs);
    }

    public static String replaceFirst(String original, String regex, String replacement) {
        return replace(original, regex, replacement, 1);
    }

    public static Pattern compile(String regex) {
        try {
            Perl5Compiler compiler = new Perl5Compiler();
            return compiler.compile(regex);
        } catch (MalformedPatternException e) {
            throw new MalformedPatternRuntimeException(e);
        }
    }

}
