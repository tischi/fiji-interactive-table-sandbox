package de.embl.cba.metadata.table;

import org.scijava.table.Table;
import org.scijava.table.TableDisplay;
import org.scijava.ui.viewer.DisplayPanel;
import org.scijava.ui.viewer.DisplayWindow;
import org.scijava.ui.viewer.table.TableDisplayPanel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * InteractiveSwingTableDisplayPanel
 *
 * Code adapted from https://github.com/scijava/scijava-ui-swing/blob/master/src/main/java/org/scijava/ui/swing/viewer/table/SwingTableDisplayPanel.java
 *
 * TODO: cleanup after the next update of scijava-ui-swing
 *
 * @author Robert Haase
 * @author Curtis Rueden
 * @author Barry DeZonia
 *
 * 11 2018
 */
public class InteractiveSwingTableDisplayPanel extends JScrollPane implements
        TableDisplayPanel {

    // -- instance variables --

    private final DisplayWindow window;
    private final TableDisplay display;
    private final JTable table;
    private final NullTableModel nullModel;

    // -- constructor --

    public InteractiveSwingTableDisplayPanel(final TableDisplay display,
                                  final DisplayWindow window)
    {
        this.display = display;
        this.window = window;
        nullModel = new NullTableModel();
        table = makeTable();
        table.setAutoCreateRowSorter(true);
        setViewportView(table);
        window.setContent(this);

        if (display instanceof InteractiveTableDisplay) {
            table.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ((InteractiveTableDisplay) display).execute(display.get(0), table.getSelectedRow(), table.getSelectedColumn());
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }

    // -- TableDisplayPanel methods --

    @Override
    public TableDisplay getDisplay() {
        return display;
    }

    // -- DisplayPanel methods --

    @Override
    public DisplayWindow getWindow() {
        return window;
    }

    @Override
    public void redoLayout() {
        // Nothing to layout
    }

    @Override
    public void setLabel(final String s) {
        // The label is not shown.
    }

    @Override
    public void redraw() {
        // BDZ - I found a TODO here saying implement me. Not sure if my one liner
        // is correct but it seems to work.
        // one liner:
        // table.update(table.getGraphics());
        // BDZ now try something more intuitive
        // table.repaint(); // nope
        // BDZ this?
        // table.doLayout(); // nope

        // note that our table is not attached to the table model as a listener.
        // we might need to do that. but try to find a simple way to enforce a
        // reread of the table because when it gets in here it's contents are ok

        javax.swing.table.TableModel model = table.getModel();

        // BDZ attempt to force a rebuild. no luck. and also fails the Clear test.
        // Object v = model.getValueAt(0, 0);
        // model.setValueAt(v, 0, 0);

        // BDZ hacky hack way that works
        table.setModel(nullModel);
        table.setModel(model);
        // table.repaint();
    }

    // -- Helper methods --

    private JTable makeTable() {
        return new JTable(new TableModel(getTable()));
    }

    private Table<?, ?> getTable() {
        return display.size() == 0 ? null : display.get(0);
    }

    // -- Helper classes --

    public static class NullTableModel extends AbstractTableModel {

        @Override
        public int getColumnCount() {
            return 0;
        }

        @Override
        public int getRowCount() {
            return 0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return null;
        }

    }

    /** A Swing {@link TableModel} backed by an ImageJ {@link Table}. */
    public static class TableModel extends AbstractTableModel {

        private final Table<?, ?> tab;

        public TableModel(final Table<?, ?> table) {
            this.tab = table;
        }

        @Override
        public String getColumnName(final int col) {
            if (col == 0) return "";
            return tab.getColumnHeader(col - 1);
        }

        @Override
        public int getRowCount() {
            return tab.getRowCount();
        }

        @Override
        public int getColumnCount() {
            return tab.getColumnCount() + 1; // +1 for row header column
        }

        @Override
        public Object getValueAt(final int row, final int col) {
            if (row < 0 || row >= getRowCount()) return null;
            if (col < 0 || col >= getColumnCount()) return null;

            if (col == 0) {
                // get row header, or row number if none
                // NB: Assumes the JTable can handle Strings equally as well as the
                // underlying type T of the Table.
                final String header = tab.getRowHeader(row);
                if (header != null) return header;
                return "" + (row + 1);
            }

            // get the underlying table value
            // NB: The column is offset by one to accommodate the row header/number.
            return tab.get(col - 1, row);
        }

        @Override
        public void setValueAt(final Object value, final int row, final int col) {
            if (row < 0 || row >= getRowCount()) return;
            if (col < 0 || col >= getColumnCount()) return;
            if (col == 0) {
                // set row header
                tab.setRowHeader(row, value == null ? null : value.toString());
                return;
            }
            set(tab, col - 1, row, value);
            fireTableCellUpdated(row, col);
        }

        // -- Helper methods --

        private <T> void set(final Table<?, T> table,
                             final int col, final int row, final Object value)
        {
            @SuppressWarnings("unchecked")
            final T typedValue = (T) value;
            table.set(col, row, typedValue);
        }

    }
}
