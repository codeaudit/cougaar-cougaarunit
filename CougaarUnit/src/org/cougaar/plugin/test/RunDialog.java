package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

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
  AnimatedPanel ap = new AnimatedPanel();
  private BorderLayout borderLayout2 = new BorderLayout();

  public RunDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
      setSize(200,200);
      center();
      init2();
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
    //panel1.setPreferredSize(new Dimension(200, 350));
    this.getContentPane().setLayout(borderLayout2);
    getContentPane().add(panel1, BorderLayout.CENTER);
    panel1.add(jLabelMessage, BorderLayout.NORTH);
  }

  private void init2() {
    panel1.add(ap, BorderLayout.CENTER);
    ap.setAnimation(AnimatedPanel.ANIMATION_SPIRAL);

  }

  private void resize() {
    int width = panel1.getGraphics().getFontMetrics().stringWidth(jLabelMessage.getText());
    this.setSize(new Dimension(width+100,200));
    panel1.setPreferredSize(new Dimension(width+100, 200));
    this.pack();
  }

  public void show() {
    resize();
    center();
    super.show();
    ap.start();
  }

  public void dispose() {
    ap.stop();
    super.dispose();
  }

  public void setMessage(String msg) {
    jLabelMessage.setText(msg);
  }

  private void center () {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension windowSize = this.getSize();
    this.setLocation((screenSize.width - windowSize.width)/2, (screenSize.height - windowSize.height)/2);
  }

  void jMenuItem1_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
}