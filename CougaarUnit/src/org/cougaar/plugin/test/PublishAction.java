package org.cougaar.plugin.test;

/**
 * <p>Title: </p>
 * <p>Description:
 * This class allows the tester to contsruct Publish Actions which can then be tested
 * for.  A publish action consists of an action code and a reference to the object upom whcih  that action
 * taken.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class PublishAction  {

    private int actionId;
    private Object obj = null;

    public PublishAction() {
    }
    public static final int ADD = 1;
    public static final int REMOVE = 2;
    public static final int CHANGE = 3;

    public PublishAction(int actionId) {
        this.actionId = actionId;
    }

    public PublishAction(int actionId, Object objRef) {
        this.actionId = actionId;
        this.obj = objRef;
    }

    public void add(Object objRef) {
        this.obj = objRef;
    }

    public boolean equals(PublishAction pa) {
        if (pa == null) return false;
        try {
            //compare the action ids
            if (actionId != pa.actionId) return false;
            //check the classes of the two objects to make sure they're the same
            if (!obj.getClass().equals(pa.obj.getClass())) return false;
            //now get a Comparator for the objects and compare them
            return ObjectComparators.getComparator(obj.getClass()).compare(obj, pa.obj);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}