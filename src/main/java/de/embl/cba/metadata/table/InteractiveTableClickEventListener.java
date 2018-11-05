package de.embl.cba.metadata.table;

import org.scijava.table.Table;

/**
 * The InteractiveTableClickEventListener represents the event action being executed when a table was clicked.
 *
 * @author Robert Haase
 * 11 2018
 */
public interface InteractiveTableClickEventListener {
    void execute(Table t, int row, int column);
}
