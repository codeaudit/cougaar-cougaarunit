package org.cougaar.cougaarunit;

import java.util.Vector;

import org.cougaar.cougaarunit.vo.results.TestResult;


/**
 *
 * <p>Title: PluginTestSuite</p>
 * <p>Description: Parent class for test suite classes.  Test suites can be used to
 * group test cases together.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */
public abstract class PluginTestSuite {
	private Vector testResults;
    /**
     * This method should return an iterator of class objects that represent
     * all the test cases to be tested within this suite
     */
    public abstract Class[] getTestClasses();
  
    
    public void setTestResults(Vector v){
    	this.testResults = v;
    }
    public Vector getTestResults(){
    	return this.testResults;
    	
    }
    public void addTestResult(TestResult result){
    	if(testResults==null){
    		testResults = new Vector();
    	}
    	testResults.add(result);
    	
    }
}