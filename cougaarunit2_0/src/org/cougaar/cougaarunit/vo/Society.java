package org.cougaar.cougaarunit.vo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

import org.cougaar.core.util.UID;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

/**
 * The society value object
 * 
 * @author mabrams
 */
public class Society implements Serializable {

	/** society name */
	private String name = null;
	
	/** a list of hosts in the society */
	private Vector nodeList = null;

	private UID uid;
	
	/**
	 * Default Constructor
	 *
	 */
	public Society() {
		nodeList = new Vector();
	}
	
	public Society(String _name) {
		this();
		this.name = _name;
	}
	/**
	 * @return Returns the nodeList.
	 */
	public Vector getNodeList() {
		return nodeList;
	}
	/**
	 * @param nodeList The nodeList to set.
	 */
	public void setNodeList(Vector hostList) {
		this.nodeList = hostList;
	}
	
	/** 
	 * remove a host from the nodeList
	 * @param host
	 */
	public boolean removeNode(Object host) {
		return this.nodeList.remove(host);
	}
	
	/**
	 * add a host to the node list
	 * @param host
	 * @return
	 */
	public boolean addNode(Object host) {
		return this.nodeList.add(host);
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
	
	public void saveSociety(String saveLocation) {
		
		Mapping   mapping = new Mapping();
		
		try {
			mapping.loadMapping("societyMapping.xml");			
			Marshaller marshaller = new Marshaller(new FileWriter(saveLocation + "/testSociety.xml"));
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
	

	public UID getUID() { return uid; }
	public void setUID(UID uid) { this.uid = uid; }
}
