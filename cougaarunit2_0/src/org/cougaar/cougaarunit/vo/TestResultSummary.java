package org.cougaar.cougaarunit.vo;

import java.io.Serializable;
import java.util.Vector;

public class TestResultSummary implements Serializable{
	private String name;
	private Vector idList;
	

	/**
	 * @return
	 */
	public Vector getIdList() {
		return idList;
	}

	/**
	 * @param idList
	 */
	public void setIdList(Vector idList) {
		this.idList = idList;
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

}
