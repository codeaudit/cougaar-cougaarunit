package org.cougaar.cougaarunit;
public class SampleTestSuite extends PluginTestSuite{

	/* (non-Javadoc)
	 * @see org.cougaar.cougaarunit.PluginTestSuite#getTestClasses()
	 */
	public Class[] getTestClasses() {
		Class[] testCases = new Class[2];
		testCases[0]=org.cougaar.cougaarunit.SamplePluginTest.class;
		testCases[1]=org.cougaar.cougaarunit.SamplePluginTest2.class;
		return testCases;
	}

}
