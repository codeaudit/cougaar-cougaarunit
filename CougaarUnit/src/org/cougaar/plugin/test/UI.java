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
import java.io.FileFilter;
import org.apache.bcel.classfile.*;
import org.apache.bcel.Repository;
import javax.swing.ProgressMonitor;

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
    private JButton jButtonSearch = new JButton();

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
        jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonRun_actionPerformed(e);
            }
        });
        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonCancel_actionPerformed(e);
            }
        });
        jPanel1.setLayout(borderLayout2);
        jPanel3.setLayout(borderLayout3);
        jTextFieldDirJar.setPreferredSize(new Dimension(230, 21));
        jButtonBrowse.setText("Browse");
        jButtonBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonBrowse_actionPerformed(e);
            }
        });
        jLabelSelectDir.setText("Select Directory/Jar File");
        jPanelDirJar.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
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
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                this_windowClosing(e);
            }
        });
        jButtonSearch.setText("Search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonSearch_actionPerformed(e);
            }
        });
        jPanelDirJar.add(jLabelSelectDir, null);
        this.getContentPane().add(jLabel1,  BorderLayout.NORTH);
        this.getContentPane().add(jPanel1, BorderLayout.CENTER);
        jPanel1.add(jPanel3, BorderLayout.NORTH);
        jPanel3.add(jPanelDirJar, BorderLayout.CENTER);
        jPanelDirJar.add(jTextFieldDirJar, null);
        jPanelDirJar.add(jButtonSearch, null);
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
        ui.pack();
        ui.center();
        ui.show();
    }

    private void searchJarFile(File file) {
        //search jar file for classes that are instances of PLutinTestCase or PluginTestSuite
        try {
            JarFile jarFile = new JarFile(file);
            int count = 0;
            for (Enumeration entries = jarFile.entries(); entries.hasMoreElements(); ) {
                JarEntry entry = (JarEntry)entries.nextElement();
                String fileName = entry.getName();
                if (fileName.endsWith(".class")) {
                    try {
                        ClassParser cp = new ClassParser(jarFile.getInputStream(entry), fileName);
                        JavaClass jc = cp.parse();
                        if (Repository.instanceOf(jc, "org.cougaar.plugin.test.PluginTestCase")) {
                            testCaseModel.addElement(jc.getClassName());
                        }
                        else if (Repository.instanceOf(jc,"org.cougaar.plugin.test.PluginTestSuite")) {
                            testSuiteModel.addElement(jc.getClassName());
                        }
                    }
                    catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
        }
        catch (Exception e) {}
    }


    void jButtonBrowse_actionPerformed(ActionEvent e) {

        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Select directory or jar file");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.getName().endsWith(".jar") || pathname.isDirectory())
                    return true;
                return false;
            }
            public String getDescription() {
                return "Jars and Dirs";
            }
        });
        int ret = jfc.showOpenDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            jTextFieldDirJar.setText(jfc.getSelectedFile().getAbsolutePath());
        }

    }

    void jButtonCancel_actionPerformed(ActionEvent e) {
        System.exit(1);
    }

    void this_windowClosing(WindowEvent e) {
        System.exit(1);
    }

    private void center () {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = this.getSize();
        this.setLocation((screenSize.width - windowSize.width)/2, (screenSize.height - windowSize.height)/2);
    }

    void jButtonSearch_actionPerformed(ActionEvent e) {
        Thread t = new Thread() {
            public void run() {
                try {
                    File f = new File(jTextFieldDirJar.getText());
                    if (f.isDirectory()) {
                        //search for class and jar files that are instance of PluginTestCase or PluginTestSuite
                        File[] jarFiles = f.listFiles( new FileFilter() {
                                    public boolean accept(File pathname) {
                                    if (pathname.getName().endsWith(".jar"))
                                        return true;
                                    return false;
                                    }
                        });
                        SearchProgressDialog spd = new SearchProgressDialog("Searching for test cases/suites...", "", 0,0);
                        spd.setProgressValue(0);
                        int count = 0;
                        for (int i=0; i<jarFiles.length; i++) {
                            spd.setNote(jarFiles[i].getName());
                            spd.setProgressValue(count++);
                            searchJarFile(jarFiles[i]);
                        }
                        spd.close();
                    }
                    else if (f.getName().toLowerCase().endsWith(".jar")) {
                        searchJarFile(f);
                    }
                }
                catch (Exception e2){}
            }
        };
        t.start();
    }

    void jButtonRun_actionPerformed(ActionEvent e) {
        Object[] testSuites = jListTestSuites.getSelectedValues();

    }
}
