package org.cougaar.plugin.test;

import java.util.Vector;
import java.util.TreeSet;
import java.util.Enumeration;

/**
 * <p>Title: </p>
 * <p>Description:
 * This class is used to represent a set of publsh actions that are expected to occur on the
 * system blackboard.  The test infratstrucre is able to monitor a plugins blackboard activity
 * to validate it's actions against the BlackboardState object.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BlackboardDeltaState {

    Vector stateChanges = new Vector();

    public BlackboardDeltaState() {
    }

    public void add(PublishAction pa) {
        stateChanges.add(pa);
    }

    public int getSize() {
        return stateChanges.size();
    }

    /**
     * compare this BlackboardState object another one
     * @param bbs The BlackboardState object to compare to
     * @param ordered Should the comparison include an order dependency: true = yes, false = no.
     * @return boolean indicating whether the two BlackboardState objects match or not
     *    true = match
     *    false = no match
     */
    public boolean compare(BlackboardDeltaState bbs, boolean isOrdered) {
        if (this.getSize() != bbs.getSize()) return false;  //verify the sizes are the same
        if (isOrdered) {  //if the comparison is ordered, then compare elements one by one in order
            for (int i=0; i<stateChanges.size(); i++) {
                if (!stateChanges.elementAt(i).equals(bbs.stateChanges.elementAt(i)))
                    return false;
            }
            return true;
        }
        else {
            Vector targetStateChanges = new Vector(bbs.stateChanges);  //create a copy of the stateChanges object in the bbs
            for (Enumeration enum = stateChanges.elements(); enum.hasMoreElements(); ) {
                Object item = enum.nextElement();
                if (targetStateChanges.contains(item))  //if the item is in the targetStateChanges, then remove it and keep going
                    targetStateChanges.remove(item);
                else  //otherwise the whole test fails
                    return false;
            }
            if (targetStateChanges.isEmpty())  //if the targetStateChanges vector is empty then we've matched all the items
                return true;
        }
        return false;
    }

    /**
     * clears the contents of this blackboard state
     */
    public void reset() {
        stateChanges.clear();
    }

}