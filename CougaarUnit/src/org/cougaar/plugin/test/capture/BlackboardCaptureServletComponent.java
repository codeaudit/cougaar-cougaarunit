package org.cougaar.plugin.test.capture;

import org.cougaar.util.*;
import org.cougaar.core.servlet.*;
import javax.servlet.Servlet;
import org.cougaar.core.service.BlackboardService;
import org.cougaar.core.blackboard.BlackboardClient;
import java.sql.Date;
import java.io.File;
import java.io.FileOutputStream;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class BlackboardCaptureServletComponent extends BaseServletComponent implements BlackboardClient {
  private BlackboardService blackboard;

  static BlackboardCaptureServletComponent me;

  public BlackboardCaptureServletComponent() {
    me = this;
  }

  public void setBlackboardService(BlackboardService blackboard) {
    this.blackboard = blackboard;
  }

  //dump the contents of the BlackboardCapturingServiceProxy to a file
  public static void dumpBlackboard(String path) throws Exception {
    if (me == null) {
      throw new Exception("Error getting reference to BlackboardCaptureServletComponent");
    }
    if (me.blackboard instanceof BlackboardServiceCapturingProxy) {
      String data = ((BlackboardServiceCapturingProxy)me.blackboard).getSerializedData();
      try {
        File f = new File(path);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(data.getBytes());
        fos.flush();
        fos.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    else {
      throw new Exception("Unable to get reference to BlackboardServiceCapturingProxy");
    }
  }

  // BlackboardClient method:
  public String getBlackboardClientName() {
    return toString();
  }

  // unused BlackboardClient method:
  public long currentTimeMillis() {
      return System.currentTimeMillis();
  }

  // unused BlackboardClient method:
  public boolean triggerEvent(Object event) {
      return false;
  }

  public void suspend() throws org.cougaar.util.StateModelException {
    /**@todo Implement this org.cougaar.util.GenericStateModel abstract method*/
  }

  protected String getPath() {
    return "/captureBlackboard";
  }

  protected Servlet createServlet() {
    // get the blackboard service
    blackboard = (BlackboardService) serviceBroker.getService(this, BlackboardService.class, null);
    if (blackboard == null) {
      throw new RuntimeException("Unable to obtain blackboard service");
    }
    return new BlackboardCaptureServlet();
  }
}