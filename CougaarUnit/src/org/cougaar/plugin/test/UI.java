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
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.AbstractTableModel;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.StringReader;
import org.xml.sax.InputSource;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;

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
  private JPanel jPanelDirJar = new JPanel();
  private BorderLayout borderLayout3 = new BorderLayout();
  private JTextField jTextFieldDirJar = new JTextField();
  private JButton jButtonBrowse = new JButton();
  private JLabel jLabelSelectDir = new JLabel();
  private FlowLayout flowLayout1 = new FlowLayout();
  private DefaultListModel testCaseModel = new DefaultListModel();
  private DefaultListModel testSuiteModel = new DefaultListModel();
  private Border border1;
  private TitledBorder titledBorder1;
  private Border border2;
  private TitledBorder titledBorder2;
  private Border border3;
  private TitledBorder titledBorder3;
  private Border border4;
  private TitledBorder titledBorder4;
  private JSplitPane jSplitPane1 = new JSplitPane();
  OutputTableModel outputTableModel = new OutputTableModel();
  private TitledBorder titledBorder5;
  private JButton jButtonSearch = new JButton();
  private JTabbedPane jTabbedPane1 = new JTabbedPane();
  private JTable jTableResults = new JTable(outputTableModel);
  private JScrollPane jScrollPaneResults = new JScrollPane();
  private JSplitPane jSplitPaneTests = new JSplitPane();
  private JList jListTestCases = new JList();
  private JPanel jPanelTests = new JPanel();
  private JList jListTestSuites = new JList();
  private BorderLayout borderLayout4 = new BorderLayout();
  private JScrollPane jScrollPaneTestSuites = new JScrollPane();
  private JScrollPane jScrollPaneTestCases = new JScrollPane();
  private JScrollPane jScrollPaneOutput = new JScrollPane();
  private JTextPane jTextPaneOutput = new JTextPane();

  private final Cursor CURSOR_WAIT = new Cursor(Cursor.WAIT_CURSOR);
  private final Cursor CURSOR_DEFAULT = new Cursor(Cursor.DEFAULT_CURSOR);
  private JMenuBar jMenuBar1 = new JMenuBar();
  private JMenu jMenu1 = new JMenu();
  private JMenuItem jMenuItem1 = new JMenuItem();
  private JMenu jMenu2 = new JMenu();
  private JMenuItem jMenuItem2 = new JMenuItem();

  class OutputTableModel extends AbstractTableModel {
    static final String COLUMN_TEST_NAME = "TEST NAME";
    static final String COLUMN_ID = "ID";
    static final String COLUMN_TEST_PHASE = "TEST PHASE";
    static final String COLUMN_COMMAND = "COMMAND";
    static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    static final String COLUMN_RESULT = "RESULT";

    protected final String[] columnNames = {COLUMN_TEST_NAME, COLUMN_ID, COLUMN_TEST_PHASE, COLUMN_COMMAND, COLUMN_DESCRIPTION, COLUMN_RESULT};
    Vector data = new Vector();

    public int getColumnCount() {
      return columnNames.length;
    }

    public int getRowCount() {
      return data.size();
    }

    public String getColumnName(int col) {
      return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
      if (data.size() > 0)
        return ((Object[])data.elementAt(row))[col];
      return null;
    }

    public void addRow(Object[] row) {
      data.add(row);
    }
  }

  class OutputCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table,
                                               Object value,
                                               boolean isSelected,
                                               boolean hasFocus,
                                               int row,
                                               int column) {
      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (table.getColumnName(column).equals(OutputTableModel.COLUMN_RESULT)) {
        if (value.equals("pass"))
          c.setBackground(Color.green);
        else
          c.setBackground(Color.red);
      }
      else
        c.setBackground(Color.white);

      return c;
    }
  }


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
    jTableResults.setDefaultRenderer(Object.class, new OutputCellRenderer());
    jTableResults.getColumn(OutputTableModel.COLUMN_TEST_NAME).setPreferredWidth(100);
    jTableResults.getColumn(OutputTableModel.COLUMN_ID).setPreferredWidth(5);
    jTableResults.getColumn(OutputTableModel.COLUMN_TEST_PHASE).setPreferredWidth(75);
    jTableResults.getColumn(OutputTableModel.COLUMN_COMMAND).setPreferredWidth(80);
    jTableResults.getColumn(OutputTableModel.COLUMN_DESCRIPTION).setPreferredWidth(100);
    jTableResults.getColumn(OutputTableModel.COLUMN_RESULT).setPreferredWidth(10);
    jTableResults.updateUI();
  }

  private void jbInit() throws Exception {
    this.setJMenuBar(jMenuBar1);
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
    titledBorder1 = new TitledBorder(border1,"Test Suites");
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder2 = new TitledBorder(border2,"Test Suites");
    border3 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder3 = new TitledBorder(border3,"Test Cases");
    border4 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
    titledBorder4 = new TitledBorder(border4,"Output");
    titledBorder5 = new TitledBorder("");
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 24));
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("Cougaar Unit");
    this.getContentPane().setLayout(borderLayout1);
    jButtonRun.setBorder(BorderFactory.createEtchedBorder());
    jButtonRun.setPreferredSize(new Dimension(73, 27));
    jButtonRun.setText("Run");
    jButtonRun.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonRun_actionPerformed(e);
      }
    });
    jButtonCancel.setBorder(BorderFactory.createEtchedBorder());
    jButtonCancel.setPreferredSize(new Dimension(73, 27));
    jButtonCancel.setText("Cancel");
    jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonCancel_actionPerformed(e);
      }
    });
    jPanel1.setLayout(borderLayout2);
    jPanel3.setLayout(borderLayout3);
    jTextFieldDirJar.setPreferredSize(new Dimension(230, 21));
    jButtonBrowse.setBorder(BorderFactory.createEtchedBorder());
    jButtonBrowse.setPreferredSize(new Dimension(73, 27));
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
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jButtonSearch.setBorder(BorderFactory.createEtchedBorder());
    jButtonSearch.setPreferredSize(new Dimension(73, 27));
    jButtonSearch.setText("Search");
    jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonSearch_actionPerformed(e);
      }
    });
    jTableResults.setRowSelectionAllowed(false);
    jScrollPaneResults.setBorder(null);
    jScrollPaneResults.setPreferredSize(new Dimension(70, 125));
    jSplitPaneTests.setPreferredSize(new Dimension(543, 230));
    jSplitPaneTests.setContinuousLayout(true);
    jPanelTests.setLayout(borderLayout4);
    jScrollPaneTestSuites.setBorder(titledBorder2);
    jScrollPaneTestSuites.setPreferredSize(new Dimension(268, 230));
    jScrollPaneTestCases.setBorder(titledBorder3);
    jScrollPaneTestCases.setPreferredSize(new Dimension(268, 230));
    jTextPaneOutput.setEditable(false);
    jListTestCases.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jListTestSuites.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jMenu1.setText("File");
    jMenuItem1.setText("Exit");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem1_actionPerformed(e);
      }
    });
    jMenu2.setText("Help");
    jMenuItem2.setText("About");
    jPanelDirJar.add(jLabelSelectDir, null);
    this.getContentPane().add(jLabel1,  BorderLayout.NORTH);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel3,  BorderLayout.NORTH);
    jPanel3.add(jPanelDirJar, BorderLayout.CENTER);
    jPanelDirJar.add(jTextFieldDirJar, null);
    jPanelDirJar.add(jButtonSearch, null);
    jPanelDirJar.add(jButtonBrowse, null);
    this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(jButtonCancel, null);
    jPanel2.add(jButtonRun, null);
    jPanel1.add(jSplitPane1, BorderLayout.CENTER);
    jSplitPane1.add(jTabbedPane1, JSplitPane.BOTTOM);
    jSplitPane1.add(jPanelTests, JSplitPane.TOP);
    jPanelTests.add(jSplitPaneTests, BorderLayout.CENTER);
    jSplitPaneTests.add(jScrollPaneTestSuites, JSplitPane.TOP);
    jSplitPaneTests.add(jScrollPaneTestCases, JSplitPane.BOTTOM);
    jScrollPaneTestCases.getViewport().add(jListTestCases, null);
    jScrollPaneTestSuites.getViewport().add(jListTestSuites, null);
    jTabbedPane1.add(jScrollPaneResults,   "Results");
    jTabbedPane1.add(jScrollPaneOutput,  "Output");
    jScrollPaneOutput.getViewport().add(jTextPaneOutput, null);
    jScrollPaneResults.getViewport().add(jTableResults, null);
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu2);
    jMenu1.add(jMenuItem1);
    jMenu2.add(jMenuItem2);
    jSplitPane1.setDividerLocation(200);
    jSplitPaneTests.setDividerLocation(250);
    jTabbedPane1.setSelectedComponent(jScrollPaneResults);

  }




  public static void main(String[] args) {
    UI ui= new UI();
    ui.pack();
    ui.setSize(800,600);
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

  private void processResult(String s) {
    jTextPaneOutput.setText(s);
    //find the beginning of the xml output
    int index = s.indexOf("<?xml");
    String xmlStr = s.substring(index);
    xmlStr = xmlStr.substring(0, xmlStr.lastIndexOf("</TEST>")+7);
    try {
      InputSource is = new InputSource(new StringReader(xmlStr));
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = builder.parse(is);

      //get the test name
      Element root = doc.getDocumentElement();
      String testName = root.getAttributes().getNamedItem("Name").getNodeValue();
      //get the list of test ids
      NodeList idList = root.getElementsByTagName("ID");
      for (int i=0; i<idList.getLength(); i++) {
        Object[] results = new Object[6];
        results[0] = testName;
        org.w3c.dom.Node idNode = (org.w3c.dom.Node)idList.item(i);
        String id = idNode.getAttributes().getNamedItem("Value").getNodeValue();
        NodeList subNodes = idNode.getChildNodes();
        results[1] = id;
        for (int j = 0; j<subNodes.getLength(); j++) {
          org.w3c.dom.Node n = subNodes.item(j);
          String nodeName = n.getNodeName();
          if (nodeName.equals("PHASE"))
            results[2] = n.getFirstChild().getNodeValue();
          else if (nodeName.equals("COMMAND"))
            results[3] = n.getFirstChild().getNodeValue();
          else if (nodeName.equals("DESCRIPTION"))
            results[4] = n.getFirstChild().getNodeValue();
          else if (nodeName.equals("RESULT"))
            results[5] = n.getFirstChild().getNodeValue();

        }
        outputTableModel.addRow(results);
        jTableResults.updateUI();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  void jButtonRun_actionPerformed(ActionEvent e) {
    ByteArrayOutputStream baos;
    Launcher launcher = new Launcher();
    launcher.setOutputStyle(Launcher.OUTPUT_STYLE_XML);
    Object[] testSuites = jListTestSuites.getSelectedValues();

    for (int i=0; i<testSuites.length; i++) {
      try {
        baos = new ByteArrayOutputStream(256);
        Class clazz = Class.forName((String)testSuites[i]);
        Thread t = new Thread(new LauncherRunnable("Executing test suite", launcher, clazz, baos));
        t.start();
      }
      catch (ClassNotFoundException ex) {
      }
    }

    Object[] testCases = jListTestCases.getSelectedValues();
    for (int i=0; i<testCases.length; i++) {
      try {
        baos = new ByteArrayOutputStream(256);
        Class clazz = Class.forName((String)testCases[i]);
        Thread t = new Thread(new LauncherRunnable("Executing test class", launcher, clazz, baos));
        t.start();
      }
      catch (ClassNotFoundException ex) {
      }
    }
  }

  class LauncherRunnable implements Runnable {
    Launcher launcher;
    Class clazz;
    ByteArrayOutputStream baos;
    String title;

    public LauncherRunnable(String title, Launcher launcher, Class clazz, ByteArrayOutputStream baos) {
      this.launcher = launcher;
      this.clazz = clazz;
      this.baos = baos;
      this.title = title;
    }

    public void run() {
      try {
        RunDialog rd = new RunDialog();
        rd.setTitle(title);
        rd.setMessage(clazz.getName());
        rd.show();
        rd.setCursor(CURSOR_WAIT);

        Object obj = clazz.newInstance();
        if (obj instanceof PluginTestCase)
          launcher.runTestCase((PluginTestCase)obj, baos);
        else if (obj instanceof PluginTestSuite)
          launcher.runTestSuite((PluginTestSuite)obj, baos);

        processResult(baos.toString());
        rd.dispose();
        rd.setCursor(CURSOR_DEFAULT);
      }
      catch (Exception e ){
        e.printStackTrace();
      }
    }
  }

  void jMenuItem1_actionPerformed(ActionEvent e) {
    System.exit(0);
  }
}