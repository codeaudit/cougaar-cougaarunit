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
    private Comparator comp = null;

    public PublishAction() {
    }
    public static final int ADD = 1;
    public static final int REMOVE = 2;
    public static final int CHANGE = 3;


    public PublishAction(int actionId, Object obj, Comparator comp) {
      this.actionId = actionId;
      this.obj = obj;
      this.comp = comp;
    }

    public PublishAction(int actionId, Object obj) {
      this(actionId, obj, null);
    }

    public PublishAction(int actionId) {
      this(actionId, null);
    }

    public void add(Object obj) {
      this.obj = obj;
    }

    public void addComparator(Comparator comp) {
      this.comp = comp;
    }

    private String getActionString(int actionID) {
     switch (actionID) {
         case ADD: return "ADD";
         case REMOVE: return "REMOVE";
         case CHANGE: return "CHANGE";
     }
     return "";
    }

    private String getResultString() {
      if (obj instanceof Class) {
        return ((Class)obj).getName();
      }
      else
        return obj.getClass().getName();
    }


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

    public String getXML() {
      StringBuffer ret = new StringBuffer().append("<PUBLISH_ACTION><ACTION_ID>").append(getActionString(actionId)).append("</ACTION_ID><RESULT>").append(getResultString()).append("</RESULT></PUBLISH_ACTION>");
      return ret.toString();
    }
}