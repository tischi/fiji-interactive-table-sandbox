package de.embl.cba.metadata.table;

import net.imagej.ops.Ops;
import org.scijava.display.Display;
import org.scijava.plugin.Plugin;
import org.scijava.table.DefaultTableDisplay;
import org.scijava.table.Table;

import java.util.ArrayList;

/**
 * The InteractiveTableDisplay is a display with a click event listener for tables.
 *
 * @author Robert Haase
 * 11 2018
 */

@Plugin(
        type = Display.class
)
public class InteractiveTableDisplay extends DefaultTableDisplay {
    ArrayList<InteractiveTableClickEventListener> listeners = new ArrayList<InteractiveTableClickEventListener>();

    public void addClickEventListener(InteractiveTableClickEventListener r) {
        listeners.add(r);
    }

    public void removeClickEventListener(InteractiveTableClickEventListener r) {
        listeners.remove(r);
    }

    public void execute(Table t, int row, int column) {
        for (InteractiveTableClickEventListener listener : listeners) {
            listener.execute(t, row, column);
        }
    }
}
