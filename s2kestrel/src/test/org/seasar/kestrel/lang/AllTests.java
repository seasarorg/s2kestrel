package test.org.seasar.kestrel.lang;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for test.org.seasar.kestrel.lang");
        //$JUnit-BEGIN$
        suite.addTestSuite(BooleanUtilTest.class);
        //$JUnit-END$
        return suite;
    }

}
