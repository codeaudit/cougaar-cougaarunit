/*
 * Created on Nov 5, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.comparators;

import java.util.Comparator;

import org.cougaar.planning.ldm.plan.WorkflowImpl;

/**
 * @author ttschampel
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class WorkflowImplComparator implements Comparator {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		if(!(arg0 instanceof WorkflowImpl && arg1 instanceof WorkflowImpl)){
			return 0;
		}
		WorkflowImpl wf1 = (WorkflowImpl)arg0;
		WorkflowImpl wf2 = (WorkflowImpl)arg1;
		if(wf1==null && wf2==null){
			return 1;
		}else if(wf1==null || wf2==null){
			return 0;
		}else{
			if(wf1.getParentTask().getVerb().equals(wf2.getParentTask().getVerb())){
				return 1;
			}else{
				return 0;
			}
		}
	
	}

}
