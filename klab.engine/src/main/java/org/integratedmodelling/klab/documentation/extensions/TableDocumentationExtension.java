package org.integratedmodelling.klab.documentation.extensions;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class TableDocumentationExtension {

	private IAnnotation definition;
	private IRuntimeScope scope;
	private IObservable target;
	/*
	 * rows and columns MUST keep the order of definition, although they are
	 * computed in order of dependency. They either have a user-assigned name or are
	 * named {r|c}<n> starting at 1 (and following the actual index, even if there
	 * are named ones in between).
	 */
	private Map<String, Column> columns = null;
	private Map<String, Row> rows = null;
	private List<Split> splits = new ArrayList<>();
	boolean purgeZeroColumns = true;
	private int nonEmptyColumns;
	private int nScales = 1;

	/**
	 * Locators to describe the state (linked to 'at' or to 'compare'). If no
	 * locators, we describe the latest state ('at: end'). If two, we compare two
	 * time points ('compare: (2010 end)'). >2 creates multiple tables with pairwise
	 * comparisons.
	 */
	private List<ILocator> locators = new ArrayList<>();

	/**
	 * Locators if aggregation "by feature" has been requested. These should be
	 * observation groups and their iteration will provide columns, rows or both.
	 */
	private List<IObservation> aggregators = new ArrayList<>();

	enum TargetType {
		AREA, DURATION, QUALITY, NUMEROSITY
	}

	enum SplitType {
		TOTAL, PERIOD, RANGE, SEMANTICS, EXPRESSION, REMAINDER
	};

	/**
	 * Descriptor for one (or more if distributed) column
	 * 
	 * @author Ferd
	 *
	 */
	class Column {

		// TODO the target
		boolean repeats = false;
		List<String> dependencies = new ArrayList<>();
		// not null if the column describes a specific observation or aggregates by it.
		IObservation observation;
		IExpression expression;
		Map<String, Object> data = new HashMap<>();
		String id;
		public String label;
		public Classifier classifier;
		public Object group;

	}

	/**
	 * Descriptor for one (or more if distributed) row
	 * 
	 * @author Ferd
	 *
	 */
	class Row {

		String id;
		IExpression expression;
		// not null if the row describes a specific observation or aggregates by it.
		IObservation observation;
		boolean repeats = false;
		// there may be several titles, potentially nulls. The index in the array is the
		// level of
		// indentation.
		String[] titles;
		Map<String, Object> data = new HashMap<>();
		List<String> dependencies = new ArrayList<>();
		// if this is null, the row is a separator, potentially with titles and
		// formatting
		Aggregator[] aggregators;
	}

	/**
	 * Description of a table, potentially on a subset of the data. If TOTAL it
	 * means all of the data, so there is at least one of those.
	 * 
	 * @author Ferd
	 *
	 */
	class Split {

		SplitType type = SplitType.TOTAL;

	}

	public TableDocumentationExtension(IAnnotation definition, IObservable target, IRuntimeScope scope) {

		this.definition = definition;
		this.scope = scope;
		this.target = target;
		parseColumns(definition.get("columns"));
		parseRows(definition.get("rows"));
		this.splits = parseSplits(definition.get("splits"));

		// TODO check out locators. We may be splitting by temporal extent with no
		// comparison, splitting with
		// comparison, not splitting with comparison, not splitting with no comparison.
	}

	private void parseRows(Object object) {

		this.rows = new LinkedHashMap<>();
		if (object instanceof Collection) {
			for (Object o : ((Collection<?>) object)) {
				if (o instanceof Map) {
					parseRow((Map<?, ?>) o);
				} else if (o instanceof String) {
					switch (o.toString()) {
					case "empty":
						break;
					// TODO others?
					}
				} else {
					throw new KlabValidationException("rows must be specified as maps or lists of maps");
				}
			}
		} else if (object instanceof Map) {
			parseRow((Map<?, ?>) object);
		} else {
			throw new KlabValidationException("rows must be specified as maps or lists of maps");
		}

	}

	private void parseColumns(Object object) {
		this.columns = new LinkedHashMap<>();
		if (object instanceof Collection) {
			for (Object o : ((Collection<?>) object)) {
				if (o instanceof Map) {
					parseColumn((Map<?, ?>) o);
				} else if (o instanceof String) {
					switch (o.toString()) {
					case "empty":
						break;
					// TODO others?
					}
				} else {
					throw new KlabValidationException("colums must be specified as maps or lists of maps");
				}
			}
		} else if (object instanceof Map) {
			parseColumn((Map<?, ?>) object);
		} else {
			throw new KlabValidationException("columns must be specified as maps or lists of maps");
		}
	}

	/*
	 * A column spec may turn into >1 columns
	 */
	private void parseColumn(Map<?, ?> map) {

		if (map.containsKey("classifier")) {
			if (map.get("classifier") instanceof IKimConcept) {
				IConcept classifier = Concepts.INSTANCE.declare((IKimConcept) map.get("classifier"));
				if (classifier.is(Type.COUNTABLE)) {
					// TODO build columns based on the contextualized artifacts for the concept
				} else {
					for (IConcept concept : expandClassifier(classifier, map.get("downto"))) {
						// add as many newColumns as needed
						Column column = newColumn(concept, map);
						column.group = map.get("group") instanceof IKimConcept
								? Concepts.INSTANCE.declare((IKimConcept) map.get("group"))
								: null;
						this.columns.put(column.id, column);
					}
				}
			} else {
				Column column = newColumn(map.get("classifier"), map);
				column.group = map.get("group");
				this.columns.put(column.id, column);
			}
		}
	}

	private Column newColumn(Object classifier, Map<?, ?> theRest) {

		Column ret = new Column();
		ret.classifier = Classifier.create(classifier);

		if (classifier instanceof IConcept && theRest.containsKey("qualifier")) {
			Object label = ((IConcept) classifier).getMetadata().get(theRest.get("qualifier"));
			ret.label = label == null ? null : label.toString();
		}
		if (theRest.containsKey("name")) {
			ret.id = theRest.get("name").toString();
		} else {
			ret.id = "c" + (this.columns.size() + 1);
		}

		this.nonEmptyColumns++;

		return ret;
	}

	private Collection<IConcept> expandClassifier(IConcept declared, Object downto) {

		if (declared.isAbstract() || downto != null) {
			if (downto != null) {
				if (downto instanceof Number) {
					return Types.INSTANCE.getConcreteChildrenAtLevel(declared, ((Number) downto).intValue());
				} else {
					return Types.INSTANCE.getConcreteChildrenAtLevel(declared,
							Types.INSTANCE.getDetailLevel(declared, downto.toString()));
				}
			}
			return Types.INSTANCE.getConcreteChildren(declared);
		}
		return Collections.singletonList(declared);
	}

	private Row newRow(Object classifier, Map<?, ?> theRest) {

		Row ret = new Row();

		if (theRest.containsKey("title")) {
			List<String> titles = new ArrayList<>();
			if (theRest.get("title") instanceof Collection) {
				for (Object o : ((Collection<?>) theRest.get("title"))) {
					titles.add(o == null ? "" : o.toString());
				}
			} else {
				String theTitle = theRest.get("title").toString();
				// count the spaces in front to establish level
				for (; theTitle.startsWith(" ");) {
					titles.add("");
					theTitle = theTitle.substring(1);
				}
				titles.add(theTitle);
			}
			ret.titles = titles.toArray(new String[titles.size()]);
		}

		// name is either attributed or computed based on the size of the column map so
		// far.
		if (theRest.containsKey("name")) {
			ret.id = theRest.get("name").toString();
		} else {
			ret.id = "r" + (this.rows.size() + 1);
		}

		// add enough aggregators to match the number of non-empty columns.
		ret.aggregators = new Aggregator[this.nonEmptyColumns];
		return ret;
	}

	/*
	 * A row spec may turn into >1 rows
	 */
	private void parseRow(Map<?, ?> map) {
		// TODO Auto-generated method stub
		if (map.get("classifier") instanceof IKimConcept) {
			IConcept classifier = Concepts.INSTANCE.declare((IKimConcept) map.get("classifier"));
			if (classifier.is(Type.COUNTABLE)) {
				// TODO build columns based on the contextualized artifacts for the concept
			} else {
				// TODO row cells will summarize the classified values that match a classifier
				// works just like columns
			}
		}
	}

	private List<Split> parseSplits(Object object) {
		List<Split> ret = new ArrayList<>();
		if (object == null) {
			ret.add(new Split());
		}
		return ret;
	}

	public String compile() {

		StringBuffer buffer = new StringBuffer(2048);

		for (Split split : splits) {
			buffer.append("\n<p>");
			buffer.append(compile(split));
			buffer.append("</p>");
		}

		return buffer.toString();
	}

	private String compile(Split split) {

		/*
		 * Null out the aggregator lists in each row, for reentrancy.
		 */
		for (Row row : rows.values()) {
			if (row.aggregators != null) {
				for (int i = 0; i < row.aggregators.length; i++) {
					row.aggregators[i] = null;
				}
			}
		}

		/*
		 * Sort both columns and rows by dependency
		 */

		/*
		 * foreach outer-level scale (normally 1, if 2 we're comparing two scales for
		 * the same objects)
		 */
		int scaleIndex = 0;

		if (this.target.is(Type.COUNTABLE)) {
			// TODO at least one dimension is for objects: the other may be aggregating
			// their qualities or
			// part-of relationships or other relationship between objects if both have
			// objects. E.g.
			// target = {count of} {Income of} CommercialRelationship between countries on
			// both dimensions - semantics can be used effectively here.
		} else {
			for (IScale scale : getScales()) {

				/**
				 * If !aggregate, we're at the first pass and accumulating values for comparison
				 */
				boolean aggregate = scaleIndex == (this.nScales - 1);

				/*
				 * foreach value in state being valued
				 */
				for (ILocator locator : scale) {

					Object value = null; // TODO either the target observable or whatever other concept we are
					// looking at, area, duration of the timestep, attribute, whatever.

					/*
					 * foreach column in dependency order
					 */
					for (Column column : sortColumns()) {

						/*
						 * check if value applies. Columns w/o classifier that are not separators match
						 * by definition.
						 */
						if (column.classifier != null && !column.classifier.classify(value, scope)) {
							continue;
						}

						/*
						 * create the aggregatable value we want in this column
						 */

						/*
						 * foreach row in dependency order
						 */
						for (Row row : sortRows()) {

							/*
							 * Check if value applies
							 */

							/*
							 * Add whatever it is we compute. Each row will have an array of aggregators,
							 * one per cell, to which we add the value.
							 */
						}
					}
				}
			}
		}

		/*
		 * second pass to build the final table when all addenda are in
		 */

		return "<b>UNIMPLEMENTED TABLE EXTENSION</b>";
	}

	private Iterable<IScale> getScales() {

		IScale refScale = scope.getScale();

		if (locators.isEmpty()) {
			return Collections.singletonList((IScale) (refScale.isTemporallyDistributed()
					? refScale.at(refScale.getTime().getExtent(refScale.getTime().size() - 1))
					: refScale));
		}
		return null;
	}

	private Iterable<Column> sortColumns() {
		// TODO
		return columns.values();
	}

	private Iterable<Row> sortRows() {
		// TODO
		return rows.values();
	}

	/**
	 * Produce the beloved Excel spreasheet, to be linked to report buttons. This
	 * needs a peer to ADD to an open spreadsheet so that we can put all tables in a
	 * report in multiple sheets of a single document.
	 * 
	 * @param file
	 */
	public void export(File file) {

	}

}
