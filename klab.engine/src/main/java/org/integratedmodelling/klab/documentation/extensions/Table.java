package org.integratedmodelling.klab.documentation.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.documentation.extensions.TableDocumentationExtension.Column;
import org.integratedmodelling.klab.documentation.extensions.TableDocumentationExtension.TargetType;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class Table {

	enum AggregationType {
		Aggregator, Differentiator, Comparator, Counter
	};

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
	class Filter {

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
	class Dimension {

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
		 * 
		 */
		IExpression computation;

		/**
		 * Any symbols used in computations, to compute dependencies.
		 */
		Set<String> symbols = new HashSet<>();
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
		this.activeColumns = parseDimension(definition.get("columns"), this.columns);
		this.activeRows = parseDimension(definition.get("rows"), this.rows);
		this.cells = new Cell[activeColumns][activeRows];
	}

	private int parseDimension(Object object, Map<String, Dimension> dimensions) {

		int ret = 0;

		if (object instanceof Collection) {
			for (Object o : ((Collection<?>) object)) {
				if (o instanceof Map) {
					ret += parseDimension((Map<?, ?>) o, dimensions);
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
			parseDimension((Map<?, ?>) object, dimensions);
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
	private int parseDimension(Map<?, ?> map, Map<String, Dimension> dimensions) {

		int ret = 0;
		
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
						Dimension column = newDimension(concept, map);
//						column.group = group;
						dimensions.put(column.id, column);
					}
				}
			} else {
				Dimension column = newDimension(map.get("classifier"), map);
//				column.group = map.get("group");
				dimensions.put(column.id, column);
			}
		}
		
		return ret;
	}

	private Dimension newDimension(Object classifier, Map<?, ?> theRest) {

		Dimension ret = new Dimension();
//		ret.classifier = Classifier.create(classifier);
//
//		if (classifier instanceof IConcept && theRest.containsKey("qualifier")) {
//			Object label = ((IConcept) classifier).getMetadata().get(theRest.get("qualifier"));
//			ret.label = label == null ? null : label.toString();
//		}
//		if (theRest.containsKey("name")) {
//			ret.id = theRest.get("name").toString();
//		} else {
//			ret.id = "c" + (this.columns.size() + 1);
//		}
//
//		if (theRest.get("target") instanceof IKimConcept) {
//			IConcept trg = Concepts.INSTANCE.declare((IKimConcept) theRest.get("target"));
//			if (trg != null && trg.is(Concepts.c(NS.CORE_AREA))) {
//				ret.targetType = TargetType.AREA;
//			} else if (trg != null && trg.is(Concepts.c(NS.CORE_DURATION))) {
//				ret.targetType = TargetType.DURATION;
//			} else if (trg != null && trg.is(Concepts.c(NS.CORE_COUNT))) {
//				ret.targetType = TargetType.NUMEROSITY;
//			}
//		} else if (theRest.containsKey("target")) {
//			throw new KlabValidationException("Table definition: unknown target: " + definition.get("target"));
//		}
//
//		if (theRest.get("compute") instanceof IKimExpression) {
//			parseExpression((IKimExpression) theRest.get("compute"), ret);
//		}

		return ret;
	}

	private void parseExpression(IKimExpression iKimExpression, Column ret) {
		// TODO Auto-generated method stub

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

	/**
	 * Compute all cells that want to be computed.
	 */
	public void compute(IRuntimeScope scope) {

		/*
		 * Prime all cells by resetting their aggregators
		 */

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

}
