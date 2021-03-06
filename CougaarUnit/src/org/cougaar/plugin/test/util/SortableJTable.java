package org.cougaar.plugin.test.util;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.util.Comparator;
import java.util.Vector;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * <p>Title: CougaarUnit</p>
 * <p>Description: Cougaar Unit Test Infratstructure</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther, LLC</p>
 * @author David Craine
 * @version 1.0
 */

public class SortableJTable extends JTable {


  public SortableJTable() {
    super();
    init();
  }

  public SortableJTable(TableModel tm) {
    super(tm);
    init();
  }

  private void init() {
    getTableHeader().addMouseListener(new ColumnHeaderListener());
    //this.setDefaultRenderer(Object.class, new JListRenderer());
 }

  public void sortAllRowsBy(DefaultTableModel model, int colIndex, boolean ascending) {
    Vector data = model.getDataVector();
    Collections.sort(data, new ColumnSorter(colIndex, ascending));
    model.fireTableStructureChanged();
    try {this.updateUI();} catch (Exception e){}
  }


  public static void main(String[] args) {
    JFrame f = new JFrame("test");
    f.getContentPane().setLayout(new BorderLayout());
    JScrollPane jsc = new JScrollPane();
    f.getContentPane().add(jsc, BorderLayout.CENTER);
    SortableJTable st = new SortableJTable();
    jsc.getViewport().add(st);

    DefaultTableModel dft = (DefaultTableModel)st.getModel();
    dft.addColumn("1");
    dft.addColumn("2");
    dft.addColumn("3");
    dft.addRow(new String[] {"one", "two", "three"});
    dft.addRow(new String[] {"four", "five", "six"});
    dft.addRow(new String[] {"seven", "eight", "nine"});
    f.pack();
    f.setSize(500,500);
    st.updateUI();

    f.show();
  }


  class ColumnHeaderListener extends MouseAdapter {
    public void mouseClicked(MouseEvent evt) {
      JTable table = ((JTableHeader)evt.getSource()).getTable();
      TableColumnModel colModel = table.getColumnModel();

      // The index of the column whose header was clicked
      int vColIndex = colModel.getColumnIndexAtX(evt.getX());

      // Return if not clicked on any column header
      if (vColIndex == -1) {
        return;
      }
      SortableJTable.this.sortAllRowsBy((DefaultTableModel)table.getModel(), vColIndex, true);
    }
  }

  // This comparator is used to sort vectors of data
  class ColumnSorter implements Comparator {
    int colIndex;
    boolean ascending;
    ColumnSorter(int colIndex, boolean ascending) {
      this.colIndex = colIndex;
      this.ascending = ascending;
    }
    public int compare(Object a, Object b) {
      Vector v1 = (Vector)a;
      Vector v2 = (Vector)b;
      Object o1 = v1.get(colIndex);
      Object o2 = v2.get(colIndex);

      // Treat empty strains like nulls
      if (o1 instanceof String && ((String)o1).length() == 0) {
        o1 = null;
      }
      if (o2 instanceof String && ((String)o2).length() == 0) {
        o2 = null;
      }

      // Sort nulls so they appear last, regardless
      // of sort order
      if (o1 == null && o2 == null) {
        return 0;
      } else if (o1 == null) {
        return 1;
      } else if (o2 == null) {
        return -1;
      }
      else if ((o1 instanceof String )&& (o2 instanceof String)) {
        try {
          long i1 = Long.parseLong((String)o1);
          long i2 = Long.parseLong((String)o2);
          if (ascending)
            return (int)(i1-i2);
          else
            return (int)(i2-i1);
        }
        catch(NumberFormatException nfe){}
      }

      if (o1 instanceof Comparable) {
        if (ascending) {
          return ((Comparable)o1).compareTo(o2);
        }
        else {
          return ((Comparable)o2).compareTo(o1);
        }
      }
      else {
        if (ascending) {
          return o1.toString().compareTo(o2.toString());
        }
        else {
          return o2.toString().compareTo(o1.toString());
        }
      }
    }
  }

  public class JListRenderer implements TableCellRenderer {

    public JListRenderer() {
    }

    public Component getTableCellRendererComponent(JTable jTable,
        Object obj, boolean isSelected, boolean hasFocus, int row,
        int column) {
      Vector v = new Vector();
      Component c = null;
      StringTokenizer st = new StringTokenizer((String)obj, "\n");
      int lines = st.countTokens();
      //if there are multiple lines then we want to return a JList component
      if (lines > 1) {
        while (st.hasMoreTokens()) {
          v.addElement(st.nextToken());
        }
        c = new JList(v);
        ((JList)c).setVisibleRowCount(lines);

        int height_wanted = (int)c.getPreferredSize().getHeight();
        if (height_wanted != jTable.getRowHeight(row) && height_wanted > 0)
          jTable.setRowHeight(row, height_wanted);
      }
      else {//otherwise let's just return a text field
        c = new JTextArea((String)obj);
      }

      return c;
    }
  }
}