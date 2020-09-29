package org.integratedmodelling.klab.documentation.extensions.table;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.ComputationType;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Dimension;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.DimensionType;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Phase;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Style;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.TemplateUtils;

/**
 * A computed table based on a table view model.
 */
public class TableArtifact extends Artifact implements IKnowledgeView {

	/**
	 * A cell may contain a standard aggregator, a comparator, a differentiator or
	 * other accumulator of values.
	 * 
	 * @author Ferd
	 *
	 */
	public static class Cell {

		// if we see only one phase, we keep the value here
		Aggregator aggregator = null;

		// otherwise we leave that null and set the values in this hash; the only way to
		// get values will be to use expressions that reference them explicitly
		Map<String, Aggregator> phaseHash = null;

		// keep the last seen phase here so that we know when we see a different one.
		String phase = null;

		/*
		 * the last value added to the aggregator.
		 */
		public Object currentValue;

		/**
		 * The final value after aggregation. Precomputed so we can do further in-table
		 * aggregations within a single loop.
		 */
		public Object computedValue;

		public ComputationType computationType;

		// cells that aggregate other aggregated cells must be computed last, so we add
		// 1 every time
		// the cell collects an aggregation. If aggregationLevel > 1, the
		// computationType is guaranteed to be the same in row and column, so
		// aggregating
		// is safe.
		public int aggregationLevel = 0;

		public Dimension column;

		public Dimension row;

		public Object get() {

			if (aggregator != null) {
				return aggregator.aggregate();
			}

			return null;
		}

		@Override
		public String toString() {
			return "<[" + (row == null ? "*" : ("" + row.index)) + "," + (column == null ? "*" : ("" + column.index)) + "]: "
					+ computedValue + (row.computation == null ? "" : row.computation) + ">";
			
		}
	}

	/**
	 * The computed part of the table where things happen. Created after reading
	 * rows and columns. Contains a cell per active column/row combination, null if
	 * nothing has gotten in.
	 */
	private Cell[][] cells;
	private TableCompiler table;
	private List<Dimension> rows;
	private List<Dimension> columns;
	private Map<String, Dimension> dimensionCatalog = new HashMap<>();
	private IRuntimeScope scope;
	Set<Integer> activeColumns = new HashSet<>();
	Set<Integer> activeRows = new HashSet<>();
	private String compiledView;
	private String id = "v" + NameGenerator.shortUUID();

	/**
	 * When we get here, the catalogs of rows and columns may not be finalized!
	 * 
	 * @param table
	 * @param rowCatalog
	 * @param colCatalog
	 */
	public TableArtifact(TableCompiler table, List<Dimension> rowCatalog, List<Dimension> colCatalog,
			IRuntimeScope scope) {
		this.table = table;
		this.rows = rowCatalog;
		this.columns = colCatalog;
		this.scope = scope;
		/*
		 * make a single catalog so we have the chance to catch ambiguous naming
		 */
		for (Dimension dimension : CollectionUtils.join(rowCatalog, colCatalog)) {
			if (dimensionCatalog.containsKey(dimension.id)) {
				throw new KlabValidationException(
						"table: ambiguous identifier " + dimension.id + " identifies both a row and a column");
			}
			dimensionCatalog.put(dimension.id, dimension);
		}
	}

	/**
	 * Accumulate a value to be aggregated later according to semantics. If we see
	 * two different phases, remove the aggregation value (cell will be nodata) but
	 * put the aggregators in a hash so that self@phase can be used in expressions.
	 * 
	 * @param value
	 * @param observable
	 * @param locator
	 * @param phase
	 * @param column
	 * @param row
	 */
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

			if (cell.phaseHash == null) {
				if (cell.phase == null) {
					cell.phase = phase.getKey();
				} else if (!cell.phase.equals(phase.getKey())) {
					// use the hash from now on. Every other use produces nodata.
					cell.phaseHash = new HashMap<>();
					cell.phaseHash.put(cell.phase, cell.aggregator);
					cell.aggregator = null;
					cell.phase = null;
				}
			}

			if (cell.phaseHash != null) {

				if (!cell.phaseHash.containsKey(phase.getKey())) {
					cell.phaseHash.put(phase.getKey(), new Aggregator(observable, scope.getMonitor()));
				}

				cell.phaseHash.get(phase.getKey()).add(value);

				/*
				 * this is held to feed references in computations that happen at each cycle,
				 * within the same phase
				 */
				cell.currentValue = value;

			} else {

				/*
				 * this is held to feed references in computations that happen at each cycle
				 */
				cell.currentValue = value;

				/*
				 * create aggregator if not there
				 */
				if (cell.aggregator == null) {
					cell.aggregator = new Aggregator(observable, scope.getMonitor());
				}
				cell.aggregator.add(value, observable, locator);
			}

		} else if (cells[column][row] != null) {

			// just set the nodata value as the latest if we have previous ones
			cells[column][row].currentValue = value;

		}
	}

	@Override
	public String getCompiledView(String mediaType) {

		if (this.compiledView == null) {

			List<Cell> aggregatedCells = new ArrayList<>();

			/*
			 * precompute all cells with aggregators and not those that aggregate them; the
			 * latter are put away and deferred for computing in order of level, so that
			 * aggregations of aggregations are computed last. Put row and column in the
			 * cell in this pass.
			 */
			for (Dimension column : getActiveColumns()) {
				for (Dimension row : getActiveRows()) {
					Cell cell = cells[column.index][row.index];
					if (cell != null) {
						cell.row = row;
						cell.column = column;
						if (cell.computationType == null && cell.aggregator != null) {
							cell.computedValue = cell.get();
						} else if (cell.computationType != null) {
							// those with summarizing computations go first
							if (cell.computationType == ComputationType.Summarize) {
								cell.aggregationLevel = 0;
							} else {
								cell.aggregationLevel++;
							}
							aggregatedCells.add(cell);
						}
					}
				}
			}

			/*
			 * compute all aggregating cells in order of level
			 */
			Collections.sort(aggregatedCells, new Comparator<Cell>() {
				@Override
				public int compare(Cell o1, Cell o2) {
					return Integer.compare(o1.aggregationLevel, o2.aggregationLevel);
				}
			});

			for (Cell cell : aggregatedCells) {
				cell.computedValue = aggregateData(cell);
			}

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
				if (row.hidden) {
					continue;
				}
				if (row.titles != null && row.titles.length > rTitles) {
					rTitles = row.titles.length;
				}
				if (row.separator) {
					activeRows.add(row.index);
				}
			}
			int cTitles = 0;
			for (Dimension column : columns) {
				if (column.hidden) {
					continue;
				}
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
					for (Dimension cDesc : getActiveColumns()) {
						if (cDesc.hidden) {
							continue;
						}
						/*
						 * write the ct-th title, using the array starting counting from the bottom
						 */
						ret.append("      <th" + getStyle(cDesc.style) + ">"
								+ Escape.forHTML(getHeader(cDesc, ct, cTitles, scope)) + "</th>\n");
					}
					ret.append("    </tr>\n");
				}
				ret.append("  </thead>\n");
			}

			/*
			 * data and row titles
			 */
			ret.append("  <tbody>\n");
			for (Dimension rDesc : getActiveRows()) {
				if (rDesc.hidden) {
					continue;
				}
				ret.append("    <tr>\n");
				for (int i = 0; i < rTitles; i++) {
					ret.append("      <th scope=\"row\"" + getStyle(rDesc.style) + ">"
							+ Escape.forHTML(getHeader(rDesc, i, rTitles, scope)) + "</th>\n");
				}
				for (Dimension cDesc : getActiveColumns()) {
					if (cDesc.hidden) {
						continue;
					}
					Cell cell = cells[cDesc.index][rDesc.index];
					ret.append("      <td" + getStyle(cell) + ">" + getData(cell) + "</td>\n");
				}
				ret.append("    </tr>\n");
			}
			ret.append("  <tbody>\n");

			ret.append("</table>");

			this.compiledView = ret.toString();
		}
		return this.compiledView;
	}

	/**
	 * Return the active rows in order of definition.
	 * 
	 * @return
	 */
	private List<Dimension> getActiveRows() {
		List<Dimension> ret = new ArrayList<>();
		for (String id : table.getRowOrder()) {
			Dimension dim = dimensionCatalog.get(id);
			if (activeRows.contains(dim.index)) {
				ret.add(dim);
			}
		}
		return ret;
	}

	/**
	 * Return the active columns in order of definition.
	 * 
	 * @return
	 */
	private List<Dimension> getActiveColumns() {
		List<Dimension> ret = new ArrayList<>();
		for (String id : table.getColumnOrder()) {
			Dimension dim = dimensionCatalog.get(id);
			if (activeColumns.contains(dim.index)) {
				ret.add(dim);
			}
		}
		return ret;
	}

	private String getStyle(Cell cell) {
		if (cell == null) {
			return "";
		}
		Set<Style> style = cell.column.style;
		style.addAll(cell.row.style);
		return getStyle(style);
	}

	private String getStyle(Set<Style> style) {

		if (!style.isEmpty()) {
			String ret = "\"";
			for (Style s : style) {
				switch (s) {
				case BOLD:
					ret += (ret.length() == 1 ? "" : " ") + "kv-bold";
					break;
				case CENTER:
					ret += (ret.length() == 1 ? "" : " ") + "kv-align-center";
					break;
				case ITALIC:
					ret += (ret.length() == 1 ? "" : " ") + "kv-italic";
					break;
				case LEFT:
					ret += (ret.length() == 1 ? "" : " ") + "kv-align-left";
					break;
				case RIGHT:
					ret += (ret.length() == 1 ? "" : " ") + "kv-align-right";
					break;
				case BG_HIGHLIGHT:
					ret += (ret.length() == 1 ? "" : " ") + "kv-bg-highlight";
					break;
				case FG_HIGHLIGHT:
					ret += (ret.length() == 1 ? "" : " ") + "kv-fg-highlight";
					break;
				default:
					break;

				}
			}

			return " class=" + ret + "\"";
		}
		return "";
	}

	private Object aggregateData(Cell cell) {

		Object ret = null;

		/*
		 * find out who is asking me to aggregate, the row or the column; if both,
		 * choose the row, scanned in the inner loop.
		 */
		DimensionType aggregatingDimension = (cell.row.computationType != null
				&& cell.row.computationType.isAggregation()) ? DimensionType.ROW : DimensionType.COLUMN;
		Dimension dimension = aggregatingDimension == DimensionType.ROW ? cell.row : cell.column;

		if (dimension.computationType == ComputationType.Summarize) {

			/*
			 * must compute an expression using the mentioned row or column values in the
			 * correspondent dimension
			 */
			IParameters<String> parameters = Parameters.create();
			for (String symbol : dimension.symbols) {
				if (dimensionCatalog.containsKey(symbol)) {
					int row = aggregatingDimension == DimensionType.ROW ? dimensionCatalog.get(symbol).index
							: cell.row.index;
					int col = aggregatingDimension == DimensionType.ROW ? cell.column.index
							: dimensionCatalog.get(symbol).index;
					Cell target = cells[col][row];
					parameters.put(symbol,
							target == null ? 0 : (target.computedValue == null ? 0 : target.computedValue));
				}
			}

			ret = dimension.getExpression(scope).eval(parameters, scope);

		} else {

			/*
			 * Create an aggregator w/o semantics according to the computation type, which
			 * is guaranteed consistent.
			 */
			Aggregator aggregator = new Aggregator(cell.computationType.getAggregation());

			/*
			 * scan the OTHER dimension and add the computedValue of all cells that have
			 * aggregators
			 */
			if (aggregatingDimension == DimensionType.COLUMN) {
				for (int i = 0; i < columns.size(); i++) {
					Cell target = cells[i][cell.row.index];
					if (target != null
							&& (cell.computationType == null || cell.computationType == cell.column.computationType)) {
						aggregator.add(target.computedValue == null ? 0 : target.computedValue);
					}
				}
			} else {
				for (int i = 0; i < rows.size(); i++) {
					Cell target = cells[cell.column.index][i];
					if (target != null
							&& (cell.computationType == null || cell.computationType == cell.row.computationType)) {
						aggregator.add(target.computedValue == null ? 0 : target.computedValue);
					}
				}
			}

			ret = aggregator.aggregate();

		}

		return ret;
	}

	@Override
	public boolean export(File file, String mediaType) {
		return false;
	}

	private String getData(Cell cell) {

		if (cell == null) {
			return "";
		}

		Object ret = cell.computedValue;
		if (Observations.INSTANCE.isNodata(ret)) {
			return "";
		}
		if (ret instanceof Number) {
			// TODO harvest format specs from row, then col
			ret = NumberFormat.getNumberInstance().format((Number) ret);
		}
		return ret.toString();
	}

	private String getHeader(Dimension dimension, int currentLevelIndex, int totalLevels, IRuntimeScope scope) {
		// choose the title according to the level based on what was defined in the
		// dimension. It will be
		// one of the titles but we may have less than the max as other dims. Titles are
		// more to less specific.
		String title = "";
		if (dimension.titles != null && dimension.titles.length > currentLevelIndex) {
			title = dimension.titles[currentLevelIndex];
		}
		return TemplateUtils.expandMatches(title, this.table.getTemplateVars(dimension, scope)).get(0);
	}

	@Override
	public String getViewClass() {
		return "table";
	}

	@Override
	public String getName() {
		return this.table.getName();
	}

	@Override
	public String getTitle() {
		return this.table.getTitle();
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.scalar();
	}

	@Override
	public Type getType() {
		return Type.VOID;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public IArtifact getGroupMember(int n) {
		return n == 0 ? this : null;
	}

	@Override
	public String getLabel() {
		return this.table.getLabel();
	}

	@Override
	public Collection<ExportFormat> getExportFormats() {
		List<ExportFormat> ret = new ArrayList<>();
		ret.add(new ExportFormat("Excel worksheet", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"xlsx", "xlsx"));
		return ret;
	}

	/**
	 * Tags the cell as an aggregator to be matched to cells in the correspondent
	 * dimension that have aggregators and computed at the second pass.
	 * 
	 * @param val
	 * @param rowComputationType
	 * @param phase
	 * @param index
	 * @param index2
	 */
	public void aggregate(ComputationType rowComputationType, Phase phase, int column, int row, int aggregationLevel) {

		/*
		 * at this point the catalogs are stable
		 */
		if (this.cells == null) {
			this.cells = new Cell[columns.size()][rows.size()];
		}

		// when this get called, all filters have matched so we have a value to report
		activeColumns.add(column);
		activeRows.add(row);

		Cell cell = cells[column][row];
		if (cell == null) {
			cell = new Cell();
			cells[column][row] = cell;
		}

		cell.computationType = rowComputationType;
		cell.aggregationLevel = aggregationLevel;

	}

	public Object getCurrentValue(int column, int row, Object rowRef, boolean isValue) {

		if (this.cells == null) {
			return null;
		}

		Object ret = null;

		if ("value".equals(rowRef)) {

			Cell cell = this.cells[column][row];

			if (cell == null) {
				return null;
			}

			if (cell.phaseHash != null && !isValue) {
				ret = new HashMap<String, Object>();
				for (Entry<String, Aggregator> entry : cell.phaseHash.entrySet()) {
					((Map<String, Object>) ret).put(entry.getKey(), entry.getValue().aggregate());
				}
			} else if (isValue) {
				ret = cell.currentValue;
			} else if (cell.aggregator != null) {
				ret = cell.aggregator.aggregate();
			}
		} else if (dimensionCatalog.containsKey(rowRef)) {

			Dimension dim = dimensionCatalog.get(rowRef);
			if (dim.dimensionType == DimensionType.ROW) {
				ret = getCurrentValue(column, dim.index, "value", isValue);
			} else {
				ret = getCurrentValue(dim.index, row, "value", isValue);
			}
			if (Observations.INSTANCE.isNodata(ret)) {
				ret = dim.getDefault();
			}

		}

		return ret;
	}

	public void setValue(Object value, int column, int row) {
		if (this.cells == null) {
			return;
		}

		Cell cell = this.cells[column][row];
		if (cell != null) {
			cell.computedValue = value;
		}
	}

}
