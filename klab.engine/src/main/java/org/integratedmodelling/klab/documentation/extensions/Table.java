package org.integratedmodelling.klab.documentation.extensions;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import com.google.common.collect.Sets;

public class Table extends ViewArtifact {

	enum AggregationType {
		Aggregator, Differentiator, Comparator, Counter
	};

	enum TargetType {
		AREA, DURATION, QUALITY, NUMEROSITY
	}

	/**
	 * A cell may contain a standard aggregator, a comparator, a differentiator or
	 * other accumulator of values.
	 * 
	 * @author Ferd
	 *
	 */
	class Cell {

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
	 * Each phase is a scan of the target observation. There is always at least one
	 * phase. If more than one, it's for comparison or selection of specific
	 * locators. If we're scanning a quality, we can iterate locators; otherwise we
	 * iterate observations. The phase can tell us what we're locating against and
	 * its own index in the set of phases.
	 * 
	 * @author Ferd
	 *
	 */
	class Phase {

		IScale scale;
		SpaceSelector space;
		TimeSelector time;
		int index;
		int total;

		boolean isLast() {
			return index == (total - 1);
		}

		/**
		 * Iterate over the primary target. If the target is a state, the returned pairs
		 * will contain the value we want and the correspondent locator. Otherwise, the
		 * value will be an observation and the locator will be its scale.
		 * 
		 * @return
		 */
		Iterable<Pair<Object, ILocator>> states() {
			if (scale != null) {
//				return scale;
			}
			return null;
		}
	}

	/**
	 * Selector of time points to use with spatially varying targets. If resolution
	 * != null, selects all timepoints at the resolution within the target range.
	 * Start and end select the first or last slice at the native resolution of the
	 * target.
	 * 
	 * @author Ferd
	 *
	 */
	class TimeSelector {
		boolean start;
		boolean end;
		Resolution resolution;
	}

	/**
	 * Not used for the time being.
	 * 
	 * @author Ferd
	 *
	 */
	class SpaceSelector {
		IShape shape;
	}

	/*
	 * Filter to select which dimensions are added to when scanning the target
	 * observations. One or more of the selectors can be non-null.
	 */
	static class Filter {

		public Filter(List<Object> classifiers) {
			// TODO
		}

		public Filter() {
			this.universal = true;
		}

		/*
		 * Universal filter matches everything except nodata. Specified by no spec,
		 * cleaner with an actual object.
		 */
		boolean universal = false;

		/**
		 * Time selector if we only accumulate values in specified times.
		 */
		TimeSelector timeSelector;

		/**
		 * If specified, the concept we use to filter and the key observable to find the
		 * correspondent observation in {@link #observations}. The key observable may be
		 * null if we take the values from the context, as when aggregating area or
		 * durations.
		 */
		Pair<ObservedConcept, ObservedConcept> observable;

		/**
		 * Range, which may also specify a relational constraint on any numeric target,
		 * including inequality.
		 */
		Range range;

		/**
		 * Complex filtering can be done with expressions.
		 */
		IExpression expression;

		boolean matches(Object value, ILocator locator) {
			if (universal) {
				return true;
			}
			// TODO
			return false;
		}
	}

	/**
	 * Represents both rows and columns. The index is the position of the cell
	 * (which is not necessarily computed in order).
	 * 
	 * @author Ferd
	 *
	 */
	static class Dimension {

		/**
		 * Mandatory id, either assigned in configuration or attributed using the scheme
		 * c<n> for columns and r<n> for rows.
		 */
		String id;

		/**
		 * If hidden, used for intermediate computations but not rendered.
		 */
		boolean hidden = false;

		/**
		 * Index of the correspondent cell in the dimension
		 */
		int index;

		/**
		 * Where the value we specify comes from. Null if computed or just coming from
		 * another dimension.
		 */
		ObservedConcept target;

		/**
		 * This tells us if we're scanning the actual values of the target or only the
		 * associated context metrics such as area occupied per category or counts.
		 */
		public TargetType targetType;

		/**
		 * Hierarchically arranged titles at the levels we need them. Nulls for
		 * intermediate levels that aren't specified.
		 */
		String[] titles;

		/**
		 * Filter that will define whether the value correspondent to dimension's
		 * aggregator is added to the cell or not.
		 */
		Filter filter;

		/**
		 * Aggregation type, if any.
		 */
		AggregationType aggregation = null;

		/**
		 * 
		 */
		IExpression computation;

		/**
		 * Any symbols used in computations, to compute dependencies.
		 */
		Set<String> symbols = new HashSet<>();

		boolean separator = false;

	}

	/**
	 * The needed observables so that the table can be computed. These are defined
	 * after configuration and not validated against the scope until the table is
	 * compiled, so the observations can be made in case the table <b>is</b> the
	 * primary query.
	 */
	private Set<ObservedConcept> observables = new HashSet<>();

	// columns in order of definition and expansion
	private Map<String, Dimension> columns = new LinkedHashMap<>();
	// rows in order of definition and expansion
	private Map<String, Dimension> rows = new LinkedHashMap<>();

	private List<Phase> phases = new ArrayList<>();

	/**
	 * The computed part of the table where things happen. Created after reading
	 * rows and columns. Contains a cell per active column/row combination, null if
	 * nothing has gotten in.
	 */
	private Cell[][] cells;

	/**
	 * All observations used in compiling the table. Harvested from the context
	 * during filter parsing. Keys are the same used in the filters.
	 */
	private Map<ObservedConcept, IObservation> observations = new HashMap<>();
	private IRuntimeScope scope;
	private IObservable target;
	private int activeColumns;
	private int activeRows;

	/**
	 * Return the passed dimensions in order of dependency. If circular dependencies
	 * are detected throw a validation exception as the definition is misconfigured.
	 * 
	 * @param dimensions
	 * @return
	 */
	private Iterable<Dimension> getSortedDimension(Map<String, Dimension> dimensions) {
		List<Dimension> ret = new ArrayList<>();
		Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);

		return ret;
	}

	/*
	 * ------- Definition and validation --------------------------
	 */

	public Table(Map<?, ?> definition, IObservable target, IRuntimeScope scope) {
		this.scope = scope;
		this.target = target;
		this.activeColumns = parseDimension(definition.get("columns"), this.columns, "c");
		this.activeRows = parseDimension(definition.get("rows"), this.rows, "r");
		this.cells = new Cell[activeColumns][activeRows];
	}

	private int parseDimension(Object object, Map<String, Dimension> dimensions, String namePrefix) {

		int ret = 0;

		if (object instanceof Collection) {
			for (Object o : ((Collection<?>) object)) {
				if (o instanceof Map) {
					ret += parseDimension((Map<?, ?>) o, dimensions, namePrefix);
				} else if (o instanceof String) {
					switch (o.toString()) {
					case "empty":
						break;
					// TODO others?
					}
				} else {
					throw new KlabValidationException(
							"table dimensions (rows and columns) must be specified as maps or lists of maps");
				}
			}
		} else if (object instanceof Map) {
			ret = parseDimension((Map<?, ?>) object, dimensions, namePrefix);
		} else {
			throw new KlabValidationException(
					"table dimensions (rows and columns) must be specified as maps or lists of maps");
		}

		return ret;
	}

	/*
	 * A classifier spec may turn into >1 classifiers. Return the number of ACTIVE
	 * dimensions built from the classifier list.
	 */
	private int parseDimension(Map<?, ?> map, Map<String, Dimension> dimensions, String namePrefix) {

		int ret = 0;

		for (Filter filter : expandClassifier(map.get("classifier"))) {
			Dimension dimension = newDimension(filter, map, namePrefix);
			dimensions.put(dimension.id, dimension);
			if (!dimension.separator) {
				ret++;
			}
		}
//		
//		if (map.containsKey("classifier")) {
//			if (map.get("classifier") instanceof IKimConcept) {
//				IConcept classifier = Concepts.INSTANCE.declare((IKimConcept) map.get("classifier"));
//				if (classifier.is(Type.COUNTABLE)) {
//					// TODO build columns based on the contextualized artifacts for the concept
//				} else {
//					Object group = map.get("group") instanceof IKimConcept
//							? Concepts.INSTANCE.declare((IKimConcept) map.get("group"))
//							: null;
//					for (IConcept concept : expandClassifier(classifier, map.get("downto"))) {
//						// add as many newColumns as needed
//						Dimension column = newDimension(concept, map);
////						column.group = group;
//						dimensions.put(column.id, column);
//					}
//				}
//			} else {
//				Dimension column = newDimension(map.get("classifier"), map);
////				column.group = map.get("group");
//				dimensions.put(column.id, column);
//			}
//		}

		return ret;
	}

	private Dimension newDimension(Filter filter, Map<?, ?> theRest, String namePrefix) {

		Dimension ret = new Dimension();

		ret.filter = filter;
		if (theRest.containsKey("name")) {
			ret.id = theRest.get("name").toString();
		} else {
			ret.id = namePrefix + (this.columns.size() + 1);
		}

		if (theRest.get("target") instanceof IKimConcept || theRest.get("target") instanceof IKimObservable) {
			IKimStatement tdef = (IKimStatement) theRest.get("target");
			IObservable trg = tdef instanceof IKimObservable
					? Observables.INSTANCE.declare((IKimObservable) tdef, scope.getMonitor())
					: Observable.promote(Concepts.INSTANCE.declare((IKimConcept) tdef));
			if (trg != null) {

				ret.target = new ObservedConcept(trg,
						trg.is(IKimConcept.Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);
				this.observables.add(ret.target);

				if (trg.is(Concepts.c(NS.CORE_AREA))) {
					ret.targetType = TargetType.AREA;
				} else if (trg.is(Concepts.c(NS.CORE_DURATION))) {
					ret.targetType = TargetType.DURATION;
				} else if (trg.is(Concepts.c(NS.CORE_COUNT))) {
					ret.targetType = TargetType.NUMEROSITY;
				}
			} else {
				throw new KlabValidationException(
						"Table definition: target: " + theRest.get("target") + " does not specify a known observable");
			}
		} else if (theRest.containsKey("target")) {
			throw new KlabValidationException("Table definition: unknown target: " + theRest.get("target"));
		}

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

		if (theRest.containsKey("separator")) {
			// TODO separator types for display
			ret.separator = true;
		}

		if (theRest.get("compute") instanceof IKimExpression) {
			IKimExpression expression = (IKimExpression) theRest.get("compute");
			ILanguageProcessor processor = Extensions.INSTANCE
					.getLanguageProcessor(expression.getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE
							: expression.getLanguage());
			Descriptor descriptor = processor.describe(expression.getCode(), scope.getExpressionContext(), false);
			ret.symbols.addAll(descriptor.getIdentifiersInScalarScope());
			ret.computation = descriptor.compile();
		}

		return ret;
	}

	private Collection<Filter> expandClassifier(Object declaration) {

		List<Filter> ret = new ArrayList<>();

		if (declaration == null) {
			ret.add(new Filter());
			return ret;
		}

		/*
		 * categories
		 */
		final int CATEGORY = 0;
		final int OBJECT = 1;
		final int EXPRESSION = 2;
		final int NUMERIC = 3;
		final int TIME = 4;
		final int SPACE = 5;

		Map<Integer, List<Object>> sorted = new HashMap<>();

		for (Object o : (declaration instanceof Collection ? (Collection<?>) declaration
				: Collections.singleton(declaration))) {
			if (o instanceof IKimConcept || o instanceof IKimObservable) {

				IObservable observable = o instanceof IKimObservable
						? Observables.INSTANCE.declare((IKimObservable) o, scope.getMonitor())
						: Observable.promote(Concepts.INSTANCE.declare((IKimConcept) o));
				if (observable == null) {
					throw new KlabValidationException(
							"Table definition: classifier: " + o + " does not specify a known observable");
				}

				if (observable.is(IKimConcept.Type.COUNTABLE)) {
					categorize(OBJECT, new ObservedConcept(observable, Mode.INSTANTIATION), sorted);
				} else if (observable.is(IKimConcept.Type.CLASS)) {
					for (ObservedConcept category : expandCategory(observable)) {
						categorize(CATEGORY, category, sorted);
					}
				} else if (observable.is(IKimConcept.Type.PREDICATE)) {
					for (ObservedConcept category : expandConcept(observable.getType(), observable)) {
						categorize(CATEGORY, category, sorted);
					}
				} else {
					throw new KlabValidationException("table: cannot classify on " + observable.getType()
							+ ": only categories (type of) and countables are valid classifiers");
				}

			} else if (o instanceof Range) {
				categorize(NUMERIC, o, sorted);
			} else if (o instanceof IKimExpression) {
				categorize(EXPRESSION, o, sorted);
			} else if (o instanceof Map) {
				// TODO space and time constraints: e.g (inside conservation:ProtectedArea)
			} else if (o instanceof List) {
				// TODO space and time constraints
			} else {
				switch (o.toString()) {
				case "start":
				case "end":
					categorize(TIME, o.toString(), sorted);
					break;
				}
			}
		}

		List<Set<Object>> joinable = new ArrayList<>();
		for (List<Object> list : sorted.values()) {
			joinable.add(new LinkedHashSet<>(list));
		}

		for (List<Object> combination : Sets.cartesianProduct(joinable)) {
			ret.add(new Filter(combination));
		}

		return ret;
	}

	private void categorize(int key, Object value, Map<Integer, List<Object>> sorted) {
		List<Object> list = sorted.get(key);
		if (list == null) {
			list = new ArrayList<>();
			sorted.put(key, list);
		}
		list.add(value);
	}

	public List<ObservedConcept> expandCategory(IObservable observable) {

		IConcept category = Observables.INSTANCE.getDescribedType(observable.getType());
		this.observables.add(new ObservedConcept(observable, Mode.RESOLUTION));
		return expandConcept(category, observable);
	}

	private List<ObservedConcept> expandConcept(IConcept category, IObservable observable) {

		List<ObservedConcept> ret = new ArrayList<>();

		/*
		 * TODO support down to, all and any in the observable, which may be null
		 */
		if (!category.isAbstract() && (observable == null || !(observable.isGeneric() || observable.isGlobal()))) {
			// just add the concept
			ret.add((new ObservedConcept(Observable.promote(category), Mode.RESOLUTION)));
			// add the reified base observable to those we need to have
			IConcept base = Observables.INSTANCE.getBaseObservable(category);
			if (base != null) {
				this.observables.add(new ObservedConcept(Observable.promote(base).getBuilder(scope.getMonitor())
						.as(UnarySemanticOperator.TYPE).buildObservable(), Mode.RESOLUTION));
			}
		} else {
			for (IConcept child : (observable != null && observable.isGlobal())
					? Types.INSTANCE.getConcreteChildren(category)
					: Types.INSTANCE.getConcreteLeaves(category)) {
				ret.add(new ObservedConcept(Observable.promote(child), Mode.RESOLUTION));
			}
		}
		return ret;
	}

	/**
	 * Compute all cells that want to be computed.
	 */
	@Override
	public void compute(IRuntimeScope scope) {

		/*
		 * Find all observations in scope and fill in the observation map
		 */

		// reset every active cell
		for (int col = 0; col < cells.length; col++) {
			for (int row = 0; row < cells[col].length; row++) {
				if (cells[col][row] != null) {
					cells[col][row].reset();
				}
			}
		}

		for (Phase phase : phases) {
			for (Pair<Object, ILocator> value : phase.states()) {
				for (Dimension column : getSortedDimension(columns)) {
					for (Dimension row : getSortedDimension(rows)) {
						if (cells[column.index][row.index] != null) {
							cells[column.index][row.index].accumulate(value.getFirst(), value.getSecond(), phase);
						}
					}
				}
			}
		}
	}

	/**
	 * Finalize all cells and render the result to HTML.
	 * 
	 * @return
	 */
	@Override
	public String compile() {

		StringBuffer ret = new StringBuffer(128 * rows.size() * columns.size());

		/*
		 * compute and render column headers in order of definition
		 */

		/*
		 * for each row, finalize the aggregator and render the cell
		 */

		return ret.toString();
	}

	@Override
	public void export(File file) {
		// TODO Auto-generated method stub

	}

}
