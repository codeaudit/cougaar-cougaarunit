package org.cougaar.plugin.test.capture;

import java.io.Serializable;
import java.io.IOException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class CapturedPublishAction implements Serializable {
    public final static int ACTION_ADD = 1;
    public final static int ACTION_CHANGE =2 ;
    public final static int ACTION_REMOVE = 3;
    public final static int ACTION_OPEN_TRANSACTION = 4;
    public final static int ACTION_CLOSE_TRANSACTION = 5;
    public final static int ACTION_INTERAGENT_TRANSFER = 6;

    public transient Object publishedObject;
    public int action;
    public String publishingSource;
    public long timeStamp;


    public CapturedPublishAction(int action, Object publishedObject, String publishingSource) {
        this.action = action;
        this.publishedObject = publishedObject;
        this.publishingSource = publishingSource;
        this.timeStamp = System.currentTimeMillis();
    }

    public static String getActionString(int actionId) {
      switch (actionId) {
          case ACTION_ADD: return "ADD";
          case ACTION_REMOVE: return "REMOVE";
          case ACTION_CHANGE: return "CHANGE";
          case ACTION_OPEN_TRANSACTION: return "OPEN TRANSACTION";
          case ACTION_CLOSE_TRANSACTION: return "CLOSE TRANSACTION";
          case ACTION_INTERAGENT_TRANSFER: return "INTERAGENT TRANSFER";
      }
     return "";
    }
    /**
     * Get the string representation of an action
     * @param actionID
     * @return
     */
    public String getActionString() {
      return CapturedPublishAction.getActionString(action);
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();
      ExternalizedContainer cont = Externalizers.externalize(publishedObject);
      out.writeObject(cont);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      ExternalizedContainer cont= (ExternalizedContainer)in.readObject();
      publishedObject = Externalizers.internalize(cont);
    }

}