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
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IClassifier;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.data.general.IExpression.Scope;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.extensions.ITableCompiler;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.knowledge.IViewModel.Schedule;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.classification.Classifier;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;
import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Sets;

/**
 * A class that interprets a table view definition and produces a table artifact when requested.
 * 
 * @author Ferd
 */
public class TableCompiler {

    /**
     * Each phase is a scan of the target observation. There is always at least one phase. If more
     * than one, it's for comparison or selection of specific locators. If we're scanning a quality,
     * we can iterate locators; otherwise we iterate observations. The phase can tell us what we're
     * locating against and its own index in the set of phases.
     * <p>
     * For now the recognized phases are only temporal, but there's no reason why they couldn't be
     * spatial or logical, or more complex with separate contextualization for different objects.
     * 
     * @author Ferd
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
         * Iterate over the primary target. If the target is a state, the returned pairs will
         * contain the value we want and the correspondent locator. Otherwise, the value will be an
         * observation and the locator will be its scale.
         * 
         * @return
         */
        Iterable<Pair<Object, ILocator>> states(final IObservation target) {
            if (isState(target) && scale != null) {
                return new Iterable<Pair<Object, ILocator>>(){

                    Iterator<ILocator> it = scale.iterator();

                    @Override
                    public Iterator<Pair<Object, ILocator>> iterator() {
                        return new Iterator<Pair<Object, ILocator>>(){

                            @Override
                            public boolean hasNext() {
                                return it.hasNext();
                            }

                            @Override
                            public Pair<Object, ILocator> next() {
                                ILocator locator = it.next();
                                // isState has returned true but we have a collection of states that
                                // must be contextualized
                                // to each filter.
                                Object value = target instanceof IState ? ((IState) target).get(locator) : null;
                                return new Pair<>(value, locator);
                            }

                        };
                    }
                };

            } else if (observation != null) {
                return new Iterable<Pair<Object, ILocator>>(){

                    @Override
                    public Iterator<Pair<Object, ILocator>> iterator() {
                        return new Iterator<Pair<Object, ILocator>>(){

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
                    switch(c.toString()) {
                    case "init":
                        return timeSelector.init;
                    case "start":
                        return timeSelector.start;
                    case "end":
                        return timeSelector.end;
                    case "time":
                        return timeSelector.steps;
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
     * Selector of time points to use with spatially varying targets. If resolution != null, selects
     * all timepoints at the resolution within the target range. Start and end select the first or
     * last slice at the native resolution of the target.
     * 
     * @author Ferd
     */
    class TimeSelector {

        boolean start;
        boolean end;
        boolean init;
        boolean steps;
        Resolution resolution;
        private String displayLabel;
        
        
        TimeSelector(Object o) {
            if (o instanceof IKimQuantity) {
                this.resolution = Time.resolution((IKimQuantity) o);
            } else if ("years".equals(o)) {
                this.resolution = Time.resolution(1, Resolution.Type.YEAR);
                this.steps = true;
            } else if ("days".equals(o)) {
                this.resolution = Time.resolution(1, Resolution.Type.DAY);
                this.steps = true;
            } else if ("months".equals(o)) {
                this.resolution = Time.resolution(1, Resolution.Type.MONTH);
                this.steps = true;
            } else if ("weeks".equals(o)) {
                this.resolution = Time.resolution(1, Resolution.Type.WEEK);
                this.steps = true;
            } else if ("hours".equals(o)) {
                this.resolution = Time.resolution(1, Resolution.Type.HOUR);
                this.steps = true;
            } else {
                this.start = "start".equals(o);
                this.init = "init".equals(o);
                this.end = "end".equals(o);
                this.steps = "steps".equals(o);
            }
        }

        public String toString() {
            if (init) {
                return "init";
            }
            if (end) {
                return "end";
            }
            if (start) {
                return "start";
            }
            if (resolution != null && resolution.getMultiplier() == 1) {
                switch (resolution.getType()) {
                case CENTURY:
                    return "centuries";
                case DAY:
                    return "days";
                case DECADE:
                    return "decades";
                case HOUR:
                    return "hours";
                case MILLENNIUM:
                    return "millennia";
                case MILLISECOND:
                    return "milliseconds";
                case MINUTE:
                    return "minutes";
                case MONTH:
                    return "months";
                case SECOND:
                    return "seconds";
                case WEEK:
                    return "weeks";
                case YEAR:
                    return "years";                
                }
            }
            if (steps) {
                return "steps";
            }
            return "IMPLEMENT ME";
        }

        public String getDisplayLabel(IRuntimeScope scope) {

            if (this.displayLabel == null) {
                if (this.init) {
                    if (timelabels != null && timelabels.containsKey("init")) {
                        // TODO eval if it's an expression
                        return timelabels.get("init").toString();
                    }

                    // TODO check use of root subject. Should use target artifact but it's hard from
                    // this call chain.
                    this.displayLabel = "at start of "
                            + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
                                    scope.getRootSubject().getScale().getTime().getResolution());

                } else if (this.start) {

                    if (timelabels != null && timelabels.containsKey("start")) {
                        // TODO eval if it's an expression
                        return timelabels.get("start").toString();
                    }

                    this.displayLabel = Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
                            scope.getRootSubject().getScale().getTime().getResolution());

                } else if (this.end) {

                    if (timelabels != null && timelabels.containsKey("end")) {
                        // TODO eval if it's an expression
                        return timelabels.get("end").toString();
                    }

                    this.displayLabel = /*
                                         * "at start of " +
                                         */ Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().latest().getStart(),
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
     */
    class SpaceSelector {
        IShape shape;
    }

    /*
     * Filter to select which dimensions are added to when scanning the target observations. One or
     * more of the selectors can be non-null.
     */
    class Filter {

        /*
         * Universal filter matches everything except nodata. Specified by no spec, cleaner with an
         * actual object.
         */
        boolean universal = false;

        /**
         * The observation we're filtering when using . For expression filters, we may be filtering
         * more than one, using the localized names in the context at the current locator.
         */
        ObservedConcept target = null;

        /**
         * Time selector if we only accumulate values in specified times, matching the classifier in
         * the phase.
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

        // save the last value matched through classifier. For debugging only.
        Object lastMatched;

        private boolean selectsState;

        public Filter() {
            this.universal = true;
        }

        public Filter(String artifactId) {
            this.objectId = artifactId;
        }

        public String toString() {
            String ret = "";
            if (classifier != null) {
                ret += classifier.toString();
            }
            if (timeSelector != null) {
                ret += (ret.isEmpty() ? "" : " ") + timeSelector.toString();
            }
            if (expression != null) {
                ret += (ret.isEmpty() ? "" : " ") + "[" + expression + "]";
            }
            return ret;
        }

        boolean isEnabled(Map<IObservedConcept, IObservation> catalog, IContextualizationScope scope) {

            // TODO there may be other situations, like ranges that can't be matched, but
            // maybe it's not worth checking compared to this.

            if (classifier != null && target != null) {

                IObservation observation = catalog.get(target);
                if (observation instanceof IState) {
                    if (((IState) observation).getDataKey() != null && classifier.isConcept()) {
                        if (classifier.getConceptResolution() == IObservable.Resolution.Only) {
                            if (!((IState) observation).getDataKey().getConcepts().contains(classifier.getConcept())) {
                                return false;
                            } else if (classifier.getConceptResolution() == IObservable.Resolution.Any) {
                                if (((IState) observation).getDataKey().getConcepts().contains(classifier.getConcept())) {
                                    return true;
                                }
                                for (IConcept child : classifier.getConcept().getChildren()) {
                                    if (((IState) observation).getDataKey().getConcepts().contains(child)) {
                                        return true;
                                    }
                                }
                                return false;
                            }
                        }
                    }
                }
            }

            return true;
        }

        boolean matches(Map<IObservedConcept, IObservation> catalog, ILocator locator, Phase phase, Object currentState,
                Dimension dimension, IContextualizationScope scope) {
            if (universal) {
                return true;
            }
            if (this.timeSelector != null) {
                if (phase == null || !phase.matches(this.timeSelector)) {
                    return false;
                }
            }
            if (classifier != null) {

                if (dimension.contextualizedState != null) {
                    // we have found a filter matching the state
                    return true;
                }

                if (target != null) {

                    IObservation observation = getObservation(catalog, classifier, dimension);

                    if (observation instanceof IState) {
                        currentState = ((IState) observation).get(locator);
                    } else if (selectsState) {
                        return false;
                    }

                    if (dimension.contextualizedState != null) {
                        this.lastMatched = currentState;
                        return true;
                    }

                }
                if (!selectsState && !classifier.classify(currentState, scope)) {
                    return false;
                }

                this.lastMatched = currentState;
            }

            return true;
        }

        IObservation getObservation(Map<IObservedConcept, IObservation> catalog, IClassifier classifier, Dimension dimension) {

            if (this.target.getObservable().isAbstract() && classifier.isConcept()) {

                if (!dimension.stateContextualized) {

                    this.selectsState = true;
                    dimension.stateContextualized = true;

                    /*
                     * find the specific artifact that incarnates the abstract concept
                     */
                    if (classifier.getConcept().is(Type.PREDICATE)) {
                        for (IObservation obs : catalog.values()) {
                            if (obs instanceof IState
                                    && obs.getObservable().getResolvedPredicates().containsValue(classifier.getConcept())) {
                                dimension.contextualizedState = (IState) obs;
                                break;
                            }
                        }
                    } else {
                        IObservation obs = catalog.get(new ObservedConcept(classifier.getConcept()));
                        if (obs instanceof IState) {
                            dimension.contextualizedState = (IState) obs;
                        }
                    }

                }
                if (dimension.contextualizedState != null) {
                    return dimension.contextualizedState;
                }
            }

            return catalog.get(target);

        }

    }

    public enum DimensionType {
        ROW, COLUMN, SPLIT
    }

    /**
     * Operations that will redefine a target concept; these operate on the targets inherited during
     * scan and use operations from parent dimensions to implement complex transformation chains.
     * For now we recognize: unary semantic operators (to remove or add), removal or addition of a
     * role or attribute. Specified with one token for add (the concept or 'value', 'magnitude'
     * etc.) and with (remove ...) for removal.
     * 
     * @author Ferd
     */
    class TargetOperation {

        UnarySemanticOperator operatorRemove;
        UnarySemanticOperator operatorAdd;
        IConcept predicateAdd;
        IConcept predicateRemove;

        public TargetOperation(Object object) {
            String operation = "add";
            Object operand = null;
            if (object instanceof List && ((List<?>) object).size() > 1) {
                operation = ((List<?>) object).get(0).toString();
                object = ((List<?>) object).get(1);
            }
            if (object instanceof String) {
                operand = object;
            } else if (object instanceof IKimConcept) {
                operand = Concepts.INSTANCE.declare((IKimConcept) object);
            }
            if (operand != null) {
                switch(operation) {
                case "remove":
                    if (operand instanceof IConcept && ((IConcept) operand).is(Type.PREDICATE)) {
                        predicateRemove = (IConcept) operand;
                    } else if (operand instanceof String) {
                        switch((String) operand) {
                        case "value":
                            operatorRemove = UnarySemanticOperator.VALUE;
                            break;
                        // TODO the rest
                        }
                    }
                    break;
                case "add":
                    if (operand instanceof IConcept && ((IConcept) operand).is(Type.PREDICATE)) {
                        predicateAdd = (IConcept) operand;
                    } else if (operand instanceof String) {
                        switch((String) operand) {
                        case "value":
                            operatorAdd = UnarySemanticOperator.VALUE;
                            break;
                        // TODO the rest
                        }
                    }
                    break;
                }
            }
        }

        public IObservedConcept transform(IObservedConcept trg) {
            IObservedConcept ret = trg;
            if (this.operatorAdd != null) {
                return new ObservedConcept(trg.getObservable().getBuilder(monitor).as(this.operatorAdd).buildObservable(),
                        trg.getMode());
            } else if (this.operatorRemove != null) {
                return new ObservedConcept(
                        Observable.promote(Observables.INSTANCE.getDescribedType(trg.getObservable().getType())), trg.getMode());
            } else if (this.predicateAdd != null) {
                if (this.predicateAdd.is(Type.ROLE)) {
                    return new ObservedConcept(
                            trg.getObservable().getBuilder(monitor).withRole(this.predicateAdd).buildObservable(), trg.getMode());
                } else {
                    return new ObservedConcept(
                            trg.getObservable().getBuilder(monitor).withTrait(this.predicateAdd).buildObservable(),
                            trg.getMode());
                }
            } else if (this.predicateRemove != null) {
                return new ObservedConcept(
                        trg.getObservable().getBuilder(monitor).without(this.predicateRemove).buildObservable(), trg.getMode());
            }
            return ret;
        }

    }

    /**
     * Represents rows, columns and (later) splits. The index is the position of the cell (which is
     * not necessarily computed in order).
     * 
     * @author Ferd
     */
    class Dimension {

        /**
         * Mandatory id, either assigned in configuration or attributed using the scheme c<n> for
         * columns and r<n> for rows. It is private so that the getName() method must be used: the
         * column name is composed by the name of the parent (not part of this) + the assigned name.
         */
        private String id;

        /**
         * Only used to validate use of cross-references, as rows can only refer to cells in the
         * same row (i.e. use column references) and the other way around.
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
         * Index of this column in same group of child columns. Values of columns in same group may
         * be referenced using g1...gn in expressions.
         */
        int groupIndex = -1;

        /**
         * Parent column for columns that are declared inside of others.
         */
        Dimension parent;

        /**
         * Children - sucks to keep both parents and children but these will not get garbage
         * collected that often, and it makes it easy to browse the hierarchy when referenced in
         * expressions.
         */
        Collection<Dimension> children = new ArrayList<>();

        /**
         * Where the value we specify comes from. Null if computed or just coming from another
         * dimension. Target operations will fill this in at the first usage.
         */
        ObservedConcept target;

        /**
         * If specified (using retarget) the existing (stated or inherited) target will be
         * transformed at the first usage and set into target. Target operations are semantic
         * transformations that will redefine the target when it's known, and if the target of a
         * column is unspecified, will apply to the rows when the target is assigned.
         */
        List<TargetOperation> targetOperation = new ArrayList<>();

        /**
         * Gets to the view in case a column wants to specify a number format. Ignored in rows for
         * now.
         */
        String numberformat = null;

        /**
         * This tells us if we're scanning the actual values of the target or only the associated
         * context metrics such as area occupied per category or counts.
         */
        public IKnowledgeView.TargetType targetType;

        /**
         * This is associated to the target and will determine the default for non-existing columns.
         * FIXME should have no default - this is a shortcut for now.
         */
        public IArtifact.Type dataType = IArtifact.Type.NUMBER;

        /**
         * Associated to the "require" tag, contains zero or more IDs for sub-columns in a group
         * that are required to be non-empty in order for the whole group to be compiled in the
         * final table.
         */
        Set<String> required = new HashSet<>();

        /**
         * Hierarchically arranged titles at the levels we need them. Nulls for intermediate levels
         * that aren't specified.
         */
        String[] titles;

        /**
         * Filters that will define whether the value correspondent to dimension's aggregator is
         * added to the cell or not. May be empty, never null.
         */
        List<Filter> filters;

        /**
         * Classifiers are like filters but they don't prevent the addition of non-matching values.
         * May be empty, never null.
         */
        List<Filter> classifiers;

        /**
         * Aggregation type, if any. (UNUSED)
         */
        IKnowledgeView.AggregationType aggregation = null;

        /**
         * This takes over the type of aggregator if specified with 'aggregation'.
         */
        IKnowledgeView.ComputationType forcedAggregation = null;

        /**
         * Computation type may require the use of specific formulas or defer calculation to a
         * second pass for row/column totals or other statistics. Rows with expressions are computed
         * in order of dependency; rows of sums or other aggregation must always be computed after
         * all others.
         */
        IKnowledgeView.ComputationType computationType = null;

        /**
         * Expression is assigned on parsing, the actual compilation is done before calculating when
         * we have a scope.
         */
        IKimExpression expression;
        ILanguageExpression computation;

        /**
         * Any symbols used in computations, to compute dependencies.
         */
        Set<String> symbols = new HashSet<>();

        /**
         * Styles if any were specified
         */
        Set<IKnowledgeView.Style> style = new HashSet<>();

        private Map<String, Set<String>> phaseReferences = new HashMap<>();

        private boolean referencesStates;

        private boolean referencesPhases;

        private Set<String> referencedObjects = new HashSet<>();

        private int multiplicity = 1;

        // if true, dimensions that point to numeric cells that are all 0 or nodata will
        // be removed before compiling the final table
        boolean hideZero = false;

        // if the dimension describes a concretized target, it must choose a state from
        // the concrete
        // ones available according to the filters it implements. We keep the state and
        // a flag to
        // ensure this only has to be done once.
        boolean stateContextualized = false;
        IState contextualizedState = null;

        public Dimension(Dimension dim) {
            this.aggregation = dim.aggregation;
            this.computation = dim.computation;
            this.expression = dim.expression;
            this.hideZero = dim.hideZero;
            this.dimensionType = dim.dimensionType;
            this.forcedAggregation = dim.forcedAggregation;
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
            this.referencedObjects.addAll(dim.referencedObjects);
            this.phaseReferences.putAll(dim.phaseReferences);
            this.targetOperation.addAll(dim.targetOperation);
            this.parent = dim.parent;
            this.children.addAll(dim.children);
            this.multiplicity = dim.multiplicity;
            this.filterClassId = dim.filterClassId;
            this.required.addAll(dim.required);
        }

        public Dimension() {
            // TODO Auto-generated constructor stub
        }

        public ILanguageExpression getExpression(IRuntimeScope scope) {
            if (expression != null && computation == null) {
                ILanguageProcessor processor = Extensions.INSTANCE.getLanguageProcessor(
                        expression.getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE : expression.getLanguage());
                Scope context = scope.getExpressionContext();

                // register row and column names unless the rows/colums are aggregations
                for (Dimension dimension : rows.values()) {
                    // if (dimension.computationType == null ||
                    // !dimension.computationType.isAggregation()) {
                    // FIXME IDs for all siblings, getName() for others
                    context.addKnownIdentifier(dimension.id, IKimConcept.Type.QUALITY);
                    // }
                }
                for (Dimension dimension : columns.values()) {
                    // if (dimension.computationType == null ||
                    // !dimension.computationType.isAggregation()) {
                    // FIXME IDs for all siblings, getName() for others
                    context.addKnownIdentifier(dimension.id, IKimConcept.Type.QUALITY);
                    // }
                }

                context.addKnownIdentifier("cell", Type.COUNTABLE);
                context.addKnownIdentifier("row", Type.COUNTABLE);
                context.addKnownIdentifier("column", Type.COUNTABLE);

                Descriptor descriptor = processor.describe(expression.getCode(), context, CompilerOption.RecontextualizeAsMap,
                        CompilerOption.IgnoreContext);
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
                this.referencedObjects.addAll(descriptor.getIdentifiersInNonscalarScope());

                /*
                 * TODO 1. if the expr has only scalars for self/ctx names, will eval and accumulate
                 * the result; 2. if it has non-scalars for self/ctx, will accumulate normally and
                 * then compute and set the value; 3. if keys contain start/end/init, it will only
                 * compute at last phase.
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
                 * if this is true, we accumulate the phase values and only evaluate the expression
                 * at the end
                 */
                for (String ref : phaseReferences.keySet()) {
                    if ("value".equals(ref) || scope.getArtifact(ref) instanceof IState) {
                        this.referencesPhases = true;
                        if (!phaseItems.containsAll(phaseReferences.get(ref))) {
                            throw new KlabValidationException("table expression references unknown phases in @ locator");
                        }
                    }
                }
            }

            return computation;

        }

        @Override
        public String toString() {
            return "<" + dimensionType + " " + this.getName() + " T[" + target + "] " + encodeFilters() + ">";
        }

        private String encodeFilters() {
            String fret = "";
            if (this.filters != null) {
                for (Filter filter : this.filters) {
                    fret += (fret.isEmpty() ? "" : ", ") + filter.toString();
                }
            }
            String cret = "";
            if (this.classifiers != null) {
                for (Filter filter : this.classifiers) {
                    cret += (cret.isEmpty() ? "" : ", ") + filter.toString();
                }
            }
            String ret = (fret.isEmpty() ? "" : ("F(" + fret + ")"));
            return (ret.isEmpty() ? "" : (ret + " ")) + (cret.isEmpty() ? "" : ("C(" + cret + ")"));
        }

        boolean separator = false;

        private String filterClassId;

        // ensures that all rows get their target computed properly and transformed as
        // needed before any cell is computed.
        public Map<String, IObservedConcept> columnTargets = new HashMap<>();

        /**
         * Called once at sorting to weed out dimensions that cannot match the data scope.
         * 
         * @param catalog
         * @param scope
         * @return
         */
        public boolean isEnabled(Map<IObservedConcept, IObservation> catalog, IContextualizationScope scope) {

            if (this.filters != null) {
                for (Filter filter : this.filters) {
                    if (!filter.isEnabled(catalog, scope)) {
                        return false;
                    }
                }
            }

            // classifiers are also checked
            if (this.classifiers != null) {
                for (Filter filter : this.classifiers) {
                    if (!filter.isEnabled(catalog, scope)) {
                        return false;
                    }
                }
            }

            return true;
        }

        /**
         * True if all filters match, or there are no filters (unless it's a separator). This should
         * compute as quickly as possible.
         * 
         * @param catalog
         * @param second
         * @param phase
         * @param currentState the primary target's value - may be a value or a direct observation
         *        according to what we're computing against.
         * @return
         */
        public boolean isActive(Map<IObservedConcept, IObservation> catalog, ILocator locator, Phase phase, Object currentState,
                IContextualizationScope scope) {

            if (this.separator) {
                return false;
            }

            /*
             * match the filters starting at the top parent.
             */
            List<Dimension> parents = new ArrayList<Dimension>();
            Dimension myparent = this;
            parents.add(myparent);
            while(myparent.parent != null) {
                parents.add(myparent.parent);
                myparent = myparent.parent;
            }

            for (int i = parents.size() - 1; i >= 0; i--) {
                if (parents.get(i).filters != null) {
                    for (Filter filter : parents.get(i).filters) {
                        if (!filter.matches(catalog, locator, phase, currentState, this, scope)) {
                            return false;
                        }
                    }
                }
            }

            return true;
        }

        /**
         * for debugging
         * 
         * @param concept
         * @return
         */
        public boolean matches(IConcept concept) {

            if (this.separator) {
                return false;
            }

            /*
             * match the filters starting at the top parent.
             */
            List<Dimension> parents = new ArrayList<Dimension>();
            Dimension myparent = this;
            parents.add(myparent);
            while(myparent.parent != null) {
                parents.add(myparent.parent);
                myparent = myparent.parent;
            }

            for (int i = parents.size() - 1; i >= 0; i--) {
                if (parents.get(i).filters != null) {
                    for (Filter filter : parents.get(i).filters) {
                        if (filter.classifier != null && filter.classifier.classify(concept, null)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        /**
         * For each one of the observations in this group, make a copy of this with the filter that
         * has the objectFilter matching that specific observation.
         * 
         * @param group
         * @return
         */
        public Collection<Dimension> propagateFilteredObservable(IObservationGroup group) {
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
            ret.forcedAggregation = this.forcedAggregation;
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
            ret.referencedObjects.addAll(this.referencedObjects);
            ret.targetOperation.addAll(this.targetOperation);
            ret.parent = this.parent;
            ret.hideZero = this.hideZero;
            ret.children.addAll(this.children);
            ret.filterClassId = this.filterClassId;
            ret.multiplicity = this.multiplicity;
            ret.required.addAll(this.required);

            return ret;
        }

        public Object getDefault() {
            // TODO Auto-generated method stub
            return 0;
        }

        public Aggregation getForcedAggregation() {
            return this.forcedAggregation == null ? null : this.forcedAggregation.getAggregation();
        }

        // public void retarget(ObservedConcept target) {
        // // TODO execute all the retargeting operations and reassign the target
        //
        // this.target = target;
        // }

        public ObservedConcept findTarget() {
            Dimension dim = this;
            while(dim.target == null && dim.parent != null) {
                dim = dim.parent;
            }
            return dim.target;
        }

        public String getName() {
            return (parent == null ? "" : parent.getName()) + getLocalName();
        }

        public String getLocalName() {
            return this.id;
        }

    }

    /**
     * The needed observables so that the table can be computed. These are defined after
     * configuration and not validated against the scope until the table is compiled, so the
     * observations can be made in case the table <b>is</b> the primary query.
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
    private IKnowledgeView.TargetType targetType;
    private String name;
    private String title;
    private String label;
    private Set<String> harvestedTimeSelectors = new HashSet<>();
    private Schedule schedule;
    private INamespace namespace;

    // saved to build contextualized schedules
    private IParameters<String> definition;

    // scope is null when created from k.IM, which is just an instance for
    // validation.
    private IRuntimeScope scope;

    private IObservable targetObservable;

    private Map<?, ?> timelabels;

    private String numberFormat = null;

    // tied to the "name" spec and initialized to the table's model name if
    // unspecified.
    private String identifier;

    private ITableCompiler compiler;

    /**
     * Return the passed dimensions in order of dependency. If circular dependencies are detected
     * throw a validation exception as the definition is misconfigured.
     * 
     * @param dimensions
     * @return
     */
    private List<Dimension> getSortedDimension(Map<String, Dimension> dimensions, Map<IObservedConcept, IObservation> catalog,
            IRuntimeScope scope) {
        List<Dimension> ret = new ArrayList<>();

        List<Dimension> originalDims = new ArrayList<>();
        for (Dimension dim : dimensions.values()) {

            if (!dim.isEnabled(catalog, scope)) {
                continue;
            }

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
                        if (!(group instanceof IObservationGroup)) {
                            throw new KlabValidationException(
                                    "cannot aggregate by a direct artifact that does not resolve to an observation group");
                        }

                        originalDims.addAll(dim.propagateFilteredObservable((IObservationGroup) group));

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
            dictionary.put(dimension.getName(), dimension);
            graph.addVertex(dimension.getName());
            if (dimension.getExpression(scope) != null) {
                for (String s : dimension.symbols) {
                    if (dimensions.containsKey(s)) {
                        graph.addVertex(s);
                        graph.addEdge(s, dimension.getName());
                    }
                }
            }
        }

        CycleDetector<String, DefaultEdge> cycles = new CycleDetector<>(graph);
        if (cycles.detectCycles()) {
            throw new KlabValidationException(
                    "table: expressions in rows or columns contain circular dependencies between " + cycles.findCycles());
        }
        TopologicalOrderIterator<String, DefaultEdge> sort = new TopologicalOrderIterator<>(graph);
        while(sort.hasNext()) {
            ret.add(dictionary.get(sort.next()));
        }

        return ret;
    }

    public boolean isState(IObservation target) {
        return target instanceof IState || (target instanceof IObservationGroup && ((IObservationGroup) target).groupSize() > 0
                && ((IObservationGroup) target).getGroupMember(0) instanceof IState);
    }

    /*
     * Definition and validation. Called with scope == null when read from k.IM, for validation.
     * Called again with actual scope at contextualize(), when roles and other contextual
     * classifiers should be expanded.
     */
    public TableCompiler(String name, IParameters<String> definition, @Nullable IObservable target, INamespace namespace,
            IRuntimeScope scope, IMonitor monitor) {

        if (scope != null) {
            IParameters<String> templateVars = Parameters.create();
            if (definition.getTemplateVariables() != null) {
                templateVars.putAll(definition.getTemplateVariables());
            }
            for (String key : templateVars.keySet()) {
                if (scope.getSession().getState().containsKey(key)) {
                    templateVars.put(key, scope.getSession().getState().get(key));
                }
            }
            definition = definition.with(templateVars);
        }

        this.name = name;
        this.monitor = monitor;
        this.scope = scope;
        this.targetObservable = target;
        List<Pair<ObservedConcept, IKnowledgeView.TargetType>> tdesc = parseTarget(definition.get("target"));
        if (tdesc.size() > 1) {
            throw new KlabValidationException("Only one specific target is admitted for the top-level target of a table");
        } else if (tdesc.size() > 0) {
            this.target = tdesc.get(0).getFirst();
            this.targetType = tdesc.get(0).getSecond();
        }

        List<Dimension> rowList = new ArrayList<>();
        List<Dimension> colList = new ArrayList<>();

        this.activeColumns = parseDimension(definition.get("columns"), colList, DimensionType.COLUMN, null);
        this.activeRows = parseDimension(definition.get("rows"), rowList, DimensionType.ROW, null);
        this.numberFormat = definition.containsKey("numberformat") ? definition.get("numberformat").toString() : null;

        for (Dimension row : rowList) {
            String id = rename.containsKey(row.getName()) ? rename.get(row.getName()) : row.getName();
            row.id = id.substring(row.parent == null ? 0 : row.parent.getName().length());
            this.rows.put(id, row);
        }
        for (Dimension col : colList) {
            String id = rename.containsKey(col.getName()) ? rename.get(col.getName()) : col.getName();
            col.id = id.substring(col.parent == null ? 0 : col.parent.getName().length());
            this.columns.put(id, col);
        }

        /*
         * additional observables, needed if some classifiers are only mentioned in function
         * parameters where we can't find them reliably.
         */
        if (definition.containsKey("observables")) {
            for (Object aob : CollectionUtils.flatCollection(definition.get("observables"))) {
                IObservable observable = Observables.INSTANCE.asObservable(aob);
                if (observable != null) {
                    this.observables.add(new ObservedConcept(observable));
                }
            }
        }

        this.title = definition.containsKey("title") ? definition.get("title").toString() : null;
        this.label = definition.containsKey("label") ? definition.get("label").toString() : null;
        this.identifier = definition.containsKey("name") ? definition.get("name").toString() : this.name;
        this.namespace = namespace;
        this.definition = definition;
        if (definition.get("timelabels") instanceof Map) {
            this.timelabels = (Map<?, ?>) definition.get("timelabels");
        }

        if (definition.containsKey("use")) {
            if (definition.get("use") instanceof IServiceCall) {
                Object o = Extensions.INSTANCE.callFunction((IServiceCall) definition.get("use"), scope);
                if (o instanceof ITableCompiler) {
                    this.compiler = (ITableCompiler) o;
                    this.compiler.initialize(
                            ((IServiceCall) definition.get("use")).getParameters().with(definition.getTemplateVariables()),
                            definition, scope);
                }
            }
        }

        /*
         * validate that only rows OR columns have an additional target but not both. Cells must
         * aggregate a single observable.
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
            throw new KlabValidationException("table: only rows or columns may define a target observable, but not both");
        }

        compileSchedule(definition);
    }

    /**
     * Create a copy of this compiler contextualized to the passed scope. This will expand roles and
     * any other contextual classifiers. Called at resolution when the target artifact is built,
     * before actual observations are made.
     * 
     * @param original
     * @param scope
     */
    private TableCompiler(TableCompiler original, IRuntimeScope scope) {
        this(original.name, original.definition, original.targetObservable, original.namespace, scope, scope.getMonitor());
    }

    private void compileSchedule(Map<?, ?> definition) {
        /*
         * infer using any harvested temporal classifiers, adding any that were specified in the
         * 'when' parameter
         */
        if (definition.containsKey("when")) {
            for (Object cls : definition.get("when") instanceof Collection
                    ? (Collection<?>) definition.get("when")
                    : Collections.singleton(definition.get("when"))) {
                if ("time".equals(cls) || "start".equals(cls) || "end".equals(cls) || "init".equals(cls)) {
                    harvestedTimeSelectors.add(cls.toString());
                    phaseItems.add(cls.toString());
                } else {
                    throw new KlabValidationException("table: temporal classifier " + cls + " not undestood or supported");
                }
            }
        }

        if (!harvestedTimeSelectors.isEmpty()) {

            this.schedule = new Schedule(){

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

    private int parseDimension(Object object, List<Dimension> dimensions, DimensionType type, Dimension parent) {

        int ret = 0;

        if (object == null) {
            return ret;
        }

        if (object instanceof Collection) {
            for (Object o : ((Collection<?>) object)) {
                if (o instanceof Map) {
                    ret += parseDimension((Map<?, ?>) o, dimensions, type, parent);
                } else if (o instanceof String) {
                    switch(o.toString()) {
                    case "empty":
                        Dimension empty = new Dimension();
                        empty.separator = true;
                        empty.id = "s" + dimensions.size();
                        dimensions.add(empty);
                        break;
                    // TODO others?
                    }
                } else {
                    throw new KlabValidationException(
                            "table dimensions (rows and columns) must be specified as maps or lists of maps");
                }
            }
        } else if (object instanceof Map) {
            ret = parseDimension((Map<?, ?>) object, dimensions, type, parent);
        } else {
            throw new KlabValidationException("table dimensions (rows and columns) must be specified as maps or lists of maps");
        }

        return ret;
    }

    /*
     * A classifier spec may turn into >1 classifiers. Return the number of ACTIVE dimensions built
     * from the classifier list.
     */
    private int parseDimension(Map<?, ?> map, List<Dimension> dimensions, DimensionType type, Dimension parent) {

        int ret = 0;

        for (Pair<ObservedConcept, IKnowledgeView.TargetType> target : parseTarget(map.get("target"))) {
            Object classifiers = map.containsKey("filter") ? map.get("filter") : map.get("classifier");
            boolean isClassifier = map.containsKey("classifier");
            Pair<Collection<List<Filter>>, String> clss = expandClassifier(target.getFirst(), target.getSecond(), classifiers,
                    map);
            for (List<Filter> filters : clss.getFirst()) {
                Dimension dimension = newDimension(target.getFirst(), target.getSecond(), filters, map, type, dimensions.size(),
                        isClassifier, clss.getSecond(), parent, dimensions);
                dimensions.add(dimension);
                if (!dimension.separator) {
                    ret += dimension.multiplicity;
                }
            }
        }

        return ret;
    }

    private List<Pair<ObservedConcept, IKnowledgeView.TargetType>> parseTarget(Object object) {

        List<Pair<ObservedConcept, IKnowledgeView.TargetType>> ret = new ArrayList<>();

        ObservedConcept target = null;
        IKnowledgeView.TargetType targetType = null;

        if (object instanceof IKimConcept || object instanceof IKimObservable) {

            IKimStatement tdef = (IKimStatement) object;
            IObservable trg = tdef instanceof IKimObservable
                    ? Observables.INSTANCE.declare((IKimObservable) tdef, monitor)
                    : Observable.promote(Concepts.INSTANCE.declare((IKimConcept) tdef));

            if (trg != null && trg.is(Type.ROLE) && this.scope != null) {

                ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
                for (IConcept c : session.getState().getRoles().keySet()) {
                    if (trg.is(c)) {
                        for (IConcept targ : session.getState().getRoles().get(c)) {
                            ret.add(new Pair<>(new ObservedConcept(Observable.promote(targ), Mode.RESOLUTION),
                                    trg.is(Type.QUALITY) ? IKnowledgeView.TargetType.QUALITY : null));
                        }
                    }
                }
            } else if (trg != null) {

                List<IObservable> targets = new ArrayList<>();

                if (trg.isAbstract() && !isExtent(trg)) {

                    if (scope != null) {

                        Map<IObservedConcept, IObservation> catalog = scope.getCatalog();
                        for (IObservedConcept concept : catalog.keySet()) {
                            boolean stateOK = trg.resolves(concept.getObservable(),
                                    scope.getContextObservation() == null
                                            ? null
                                            : scope.getContextObservation().getObservable().getType());
                            if (stateOK) {
                                targets.add(concept.getObservable());
                            }
                        }
                    }

                } else {
                    targets.add(trg);
                }

                for (IObservable observable : targets) {

                    target = new ObservedConcept(observable,
                            observable.is(IKimConcept.Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);

                    if (trg.getType().equals(Concepts.c(NS.CORE_AREA))) {
                        targetType = IKnowledgeView.TargetType.AREA;
                    } else if (trg.getType().equals(Concepts.c(NS.CORE_DURATION))) {
                        targetType = IKnowledgeView.TargetType.DURATION;
                    } else if (trg.getType().equals(Concepts.c(NS.CORE_COUNT))) {
                        targetType = IKnowledgeView.TargetType.NUMEROSITY;
                    } else {
                        this.observables.add(target);
                        targetType = observable.is(Type.QUALITY) ? IKnowledgeView.TargetType.QUALITY : null;
                    }

                    ret.add(new Pair<>(target, targetType));
                }

            } else {
                throw new KlabValidationException("Table definition: target: " + object + " does not specify a known observable");
            }

        } else if (object != null) {

            IKimObject resource = Resources.INSTANCE.getModelObject(object.toString());
            if (resource instanceof IModel) {
                IObservable trgObs = ((IModel) resource).getObservables().get(0);
                target = new ObservedConcept(Observable.promote((IModel) resource),
                        trgObs.is(IKimConcept.Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);
                targetType = trgObs.is(Type.QUALITY) ? IKnowledgeView.TargetType.QUALITY : null;
                ret.add(new Pair<>(target, targetType));
                this.observables.add(target);

            } else {
                throw new KlabValidationException("Table definition: unknown target: " + object);
            }

        } else {
            // universal
            ret.add(new Pair<>(null, null));
        }

        return ret;

    }

    private boolean isExtent(IObservable trg) {

        if (trg.getType().equals(Concepts.c(NS.CORE_AREA))) {
            return true;
        } else if (trg.getType().equals(Concepts.c(NS.CORE_DURATION))) {
            return true;
        } else if (trg.getType().equals(Concepts.c(NS.CORE_COUNT))) {
            return true;
        }
        return false;

    }

    private Dimension newDimension(ObservedConcept target, IKnowledgeView.TargetType targetType, List<Filter> filters,
            Map<?, ?> definition, DimensionType type, int lastIndex, boolean filtersAreClassifiers, String filterClassId,
            Dimension parent, List<Dimension> dimensions) {

        Dimension ret = new Dimension();

        ret.target = target;
        ret.targetType = targetType;
        ret.classifiers = filtersAreClassifiers ? filters : new ArrayList<>();
        ret.filters = filtersAreClassifiers ? new ArrayList<>() : filters;
        ret.dimensionType = type;
        ret.parent = parent;
        ret.filterClassId = filterClassId;
        ret.numberformat = definition.get("numberformat") != null ? definition.get("numberformat").toString() : null;

        if (parent != null) {
            ret.parent.children.add(ret);
        }

        if (definition.containsKey("hidezero")) {
            ret.hideZero = (Boolean) definition.get("hidezero");
        }

        if (definition.containsKey("name")) {
            ret.id = definition.get("name").toString();
        } else {
            ret.id = (type == DimensionType.ROW ? "r" : "c");
        }

        if (definition.containsKey("require")) {
            Object required = definition.get("require");
            if (required instanceof String) {
                ret.required.add((String) required);
            } else if (required instanceof List) {
                for (Object r : (List<?>) required) {
                    ret.required.add(r.toString());
                }
            }
        }

        ret.id = checkNameSequence(ret);

        if (definition.containsKey("title")) {
            List<String> titles = new ArrayList<>();
            if (definition.get("title") instanceof Collection) {
                for (Object o : ((Collection<?>) definition.get("title"))) {
                    titles.add(o == null ? "" : o.toString());
                }
            } else {
                String theTitle = definition.get("title").toString();
                // count the pound signs in front to establish level
                for (; theTitle.startsWith("#");) {
                    titles.add("");
                    theTitle = theTitle.substring(1);
                }
                titles.add(theTitle);
            }
            ret.titles = titles.toArray(new String[titles.size()]);
        }

        if (definition.containsKey("separator")) {
            // TODO separator types for display
            ret.separator = true;
        }

        if (definition.containsKey("hidden")) {
            if (!(definition.get("hidden") instanceof Boolean)) {
                throw new KlabValidationException("the 'hidden' parameter only admits true/false values");
            }
            ret.hidden = (Boolean) definition.get("hidden");
        }

        if (definition.containsKey("retarget")) {
            Object retarget = definition.get("retarget");
            if (retarget instanceof List && ((List<?>) retarget).size() > 0 && ((List<?>) retarget).get(0) instanceof List) {
                for (Object rt : ((List<?>) retarget)) {
                    ret.targetOperation.add(new TargetOperation(rt));
                }
            } else {
                ret.targetOperation.add(new TargetOperation(retarget));
            }
        }

        if (definition.containsKey("style")) {
            for (Object style : (definition.get("style") instanceof Collection
                    ? ((Collection<?>) definition.get("style"))
                    : Collections.singleton(definition.get("style")))) {
                switch(style.toString()) {
                case "bold":
                    ret.style.add(IKnowledgeView.Style.BOLD);
                    break;
                case "italic":
                    ret.style.add(IKnowledgeView.Style.ITALIC);
                    break;
                case "right":
                    ret.style.add(IKnowledgeView.Style.RIGHT);
                    break;
                case "left":
                    ret.style.add(IKnowledgeView.Style.LEFT);
                    break;
                case "center":
                    ret.style.add(IKnowledgeView.Style.CENTER);
                    break;
                case "bg_highlight":
                    ret.style.add(IKnowledgeView.Style.BG_HIGHLIGHT);
                    break;
                case "fg_highlight":
                    ret.style.add(IKnowledgeView.Style.FG_HIGHLIGHT);
                    break;
                default:
                    throw new KlabValidationException("table: unrecognized style " + style);
                }
            }
        }

        if (definition.get("compute") instanceof IKimExpression) {
            ret.expression = (IKimExpression) definition.get("compute");
            ret.computationType = IKnowledgeView.ComputationType.Expression;
        } else if (definition.get("summarize") instanceof IKimExpression) {
            ret.expression = (IKimExpression) definition.get("summarize");
            ret.computationType = IKnowledgeView.ComputationType.Summarize;
        } else if (definition.containsKey("summarize")) {
            switch(definition.get("summarize").toString()) {
            case "sum":
                ret.computationType = IKnowledgeView.ComputationType.Sum;
                break;
            case "average":
                ret.computationType = IKnowledgeView.ComputationType.Average;
                break;
            case "variance":
                ret.computationType = IKnowledgeView.ComputationType.Variance;
                break;
            case "std":
                ret.computationType = IKnowledgeView.ComputationType.Std;
                break;
            case "min":
                ret.computationType = IKnowledgeView.ComputationType.Min;
                break;
            case "max":
                ret.computationType = IKnowledgeView.ComputationType.Max;
                break;
            default:
                throw new KlabValidationException("unrecognized symbol in summarization: " + definition.get("summarize"));
            }
        }

        if (definition.containsKey("aggregation")) {
            switch(definition.get("aggregation").toString()) {
            case "sum":
                ret.forcedAggregation = IKnowledgeView.ComputationType.Sum;
                break;
            case "average":
                ret.forcedAggregation = IKnowledgeView.ComputationType.Average;
                break;
            case "variance":
                ret.forcedAggregation = IKnowledgeView.ComputationType.Variance;
                break;
            case "std":
                ret.forcedAggregation = IKnowledgeView.ComputationType.Std;
                break;
            case "min":
                ret.forcedAggregation = IKnowledgeView.ComputationType.Min;
                break;
            case "max":
                ret.forcedAggregation = IKnowledgeView.ComputationType.Max;
                break;
            default:
                throw new KlabValidationException("unrecognized symbol in aggregation: " + definition.get("aggregation"));
            }
        }

        if (definition.containsKey(type == DimensionType.COLUMN ? "columns" : "rows")) {
            ret.multiplicity += parseDimension(definition.get(type == DimensionType.COLUMN ? "columns" : "rows"), dimensions,
                    type, ret);
        }

        return ret;
    }

    private Map<String, Integer> idIndex = new HashMap<>();
    private Set<String> rowNames = new HashSet<>();
    private Set<String> colNames = new HashSet<>();
    private Map<String, String> rename = new HashMap<>();

    /**
     * Rename multiple rows with same ID to id1, id2.. etc
     * 
     * @param ret
     */
    private String checkNameSequence(Dimension dimension) {

        Set<String> dimensions = null;
        if (dimension.dimensionType == DimensionType.ROW) {
            dimensions = rowNames;
        } else if (dimension.dimensionType == DimensionType.COLUMN) {
            dimensions = colNames;
        }

        String ret = dimension.getName();
        String baseId = ret;
        // could be a split, in which case do nothing
        if (dimensions != null) {
            if (dimensions.contains(ret)) {
                boolean ren = false;
                Integer index = idIndex.get(ret);
                if (index == null) {
                    index = 1;
                    ren = true;
                }
                String prevId = baseId + index;
                String newId = baseId + (index + 1);
                idIndex.put(baseId, index + 1);
                if (ren) {
                    rename.put(baseId, prevId);
                }
                ret = newId;
            } else {
                dimensions.add(baseId);
            }
        }

        return ret.substring(dimension.parent == null ? 0 : dimension.parent.getName().length());

    }

    private Pair<Collection<List<Filter>>, String> expandClassifier(ObservedConcept target, IKnowledgeView.TargetType targetType,
            Object declaration, Map<?, ?> dimensionDeclaration) {

        List<List<Filter>> ret = new ArrayList<>();
        String classId = null;

        if (declaration == null) {
            ret.add(new ArrayList<>());
            return new Pair<>(ret, null);
        }

        if (declaration instanceof Map) {
            for (Entry<?, ?> entry : ((Map<?, ?>) declaration).entrySet()) {
                if ("default".equals(entry.getKey())) {
                    expandClassifiers(target, targetType, declaration, ret, dimensionDeclaration);
                } else {
                    List<Pair<ObservedConcept, IKnowledgeView.TargetType>> classifierTarget = parseTarget(entry.getKey());
                    if (classifierTarget.size() > 1) {
                        throw new KlabValidationException("Only one specific target is admitted in a classifier");
                    } else if (classifierTarget.size() > 0) {
                        expandClassifiers(classifierTarget.get(0).getFirst(), classifierTarget.get(0).getSecond(), declaration,
                                ret, dimensionDeclaration);
                    }
                }
            }
        } else {
            expandClassifiers(target, targetType, declaration, ret, dimensionDeclaration);
        }

        return new Pair<>(ret, classId);
    }

    private void expandClassifiers(ObservedConcept target, IKnowledgeView.TargetType targetType, Object declaration,
            List<List<Filter>> ret, Map<?, ?> dimensionDeclaration) {
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
        final int BOOLEAN = 6;

        for (Object o : (declaration instanceof Collection ? (Collection<?>) declaration : Collections.singleton(declaration))) {
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

                    observables.add(new ObservedConcept(observable));
                    List<IObservable> keep = null;
                    if (dimensionDeclaration.containsKey("keep")) {
                        keep = new ArrayList<>();
                        Object z = dimensionDeclaration.get("keep");
                        if (z instanceof List) {
                            for (Object zz : ((List<?>) z)) {
                                keep.add(zz instanceof IKimObservable
                                        ? Observables.INSTANCE.declare((IKimObservable) zz, monitor)
                                        : Observable.promote(Concepts.INSTANCE.declare((IKimConcept) zz)));
                            }
                        } else {
                            keep.add(z instanceof IKimObservable
                                    ? Observables.INSTANCE.declare((IKimObservable) z, monitor)
                                    : Observable.promote(Concepts.INSTANCE.declare((IKimConcept) z)));
                        }
                    }

                    for (ObservedConcept category : expandCategory(observable, keep)) {
                        categorize(CATEGORY, category, sorted, observable);
                    }

                } else if (observable.is(IKimConcept.Type.PRESENCE)) {

                    observables.add(new ObservedConcept(observable));
                    categorize(BOOLEAN, true, sorted, observable);
                    categorize(BOOLEAN, false, sorted, observable);

                } else if (observable.is(IKimConcept.Type.PREDICATE)) {
                    /*
                     * FIXME if the observable is an abstract identity or any other that is
                     * represented in the targetObservation, we must choose the incarnated values
                     * from the available ones, not expand to all subclasses, because these may be
                     * authority concepts and/or we may end up with thousands of columns in a busy
                     * engine.
                     */
                    for (ObservedConcept category : expandConcept(observable.getType(), observable)) {
                        categorize(CATEGORY, category, sorted, observable);
                    }
                } else if (observable.isAbstract()) {

                    if (scope != null) {

                        Map<IObservedConcept, IObservation> catalog = scope.getCatalog();
                        for (IObservedConcept concept : catalog.keySet()) {
                            boolean stateOK = observable.resolves(concept.getObservable(),
                                    scope.getContextObservation() == null
                                            ? null
                                            : scope.getContextObservation().getObservable().getType());
                            if (stateOK) {
                                categorize(CATEGORY, concept, sorted, observable);
                            }
                        }
                    }

                } else {
                    throw new KlabValidationException("table: cannot classify on " + observable.getType().getDefinition()
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
                switch(o.toString()) {
                case "start":
                case "end":
                case "init":
                case "steps":
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

    public List<ObservedConcept> expandCategory(IObservable observable, List<IObservable> keep) {
        IConcept category = Observables.INSTANCE.getDescribedType(observable.getType());
        this.observables
                .add(new ObservedConcept(Observables.INSTANCE.removeValueOperators(observable, monitor), Mode.RESOLUTION));

        if (keep != null && !keep.isEmpty()) {
            List<ObservedConcept> ret = new ArrayList<>();
            for (IObservable k : keep) {
                ret.add(new ObservedConcept(k));
            }
            return ret;
        }

        return expandConcept(category, observable);
    }

    private List<ObservedConcept> expandConcept(IConcept category, IObservable observable) {

        List<ObservedConcept> ret = new ArrayList<>();

        /*
         * TODO support down to, all and any in the observable, which may be null
         */
        if (category.is(Type.ROLE)) {
            if (this.scope != null) {
                ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
                for (IConcept c : session.getState().getRoles().keySet()) {
                    if (category.getType().is(c)) {
                        for (IConcept trg : session.getState().getRoles().get(c)) {
                            ret.add(new ObservedConcept(Observable.promote(trg), Mode.RESOLUTION));
                        }
                    }
                }
            } else {
                /*
                 * keep the role in the reference copy, it won't be used.
                 */
                ret.add(new ObservedConcept(Observable.promote(category), Mode.RESOLUTION));
            }
        } else if (!category.isAbstract() && (observable == null || !(observable.isGeneric() || observable.isGlobal()))) {
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
                boolean ok = true;
                if (observable != null && !observable.getValueOperators().isEmpty()) {

                    for (Pair<ValueOperator, Object> vp : observable.getValueOperators()) {
                        switch(vp.getFirst()) {
                        case AVERAGED:
                            break;
                        case BY:
                            break;
                        case DOWN_TO:
                            break;
                        case GREATER:
                            break;
                        case GREATEREQUAL:
                            break;
                        case IS:
                            if (vp.getSecond() instanceof IConcept) {
                                ok = child.is((IConcept) vp.getSecond());
                            }
                            break;
                        case LESS:
                            break;
                        case LESSEQUAL:
                            break;
                        case MINUS:
                            break;
                        case OVER:
                            break;
                        case PLUS:
                            break;
                        case SAMEAS:
                            ok = child.equals(vp.getSecond());
                            break;
                        case SUMMED:
                            break;
                        case TIMES:
                            break;
                        case TOTAL:
                            break;
                        case WHERE:
                            break;
                        case WITHOUT:
                            break;
                        }

                        if (!ok) {
                            break;
                        }

                    }
                }
                if (ok) {
                    ret.add(new ObservedConcept(Observable.promote(child), Mode.RESOLUTION));
                }
            }
        }
        return ret;
    }

    /**
     * Compute all cells that want to be computed. Target comes from caller, if null we must have an
     * observable and find it in the catalog.
     */
    public IKnowledgeView compute(IObservation targetObservation, IRuntimeScope scope) {

        if (this.compiler != null) {
            scope.getMonitor().info("Computing table " + name + " using custom builder");
            IKnowledgeView.Builder builder = SimpleTableArtifact.builder(this, scope);
            this.compiler.compile(builder);
            return builder.build();
        }

        scope.getMonitor().info("start computing table " + name);

        Map<IObservedConcept, IObservation> catalog = scope.getCatalog();
        if (this.target != null && !this.target.getObservable().isAbstract()) {
            targetObservation = catalog.get(this.target);
        }

        /*
         * use sorted, normalized copies of all rows and columns. From this point on, the matches of
         * IDs in the row/column catalog fields is no longer valid but each row/column in the lists
         * contains the index of definition, which isn't the same as that of computation.
         */
        List<Dimension> sortedColumns = getSortedDimension(columns, catalog, scope);
        List<Dimension> sortedRows = getSortedDimension(rows, catalog, scope);
        List<Phase> phases = getPhases(scope, targetObservation);

        // publish unchanging time extents for expressions
        Map<String, ITime> phaseMap = new HashMap<>();
        for (Phase phase : phases) {
            if (phase.scale != null) {
                // may be null
                phaseMap.put(phase.getKey(), phase.scale.getTime());
            }
        }
        if (!phaseMap.containsKey("init")) {
            phaseMap.put("init", targetObservation.getScale().initialization().getTime());
        }
        if (!phaseMap.containsKey("start") && targetObservation.getScale().isTemporallyDistributed()) {
            phaseMap.put("start", (ITime) targetObservation.getScale().getTime()
                    .getExtent(targetObservation.getScale().getTime().size() < 3 ? 0 : 1));
        }

        // if (targetObservation instanceof State) {
        // ((State)targetObservation).dumpStatistics();
        // }

        /*
         * Find all observations in scope and fill in the observation map
         */
        TableArtifact ret = new TableArtifact(this, sortedRows, sortedColumns, scope);

        boolean abstractStates = isState(targetObservation) && targetObservation instanceof IObservationGroup;

        for (Phase phase : phases) {

            for (Pair<Object, ILocator> value : phase.states(targetObservation)) {

                if (monitor.isInterrupted()) {
                    return ret;
                }

                /*
                 * TODO check if there are other situations where we want to tabulate nulls
                 */
                if (value.getFirst() == null && !abstractStates) {
                    continue;
                }

                for (Dimension column : sortedColumns) {

                    if (monitor.isInterrupted()) {
                        return ret;
                    }

                    if (column.children.size() > 0
                            || !column.isActive(catalog, value.getSecond(), phase, value.getFirst(), scope)) {
                        continue;
                    }

                    if (abstractStates && value.getFirst() == null) {
                        contextualizeValue(value, column);
                    }

                    ObservedConcept columnTarget = column.target == null ? this.target : column.target;
                    IKnowledgeView.TargetType columnTargetType = column.targetType == null ? this.targetType : column.targetType;
                    IKnowledgeView.ComputationType columnComputationType = column.computationType;
                    int aggregationLevel = (column.computationType != null && column.computationType.isAggregation()) ? 1 : 0;

                    for (Dimension row : sortedRows) {

                        if (monitor.isInterrupted()) {
                            return ret;
                        }

                        if (!row.isActive(catalog, value.getSecond(), phase, value.getFirst(), scope)) {
                            continue;
                        }

                        if (abstractStates && value.getFirst() == null) {
                            contextualizeValue(value, column);
                            // now yes - we only get here if abstractStates, otherwise the case has
                            // been weeded out way earlier.
                            if (value.getFirst() == null) {
                                continue;
                            }
                        }

                        // bring along the data of computation closest to us
                        IObservedConcept rowTarget = getCellTarget(row, column, columnTarget);
                        IKnowledgeView.TargetType rowTargetType = row.targetType == null ? columnTargetType : row.targetType;
                        IKnowledgeView.ComputationType forcedAggregation = row.forcedAggregation == null
                                ? column.forcedAggregation
                                : row.forcedAggregation;
                        IKnowledgeView.ComputationType rowComputationType = row.computationType == null
                                ? column.computationType
                                : row.computationType;
                        ILanguageExpression rowExpression = row.getExpression(scope) == null
                                ? column.getExpression(scope)
                                : row.getExpression(scope);
                        Set<String> rowSymbols = row.symbols == null ? column.symbols : row.symbols;
                        Set<String> objSymbols = row.referencedObjects.isEmpty()
                                ? column.referencedObjects
                                : row.referencedObjects;
                        boolean referencesPhases = row.getExpression(scope) == null
                                ? column.referencesPhases
                                : row.referencesPhases;

                        if (row.computationType != null && row.computationType.isAggregation()) {
                            aggregationLevel++;
                        }

                        // ugh. This sucks because isAggregation() is WRONG (includes Summarize) but
                        // if that changes, more gets messed up.
                        boolean inconsistentAggregation = columnComputationType != null && row.computationType != null
                                && rowComputationType.isAggregation() && column.computationType.isAggregation()
                                && row.computationType != column.computationType;

                        // Debug.INSTANCE.say("DIO PIROGA");

                        Object val = value.getFirst();
                        if (rowTargetType != null && rowTarget != null) {
                            switch(rowTargetType) {
                            case AREA:

                                // double area = 0;
                                //// Space space = (Space) targetObservation.getScale().getSpace();
                                //// if (space.getGrid() != null) {
                                // area =
                                // ((IScale)value.getSecond()).getSpace().getStandardizedArea();
                                //// }
                                //
                                //
                                // // use precomputed if grid
                                // if (adaptedAreaMq != null) {
                                // val = adaptedAreaMq;
                                // } else if (fixedAreaMq != null) {
                                // val = adaptedAreaMq = rowTarget.getObservable().getUnit()
                                // .convert(fixedAreaMq,
                                // Units.INSTANCE.SQUARE_METERS).doubleValue();
                                // } else {
                                val = rowTarget.getObservable().getUnit().convert(
                                        ((IScale) value.getSecond()).getSpace().getStandardizedArea(),
                                        Units.INSTANCE.SQUARE_METERS);
                                // }
                                break;
                            case DURATION:
                                // TODO same
                                val = ((IScale) value.getSecond()).getTime().getLength(rowTarget.getObservable().getUnit());
                                break;
                            case NUMEROSITY:
                                // add one to the cell
                                val = 1;
                                break;
                            case QUALITY:
                                // TODO maybe we have this already
                                IArtifact source = row.contextualizedState;
                                if (source == null) {
                                    source = column.contextualizedState;
                                }
                                if (source == null) {
                                    source = catalog.get(rowTarget);
                                }
                                if (!(source instanceof IState)) {
                                    continue;
                                }
                                val = ((IState) source).get(value.getSecond());
                                if (val == null) {
                                    continue;
                                }
                                break;
                            default:
                                break;
                            }
                        }

                        if (rowComputationType == null) {

                            ret.accumulate(val, rowTarget == null ? null : rowTarget.getObservable(), value.getSecond(), phase,
                                    column.index, row.index, forcedAggregation);

                            // System.out.println("Accumulating " + val + " of " + rowTarget + " in
                            // " + column + ", " + row
                            // + " at " + ((Cell) ((IScale) value.getSecond()).getSpace()).getX() +
                            // ","
                            // + ((Cell) ((IScale) value.getSecond()).getSpace()).getY());

                        } else if (rowComputationType == IKnowledgeView.ComputationType.Expression) {

                            phaseMap.put("time", scope.getScale().getTime());

                            if (!referencesPhases) {
                                val = evaluate(rowExpression, val, rowSymbols, objSymbols, value, ret, column.index, row.index,
                                        column, row, true, phaseMap, catalog, scope);
                            }

                            ret.accumulate(val, rowTarget == null ? null : rowTarget.getObservable(), value.getSecond(), phase,
                                    column.index, row.index, forcedAggregation);

                            if (referencesPhases && phase.isLast()) {
                                ret.setValue(evaluate(rowExpression, val, rowSymbols, objSymbols, value, ret, column.index,
                                        row.index, column, row, false, phaseMap, catalog, scope), column.index, row.index);
                            }

                        } else if (!inconsistentAggregation) {
                            // schedule for aggregation after all other cells are computed
                            ret.aggregate(rowComputationType, phase, column.index, row.index, aggregationLevel);
                        } else if (inconsistentAggregation && (rowComputationType == IKnowledgeView.ComputationType.Summarize
                                || column.computationType == IKnowledgeView.ComputationType.Summarize)) {
                            // this mess shouldn't be here. Stems from Summarize being considered
                            // aggregation when it shouldn't.
                            ret.aggregate(rowComputationType == IKnowledgeView.ComputationType.Summarize
                                    ? columnComputationType
                                    : rowComputationType, phase, column.index, row.index, aggregationLevel);
                        }

                    }
                }
            }
        }

        scope.getMonitor().info("table " + name + " computed successfully");

        return ret;
    }

    private void contextualizeValue(Pair<Object, ILocator> value, Dimension dimension) {
        if (dimension.contextualizedState != null) {
            value.setFirst(dimension.contextualizedState.get(value.getSecond()));
        }
    }

    private IObservedConcept getCellTarget(Dimension row, Dimension column, IObservedConcept defaultTarget) {

        if (!row.columnTargets.containsKey(column.getName())) {

            IObservedConcept trg = row.target;
            if (trg == null) {
                /*
                 * get the closest target for the row, then for the column, then the default.
                 */
                trg = row.findTarget();
                if (trg /* still */ == null) {
                    trg = column.findTarget();
                }
                if (trg /* still */ == null) {
                    trg = defaultTarget;
                }
            }

            if (trg != null) {
                for (TargetOperation op : compileOperations(row, column)) {
                    trg = op.transform(trg);
                }
            }

            row.columnTargets.put(column.getName(), trg);

            // System.out.println(row.id + "," + column.id + ": " + trg + "; col = " +
            // column + ",
            // row = " + row);

        }

        return row.columnTargets.get(column.getName());
    }

    private List<TargetOperation> compileOperations(Dimension row, Dimension column) {
        List<TargetOperation> ret = new ArrayList<>();

        /*
         * accumulate the ops in reverse order first from the row, then columns. TODO these should
         * be optimized if there is a remove following by an add of the same thing.
         */
        Dimension r = row;
        do {
            int insertPoint = 0;
            for (TargetOperation op : r.targetOperation) {
                ret.add(insertPoint++, op);
            }
            r = r.parent;
        } while(r != null);

        r = column;
        do {
            int insertPoint = 0;
            for (TargetOperation op : r.targetOperation) {
                ret.add(insertPoint++, op);
            }
            r = r.parent;
        } while(r != null);

        return ret;
    }

    /*
     * just a few parameters
     */
    private Object evaluate(ILanguageExpression rowExpression, Object self, Set<String> scalarSymbols, Set<String> objectSymbols,
            Pair<Object, ILocator> value, TableArtifact ret, int columnIndex, int rowIndex, Dimension column, Dimension row,
            boolean isValue, Map<String, ITime> phases, Map<IObservedConcept, IObservation> catalog, IRuntimeScope scope) {

        IParameters<String> parameters = Parameters.create(scope);
        parameters.put("self", self);
        for (String key : phases.keySet()) {
            parameters.put(key, phases.get(key));
        }
        for (String symbol : rowExpression.getIdentifiers()) {
            switch(symbol) {
            case "cell":
                parameters.put(symbol, new TableApiObjects.TableCell(ret, value.getFirst(), column, row, value.getSecond()));
                break;
            case "row":
                parameters.put(symbol, new TableApiObjects.TableDimension(row, catalog, value.getSecond()));
                break;
            case "column":
                parameters.put(symbol, new TableApiObjects.TableDimension(column, catalog, value.getSecond()));
                break;
            default:
                if (rows.containsKey(symbol) || columns.containsKey(symbol)) {
                    parameters.put(symbol, ret.getCurrentValue(columnIndex, rowIndex, symbol, true));
                } else {

                    /*
                     * check with symbol contextualized to group
                     */
                    boolean done = false;
                    if (row.parent != null) {
                        String csym = row.parent.getName() + symbol;
                        if (rows.containsKey(csym)) {
                            parameters.put(symbol, ret.getCurrentValue(columnIndex, rowIndex, csym, true));
                            done = true;
                        }
                    }

                    if (!done && column.parent != null) {
                        String csym = column.parent.getName() + symbol;
                        if (columns.containsKey(csym)) {
                            parameters.put(symbol, ret.getCurrentValue(columnIndex, rowIndex, csym, true));
                            done = true;
                        }
                    }

                    if (!done) {
                        IArtifact artifact = scope.getArtifact(symbol);
                        if (artifact instanceof IState) {
                            parameters.put(symbol, ((IState) artifact).get(value.getSecond()));
                        } else if (artifact != null) {
                            parameters.put(symbol, artifact);
                        }
                    }
                }
                break;
            }
            // intersections, for now fuck.
        }

        return rowExpression.eval(scope, parameters);
    }

    private List<Phase> getPhases(IRuntimeScope scope, IObservation targetObservation) {
        List<Phase> ret = new ArrayList<>();

        IObservation trg = targetObservation;
        if (target.getObservable().isAbstract() && trg instanceof IObservationGroup
                && ((IObservationGroup) trg).groupSize() > 0) {
            IObservation o = (IObservation) ((IObservationGroup) trg).getGroupMember(0);
            if (o instanceof IState) {
                trg = o;
            }
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
                        ret.add(new Phase((IScale) trg.getScale().at(time.earliest()), "start").setKey("start"));
                    }
                    if (phaseItems.contains("end")) {
                        ret.add(new Phase((IScale) trg.getScale().at(time.latest()), "end").setKey("end"));
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
        for (List<Filter> filterList : new List[]{dimension.filters, dimension.classifiers}) {
            if (filterList != null) {
                for (Filter filter : filterList) {
                    if (filter.classifier != null) {
                        ret.put("classifier", ((Classifier) filter.classifier).getDisplayLabel());
                        if (filter.classifier.isInterval()) {
                            ret.put("range", ((Classifier) filter.classifier).getDisplayLabel());
                        } else if (filter.classifier.isConcept()) {
                            ret.put("concept", ((Classifier) filter.classifier).getDisplayLabel());
                        } else if (filter.classifier.isBoolean()) {
                            if (filter.target != null) {
                                IConcept c = Observables.INSTANCE.getDescribedType(filter.target.getConcept());
                                if (c != null) {
                                    ret.put("classifier", Concepts.INSTANCE.getDisplayLabel(c) + " " + ret.get("classifier"));
                                }
                            }
                        }
                    } else if (filter.timeSelector != null) {
                        ret.put("time", filter.timeSelector.getDisplayLabel(scope));
                    }
                }
            }
        }

        if (dimension.target != null) {
            ret.put("target", Observables.INSTANCE.getDisplayName(dimension.target.getObservable()));
            if (!dimension.target.getObservable().getResolvedPredicates().isEmpty()) {
                String resolved = "";
                for (IConcept c : dimension.target.getObservable().getResolvedPredicates().values()) {
                    resolved += (resolved.isEmpty() ? "" : ", ") + Concepts.INSTANCE.getDisplayName(c);
                }
                ret.put("classifier", resolved);
            }
        }

        ret.put("init", "at start of " + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
                scope.getRootSubject().getScale().getTime().getResolution()));
        ret.put("start", Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getStart(),
                scope.getRootSubject().getScale().getTime().getResolution()));
        ret.put("end", "at start of " + Time.getDisplayLabel(scope.getRootSubject().getScale().getTime().getEnd(),
                scope.getRootSubject().getScale().getTime().getResolution()));

        return ret;
    }

    public List<Filter> compileFilters(ObservedConcept target, IKnowledgeView.TargetType targetType, List<Object> classifiers) {

        List<Filter> ret = new ArrayList<>();

        int nTemporal = 0;

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
                         * by default concept matches on expanded concepts are exact unless the
                         * concept is abstract
                         */
                        IObservable.Resolution resolution = observable.getObservable().getType().isAbstract()
                                ? IObservable.Resolution.Any
                                : IObservable.Resolution.Only;
                        if (observable.getObservable().getResolution() != null) {
                            resolution = observable.getObservable().getResolution();
                        }
                        filter.classifier = Classifier.forConcept(observable.getObservable().getType(), resolution);
                        filter.target = ((Pair<?, ?>) o).getSecond() == null
                                ? target
                                : (ObservedConcept) ((Pair<?, ?>) o).getSecond();
                        filter.targetType = ((Pair<?, ?>) o).getSecond() == null ? targetType : null;
                    }
                } else if (((Pair<?, ?>) o).getFirst() instanceof Boolean) {
                    filter.classifier = Classifier.booleanMatch((Boolean) ((Pair<?, ?>) o).getFirst());
                    filter.target = ((Pair<?, ?>) o).getSecond() == null
                            ? target
                            : (ObservedConcept) ((Pair<?, ?>) o).getSecond();
                    filter.targetType = ((Pair<?, ?>) o).getSecond() == null ? targetType : null;
                }
            } else if (o instanceof ObservedConcept) {
                if (((ObservedConcept) o).getObservable().is(Type.COUNTABLE)) {
                    // filter placeholder which will be removed at resolution
                    filter.objectFilter = ((ObservedConcept) o);
                } else {
                    throw new KlabValidationException(
                            "unexpected non-countable classifier without a target: " + ((ObservedConcept) o).getObservable());
                }
            } else {

                filter.target = target;
                filter.targetType = targetType;

                if (o instanceof String) {
                    switch(o.toString()) {
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

        /*
         * simplify temporal filters: remove start/end and replace with init if context is not
         * occurrent; if occurrent but 1 timestep, leave start and remove end.
         */
        if (harvestedTimeSelectors.size() > 0) {
            // System.out.println("CHECK THIS FUCKA OUT");
        }

        return ret;
    }

    public List<String> getRowOrder() {
        return new ArrayList<>(rows.keySet());
    }

    public List<String> getColumnOrder() {
        return new ArrayList<>(columns.keySet());
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

    public INamespace getNamespace() {
        return namespace;
    }

    public String getIdentifier() {
        return identifier;
    }

    public TableCompiler contextualize(IRuntimeScope scope) {
        return new TableCompiler(this, scope);
    }

    public IObservable getTargetObservable() {
        return this.target == null ? null : this.target.getObservable();
    }

    public String getNumberFormat() {
        return numberFormat;
    }

}
