import de.embl.cba.metadata.table.InteractiveGenericTable;
import de.embl.cba.metadata.table.InteractiveTableClickEventListener;
import de.embl.cba.metadata.table.InteractiveTableDisplay;
import de.embl.cba.metadata.table.InteractiveTablePanel;
import ij.IJ;
import net.imagej.ImageJ;
import org.scijava.table.DefaultGenericTable;
import org.scijava.table.DefaultTableDisplay;
import org.scijava.table.Table;
import org.scijava.ui.DefaultUIService;

public class TableTestTischi
{
	public static void main( String[] args )
	{
		final DefaultGenericTable defaultGenericTable = new DefaultGenericTable();

		defaultGenericTable.insertColumn( 0 );
		defaultGenericTable.setColumnHeader( 0, "Header" );
		defaultGenericTable.appendRow();
		defaultGenericTable.set( 0,0, "value" );

		// show non-interactive
		ImageJ imagej = new ImageJ();
		imagej.ui().showUI();

		InteractiveTableDisplay tableDisplay = (InteractiveTableDisplay) imagej.display().createDisplay(defaultGenericTable);
		tableDisplay.addClickEventListener(new InteractiveTableClickEventListener() {
			@Override
			public void execute(Table t, int row, int column) {
				IJ.log("CLICK! table " + t + " row" + row + " column " + column);
			}
		});

		// show interactive
		//final InteractiveTablePanel interactiveTablePanel = new InteractiveTablePanel( defaultGenericTable );
		//interactiveTablePanel.showTable();

	}
}
