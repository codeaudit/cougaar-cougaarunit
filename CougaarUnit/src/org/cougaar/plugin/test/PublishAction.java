package org.cougaar.plugin.test;

/**
 * <p>Title: PublishAction</p>
 * <p>Description:
 * This class allows the tester to contsruct Publish Actions which can then be tested
 * for.  A publish action consists of an action code and a reference to the object upom whcih  that action
 * taken.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class PublishAction  {

    private int actionId;
    private Object obj = null;
    private Comparator comp = null;
    public static final int ADD = 1;
    public static final int REMOVE = 2;
    public static final int CHANGE = 3;

    /**
     * Standard constructor
     */
    public PublishAction() {
    }

    /**
     * Constructor
     * @param actionId
     * @param obj
     * @param comp
     */
    public PublishAction(int actionId, Object obj, Comparator comp) {
      this.actionId = actionId;
      this.obj = obj;
      this.comp = comp;
    }

    /**
     * Constructor
     * @param actionId
     * @param obj
     */
    public PublishAction(int actionId, Object obj) {
      this(actionId, obj, null);
    }

    /**
     * Constructor
     * @param actionId
     */
    public PublishAction(int actionId) {
      this(actionId, null);
    }

    /**
     * Add an object to this PublishAction
     * @param obj
     */
    public void add(Object obj) {
      this.obj = obj;
    }

    /**
     * Add a comparator to this PublishAction.  This comparator will be used
     * when comparing the object within this publish action to objects in any
     * other publich action.
     * @param comp
     */
    public void addComparator(Comparator comp) {
      this.comp = comp;
    }

    /**
     * Get the string representation of an action
     * @param actionID
     * @return
     */
    private String getActionString(int actionID) {
     switch (actionID) {
         case ADD: return "ADD";
         case REMOVE: return "REMOVE";
         case CHANGE: return "CHANGE";
     }
     return "";
    }

    /**
     * Returns the class name of the object
     * @return
     */
    private String getResultString() {
      if (obj instanceof Class) {
        return ((Class)obj).getName();
      }
      else
        return obj.getClass().getName();
    }


    /**
     * Compares another PublishAction object
     * @param pa
     * @return
     */
    public boolean equals(PublishAction pa) {
        if (pa == null) return false;
        try {
            //compare the action ids
            if (actionId != pa.actionId) return false;
            //is there a specific comparator registered for this PublishAction
            if (comp != null) return comp.compare(obj, pa.obj);
            //now get a Comparator for the objects and compare them
            return ObjectComparators.getComparator(obj.getClass()).compare(obj, pa.obj);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get an XML version of the PublishAction
     * @return
     */
    public String getXML() {
      StringBuffer ret = new StringBuffer().append("<PUBLISH_ACTION><ACTION_ID>").append(getActionString(actionId)).append("</ACTION_ID><RESULT>").append(getResultString()).append("</RESULT></PUBLISH_ACTION>");
      return ret.toString();
    }
}