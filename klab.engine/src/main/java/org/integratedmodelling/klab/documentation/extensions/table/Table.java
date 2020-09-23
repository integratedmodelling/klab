package org.integratedmodelling.klab.documentation.extensions.table;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.documentation.extensions.table.Spreadsheet.Dimension;
import org.integratedmodelling.klab.documentation.extensions.table.Spreadsheet.Phase;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.TemplateUtils;

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

		public Object get() {

			if (aggregator.size() > 1) {
				// boh needs more work - a strategy to aggregate the aggregated. Can this happen
				// given
				// that we have a cartesian product of filters?
			} else if (aggregator.size() == 1) {
				return aggregator.get(0).aggregate();
			}

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

		StringBuffer ret = new StringBuffer(columns.size() * rows.size() + 256);

		ret.append("<table>\n");

		if (this.table.getTitle() != null) {
			ret.append("  <caption>" + Escape.forHTML(this.table.getTitle()) + "</caption>\n");
		}
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
		if (rTitles + cTitles > 0) {
			ret.append("  <thead>\n");
			/*
			 * TODO GROUPS!
			 */
			for (int ct = 0; ct < cTitles; ct++) {
				ret.append("    <tr>\n");
				for (int rt = 0; rt < rTitles; rt++) {
					// empty cells to leave space for row headers later
					ret.append("      <th></th>\n");
				}
				for (Integer col : activeColumns) {
					Dimension cDesc = columns.get(col);
					/*
					 * write the ct-th title, using the array starting counting from the bottom
					 */
					ret.append("      <th>" + Escape.forHTML(getHeader(cDesc, ct, cTitles)) + "</th>\n");
				}
				ret.append("    </tr>\n");
			}
			ret.append("  </thead>\n");
		}

		/*
		 * data and row titles
		 */
		ret.append("  <tbody>\n");
		for (Integer row : activeRows) {
			Dimension rDesc = rows.get(row);
			ret.append("    <tr>\n");
			for (int i = 0; i < rTitles; i++) {
				ret.append("      <th scope=\"row\">" + Escape.forHTML(getHeader(rDesc, i, rTitles)) + "</th>\n");
			}
			for (Integer col : activeColumns) {
				Dimension cDesc = columns.get(col);
				Cell cell = cells[col][row];
				ret.append("      <td>" + getData(cell, rDesc, cDesc) + "</td>\n");
			}
			ret.append("    </tr>\n");
		}
		ret.append("  <tbody>\n");

		ret.append("</table>");

		return ret.toString();
	}

	private String getData(Cell cell, Dimension rowDesc, Dimension colDesc) {
		if (cell == null) {
			return "";
		}
		Object ret = cell.get();
		if (Observations.INSTANCE.isNodata(ret)) {
			return "";
		}
		if (ret instanceof Number) {
			// TODO harvest format specs from row, then col
			ret = NumberFormat.getNumberInstance().format((Number) ret);
		}
		return ret.toString();
	}

	private String getHeader(Dimension dimension, int currentLevelIndex, int totalLevels) {
		// choose the title according to the level based on what was defined in the
		// dimension. It will be
		// one of the titles but we may have less than the max as other dims. Titles are
		// more to less specific.
		String title = "";
		if (dimension.titles != null && dimension.titles.length > currentLevelIndex) {
			title = dimension.titles[currentLevelIndex];
		}
		return TemplateUtils.expandMatches(title, this.table.getTemplateVars(dimension)).get(0);
	}

}
