package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ResultsDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();

  public ResultsDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
      setSize(300,300);
      center();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public ResultsDialog() {
    this(null, "", false);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    getContentPane().add(panel1);
  }

  private void center () {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension windowSize = this.getSize();
    this.setLocation((screenSize.width - windowSize.width)/2, (screenSize.height - windowSize.height)/2);
  }

}