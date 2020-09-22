package org.integratedmodelling.klab.documentation.extensions.table;

import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.documentation.extensions.table.Spreadsheet.Dimension;
import org.integratedmodelling.klab.documentation.extensions.table.Spreadsheet.Phase;

/**
 * The container of the computed cells of a table.
 */
public class Table {
	
	/**
	 * A cell may contain a standard aggregator, a comparator, a differentiator or
	 * other accumulator of values.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Cell {

		Aggregator aggregator;

		/**
		 * Accumulate the passed value corresponding to the passed locator and view.
		 * 
		 * @param value
		 * @param locator
		 * @param view
		 */
		void accumulate(Object value, ILocator locator, Phase view) {

		}

		public void reset() {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * The computed part of the table where things happen. Created after reading
	 * rows and columns. Contains a cell per active column/row combination, null if
	 * nothing has gotten in.
	 */
	private Cell[][] cells;
	private Spreadsheet table;
	private List<Dimension> rows;
	private List<Dimension> columns;

	/**
	 * When we get here, the catalogs of rows and columns may not be finalized!
	 * 
	 * @param table
	 * @param rowCatalog
	 * @param colCatalog
	 */
	public Table(Spreadsheet table, List<Dimension> rowCatalog, List<Dimension> colCatalog) {
		this.table = table;
		this.rows = rowCatalog;
		this.columns = colCatalog;
	}

	public void accumulate(Object value, ILocator locator, Phase phase, int column, int row) {

		/*
		 * at this point the catalogs are stable
		 */
		if (this.cells == null) {
			this.cells = new Cell[columns.size()][rows.size()];
		}
		
		// TODO Auto-generated method stub
		if (!Observations.INSTANCE.isNodata(value)) {
			Cell cell = cells[column][row];
			if (cell == null) {
				cell = new Cell();
				cells[column][row] = cell; 
			}
			
			/*
			 * create aggregator if not there
			 */
			
		}
	}
	
}
