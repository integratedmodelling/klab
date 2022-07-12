package org.integratedmodelling.klab.resolution;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.model.Acknowledgement;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;

public class ResolutionScope implements IResolutionScope {

    /**
     * Each successful merge creates a link that is saved in the scope. At the end of the
     * resolution, all link are used to build the final dependency graph. Class is used by the
     * dataflow compiler, so it's package private.
     */
    public class Link {

        ResolutionScope target;
        int order;
        boolean partition;

        Link(ResolutionScope target) {
            this.target = target;
        }

        public ResolutionScope getSource() {
            return ResolutionScope.this;
        }

        public ResolutionScope getTarget() {
            return target;
        }

        public Link withOrder(int order) {
            this.order = order;
            return this;
        }

        public int getOrder() {
            return order;
        }

        @Override
        public String toString() {
            return getSource() + " <- " + target;
        }

        @Override
        public boolean equals(Object object) {
            return object == this || (object instanceof Link && ((Link) object).getSource().equals(getSource())
                    && target.equals(((Link) object).target));
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getSource().hashCode();
            result = prime * result + target.hashCode();
            return result;
        }

        public boolean isPartition() {
            return partition;
        }

        public Link withPartition(boolean b) {
            this.partition = b;
            return this;
        }
    }

    /*
     * The three main pieces of info in each scope: only the root node has none of these, other
     * nodes have one and only one of these.
     */
    private Observable observable;
    private Model model;
    private Acknowledgement observer;
    private Map<IConcept, Collection<IConcept>> roles = new HashMap<>();

    /*
     * When an artifact is here, it's from the context and will be turned into an 'input' actuator
     * in the dataflow. Scopes resolved with an artifact have a coverage of 1.
     */
    private ResolvedArtifact resolvedArtifact;

    /*
     * When resolving relationships, we carry around their source and target subjects to inform
     * resolution and enable the runtime to create their observations.
     */
    private Subject relationshipSource;
    private Subject relationshipTarget;

    /*
     * These two are built at merge() and thrown away if a resolution ends up empty.
     */
    Set<Link> links = new HashSet<>();
    Map<ObservedConcept, Set<ResolutionScope>> resolvedObservables = new HashMap<>();

    Map<ObservedConcept, List<IRankedModel>> resolutions = new HashMap<>();

    /*
     * We chain resolutions when we resolve change after the initialization resolution. In this case
     * we use all the previous resolutions to locate any resolved observables before we look for a
     * model. The logic is implemented in getObservable() which is called when creating a scope to
     * resolve an observable.
     */
    Set<ResolutionScope> previousResolution = new HashSet<>();
    // this will contain anything that will change due to occurrence, not the
    // occcurrents themselves.
    Set<IObservedConcept> occurrents;

    // additional resolutions for change in states that explicitly
    // include transition contextualizers in the initialization run.
    private List<ResolutionScope> occurrentResolutions;

    /**
     * If not null, this is a scope for a logical combination of resolutions.
     */
    LogicalConnector connector;

    /*
     * Cache of names of models being resolved upstream, to avoid recursive resolutions
     */
    Set<String> beingResolved = new HashSet<>();
    /*
     * These change during resolution and influence the choice of models
     */
    private Mode mode = Mode.RESOLUTION;
    private Namespace resolutionNamespace;

    /*
     * these do not change during an individual resolution.
     */

    /*
     * a secondary resolution may have the context set (if it's been created) or not (if it's being
     * resolved). Either one or the other are set and #getContextObservable() returns the
     * appropriate response.
     */
    private DirectObservation context;
    private Observable contextObservable;
    // this is set when resolving a new observation, which the dataflow will build
    // with this name.
    private String observationName;

    private Collection<String> scenarios = new ArrayList<>();
    private boolean interactive;

    // true = resolution is deferred to after instantiation
    private boolean deferred;

    private IMonitor monitor;
    private Scope originalScope;

    // we keep these around for speed. They could be removed.
    private ResolutionScope rootScope;
    private ResolutionScope rootContextScope;

    /**
     * if not null, the observable has been resolved through a value specified inline and requires
     * no further resolution.
     */
    private Object inlineValue;

    /*
     * The parent is only used during model ranking, to establish project and namespace nesting and
     * distance
     */
    private ResolutionScope parent;

    /*
     * this is only used to correctly merge in dependencies.
     */
    int mergedObservables = 0;

    /*
     * the coverage for this scope.
     */
    private Coverage coverage;

    /**
     * Any abstract predicates that have been resolved upstream will be stored here and propagate to
     * downstream resolutions.
     */
    private Map<IConcept, IConcept> resolvedPredicates = new HashMap<>();

    /*
     * Cached resolvers for specific observables in the current scale. Will be reassessed (by the
     * kbox) to match them to the scale of an instance created by an instantiator for our scale,
     * moving on to a full search only if resolvers are available but they don't cover the scale of
     * the instance. This is a set because the kbox will reassess all models based on the specific
     * instance being resolved.
     */
    private Map<ObservedConcept, Set<IRankedModel>> resolverCache = new HashMap<>();
    private boolean caching;
    private IModel contextModel;
    private boolean occurrent = false;
    private Set<ObservedConcept> resolving = new HashSet<>();
    private Map<IConcept, Set<IConcept>> resolvedPredicatesContext = new HashMap<>();
    private boolean deferToInherent;
    private RuntimeScope rootContextualizationScope;

    private void addResolvedScope(ObservedConcept concept, ResolutionScope scope) {
        Set<ResolutionScope> slist = resolvedObservables.get(concept);
        if (slist == null) {
            slist = new LinkedHashSet<>();
            concept.getData().put("resolved.observable.scope",
                    concept.getObservable().equals(this.observable) ? this : this.getAdditionalScope(concept.getObservable()));
            resolvedObservables.put(concept, slist);
        }
        slist.add(scope);
    }

    /**
     * Accept a model for resolution, populating the resolution map for all its observables with the
     * scopes resolved by it.
     * 
     * @param model
     */
    private void recordResolution(IObservable observable, Mode mode, ResolutionScope modelScope) {

        /*
         * main observable
         */
        addResolvedScope(new ObservedConcept(observable, mode), modelScope);

        resolverCache.putAll(modelScope.resolverCache);

        /*
         * change observable: add a preresolved model for this same coverage.
         */
        if (modelScope.model.changesIn(Dimension.Type.TIME, coverage.asScale())) {
            IObservable main = modelScope.model.getObservables().get(0);
            if (!main.is(Type.CHANGE) && !main.is(Type.PREDICATE) && !main.is(Type.PROCESS) && !main.is(Type.EVENT)) {
                /*
                 * models with temporally merged resources also handle change
                 */
                IObservable change = main.getBuilder(monitor).as(UnarySemanticOperator.CHANGE).buildObservable();
                ObservedConcept cchange = new ObservedConcept(change);
                Set<IRankedModel> resolvers = resolverCache.get(cchange);
                if (resolvers == null) {
                    resolvers = new LinkedHashSet<>();
                    resolverCache.put(cchange, resolvers);
                }
                resolvers.add(Models.INSTANCE.createChangeModel(main, change, modelScope.model, this));
            }
        }

        if (observable.is(Type.PROCESS)) {
            /*
             * Add all changing outputs to the occurrents set so that dependent transformations can
             * be scheduled when they change. Note that not all of these may be actually computed.
             */
            for (int i = 1; i < modelScope.model.getObservables().size(); i++) {
                this.occurrents.add(new ObservedConcept(modelScope.model.getObservables().get(i), Mode.RESOLUTION));
            }
        }

        if (!modelScope.model.isInstantiator()) {

            /*
             * additional observables
             */
            for (int i = 1; i < modelScope.model.getObservables().size(); i++) {
                addResolvedScope(new ObservedConcept(modelScope.model.getObservables().get(i),
                        modelScope.model.getObservables().get(i).is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION),
                        modelScope);
            }
        }
    }

    /**
     * The context model, if there, is the instantiator that has created the observation being
     * resolved. Actions and states will be compiled into the dataflow and affect the resolution of
     * subsequent dependencies.
     * 
     * @return
     */
    public IModel getContextModel() {
        return contextModel;
    }

    /**
     * Get a root scope based on the definition of an observation.
     * 
     * @param observer
     * @param monitor
     * @param scenarios
     * @return a root scope
     * @throws KlabException
     */
    public static ResolutionScope create(Acknowledgement observer, IMonitor monitor, Collection<String> scenarios) throws KlabException {
        return new ResolutionScope(observer, monitor, scenarios);
    }

    /**
     * Get an empty resolution scope. FOR TESTING ONLY.
     * 
     * @param monitor
     * @return
     */
    public static ResolutionScope create(IMonitor monitor) {
        return new ResolutionScope(monitor);
    }

    /**
     * Get an empty resolution scope with a specified coverage. FOR TESTING ONLY.
     * 
     * @param monitor
     * @return
     */
    public static ResolutionScope create(IMonitor monitor, IScale scale) {
        ResolutionScope ret = new ResolutionScope(monitor);
        if (scale != null) {
            ret.coverage = Coverage.full(scale);
            if (scale.isTemporallyDistributed()) {
                ret.occurrent = true;
            }
        }
        return ret;
    }

    /**
     * Get a root scope with the scale of an existing subject used as a context for the next
     * observations.
     * 
     * @param contextSubject
     * @param monitor
     * @param scenarios
     * @return a root scope
     * @throws KlabException
     */
    public static ResolutionScope create(Subject contextSubject, IMonitor monitor, Collection<String> scenarios)
            throws KlabException {
        return new ResolutionScope(contextSubject, monitor, scenarios);
    }

    public static ResolutionScope create(Subject contextSubject, IMonitor monitor, INamespace namespace,
            Collection<String> scenarios) throws KlabException {
        ResolutionScope ret = new ResolutionScope(contextSubject, monitor, scenarios);
        ret.resolutionNamespace = (Namespace) namespace;
        return ret;
    }

    private ResolutionScope(IMonitor monitor) throws KlabException {
        this.coverage = Coverage.empty(Scale.create());
        this.monitor = monitor;
    }

    private ResolutionScope(Subject contextSubject, IMonitor monitor, Collection<String> scenarios) throws KlabException {
        this.coverage = Coverage.full(contextSubject.getScale());
        this.context = contextSubject;
        this.occurrentResolutions = new ArrayList<>();
        this.occurrents = new HashSet<>();
        this.scenarios.addAll(scenarios);
        this.roles.putAll(monitor.getIdentity().getParentIdentity(ISession.class).getState().getRoles());
        this.resolutionNamespace = contextSubject.getNamespace();
        this.monitor = monitor;
        this.occurrent = contextSubject.getScope().isOccurrent() || this.coverage.isTemporallyDistributed();
        this.rootContextualizationScope = ((RuntimeScope) contextSubject.getScope()).getRootScope();
    }

    private ResolutionScope(Acknowledgement observer, IMonitor monitor, Collection<String> scenarios) throws KlabException {
        this.coverage = Coverage.full(Scale.create(observer.getContextualization().getExtents(monitor)));
        this.scenarios.addAll(scenarios);
        this.roles.putAll(monitor.getIdentity().getParentIdentity(ISession.class).getState().getRoles());
        this.resolutionNamespace = observer.getNamespace();
        this.occurrentResolutions = new ArrayList<>();
        this.occurrents = new HashSet<>();
        this.observer = observer;
        this.monitor = monitor;
        this.occurrent = this.coverage.isTemporallyDistributed();
    }

    /**
     * Copy the passed scope without copying the results of resolution.
     * 
     * @param other
     */
    private ResolutionScope(ResolutionScope other) {
        this(other, false);
    }

    private ResolutionScope(ResolutionScope other, double coverage) {
        this(other);
        this.coverage = new Coverage(this.coverage);
        this.coverage.setCoverage(coverage);
    }

    private ResolutionScope(IScale scale, double coverage, ResolutionScope other, boolean copyResolution) {
        copy(other, copyResolution);
        this.coverage = coverage == 1 ? Coverage.full(scale) : Coverage.empty(scale);
        this.occurrent = scale.isTemporallyDistributed();
    }

    private ResolutionScope(ResolutionScope other, boolean copyResolution) {
        copy(other, copyResolution);
    }

    private void copy(ResolutionScope other, boolean copyResolution) {
        this.scenarios.addAll(other.scenarios);
        this.rootContextualizationScope = other.rootContextualizationScope;
        this.resolutionNamespace = other.resolutionNamespace;
        this.occurrentResolutions = other.occurrentResolutions;
        this.occurrents = other.occurrents;
        this.mode = other.mode;
        this.interactive = other.interactive;
        this.monitor = other.monitor;
        this.parent = other;
        this.context = other.context;
        this.coverage = other.coverage;
        this.beingResolved.addAll(other.beingResolved);
        this.contextModel = other.contextModel;
        this.originalScope = other.originalScope;
        this.deferred = other.deferred;
        this.observationName = other.observationName;
        this.contextObservable = other.contextObservable;
        this.occurrent = other.occurrent;
        this.occurrents.addAll(other.occurrents);
        this.previousResolution.addAll(other.previousResolution);
        this.roles.putAll(other.roles);
        this.resolvedPredicates.putAll(other.resolvedPredicates);
        this.resolvedPredicatesContext.putAll(other.resolvedPredicatesContext);
        this.resolving.addAll(other.resolving);
        if (copyResolution) {
            this.observable = other.observable;
            this.model = other.model;
            this.observer = other.observer;
            this.links.addAll(other.links);
            this.resolvedObservables.putAll(other.resolvedObservables);
        }
        // this.resolvedObservables.putAll(other.resolvedObservables);
        this.resolutions = other.resolutions;
    }

    public final ResolutionScope empty() {
        return new ResolutionScope(this, 0.0);
    }

    public boolean isResolving(String modelName) {
        return beingResolved.contains(modelName);
    }

    public Observable getContextObservable() {
        return contextObservable == null ? (context == null ? null : context.getObservable()) : contextObservable;
    }

    public String getObservationName() {
        return observationName;
    }

    public Collection<Link> getLinks() {
        return links;
    }

    /**
     * Create a child coverage for a passed observable with the same scale but initial coverage set
     * at 0. Pass around any models that were cached explicitly to resolve specific observables.
     * 
     * @param observable
     * @param mode
     * @return a new scope for the passed observable
     */
    public ResolutionScope getChildScope(Observable observable, Mode mode) {

        ResolutionScope ret = new ResolutionScope(this);
        ret.observable = observable;
        ret.mode = mode;
        ret.resolverCache.putAll(this.resolverCache);
        ret.resolvedPredicates.putAll(observable.getResolvedPredicates());
        ret.resolvedPredicatesContext.putAll(observable.getResolvedPredicatesContext());
        ret.resolving.add(new ObservedConcept(observable, mode));

        /*
         * check if we already can resolve this (directly or indirectly), and if so, set coverage so
         * that it can be accepted as is. This should be a model; we should make the link, increment
         * the use count for the observable, and return coverage.
         */
        ResolutionScope previous = getObservable(observable, mode);
        if (previous != null) {
            ret.coverage = previous.coverage;
        } else {
            ret.coverage = Coverage.empty(ret.coverage);
        }

        return ret;
    }

    public ResolutionScope getChildScope(LogicalConnector connector) {
        ResolutionScope ret = new ResolutionScope(this);
        ret.connector = connector;
        ret.coverage = connector == LogicalConnector.UNION ? Coverage.full(ret.coverage) : Coverage.empty(ret.coverage);
        return ret;
    }

    public ResolutionScope getChildScope(Observable observable, Mode mode, Scale scale, IModel upstreamModel) {

        ResolutionScope ret = new ResolutionScope(scale, 1.0, this, false);
        ret.observable = observable;
        ret.mode = mode;
        ret.contextModel = upstreamModel;

        /*
         * check if we already can resolve this (directly or indirectly), and if so, set coverage so
         * that it can be accepted as is. This should be a model; we should make the link, increment
         * the use count for the observable, and return coverage.
         */
        ResolutionScope previous = getObservable(observable, mode);
        if (previous != null) {
            ret.coverage = previous.coverage;
        }

        return ret;
    }

    /**
     * Return a scope to resolve a relationship that will link the passed subjects.
     * 
     * @param observable must be a relationship observable
     * @param scale scale of the relationship
     * @param source the source subject
     * @param target the target subject
     * @return a new scope
     */
    public ResolutionScope getChildScope(Observable observable, Scale scale, Subject source, Subject target,
            IModel upstreamModel) {

        if (!observable.is(Type.RELATIONSHIP)) {
            throw new KlabInternalErrorException("cannot create scope for non-relationships with a source and target subject");
        }

        ResolutionScope ret = getChildScope(observable, Mode.RESOLUTION, scale, upstreamModel);

        ret.relationshipSource = source;
        ret.relationshipTarget = target;
        ret.contextModel = upstreamModel;

        return ret;
    }

    /**
     * @param model
     * @return a scope to resolve the passed model
     * @throws KlabException
     */
    public ResolutionScope getChildScope(Model model) throws KlabException {

        ResolutionScope ret = new ResolutionScope(this);

        ret.model = model;
        ret.resolutionNamespace = (Namespace) model.getNamespace();
        ret.beingResolved.add(model.getName());

        /*
         * models start with full coverage...
         */
        ret.coverage = Coverage.full(this.coverage);

        if (!model.getCoverage(monitor).isEmpty()) {
            /*
             * ...and redefine it based on their own coverage if they have any.
             */
            ret.coverage = ret.coverage.mergeExtents(
                    Coverage.full(Scale.createLike(ret.coverage, model.getCoverage(this.monitor).getExtents())),
                    LogicalConnector.INTERSECTION);
        }

        return ret;
    }

    /**
     * @param observer
     * @return a scope to resolve the passed observer
     * @throws KlabException
     */
    public ResolutionScope getChildScope(Acknowledgement observer) throws KlabException {

        ResolutionScope ret = new ResolutionScope(this);
        ret.observer = observer;
        ret.resolutionNamespace = (Namespace) observer.getNamespace();

        if (observer.getContextualization().hasScale()) {
            ret = new ResolutionScope(Scale.create(observer.getContextualization().getExtents(monitor)), 1.0, ret, false);
        } else {
            ret.coverage = new Coverage(this.coverage);
            ret.coverage.setCoverage(1.0);
        }

        return ret;
    }

    /**
     * @param observation
     * @return a scope to resolve the passed observer
     * @throws KlabException
     */
    public ResolutionScope getChildScope(IDirectObservation observation, Mode mode) {

        ResolutionScope ret = new ResolutionScope(this, true);
        ret.context = (DirectObservation) observation;
        ret.coverage = Coverage.full(observation.getScale());
        ret.mode = mode;

        return ret;
    }

    public Set<IObservedConcept> getOccurrents() {
        return this.occurrents;
    }

    /**
     * Scope for a pre-resolved observation, to use in dataflows that will create it
     * 
     * @param observer
     * @param mode
     * @return
     */
    public ResolutionScope getChildScope(IObservable observable, IDirectObservation context, IScale scale) {

        ResolutionScope ret = new ResolutionScope(this, true);
        ret.coverage = Coverage.full(scale);
        ret.context = (DirectObservation) context;
        ret.mode = Mode.RESOLUTION;
        return ret;
    }

    public ResolutionScope getContextualizedScope(IObservable context) {
        ResolutionScope ret = new ResolutionScope(this, true);
        ret.contextObservable = (Observable) context;
        return ret;
    }

    /**
     * Return a scope to resolve a new observation of the passed observable, which will have the
     * passed name. The context remains there to tell us which subject we are resolving the
     * observable in.
     * 
     * @param observable
     * @param name
     * @return
     */
    public ResolutionScope getChildScope(IObservable observable, IScale scale, String name) {

        ResolutionScope ret = new ResolutionScope(this, true);
        ret.contextObservable = (Observable) observable;
        ret.observationName = name;
        ret.coverage = Coverage.full(scale);
        ret.mode = Mode.RESOLUTION;
        return ret;
    }

    /**
     * @param observer
     * @return a scope to resolve the passed observer
     * @throws KlabException
     */
    public ResolutionScope getDeferredChildScope(IDirectObservation observer, Mode mode) {
        ResolutionScope ret = getChildScope(observer, mode);
        ret.deferred = true;
        return ret;
    }

    @Override
    public Collection<String> getScenarios() {
        return scenarios;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public Namespace getResolutionNamespace() {
        return resolutionNamespace;
    }

    @Override
    public Mode getMode() {
        return mode;
    }

    @Override
    public boolean isInteractive() {
        return interactive;
    }

    public Observable getObservable() {
        return observable;
    }

    @Override
    public ResolvedArtifact getResolvedArtifact() {
        return resolvedArtifact;
    }

    // for the dataflow compiler
    public IResolvable getResolvable() {

        // lovin'it
        return resolvedArtifact != null
                ? resolvedArtifact
                : (observable != null ? observable : (model != null ? model : (observer != null ? observer : null)));
    }

    @Override
    public IMonitor getMonitor() {
        return monitor;
    }

    public String toString() {
        String ret = "<" + (observable == null ? "" : ("OBS " + observable)) + (model == null ? "" : ("MOD " + model.getName()))
                + (observer == null ? "" : ("SUB " + observer.getName()));
        return ret + (ret.length() == 1 ? "ROOT " : " ") + getCoverage().getCoverage() + ">";
    }

    /**
     * Link a scope but leave the definition of the resolution (including the coverage and the
     * catalog update) to an upstream resolver. Used when models are accepted but we still don't
     * know if their contribution finalizes the needed coverage.
     * 
     * @param childScope
     * @param computation
     */
    Link link(ResolutionScope childScope) {
        Link ret = null;
        links.addAll(childScope.links);
        links.add(ret = new Link(childScope));
        if (childScope.model != null && this.observable != null) {
            this.recordResolution(this.observable, this.mode, childScope);
        }
        return ret;
    }

    /**
     * Merge an accepted child scope (which has, in turn, been merged before this is called).
     * 
     * @param childScope
     * @return true if the merge did anything significant
     */
    boolean merge(ResolutionScope childScope) {

        boolean successful = childScope.coverage.isRelevant();

        /*
         * Accept the observation and merge in the model if any
         */

        /*
         * my coverage becomes the child's
         */
        this.coverage = childScope.coverage;

        if (successful) {

            // when I am OBS and the child is MOD, make links
            if (this.observable != null && childScope.getModel() != null) {

                recordResolution(this.observable, this.mode, childScope);
                //
                // for (IObservable o : childScope.getResolvedObservables(this.observable)) {
                // resolvedObservables.add(childScope.getAdditionalScope(o));
                // }
            }

            // // when the child is OBS, update all resolution records with the new
            // observable
            // if (childScope.getObservable() != null) {
            // // usage count goes up every time an observable is explicitly merged.
            // resolvedObservables.add(childScope);
            // }

            links.addAll(childScope.links);
            links.add(new Link(childScope));

            /*
             * Record any observables already resolved in the dependency graph so far. Must have the
             * model's scale if not existing already.
             */
            resolvedObservables.putAll(childScope.resolvedObservables);

            /*
             * add any unused additional resolvers implied by other models in case they're needed
             * for later resolutions.
             */
            resolverCache.putAll(childScope.resolverCache);
        }

        return successful;
    }

    @Override
    public boolean occursInScope(IObservedConcept observable) {
        return this.occurrents.contains(observable)
                || getContextContextualizationScope().getImplicitlyChangingObservables().contains(observable);
    }

    /**
     * Return a new scope for the passed observable, with same scale and coverage as ours. Used for
     * yet unused observables provided by models besides the focal one.
     * 
     * @param o
     * @return
     */
    private ResolutionScope getAdditionalScope(IObservable o) {
        ResolutionScope ret = new ResolutionScope(this);
        ret.observable = (Observable) o;
        ret.mode = o.getDescriptionType().getResolutionMode();
        return ret;
    }

    public Acknowledgement getObserver() {
        return observer;
    }

    /**
     * OR the passed scope, already established as relevant, and return whether it causes enough
     * coverage gain to bother. If so, the model should be linked to the scope.
     * 
     * @param child
     * @return true if gain > 0, meaning the child coverage was merged into us.
     */
    public boolean or(ResolutionScope child) {
        this.coverage = (Coverage) this.coverage.mergeExtents(child.coverage, LogicalConnector.UNION);
        return this.coverage.getGain() > 0;
    }

    public Observable findObservable() {
        if (observable != null) {
            return observable;
        } else if (observer != null) {
            return observer.getObservable();
        } else if (model != null) {
            return (Observable) model.getObservables().get(0);
        } else if (context != null) {
            return context.getObservable();
        }
        return null;
    }

    /**
     * Return how far away in the resolution is the passed namespace; -1 if not found.
     * 
     * @param ns
     * @return namespace distance
     */
    public int getNamespaceDistance(INamespace ns) {

        if (ns == null) {
            return -1;
        }

        int ret = 0;
        if (resolutionNamespace != null && !this.resolutionNamespace.getId().equals(ns.getId())) {
            ResolutionScope rc = parent;
            while(rc != null) {
                ret++;
                if (rc.resolutionNamespace != null && rc.resolutionNamespace.getId().equals(ns.getId())) {
                    return ret;
                }
                rc = rc.parent;
            }
        }

        return -1;
    }

    public int getProjectDistance(IProject ns) {

        if (ns == null || this.resolutionNamespace == null) {
            return -1;
        }

        if (this.resolutionNamespace.getProject() != null
                && this.resolutionNamespace.getProject().getName().equals(ns.getName())) {
            return 0;
        }

        int ret = 0;
        if (this.resolutionNamespace.getProject() == null
                || !this.resolutionNamespace.getProject().getName().equals(ns.getName())) {
            ResolutionScope rc = parent;
            while(rc != null) {
                ret++;
                if (rc.resolutionNamespace != null && rc.resolutionNamespace.getProject() != null
                        && rc.resolutionNamespace.getProject().getName().equals(ns.getName())) {
                    return ret;
                }
                rc = rc.parent;
            }
        }

        return -1;
    }

    @Override
    public IDirectObservation getContext() {
        return context;
    }

    /**
     * Get the existing dependency for a passed observable and mode, or null if none exists.
     * Observable is looked for in this first, then in any previously resolved scopes.
     * 
     * @param observable
     * @param mode
     * @param allowIndirectObservation
     * @return the existing scope for the passed parameters or null
     */
    public ResolutionScope getObservable(Observable observable, Mode mode) {

        /*
         * these are the currently resolved observables, which are not passed to children and may
         * not be accepted if the current resolution branch fails.
         */
        for (ObservedConcept o : resolvedObservables.keySet()) {
            if (((Observable) o.getObservable()).resolvesStrictly(observable) && o.getMode() == mode) {
                return (ResolutionScope) o.getData().get("resolved.observable.scope");
            }
        }
        /*
         * this is only filled in with previously resolved observables for the same context, which
         * are passed to children and don't change during this resolution.
         */
        for (ResolutionScope o : previousResolution) {
            if (o.observable.resolvesStrictly(observable) && o.mode == mode) {
                return o;
            }
        }
        return null;

    }

    @Override
    public Observable getResolvedObservable(IObservable observable, Mode mode) {
        for (ObservedConcept o : resolvedObservables.keySet()) {
            if (((Observable) o.getObservable()).resolvesStrictly((Observable) observable) && o.getMode() == mode) {
                return (Observable) o.getObservable();
            }
        }
        return null;
    }

    // /*
    // * (non-Javadoc)
    // *
    // * @see java.lang.Object#hashCode()
    // */
    // @Override
    // public int hashCode() {
    // final int prime = 31;
    // int result = 1;
    // result = prime * result + ((mode == null) ? 0 : mode.hashCode());
    // result = prime * result + ((model == null) ? 0 : model.hashCode());
    // result = prime * result + ((observable == null) ? 0 : observable.hashCode());
    // result = prime * result + ((observer == null) ? 0 : observer.hashCode());
    // return result;
    // }
    //
    // /*
    // * (non-Javadoc)
    // *
    // * @see java.lang.Object#equals(java.lang.Object)
    // */
    // @Override
    // public boolean equals(Object obj) {
    // if (obj == this) {
    // return true;
    // }
    // if (obj == null) {
    // return false;
    // }
    // if (getClass() != obj.getClass()) {
    // return false;
    // }
    // ResolutionScope other = (ResolutionScope) obj;
    // if (mode != other.mode) {
    // return false;
    // }
    // if (model == null) {
    // if (other.model != null) {
    // return false;
    // }
    // } else if (!model.equals(other.model)) {
    // return false;
    // }
    // if (observable == null) {
    // if (other.observable != null) {
    // return false;
    // }
    // } else if (!observable.equals(other.observable)) {
    // return false;
    // }
    // if (observer == null) {
    // if (other.observer != null) {
    // return false;
    // }
    // } else if (!observer.equals(other.observer)) {
    // return false;
    // }
    // return true;
    // }

    public void setContext(IDirectObservation target) {
        this.context = (DirectObservation) target;
    }

    /**
     * Called on an empty coverage to accept anyway, setting coverage to 1.0 and removing any
     * remnants from a possibly failed resolution.
     */
    public void acceptEmpty() {
        this.coverage.setCoverage(1.0);
        this.model = null;
        this.links.clear();
        this.resolvedObservables.clear();
    }

    public void acceptArtifact(Observable observable, IObservation artifact, String artifactId) {
        this.resolvedArtifact = new ResolvedArtifact(observable, artifact, artifactId);
        this.coverage.setCoverage(1.0);
    }

    @Override
    public Coverage getCoverage() {
        return this.coverage;
    }

    /**
     * True if this scope is being used to build a cache, indicating that we shouldn't try to use
     * the cache when resolving.
     * 
     * @return
     */
    public boolean isCaching() {
        return this.caching;
    }

    /**
     * Return the (possibly empty) set of resolver models that was cached after the first
     * instantiation, along with the scale of the instantiator.
     * 
     * @param observable
     * @return
     */
    public Pair<Scale, Collection<IRankedModel>> getPreresolvedModels(IObservable observable) {

        if (mode != Mode.RESOLUTION) {
            return null;
        }

        ObservedConcept obc = new ObservedConcept(observable, Mode.RESOLUTION);

        /*
         * look up in parents as far as the observable is the same (or the incarnated abstract
         * observable is) and the mode is resolution. If the observable is found earlier, return
         * that.
         */
        ResolutionScope target = this;
        while(target != null /*
                              * && target.observable != null &&
                              * (target.observable.equals(observable) ||
                              * target.observable.incarnatesSame(observable))
                              */) {
            if (target.mode == Mode.INSTANTIATION) {
                break;
            }
            if (target.observable != null
                    && !(target.observable.equals(observable) || target.observable.incarnatesSame(observable))) {
                break;
            }
            if (target.resolverCache.get(obc) != null) {
                return new Pair<>(target.coverage.asScale(), target.resolverCache.get(obc));
            }
            target = target.parent;
        }
        return (target == null || target.resolverCache.get(obc) == null)
                ? null
                : new Pair<>(target.coverage.asScale(), target.resolverCache.get(obc));
    }

    @Override
    public ISubject getRelationshipSource() {
        return relationshipSource;
    }

    @Override
    public ISubject getRelationshipTarget() {
        return relationshipTarget;
    }

    public boolean isBeingResolved(IConcept observable, Mode mode) {
        if (this.observable != null && this.observable.getType().equals(observable) && this.mode == mode) {
            return true;
        }
        return parent == null ? false : parent.isBeingResolved(observable, mode);
    }

    public ResolutionScope getChildScope(Observable observable, Mode mode, IObservation artifact, String artifactId) {
        ResolutionScope ret = getChildScope(observable, mode);
        ret.acceptArtifact(observable, artifact, artifactId);
        return ret;
    }

    public Object getInlineValue() {
        return inlineValue;
    }

    public void setInlineValue(Object inlineValue) {
        this.inlineValue = inlineValue;
        this.coverage.setCoverage(1.0);
    }

    public ISession getSession() {
        return monitor.getIdentity().getParentIdentity(ISession.class);
    }

    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    @Override
    public Scope getOriginalScope() {
        return originalScope;
    }

    public void setOriginalScope(Scope originalScope) {
        this.originalScope = originalScope;
    }

    @Override
    public Scale getScale() {
        return Scale.create(getCoverage().getExtents());
    }

    /*
     * for when scopes are reused in different scales
     */
    public ResolutionScope rescale(IScale scale) {
        ResolutionScope ret = new ResolutionScope(this);
        ret.coverage = Coverage.full(scale);
        return ret;
    }

    public boolean isDeferred() {
        return deferred;
    }

    public void setDeferred(boolean deferred) {
        this.deferred = deferred;
    }

    /**
     * Determine if this scope requires the passed observable to be contextualized to a different
     * object. If so, return the corresponding observable. The resolver will store the actual
     * observable for resolution after the dataflow has created each of the corresponding objects.
     * 
     * @param observable2
     * @return
     */
    public Observable getDeferredObservableFor(Observable observable2) {

        if (this.deferToInherent) {
            IConcept inherent = Observables.INSTANCE.getDirectInherentType(observable2.getType());
            if (inherent != null) {
                return Observable.promote(inherent);
            }
        }

        if (!observable2.is(Type.OBSERVABLE)) {
            // attribute resolvers and the like
            return null;
        }

        if (observable2.equals(this.observable)) {
            // resolving self
            return null;
        }

        /*
         * using getContext() lets any indirect inherency pass through
         */
        IConcept context = observable2.getContext();

        if (!isDeferred() && context != null && getContextObservable() != null && !getContextObservable().getType().is(context)) {

            /*
             * check if this is the observable of a learning model that has an archetype compatible
             * with the context. In that case, we let it pass as is, as the learning process will
             * deal with the distribution.
             */
            if (observable2.getReferencedModel(this) != null && observable2.getReferencedModel(this).isLearning()) {
                for (IObservable archetype : ((Model) observable2.getReferencedModel(this)).getArchetypes()) {
                    if (archetype.is(context)) {
                        return null;
                    }
                }
            }

            monitor.info("Context of " + observable2.getType().getDefinition() + " (" + context.getDefinition()
                    + ") is incompatible with current context (" + getContextObservable().getType().getDefinition()
                    + "): resolving " + context.getDefinition() + " and deferring resolution");

            return Observable.promote(context);
        }

        return null;
    }

    public ResolutionScope getRootScope() {
        if (rootScope == null) {
            rootScope = this;
            while(rootScope.parent != null) {
                rootScope = rootScope.parent;
            }
        }
        return rootScope;
    }

    // root scope for the current context
    public ResolutionScope getRootContextScope() {
        if (rootContextScope == null) {
            rootContextScope = this;
            while(rootContextScope.parent != null && rootContextScope.context.equals(rootContextScope.parent.context)) {
                rootContextScope = rootContextScope.parent;
            }
        }
        return rootContextScope;
    }

    public void setOccurrent(boolean b) {
        getRootScope().occurrent = b;
    }

    @Override
    public boolean isOccurrent() {
        return getRootScope().occurrent;
    }

    /**
     * Visit the entire resolution tree and report the resolved observables in the order they have
     * been resolved. Sorts by observable concept name before returning, for debugging purposes.
     * 
     * @param types
     * @return
     */
    public Collection<ObservedConcept> getResolved(IKimConcept.Type... types) {

        LinkedHashSet<ObservedConcept> ret = new LinkedHashSet<>();
        collectResolved(getRootContextScope(), types, ret);

        /*
         * not useful except to get a constant ordering for debugging
         */
        List<ObservedConcept> sorted = new ArrayList<>(ret);
        Collections.sort(sorted, new Comparator<ObservedConcept>(){

            @Override
            public int compare(ObservedConcept o1, ObservedConcept o2) {
                return o1.getObservable().toString().compareTo(o2.getObservable().toString());
            }
        });

        return sorted;
    }

    private void collectResolved(ResolutionScope scope, Type[] types, LinkedHashSet<ObservedConcept> ret) {

        for (Link link : scope.links) {
            collectResolved(link.getTarget(), types, ret);
        }

        if (scope.observable != null) {
            boolean ok = types == null;
            if (!ok) {
                for (Type type : types) {
                    if (scope.observable.is(type)) {
                        ret.add(new ObservedConcept(scope.observable, scope.mode, scope));
                        break;
                    }
                }
            }
        }

    }

    public void dump(PrintStream out) {

        for (Link link : links) {
            out.println(link);
        }

        for (ResolutionScope occurrent : occurrentResolutions) {
            out.println("----");
            occurrent.dump(out);
        }

    }

    /**
     * Change models resolved after the initialization resolution to address change after defining
     * the resolution as occurrent. Must be compiled in the dataflow after the initialization
     * sequence and in the same order as given here.
     * 
     * @return
     */
    public List<ResolutionScope> getOccurrentResolutions() {
        return occurrentResolutions;
    }

    /**
     * Publish the resolutions in the links into a storage that is passed to children and consulted
     * to ensure that previously resolved observables are not resolved again. This is used when
     * resolving change or any observable that is evaluated after a full resolution has been done.
     * 
     * @param scope
     * @return
     */
    public ResolutionScope acceptResolutions(ResolutionScope scope, Namespace namespace) {
        ResolutionScope ret = new ResolutionScope(this);
        ret.resolverCache.putAll(scope.resolverCache);
        for (Link link : links) {
            if (link.getSource().getObservable() != null) {
                ret.previousResolution.add(link.getSource());
            }
        }
        ret.resolutionNamespace = namespace;
        return ret;
    }

    @Override
    public Map<IConcept, Collection<IConcept>> getRoles() {
        return this.roles;
    }

    @Override
    public Map<IConcept, IConcept> getResolvedPredicates() {
        return resolvedPredicates;
    }

    /**
     * Establish the model and namespace of resolution programmatically. Used only when resolving
     * attributes manually within a resolution.
     * 
     * @param model
     * @return
     */
    public ResolutionScope withModel(Model model) {
        this.model = model;
        if (model != null) {
            this.resolutionNamespace = (Namespace) model.getNamespace();
        }
        return this;
    }

    /**
     * Notify each new resolution for documentation. Only one map of these is kept per context.
     * 
     * @param observable
     * @param result
     */
    public void notifyResolution(IObservable observable, List<IRankedModel> result, Mode mode) {
        // only notify successful resolutions because otherwise alternative strategies
        // won't work
        if (result.size() > 0) {
            this.resolutions.put(new ObservedConcept(observable, mode), result);
        }
    }

    public IModel findResolvedModel(String modelId) {
        for (List<IRankedModel> resolved : this.resolutions.values()) {
            for (IRankedModel model : resolved) {
                if (modelId.equals(model.getName())) {
                    return model;
                }
            }
        }
        return null;
    }

    public Map<ObservedConcept, List<IRankedModel>> getResolutions() {
        return this.resolutions;
    }

    public boolean isResolving(IObservable observable, Mode mode) {
        return this.resolving.contains(new ObservedConcept(observable, mode));
    }

    public Map<IConcept, Set<IConcept>> getResolvedPredicatesContext() {
        return this.resolvedPredicatesContext;
    }

    public boolean hasResolved(IObservable toResolve) {
        return resolutions.containsKey(new ObservedConcept(toResolve));
    }

    public boolean hasResolvedSuccessfully(IObservable toResolve) {
        ObservedConcept obs = new ObservedConcept(toResolve);
        if (resolutions.containsKey(obs)) {
            return !resolutions.get(obs).isEmpty();
        }
        return false;
    }

    public ResolutionScope deferToInherent() {
        ResolutionScope ret = new ResolutionScope(this, true);
        ret.deferToInherent = true;
        return ret;
    }

    // TODO clean this stuff up - there are three similar functions
    public void setRootContextualizationScope(RuntimeScope ret) {
        this.rootContextualizationScope = ret;
    }

    // TODO clean this stuff up - there are three similar functions
    public RuntimeScope getRootContextualizationScope() {
        return this.rootContextualizationScope;
    }

    public RuntimeScope getContextContextualizationScope() {
        return (RuntimeScope) getRootContextScope().getContext().getScope();
    }

    // TODO clean this stuff up - there are three similar functions
    public void addPredicateResolutionDataflow(Dataflow dataflow) {
        this.rootContextualizationScope.addPrecontextualizationDataflow(dataflow);;
    }

}
