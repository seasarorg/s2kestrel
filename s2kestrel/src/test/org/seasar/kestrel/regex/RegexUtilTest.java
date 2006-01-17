package test.org.seasar.kestrel.regex;

import junit.framework.TestCase;

import org.seasar.kestrel.regex.RegexUtil;

/**
 * @author Satoshi Kimura
 */
public class RegexUtilTest extends TestCase {

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testReplaceAll() {
        String ret = RegexUtil.replaceAll("abc", "c", "d");
        assertEquals("abd", ret);

        ret = RegexUtil.replaceAll("a.b.c", '.', '/');
        assertEquals("a/b/c", ret);
    }
    
    public void testReplaceFirst() {
        String ret = RegexUtil.replaceFirst("aaa", "a", "b");
        assertEquals("baa", ret);
    }

}
