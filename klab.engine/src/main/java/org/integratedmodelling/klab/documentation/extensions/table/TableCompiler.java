package org.integratedmodelling.klab.documentation.extensions.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.data.general.IExpression.Context;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IViewModel.Schedule;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;
import org.jgrapht.Graph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Sets;

/**
 * A class that interprets a table view definition and produces a table artifact
 * when requested.
 * 
 * @author Ferd
 *
 */
public class TableCompiler {

	enum AggregationType {
		Aggregator, Differentiator, Comparator, Counter
	};

	enum TargetType {
		AREA, DURATION, QUALITY, NUMEROSITY
	}

	enum Style {
		RIGHT, LEFT, CENTER, BOLD, ITALIC, BG_HIGHLIGHT, FG_HIGHLIGHT
	}

	enum ComputationType {

		/**
		 * Sum
		 */
		Sum(Aggregation.SUM),
		/**
		 * Sum
		 */
		Average(Aggregation.MEAN),
		/**
		 * Sum
		 */
		Std(Aggregation.STD),
		/**
		 * Sum
		 */
		Variance(Aggregation.VARIANCE),
		/**
		 * Sum
		 */
		Min(Aggregation.MIN),
		/**
		 * Sum
		 */
		Max(Aggregation.MAX),
		/**
		 * Compute a specific expression in the expression field, to be evaluated at
		 * each pass and whose result is aggregated.
		 */
		Expression(null),
		/**
		 * Compute an expression at the end of everything and only once, based on
		 * results in other cells.
		 */
		Summarize(null);

		Aggregation aggregation;

		ComputationType(Aggregation aggregation) {
			this.aggregation = aggregation;
		}

		public Aggregation getAggregation() {
			return this.aggregation;
		}

		public boolean isAggregation() {
			return this.aggregation != null || this == Summarize;
		}
	}

	/**
	 * Each phase is a scan of the target observation. There is always at least one
	 * phase. If more than one, it's for comparison or selection of specific
	 * locators. If we're scanning a quality, we can iterate locators; otherwise we
	 * iterate observations. The phase can tell us what we're locating against and
	 * its own index in the set of phases.
	 * <p>
	 * For now the recognized phases are only temporal, but there's no reason why
	 * they couldn't be spatial or logical, or more complex with separate
	 * contextualization for different objects.
	 * 
	 * @author Ferd
	 *
	 */
	class Phase {

		private IScale scale;
		private Set<Object> classifiers = new HashSet<>();
		private int index;
		private int total;
		private IArtifact observation;
		// each phase has a key that allows referencing of cell values as self@key
		// whenever >1 phases are seen by a cell.
		private String key;

		public Phase(IScale scale, Object... classifiers) {
			this.scale = scale;
			if (classifiers != null) {
				for (Object c : classifiers) {
					this.classifiers.add(c);
				}
			}
		}

		public Phase(IArtifact group) {
			this.observation = group;
			this.total = group.groupSize();
		}

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
		Iterable<Pair<Object, ILocator>> states(final IObservation target) {
			if (target instanceof IState && scale != null) {
				return new Iterable<Pair<Object, ILocator>>() {

					Iterator<ILocator> it = scale.iterator();

					@Override
					public Iterator<Pair<Object, ILocator>> iterator() {
						return new Iterator<Pair<Object, ILocator>>() {

							@Override
							public boolean hasNext() {
								return it.hasNext();
							}

							@Override
							public Pair<Object, ILocator> next() {
								ILocator locator = it.next();
								return new Pair<>(((IState) target).get(locator), locator);
							}

						};
					}
				};

			} else if (observation != null) {
				return new Iterable<Pair<Object, ILocator>>() {

					@Override
					public Iterator<Pair<Object, ILocator>> iterator() {
						return new Iterator<Pair<Object, ILocator>>() {

							Iterator<IArtifact> it = observation.iterator();

							@Override
							public Pair<Object, ILocator> next() {
								IArtifact o = it.next();
								return new Pair<>(o, ((IObservation) o).getScale());
							}

							@Override
							public boolean hasNext() {
								return it.hasNext();
							}
						};
					}

				};
			}
			return null;
		}

		public int getIndex() {
			return index;
		}

		public boolean matches(TimeSelector timeSelector) {
			for (Object c : this.classifiers) {
				if (c instanceof String) {
					switch (c.toString()) {
					case "init":
						return timeSelector.init;
					case "start":
						return timeSelector.start;
					case "end":
						return timeSelector.end;
					case "time":
						return true;
					}
				}
			}
			return false;
		}

		public String getKey() {
			return key;
		}

		public Phase setKey(String key) {
			this.key = key;
			return this;
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
		boolean init;
		Resolution resolution;
		private String displayLabel;

		TimeSelector(Object o) {
			if (o instanceof IKimQuantity) {
				this.resolution = Time.resolution((IKimQuantity) o);
			} else {
				this.start = "start".equals(o);
				this.init = "init".equals(o);
				this.end = "end".equals(o);
			}
		}

		public String getDisplayLabel(IRuntimeScope scope) {
			if (this.displayLabel == null) {
				if (this.init) {
					// TODO check use of root subject. Should use target artifact but it's hard from
					// this call chain.
					this.displayLabel = "before "
							+ Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
									scope.getRootSubject().getScale().getTime().getResolution());
				} else if (this.start) {
					this.displayLabel = Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
							scope.getRootSubject().getScale().getTime().getResolution());
				} else if (this.end) {
					this.displayLabel = "after "
							+ Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getEnd(),
									scope.getRootSubject().getScale().getTime().getResolution());
				}
			}
			return this.displayLabel;
		}
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

		/*
		 * Universal filter matches everything except nodata. Specified by no spec,
		 * cleaner with an actual object.
		 */
		boolean universal = false;

		/**
		 * The observation we're filtering when using . For expression filters, we may
		 * be filtering more than one, using the localized names in the context at the
		 * current locator.
		 */
		ObservedConcept target = null;

		/**
		 * Time selector if we only accumulate values in specified times, matching the
		 * classifier in the phase.
		 */
		TimeSelector timeSelector;

		/**
		 * Classifier to match any located value of our target.
		 */
		IClassifier classifier;

		/**
		 * Complex filtering can be done with expressions.
		 */
		IExpression expression;

		private Object targetType;

		// match an exact observation to the dimension we belong to. Only set before
		// computation.
		private String objectId;

		// if this is not null, the filter signals that the dimension it belongs to must
		// be
		// be multiplied by the objects in the target and the filter must be turned into
		// a
		// object match. Not used for filtering.
		public ObservedConcept objectFilter;

		public Filter() {
			this.universal = true;
		}

		public Filter(String artifactId) {
			this.objectId = artifactId;
		}

		boolean matches(Map<ObservedConcept, IObservation> catalog, ILocator locator, Phase phase, Object currentState,
				IContextualizationScope scope) {
			if (universal) {
				return true;
			}
			if (this.timeSelector != null) {
				if (phase == null || !phase.matches(this.timeSelector)) {
					return false;
				}
			}
			if (classifier != null) {
				if (target != null) {
					IObservation observation = catalog.get(target);
					if (observation instanceof IState) {
						currentState = ((IState) observation).get(locator);
					}
				}
				if (!classifier.classify(currentState, scope)) {
					return false;
				}
			}

			return true;
		}
	}

	enum DimensionType {
		ROW, COLUMN, SPLIT
	}

	/**
	 * Represents rows, columns and (later) splits. The index is the position of the
	 * cell (which is not necessarily computed in order).
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
		 * Only used to validate use of cross-references, as rows can only refer to
		 * cells in the same row (i.e. use column references) and the other way around.
		 * 
		 */
		DimensionType dimensionType;

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
		 * This is associated to the target and will determine the default for
		 * non-existing columns. FIXME should have no default - this is a shortcut for
		 * now.
		 */
		public IArtifact.Type dataType = IArtifact.Type.NUMBER;

		/**
		 * Hierarchically arranged titles at the levels we need them. Nulls for
		 * intermediate levels that aren't specified.
		 */
		String[] titles;

		/**
		 * Filters that will define whether the value correspondent to dimension's
		 * aggregator is added to the cell or not. May be empty, never null.
		 */
		List<Filter> filters;

		/**
		 * Classifiers are like filters but they don't prevent the addition of
		 * non-matching values. May be empty, never null.
		 */
		List<Filter> classifiers;

		/**
		 * Aggregation type, if any.
		 */
		AggregationType aggregation = null;

		/**
		 * Computation type may require the use of specific formulas or defer
		 * calculation to a second pass for row/column totals or other statistics. Rows
		 * with expressions are computed in order of dependency; rows of sums or other
		 * aggregation must always be computed after all others.
		 */
		ComputationType computationType = null;

		/**
		 * Expression is assigned on parsing, the actual compilation is done before
		 * calculating when we have a scope.
		 */
		IKimExpression expression;
		IExpression computation;

		/**
		 * Any symbols used in computations, to compute dependencies.
		 */
		Set<String> symbols = new HashSet<>();

		/**
		 * Styles if any were specified
		 */
		Set<Style> style = new HashSet<>();

		private Map<String, Set<String>> phaseReferences = new HashMap<>();

		private boolean referencesStates;

		private boolean referencesPhases;

		public Dimension(Dimension dim) {
			this.aggregation = dim.aggregation;
			this.computation = dim.computation;
			this.expression = dim.expression;
			this.dimensionType = dim.dimensionType;
			this.computationType = dim.computationType;
			if (dim.filters != null) {
				for (Filter filter : dim.filters) {
					if (this.filters == null) {
						this.filters = new ArrayList<>();
					}
					this.filters.add(filter);
				}
			}
			if (dim.classifiers != null) {
				for (Filter filter : dim.classifiers) {
					if (this.classifiers == null) {
						this.classifiers = new ArrayList<>();
					}
					this.classifiers.add(filter);
				}
			}
			this.hidden = dim.hidden;
			this.id = dim.id;
			this.titles = dim.titles;
			this.target = dim.target;
			this.targetType = dim.targetType;
			this.separator = dim.separator;
			this.style.addAll(dim.style);
			this.symbols.addAll(dim.symbols);
			this.referencesPhases = dim.referencesPhases;
			this.referencesStates = dim.referencesStates;
			this.dataType = dim.dataType;
			this.phaseReferences.putAll(dim.phaseReferences);

		}

		public Dimension() {
			// TODO Auto-generated constructor stub
		}

		public IExpression getExpression(IRuntimeScope scope) {
			if (expression != null && computation == null) {
				ILanguageProcessor processor = Extensions.INSTANCE
						.getLanguageProcessor(expression.getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE
								: expression.getLanguage());
				Context context = scope.getExpressionContext();

				// register row and column names unless the rows/colums are aggregations
				for (Dimension dimension : rows.values()) {
					if (dimension.computationType == null || !dimension.computationType.isAggregation()) {
						context.addKnownIdentifier(dimension.id, IKimConcept.Type.QUALITY);
					}
				}
				for (Dimension dimension : columns.values()) {
					if (dimension.computationType == null || !dimension.computationType.isAggregation()) {
						context.addKnownIdentifier(dimension.id, IKimConcept.Type.QUALITY);
					}
				}

				Descriptor descriptor = processor.describe(expression.getCode(), context,
						CompilerOption.RecontextualizeAsMap, CompilerOption.IgnoreContext);
				for (String symbol : descriptor.getIdentifiersInScalarScope()) {
					if (this.dimensionType == DimensionType.ROW && columns.containsKey(symbol)) {
						throw new KlabValidationException(
								"row formulas cannot access values in other columns: only same column values in other rows can be referenced");
					}
					if (this.dimensionType == DimensionType.COLUMN && rows.containsKey(symbol)) {
						throw new KlabValidationException(
								"column formulas cannot access values in other rows: only same row values in other columns can be referenced");
					}
					if (columns.containsKey(symbol) || rows.containsKey(symbol)) {
						this.symbols.add(symbol);
					}
				}
				this.computation = descriptor.compile();
				/*
				 * TODO 1. if the expr has only scalars for self/ctx names, will eval and
				 * accumulate the result; 2. if it has non-scalars for self/ctx, will accumulate
				 * normally and then compute and set the value; 3. if keys contain
				 * start/end/init, it will only compute at last phase.
				 */

				/*
				 * if this is true, we must accumulate the evaluated value of the expression
				 */
				this.phaseReferences.putAll(descriptor.getMapIdentifiers());

				for (String ref : descriptor.getIdentifiersInScalarScope()) {
					if ("value".equals(ref) || scope.getArtifact(ref) instanceof IState) {
						this.referencesStates = true;
						break;
					}
				}

				/*
				 * if this is true, we accumulate the phase values and only evaluate the
				 * expression at the end
				 */
				for (String ref : phaseReferences.keySet()) {
					if ("value".equals(ref) || scope.getArtifact(ref) instanceof IState) {
						this.referencesPhases = true;
						if (!phaseItems.containsAll(phaseReferences.get(ref))) {
							throw new KlabValidationException(
									"table expression references unknown phases in @ locator");
						}
					}
				}
			}

			return computation;

		}

		boolean separator = false;

		/**
		 * True if all filters match, or there are no filters (unless it's a separator).
		 * This should compute as quickly as possible.
		 * 
		 * @param catalog
		 * @param second
		 * @param phase
		 * @param currentState the primary target's value - may be a value or a direct
		 *                     observation according to what we're computing against.
		 * @return
		 */
		public boolean isActive(Map<ObservedConcept, IObservation> catalog, ILocator locator, Phase phase,
				Object currentState, IContextualizationScope scope) {

			if (this.separator) {
				return false;
			}
			if (this.filters != null) {
				for (Filter filter : this.filters) {
					if (!filter.matches(catalog, locator, phase, currentState, scope)) {
						return false;
					}
				}
			}

			return true;
		}

		/**
		 * For each one of the observations in this group, make a copy of this with the
		 * filter that has the objectFilter matching that specific observation.
		 * 
		 * @param group
		 * @return
		 */
		public Collection<Dimension> propagateFilteredObservable(ObservationGroup group) {
			List<Dimension> ret = new ArrayList<>();
			int i = 0;
			for (IArtifact artifact : group) {
				Dimension newDim = this.copy(artifact.getId(), i++);
				ret.add(newDim);
			}
			return ret;
		}

		private Dimension copy(String artifactId, int index) {
			Dimension ret = new Dimension();
			ret.aggregation = this.aggregation;
			ret.computation = this.computation;
			ret.computationType = this.computationType;
			ret.expression = this.expression;
			ret.dimensionType = this.dimensionType;
			if (this.filters != null) {
				for (Filter filter : this.filters) {
					if (filter.objectFilter == null) {
						ret.filters.add(filter);
					} else {
						ret.filters.add(new Filter(artifactId));
					}
				}
			}
			if (this.classifiers != null) {
				for (Filter filter : this.classifiers) {
					if (filter.objectFilter == null) {
						ret.classifiers.add(filter);
					} else {
						ret.classifiers.add(new Filter(artifactId));
					}
				}
			}
			ret.hidden = this.hidden;
			ret.id = this.id + "_" + index;
			ret.titles = this.titles;
			ret.target = this.target;
			ret.targetType = this.targetType;
			ret.separator = this.separator;
			ret.symbols.addAll(this.symbols);
			ret.style.addAll(this.style);
			ret.referencesPhases = this.referencesPhases;
			ret.referencesStates = this.referencesStates;
			ret.phaseReferences.putAll(this.phaseReferences);
			ret.dataType = this.dataType;

			return ret;
		}

		public Object getDefault() {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	/**
	 * The needed observables so that the table can be computed. These are defined
	 * after configuration and not validated against the scope until the table is
	 * compiled, so the observations can be made in case the table <b>is</b> the
	 * primary query.
	 */
	private Set<ObservedConcept> observables = new HashSet<>();

	//
	private List<Dimension> splits = new ArrayList<>();
	// columns in order of definition and expansion
	private Map<String, Dimension> columns = new LinkedHashMap<>();
	// rows in order of definition and expansion
	private Map<String, Dimension> rows = new LinkedHashMap<>();
	private ObservedConcept target;
	private int activeColumns;
	private int activeRows;
	private IMonitor monitor;
	private Set<Object> phaseItems = new HashSet<>();
	private TargetType targetType;
	private String name;
	private String title;
	private String label;
	private Set<String> harvestedTimeSelectors = new HashSet<>();
	private Schedule schedule;

	/**
	 * Return the passed dimensions in order of dependency. If circular dependencies
	 * are detected throw a validation exception as the definition is misconfigured.
	 * 
	 * @param dimensions
	 * @return
	 */
	private List<Dimension> getSortedDimension(Map<String, Dimension> dimensions,
			Map<ObservedConcept, IObservation> catalog, IRuntimeScope scope) {
		List<Dimension> ret = new ArrayList<>();

		List<Dimension> originalDims = new ArrayList<>();
		for (Dimension dim : dimensions.values()) {

			IObservation group = null;
			ObservedConcept countable = null;
			if (dim.filters != null) {
				for (Filter filter : dim.filters) {
					if (filter.objectFilter != null) {
						/*
						 * expand any dimension that contains an object filter into a dimension per
						 * filtered object, which we can only know in the scope.
						 */
						if (countable == null) {
							countable = filter.objectFilter;
							group = catalog.get(countable);
						} else if (!countable.equals(filter.objectFilter)) {
							throw new KlabValidationException(
									"cannot aggregate by more than one object type in the same dimension");
						}

						if (group == null) {
							continue;
						}
						if (!(group instanceof ObservationGroup)) {
							throw new KlabValidationException(
									"cannot aggregate by a direct artifact that does not resolve to an observation group");
						}

						originalDims.addAll(dim.propagateFilteredObservable((ObservationGroup) group));

						// continue to catch error
					}
				}
			}

			if (countable == null) {
				originalDims.add(new Dimension(dim));
			}
		}

		Map<String, Dimension> dictionary = new HashMap<>();
		Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		int i = 0;
		for (Dimension dimension : originalDims) {
			// reindex before sorting
			dimension.index = i++;
			dictionary.put(dimension.id, dimension);
			graph.addVertex(dimension.id);
			if (dimension.getExpression(scope) != null) {
				for (String s : dimension.symbols) {
					if (dimensions.containsKey(s)) {
						graph.addVertex(s);
						graph.addEdge(s, dimension.id);
					}
				}
			}
		}

		CycleDetector<String, DefaultEdge> cycles = new CycleDetector<>(graph);
		if (cycles.detectCycles()) {
			throw new KlabValidationException(
					"table: expressions in rows or columns contain circular dependencies between "
							+ cycles.findCycles());
		}
		TopologicalOrderIterator<String, DefaultEdge> sort = new TopologicalOrderIterator<>(graph);
		while (sort.hasNext()) {
			ret.add(dictionary.get(sort.next()));
		}

		return ret;
	}

	/*
	 * ------- Definition and validation --------------------------
	 */
	public TableCompiler(String name, Map<?, ?> definition, @Nullable IObservable target, IMonitor monitor) {

		this.name = name;
		this.monitor = monitor;
		Pair<ObservedConcept, TargetType> tdesc = parseTarget(definition.get("target"));
		this.target = tdesc.getFirst();
		this.targetType = tdesc.getSecond();
		this.activeColumns = parseDimension(definition.get("columns"), this.columns, DimensionType.COLUMN);
		this.activeRows = parseDimension(definition.get("rows"), this.rows, DimensionType.ROW);
		this.title = definition.containsKey("title") ? definition.get("title").toString() : null;
		this.label = definition.containsKey("label") ? definition.get("label").toString() : null;

		/*
		 * validate that only rows OR columns have an additional target but not both.
		 * Cells must aggregate a single observable.
		 */
		int targeted = 0;
		for (Dimension dim : columns.values()) {
			if (dim.target != null) {
				targeted++;
				break;
			}
		}
		for (Dimension dim : rows.values()) {
			if (dim.target != null) {
				targeted++;
				break;
			}
		}
		if (targeted > 1) {
			throw new KlabValidationException(
					"table: only rows or columns may define a target observable, but not both");
		}

		compileSchedule(definition);

	}

	private void compileSchedule(Map<?, ?> definition) {
		/*
		 * infer using any harvested temporal classifiers, adding any that were
		 * specified in the 'when' parameter
		 */
		if (definition.containsKey("when")) {
			for (Object cls : definition.get("when") instanceof Collection ? (Collection<?>) definition.get("when")
					: Collections.singleton(definition.get("when"))) {
				if ("time".equals(cls) || "start".equals(cls) || "end".equals(cls) || "init".equals(cls)) {
					harvestedTimeSelectors.add(cls.toString());
					phaseItems.add(cls.toString());
				} else {
					throw new KlabValidationException(
							"table: temporal classifier " + cls + " not undestood or supported");
				}
			}
		}

		if (!harvestedTimeSelectors.isEmpty()) {

			this.schedule = new Schedule() {

				@Override
				public boolean isStart() {
					return harvestedTimeSelectors.contains("start");
				}

				@Override
				public boolean isInit() {
					return harvestedTimeSelectors.contains("init");
				}

				@Override
				public boolean isEnd() {
					return harvestedTimeSelectors.contains("end");
				}

				@Override
				public boolean isTemporal() {
					return harvestedTimeSelectors.contains("time");
				}

				@Override
				public Resolution getResolution() {
					// TODO Auto-generated method stub
					return null;
				}

			};
		}

	}

	public Collection<ObservedConcept> getObservables() {
		return observables;
	}

	private int parseDimension(Object object, Map<String, Dimension> dimensions, DimensionType type) {

		int ret = 0;

		if (object instanceof Collection) {
			for (Object o : ((Collection<?>) object)) {
				if (o instanceof Map) {
					ret += parseDimension((Map<?, ?>) o, dimensions, type);
				} else if (o instanceof String) {
					switch (o.toString()) {
					case "empty":
						Dimension empty = new Dimension();
						empty.separator = true;
						empty.id = "s" + dimensions.size();
						dimensions.put(empty.id, empty);
						break;
					// TODO others?
					}
				} else {
					throw new KlabValidationException(
							"table dimensions (rows and columns) must be specified as maps or lists of maps");
				}
			}
		} else if (object instanceof Map) {
			ret = parseDimension((Map<?, ?>) object, dimensions, type);
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
	private int parseDimension(Map<?, ?> map, Map<String, Dimension> dimensions, DimensionType type) {

		int ret = 0;

		Pair<ObservedConcept, TargetType> target = parseTarget(map.get("target"));
		Object classifiers = map.containsKey("filter") ? map.get("filter") : map.get("classifier");
		boolean isClassifier = map.containsKey("classifier");
		for (List<Filter> filters : expandClassifier(target.getFirst(), target.getSecond(), classifiers)) {
			Dimension dimension = newDimension(target.getFirst(), target.getSecond(), filters, map, type,
					dimensions.size(), isClassifier);
			dimensions.put(dimension.id, dimension);
			if (!dimension.separator) {
				ret++;
			}
		}
		return ret;
	}

	private Pair<ObservedConcept, TargetType> parseTarget(Object object) {

		ObservedConcept target = null;
		TargetType targetType = null;

		if (object instanceof IKimConcept || object instanceof IKimObservable) {
			IKimStatement tdef = (IKimStatement) object;
			IObservable trg = tdef instanceof IKimObservable
					? Observables.INSTANCE.declare((IKimObservable) tdef, monitor)
					: Observable.promote(Concepts.INSTANCE.declare((IKimConcept) tdef));
			if (trg != null) {

				target = new ObservedConcept(trg,
						trg.is(IKimConcept.Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);

				if (trg.getType().equals(Concepts.c(NS.CORE_AREA))) {
					targetType = TargetType.AREA;
				} else if (trg.getType().equals(Concepts.c(NS.CORE_DURATION))) {
					targetType = TargetType.DURATION;
				} else if (trg.getType().equals(Concepts.c(NS.CORE_COUNT))) {
					targetType = TargetType.NUMEROSITY;
				} else {
					this.observables.add(target);
					targetType = trg.is(Type.QUALITY) ? TargetType.QUALITY : null;
				}
			} else {
				throw new KlabValidationException(
						"Table definition: target: " + object + " does not specify a known observable");
			}
		} else if (object != null) {
			throw new KlabValidationException("Table definition: unknown target: " + object);
		}

		return new Pair<>(target, targetType);

	}

	private Dimension newDimension(ObservedConcept target, TargetType targetType, List<Filter> filters,
			Map<?, ?> theRest, DimensionType type, int lastIndex, boolean filtersAreClassifiers) {

		Dimension ret = new Dimension();

		ret.target = target;
		ret.targetType = targetType;
		ret.classifiers = filtersAreClassifiers ? filters : new ArrayList<>();
		ret.filters = filtersAreClassifiers ? new ArrayList<>() : filters;
		ret.dimensionType = type;

		if (theRest.containsKey("name")) {
			ret.id = theRest.get("name").toString();
		} else {
			ret.id = (type == DimensionType.ROW ? "r" : "c") + (lastIndex + 1);
		}

		if (theRest.containsKey("title")) {
			List<String> titles = new ArrayList<>();
			if (theRest.get("title") instanceof Collection) {
				for (Object o : ((Collection<?>) theRest.get("title"))) {
					titles.add(o == null ? "" : o.toString());
				}
			} else {
				String theTitle = theRest.get("title").toString();
				// count the pound signs in front to establish level
				for (; theTitle.startsWith("#");) {
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

		if (theRest.containsKey("hidden")) {
			if (!(theRest.get("hidden") instanceof Boolean)) {
				throw new KlabValidationException("the 'hidden' parameter only admits true/false values");
			}
			ret.hidden = (Boolean)theRest.get("hidden");
		}

		
		if (theRest.containsKey("style")) {
			for (Object style : (theRest.get("style") instanceof Collection ? ((Collection<?>) theRest.get("style"))
					: Collections.singleton(theRest.get("style")))) {
				switch (style.toString()) {
				case "bold":
					ret.style.add(Style.BOLD);
					break;
				case "italic":
					ret.style.add(Style.ITALIC);
					break;
				case "right":
					ret.style.add(Style.RIGHT);
					break;
				case "left":
					ret.style.add(Style.LEFT);
					break;
				case "center":
					ret.style.add(Style.CENTER);
					break;
				case "bg_highlight":
					ret.style.add(Style.BG_HIGHLIGHT);
					break;
				case "fg_highlight":
					ret.style.add(Style.FG_HIGHLIGHT);
					break;
				default:
					throw new KlabValidationException("table: unrecognized style " + style);
				}
			}
		}

		if (theRest.get("compute") instanceof IKimExpression) {
			ret.expression = (IKimExpression) theRest.get("compute");
			ret.computationType = ComputationType.Expression;
		} else if (theRest.get("summarize") instanceof IKimExpression) {
			ret.expression = (IKimExpression) theRest.get("summarize");
			ret.computationType = ComputationType.Summarize;
		} else if (theRest.containsKey("summarize")) {
			switch (theRest.get("summarize").toString()) {
			case "sum":
				ret.computationType = ComputationType.Sum;
				break;
			case "average":
				ret.computationType = ComputationType.Average;
				break;
			case "variance":
				ret.computationType = ComputationType.Variance;
				break;
			case "std":
				ret.computationType = ComputationType.Std;
				break;
			case "min":
				ret.computationType = ComputationType.Min;
				break;
			case "max":
				ret.computationType = ComputationType.Max;
				break;
			default:
				throw new KlabValidationException("unrecognized symbol in computation: " + theRest.get("compute"));
			}
		}

		return ret;
	}

	private Collection<List<Filter>> expandClassifier(ObservedConcept target, TargetType targetType,
			Object declaration) {

		List<List<Filter>> ret = new ArrayList<>();

		if (declaration == null) {
			ret.add(new ArrayList<>());
			return ret;
		}

		if (declaration instanceof Map) {
			for (Entry<?, ?> entry : ((Map<?, ?>) declaration).entrySet()) {
				if ("default".equals(entry.getKey())) {
					expandClassifiers(target, targetType, declaration, ret);
				} else {
					Pair<ObservedConcept, TargetType> classifierTarget = parseTarget(entry.getKey());
					expandClassifiers(classifierTarget.getFirst(), classifierTarget.getSecond(), declaration, ret);
				}
			}
		} else {
			expandClassifiers(target, targetType, declaration, ret);
		}

		return ret;
	}

	private void expandClassifiers(ObservedConcept target, TargetType targetType, Object declaration,
			List<List<Filter>> ret) {
		Map<Integer, List<Object>> sorted = new HashMap<>();

		/*
		 * categories
		 */
		final int CATEGORY = 0;
		final int OBJECT = 1;
		final int EXPRESSION = 2;
		final int NUMERIC = 3;
		final int TIME = 4;
		final int SPACE = 5;

		for (Object o : (declaration instanceof Collection ? (Collection<?>) declaration
				: Collections.singleton(declaration))) {
			if (o instanceof IKimConcept || o instanceof IKimObservable) {

				IObservable observable = o instanceof IKimObservable
						? Observables.INSTANCE.declare((IKimObservable) o, monitor)
						: Observable.promote(Concepts.INSTANCE.declare((IKimConcept) o));
				if (observable == null) {
					throw new KlabValidationException(
							"Table definition: classifier: " + o + " does not specify a known observable");
				}

				if (observable.is(IKimConcept.Type.COUNTABLE)) {
					categorize(OBJECT, new ObservedConcept(observable, Mode.INSTANTIATION), sorted, null);
				} else if (observable.is(IKimConcept.Type.CLASS)) {
					for (ObservedConcept category : expandCategory(observable)) {
						categorize(CATEGORY, category, sorted, observable);
					}
				} else if (observable.is(IKimConcept.Type.PREDICATE)) {
					for (ObservedConcept category : expandConcept(observable.getType(), observable)) {
						categorize(CATEGORY, category, sorted, observable);
					}
				} else {
					throw new KlabValidationException("table: cannot classify on " + observable.getType()
							+ ": only categories (type of) and countables are valid classifiers");
				}

			} else if (o instanceof Range) {
				categorize(NUMERIC, o, sorted, null);
			} else if (o instanceof IKimExpression) {
				categorize(EXPRESSION, o, sorted, null);
			} else if (o instanceof Map) {
				// TODO space and time constraints: e.g (inside conservation:ProtectedArea)
			} else if (o instanceof List) {
				// TODO space and time constraints
			} else if (o instanceof IKimQuantity) {
				// TODO time or space resolutions; add to phase items
			} else {
				switch (o.toString()) {
				case "start":
				case "end":
				case "init":
					phaseItems.add(o.toString());
					categorize(TIME, o.toString(), sorted, null);
					break;
				}
			}
		}

		List<Set<Object>> joinable = new ArrayList<>();
		for (List<Object> list : sorted.values()) {
			joinable.add(new LinkedHashSet<>(list));
		}

		for (List<Object> combination : Sets.cartesianProduct(joinable)) {
			ret.add(compileFilters(target, targetType, combination));
		}
	}

	private void categorize(int key, Object value, Map<Integer, List<Object>> sorted, IObservable target) {
		List<Object> list = sorted.get(key);
		if (list == null) {
			list = new ArrayList<>();
			sorted.put(key, list);
		}
		list.add(target == null ? value : new Pair<Object, ObservedConcept>(value, new ObservedConcept(target)));
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
				this.observables.add(new ObservedConcept(
						Observable.promote(base).getBuilder(monitor).as(UnarySemanticOperator.TYPE).buildObservable(),
						Mode.RESOLUTION));
			}
		} else {
			for (IConcept child : /*
									 * (observable != null && observable.isGlobal()) ?
									 */Types.INSTANCE
					.getConcreteChildren(category)/*
													 * : Types.INSTANCE.getConcreteLeaves(category)
													 */) {
				ret.add(new ObservedConcept(Observable.promote(child), Mode.RESOLUTION));
			}
		}
		return ret;
	}

	/**
	 * Compute all cells that want to be computed. Target comes from caller, if null
	 * we must have an observable and find it in the catalog.
	 */
	public TableArtifact compute(IObservation targetObservation, IRuntimeScope scope) {

		scope.getMonitor().info("start computing table " + name);

		Map<ObservedConcept, IObservation> catalog = scope.getCatalog();
		if (this.target != null) {
			targetObservation = catalog.get(this.target);
		}

		/*
		 * use sorted, normalized copies of all rows and columns. From this point on,
		 * the matches of IDs in the row/column catalog fields is no longer valid but
		 * each row/column in the lists contains the index of definition, which isn't
		 * the same as that of computation.
		 */
		List<Dimension> sortedColumns = getSortedDimension(columns, catalog, scope);
		List<Dimension> sortedRows = getSortedDimension(rows, catalog, scope);

		/*
		 * Find all observations in scope and fill in the observation map
		 */
		TableArtifact ret = new TableArtifact(this, sortedRows, sortedColumns, scope);

		for (Phase phase : getPhases(scope, catalog)) {

			for (Pair<Object, ILocator> value : phase.states(targetObservation)) {

				for (Dimension column : sortedColumns) {

					if (!column.isActive(catalog, value.getSecond(), phase, value.getFirst(), scope)) {
						continue;
					}

					ObservedConcept columnTarget = column.target == null ? this.target : column.target;
					TargetType columnTargetType = column.targetType == null ? this.targetType : column.targetType;
					ComputationType columnComputationType = column.computationType;
					int aggregationLevel = (column.computationType != null && column.computationType.isAggregation())
							? 1
							: 0;

					for (Dimension row : sortedRows) {

						if (!row.isActive(catalog, value.getSecond(), phase, value.getFirst(), scope)) {
							continue;
						}

						// bring along the data of computation closest to us
						ObservedConcept rowTarget = row.target == null ? columnTarget : row.target;
						TargetType rowTargetType = row.targetType == null ? columnTargetType : row.targetType;
						ComputationType rowComputationType = row.computationType == null ? column.computationType
								: row.computationType;
						IExpression rowExpression = row.getExpression(scope) == null ? column.getExpression(scope)
								: row.getExpression(scope);
						Set<String> rowSymbols = row.symbols == null ? column.symbols : row.symbols;
						boolean referencesPhases = row.getExpression(scope) == null ? column.referencesPhases
								: row.referencesPhases;

						if (row.computationType != null && row.computationType.isAggregation()) {
							aggregationLevel++;
						}

						// ugh
						boolean inconsistentAggregation = columnComputationType != null && row.computationType != null
								&& rowComputationType.isAggregation() && column.computationType.isAggregation()
								&& row.computationType != column.computationType;

						Object val = value.getFirst();
						if (rowTargetType != null && rowTarget != null) {
							switch (rowTargetType) {
							case AREA:
								// TODO precompute this if grid
								val = rowTarget.getObservable().getUnit().convert(
										((IScale) value.getSecond()).getSpace().getStandardizedArea(),
										Units.INSTANCE.SQUARE_METERS);
								break;
							case DURATION:
								// TODO same
								val = ((IScale) value.getSecond()).getTime()
										.getLength(rowTarget.getObservable().getUnit());
								break;
							case NUMEROSITY:
								// TODO count some fucking object
								break;
							case QUALITY:
								// TODO maybe we have this already
								val = ((IState) catalog.get(rowTarget)).get(value.getSecond());
								break;
							default:
								break;
							}
						}

						if (rowComputationType == null) {

							ret.accumulate(val, rowTarget == null ? null : rowTarget.getObservable(), value.getSecond(),
									phase, column.index, row.index);

						} else if (rowComputationType == ComputationType.Expression) {

							if (!referencesPhases) {
								val = evaluate(rowExpression, rowSymbols, value.getSecond(), ret, column.index,
										row.index, true, scope);
							}

							ret.accumulate(val, rowTarget == null ? null : rowTarget.getObservable(), value.getSecond(),
									phase, column.index, row.index);

							if (referencesPhases && phase.isLast()) {
								ret.setValue(evaluate(rowExpression, rowSymbols, value.getSecond(), ret, column.index,
										row.index, false, scope), column.index, row.index);
							}

						} else if (!inconsistentAggregation) {
							// schedule for aggregation after all other cells are computed
							ret.aggregate(rowComputationType, phase, column.index, row.index, aggregationLevel);
						}
					}
				}
			}
		}

		scope.getMonitor().info("table " + name + " computed successfully");

		return ret;
	}

	private Object evaluate(IExpression rowExpression, Set<String> rowSymbols, ILocator locator, TableArtifact ret,
			int column, int row, boolean isValue, IRuntimeScope scope) {

//		IExpression.Context ectx = scope.getExpressionContext();
		// TODO this does not localize the context
		IParameters<String> parameters = Parameters.create(scope);
		parameters.put("value", ret.getCurrentValue(column, row, "value", isValue));
		for (String symbol : rowSymbols) {
			// match to current row/column if column/row name. TODO use ranges and
			if (rows.containsKey(symbol) || columns.containsKey(symbol)) {
				parameters.put(symbol, ret.getCurrentValue(column, row, symbol, true));
			} else {
				IArtifact artifact = scope.getArtifact(symbol);
				if (artifact instanceof IState) {
					parameters.put(symbol, ((IState) artifact).get(locator));
				} else if (artifact != null) {
					parameters.put(symbol, artifact);
				}
			}
			// intersections, for now fuck.
		}
		return rowExpression.eval(parameters, scope);
	}

	private List<Phase> getPhases(IRuntimeScope scope, Map<ObservedConcept, IObservation> catalog) {
		List<Phase> ret = new ArrayList<>();

		IObservation trg = null;
		if (target != null) {
			trg = catalog.get(this.target);
		}

		if (trg instanceof ObservationGroup) {

			ret.add(new Phase(trg).setKey("main"));

		} else if (trg != null) {

			if (phaseItems.isEmpty()) {
				ret.add(new Phase(scope.getScale(), 1).setKey("main"));
			} else {
				if (trg.getScale().isTemporallyDistributed()) {
					ITime time = trg.getScale().getTime();
					if (phaseItems.contains("init")) {
						ret.add(new Phase((IScale) trg.getScale().initialization(), "init").setKey("init"));
					}
					if (phaseItems.contains("start")) {
						ret.add(new Phase((IScale) trg.getScale().at(time.getExtent(time.size() < 3 ? 0 : 1)), "start")
								.setKey("start"));
					}
					if (phaseItems.contains("end")) {
						ret.add(new Phase((IScale) trg.getScale().at(time.getExtent(time.size() - 1)), "end")
								.setKey("end"));
					}
				} else {
					ret.add(new Phase(trg.getScale(), 1).setKey("main"));
				}
			}
		}

		for (int i = 0; i < ret.size(); i++) {
			ret.get(i).index = i;
			ret.get(i).total = ret.size();
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getTemplateVars(Dimension dimension, IRuntimeScope scope) {
		Map<String, Object> ret = new HashMap<>();
		for (List<Filter> filterList : new List[] { dimension.filters, dimension.classifiers }) {
			if (filterList != null) {
				for (Filter filter : filterList) {
					if (filter.classifier != null) {
						ret.put("classifier", ((Classifier) filter.classifier).getDisplayLabel());
						if (filter.classifier.isInterval()) {
							ret.put("range", ((Classifier) filter.classifier).getDisplayLabel());
						} else if (filter.classifier.isConcept()) {
							ret.put("concept", ((Classifier) filter.classifier).getDisplayLabel());
						}
					} else if (filter.timeSelector != null) {
						ret.put("time", filter.timeSelector.getDisplayLabel(scope));
					}
				}
			}
		}

		ret.put("init", "pre-" + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
				scope.getRootSubject().getScale().getTime().getResolution()));
		ret.put("start", Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
				scope.getRootSubject().getScale().getTime().getResolution()));
		ret.put("end", "post-" + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getEnd(),
				scope.getRootSubject().getScale().getTime().getResolution()));

		return ret;
	}

	public List<Filter> compileFilters(ObservedConcept target, TargetType targetType, List<Object> classifiers) {
		List<Filter> ret = new ArrayList<>();

		for (Object o : classifiers) {

			Filter filter = new Filter();
			filter.universal = false;

			if (o instanceof Pair) {
				// it's an observable with its own abstract target
				if (((Pair<?, ?>) o).getFirst() instanceof ObservedConcept) {

					ObservedConcept observable = (ObservedConcept) ((Pair<?, ?>) o).getFirst();
					if (observable.getObservable().is(Type.COUNTABLE)) {
						// filter placeholder which will be removed at resolution
						filter.objectFilter = observable;
					} else {
						/*
						 * by default concept matches on expanded concepts are exact
						 */
						IObservable.Resolution resolution = IObservable.Resolution.Only;
						if (observable.getObservable().getResolution() != null) {
							resolution = observable.getObservable().getResolution();
						}
						filter.classifier = Classifier.forConcept(observable.getObservable().getType(), resolution);
						filter.target = ((Pair<?, ?>) o).getSecond() == null ? target
								: (ObservedConcept) ((Pair<?, ?>) o).getSecond();
						filter.targetType = ((Pair<?, ?>) o).getSecond() == null ? targetType : null;
					}
				}
			} else if (o instanceof ObservedConcept) {
				if (((ObservedConcept) o).getObservable().is(Type.COUNTABLE)) {
					// filter placeholder which will be removed at resolution
					filter.objectFilter = ((ObservedConcept) o);
				} else {
					throw new KlabValidationException("unexpected non-countable classifier without a target: "
							+ ((ObservedConcept) o).getObservable());
				}
			} else {

				filter.target = target;
				filter.targetType = targetType;

				if (o instanceof String) {
					switch (o.toString()) {
					case "start":
					case "end":
					case "init":
						filter.timeSelector = new TimeSelector(o);
						// fall through
					case "time":
						harvestedTimeSelectors.add(o.toString());
						break;
					}
				} else {
					filter.classifier = Classifier.create(o);
				}

			}

			if (filter != null) {
				ret.add(filter);
			}
		}
		return ret;
	}

	public int getActiveRows() {
		return this.activeRows;
	}

	public int getActiveColumns() {
		return this.activeColumns;
	}

	public String getTitle() {
		return this.title;
	}

	public String getName() {
		return this.name;
	}

	public String getLabel() {
		return this.label;
	}

	public Schedule getSchedule() {
		return this.schedule;
	}

}
