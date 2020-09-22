package org.integratedmodelling.klab.documentation.extensions.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.documentation.extensions.table.Spreadsheet.Dimension;
import org.integratedmodelling.klab.documentation.extensions.table.Spreadsheet.Phase;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

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

		List<Aggregator> aggregator = new ArrayList<>();

		/**
		 * Accumulate the passed value corresponding to the passed locator and view.
		 * 
		 * @param value
		 * @param locator
		 * @param view
		 */
		void accumulate(Object value, ILocator locator, Phase view) {

		}

		public Object get() {
			// TODO Auto-generated method stub
			return null;
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
	private IRuntimeScope scope;
	Set<Integer> activeColumns = new TreeSet<>();
	Set<Integer> activeRows = new TreeSet<>();

	/**
	 * When we get here, the catalogs of rows and columns may not be finalized!
	 * 
	 * @param table
	 * @param rowCatalog
	 * @param colCatalog
	 */
	public Table(Spreadsheet table, List<Dimension> rowCatalog, List<Dimension> colCatalog, IRuntimeScope scope) {
		this.table = table;
		this.rows = rowCatalog;
		this.columns = colCatalog;
		this.scope = scope;
	}

	public void accumulate(Object value, IObservable observable, ILocator locator, Phase phase, int column, int row) {

		/*
		 * at this point the catalogs are stable
		 */
		if (this.cells == null) {
			this.cells = new Cell[columns.size()][rows.size()];
		}

		// when this get called, all filters have matched so we have a value to report
		activeColumns.add(column);
		activeRows.add(row);

		if (!Observations.INSTANCE.isNodata(value)) {

			Cell cell = cells[column][row];
			if (cell == null) {
				cell = new Cell();
				cells[column][row] = cell;
			}

			/*
			 * create aggregator if not there
			 */
			if (cell.aggregator.size() <= phase.getIndex()) {
				cell.aggregator.add(new Aggregator(observable, scope.getMonitor()));
			}

			cell.aggregator.get(phase.getIndex()).add(value, observable, locator);
		}
	}

	public String compile() {

		/*
		 * add separator indices for both rows and columns
		 */
		int rTitles = 0;
		for (Dimension row : rows) {
			if (row.titles != null && row.titles.length > rTitles) {
				rTitles = row.titles.length;
			}
			if (row.separator) {
				activeRows.add(row.index);
			}
		}
		int cTitles = 0;
		for (Dimension column : columns) {
			if (column.titles != null && column.titles.length > cTitles) {
				cTitles = column.titles.length;
			}
			if (column.separator) {
				activeColumns.add(column.index);
			}
		}

		/*
		 * headers
		 */
		for (Integer row : activeRows) {
			Dimension rDesc = rows.get(row);
			for (Integer col : activeColumns) {
			}
		}
		
		/*
		 * data
		 */
		for (Integer row : activeRows) {
			Dimension rDesc = rows.get(row);
			for (Integer col : activeColumns) {
				Cell cell = cells[col][row];
				
			}
		}
		
		StringBuffer ret = new StringBuffer(columns.size() * rows.size() + 256);

		return ret.toString();
	}

}
