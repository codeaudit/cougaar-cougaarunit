package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
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

    private void init2() {
        setSize(300,100);
        center();
        show();
    }

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

    public void setNote(String text) {
        jLabelNote.setText(text);
    }

    public void setHeading(String text) {
        jLabelHeading.setText(text);
    }

    public void setProgressValue(int value) {
        jProgressBar1.setValue(value);
    }

    public void close() {
        this.dispose();
    }

    private void center () {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = this.getSize();
        this.setLocation((screenSize.width - windowSize.width)/2, (screenSize.height - windowSize.height)/2);
    }
}