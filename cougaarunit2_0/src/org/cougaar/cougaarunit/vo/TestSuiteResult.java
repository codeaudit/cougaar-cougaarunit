package org.cougaar.cougaarunit.vo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

public class TestSuiteResult implements Serializable{
	private String name;
	private String result;
	private Vector testList;
	private String tests;
	private String successrate;
	private String failures;
	
	/**
	 * @return
	 */
	public String getFailures() {
		return failures;
	}

	/**
	 * @param failures
	 */
	public void setFailures(String failures) {
		this.failures = failures;
	}

	/**
	 * @return
	 */
	public String getSuccessrate() {
		return successrate;
	}

	/**
	 * @param successrate
	 */
	public void setSuccessrate(String successrate) {
		this.successrate = successrate;
	}

	/**
	 * @return
	 */
	public String getTests() {
		return tests;
	}

	/**
	 * @param tests
	 */
	public void setTests(String tests) {
		this.tests = tests;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	

	/**
	 * @return
	 */
	public Vector getTestList() {
		return testList;
	}

	/**
	 * @param testList
	 */
	public void setTestList(Vector testList) {
		this.testList = testList;
	}

	public void saveResult(String saveLocation) {
		
			Mapping   mapping = new Mapping();
		
			try {
				mapping.loadMapping("testsuiteMapping.xml");			
				Marshaller marshaller = new Marshaller(new FileWriter(saveLocation + File.separator + name+".xml"));
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
}
