/*
 * Created on Nov 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.vo.results;

import java.io.Serializable;

/**
 * @author ttschampel
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PublishAction implements Serializable{
	private String action;
	private String result;
	
	/**
	 * @return Returns the action.
	 */
	public String getAction() {
		return action;
	}
	public void populate(org.cougaar.cougaarunit.PublishAction pa){
		if(pa==null){
			return;
		}
		int actionId = pa.getActionId();
		switch(actionId){
			case org.cougaar.cougaarunit.PublishAction.ADD:
				action="ADD";
				break;
			case org.cougaar.cougaarunit.PublishAction.CHANGE:
				action="CHANGE";
				break;
			case org.cougaar.cougaarunit.PublishAction.REMOVE:
				action="REMOVE";
				break;
			
		}
		Object obj = pa.getObj();
		if(obj!=null){
			result = obj.getClass().getName();
		}
	}
	/**
	 * @param action The action to set.
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return Returns the result.
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result The result to set.
	 */
	public void setResult(String result) {
		this.result = result;
	}

}
