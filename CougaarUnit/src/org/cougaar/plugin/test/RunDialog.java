package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>Title: RunDialog</p>
 * <p>Description: Dialog which displays when tests are running</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class RunDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JLabel jLabelMessage = new JLabel();
  AnimatedPanel ap = new AnimatedPanel();
  private BorderLayout borderLayout2 = new BorderLayout();

  /**
   * Constructor
   * @param frame
   * @param title
   * @param modal
   */
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

  /**
   * Constructor
   */
  public RunDialog() {
    this(null, "", false);
  }

  /**
   * UI Initializer
   * @throws Exception
   */
  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    jLabelMessage.setHorizontalTextPosition(SwingConstants.CENTER);
    //panel1.setPreferredSize(new Dimension(200, 350));
    this.getContentPane().setLayout(borderLayout2);
    this.setResizable(false);
    getContentPane().add(panel1, BorderLayout.CENTER);
    panel1.add(jLabelMessage, BorderLayout.NORTH);
  }

  /**
   * seconday UI initializer
   */
  private void init2() {
    panel1.add(ap, BorderLayout.CENTER);
    ap.setAnimation(AnimatedPanel.ANIMATION_FIREWORKS);

  }

  /**
   * Resize the dialgo
   */
  private void resize() {
    int width = panel1.getGraphics().getFontMetrics().stringWidth(jLabelMessage.getText());
    this.setSize(new Dimension(width+100,200));
    panel1.setPreferredSize(new Dimension(width+100, 200));
    this.pack();
  }

  /**
   * override the show method to start the animation
   */
  public void show() {
    resize();
    center();
    super.show();
    ap.start();
  }

  /**
   * override the dispose method to stop the animation
   */
  public void dispose() {
    ap.stop();
    super.dispose();
  }

  /**
   * Set the message for the dialog
   * @param msg
   */
  public void setMessage(String msg) {
    jLabelMessage.setText(msg);
  }

  /**
   * center the dialog on the screen
   */
  private void center () {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension windowSize = this.getSize();
    this.setLocation((screenSize.width - windowSize.width)/2, (screenSize.height - windowSize.height)/2);
  }
}