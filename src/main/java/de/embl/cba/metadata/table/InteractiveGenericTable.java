package de.embl.cba.metadata.table;

import org.scijava.table.AbstractTable;
import org.scijava.table.Column;
import org.scijava.table.GenericColumn;
import org.scijava.table.GenericTable;

public class InteractiveGenericTable extends AbstractTable<Column<? extends Object>, Object> implements GenericTable
{

	/** Creates an empty table. */
	public InteractiveGenericTable() {
		super();
	}

	/** Creates a table with the given row and column dimensions. */
	public InteractiveGenericTable( final int columnCount, final int rowCount) {
		super(columnCount, rowCount);
	}

	// -- Internal methods --

	@Override
	protected GenericColumn createColumn( final String header) {
		return new GenericColumn(header);
	}

}
