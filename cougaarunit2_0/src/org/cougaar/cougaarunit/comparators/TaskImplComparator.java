/*
 * Created on Nov 4, 2003
 * 
 * To change the template for this generated file go to Window - Preferences -
 * Java - Code Generation - Code and Comments
 */
package org.cougaar.cougaarunit.comparators;

import java.util.Comparator;

import org.cougaar.planning.ldm.plan.Task;

/**
 * @author ttschampel
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class TaskImplComparator implements Comparator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object obj1, Object obj2) {
		if (!(obj1 instanceof Task) || !(obj2 instanceof Task)) {
			return 0;
		} else {
			Task task1 = (Task) obj1;
			Task task2 = (Task) obj2;
			if (task1.getVerb() == null && task2.getVerb() == null) {
				return 1;
			} else if (task1.getVerb() == null || task2.getVerb() == null) {
				return 0;
			} else if (task1.getVerb().equals(task2.getVerb())) {
				return 1;
			} else {
				return 0;
			}
		}

	}

}
