package test.org.seasar.kestrel.lang;

import junit.framework.TestCase;

/**
 * @author Koichi ITO
 */
public class BooleanUtilTest extends TestCase {

    public void testValueOfStringShouldSameObject() {
        assertSame(Boolean.TRUE, org.seasar.kestrel.lang.BooleanUtil.valueOf("true"));
        assertSame(Boolean.TRUE, org.seasar.kestrel.lang.BooleanUtil.valueOf("TRUE"));
        assertSame(Boolean.TRUE, org.seasar.kestrel.lang.BooleanUtil.valueOf("TrUe"));
        assertSame(Boolean.FALSE, org.seasar.kestrel.lang.BooleanUtil.valueOf("Anything except true."));
    }

    public void testValueOfBooleanLiteralShouldSameObject() {
        assertSame(Boolean.TRUE, org.seasar.kestrel.lang.BooleanUtil.valueOf(true));
        assertSame(Boolean.FALSE, org.seasar.kestrel.lang.BooleanUtil.valueOf(false));
    }

}
