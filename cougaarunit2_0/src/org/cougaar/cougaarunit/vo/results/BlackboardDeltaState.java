/*
 * Created on Nov 10, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.vo.results;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * @author ttschampel
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class BlackboardDeltaState implements Serializable{
	private Vector stateChanges;
	
	public void populate(org.cougaar.cougaarunit.BlackboardDeltaState bbds){
		Vector _v = bbds.getStateChanges();
		if(_v!=null){
			stateChanges = new Vector();
			Enumeration enumeration = _v.elements();
			while(enumeration.hasMoreElements()){
				org.cougaar.cougaarunit.PublishAction _pc = 
					(org.cougaar.cougaarunit.PublishAction)enumeration.nextElement();
				PublishAction vobject = new PublishAction();
				vobject.populate(_pc);
				stateChanges.add(vobject);
			}
		}
	}
	/**
	 * @return Returns the stateChanges.
	 */
	public Vector getStateChanges() {
		return stateChanges;
	}

	/**
	 * @param stateChanges The stateChanges to set.
	 */
	public void setStateChanges(Vector stateChanges) {
		this.stateChanges = stateChanges;
	}

}
