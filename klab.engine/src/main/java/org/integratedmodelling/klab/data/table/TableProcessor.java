package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.DimensionType;
import org.integratedmodelling.klab.documentation.extensions.table.TableCompiler.Style;
import org.integratedmodelling.klab.documentation.style.StyleDefinition;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.DocumentationNode.Table.Column;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.TemplateUtils;
import org.springframework.util.StringUtils;

public class TableProcessor {

	/**
	 * A group of either rows or columns, possibly containing other groups (of the
	 * same dimension). Each group can produce the flat list of individual rows or
	 * columns with their filters, headers and spans for the final compilation. This
	 * result is a cartesian product of what the constraints in each nested group
	 * imply.
	 * 
	 * Groups have a span which is the number of elements of the OTHER dimension
	 * they occupy. For each span there may be a header/title with a span in the
	 * same dimension.
	 * 
	 * @author Ferd
	 *
	 */
	public class Group {
		// must be same across hierarchy unless we allow table-in-table semantics, not
		// for now.
		DimensionType dimension;
		Group parent;
		String title;
		// select:
		List<Object> filters = null;
		List<Group> children = new ArrayList<>();
		Set<Style> style;
		public String label;
		int depth;

		/**
		 * Maximum depth reachable from this group (!= own depth). Corresponds to the
		 * number of headers spans to reserve below this one.
		 * 
		 * @return
		 */
		public int getSpan() {
			int ret = 0;
			for (Group g : children) {
				int gd = g.getSpan();
				if (ret < gd) {
					ret = gd;
				}
			}
			return ret;
		}

	}

	private class Container {
		// TODO add filters to quickly assess if values pertain
	}

	protected StyleDefinition style;
	protected String valueField;
	protected Type valueType;
	protected IContextualizationScope scope;

	private List<String> keyFields;
	private List<Group> columns;
	private List<Group> rows;

	public TableProcessor(StyleDefinition style, String valueField, IContextualizationScope scope) {
		this.style = style;
		this.valueField = valueField;
		this.valueType = scope.getTargetArtifact().getType();
		this.scope = scope;
		if (style != null) {
			parseStyle();
		}
	}

	private void parseStyle() {
		Object columns = style.get("columns");
		Object rows = style.get("rows");
		if (columns != null) {
			this.columns = parseGroups(columns, DimensionType.COLUMN, null, 0);
		}
		if (rows != null) {
			this.rows = parseGroups(rows, DimensionType.ROW, null, 0);
		}
	}

	private List<Group> parseGroups(Object groups, DimensionType dimension, Group parent, int depth) {

		for (Object g : CollectionUtils.flatCollection(groups)) {

			if (!(g instanceof Map)) {
				throw new KlabIllegalStateException("Group specifications in table styles must be maps");
			}

			@SuppressWarnings("unchecked")
			IParameters<Object> gmap = Parameters.wrap((Map<Object, Object>) g);

			Group group = new Group();

			group.parent = parent;
			group.depth = depth;
			group.title = gmap.get("title", String.class);
			group.label = gmap.get("label", String.class);
			group.filters = parseFilters(gmap.get("select"));
			group.style = parseStyle(gmap.get("style"));

			if (dimension == DimensionType.COLUMN && gmap.containsKey("columns")) {
				group.children.addAll(parseGroups(gmap.get("columns"), dimension, group, depth + 1));
			} else if (dimension == DimensionType.ROW && gmap.containsKey("rows")) {
				group.children.addAll(parseGroups(gmap.get("rows"), dimension, group, depth + 1));
			}

			if (parent == null) {
				switch (dimension) {
				case COLUMN:
					columns.add(group);
					break;
				case ROW:
					rows.add(group);
					break;
				default:
					break;
				}
			}
		}
		return null;
	}

	private Set<Style> parseStyle(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Object> parseFilters(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Compile to bean. If the style contains structure (row and/or column specs),
	 * use the dedicated compiler; otherwise just insert all rows and columns from
	 * the original data, possibly skipping ignored fields and honoring other style
	 * options.
	 * 
	 * @param ret
	 */
	protected void compile(List<Map<String, Object>> data, Table ret) {

		ret.setNumberFormat(getNumberFormat());

		if (columns != null || rows != null) {
			compileStyled(data, ret);
			return;
		}

		for (String key : keyFields) {
			Column column = new Column();
			column.setId(key);
			column.setTitle(StringUtils.capitalize(key) /* TODO use codelist */);
			column.setType(IArtifact.Type.TEXT);
			ret.getColumns().add(column);
		}

		Column column = new Column();
		column.setId(valueField);
		column.setTitle(StringUtils.capitalize(valueField) /* TODO use codelist */);
		column.setType(valueType);
		ret.getColumns().add(column);

		for (Map<String, Object> rDesc : data) {
			Map<String, String> rowData = new HashMap<>();
			for (String key : this.keyFields) {
				rowData.put(key, rDesc.get(key) == null ? "" : rDesc.get(key).toString());
			}
			// TODO number format
			rowData.put(valueField, rDesc.get(valueField) == null ? "" : rDesc.get(valueField).toString());
			ret.getRows().add(rowData);
		}

		if (Configuration.INSTANCE.isEchoEnabled()) {
			System.out.println(JsonUtils.printAsJson(ret));
		}
	}

	private void compileStyled(List<Map<String, Object>> data, Table ret) {

		/*
		 * prepare container lists to accumulate rows and columns as they appear;
		 * initialize known wanted containers to empty state. Empty containers may be
		 * removed or inserted according to specifications. Non-specified containers may
		 * be added as row data get classified.
		 */

		/*
		 * intermediate value storage (list of objects) indexed by container pair
		 */
		Map<Pair<Container, Container>, List<Object>> storage = new HashMap<>();

		/*
		 * put data into the respective containers
		 */
		for (Map<String, Object> rowData : data) {
			Container column = getContainer(rowData, DimensionType.COLUMN);
			if (column == null) {
				continue;
			}
			Container row = getContainer(rowData, DimensionType.ROW);
			if (row != null) {
				Pair<Container, Container> key = new Pair<>(row, column);
				List<Object> d = storage.get(key);
				if (d == null) {
					d = new ArrayList<>();
					storage.put(key, d);
				}
				d.add(rowData.get(valueField));
			}
		}

		/*
		 * At this point all rows and column containers are known; sort them and build
		 * the final table with the aggregated data, inserting header rows and columns
		 * based on depth
		 */

	}

	private Container getContainer(Map<String, Object> rowData, DimensionType dimension) {

		List<Group> groups = dimension == DimensionType.ROW ? rows : columns;
		
		if (groups == null) {
			// if specs are empty: for columns: the key to the container is the combination
			// of values for all the key fields, except those ignored; for rows, each row
			// creates a new container
		}
		
		return null;
	}

	private int getDepth(DimensionType t) {
		int ret = 0;
		List<Group> groups = t == DimensionType.ROW ? rows : columns;
		if (groups != null) {
			ret = 1;
			for (Group g : groups) {
				if (g.getSpan() > ret) {
					ret = g.getSpan() + 1;
				}
			}
		}
		return ret;
	}

	private String getNumberFormat() {
		if (style != null) {
			return style.get("numberformat", String.class);
		}
		return "%.2f";
	}

	/**
	 * List of key fields that will be used if no custom configuration is given.
	 * Must be supplied externally as it depends on knowledge of the data. It will
	 * be ignored if a custom key configuration is supplied in the style.
	 * 
	 * @param keyList
	 */
	public void setKeyFields(List<String> keyList) {
		this.keyFields = keyList;
	}

	protected Collection<String> getKeyFields() {
		return keyFields;
	}

	public String getTitle() {
		if (style != null) {
			return getStyleProperty(style, "title");
		}
		return "Untitled table";
	}

	public String getLabel() {
		if (style != null) {
			return getStyleProperty(style, "label");
		}
		return "Unnamed table";
	}

	private String getStyleProperty(StyleDefinition style2, String key) {
		String title = style.get(key, String.class);
		if (title != null && scope != null) {
			title = TemplateUtils.expandMatches(title, scope.getSession().getState()).get(0);
		}
		return title;
	}

}
