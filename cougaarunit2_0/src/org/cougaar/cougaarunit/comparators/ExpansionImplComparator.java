/*
 * Created on Nov 5, 2003
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.comparators;

import java.util.Comparator;

import org.cougaar.planning.ldm.plan.ExpansionImpl;

/**
 * @author ttschampel
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class ExpansionImplComparator implements Comparator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		if (!(arg0 instanceof ExpansionImpl
			&& arg1 instanceof ExpansionImpl)) {
			return 0;
		} else {
			ExpansionImpl exp1 = (ExpansionImpl) arg0;
			ExpansionImpl exp2 = (ExpansionImpl) arg1;
			try {
				if (exp1
					.getTask()
					.getVerb()
					.equals(exp2.getTask().getVerb())) {
					return 1;
				} else {
					return 0;
				}
			} catch (Exception e) {
				return 0;
			}
		}
	}


}
