package org.cougaar.plugin.test;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.xml.parsers.*;

import org.apache.bcel.*;
import org.apache.bcel.classfile.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import com.borland.jbcl.control.MessageDialog;
import java.io.FileInputStream;
import org.cougaar.plugin.test.capture.CapturedPublishAction;
import java.io.FilenameFilter;
import org.cougaar.plugin.test.util.*;
import com.borland.jbcl.layout.*;

/**
 * <p>Title: UI</p>
 * <p>Description: The main UI class</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class UI extends JFrame {
  private JTabbedPane jTabbedPaneMain = new JTabbedPane();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JLabel jLabel1 = new JLabel();
  private JPanel jPanelTestRunner = new JPanel();
  private JPanel jPanel2 = new JPanel();
  private JButton jButtonRun = new JButton();
  private JButton jButtonCancel = new JButton();
  private BorderLayout borderLayout2 = new BorderLayout();
  private JPanel jPanel3 = new JPanel();
  private JPanel jPanelDirJar = new JPanel();
  private BorderLayout borderLayout3 = new BorderLayout();
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
  TimeDataTableModel timeDataTableModel = new TimeDataTableModel();
  DefaultTableModel sourceDataTableModel = new DefaultTableModel();
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
  private JMenuBar jMenuBarMain = new JMenuBar();
  private JMenu jMenuFile = new JMenu();
  private JMenuItem jMenuItemExit = new JMenuItem();
  private JMenu jMenuHelp = new JMenu();
  private JMenu jMenuOptions = new JMenu();
  private JMenuItem jMenuItemAbout = new JMenuItem();
  private JMenuItem jMenuItemClearHistory = new JMenuItem();
  private JComboBox jComboBoxDirJar = new JComboBox();
  private JPopupMenu jPopupMenuOutput = new JPopupMenu();
  private JMenuItem jMenuItemOuptutSave = new JMenuItem();
  private JMenuItem jMenuItemOutputPrint = new JMenuItem();
  private JPopupMenu jPopupMenuResults = new JPopupMenu();
  private JMenuItem jMenuItemResultsSave = new JMenuItem();
  private JMenuItem jMenuItemResultsPrint = new JMenuItem();
  private JPanel jPanelObjectStreamEditor = new JPanel();
  private BorderLayout borderLayout5 = new BorderLayout();
  private JMenuItem jMenuItemOpenDataFile = new JMenuItem();
  private JPanel jPanelViews = new JPanel();
  private ButtonGroup buttonGroup1 = new ButtonGroup();
  private JRadioButton jRadioButtonTimeView = new JRadioButton();
  private JRadioButton jRadioButtonSourceView = new JRadioButton();
  private JSplitPane jSplitPaneViews = new JSplitPane();
  private OverlayLayout2 overlayLayout21 = new OverlayLayout2();
  private SortableJTable jTableSourceData = new SortableJTable(sourceDataTableModel);
  private SortableJTable jTableTimeData = new SortableJTable(timeDataTableModel);
  private JScrollPane jScrollPaneTimeData = new JScrollPane();
  private JPanel jPanelTableViews = new JPanel();
  private JScrollPane jScrollPaneSourceData = new JScrollPane();
  private JScrollPane jScrollPaneDetails = new JScrollPane();
  private JList jListDetails = new JList();


  private static final String SOURCE_DATA_COL_ID = "ID";
  private static final String SOURCE_DATA_COL_TIME = "TIME";
  private JMenuItem jMenuItemClearResults = new JMenuItem();
  private MyClassLoader loader;
  /**
   *
   * <p>Title: OutputTableModel</p>
   * <p>Description: Model for holding the output data</p>
   * <p>Copyright: Copyright (c) 2002</p>
   * <p>Company: InfoEther, LLC</p>
   * @author David Craine
   * @version 1.0
   */

  class TimeDataTableModel extends DefaultTableModel  {
    static final String COLUMN_PUBLISH_ACTION = "PUBLISH ACTION";
    static final String COLUMN_SOURCE = "SOURCE";
    static final String COLUMN_OBJECT = "OBJECT";
    static final String COLUMN_ID = "ID";
    static final String COLUMN_TIME = "TIME";
    protected final String[] columnNames = {COLUMN_ID, COLUMN_TIME, COLUMN_SOURCE, COLUMN_PUBLISH_ACTION, COLUMN_OBJECT};

    public int getColumnCount() {
      return columnNames.length;
    }

    public String getColumnName(int col) {
      return columnNames[col];
    }
  }

  class OutputTableModel extends AbstractTableModel {
    static final String COLUMN_TEST_NAME = "TEST NAME";
    static final String COLUMN_ID = "ID";
    static final String COLUMN_TEST_PHASE = "TEST PHASE";
    static final String COLUMN_COMMAND = "COMMAND";
    static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    static final String COLUMN_RESULT = "RESULT";

    protected final String[] columnNames = {COLUMN_TEST_NAME, COLUMN_ID, COLUMN_TEST_PHASE, COLUMN_COMMAND, COLUMN_DESCRIPTION, COLUMN_RESULT};
    Vector data = new Vector();
    HashMap ancillaryData = new HashMap();

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

    public void addCellAncillaryData(int row, int col, Object data) {
      if (data == null) return;
      ancillaryData.put(String.valueOf(row)+String.valueOf(col), data);
    }

    public Object getCellAncillaryData(int row, int col) {
      return ancillaryData.get(String.valueOf(row)+String.valueOf(col));
    }

    public void clear() {
      data.removeAllElements();
    }
  }

  /**
   *
   * <p>Title: OutputCellRendere</p>
   * <p>Description: Cell Renderer for the output display</p>
   * <p>Copyright: Copyright (c) 2002</p>
   * <p>Company: InfoEther, LLC</p>
   * @author David Craine
   * @version 1.0
   */
  class OutputCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column) {
      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if (table.getColumnName(column).equals(OutputTableModel.COLUMN_RESULT)) {
        c = new JButton(value.toString());
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

  class SourceTableCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column) {
      String normalValue = value.toString().trim();
      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      c.setBackground(determineColor(table, value, row, column));
      return c;
    }

    private Color determineColor(JTable table, Object value, int row, int column) {
      String normalValue = value.toString().trim();
      if (normalValue.equals("OPEN TRANSACTION") || normalValue.equals("CLOSE TRANSACTION")) {
        return Color.green;
      }
      else if (row > 0) {
        int count = row-1;
        while (count >= 0) {
          String prevValue = table.getValueAt(count, column).toString().trim();
          if (prevValue.equals("OPEN TRANSACTION"))
            return Color.green;
          else if (prevValue.equals("CLOSE TRANSACTION"))
            return Color.white;
          count--;
        }
      }
      return Color.white;
    }
  }

  /**
   * Constructor
   */
  public UI() {
    try {
      this.setupIcons();
      jbInit();
      init2();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Seconday UI initializer
   */
  private void init2() {
    //configure the customized class loader
    configClassLoader();

    jListTestCases.setModel(testCaseModel);
    jListTestSuites.setModel(testSuiteModel);
    jTableResults.setDefaultRenderer(Object.class, new OutputCellRenderer());
    jTableResults.getColumn(OutputTableModel.COLUMN_TEST_NAME).setPreferredWidth(100);
    jTableResults.getColumn(OutputTableModel.COLUMN_ID).setMaxWidth(40);
    jTableResults.getColumn(OutputTableModel.COLUMN_TEST_PHASE).setPreferredWidth(75);
    jTableResults.getColumn(OutputTableModel.COLUMN_COMMAND).setPreferredWidth(80);
    jTableResults.getColumn(OutputTableModel.COLUMN_DESCRIPTION).setPreferredWidth(100);
    jTableResults.getColumn(OutputTableModel.COLUMN_RESULT).setPreferredWidth(10);
    jTableResults.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getModifiers() == Event.META_MASK) {
          // Make the jPopupMenu visible relative to the current mouse position in the container.
          jPopupMenuResults.show(jTableResults, e.getX(), e.getY());
        }
        else {//otherwise if the click was on the results column, then display the results detail dialog
          int col = jTableResults.getSelectedColumn();
          if ((col != -1) && jTableResults.getColumnName(col).equals(OutputTableModel.COLUMN_RESULT)) {
            ResultsDialog rd = new ResultsDialog();
            rd.setResultData((ResultStates)UI.this.outputTableModel.getCellAncillaryData(jTableResults.getSelectedRow(), jTableResults.getSelectedColumn()));
            rd.show();
          }
        }
      }

      public void mousePressed(MouseEvent e) {
      }
      public void mouseReleased(MouseEvent e) {
      }
      public void mouseEntered(MouseEvent e) {
      }
      public void mouseExited(MouseEvent e) {
      }
    });

    jTextPaneOutput.add(jPopupMenuOutput);
    jTextPaneOutput.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (e.getModifiers() == Event.META_MASK) {
          // Make the jPopupMenu visible relative to the current mouse position in the container.
          jPopupMenuOutput.show(jTextPaneOutput, e.getX(), e.getY());
        }
      }
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
    });
    jTableResults.add(jPopupMenuResults);
    jTableResults.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {

      }
      public void mousePressed(MouseEvent e) {}
      public void mouseReleased(MouseEvent e) {}
      public void mouseEntered(MouseEvent e) {}
      public void mouseExited(MouseEvent e) {}
    });
    jTableResults.updateUI();

    jTableTimeData.getColumn(timeDataTableModel.COLUMN_ID).setMaxWidth(40);
    jTableTimeData.getColumn(timeDataTableModel.COLUMN_TIME).setMaxWidth(40);
    jTableTimeData.getColumn(timeDataTableModel.COLUMN_SOURCE).setPreferredWidth(100);
    jTableTimeData.getColumn(timeDataTableModel.COLUMN_PUBLISH_ACTION).setPreferredWidth(75);
    jTableTimeData.getColumn(timeDataTableModel.COLUMN_OBJECT).setPreferredWidth(100);
    jTableTimeData.setRowSelectionAllowed(true);
    jTableTimeData.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        displaySelectedRow(jTableTimeData);
      }
    });
    jTableTimeData.updateUI();

    jRadioButtonTimeView.setSelected(true);
    readFileHistory();

    jTableSourceData.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        displaySelectedRow(jTableSourceData);
      }
    });

    jScrollPaneSourceData.setVisible(false);
    jScrollPaneTimeData.setVisible(true);

    jTableSourceData.setDefaultRenderer(Object.class, new SourceTableCellRenderer());
  }

  private void setupIcons() {
    URL url = getClass().getClassLoader().getResource("images/app.gif");
    Image icon = Toolkit.getDefaultToolkit().getImage(url);
    this.setIconImage(icon);
    this.setTitle("CougaarUnit");
  }

  /**
   * UI initializer
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setJMenuBar(jMenuBarMain);
    jTableResults.setRowSelectionAllowed(false);
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
    jPanelTestRunner.setLayout(borderLayout2);
    jPanel3.setLayout(borderLayout3);
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
    jMenuFile.setText("File");
    jMenuItemExit.setText("Exit");
    jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemExit_actionPerformed(e);
      }
    });
    jMenuHelp.setText("Help");
    jMenuItemAbout.setText("About");
    jMenuOptions.setText("Options");
    jMenuItemClearHistory.setText("Clear History");
    jMenuItemClearHistory.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemClearHistory_actionPerformed(e);
      }
    });
    jComboBoxDirJar.setPreferredSize(new Dimension(400, 21));
    jComboBoxDirJar.setEditable(true);
    jMenuItemOuptutSave.setText("Save");
    jMenuItemOuptutSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemOuptutSave_actionPerformed(e);
      }
    });
    jMenuItemOutputPrint.setText("Print");
    jMenuItemOutputPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemOutputPrint_actionPerformed(e);
      }
    });
    jMenuItemResultsSave.setText("Save");
    jMenuItemResultsSave.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemResultsSave_actionPerformed(e);
      }
    });
    jMenuItemResultsPrint.setText("Print");
    jMenuItemResultsPrint.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemResultsPrint_actionPerformed(e);
      }
    });
    jPanelObjectStreamEditor.setLayout(borderLayout5);
    jMenuItemOpenDataFile.setText("Open Data File");
    jMenuItemOpenDataFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemOpenDataFile_actionPerformed(e);
      }
    });
    jRadioButtonTimeView.setText("Time View");
    jRadioButtonTimeView.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jRadioButtonTimeView_actionPerformed(e);
      }
    });
    jRadioButtonSourceView.setText("Source View");
    jRadioButtonSourceView.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jRadioButtonSourceView_actionPerformed(e);
      }
    });
    jPanelTableViews.setLayout(overlayLayout21);
    jSplitPaneViews.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jTableSourceData.setSelectionForeground(Color.yellow);
    jTableSourceData.setRowSelectionAllowed(true);
    jMenuItemClearResults.setText("Clear");
    jMenuItemClearResults.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItemClearResults_actionPerformed(e);
      }
    });
    jPanelDirJar.add(jLabelSelectDir, null);
    jPanelDirJar.add(jComboBoxDirJar, null);
    this.getContentPane().add(jLabel1,  BorderLayout.NORTH);
    this.getContentPane().add(jTabbedPaneMain, BorderLayout.CENTER);
    jTabbedPaneMain.add(jPanelTestRunner, "Test Runner");
    jPanelTestRunner.add(jPanel3,  BorderLayout.NORTH);
    jPanel3.add(jPanelDirJar, BorderLayout.CENTER);
    jPanelDirJar.add(jButtonSearch, null);
    jPanelDirJar.add(jButtonBrowse, null);
    jPanelTestRunner.add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(jButtonCancel, null);
    jPanel2.add(jButtonRun, null);
    jPanelTestRunner.add(jSplitPane1, BorderLayout.CENTER);
    jSplitPane1.add(jTabbedPane1, JSplitPane.BOTTOM);
    jSplitPane1.add(jPanelTests, JSplitPane.TOP);
    jPanelTests.add(jSplitPaneTests, BorderLayout.CENTER);
    jSplitPaneTests.add(jScrollPaneTestSuites, JSplitPane.TOP);
    jSplitPaneTests.add(jScrollPaneTestCases, JSplitPane.BOTTOM);
    jTabbedPaneMain.add(jPanelObjectStreamEditor,  "Object Stream Editor");
    jPanelObjectStreamEditor.add(jPanelViews,  BorderLayout.NORTH);
    jScrollPaneTestCases.getViewport().add(jListTestCases, null);
    jScrollPaneTestSuites.getViewport().add(jListTestSuites, null);
    jTabbedPane1.add(jScrollPaneResults,   "Results");
    jTabbedPane1.add(jScrollPaneOutput,  "Output");
    jScrollPaneOutput.getViewport().add(jTextPaneOutput, null);
    jScrollPaneResults.getViewport().add(jTableResults, null);
    jMenuBarMain.add(jMenuFile);
    jMenuBarMain.add(jMenuOptions);
    jMenuBarMain.add(jMenuHelp);
    jMenuFile.add(jMenuItemOpenDataFile);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuItemExit);
    jMenuOptions.add(jMenuItemClearHistory);
    jPopupMenuOutput.add(jMenuItemOuptutSave);
    jPopupMenuOutput.add(jMenuItemOutputPrint);
    jPopupMenuResults.add(jMenuItemResultsSave);
    jPopupMenuResults.add(jMenuItemResultsPrint);
    jPopupMenuResults.addSeparator();
    jPopupMenuResults.add(jMenuItemClearResults);
    jSplitPane1.setDividerLocation(200);
    jSplitPaneTests.setDividerLocation(250);
    jTabbedPane1.setSelectedComponent(jScrollPaneResults);
    jPanelViews.add(jRadioButtonTimeView, null);
    jPanelViews.add(jRadioButtonSourceView, null);
    jPanelObjectStreamEditor.add(jSplitPaneViews,  BorderLayout.CENTER);
    jSplitPaneViews.add(jPanelTableViews, JSplitPane.TOP);
    jPanelTableViews.add(jScrollPaneSourceData, null);
    jPanelTableViews.add(jScrollPaneTimeData, null);
    jSplitPaneViews.add(jScrollPaneDetails, JSplitPane.BOTTOM);
    jScrollPaneDetails.getViewport().add(jListDetails, null);
    jScrollPaneTimeData.getViewport().add(jTableTimeData, null);
    jScrollPaneSourceData.getViewport().add(jTableSourceData, null);
    buttonGroup1.add(jRadioButtonTimeView);
    buttonGroup1.add(jRadioButtonSourceView);
    jSplitPaneViews.setDividerLocation(200);
  }

  /**
   * Main method.  Used for starting the UI.
   * @param args
   */
  public static void main(String[] args) {
    UI ui= new UI();
    //((UI.OutputTableModel)ui.jTableResults.getModel()).addRow(new String[] {"1", "2", "3", "4", "5", "pass"});
    ui.pack();
    ui.setSize(800,600);
    ui.center();
    ui.show();
  }

  private void displaySelectedRow(JTable table ) {
    jListDetails.removeAll();
    int columnCount = table.getColumnCount();
    int row = table.getSelectedRow();
    Vector values = new Vector();
    for (int i=0; i<columnCount; i++ ) {
      String entry = table.getColumnName(i)+ ":   " + table.getValueAt(row, i);
      values.addElement(entry);
    }
    jListDetails.setListData(values);
  }

  /**
   * Search a jar file for PluginTestCase classes and PluginTestSuite classes.
   * @param file
   */
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

  private void checkClassFile(File file) {
    try {
      ClassParser cp = new ClassParser(file.getAbsolutePath());
      JavaClass jc = cp.parse();
      if (Repository.instanceOf(jc, "org.cougaar.plugin.test.PluginTestCase")) {
        testCaseModel.addElement(jc.getClassName());
      }
      else if (Repository.instanceOf(jc,"org.cougaar.plugin.test.PluginTestSuite")) {
        testSuiteModel.addElement(jc.getClassName());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Handler for the Browse button
   * @param e
   */
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
      //update the combo box
      String path  = jfc.getSelectedFile().getAbsolutePath();
      if ((((DefaultComboBoxModel)jComboBoxDirJar.getModel()).getIndexOf(path)) == -1){
        jComboBoxDirJar.addItem(path);
      }
      jComboBoxDirJar.setSelectedItem(path);
    }
  }

  /**
   * Handler for the Cancel button
   * @param e
   */
  void jButtonCancel_actionPerformed(ActionEvent e) {
    closeApp();
  }

  /**
   * Handler for the WindowClosing event
   * @param e
   */
  void this_windowClosing(WindowEvent e) {
    closeApp();
  }

  /**
   * Read the history file
   */
  private void readFileHistory() {
    File f = new File("history");
    if (f.exists()) {
      try {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        while (line != null) {
          jComboBoxDirJar.addItem(line);
          line = br.readLine();
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Save the File history
   */
  private void saveFileHistory() {
    try {
      File f = new File("history");
      FileWriter fw = new FileWriter(f);
      int count = jComboBoxDirJar.getItemCount();
      for (int i=0; i<count; i++) {
        fw.write(((String)jComboBoxDirJar.getItemAt(i))+"\n");
      }
      fw.close();
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }

  /**
   * close the test application
   */
  private void closeApp() {
    saveFileHistory();
    System.exit(1);
  }

  /**
   * Center the UI on the screen
   */
  private void center () {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension windowSize = this.getSize();
    this.setLocation((screenSize.width - windowSize.width)/2, (screenSize.height - windowSize.height)/2);
  }

  /**
   * Handler for the Search button
   * @param e
   */
  void jButtonSearch_actionPerformed(ActionEvent e) {
    testCaseModel.clear();   //clear the test cases
    testSuiteModel.clear();  //clear the test suites
    Thread t = new Thread() {
      public void run() {
        try {
          File f = new File((String)jComboBoxDirJar.getSelectedItem());
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

            Vector classFiles = Misc.listFilesRecursively(f, new FileFilter() {
              public boolean accept(File pathName) {
                if (pathName.getName().endsWith(".class"))
                  return true;
                return false;
              }});
            for (Enumeration e=classFiles.elements(); e.hasMoreElements(); ) {
              checkClassFile((File)e.nextElement());
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

  /**
   * Parse the output string so as to construct the results table
   * @param s
   */
  private void processResult(String s) {
    //jTextPaneOutput.setText(s);
    //find the beginning of the xml output
    int index = s.indexOf("<?xml");
    int rowNum = 0;
    while (index != -1 ) {
      int endIndex = s.indexOf("</TEST>", index)+7;
      String xmlStr = s.substring(index, endIndex);
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
          ResultStates resultStates = null;
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
            else if (nodeName.equals("EXPECTED_STATE")) {
              if (resultStates == null) resultStates = new ResultStates();
              NodeList nl = n.getChildNodes().item(0).getChildNodes();
              for (int k=0; k<nl.getLength(); k++) {
                resultStates.addExpectedResult(nl.item(k).getChildNodes().item(0).getFirstChild().getNodeValue(), nl.item(k).getChildNodes().item(1).getFirstChild().getNodeValue());
              }
            }
            else if (nodeName.equals("ACTUAL_STATE")) {
              if (resultStates == null) resultStates = new ResultStates();
              NodeList nl = n.getChildNodes().item(0).getChildNodes();
              for (int k=0; k<nl.getLength(); k++) {
                resultStates.addActualResult(nl.item(k).getChildNodes().item(0).getFirstChild().getNodeValue(), nl.item(k).getChildNodes().item(1).getFirstChild().getNodeValue());
              }
            }

          }
          outputTableModel.addRow(results);
          outputTableModel.addCellAncillaryData(rowNum, 5, resultStates);
          rowNum++;
          jTableResults.updateUI();
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      index = s.indexOf("<?xml", endIndex);
    }
  }

  /**
   * Handler for the Run button
   * @param e
   */
  void jButtonRun_actionPerformed(ActionEvent e) {
    jTextPaneOutput.setText("");  //clear the output pane
    MyByteArrayOutputStream baos;
    Launcher launcher = new Launcher();
    launcher.setOutputStyle(Launcher.OUTPUT_STYLE_XML);
    try {
      File f = new File((String)jComboBoxDirJar.getSelectedItem());
      loader.addURL(new URL("file:"+ (String)jComboBoxDirJar.getSelectedItem())); //add the selected jar file to the classpath
      launcher.setTestJarFile(f.getCanonicalPath());  //configure the laucnher witht hte selected jar file
    }
    catch (Exception e2){}
    Object[] testSuites = jListTestSuites.getSelectedValues();

    for (int i=0; i<testSuites.length; i++) {
      try {
        baos = new MyByteArrayOutputStream(256);
        Class clazz = loader.loadClass((String)testSuites[i]);
        Thread t = new Thread(new LauncherRunnable("Executing test suite", launcher, clazz, baos, loader));
        t.start();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    Object[] testCases = jListTestCases.getSelectedValues();
    for (int i=0; i<testCases.length; i++) {
      try {
        baos = new MyByteArrayOutputStream(256);
        Class clazz = loader.loadClass((String)testCases[i]);
        Thread t = new Thread(new LauncherRunnable("Executing test class", launcher, clazz, baos, loader));
        t.start();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  private void configClassLoader()  {

    loader = MyClassLoader.getInstance();
    //String cip = Misc.getEnv("COUGAAR_INSTALL_PATH");
    //loader.addJarsFromDir(cip+"/lib");
    //loader.addJarsFromDir(cip+"/sys");
  }

  /**
   * Handler for the Exit menu item
   * @param e
   */
  void jMenuItemExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }


  /**
   *
   * <p>Title: LaunchRunnable</p>
   * <p>Description: Used to launch the cougaar process via a separate thread</p>
   * <p>Copyright: Copyright (c) 2002</p>
   * <p>Company: InfoEther, LLC</p>
   * @author David Craine
   * @version 1.0
   */
  class LauncherRunnable implements Runnable {
    Launcher launcher;
    Class clazz;
    ByteArrayOutputStream baos;
    String title;
    ClassLoader loader;

    public LauncherRunnable(String title, Launcher launcher, Class clazz, ByteArrayOutputStream baos, ClassLoader loader) {
      this.launcher = launcher;
      this.clazz = clazz;
      this.baos = baos;
      this.title = title;
      this.loader = loader;
    }

    public void run() {
      try {
        RunDialog rd = new RunDialog();
        rd.setTitle(title);
        rd.setMessage(clazz.getName());
        rd.show();
        rd.setCursor(CURSOR_WAIT);
        jTabbedPane1.setSelectedComponent(jScrollPaneOutput);  //switch to the output tab while running cougaar
        System.out.println("class loader: " + clazz.getClassLoader());
        Object obj = clazz.newInstance();
        System.out.println("OBJ:" + obj);
        if (obj instanceof PluginTestCase)
          launcher.runTestCase((PluginTestCase)obj, baos);
        else if (obj instanceof PluginTestSuite)
          launcher.runTestSuite((PluginTestSuite)obj, baos);

        processResult(baos.toString());
        rd.dispose();
        rd.setCursor(CURSOR_DEFAULT);
        jTabbedPane1.setSelectedComponent(jScrollPaneResults); //switch to the results tab after completion
      }
      catch (Exception e ){
        e.printStackTrace();
      }
    }
  }

  /**
   *
   * <p>Title: MyByteArrayOutputStream</p>
   * <p>Description: Used so that we can override the write method to update the output panel in real-time.</p>
   * <p>Copyright: Copyright (c) 2002</p>
   * <p>Company: InfoEther, LLC</p>
   * @author David Craine
   * @version 1.0
   */
  class MyByteArrayOutputStream extends ByteArrayOutputStream {
    public MyByteArrayOutputStream(int size) {
      super(size);
    }

    /**
     * Write the bytes to the output text pane
     * @param bytes
     */
    public void write(byte[] bytes) throws IOException  {
      super.write(bytes);
      jTextPaneOutput.setText(jTextPaneOutput.getText()+new String(bytes));
    }
  }

  void jMenuItemClearHistory_actionPerformed(ActionEvent e) {
    ((DefaultComboBoxModel)jComboBoxDirJar.getModel()).removeAllElements();
    File f = new File("history");
    if (f.exists()) f.delete();
  }

  void jMenuItemResultsPrint_actionPerformed(ActionEvent e) {
    PrintableComponent pc = new PrintableComponent(this.jTableResults);
    try {pc.print();} catch(Exception e1){}
  }

  void jMenuItemOutputPrint_actionPerformed(ActionEvent e) {
    PrintableComponent pc = new PrintableComponent(this.jTextPaneOutput);
    try {pc.print();} catch (Exception e1){}
  }

  void jMenuItemResultsSave_actionPerformed(ActionEvent e) {
    StringBuffer sb = new StringBuffer();
    int colCount = outputTableModel.getColumnCount();
    int rowCount = outputTableModel.getRowCount();
    for (int i=0; i<rowCount; i++) {
      for (int j=0; j<colCount; j++) {
        sb.append(outputTableModel.getValueAt(i,j));
        sb.append('\t');
      }
      sb.append('\n');
    }
    saveToFile(sb.toString());
  }

  private void saveToFile(String s) {
    JFileChooser jfc = new JFileChooser();
    jfc.setDialogType(JFileChooser.SAVE_DIALOG);
    jfc.setDialogTitle("Save Content");
    System.out.println(s);
    int ret = jfc.showSaveDialog(this);
    if (ret == JFileChooser.APPROVE_OPTION) {
      try {
        File f = jfc.getSelectedFile();
        FileWriter fw = new FileWriter(f);
        fw.write(s);
        fw.flush();
        fw.close();
      }
      catch (Exception e1) {

      }
    }
  }

  void jMenuItemOuptutSave_actionPerformed(ActionEvent e) {
    saveToFile(this.jTextPaneOutput.getText());
  }

  /**
   * Parse the data file and add the contents to the streamedDataTableModel
   * @param dataFile
   */
  private void parseDataFile(File dataFile) {
    try {
      FileInputStream fis = new FileInputStream(dataFile);
      ObjectInputStream ois = new ObjectInputStream(fis);
      Object obj = ois.readObject();
      Vector v = (Vector)obj;
      buildTimeDataModel(v);
      buildSourceDataModel(v);
    }
    catch (Exception e) {
      MessageDialog md = new MessageDialog(this, "Error", "Unable to process this file: " + e.toString());
      md.show();
    }
  }

  private void buildTimeDataModel(Vector v) {
    if (v.size() == 0) return;
    long baseTime = ((CapturedPublishAction)v.elementAt(0)).timeStamp;
    for (int i=0; i<v.size(); i++ ) {
      CapturedPublishAction cpa = (CapturedPublishAction)v.elementAt(i);
      timeDataTableModel.addRow(new Object[] {String.valueOf(i), String.valueOf(cpa.timeStamp-baseTime), cpa.publishingSource, String.valueOf(cpa.getActionString()), cpa.publishedObject});
    }
    this.jTableTimeData.updateUI();
  }

  private void buildSourceDataModel(Vector v) {
    Vector cols = new Vector();
    if (v.size() == 0) return;
    long baseTime = ((CapturedPublishAction)v.elementAt(0)).timeStamp;
    cols.addElement(SOURCE_DATA_COL_ID);
    cols.addElement(SOURCE_DATA_COL_TIME);
    //calculate the column names
    for (int i=0; i<v.size(); i++) {
      CapturedPublishAction cpa = (CapturedPublishAction)v.elementAt(i);
      if (!cols.contains(cpa.publishingSource)) {
        cols.addElement(cpa.publishingSource);
      }
    }

    Vector rows = new Vector();  //map of rows keyed by timestamp
    //calculate the rows
    for (int i=0; i<v.size(); i++) {
      CapturedPublishAction cpa = (CapturedPublishAction)v.elementAt(i);
      /*Object[] row = (Object[])rows.get(String.valueOf(cpa.timeStamp-baseTime));
      if (row == null) {
        row = new Object[cols.size()];
        row[0] = String.valueOf(cpa.timeStamp-baseTime);
        for (int j=1; j<cols.size(); j++) { //initialize the row
          row[j] = "";
        }
        rows.put(String.valueOf(cpa.timeStamp-baseTime), row);
      }
      String currentVal = (String)row[cols.indexOf(cpa.publishingSource)];
      String newVal = (currentVal.equals(""))?cpa.getActionString() + " " + cpa.publishedObject:currentVal+"\n"+cpa.getActionString() + " " + cpa.publishedObject;
      row[cols.indexOf(cpa.publishingSource)] = newVal;*/
      Object[] row = new Object[cols.size()];
        for (int j=2; j<cols.size(); j++) { //initialize the row
          row[j] = "";
        }
        row[0] = String.valueOf(i);
        row[1] = String.valueOf(cpa.timeStamp-baseTime);
        row[cols.indexOf(cpa.publishingSource)] = cpa.getActionString() + " " + cpa.publishedObject;
        rows.addElement(row);
    }

    //adjust the column ids to only include the class name and not the whole path
    Vector colIds = new Vector();
    for (Enumeration i = cols.elements(); i.hasMoreElements(); ) {
      String s = (String)i.nextElement();
      int index = s.lastIndexOf(".");
      if (index != -1) {
        s = s.substring(index+1);
      }
      colIds.addElement(s);
    }
    sourceDataTableModel.setColumnIdentifiers(colIds);
    for (Enumeration i = rows.elements(); i.hasMoreElements(); ) {
      sourceDataTableModel.addRow((Object[])i.nextElement());
    }
  }

  void jMenuItemOpenDataFile_actionPerformed(ActionEvent e) {
    JFileChooser jfc = new JFileChooser();
    jfc.setDialogTitle("Select the data file to edit");
    jfc.setDialogType(JFileChooser.OPEN_DIALOG);
    int ret = jfc.showOpenDialog(this);
    if (ret == JFileChooser.APPROVE_OPTION) {
      parseDataFile(jfc.getSelectedFile());
    }
    this.jTabbedPaneMain.setSelectedComponent(this.jPanelObjectStreamEditor);
  }

  void jRadioButtonTimeView_actionPerformed(ActionEvent e) {
    jScrollPaneTimeData.setVisible(true);
    jScrollPaneSourceData.setVisible(false);
  }

  void jRadioButtonSourceView_actionPerformed(ActionEvent e) {
    jScrollPaneTimeData.setVisible(false);
    jScrollPaneSourceData.setVisible(true);
    jTableSourceData.sortAllRowsBy(sourceDataTableModel, 0, true);
  }

  void jMenuItemClearResults_actionPerformed(ActionEvent e) {
    outputTableModel.clear();
    jTableResults.updateUI();
  }
}


/**
 *
 * <p>Title: ResultStates</p>
 * <p>Description: Used to contain the list of results that are returned from the
 * test class.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */
class ResultStates {
  class ResultState{
    String id;
    String result;
    public ResultState(String id, String result) {
      ResultState.this.id = id;
      ResultState.this.result = result;
    }
  }
  Vector expectedResults = new Vector();
  Vector actualResults = new Vector();

  public void addExpectedResult(String id, String result) {
    expectedResults.addElement(new ResultState(id, result));
  }

  public void addActualResult(String id, String result) {
    actualResults.addElement(new ResultState(id, result));
  }
}

/**
 *
 * <p>Title: MyClassLoader</p>
 * <p>Description: Used to update the classpath at runtime so as to be able to load
 * the user-selected PluginTestCase classes</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */
class MyClassLoader extends URLClassLoader {
  private static MyClassLoader me;

  public MyClassLoader(URL[] urls) {
    super(urls);
  }

  public static MyClassLoader getInstance() {
    if (me == null)
      me = new MyClassLoader(new URL[] {});
    return me;
  }

  public static MyClassLoader getInstance(URL[] urls) {
    if (me == null)
      me = new MyClassLoader(urls);
    else {
      if (urls != null) {
        for (int i=0; i<urls.length; i++) {
          me.addURL(urls[i]);
        }
      }
    }
    return me;
  }

  public void addJarsFromDir(String dir) {
    try {
      File f = new File(dir);
      String[] files = f.list(new FilenameFilter() {
                              public boolean accept(File dir, String name) {
                              if (name.toLowerCase().endsWith(".jar")) {
                                return true;
                              }
                              return false;
                              }
      });
      for (int i=0; i<files.length; i++ ) {
        String path = dir+"\\"+files[i];
        this.addURL(new URL("file:" + path));
      }

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addURL(URL url) {
    super.addURL(url);
  }
}


/**
 * This wrapper class encapsulates a Component and allows it to be printed
 * using the Java 1.2 printing API
 */
class PrintableComponent implements Printable {
  // The component to be printed
  Component c;

  /** Create a PrintableComponent wrapper around a Component */
  public PrintableComponent(Component c) { this.c = c; }

  /**
   * This method is not part of the Printable interface.  It is a method
   * that sets up the PrinterJob and initiates the printing.
   */
  public void print() throws PrinterException {
    // Get the PrinterJob object
    PrinterJob job = PrinterJob.getPrinterJob();
    // Get the default page format, then allow the user to modify it
    PageFormat format = job.pageDialog(job.defaultPage());
    // Tell the PrinterJob what to print
    job.setPrintable(this, format);
    // Ask the user to confirm, and then begin the printing process
    if (job.printDialog())
      job.print();
  }

  /**
   * This is the "callback" method that the PrinterJob will invoke.
   * This method is defined by the Printable interface.
   */
  public int print(Graphics g, PageFormat format, int pagenum) {
    // The PrinterJob will keep trying to print pages until we return
    // this value to tell it that it has reached the end
    if (pagenum > 0)
      return Printable.NO_SUCH_PAGE;

    // We're passed a Graphics object, but it can always be cast to Graphics2D
    Graphics2D g2 = (Graphics2D) g;

    // Use the top and left margins specified in the PageFormat Note
    // that the PageFormat methods are poorly named.  They specify
    // margins, not the actual imageable area of the printer.
    g2.translate(format.getImageableX(), format.getImageableY());

    // Tell the Component to draw itself to the printer by passing in
    // the Graphics2D object.  This will not work well if the Component
    // has double-buffering enabled.
    c.paint(g2);

    // Return this constant to tell the PrinterJob that we printed the page
    return Printable.PAGE_EXISTS;
  }
}
