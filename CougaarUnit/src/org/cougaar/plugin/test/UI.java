package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.File;
import java.util.jar.JarFile;
import java.util.Enumeration;
import java.util.jar.JarEntry;

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
    private JPanel jPanelTests = new JPanel();
    private JPanel jPanelDirJar = new JPanel();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JTextField jTextFieldDirJar = new JTextField();
    private JButton jButtonBrowse = new JButton();
    private JLabel jLabelSelectDir = new JLabel();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JSplitPane jSplitPaneTests = new JSplitPane();
    private JScrollPane jScrollPaneTestSuites = new JScrollPane();
    private JScrollPane jScrollPaneTestCases = new JScrollPane();
    private JList jListTestSuites = new JList();
    private JList jListTestCases = new JList();
    private DefaultListModel testCaseModel = new DefaultListModel();
    private DefaultListModel testSuiteModel = new DefaultListModel();
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
            init2();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void init2() {
        jListTestCases.setModel(testCaseModel);
        jListTestSuites.setModel(testSuiteModel);
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
        jTextFieldDirJar.setPreferredSize(new Dimension(300, 21));
        jButtonBrowse.setText("Browse");
        jButtonBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonBrowse_actionPerformed(e);
            }
        });
        jLabelSelectDir.setText("Select Directory/Jar File");
        jPanelDirJar.setLayout(flowLayout1);
        flowLayout1.setHgap(10);
        jPanelTests.setLayout(borderLayout4);
        jScrollPaneTestSuites.setBorder(titledBorder2);
        jScrollPaneTestSuites.setPreferredSize(new Dimension(268, 230));
        jScrollPaneTestCases.setBorder(titledBorder3);
        jScrollPaneTestCases.setPreferredSize(new Dimension(268, 230));
        jSplitPaneTests.setPreferredSize(new Dimension(543, 230));
        jSplitPaneTests.setContinuousLayout(true);
        jTextArea1.setPreferredSize(new Dimension(70, 125));
        jScrollPane3.setBorder(titledBorder4);
        jPanelDirJar.add(jLabelSelectDir, null);
        this.getContentPane().add(jLabel1,  BorderLayout.NORTH);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.NORTH);
        jPanel3.add(jPanelDirJar, BorderLayout.CENTER);
        jPanelDirJar.add(jTextFieldDirJar, null);
        jPanelDirJar.add(jButtonBrowse, null);
        jPanel1.add(jPanelTests, BorderLayout.CENTER);
        this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
        jPanel2.add(jButtonCancel, null);
        jPanel2.add(jButtonRun, null);
        jPanelTests.add(jSplitPaneTests, BorderLayout.CENTER);
        jSplitPaneTests.add(jScrollPaneTestSuites, JSplitPane.TOP);
        jScrollPaneTestSuites.getViewport().add(jListTestSuites, null);
        jSplitPaneTests.add(jScrollPaneTestCases, JSplitPane.BOTTOM);
        jPanel1.add(jScrollPane3,  BorderLayout.SOUTH);
        jScrollPane3.getViewport().add(jTextArea1, null);
        jScrollPaneTestCases.getViewport().add(jListTestCases, null);
        jSplitPaneTests.setDividerLocation(250);
    }
    public static void main(String[] args) {
        UI ui= new UI();
        ui.setSize(600,500);
        ui.show();
    }

    private void searchJarFile(File file) {
        //search jar file for classes that are instances of PLutinTestCase or PluginTestSuite
        try {
            JarFile jarFile = new JarFile(file);
            for (Enumeration entries = jarFile.entries(); entries.hasMoreElements(); ) {
                JarEntry entry = (JarEntry)entries.nextElement();
                String fileName = entry.getName();
                if (fileName.endsWith(".class")) {
                    String className = fileName.substring(0, fileName.lastIndexOf(".class"));
                    className = className.replace('/', '.');
                    try {
                        Class clazz = this.getClass().forName(className);
                        Class superClazz = clazz.getSuperclass();
                        if (superClazz.equals(PluginTestCase.class)) {
                            testCaseModel.addElement(clazz);
                        }
                        else if (superClazz.equals(PluginTestSuite.class)) {
                            testSuiteModel.addElement(clazz);
                        }
                    }
                    catch (RuntimeException rtw){}
                    catch (Exception e) {}

                }
            }
        }
        catch (Exception e) {}
    }


    void jButtonBrowse_actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Select directory or jar file");
        int ret = jfc.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File f = jfc.getSelectedFile();
            if (f.isDirectory()) {
                //search for class and jar files that are instance of PluginTestCase or PluginTestSuite

            }
            else if (f.getName().toLowerCase().endsWith(".jar")) {
                searchJarFile(f);
            }
        }

    }
}