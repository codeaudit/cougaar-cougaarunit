/*
 * Created on Nov 6, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.vo;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author ttschampel
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TestResult implements Serializable {
	
	private String name;
	private Vector idList;
	
	public TestResult(){
		this.idList = new Vector();
	}
	
	
	/**
	 * @return Returns the idList.
	 */
	public Vector getIdList() {
		return idList;
	}

	/**
	 * @param idList The idList to set.
	 */
	public void setIdList(Vector idList) {
		this.idList = idList;
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

	

}
