package org.cougaar.cougaarunit.vo;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author mabrams
 */
public class Parameters implements Serializable{
	private Vector parameters;

	/**
	 * 
	 */
	public Parameters() {
		parameters = new Vector();
	}

	/**
	 * @return Returns the parameters.
	 */
	public Vector getParameters() {
		return parameters;
	}
	/**
	 * @param parameters The parameters to set.
	 */
	public void setParameters(Vector parameters) {
		this.parameters = parameters;
	}
}
