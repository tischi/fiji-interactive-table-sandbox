import de.embl.cba.metadata.table.InteractiveGenericTable;
import de.embl.cba.metadata.table.InteractiveTablePanel;
import net.imagej.ImageJ;
import net.imagej.table.DefaultGenericTable;

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
		imagej.ui().show( defaultGenericTable );

		// show interactive
		final InteractiveTablePanel interactiveTablePanel = new InteractiveTablePanel( defaultGenericTable );
		interactiveTablePanel.showTable();

	}
}
