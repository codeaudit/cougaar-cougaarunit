package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/**
 * <p>Title: SearchProgressDialg</p>
 * <p>Description: Dialog which displays the progress of searches for test cases</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class SearchProgressDialog extends JDialog {
    private JPanel panel1 = new JPanel();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private JLabel jLabelNote = new JLabel();
    private JLabel jLabelHeading = new JLabel();
    private JProgressBar jProgressBar1 = new JProgressBar();

    /**
     * Constructor
     * @param frame
     * @param title
     * @param modal
     */
    public SearchProgressDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
            init2();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Secondary initializer
     */
    private void init2() {
        setSize(300,100);
        center();
        show();
    }

    /**
     * Constructor
     * @param heading
     * @param note
     * @param min
     * @param max
     */
    public SearchProgressDialog(String heading, String note, int min, int max) {
        this(null, "", false);
        this.jLabelHeading.setText(heading);
        this.jLabelNote.setText(note);
        if (max <= 0)  {
            jProgressBar1.setIndeterminate(true);
        }
        else {
            jProgressBar1.setMinimum(min);
            jProgressBar1.setMaximum(max);
        }
    }

    /**
     * UI initializer
     * @throws Exception
     */
    private void jbInit() throws Exception {
        panel1.setLayout(borderLayout1);
        jPanel1.setLayout(verticalFlowLayout1);
        jLabelNote.setToolTipText("");
        jLabelNote.setText("Note");
        jLabelHeading.setToolTipText("");
        jLabelHeading.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelHeading.setText("Seach Progress Dialog");
        this.setResizable(false);
        getContentPane().add(panel1);
        panel1.add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jLabelNote, null);
        panel1.add(jLabelHeading, BorderLayout.NORTH);
        jPanel1.add(jProgressBar1, null);
    }

    /**
     * Set the note to be displayed in the dialog
     * @param text
     */
    public void setNote(String text) {
        jLabelNote.setText(text);
    }

    /**
     * Set the dialog heading
     * @param text
     */
    public void setHeading(String text) {
        jLabelHeading.setText(text);
    }

    /**
     * Set the progress value
     * @param value
     */
    public void setProgressValue(int value) {
        jProgressBar1.setValue(value);
    }

    /**
     * Close the dialog
     */
    public void close() {
        this.dispose();
    }

    /**
     * Center dialog
     */
    private void center () {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = this.getSize();
        this.setLocation((screenSize.width - windowSize.width)/2, (screenSize.height - windowSize.height)/2);
    }
}