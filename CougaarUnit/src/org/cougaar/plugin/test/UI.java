package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author David Craine
 * @version 1.0
 */

public class UI extends JFrame {
    private BorderLayout borderLayout1 = new BorderLayout();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JButton jButtonRun = new JButton();
    private JButton jButtonCancel = new JButton();
    private BorderLayout borderLayout2 = new BorderLayout();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel4 = new JPanel();
    private JPanel jPanel5 = new JPanel();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JTextField jTextField1 = new JTextField();
    private JButton jButtonBrowse = new JButton();
    private JLabel jLabelSelectDir = new JLabel();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JList jList1 = new JList();
    private JList jList2 = new JList();
    private Border border1;
    private TitledBorder titledBorder1;
    private Border border2;
    private TitledBorder titledBorder2;
    private Border border3;
    private TitledBorder titledBorder3;
    private BorderLayout borderLayout4 = new BorderLayout();
    private JScrollPane jScrollPane3 = new JScrollPane();
    private JTextArea jTextArea1 = new JTextArea();
    private Border border4;
    private TitledBorder titledBorder4;

    public UI() {
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception {
        border1 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
        titledBorder1 = new TitledBorder(border1,"Test Suites");
        border2 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
        titledBorder2 = new TitledBorder(border2,"Test Suites");
        border3 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
        titledBorder3 = new TitledBorder(border3,"Test Cases");
        border4 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
        titledBorder4 = new TitledBorder(border4,"Output");
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Cougaar Unit");
        this.getContentPane().setLayout(borderLayout1);
        jButtonRun.setPreferredSize(new Dimension(73, 27));
        jButtonRun.setText("Run");
        jButtonCancel.setText("Cancel");
        jPanel1.setLayout(borderLayout2);
        jPanel3.setLayout(borderLayout3);
        jTextField1.setPreferredSize(new Dimension(350, 21));
        jTextField1.setText("jTextField1");
        jButtonBrowse.setText("Browse");
        jLabelSelectDir.setText("Select Directory");
        jPanel5.setLayout(flowLayout1);
        flowLayout1.setHgap(10);
        jPanel4.setLayout(borderLayout4);
        jScrollPane1.setBorder(titledBorder2);
        jScrollPane1.setPreferredSize(new Dimension(268, 230));
        jScrollPane2.setBorder(titledBorder3);
        jScrollPane2.setPreferredSize(new Dimension(268, 230));
        jSplitPane1.setPreferredSize(new Dimension(543, 230));
        jSplitPane1.setContinuousLayout(true);
        jTextArea1.setPreferredSize(new Dimension(70, 125));
        jScrollPane3.setBorder(titledBorder4);
        jPanel5.add(jLabelSelectDir, null);
        this.getContentPane().add(jLabel1,  BorderLayout.NORTH);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.NORTH);
        jPanel3.add(jPanel5, BorderLayout.CENTER);
        jPanel5.add(jTextField1, null);
        jPanel5.add(jButtonBrowse, null);
        jPanel1.add(jPanel4, BorderLayout.CENTER);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jButtonCancel, null);
        jPanel2.add(jButtonRun, null);
        jPanel4.add(jSplitPane1, BorderLayout.CENTER);
        jSplitPane1.add(jScrollPane1, JSplitPane.TOP);
        jScrollPane1.getViewport().add(jList1, null);
        jSplitPane1.add(jScrollPane2, JSplitPane.BOTTOM);
        jPanel1.add(jScrollPane3,  BorderLayout.SOUTH);
        jScrollPane3.getViewport().add(jTextArea1, null);
        jScrollPane2.getViewport().add(jList2, null);
        jSplitPane1.setDividerLocation(250);
    }
    public static void main(String[] args) {
        UI ui= new UI();
        ui.setSize(500,500);
        ui.show();
    }
}