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
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.documentation.views.IDocumentationView;
import org.integratedmodelling.klab.api.documentation.views.ITableView;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Style;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.DocumentationNode.Table.Column;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;

public class SimpleTableArtifact extends Artifact implements IKnowledgeView {

	static class Dimension {
		public Dimension(String string, Object[] options) {
			this.id = string;
			// TODO handle options
		}

		boolean header;
		String id;
		String label;
		Set<Style> style = EnumSet.noneOf(Style.class);
	}

	static class Cell {
		Object value;
		Set<Style> style = EnumSet.noneOf(Style.class);
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
		
		TableBuilder(TableCompiler compiler, IRuntimeScope scope) {
			this.scope = scope;
			this.compiler = compiler;
		}

		@Override
		public String getColumn(Object classifier, Object... options) {
			Dimension dim = ccols.get(classifier);
			if (dim == null) {
				dim = new Dimension("c" + ccols.size() + 1, options);
				if (classifier instanceof ISemantic) {
					dim.label = Concepts.INSTANCE.getDisplayLabel((ISemantic) classifier);
					colHeaders = true;
				} else if (classifier instanceof String) {
					dim.label = (String) classifier;
					colHeaders = true;
				}
				ccols.put(classifier, dim);
			}
			return dim.id;
		}

		@Override
		public String getRow(Object classifier, Object... options) {
			Dimension dim = crows.get(classifier);
			if (dim == null) {
				dim = new Dimension("r" + crows.size() + 1, options);
				if (classifier instanceof ISemantic) {
					dim.label = Concepts.INSTANCE.getDisplayLabel((ISemantic) classifier);
					rowHeaders = true;
				} else if (classifier instanceof String) {
					dim.label = (String) classifier;
					rowHeaders = true;
				}
				crows.put(classifier, dim);
			}
			return dim.id;
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
			// TODO set style, other options
		}

		@Override
		public IKnowledgeView build() {
			return new SimpleTableArtifact(new ArrayList<>(crows.values()), new ArrayList<>(ccols.values()), cells,
					compiler, scope, rowHeaders, colHeaders, rowTotals, colTotals);
		}

		@Override
		public void setTotals(boolean rowTotals, boolean colTotals) {
			this.rowTotals = rowTotals;
			this.colTotals = colTotals;
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
	private boolean rowTotals;
	private boolean colTotals;

	private SimpleTableArtifact(List<Dimension> rows, List<Dimension> cols, Map<Pair<String, String>, Cell> cells,
			TableCompiler compiler, IRuntimeScope scope, boolean rowHeaders, boolean colHeaders, boolean rowTotals,
			boolean colTotals) {
		this.rows = rows;
		this.columns = cols;
		this.cells = cells;
		this.tableCompiler = compiler;
//		this.scope = scope;
		this.rowHeaders = rowHeaders;
//		this.colHeaders = colHeaders;
		this.rowTotals = rowTotals;
		this.colTotals = colTotals;
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
	public IDocumentationView getCompiledView(String mediaType) {

		ITableView ret = null;

		if (TableArtifact.HTML_MEDIA_TYPE.equals(mediaType)) {
			ret = new TableView();
		} else if (TableArtifact.EXCEL_MEDIA_TYPE.equals(mediaType)) {
			ret = new ExcelView();
		}

		if (ret == null) {
			throw new KlabValidationException("table view: media type " + mediaType + " is not supported");
		}

		return getCompiledView(ret, ret.sheet(tableCompiler.getLabel()));
	}

	private IDocumentationView getCompiledView(ITableView view, int sheetId) {

		int hTable = view.table(this.tableCompiler.getTitle(), sheetId);
		/*
		 * data and row titles. Row groups can go to hell for now.
		 */
		int hBody = view.body(hTable);
		for (Dimension rDesc : rows) {
			int hRow = view.newRow(hBody);
			for (Dimension column : columns) {
				view.write(view.newHeaderCell(hRow, true), column.label, Double.NaN, rDesc.style);
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
			return tableCompiler.getNumberFormat() == null ? "" : "0";
		}

		Object ret = cell.value;
		if (Observations.INSTANCE.isNodata(ret)) {
			return "";
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
