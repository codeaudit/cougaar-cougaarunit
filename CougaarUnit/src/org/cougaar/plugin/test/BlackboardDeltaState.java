package org.cougaar.plugin.test;

import java.util.Vector;
import java.util.TreeSet;
import java.util.Enumeration;
import java.io.Serializable;

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

public class BlackboardDeltaState implements Serializable {

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
                PublishAction pa1 = (PublishAction)stateChanges.elementAt(i);
                PublishAction pa2 = (PublishAction)bbs.stateChanges.elementAt(i);
                if (!pa1.equals(pa2)) return false;
            }
            return true;
        }
        else {
            Vector targetStateChanges = new Vector(bbs.stateChanges);  //create a copy of the stateChanges object in the bbs
            for (Enumeration enm = stateChanges.elements(); enm.hasMoreElements(); ) {
                PublishAction pa1 = (PublishAction)enm.nextElement();
                //now loop through the targetStateChanges vector to see if we get a match
                for (Enumeration enm2 = targetStateChanges.elements(); enm2.hasMoreElements(); ) {
                    PublishAction pa2 = (PublishAction)enm2.nextElement();
                    if (pa1.equals(pa2)) {  //if they match then remove the item and break out to the outer loop to continue
                        targetStateChanges.remove(pa2);
                        break;
                    }
                }
            }
            if (targetStateChanges.isEmpty())  //if the targetStateChanges vector is empty then we've matched all the items
                return true;
        }
        return false;
    }

    /**
     * Render the contents of this object as XML
     * @return
     */
    public String getXML() {
      StringBuffer sb = new StringBuffer();
      sb.append("<BLACKBOARD_DELTA_STATE>");
      for (Enumeration enm = stateChanges.elements(); enm.hasMoreElements(); ) {
        sb.append(((PublishAction)enm.nextElement()).getXML());
      }
      sb.append("</BLACKBOARD_DELTA_STATE>");

      return sb.toString();

    }


}