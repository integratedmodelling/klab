package org.integratedmodelling.klab.documentation.extensions.table;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.documentation.views.IDocumentationView;
import org.integratedmodelling.klab.api.documentation.views.ITableView;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.DocumentationNode.Table.Column;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;

public class SimpleTableArtifact extends AbstractTableArtifact {

	String emptyValue = "0";
	String noDataValue = "0";

	static class Dimension {
		public Dimension(String string, Object[] options) {
			this.id = string;
			// TODO handle options
		}

		boolean header;
		String id;
		String label;
		Set<IKnowledgeView.Style> style = EnumSet.noneOf(IKnowledgeView.Style.class);
	}

	static class Cell {
		Object value;
		Set<IKnowledgeView.Style> style = EnumSet.noneOf(IKnowledgeView.Style.class);
	}

	public static class TableBuilder implements IKnowledgeView.Builder {

		IRuntimeScope scope;
		TableCompiler compiler;
		Map<Object, Dimension> crows = new LinkedHashMap<>();
		Map<Object, Dimension> ccols = new LinkedHashMap<>();
		Map<Pair<String, String>, Cell> cells = new HashMap<>();
		boolean colHeaders = false;
		boolean rowHeaders = false;
		boolean rowTotals = false;
		boolean colTotals = false;
		String emptyValue = "0";
		String noDataValue = "0";
		private int unnamedDimensions;

		TableBuilder(TableCompiler compiler, IRuntimeScope scope) {
			this.scope = scope;
			this.compiler = compiler;
		}

		private String getDimension(boolean isRow, Object... options) {

			Object classifier = null;
			Object header = null;
			Set<Style> styles = EnumSet.noneOf(Style.class);
			if (options != null) {
				for (int i = 0; i < options.length; i++) {
				    
					if (options[i] instanceof Attribute) {
						switch ((Attribute) options[i]) {
						case HEADER:
							header = options[++i];
							break;
						case HEADER_0:
							// TODO record nesting
							header = options[++i];
							break;
						case HEADER_1:
							// TODO record nesting
							header = "  " + options[++i];
							break;
						case HEADER_2:
							// TODO record nesting
							header = "    " + options[++i];
							break;
						case HEADER_3:
							// TODO record nesting
							header = "      " + options[++i];
							break;
						default:
							break;
						}
					} else if (options[i] instanceof Style) {
						styles.add((Style) options[i]);
					} else {
						// TODO more options
						classifier = options[i];
					}
				}
			}

			if (classifier != null || header != null) {
				if (isRow) {
					rowHeaders = true;
				} else {
					colHeaders = true;
				}
			}

			if (classifier == null) {
				classifier = "_" + (unnamedDimensions++);
			}

			Map<Object, Dimension> hash = isRow ? this.crows : this.ccols;
			Dimension dim = hash.get(classifier);
			if (dim == null) {
				dim = new Dimension("c" + hash.size(), options);
				if (classifier instanceof Collection) {
					// FIXME they may have >1 dims
					classifier = ((Collection)classifier).iterator().next();
				}
				if (classifier instanceof ISemantic) {
					dim.label = Concepts.INSTANCE.getDisplayLabel((ISemantic) classifier);
				} else if (classifier instanceof String) {
					dim.label = (String) classifier;
				}
				hash.put(classifier, dim);
			}
			
			if (header != null) {
				dim.label = header.toString();
			}
			
			return dim.id;

		}

		@Override
		public String getColumn(Object... options) {
			return getDimension(false, options);
		}

		@Override
		public String getRow(Object... options) {
			return getDimension(true, options);
		}

		@Override
		public void setCell(String rowId, String colId, Object value, Object... options) {
			Pair<String, String> key = new Pair<>(rowId, colId);
			Cell ret = cells.get(key);
			if (ret == null) {
				ret = new Cell();
				cells.put(key, ret);
			}
			ret.value = value;
		}

		@Override
		public IKnowledgeView build() {
			return new SimpleTableArtifact(new ArrayList<>(crows.values()), new ArrayList<>(ccols.values()), cells,
					compiler, scope, rowHeaders, colHeaders, emptyValue, noDataValue);
		}

		@Override
		public void setEmptyCells(String emptyValue, String noDataValue) {
			this.emptyValue = emptyValue;
			this.noDataValue = noDataValue;
		}

        @Override
        public List<String> getColumnIds() {
            List<String> ret = new ArrayList<>();
            for (Dimension column : ccols.values()) {
                ret.add(column.id);
            }
            return ret;
        }

        @Override
        public List<String> getRowIds() {
            List<String> ret = new ArrayList<>();
            for (Dimension row : crows.values()) {
                ret.add(row.id);
            }
            return ret;
        }

        @Override
        public Object getCellValue(String rowId, String colId) {
            Pair<String, String> key = new Pair<>(rowId, colId);
            Cell cell = cells.get(key);
            return cell == null ? null : cell.value;
        }

	}

	private TableCompiler tableCompiler;
//	private IRuntimeScope scope;
	private String id = "v" + NameGenerator.shortUUID();
	private List<Dimension> rows;
	private List<Dimension> columns;
	private Map<Pair<String, String>, Cell> cells;
	private boolean rowHeaders;
//	private boolean colHeaders;
	private boolean hasNumbers;

	private SimpleTableArtifact(List<Dimension> rows, List<Dimension> cols, Map<Pair<String, String>, Cell> cells,
			TableCompiler compiler, IRuntimeScope scope, boolean rowHeaders, boolean colHeaders, String emptyValue,
			String noDataValue) {
		this.rows = rows;
		this.columns = cols;
		this.cells = cells;
		this.tableCompiler = compiler;
//		this.scope = scope;
		this.rowHeaders = rowHeaders;
//		this.colHeaders = colHeaders;
		this.emptyValue = emptyValue;
		this.noDataValue = noDataValue;
	}

	@Override
	public String getViewClass() {
		return "table";
	}

	@Override
	public String getName() {
		return this.tableCompiler.getName();
	}

	@Override
	public String getTitle() {
		return this.tableCompiler.getTitle();
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
	public String getIdentifier() {
		return this.tableCompiler.getIdentifier();
	}

	@Override
	public IArtifact getGroupMember(int n) {
		return n == 0 ? this : null;
	}

	@Override
	public String getLabel() {
		return this.tableCompiler.getLabel();
	}

	@Override
	public Collection<ExportFormat> getExportFormats() {
		List<ExportFormat> ret = new ArrayList<>();
		ret.add(new ExportFormat("Excel worksheet", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				"xlsx", "xlsx"));
		return ret;
	}

	@Override
	public long getLastUpdate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected IDocumentationView getCompiledView(ITableView view, int sheetId) {

		int hTable = view.table(this.tableCompiler.getTitle(), sheetId);
		/*
		 * data and row titles. Single level is now managed
		 */
		int hBody = view.body(hTable);
		int headerRow = view.newRow(hBody);
		if (rowHeaders) {
			view.write(view.newHeaderCell(headerRow, true), "", Double.NaN, rows.size() > 0 ? rows.get(0).style : null);
		}
		for (Dimension column : columns) {
			view.write(view.newHeaderCell(headerRow, true), column.label != null ? column.label : "", Double.NaN, column.style);
		}
		for (Dimension rDesc : rows) {
			int hRow = view.newRow(hBody);
			if (rowHeaders) {
				view.write(view.newHeaderCell(hRow, true), rDesc.label != null ? rDesc.label : "", Double.NaN, rDesc.style);
			}
			for (Dimension cDesc : columns) {
				Cell cell = cells.get(new Pair<>(rDesc.id, cDesc.id));
				view.write(view.newCell(hRow), getData(cell), getNumberValue(cell), cell == null ? null : cell.style);
			}
		}

		return view;
	}

	@Override
	public boolean export(File file, String mediaType) {
		try (FileOutputStream output = new FileOutputStream(file)) {
			getCompiledView(mediaType).write(output);
			return true;
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> cls) {

		if (Table.class.isAssignableFrom(cls)) {
			Table ret = new Table();
			compile(ret);
			return (T) ret;
		}

		return null;
	}

	private void compile(Table ret) {

		List<Map<String, String>> data = new ArrayList<>();
		ret.setNumberFormat(
				this.tableCompiler.getNumberFormat() == null ? "%.2f" : this.tableCompiler.getNumberFormat());
		ret.setDocumentationIdentifier(this.tableCompiler.getIdentifier());

		if (rowHeaders) {
			Column head = new Column();
			head.setId("rowheaders");
			head.setTitle("");
			head.setType(IArtifact.Type.TEXT);
			ret.getColumns().add(head);
		}

		for (Dimension column : columns) {
			ret.getColumns().add(compileColumn(column));
		}

		for (Dimension rDesc : rows) {
			Map<String, String> rowData = new HashMap<>();
			if (rowHeaders) {
				rowData.put("rowheaders", rDesc.label == null ? "" : rDesc.label);
			}
			for (Dimension cDesc : columns) {
				Cell cell = cells.get(new Pair<>(rDesc.id, cDesc.id));
				Object value = getNumberValue(cell);
				if (value instanceof Double && Double.isNaN((Double) value)) {
					value = getData(cell);
				}
				rowData.put(cDesc.id, value.toString());
			}
			data.add(rowData);
		}

		ret.getRows().addAll(data);

		if (Configuration.INSTANCE.isEchoEnabled()) {
			System.out.println(JsonUtils.printAsJson(ret));
		}
	}

	private Column compileColumn(Dimension column) {
		Column ret = new Column();
		ret.setId(column.id);
		ret.setTitle(column.label);
		ret.setType(IArtifact.Type.NUMBER);
		return ret;
	}

	private String getData(Cell cell) {

		if (cell == null) {
			return emptyValue;
		}

		Object ret = cell.value;
		if (Observations.INSTANCE.isNodata(ret)) {
			return noDataValue;
		}
		if (ret instanceof Number) {
			// TODO harvest format specs from row, then col
			ret = NumberFormat.getNumberInstance().format((Number) ret);
		}
		return ret.toString();
	}

	private double getNumberValue(Cell cell) {

		if (cell == null) {
			return Double.NaN;
		}

		Object ret = cell.value;
		if (Observations.INSTANCE.isNodata(ret)) {
			return Double.NaN;
		}
		if (ret instanceof Number) {
			// TODO harvest format specs from row, then col
			return ((Number) ret).doubleValue();
		}
		return Double.NaN;
	}

	/**
	 * Get a builder for the custom plugins
	 */
	public static Builder builder(TableCompiler compiler, IRuntimeScope scope) {
		return new TableBuilder(compiler, scope);
	}

}
