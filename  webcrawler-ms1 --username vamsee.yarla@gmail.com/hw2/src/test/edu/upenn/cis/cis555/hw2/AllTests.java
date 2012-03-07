package test.edu.upenn.cis.cis555.hw2;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(ServletTest.class);
		suite.addTestSuite(RequestTest.class);
		suite.addTestSuite(ResponseTest.class);
		suite.addTestSuite(ConfigTest.class);
		suite.addTestSuite(ContextTest.class);
		suite.addTestSuite(SessionTest.class);
		
		//$JUnit-END$
		return suite;
	}

}
