package de.embl.cba.metadata.table;

import org.scijava.display.Display;
import org.scijava.plugin.Plugin;
import org.scijava.ui.UserInterface;
import org.scijava.ui.swing.SwingUI;
import org.scijava.ui.viewer.DisplayViewer;
import org.scijava.ui.viewer.DisplayWindow;
import org.scijava.ui.viewer.table.AbstractTableDisplayViewer;

/**
 * InteractiveSwingTableDiplayViewer
 *
 * Code adapted from https://github.com/scijava/scijava-ui-swing/blob/master/src/main/java/org/scijava/ui/swing/viewer/table/SwingTableDisplayViewer.java
 *
 * TODO: cleanup after the next update of scijava-ui-swing
 *
 * @author Robert Haase
 * @author Curtis Rueden
 * @author Barry DeZonia
 * 11 2018
 */
@Plugin(type = DisplayViewer.class, priority = 1.0)
public class InteractiveSwingTableDiplayViewer extends AbstractTableDisplayViewer {

    @Override
    public boolean isCompatible(final UserInterface ui) {
        // TODO: Consider whether to use an interface for Swing UIs instead?
        return ui instanceof SwingUI;
    }

    @Override
    public void view(final DisplayWindow w, final Display<?> d) {
        super.view(w, d);
        setPanel(new InteractiveSwingTableDisplayPanel(getDisplay(), w));
    }
}
