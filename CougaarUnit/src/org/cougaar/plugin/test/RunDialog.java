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

public class RunDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JLabel jLabelMessage = new JLabel();

  public RunDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
      center();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public RunDialog() {
    this(null, "", false);
  }
  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    jLabelMessage.setHorizontalTextPosition(SwingConstants.CENTER);
    getContentPane().add(panel1);
    panel1.add(jLabelMessage, BorderLayout.CENTER);
  }

  public void show() {
    super.show();
    center();
  }

  public void setMessage(String msg) {
    jLabelMessage.setText(msg);
  }

  private void center () {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension windowSize = this.getSize();
    this.setLocation((screenSize.width - windowSize.width)/2, (screenSize.height - windowSize.height)/2);
  }
}