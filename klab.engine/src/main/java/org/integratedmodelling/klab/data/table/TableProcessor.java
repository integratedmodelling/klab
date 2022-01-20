package org.integratedmodelling.klab.data.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.classification.Classifier;
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
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.TemplateUtils;
import org.integratedmodelling.klab.utils.Utils;
import org.springframework.util.StringUtils;

public class TableProcessor {

	private AtomicInteger groupIds = new AtomicInteger(0);
	private AtomicInteger rowCounter = new AtomicInteger(0);
	private AtomicInteger columnCounter = new AtomicInteger(0);
	private AtomicInteger boxCounter = new AtomicInteger(0);

	/*
	 * there is zero or one root header box for both rows and columns; each row or
	 * column maps to a level in the box.
	 */
	private class Box {
		int span = 1;
		String header;
		Group group;
		List<Box> contents = new ArrayList<>();
		String boxId = "bx" + boxCounter.incrementAndGet();
		Box next;
		Box parentBox;
		boolean skipped;
		//
		String id;

		/**
		 * Get the depth of this box
		 * 
		 * @return
		 */
		public int getDepth() {
			int ret = 1;
			Box box = this;
			for (Box b : box.contents) {
				ret += b.getDepth();
				break;
			}
			return ret;
		}

		/**
		 * Get the maximum depth across this box and all those linked to it
		 * 
		 * @return
		 */
		public int getMaximumDepth() {
			int ret = getDepth();
			for (Box b = next; b != null; b = b.next) {
				int bd = b.getDepth();
				if (bd > ret) {
					ret = bd;
				}
			}
			return ret;
		}

		public String getPath() {
			return (this.parentBox == null ? "" : this.parentBox.getPath()) + (skipped ? "" : boxId);
		}

		public String getId() {
			if (this instanceof Container) {
				return ((Container) this).id;
			} else if (group != null) {
				return group.id;
			}
			return "hbox" + groupIds.incrementAndGet();
		}

		public int getSize() {
			if (this.contents.isEmpty()) {
				return 1;
			}
			int ret = 0;
			for (Box content : this.contents) {
				ret += content.getSize();
			}
			return ret;
		}

		public void addChild(Box container) {
			container.parentBox = this;
			// FIXME this should not happen!
			if (!this.contents.contains(container)) {
				this.contents.add(container);
			}
		}

		@Override
		public String toString() {
			return "Box[" + (header == null ? "innermost" : header) + "](" + this.contents.size() + ")";
		}

	}

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
		String sort;
		// classify:
		List<IClassifier> classifiers = new ArrayList<>();
		List<Group> children = new ArrayList<>();
		Set<Style> style;
		String align = null;
		public String label;
		int depth;
		public String targetField;
		public boolean classifyValues;
		public String id = "g" + groupIds.incrementAndGet();

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

		public IClassifier getMatch(Map<String, Object> rowData) {

			Object o = null;
			if (this.targetField != null) {
				o = rowData.get(this.targetField);
			}

			if (classifiers != null) {

				for (IClassifier classifier : this.classifiers) {
					if (classifier.classify(o, scope)) {
						// if 'distribute', make a classifier for the value, not the classifier itself
						if (Boolean.TRUE.equals(classifier.getMetadata().get("distribute"))) {
							return Classifier.create(o).withMetadata("group", this).withMetadata("distributed", true);
						}
						return classifier;
					}
				}
			} else if (this.targetField != null) {
				/*
				 * make a classifier for the field
				 */
				return Classifier.create(o).withMetadata("group", this).withMetadata("distributed", true);
			}
			return null;
		}

		/*
		 * if true, the group is meant to select something and is not just a structural
		 * grouping
		 */
		public boolean isSelector() {
			return targetField != null;
		}

		public boolean admits(Map<String, Object> rowData) {
			// TODO filter expressions
			return true;
		}

	}

	private class Container extends Box {

		List<IClassifier> classifiers = null;
		Group originator = null;
		// stores the target field name for columns; redundant with the classifier, for
		// cleanliness
		Object target;
		String title;
		String id;
		IArtifact.Type type = IArtifact.Type.TEXT;

		public Container(List<IClassifier> matched, Group group) {
			this.classifiers = matched;
			this.originator = group;
			this.id = (group.dimension == DimensionType.COLUMN ? ("c" + columnCounter.incrementAndGet())
					: ("r" + +rowCounter.incrementAndGet()));
		}

		public Container withTarget(Object target) {
			this.target = target;
			return this;
		}

		public Container withId(String id) {
			this.id = id;
			return this;
		}

		@Override
		public String toString() {
			return getId() + (classifiers == null ? classifiers : "[]");
		}

		public String getId() {
			if (id != null) {
				return id;
			}
			if (target instanceof String) {
				return (String) target;
			}
			if (originator != null) {
				Group group = originator;
				String ret = originator.id;
				while (group.parent != null) {
					ret = group.parent.id + "." + ret;
					group = group.parent;
				}
				id = ret;
				return ret;
			}
			return null;
		}

		public String getTitle() {
			if (title != null) {
				return title;
			}
			return StringUtil.capitalize(getId()).replace("_", " ");
		}

		public Type getType() {
			return type;
		}

	}

	protected StyleDefinition style;
	protected String valueField;
	protected Type valueType;
	protected IContextualizationScope scope;
	protected Aggregator aggregator;

	private List<String> keyFields;
	private Map<String, String> hiddenFields = new HashMap<>();
	private List<Group> columns = new ArrayList<>();
	private List<Group> rows = new ArrayList<>();

	public TableProcessor(StyleDefinition style, String valueField, Aggregator aggregator,
			IContextualizationScope scope) {
		this.style = style;
		this.valueField = valueField;
		this.valueType = scope.getTargetArtifact().getType();
		this.aggregator = aggregator;
		this.scope = scope;

		if (style != null) {
			parseStyle();
		}
	}

	public Group universal(DimensionType row) {
		Group ret = new Group();
		ret.dimension = row;
		// if column, produce a classifier per each key; if row, one per row number
		// (number or sth)
		ret.classifyValues = true;
		return ret;
	}

	private void parseStyle() {

		Object columns = style.get("columns");
		Object rows = style.get("rows");
		if (columns != null) {
			this.columns = new ArrayList<>();
			for (Object g : CollectionUtils.flatCollection(columns)) {
				if (!(g instanceof Map)) {
					throw new KlabIllegalStateException("Group specifications in table styles must be maps: " + g);
				}
				for (Object o : ((Map<?, ?>) g).keySet()) {
					Object column = ((Map<?, ?>) g).get(o);
					if (column instanceof Map) {
						this.columns.add(parseGroup((Map<?, ?>) column, DimensionType.COLUMN, null, 0, o.toString()));
					}
				}
			}
		} else {
			this.columns.add(universal(DimensionType.COLUMN));
		}
		if (rows != null) {
			this.rows = new ArrayList<>();
			for (Object g : CollectionUtils.flatCollection(rows)) {
				if (!(g instanceof Map)) {
					throw new KlabIllegalStateException("Group specifications in table styles must be maps: " + g);
				}
				for (Object o : ((Map<?, ?>) g).keySet()) {
					Object row = ((Map<?, ?>) g).get(o);
					if (row instanceof Map) {
						this.rows.add(parseGroup((Map<?, ?>) row, DimensionType.ROW, null, 0, o.toString()));
					}
				}
			}
		} else {
			this.rows.add(universal(DimensionType.ROW));
		}
		if (this.style.get("hide") instanceof Map) {
			for (Object o : ((Map<?, ?>) this.style.get("hide")).keySet()) {
				hiddenFields.put(o.toString(), ((Map<?, ?>) this.style.get("hide")).get(o).toString());
			}
		}
	}

	/**
	 * Use this to insert field descriptions as column headers.
	 * 
	 * @param field
	 * @return
	 */
	protected String getFieldHeader(String field) {
		return StringUtils.capitalize(field).replace("_", " ");
	}

	private Group parseGroup(Map<?, ?> group, DimensionType dimension, Group parent, int depth, String targetField) {

		@SuppressWarnings("unchecked")
		IParameters<Object> gmap = Parameters.wrap((Map<Object, Object>) group);

		Group ret = new Group();

		ret.parent = parent;
		ret.depth = depth;
		ret.dimension = dimension;
		ret.targetField = targetField;
		ret.title = gmap.get("title", String.class);
		ret.label = gmap.get("label", String.class);
		ret.classifiers = parseClassifiers(gmap.get("classify"), ret);
		ret.style = parseStyle(gmap.get("style"));
		ret.sort = gmap.get("sort", String.class);
		ret.align = gmap.get("align", String.class);

		if (dimension == DimensionType.COLUMN && gmap.containsKey("columns") && gmap.get("columns") instanceof Map) {
			Map<?, ?> dim = (Map<?, ?>) gmap.get("columns");
			for (Object o : dim.keySet()) {
				Object d = dim.get(o);
				if (d instanceof Map) {
					ret.children.add(parseGroup((Map<?, ?>) d, dimension, ret, depth + 1, o.toString()));
				}
			}
		} else if (dimension == DimensionType.ROW && gmap.containsKey("rows") && gmap.get("rows") instanceof Map) {
			Map<?, ?> dim = (Map<?, ?>) gmap.get("rows");
			for (Object o : dim.keySet()) {
				Object d = dim.get(o);
				if (d instanceof Map) {
					ret.children.add(parseGroup((Map<?, ?>) d, dimension, ret, depth + 1, o.toString()));
				}
			}
		}

		return ret;
	}

	private Set<Style> parseStyle(Object object) {
		Set<Style> ret = null;
		if (object != null) {
			for (Object o : CollectionUtils.flatCollection(object)) {
				try {
					Style s = Style.valueOf(o.toString().toUpperCase());
					if (ret == null) {
						ret = new HashSet<>();
					}
					ret.add(s);
				} catch (Throwable t) {
					// ok
				}
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	private List<IClassifier> parseClassifiers(Object object, Group group) {

		if (object == null) {
			return null;
		}

		List<IClassifier> ret = new ArrayList<>();
		for (Object o : CollectionUtils.shallowCollection(object)) {

			String label = null;
			Object classifier = o;

			if (classifier instanceof Map) {
				if (!((Map<?, ?>) o).containsKey("classifier")) {
					throw new KlabIllegalStateException(
							"Classifier specifications in table styles must be simple or contain the keyword 'classifier': "
									+ o);
				}
				classifier = ((Map<?, ?>) o).get("classifier");
			} else if (classifier instanceof List) {
				if (((List<?>) o).size() != 2) {
					throw new KlabIllegalStateException(
							"List classifier specifications in table styles must contain two strings (classifier label): "
									+ o);
				}
				classifier = ((List<?>) o).get(0);
				label = ((List<?>) o).get(1).toString();
			}

			// TODO support (cls, label) as well

			Classifier cls = null;
			if (classifier instanceof String && StringUtil.containsAny((String) classifier, "?*^[]")) {
				cls = Classifier.regexpMatch((String) classifier).withMetadata("group", group);
			} else {
				cls = Classifier.create(classifier).withMetadata("group", group);
			}

			if (cls == null) {
				scope.getMonitor().warn("Unrecognized classifier in select: " + classifier);
			} else {
				if (label != null) {
					cls.getMetadata().put("label", label);
				}
				if (o instanceof Map) {
					cls.getMetadata().putAll((Map<String, Object>) o);
				}
			}

			ret.add(cls);
		}
		return ret;
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

		if (style != null) {
			compileStyled(data, ret);
			return;
		}

		for (String key : keyFields) {
			Column column = new Column();
			column.setId(key);
			column.setTitle(StringUtils.capitalize(key).replace("_", " ") /* TODO use codelist */);
			column.setType(IArtifact.Type.TEXT);
			ret.getColumns().add(column);
		}

		Column column = new Column();
		column.setId(valueField);
		column.setTitle(StringUtils.capitalize(valueField).replace("_", " ") /* TODO use codelist */);
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

		Map<Object, Container> columns = new LinkedHashMap<>();
		Map<Object, Container> rows = new LinkedHashMap<>();

		/*
		 * put data into the respective containers
		 */
		int r = 0;
		boolean trivial = false;
		for (Map<String, Object> rowData : data) {
			for (Container column : getContainer(rowData, DimensionType.COLUMN, r, columns)) {

				if (!trivial) {
					trivial = column.target instanceof String;
				}

				for (Container row : getContainer(rowData, DimensionType.ROW, r, rows)) {

					// skip everything in the trivial case, we'll just use the row data in the
					// target
					if (trivial && row.target instanceof Map) {
						continue;
					}
					Object odat = rowData.get(valueField);
					Pair<Container, Container> key = new Pair<>(row, column);
					List<Object> d = storage.get(key);
					if (d == null) {
						d = new ArrayList<>();
						storage.put(key, d);
						if (column.type == null && Observations.INSTANCE.isData(odat)) {
							column.type = Utils.getArtifactType(odat.getClass());
						}
					}
					d.add(odat);
				}
			}
			r++;
		}

		Box columnHeaders = computeHeaders(DimensionType.COLUMN, columns, rows);
		Box rowHeaders = computeHeaders(DimensionType.ROW, rows, columns);

		// columns to hold row headers. The depth includes the row container so we
		// subtract 1.
		if (rowHeaders != null) {
			for (int i = 0; i < rowHeaders.getMaximumDepth() - 1; i++) {
				Column column = new Column();
				column.setId("rh" + i);
				column.setType(Type.TEXT);
				ret.getColumns().add(column);
			}
		}

		if (columnHeaders != null) {
			int hRows = columnHeaders.getMaximumDepth();
			for (Box cbox = columnHeaders; cbox != null; cbox = cbox.next) {

				int depth = cbox.getDepth();
				int start = hRows - depth;
				Column column = null;
				for (int i = 0; i < start; i++) {
					Column col = new Column();
					if (column != null) {
						column.getColumns().add(col);
						column = col;
					}
				}

				ret.getColumns().add(makeColumns(column, cbox));
			}
		} else {

			for (String key : keyFields) {
				Column column = new Column();
				column.setId(key);
				column.setTitle(getFieldHeader(key));
				column.setType(IArtifact.Type.TEXT);
				ret.getColumns().add(column);
			}
		}

		if (rowHeaders != null) {
			int hCols = rowHeaders.getMaximumDepth() - 1;
			Map<String, String> headers = new HashMap<>();
			for (Box rbox = rowHeaders; rbox != null; rbox = rbox.next) {
				int level = hCols - rbox.getDepth() + 1;
				Box curbox = rbox;
				while (!curbox.contents.isEmpty() && !(curbox.contents.get(0) instanceof Container)) {
					if (curbox.header != null) {
						headers.put("rh" + level, curbox.header);
					}
					curbox = curbox.contents.get(0);
				}
				if (curbox.header != null) {
					headers.put("rh" + level, curbox.header);
				}

				// at this point the rbox contains row containers
				for (Box row : curbox.contents) {
					Map<String, String> rowData = processRow((Container) row, ret, storage, columns, false);
					rowData.putAll(headers);
				}
			}

		} else {

			for (Container row : rows.values()) {
				processRow(row, ret, storage, columns, trivial);
			}
		}

		System.out.println(JsonUtils.printAsJson(ret));

	}

	private Map<String, String> processRow(Container row, Table ret,
			Map<Pair<Container, Container>, List<Object>> storage, Map<Object, Container> columns, boolean trivial) {
		// TODO Auto-generated method stub
		Map<String, String> rowData = new HashMap<>();
		for (Container col : columns.values()) {
			if (trivial && row.target instanceof Map) {
				rowData.put(col.getId(), ((Map<?, ?>) row.target).get(col.getId()).toString());
			} else {
				List<Object> value = storage.get(new Pair<Container, Container>(row, col));
				Object val = "";
				if (value == null || value.isEmpty()) {
					val = "";
				} else if (value.size() == 1) {
					val = value.get(0);
				} else {
					val = aggregator.aggregate(value);
				}
				rowData.put(col.getPath(), /* TODO number format etc */ val.toString());
			}
		}
		ret.getRows().add(rowData);
		return rowData;
	}

	private Pair<Collection<String>, Collection<Container>> getRowDimensions(Box cbox, int maxHeaderDepth) {
		List<String> headers = new ArrayList<>();
		List<Container> rows = new ArrayList<>();
		return new Pair<>(headers, rows);
	}

	private Column makeColumns(Column parent, Box cbox) {
		Column column = new Column();
		column.setTitle(cbox.header);
//		column.setId(cbox.getId());
		column.setId(cbox.boxId);
		column.setHozAlign(cbox.group == null ? null : cbox.group.align);
		for (Box child : sortContents(cbox)) {
			makeColumns(column, child);
		}
		// simplify empty boxes (viewer will insert another untitled column and make it
		// sortable with a header)
		if (column.getColumns().size() == 1 && column.getColumns().get(0).getTitle() == null) {
			cbox.skipped = true;
			Column inner = column.getColumns().get(0);
			column.setId(inner.getId());
			column.getColumns().clear();
			column.getColumns().addAll(inner.getColumns());
		}
		if (parent != null) {
			parent.getColumns().add(column);
		}
		return parent == null ? column : parent;
	}

	private List<Box> sortContents(Box box) {
		List<Box> ret = box.contents;
		if (ret.size() > 1) {
			Group sortingGroup = getSortingGroup(box);
			if (sortingGroup != null) {
				switch (sortingGroup.sort) {
				case "numeric":
					Collections.sort(ret, new Comparator<Box>() {
						@Override
						public int compare(Box o1, Box o2) {
							return Long.valueOf(o1.header).compareTo(Long.valueOf(o2.header));
						}
					});
					break;
				case "numeric_descending":
					Collections.sort(ret, new Comparator<Box>() {
						@Override
						public int compare(Box o1, Box o2) {
							return Long.valueOf(o2.header).compareTo(Long.valueOf(o1.header));
						}
					});
					break;
				case "ascending":
					Collections.sort(ret, new Comparator<Box>() {
						@Override
						public int compare(Box o1, Box o2) {
							return o1.header.compareTo(o2.header);
						}
					});
					break;
				case "descending":
					Collections.sort(ret, new Comparator<Box>() {
						@Override
						public int compare(Box o1, Box o2) {
							return o2.header.compareTo(o1.header);
						}
					});
					break;
				default:
					throw new KlabIllegalStateException("unknown sorting option: " + sortingGroup.sort);
				}
			}
		}
		return ret;
	}

	/**
	 * Called if a box has >1 children, which can be other boxes with more box
	 * children (upper-level subdivision, group of the main box is the
	 * sort-providing group) or other boxes with 1 Container as a child, in which
	 * case each box is just a wrapper of the container providing a header for each
	 * container, and the sorting criterion is the group each box belongs to, as
	 * long as it is the same group for all boxes.
	 * 
	 * Returns null unless the group providing the sorting has a sort specification.
	 * 
	 * @param box
	 * @return
	 */
	private Group getSortingGroup(Box box) {
		Group g = null;
		boolean different = false;
		for (Box b : box.contents) {
			if (b.contents.size() == 1 && b.contents.get(0) instanceof Container) {
				if (g == null) {
					g = b.group;
				} else if (g != b.group) {
					different = true;
				}
			} else {
				g = box.group;
			}
		}
		return different ? null : (g.sort == null ? null : g);
	}

	private Box computeHeaders(DimensionType dimension, Map<Object, Container> described,
			Map<Object, Container> orthogonal) {
		Box ret = null;
		Map<String, Box> boxCatalog = new HashMap<>();
		Box current = null;
		for (Object containerKey : described.keySet()) {
			Box box = box(described.get(containerKey), containerKey, boxCatalog, 0);
			if (ret == null) {
				ret = current = box;
			} else if (current != box) {
				current.next = box;
				current = box;
			}
		}
		return ret;
	}

	private Box box(Box container, Object containerKey, Map<String, Box> boxCatalog, int depth) {

		Box ret = null;

		if (containerKey instanceof String) {

			/*
			 * a field name, can't be in a group so no parents. Use the protected method to
			 * box the column with its label and return the 1-span box.
			 */
			ret = new Box();
			ret.header = getFieldHeader((String) containerKey);
			ret.addChild(container);

		} else if (containerKey instanceof List) {

			/*
			 * list of classifiers, linked to a group and possibly distributing up to a root
			 * group.
			 */
			for (int i = ((List<?>) containerKey).size() - 1; i >= 0; i--) {

				boolean innermost = i == ((List<?>) containerKey).size() - 1;

				Classifier classifier = (Classifier) ((List<?>) containerKey).get(i);
				Group group = classifier.getMetadata().get("group", Group.class);
				boolean distributed = classifier.getMetadata().get("distributed", false);
				boolean distributing = classifier.getMetadata().get("distribute", false);
				String clabel = classifier.getMetadata().get("label", String.class);

				if (clabel == null) {
					clabel = group.label;
				}

				if (clabel != null) {

					Map<String, Object> variables = new HashMap<>();
					variables.put("value", classifier.asValue(scope));
					if (group.targetField != null && classifier.isStringMatch()) {
						variables.put("description", getValueLabel(group.targetField, classifier.asValue(scope)));
					}

					// process special fields in label and apply to classifier
					clabel = TemplateUtils.expandMatches(clabel, variables).get(0);
					String boxKey = group.id + "@" + classifier.dumpCode();
					Box box = innermost ? null : boxCatalog.get(boxKey);
					if (box == null) {
						box = new Box();
						box.header = clabel;
						box.group = group;
						boxCatalog.put(boxKey, box);
					}
					if (ret != null) {
						box.addChild(ret);
					} else {
						box.addChild(container);
					}
					ret = box;
				}

				if (group.title != null) {
					Box box = boxCatalog.get(group.id);
					if (box == null) {
						box = new Box();
						box.header = group.title;
						box.group = group;
						boxCatalog.put(group.id, box);
					} else {
						box.span++;
					}

					/*
					 * no check for duplicates: addChild() won't add a box that is already there
					 */
					if (ret != null) {
						box.addChild(ret);
					} else {
						box.addChild(container);
					}
					ret = box;
				}
			}

		}
		/**
		 * otherwise it's an integer for the row number, which we ignore because it
		 * means there was no row group, we just return null. We may want to number rows
		 * through a global option.
		 */

		return ret;
	}

	protected String getValueLabel(String targetField, Object value) {
		return value == null ? "" : value.toString();
	}

	private Collection<Container> getContainer(Map<String, Object> rowData, DimensionType dimension, int rowNumber,
			Map<Object, Container> catalog) {

		List<Container> ret = new ArrayList<>();

		List<Group> groups = dimension == DimensionType.ROW ? rows : columns;
		for (Group group : groups) {
			if (group.admits(rowData)) {
				List<Container> containers = new ArrayList<>();
				List<IClassifier> matched = new ArrayList<>();
				findMatches(rowData, group, matched, containers, rowNumber, catalog);
				ret.addAll(containers);
			}
		}

		return ret;
	}

	private void findMatches(Map<String, Object> rowData, Group group, List<IClassifier> matched, List<Container> ret,
			int rowNumber, Map<Object, Container> catalog) {

		/*
		 * trivial case (no specs) handled separately
		 */
		if (group.classifyValues) {

			if (group.dimension == DimensionType.COLUMN) {
				/*
				 * add or find a container per field (with target = targetField) unless skipped.
				 * If fields must be skipped on unchanging values, keep them and remove them
				 * later.
				 */
				if (group.targetField == null) {
					for (String key : keyFields) {
						if (!"always".equals(hiddenFields.get(key))) {
							if (!catalog.containsKey(key)) {
								catalog.put(key, new Container(null, group).withTarget(key).withId(key));
							}
							ret.add(catalog.get(key));
						}
						if (!catalog.containsKey(valueField)) {
							catalog.put(valueField,
									new Container(null, group).withTarget(valueField).withId(valueField));
						}
						ret.add(catalog.get(valueField));
					}
				} else {
					if (!catalog.containsKey(group.targetField)) {
						catalog.put(group.targetField, new Container(null, group).withTarget(group.targetField));
					}
					ret.add(catalog.get(group.targetField));
				}
			} else {
				if (!catalog.containsKey(rowNumber)) {
					catalog.put(rowNumber, new Container(null, group).withTarget(rowData).withId("r" + rowNumber));
				}
				ret.add(catalog.get(rowNumber));
			}

			return;
		}

		IClassifier cls = group.getMatch(rowData);
		if (cls != null) {
			matched.add(cls);
		}

		if (cls == null && group.isSelector()) {
			return;
		}

		if (group.children.isEmpty()) {
			if (!catalog.containsKey(matched)) {
				catalog.put(matched, new Container(matched, group));
			}
			ret.add(catalog.get(matched));
		} else {
			for (Group g : group.children) {
				findMatches(rowData, g, matched, ret, rowNumber, catalog);
			}
		}
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
