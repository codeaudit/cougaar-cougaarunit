package org.cougaar.plugin.test;

import java.util.Vector;

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

    /**
     * compare this BlackboardState object another one
     * @param bbs The BlackboardState object to compare to
     * @param ordered Should the comparison include an order dependency: true = yes, false = no.
     * @return boolean indicating whether the two BlackboardState objects match or not
     *    true = match
     *    false = no match
     */
    public boolean compare(BlackboardDeltaState bbs, boolean isOrdered) {
        return false;
    }

    /**
     * clears the contents of this blackboard state
     */
    public void reset() {
        stateChanges.clear();
    }

}