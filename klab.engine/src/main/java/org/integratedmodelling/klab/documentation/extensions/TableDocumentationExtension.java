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
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

/**
 * A table has a set of column and row specs, specified either individually or by expanding 
 * one or more specs based on the classifiers adopted. It has a target observation that selects
 * the artifact whose computation triggers the table generation, and an optional trigger that
 * defines when the table can be generated (after initialization of the target, after scheduling of
 * all temporal transitions, or every time the report is generated, which is the default).
 * 
 * Once expanded, a set of row and column descriptors is built. Each descriptor has:
 * 
 * Columns contain:
 * 
 * 0. A target observable that selects the observation to be summarized in the column. If not given, 
 *    the target is the target observation for the table. The observable can be given as a concept
 *    or as a string if units, ranges or currencies must be specified.
 *    
 * 1. An optional classifier to select a subset of the observation. If the classifier is an 
 *    abstract concept, it is expanded into as many columns as there are concrete values for it, 
 *    optionally at specified levels (by default the "leaf" values are considered).
 * 
 * Rows have:
 * 
 * 0. A target, which selects an observation to use as classifier;
 * 1. a FILTER, which may be a concept (to match a category observation), a range or other
 *    numeric classifier (to match quantities), a subject or event (to match their area, time
 *    or duration in the column observations), nothing (to match everything), or ....
 * 2. An optional expression, to modify the value once a match is positive;
 * 3. An aggregator, to collect all the values that match the filter. If unspecified, the
 *    aggregation will be defined by the target observable in the column and its units.
 *   
 *    
 * 
 * @author Ferd
 *
 */
@Deprecated
public class TableDocumentationExtension {

	private Map<?, ?> definition;
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
	
	enum TargetType {
		AREA, DURATION, QUALITY, NUMEROSITY
	}

	enum SplitType {
		TOTAL, PERIOD, RANGE, SEMANTICS, EXPRESSION, REMAINDER
	};

	/**
	 * Views are for comparison - there is either one view or two (maybe more); a
	 * column or row can be specific of a given view or dedicated to collecting and
	 * comparing all of them, but never both at the same time. Views may be defined
	 * in terms of time points, space coverages or (later) predicates or
	 * interpretations.
	 * 
	 * The view should expose the iterator for the aggregatable values represented
	 * by it.
	 * 
	 * @author Ferd
	 *
	 */
	class View {
		IScale scale;

		public Iterable<ILocator> states() {
			if (scale != null) {
				return scale;
			}
			return null;
		}
	}

	/**
	 * Each row and column has a filter to select the subset of the data space they apply to. The simplest
	 * filter is the all-pass or empty filter. Others may match values (through a classifier) or direct
	 * observations (through their ID).
	 * 
	 * @author Ferd
	 *
	 */
	class Filter {
		
		boolean allPass;
		IClassifier classifier;
		
		
	}


	
	/**
	 * Descriptor for one (or more if distributed) column
	 * 
	 * @author Ferd
	 *
	 */
	class Column {

		List<String> dependencies = new ArrayList<>();
		// not null if the column describes a specific observation or aggregates by it.
		IObservation observation;
		IExpression expression;
		Map<String, Object> data = new HashMap<>();
		String id;
		public String label;
		public Classifier classifier;
		public Object group;
		public TargetType targetType = TargetType.QUALITY;

	}

	/**
	 * Descriptor for one (or more if distributed) row. TODO lots of info are in
	 * common with column, and in fact the two are basically interchangeable, except
	 * the aggregators for the cells are only attributed to rows (but they don't
	 * have to). So maybe we should improve the data model and use a common class.
	 * 
	 * @author Ferd
	 *
	 */
	class Row {

		String id;
		IExpression expression;
		// not null if the row describes a specific observation or aggregates by it.
		IObservation observation;
		// there may be several titles, potentially nulls. The index in the array is the
		// level of
		// indentation.
		String[] titles;
		Map<String, Object> data = new HashMap<>();
		List<String> dependencies = new ArrayList<>();
		// if this is null, the row is a separator, potentially with titles and
		// formatting
		Aggregator[] aggregators;
		public Object group;
		public Object classifier;
		public Object filter;
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

	public TableDocumentationExtension(Map<?, ?> definition, IObservable target, IRuntimeScope scope) {

		this.definition = definition;
		this.scope = scope;
		this.target = target;
		parseColumns(definition.get("columns"));
		parseRows(definition.get("rows"));
		this.splits = parseSplits(definition.get("splits"));

		buildDependencies();

		// TODO check out locators. We may be splitting by temporal extent with no
		// comparison, splitting with
		// comparison, not splitting with comparison, not splitting with no comparison.
	}

	private void buildDependencies() {
		// TODO create dependency structure between rows and between columns. We don't
		// support cell definitions so no cell-scoped dependencies.

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
					Object group = map.get("group") instanceof IKimConcept
							? Concepts.INSTANCE.declare((IKimConcept) map.get("group"))
							: null;
					for (IConcept concept : expandClassifier(classifier, map.get("downto"))) {
						// add as many newColumns as needed
						Column column = newColumn(concept, map);
						column.group = group;
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

		if (theRest.get("target") instanceof IKimConcept) {
			IConcept trg = Concepts.INSTANCE.declare((IKimConcept) theRest.get("target"));
			if (trg != null && trg.is(Concepts.c(NS.CORE_AREA))) {
				ret.targetType = TargetType.AREA;
			} else if (trg != null && trg.is(Concepts.c(NS.CORE_DURATION))) {
				ret.targetType = TargetType.DURATION;
			} else if (trg != null && trg.is(Concepts.c(NS.CORE_COUNT))) {
				ret.targetType = TargetType.NUMEROSITY;
			}
		} else if (theRest.containsKey("target")) {
			throw new KlabValidationException("Table definition: unknown target: " + definition.get("target"));
		}

		if (theRest.get("compute") instanceof IKimExpression) {
			parseExpression((IKimExpression) theRest.get("compute"), ret);
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
			return Types.INSTANCE.getConcreteLeaves(declared);
		}
		return Collections.singletonList(declared);
	}

	private Row newRow(Object classifier, Map<?, ?> theRest) {

		Row ret = new Row();

		ret.classifier = classifier;
		ret.filter = theRest.get("filter");

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

		if (theRest.get("compute") instanceof IKimExpression) {
			parseExpression((IKimExpression) theRest.get("compute"), ret);
		}

		// add enough aggregators to match the number of non-empty columns.
		ret.aggregators = new Aggregator[this.nonEmptyColumns];
		return ret;
	}

	private void parseExpression(IKimExpression iKimExpression, Object ret) {
		// TODO compile expression; set dependencies

	}

	/*
	 * A row spec may turn into >1 rows
	 */
	private void parseRow(Map<?, ?> map) {

		if (map.containsKey("classifier")) {
			if (map.get("classifier") instanceof IKimConcept) {
				IConcept classifier = Concepts.INSTANCE.declare((IKimConcept) map.get("classifier"));
				if (classifier.is(Type.COUNTABLE)) {
					// TODO build columns based on the contextualized artifacts for the concept
				} else {
					for (IConcept concept : expandClassifier(classifier, map.get("downto"))) {
						// add as many newColumns as needed
						Row row = newRow(concept, map);
						row.group = map.get("group") instanceof IKimConcept
								? Concepts.INSTANCE.declare((IKimConcept) map.get("group"))
								: null;
						this.rows.put(row.id, row);
					}
				}
			} else {
				Row row = newRow(map.get("classifier"), map);
				row.group = map.get("group");
				this.rows.put(row.id, row);
			}
		} else {
			Row row = newRow(null, map);
			row.group = map.get("group");
			this.rows.put(row.id, row);
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

			/*
			 * these should be Phases, which may imply a scale
			 */
			for (View view : getViews()) {

				/**
				 * If !aggregate, we're at the first pass and accumulating values for comparison
				 */
				boolean aggregate = scaleIndex == (this.nScales - 1);

				/*
				 * foreach value in state being valued
				 */
				for (ILocator locator : view.states()) {

					/*
					 * If the target observable is not the target observation, check for area or
					 * duration
					 */
					/*
					 * foreach column in dependency order
					 */
					for (Column column : sortColumns()) {

						Object value = null; // TODO either the target observable or whatever other concept we are
						// looking at, area, duration of the timestep, attribute, whatever.
						switch (column.targetType) {
						case AREA:
							break;
						case DURATION:
							break;
						case NUMEROSITY:
							break;
						case QUALITY:
							break;
						default:
							break;
						}

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

	/**
	 * TODO make this getViews() instead. Each view
	 * 
	 * @return
	 */
	private Iterable<View> getViews() {

		IScale refScale = scope.getScale();

		if (locators.isEmpty()) {
			View ret = new View();
			ret.scale = (IScale) (refScale.isTemporallyDistributed()
					? refScale.at(refScale.getTime().getExtent(refScale.getTime().size() - 1))
					: refScale);
			return Collections.singletonList(ret);
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
