package org.cougaar.plugin.test.capture;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class BlackboardCaptureServlet extends HttpServlet {
  static final private String CONTENT_TYPE = "text/html";
  //Initialize global variables
  public void init() throws ServletException {
  }
  //Process the HTTP Get request
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    if (request.getParameter("CaptureButton") != null) {  //if the capture button is passed then perform the capture
      String path = request.getParameter("path");

      if (path == null)
        path = ".";
      try {
        BlackboardCaptureServletComponent.dumpBlackboard(path);
        out.println("<html><head></head><body>Data dumped to file: " + path + "</body></html>");
      }
      catch (Exception e) {
        out.println("<html><head></head><body>"+e.toString()+" while trying to save file: "+path+" </body></html>");
      }
    }
    else {   //otherwise serve up the form
      out.println("<html>");
      out.println("<head><title>BlackboardCaptureServlet</title></head>");
      out.println("<h1>Blackboard Capture Servlet</h1>");
      out.println("<body>");
      out.println("<FORM>");
      out.println("Enter the path to save captured data:&nbsp&nbsp");
      out.println("<INPUT type=\"text\" name=\"path\">");
      out.println("<BR><BR>");
      out.println("<INPUT type=\"submit\" name=\"CaptureButton\" value=\"Capture\" onclick=\"CaptureButton.value=path.value\">");
      out.println("</FORM>");
      out.println("</body></html>");
    }
  }
  //Clean up resources
  public void destroy() {
  }
}