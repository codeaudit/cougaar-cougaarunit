/*
 * Created on Nov 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.vo.results;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import org.cougaar.cougaarunit.PluginTestResult;
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
public class TestResult implements Serializable{
	private String name;
	private Vector entries;
	
	public void populate(){
		this.name = PluginTestResult.getTestName();
		Vector _entries = PluginTestResult.getEntries();
		if(_entries!=null){
			entries = new Vector();
			Enumeration enumeration = _entries.elements();
			while(enumeration.hasMoreElements()){
				PluginTestResult.TestResultEntry entry =  (PluginTestResult.TestResultEntry)enumeration.nextElement();
				TestResultEntry voEntry = new TestResultEntry();
				voEntry.populate(entry);
				entries.add(voEntry);
			}
		}
	}
	
	/**
	 * @return Returns the entries.
	 */
	public Vector getEntries() {
		return entries;
	}

	/**
	 * @param entries The entries to set.
	 */
	public void setEntries(Vector entries) {
		this.entries = entries;
	}

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
	public void save(String saveLocation) {
		
		Mapping   mapping = new Mapping();
		
		try {
			mapping.loadMapping("testresultMapping.xml");			
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
}
