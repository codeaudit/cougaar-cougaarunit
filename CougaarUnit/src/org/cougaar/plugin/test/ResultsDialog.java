package org.cougaar.plugin.test;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;
import java.util.Enumeration;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ResultsDialog extends JDialog {
  private JPanel panel1 = new JPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private JSplitPane jSplitPaneResults = new JSplitPane();
  private JScrollPane jScrollPaneExpectedResults = new JScrollPane();
  private JScrollPane jScrollPaneActualResults = new JScrollPane();
  private JTable jTableExpectedResults = new JTable();
  private JTable jTableActualResults = new JTable();
  private Border border1;
  private TitledBorder titledBorder1;
  private Border border2;
  private TitledBorder titledBorder2;
  private Border border3;
  private TitledBorder titledBorder3;
  private Border border4;
  private TitledBorder titledBorder4;
  private ResultTableModel expectedResultsModel = new ResultTableModel();
  private ResultTableModel actualResultsModel = new ResultTableModel();

  class ResultTableModel extends AbstractTableModel {
      static final String COLUMN_ACTION_ID = "ACTION ID";
      static final String COLUMN_RESULT = "RESULT";

      protected final String[] columnNames = {COLUMN_ACTION_ID, COLUMN_RESULT};
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

  public ResultsDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      init2();
      pack();
      setSize(300,300);
      center();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setResultData(ResultStates resultStates) {
    if (resultStates == null || resultStates.expectedResults == null || resultStates.actualResults == null) return;
    for (Enumeration enum = resultStates.expectedResults.elements(); enum.hasMoreElements(); ) {
      ResultStates.ResultState state = (ResultStates.ResultState)enum.nextElement();
      Object[] row = new Object[2];
      System.out.println("state.id: "+ state.id);
      System.out.println("state.result: "+ state.result);
      row[0] = state.id;
      row[1] = state.result;
      this.expectedResultsModel.addRow(row);
    }
    for (Enumeration enum = resultStates.actualResults.elements(); enum.hasMoreElements(); ) {
      ResultStates.ResultState state = (ResultStates.ResultState)enum.nextElement();
      Object[] row = new Object[2];
      row[0] = state.id;
      row[1] = state.result;
      this.actualResultsModel.addRow(row);
    }
    this.jTableActualResults.updateUI();
    this.jTableExpectedResults.updateUI();
  }

  public ResultsDialog() {
    this(null, "", false);
  }

  private void init2() {
    jTableExpectedResults.setModel(expectedResultsModel);
    jTableExpectedResults.getColumn(ResultTableModel.COLUMN_ACTION_ID).setPreferredWidth(75);
    jTableExpectedResults.getColumn(ResultTableModel.COLUMN_ACTION_ID).setMaxWidth(75);
    jTableExpectedResults.getColumn(ResultTableModel.COLUMN_RESULT).setPreferredWidth(150);
    jTableActualResults.setModel(actualResultsModel);
    jTableActualResults.getColumn(ResultTableModel.COLUMN_ACTION_ID).setPreferredWidth(75);
    jTableActualResults.getColumn(ResultTableModel.COLUMN_ACTION_ID).setMaxWidth(75);
    jTableActualResults.getColumn(ResultTableModel.COLUMN_RESULT).setPreferredWidth(150);

  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(115, 114, 105),new Color(165, 163, 151));
    titledBorder1 = new TitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(115, 114, 105),new Color(165, 163, 151)),"Expected Publish Actions");
    border2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(115, 114, 105),new Color(165, 163, 151));
    titledBorder2 = new TitledBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(115, 114, 105),new Color(165, 163, 151)),"Actual Publish Actions");
    border3 = BorderFactory.createEmptyBorder();
    titledBorder3 = new TitledBorder(border3,"Publish Actions");
    border4 = BorderFactory.createEmptyBorder();
    titledBorder4 = new TitledBorder(border4,"Publish Actions");
    panel1.setLayout(borderLayout1);
    jSplitPaneResults.setOrientation(JSplitPane.VERTICAL_SPLIT);
    jScrollPaneExpectedResults.setBorder(titledBorder1);
    jScrollPaneActualResults.setBorder(titledBorder2);
    getContentPane().add(panel1);
    panel1.add(jSplitPaneResults, BorderLayout.CENTER);
    jSplitPaneResults.add(jScrollPaneExpectedResults, JSplitPane.TOP);
    jScrollPaneExpectedResults.getViewport().add(jTableExpectedResults, null);
    jSplitPaneResults.add(jScrollPaneActualResults, JSplitPane.BOTTOM);
    jScrollPaneActualResults.getViewport().add(jTableActualResults, null);
    jSplitPaneResults.setDividerLocation(130);
  }

  private void center () {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension windowSize = this.getSize();
    this.setLocation((screenSize.width - windowSize.width)/2, (screenSize.height - windowSize.height)/2);
  }

}