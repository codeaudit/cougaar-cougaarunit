package org.cougaar.plugin.test.capture;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */
import javax.servlet.*;
import javax.servlet.http.*;
import org.cougaar.core.servlet.*;
import java.io.IOException;

public class BlackboardCaptureServlet extends HttpServlet {

  private SimpleServletSupport support;

  public BlackboardCaptureServlet() {
  }

  public void setSimpleServletSupport(SimpleServletSupport support)
  {
    this.support = support;
  }

  public void doGet(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException
  {
    execute(request, response);
  }

  private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
  {
  }
}