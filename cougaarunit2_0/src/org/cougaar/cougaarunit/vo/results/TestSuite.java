/*
 * Created on Nov 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.vo.results;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import org.cougaar.cougaarunit.PluginTestSuite;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

/**
 * @author ttschampel
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestSuite {
	private String name;
	private String numberOfTests;
	private String numberOfFailures;
	private String successRate;
	
	public void populate(PluginTestSuite testSuite){
		int _numberOfTests=0;
		int _numberOfFailures= 0;
		float _successRate=0f;
		this.name = testSuite.getClass().getName();
		this.tests = testSuite.getTestResults();
		Enumeration enumeration = tests.elements();
		while(enumeration.hasMoreElements()){
			TestResult result = (TestResult)enumeration.nextElement();
			Vector entries  = result.getEntries();
			Enumeration entryEnumeration = entries.elements();
			while(entryEnumeration.hasMoreElements()){
				TestResultEntry entry = (TestResultEntry)entryEnumeration.nextElement();
				String _result= entry.getResult();
				if(!(_result.equals("pass"))){
					_numberOfFailures ++;
				}
				_numberOfTests++;
			}
		}
		numberOfTests=""+_numberOfTests;
		numberOfFailures=""+_numberOfFailures;
		float successes = _numberOfTests - _numberOfFailures;
		if(successes>0){
			float _success = successes / _numberOfTests; 
			successRate=""+_success;
		}else{
			successRate="0.0";
		}
		
		
	}
	public void save(String saveLocation) {
		
		Mapping   mapping = new Mapping();
		
		try {
			mapping.loadMapping("testsuiteMapping.xml");			
			Marshaller marshaller = new Marshaller(new FileWriter(saveLocation));
			marshaller.setMapping(mapping);
			marshaller.marshal(this);
		} catch (MarshalException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ValidationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Vector tests;
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the numberOfFailures.
	 */
	public String getNumberOfFailures() {
		return numberOfFailures;
	}

	/**
	 * @param numberOfFailures The numberOfFailures to set.
	 */
	public void setNumberOfFailures(String numberOfFailures) {
		this.numberOfFailures = numberOfFailures;
	}

	/**
	 * @return Returns the numberOfTests.
	 */
	public String getNumberOfTests() {
		return numberOfTests;
	}

	/**
	 * @param numberOfTests The numberOfTests to set.
	 */
	public void setNumberOfTests(String numberOfTests) {
		this.numberOfTests = numberOfTests;
	}

	/**
	 * @return Returns the successRate.
	 */
	public String getSuccessRate() {
		return successRate;
	}

	/**
	 * @param successRate The successRate to set.
	 */
	public void setSuccessRate(String successRate) {
		this.successRate = successRate;
	}

	/**
	 * @return Returns the tests.
	 */
	public Vector getTests() {
		return tests;
	}

	/**
	 * @param tests The tests to set.
	 */
	public void setTests(Vector tests) {
		this.tests = tests;
	}

}
