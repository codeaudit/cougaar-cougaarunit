/*
 * Created on Nov 9, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.vo;

import java.io.Serializable;

/**
 * @author ttschampel
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ExpectedBlackboardState implements Serializable{
	private String blackboardDeltaState;
	
	/**
	 * @return Returns the blackboardDeltaState.
	 */
	public String getBlackboardDeltaState() {
		return blackboardDeltaState;
	}

	/**
	 * @param blackboardDeltaState The blackboardDeltaState to set.
	 */
	public void setBlackboardDeltaState(String blackboardDeltaState) {
		this.blackboardDeltaState = blackboardDeltaState;
	}

}
