package org.cougaar.cougaarunit.vo;

import java.io.Serializable;

/**
 * @author mabrams
 */
public class Parameter implements Serializable {

	private String name;
	private String value;
	
	public Parameter() {
		
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
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
