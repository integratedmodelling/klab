package org.integratedmodelling.klab.components.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import org.apache.commons.collections.IteratorUtils;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Roles;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.auth.ITaskIdentity;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression.Scope;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.View;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.Builder;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.ValuePresentation;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.IVariable;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IInspector;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.components.runtime.observations.Configuration;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroupView;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.components.time.extents.Scheduler;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.storage.MediatingState;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.data.table.TableValue;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Actuator.Computation;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.documentation.style.StyleDefinition;
import org.integratedmodelling.klab.engine.runtime.EventBus;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.SessionState;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.engine.runtime.code.ExpressionScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.IntelligentMap;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.owl.ObservableBuilder;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.KnowledgeViewReference;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.Triple;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * A runtime scope is installed in the root subject to keep track of what happens during
 * contextualization. Children of the root scope are used across the entire lifetime of a context
 * and carry all the information about the runtime environment during computation.
 * 
 * @author ferdinando.villa
 */
public class RuntimeScope extends AbstractRuntimeScope {

    INamespace namespace;
    Provenance provenance;
    EventBus eventBus;
    // ConfigurationDetector configurationDetector;
    Graph<IDirectObservation, IRelationship> network;
    Structure structure;
    Map<String, IArtifact> catalog;
    RuntimeScope parent;
    IArtifact target;
    IScale scale;
    IKimConcept.Type artifactType;
    Map<String, IObservable> semantics;
    IObservable targetSemantics;
    String targetName;
    ISubject rootSubject;
    IDirectObservation contextSubject;
    Map<String, IObservation> observations;
    IScheduler scheduler;
    Report report;
    // set only by the actuator, relevant only in instantiators with attributes
    IModel model;
    Set<String> notifiedObservations;
    Map<IConcept, ObservationGroup> groups;
    Map<String, IVariable> symbolTable = new HashMap<>();
    IntelligentMap<Pair<String, IKimExpression>> behaviorBindings;
    Set<String> watchedObservations = null;
    IObserver<?> observer = null;
    
    /*
     * info that may be filled in when contextualizing particular objects
     */
    IDirectObservation relationshipSource;
    IDirectObservation relationshipTarget;
    String directObservationName;
    ObservationGroup currentGroup;
    IMetadata objectMetadata;
    INotification.Mode notificationMode;
    Collection<IObservation> configurationTargets;

    // // cache for repeated dataflow resolutions
    // Map<ObservedConcept, List<Pair<ICoverage, Dataflow>>> dataflowCache = new
    // HashMap<>();
    private Actuator actuator;
    private boolean occurrent;
    private Map<String, IKnowledgeView> views;
    private Map<String, IKnowledgeView> viewsByUrn;
    private Map<String, Object> contextData;
    private Map<String, Object> globalData;

    /**
     * This is used during <em>contextualization</em> of previously characterized abstract
     * dependencies, mapping each abstract predicate to its specific incarnation in the scope.
     */
    Map<IConcept, IConcept> resolvedPredicates = new HashMap<>();

    /**
     * Get a child scope for the entire context. Must be called only on the root scope.
     * 
     * @param actuator
     * @param scope
     * @param scale
     * @param monitor
     * @return
     */
    @Override
    public RuntimeScope getContextScope(Actuator actuator, IResolutionScope scope, IScale scale,
            IDataflow<?> dataflow,
            IMonitor monitor) {

        RuntimeScope ret = new RuntimeScope((ResolutionScope) scope);
        ret.copyDataflowInfo(this);
        ret.setRootDataflow((Dataflow) dataflow, null);
        ret.implicitlyChangingObservables = this.implicitlyChangingObservables;
        ret.parent = this;
        ret.observer = this.observer;
        ret.catalog = new HashMap<>();
        ret.actuatorData = new HashMap<>();
        ret.globalData = new HashMap<>();
        ret.contextData = new HashMap<>();
        ret.behaviorBindings = new IntelligentMap<>();
        ret.report = new Report(this, scope, monitor.getIdentity().getParentIdentity(ISession.class).getId());
        ret.observations = new HashMap<>();
        ret.network = new DefaultDirectedGraph<>(IRelationship.class);
        ret.structure = new Structure();
        ret.provenance = new Provenance(this);
        ret.notifiedObservations = new HashSet<>();
        ret.groups = new HashMap<>();
        ret.monitor = monitor;
        ret.namespace = actuator.getNamespace();
        ret.scale = scale;
        ret.targetName = actuator.isPartition() ? actuator.getPartitionedTarget() : actuator.getName();
        ret.actuator = actuator;
        ret.targetSemantics = actuator.getObservable();
        ret.artifactType = Observables.INSTANCE.getObservableType(actuator.getObservable(), true);
        ret.dataflow = actuator.getDataflow();
        ret.watchedObservations = Collections.synchronizedSet(new HashSet<>());
        ret.views = new LinkedHashMap<>();
        ret.viewsByUrn = new LinkedHashMap<>();
        ret.concreteIdentities = this.concreteIdentities;

        // store and set up for further resolutions
        ret.resolutionScope = (ResolutionScope) scope;
        ret.semantics = new HashMap<>();
        ret.semantics.put(actuator.getName(), this.targetSemantics);

        for (IActuator a : actuator.getActuators()) {
            if (!((Actuator) a).isExported()) {
                String id = a.getAlias() == null ? a.getName() : a.getAlias();
                ret.semantics.put(id, ((Actuator) a).getObservable());
            }
        }
        for (IActuator a : actuator.getOutputs()) {
            String id = a.getAlias() == null ? a.getName() : a.getAlias();
            ret.semantics.put(id, ((Actuator) a).getObservable());
        }

        IArtifact target = ret.createTarget((Actuator) actuator, scope.getScale(), scope, null);
        getActuatorData(actuator).target = (IObservation) target;
        if (target instanceof IDirectObservation) {
            ((ResolutionScope) scope).setContext((IDirectObservation) target);
        }

        return ret;
    }

    /**
     * Creates a localized context with the passed ephemeral variables plus all the data artifacts
     * in the value map, indexed by their local name.
     * 
     * @param scope
     * @param variables
     */
    RuntimeScope(RuntimeScope scope, Map<String, IVariable> variables) {
        this(scope);
        this.model = scope.model;
        this.getVariables().putAll(variables);
        for (Pair<String, IDataArtifact> state : getArtifacts(IDataArtifact.class)) {
            this.put(state.getFirst(), state.getSecond());
        }
    }

    RuntimeScope(RuntimeScope context) {
        super(context);
        this.namespace = context.namespace;
        this.provenance = context.provenance;
        this.eventBus = context.eventBus;
        this.network = context.network;
        this.structure = context.structure;
        this.monitor = context.monitor;
        this.catalog = context.catalog;
        this.actuatorData = context.actuatorData;
        this.groups = context.groups;
        this.scale = context.scale;
        this.artifactType = context.artifactType;
        this.semantics = context.semantics;
        this.parent = context.parent;
        this.report = context.report;
        this.resolutionScope = context.resolutionScope;
        this.targetSemantics = context.targetSemantics;
        this.targetName = context.targetName;
        this.rootSubject = context.rootSubject;
        this.contextSubject = context.contextSubject;
        this.observations = context.observations;
        this.actuator = context.actuator;
        this.target = context.target;
        this.notifiedObservations = context.notifiedObservations;
        this.dataflow = context.dataflow;
        this.behaviorBindings = context.behaviorBindings;
        this.watchedObservations = context.watchedObservations;
        this.views = context.views;
        this.viewsByUrn = context.viewsByUrn;
        this.concreteIdentities = context.concreteIdentities;
        this.resolvedPredicates.putAll(context.resolvedPredicates);
        this.notificationMode = context.notificationMode;
        this.contextData = context.contextData;
        this.globalData = context.globalData;
        this.observer = context.observer;
    }

    private RuntimeScope(ResolutionScope resolutionScope) {
        super(null, resolutionScope, resolutionScope.getMonitor());
    }

    @Override
    public IRuntimeScope createChild(IObservable indirectTarget) {

        RuntimeScope ret = new RuntimeScope(this);

        ret.parent = this;
        ret.targetName = indirectTarget.getName();
        ret.artifactType = Observables.INSTANCE.getObservableType(indirectTarget, true);
        ret.semantics = new HashMap<>();
        ret.targetSemantics = indirectTarget;
        ret.monitor = monitor;
        ret.semantics.put(ret.targetName, ret.targetSemantics);
        if (this.target instanceof IDirectObservation) {
            ret.contextSubject = (IDirectObservation) this.target;
        } else if (ret.contextSubject == null) {
            // this only happens when the father is root.
            ret.contextSubject = rootSubject;
        }

        ret.resolvedPredicates.putAll(((Observable) indirectTarget).getResolvedPredicates());

        /*
         * if not within a partition, the target has been created by the upstream createChild(...
         * Actuator ...), so we just set it from the catalog.
         */
        ret.target = getArtifact(ret.targetName);
        if (ret.target == null) {
            ret.target = createTarget(indirectTarget);
        }
        return ret;
    }

    @Override
    public Provenance getProvenance() {
        return provenance;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public Collection<IRelationship> getOutgoingRelationships(IDirectObservation observation) {
        return network.outgoingEdgesOf(observation);
    }

    @Override
    public Collection<IRelationship> getIncomingRelationships(IDirectObservation observation) {
        return network.incomingEdgesOf(observation);
    }

    @Override
    public IDirectObservation getParentOf(IObservation observation) {
        return (IDirectObservation) structure.getLogicalParent(observation);
    }

    @Override
    public Collection<IObservation> getChildrenOf(IObservation observation) {
        return getChildren(observation, IObservation.class);
    }

    @Override
    public IDirectObservation getSourceSubject(IRelationship relationship) {
        return network.getEdgeSource(relationship);
    }

    @Override
    public IDirectObservation getTargetSubject(IRelationship relationship) {
        return network.getEdgeTarget(relationship);
    }

    @Override
    public INamespace getNamespace() {
        return namespace;
    }

    @Override
    public IArtifact getTargetArtifact() {
        return target == null ? this.catalog.get(this.targetName) : target;
    }

    private IArtifact findInCatalog(String name) {

        IArtifact ret = null;
        String referenceName = name;
        if (model != null) {
            IObservable observable = model.getObservableFor(name);
            if (observable != null) {
                referenceName = observable.getReferenceName();
            }
        }

        ret = catalog.get(referenceName);
        if (ret == null) {
            /*
             * lenient, allows us to also use the reference name when renamed. This is crucial for
             * those contextualizers that work with the reference name passed as a parameter. It is
             * important that this is the second choice in case of extremely unlikely conflicts.
             */
            ret = catalog.get(name);
        }

        if (ret /* still */ == null && model == null) {
            /*
             * look for the simple observable name instead of the reference name in case the model
             * was null
             */
            for (IArtifact artifact : catalog.values()) {
                if (artifact instanceof IObservation
                        && name.equals(((IObservation) artifact).getObservable().getName())) {
                    ret = artifact;
                    break;
                }
            }
        }

        return ret;
    }

    @Override
    public IArtifact getArtifact(String localName) {
        IArtifact ret = findInCatalog(localName);
        if (ret == null) {
            ret = this.views.get(localName);
        }
        if (ret == null) {
            ret = this.viewsByUrn.get(localName);
        }
        return ret;
    }

    @Override
    public String getTargetName() {
        return targetName;
    }

    @Override
    public RuntimeScope copy() {
        RuntimeScope ret = new RuntimeScope(this);
        return ret;
    }

    @Override
    public void setData(String name, IArtifact data) {

        if (catalog.get(name) != null) {
            structure.replace(catalog.get(name), data);
        }
        catalog.put(name, data);
        if (data instanceof Observation && observations.containsKey(data.getId())) {
            observations.put(data.getId(), (IObservation) data);
        }
    }

    public void set(String name, Object value) {
        this.put(name, value);
    }

    @Override
    public IMonitor getMonitor() {
        return monitor;
    }

    @Override
    public IConfiguration newConfiguration(IConcept configurationType, Collection<IObservation> targets,
            IMetadata metadata) {

        if (!configurationType.is(Type.CONFIGURATION)) {
            throw new IllegalArgumentException(
                    "RuntimeContext: cannot create a non-configuration with newConfiguration()");
        }

        Observable observable = Observable.promote(configurationType);

        /*
         * these are one-off so no caching of resolvers needed
         */
        IConfiguration ret = null;
        ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
        ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity())
                .createChild(
                        "Resolution of configuration " + Concepts.INSTANCE.getDisplayName(configurationType));
        ResolutionScope scope = Resolver.create(this.dataflow).resolve(observable, this.resolutionScope,
                Mode.RESOLUTION, scale,
                model);
        if (scope.getCoverage().isRelevant()) {
            Dataflow dataflow = Dataflows.INSTANCE.compile(
                    "local:task:" + session.getId() + ":" + subtask.getId(), scope,
                    this.actuator);
            dataflow.setModel((Model) model);

            RuntimeScope runtimeScope = new RuntimeScope(this).withMetadata(metadata)
                    .withConfigurationTargets(targets);

            ret = (IConfiguration) dataflow.run(scale, (Actuator) this.actuator, runtimeScope);

            runtimeScope.notifyDataflowChanges(runtimeScope);
        }
        return ret;
    }

    private RuntimeScope withConfigurationTargets(Collection<IObservation> targets) {
        this.configurationTargets = targets;
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IArtifact> T resolve(IObservable observable, IDirectObservation observation,
            ITaskTree<?> task, Mode mode,
            IActuator parentDataflow) {

        ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
        Dataflow dataflow = getDataflow(observable, mode, observation.getScale(), observation, (geometry) -> {

            Dataflow df = null;

            ResolutionScope scope = Resolver.create(this.dataflow).resolve((Observable) observable,
                    this.resolutionScope.getDeferredChildScope(observation, mode), mode,
                    observation.getScale(), model);

            if (scope.getCoverage().isRelevant()) {
                df = Dataflows.INSTANCE.compile("local:task:" + session.getId() + ":" + task.getId(), scope,
                        parentDataflow);
            }

            return df;
        });

        IArtifact ret = null;
        if (dataflow == null) {
            if (observable.isOptional()) {
                monitor.warn("cannot resolve optional observable " + observable.getDefinition() + " in "
                        + observation);
            } else {
                monitor.error("cannot resolve mandatory observable " + observable.getDefinition() + " in "
                        + observation);
                // don't stop so we know which objects don't resolve, although >1 may be
                // annoying.
            }
        } else {

            RuntimeScope runtimeScope = new RuntimeScope(this).withContext(observation)
                    .withScope(this.resolutionScope.getDeferredChildScope(observation, mode))
                    .withMetadata(observation.getMetadata());

            ret = dataflow.run(observation.getScale(), (Actuator) parentDataflow, runtimeScope);

            runtimeScope.notifyDataflowChanges(runtimeScope);

        }

        return (T) ret;
    }

    public Dataflow supply(boolean shit) {
        return null;
    }

    /**
     * Resolve a new direct observation (which doesn't exist yet: the dataflow will create it)
     * passing the observable and the name.
     * 
     * @param observable
     * @param name
     * @param scale
     * @param subtask
     * @return
     */
    public Dataflow resolve(IObservable observable, String name, IScale scale, ITaskTree<?> subtask) {

        ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
        return getDataflow(observable, Mode.RESOLUTION, scale, null, (geometry) -> {

            Dataflow df = null;

            ResolutionScope scope = Resolver.create(this.dataflow).resolve((Observable) observable,
                    this.resolutionScope.getChildScope(observable, geometry, name), Mode.RESOLUTION, scale,
                    model);

            if (scope.getCoverage().isRelevant()) {

                df = Dataflows.INSTANCE.compile("local:task:" + session.getId() + ":" + subtask.getId(),
                        scope,
                        this.actuator)/*
                                       * .setPrimary(false)
                                       */;
                df.setModel((Model) model);
                df.setDescription("Resolution of " + observable);

                notifyDataflowChanges(this);

            } else if (resolutionScope.getPreresolvedModels(observable) == null
                    || this.resolutionScope.getPreresolvedModels(observable).getSecond().size() == 0) {

                /*
                 * Add an empty dataflow to create the observation. This is only done if there are
                 * no preloaded resolvers in this scale, so we are certain that other subjects will
                 * encounter the same conditions.
                 */
                df = Dataflow.empty(observable, observable.getName(), scope, dataflow);
            }

            return df;
        });
    }

    /**
     * TODO move the dataflow caching logics of all the new-.... functions into a DataflowPool
     * object or something like that.
     */
    @Override
    public IDirectObservation newObservation(IObservable observable, String name, IScale scale,
            IMetadata metadata)
            throws KlabException {

        if (!observable.is(Type.COUNTABLE)) {
            throw new IllegalArgumentException(
                    "RuntimeContext: cannot create a non-countable observation with newObservation()");
        }

        IDirectObservation ret = null;
        Observable obs = new Observable((Observable) observable);

        INotification.Mode notificationMode = INotification.Mode.Normal;
        for (IAnnotation annotation : ((Actuator) actuator).getAnnotations()) {
            if ("verbose".equals(annotation.getName())) {
                notificationMode = INotification.Mode.Verbose;
            } else if ("silent".equals(annotation.getName())) {
                notificationMode = INotification.Mode.Silent;
            }
        }

        /*
         * harmonize the scale according to what the model wants and the context's
         */
        scale = Scale.contextualize(scale, contextSubject.getScale(),
                model == null ? null : model.getAnnotations(), monitor);

        ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity()).createChild("Resolution of " + name);
        Dataflow dataflow = resolve(obs, name, scale, subtask);

        RuntimeScope runtimeScope = new RuntimeScope(this)
                .withScope(this.resolutionScope.getChildScope(observable, contextSubject, scale))
                .withMetadata(metadata)
                .withDirectObservationName(name).withNotificationMode(notificationMode)
                .withinGroup(this.target instanceof ObservationGroup ? (ObservationGroup) this.target : null);

        IArtifact observation = dataflow.run(scale, (Actuator) this.actuator, runtimeScope);

        if (observation instanceof IDirectObservation) {
            ret = (IDirectObservation) observation;
        }

        return ret;
    }

    private RuntimeScope withinGroup(ObservationGroup object) {
        this.currentGroup = object;
        return this;
    }

    private RuntimeScope withNotificationMode(INotification.Mode notificationMode2) {
        this.notificationMode = notificationMode2;
        return this;
    }

    private RuntimeScope withDirectObservationName(String name) {
        this.directObservationName = name;
        return this;
    }

    private RuntimeScope withMetadata(IMetadata metadata) {
        this.objectMetadata = metadata;
        return this;
    }

    private RuntimeScope withScope(ResolutionScope childScope) {
        this.resolutionScope = childScope;
        return this;
    }

    private RuntimeScope withContext(IDirectObservation observation) {
        this.contextSubject = observation;
        this.scale = observation.getScale();
        return this;
    }

    @Override
    public void newPredicate(IDirectObservation target, IConcept predicate) {

        if (predicate.isAbstract() || (!predicate.is(Type.TRAIT) && !predicate.is(Type.ROLE))) {
            throw new IllegalArgumentException(
                    "RuntimeContext: cannot attribute predicate " + predicate
                            + ": must be a concrete trait or role");
        }

        IObservable observable = new ObservableBuilder(predicate, monitor)
                .of(target.getObservable().getType()).buildObservable();

        // /*
        // * preload all the possible resolvers in the wider scope before specializing
        // the
        // * scope to the child observation. Then leave it to the kbox to use the
        // context
        // * with the preloaded cache.
        // */
        // this.resolutionScope.preloadResolvers(observable, target);

        ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
        ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity()).createChild(
                "Resolution of predicate " + Concepts.INSTANCE.getDisplayName(predicate) + " within "
                        + target.getName());
        ResolutionScope scope = this.resolutionScope.getChildScope(target, Mode.RESOLUTION);

        Dataflow dataflow = getDataflow(observable, Mode.RESOLUTION, target.getScale(), contextSubject,
                (geometry) -> {

                    Dataflow df = null;
                    ResolutionScope scp = Resolver.create(this.dataflow).resolve((Observable) observable,
                            scope, Mode.RESOLUTION,
                            geometry, model);

                    if (scp.getCoverage().isRelevant()) {

                        df = Dataflows.INSTANCE.compile(
                                "local:task:" + session.getId() + ":" + subtask.getId(), scp, this.actuator);
                        df.setModel((Model) model);

                    } else if (resolutionScope.getPreresolvedModels(observable) == null
                            || resolutionScope.getPreresolvedModels(observable).getSecond().size() == 0) {

                        /*
                         * Add an empty dataflow to create the predicate without further
                         * consequences. This is only done if there are no preloaded resolvers in
                         * this scale, so we are certain that other subjects will encounter the same
                         * conditions.
                         */
                        df = Dataflow.empty(observable, null, scp, this.dataflow);
                    }

                    notifyDataflowChanges(this);

                    return df;

                });

        if (dataflow != null) {
            dataflow.run(target.getScale(), (Actuator) this.actuator, this);
        }

    }

    @Override
    public IRelationship newRelationship(IObservable observable, String name, IScale scale,
            IObjectArtifact source,
            IObjectArtifact target, IMetadata metadata) {

        if (!observable.is(Type.RELATIONSHIP)) {
            throw new IllegalArgumentException(
                    "RuntimeContext: cannot create a relationship of type " + observable.getType());
        }

        INotification.Mode notificationMode = INotification.Mode.Normal;
        for (IAnnotation annotation : ((Actuator) actuator).getAnnotations()) {
            if ("verbose".equals(annotation.getName())) {
                notificationMode = INotification.Mode.Verbose;
            } else if ("silent".equals(annotation.getName())) {
                notificationMode = INotification.Mode.Silent;
            }
        }

        Observable obs = new Observable((Observable) observable).withoutModel();
        obs.setName(name);
        scale = Scale.contextualize(scale, contextSubject.getScale(),
                model == null ? null : model.getAnnotations(), monitor);
        ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity())
                .createChild("Resolution of relationship " + name);
        Dataflow dataflow = resolve(obs, name, scale, subtask);

        RuntimeScope runtimeScope = new RuntimeScope(this).withDirectObservationName(name)
                .withinGroup(this.target instanceof ObservationGroup ? (ObservationGroup) this.target : null)
                .withNotificationMode(notificationMode)
                .connecting((IDirectObservation) source, (IDirectObservation) target)
                .withMetadata(metadata)
                .withScope(this.resolutionScope.getChildScope(observable, contextSubject, scale));

        return (IRelationship) dataflow.run(scale, (Actuator) this.actuator, runtimeScope);
    }

    private RuntimeScope connecting(IDirectObservation source, IDirectObservation target2) {
        this.relationshipSource = source;
        this.relationshipTarget = target2;
        return this;
    }

    @Override
    public IRuntimeScope createChild(IScale scale, IActuator act, IResolutionScope scope, IMonitor monitor) {

        Actuator actuator = (Actuator) act;

        RuntimeScope ret = new RuntimeScope(this);
        ret.parent = this;
        ret.namespace = actuator.getNamespace();
        ret.targetName = actuator.isPartition() ? actuator.getPartitionedTarget() : actuator.getName();
        ret.resolutionScope = (ResolutionScope) scope;
        ret.artifactType = actuator.getObservable().is(Type.CONFIGURATION)
                ? Type.CONFIGURATION
                : Observables.INSTANCE.getObservableType(actuator.getObservable(), true);
        ret.scale = scale;
        ret.semantics = new HashMap<>();
        ret.targetSemantics = actuator.getObservable();
        ret.monitor = monitor;
        ret.semantics.put(actuator.getName(), ret.targetSemantics);
        ret.actuator = actuator;
        if (this.target instanceof IDirectObservation && !(this.target instanceof ObservationGroup)) {
            ret.contextSubject = (IDirectObservation) this.target;
            // if (ret.contextSubject instanceof IProcess) {
            // ret.contextSubject = getParentOf(ret.contextSubject);
            // }
        } else if (ret.contextSubject == null) {
            // this only happens when the father is root.
            ret.contextSubject = rootSubject;
        }

        for (IActuator a : actuator.getActuators()) {
            if (!((Actuator) a).isExported()) {
                String id = a.getAlias() == null ? a.getName() : a.getAlias();
                ret.semantics.put(id, ((Actuator) a).getObservable());
            }
        }
        for (IActuator a : actuator.getOutputs()) {
            String id = a.getAlias() == null ? a.getName() : a.getAlias();
            ret.semantics.put(id, ((Actuator) a).getObservable());
        }

        if (actuator.getObservable() != null) {
            ret.resolvedPredicates.putAll(actuator.getObservable().getResolvedPredicates());
        }

        /*
         * if we're subsetting the scale, finish up the partial scale we're using and set the
         * computation to use it.
         */
        if (getMergedScale(actuator) != null) {

            /*
             * the child will contribute to our own target, so just give it a view and let the
             * computation happen in the subscale of interest.
             */
            ret.scale = getMergedScale(actuator);
            if (actuator.isPartition()) {

                /*
                 * the target to rescale hasn't been created yet. We find the corresponding actuator
                 * and call createTarget() on it using the overall scale, pre-building the
                 * observation in the catalog but letting its actuator notify it later after the
                 * merging is done.
                 */
                Actuator mergingActuator = actuator.getDataflow()
                        .getActuator(actuator.getPartitionedTarget());
                if (mergingActuator == null) {
                    throw new IllegalStateException(
                            "internal: cannot find merging actuator named "
                                    + actuator.getPartitionedTarget());
                }

                IArtifact merging = createTarget(mergingActuator, getResolutionScale(), scope, rootSubject);

                /*
                 * partition sub-state does not go in the catalog
                 */
                if (merging instanceof IState) {
                    // complete the partial scale with the overall view of the context
                    merging = Observations.INSTANCE.getStateView((IState) merging, getMergedScale(actuator),
                            ret);
                    if (merging instanceof RescalingState) {
                        // for debugging
                        ((RescalingState) merging).setLocalId(actuator.getName());
                    }
                    getActuatorData(actuator).target = (IObservation) merging;

                } else if (merging instanceof IProcess && actuator.getObservable().is(Type.CHANGE)) {

                    IObservable tochange = actuator.getObservable().getBuilder(monitor)
                            .without(ObservableRole.UNARY_OPERATOR)
                            .buildObservable();

                    // avoid multiple rescaling states on the same object
                    IActuator originalTarget = findOriginalActuator(tochange, actuator);
                    if (originalTarget != null) {
                        merging = getActuatorData(originalTarget).target;
                    }

                    // either the original target is null or the merging actuator wasn't found
                    if (originalTarget == null || merging == null) {

                        // process creates the state, must make the rescaler here
                        merging = getCatalog().get(new ObservedConcept(tochange));
                        if (merging instanceof IState) {

                            merging = Observations.INSTANCE.getStateView((IState) merging,
                                    getMergedScale(actuator), ret);
                            if (merging instanceof RescalingState) {
                                // for debugging
                                ((RescalingState) merging).setLocalId(actuator.getName());
                            }
                        }
                    }
                }
                getActuatorData(actuator).target = (IObservation) merging;
                ret.target = merging;
            }

        } else if (!actuator.isInput()) {

            if (actuator.getObservable().is(Type.CHANGE) && actuator.getModel() != null
                    && actuator.getModel().isDerived()) {
                /*
                 * Find the changing target and set that as the target. This is confusing but the
                 * derived model copies any computation that affects the original quality, so the
                 * alternative would be to keep it a process and duplicate all the computation in
                 * the process contextualizer, which is way messier.
                 */
                IObservable tochange = actuator.getObservable().getBuilder(monitor)
                        .without(ObservableRole.UNARY_OPERATOR)
                        .buildObservable();
                ret.target = getCatalog().get(new ObservedConcept(tochange));
                getActuatorData(actuator).target = (IObservation) ret.target;

                // TODO see if we need to change anything else. At this point the semantics and
                // the type are out of sync
                // with the target, which could be OK - we compute a change process by passing
                // the changing target to
                // known computations that require it.

            } else if (ret.artifactType != Type.NOTHING && ret.artifactType != Type.TRAIT) {
                ret.target = ret.createTarget((Actuator) actuator, getResolutionScale(), scope, rootSubject);
                getActuatorData(actuator).target = (IObservation) ret.target;
            }
            // save existing target
            if (ret.target != null && this.target != null) {
                ret.semantics.put(actuator.getName(), ((Actuator) actuator).getObservable());
                // ret.artifactType = Observables.INSTANCE.getObservableType(((Actuator)
                // actuator).getObservable(), true);
            }
        }

        return ret;
    }

    private IActuator findOriginalActuator(IObservable tochange, Actuator actuator) {

        IActuator ret = null;
        List<IActuator> existing = new ArrayList<>(actuatorData.keySet());
        for (IActuator act : existing) {
            if (new ObservedConcept(((Actuator) act).getObservable(), ((Actuator) act).getMode())
                    .equals(new ObservedConcept(tochange, actuator.getMode()))) {
                IScale ours = getMergedScale(actuator);
                IScale itss = getMergedScale(act);
                if (ours.equals(itss)) {
                    ret = act;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * Used in sub-resolution with switched context.
     */
    @Override
    public IRuntimeScope createContext(IScale scale, IActuator actuator, IDataflow<?> dataflow,
            IResolutionScope scope) {

        RuntimeScope ret = new RuntimeScope(this);
        ret.parent = this;
        ret.namespace = ((Actuator) actuator).getNamespace();
        ret.targetName = ((Actuator) actuator).isPartition()
                ? ((Actuator) actuator).getPartitionedTarget()
                : actuator.getName();
        ret.resolutionScope = (ResolutionScope) scope;
        ret.artifactType = Observables.INSTANCE.getObservableType(((Actuator) actuator).getObservable(),
                true);
        ret.scale = scale;
        ret.semantics = new HashMap<>();
        ret.catalog = new HashMap<>();
        ret.actuatorData = new HashMap<>();
        ret.groups = new HashMap<>();
        ret.targetSemantics = ((Actuator) actuator).getObservable();
        ret.monitor = monitor;
        ret.semantics.put(actuator.getName(), ret.targetSemantics);
        ret.actuator = (Actuator) actuator;
        ret.contextSubject = scope.getContext();
        ret.dataflow = (Dataflow) dataflow;
        ret.objectMetadata = this.objectMetadata;
        ret.directObservationName = this.directObservationName;
        ret.currentGroup = this.currentGroup;
        ret.globalData = this.globalData;
        ret.contextData = new HashMap<>();
        ret.relationshipSource = this.relationshipSource;
        ret.relationshipTarget = this.relationshipTarget;

        for (IActuator a : actuator.getActuators()) {
            if (!((Actuator) a).isExported()) {
                String id = a.getAlias() == null ? a.getName() : a.getAlias();
                ret.semantics.put(id, ((Actuator) a).getObservable());
            }
        }

        for (IActuator a : actuator.getOutputs()) {
            String id = a.getAlias() == null ? a.getName() : a.getAlias();
            ret.semantics.put(id, ((Actuator) a).getObservable());
        }

        // save existing target
        ret.target = ret.createTarget((Actuator) actuator, scale, scope, rootSubject);
        if (ret.target != null && this.target != null) {
            ret.semantics.put(actuator.getName(), ((Actuator) actuator).getObservable());
            ret.artifactType = Observables.INSTANCE.getObservableType(((Actuator) actuator).getObservable(),
                    true);
        }

        return ret;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls) {
        List<T> ret = new ArrayList<>();
        if (artifact instanceof ObservationGroup) {
            return IteratorUtils.toList(((ObservationGroup) artifact).iterator());
        }
        if (artifact instanceof ObservationGroupView) {
            return IteratorUtils.toList(((ObservationGroupView) artifact).iterator());
        }
        for (IArtifact source : this.structure.getLogicalChildren(artifact)) {
            if (cls.isAssignableFrom(source.getClass())) {
                ret.add((T) source);
            }
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IArtifact> Collection<Pair<String, T>> getArtifacts(Class<T> type) {
        List<Pair<String, T>> ret = new ArrayList<>();
        for (String s : catalog.keySet()) {
            if (type.isAssignableFrom(catalog.get(s).getClass())) {
                IArtifact artifact = catalog.get(s);
                if (artifact instanceof IObservation && this.model != null) {
                    String localName = model.getLocalNameFor(((IObservation) artifact).getObservable());
                    if (localName != null) {
                        s = localName;
                    }
                }
                ret.add(new Pair<>(s, (T) artifact));
            }
        }
        return ret;
    }

    @Override
    public IScale getScale() {
        return scale;
    }

    @Override
    public Type getArtifactType() {
        return artifactType;
    }

    public void setScale(IScale scale) {
        this.scale = scale;
    }

    @Override
    public IObservable getSemantics(String identifier) {
        IObservable ret = semantics.get(identifier);
        if (ret == null) {
            // this catches partitions
            for (IObservable obs : semantics.values()) {
                if (identifier.equals(obs.getName())) {
                    ret = obs;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public void setTarget(IArtifact target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IArtifact> T getArtifact(String localName, Class<T> cls) {
        IArtifact ret = getArtifact(localName);
        if (ret != null && cls.isAssignableFrom(ret.getClass())) {
            return (T) ret;
        }
        return null;
    }

    @Override
    public IObservable getTargetSemantics() {
        return targetSemantics;
    }

    @Override
    public IState addState(IDirectObservation target, IObservable observable, Object data) {

        if (!observable.is(Type.QUALITY)) {
            throw new KlabValidationException(
                    "klab: API usage: adding a state with a non-quality observable");
        }
        IObservation ret = DefaultRuntimeProvider.createObservation(observable,
                Scale.copyForObservation(target.getScale()), this,
                false);
        if (data != null) {
            ((IState) ret).fill(data);
        }

        this.structure.link(ret, target);
        notifyListeners(ret);

        return (IState) ret;
    }

    /**
     * Pre-fill the artifact catalog with the artifact relevant to the passed actuator and scope.
     * 
     * @param actuator
     * @param scope
     */
    public IArtifact createTarget(Actuator actuator, IScale scale, IResolutionScope scope,
            IDirectObservation rootSubject) {

        /*
         * support map: the fields are observable, mode, and a boolean that is true if the actuator
         * is void, meaning an archetype should be created instead of a true observation. So far
         * this only happens with contextual quality learning models.
         */
        Map<String, Triple<Observable, Mode, Boolean>> targetObservables = new HashMap<>();

        Pair<String, IArtifact> existing = findArtifact(actuator.getObservable());
        if (existing != null) {
            return existing.getSecond();
        }

        if (!actuator.isPartition()) {
            targetObservables.put(actuator.getName(),
                    new Triple<>(actuator.getObservable(), actuator.getMode(),
                            actuator.getType() == IArtifact.Type.VOID));
        }

        /*
         * add any target of indirect computations
         */
        for (IContextualizable computation : actuator.getComputation()) {
            if (computation.getTarget() != null
                    && this.catalog.get(computation.getTarget().getName()) == null) {
                targetObservables.put(computation.getTarget().getReferenceName(),
                        new Triple<>((Observable) computation.getTarget(), computation.getComputationMode(),
                                false));
            }
        }

        /*
         * add additional observables that are created by a process FIXME this is actually not right
         * - will create all outputs for directly observed models, but add unneeded outputs when the
         * additionals are not dependencies. What should be done is that a model "dropped" directly
         * would set in all its own dependencies so we keep both behaviors.
         */
        if (actuator.getObservable().is(Type.PROCESS) && actuator.getModel() != null) {
            for (int i = 1; i < actuator.getModel().getObservables().size(); i++) {
                IObservable output = actuator.getModel().getObservables().get(i);
                if (Observables.INSTANCE.isCreatedBy(output, actuator.getObservable())
                        && !this.catalog.containsKey(output.getReferenceName())) {
                    targetObservables.put(output.getReferenceName(), new Triple<>((Observable) output,
                            output.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION, false));
                }
            }
        }

        /*
         * will be pre-existing only if: (i) it's an existing observation group and the observable
         * does not determine a new view; or (ii) we're resolving an attribute through a dataflow,
         * which will be added by this.
         */
        IObservation preexisting = null;

        for (String name : targetObservables.keySet()) {

            Triple<Observable, Mode, Boolean> op = targetObservables.get(name);
            Observable observable = op.getFirst();
            Mode mode = op.getSecond();

            IObservation observation = null;
            List<IState> predefinedStates = new ArrayList<>();

            if (observable.is(Type.COUNTABLE) && mode == Mode.INSTANTIATION) {
                observation = getObservationGroup(observable, scale);
                if (getNotifiedObservations().contains(observation.getId())) {
                    preexisting = observation;
                }
            } else if ((observable.is(Type.TRAIT) || observable.is(Type.ROLE)) && mode == Mode.RESOLUTION) {
                // get the target from the scope, add the predicate to it, return that
                if (this.target instanceof IDirectObservation) {
                    ((DirectObservation) this.target)
                            .addPredicate(Observables.INSTANCE.getBaseObservable(observable.getType()));
                }
                preexisting = (IObservation) this.target;
            } else {

                Observable obs = observable;

                // attribute the name if any
                if (directObservationName != null && ((Actuator) actuator).getMode() == Mode.RESOLUTION
                        && currentGroup != null
                        && observable.is(currentGroup.getObservable())) {
                    obs = new Observable(obs);
                    obs.setName(directObservationName);
                }

                if (obs.is(Type.RELATIONSHIP)) {
                    observation = DefaultRuntimeProvider.createRelationship(obs,
                            Scale.copyForObservation(scale),
                            relationshipSource, relationshipTarget, this);
                } else {
                    observation = DefaultRuntimeProvider.createObservation(obs,
                            Scale.copyForObservation(scale), this,
                            op.getThird());
                }

                if (getRootSubject() != null) {
                    ((Observation) getRootSubject()).setLastUpdate(System.currentTimeMillis());
                }

                /*
                 * When we get here the model is the resolver, or null for instantiated objects that
                 * found no resolver. If we are downstream of an instantiator, resolve pre-defined
                 * states before resolution is attempted. States are resolved from resource metadata
                 * only if no contextual states are present.
                 */
                if (objectMetadata != null) {
                    observation.getMetadata().putAll(objectMetadata);
                }

                if (parent != null && actuator.getDataflow().getModel() != null) {
                    for (String attr : actuator.getDataflow().getModel().getAttributeObservables().keySet()) {

                        /*
                         * TODO/FIXME: both of these must be turned into contextualizers and run
                         * within the dataflow. Reasons: 1) no replicability if the dataflow is used
                         * by itself; 2) (worse for now): no dynamics if the states change with
                         * time.
                         */

                        boolean done = false;
                        if (objectMetadata != null) {
                            /* state specs may be in metadata from resource attributes */
                            if (objectMetadata.containsKey(attr)) {
                                Object obj = objectMetadata.getCaseInsensitive(attr);
                                IState state = (IState) DefaultRuntimeProvider.createObservation(
                                        actuator.getDataflow().getModel().getAttributeObservables().get(attr),
                                        Scale.copyForObservation(scale), this);
                                ((State) state).distributeScalar(obj);
                                predefinedStates.add(state);
                                done = true;
                            }
                        }

                        if (!done) {
                            // look up in the first context that has the root subject as a target,
                            // or get
                            // the parent if none does.
                            RuntimeScope p = getParentWithTarget(rootSubject);
                            IArtifact artifact = p.findArtifactByObservableName(attr);
                            if (artifact == null) {
                                Pair<String, IArtifact> art = p
                                        .findArtifact(actuator.getDataflow().getModel()
                                                .getAttributeObservables().get(attr));
                                artifact = art == null ? null : art.getSecond();
                            }
                            if (artifact instanceof IState) {
                                // observable may be different or use data reduction traits
                                IState stateView = Observations.INSTANCE.getStateViewAs(
                                        actuator.getDataflow().getModel().getAttributeObservables().get(attr),
                                        (IState) artifact,
                                        scale, this);
                                predefinedStates.add(stateView);
                            }
                        }
                    }
                }
            }

            if (preexisting == null && observation != null) {

                // transmit all annotations and any interpretation keys to the artifact
                actuator.notifyNewObservation(observation);

                /*
                 * register the obs and potentially the root subject
                 */
                boolean wasRoot = false;
                this.observations.put(observation.getId(), observation);
                if (this.rootSubject == null && observation instanceof ISubject) {
                    this.rootSubject = (ISubject) observation;
                    this.eventBus = new EventBus((Subject) this.rootSubject);
                    /*
                     * We register the root subject for updates by default. TODO This may be
                     * subjected to a view asking for it at the time of session establishment.
                     */
                    watchedObservations.add(observation.getId());
                    wasRoot = true;
                }
                this.catalog.put(name, observation);
                this.structure.add(observation);
                if (contextSubject != null) {
                    link(observation, getLinkTarget());
                }
                if (observation instanceof ISubject) {
                    this.network.addVertex((ISubject) observation);
                }

                /*
                 * set the target observations if this is a configuration
                 */
                if (observation instanceof IConfiguration) {
                    ((Configuration) observation).setTargets(configurationTargets);
                }

                if (wasRoot && this.resolutionScope.getObserver() != null) {
                    for (IObservable state : this.resolutionScope.getObserver().getStates()) {
                        if (state.getValue() != null) {
                            IObservation ostate = DefaultRuntimeProvider.createObservation(state, scale, this,
                                    false);
                            actuator.notifyNewObservation(ostate);
                            this.observations.put(ostate.getId(), ostate);
                            this.catalog.put(state.getName(), ostate);
                            this.structure.add(ostate);
                            link(ostate, observation);
                        }
                    }
                }

                /*
                 * add any predefined states to the structure and the catalog
                 */
                for (IState state : predefinedStates) {
                    link(state, observation);
                    catalog.put(state.getObservable().getName(), state);
                }

                if (!(observation instanceof IState)) {

                    /*
                     * chain to the group if we're in one and we're supposed to
                     */
                    if (currentGroup != null && ((Actuator) actuator).getMode() == Mode.RESOLUTION
                            && observable.is(currentGroup.getObservable())) {
                        this.currentGroup.chain(observation, notificationMode != INotification.Mode.Silent);
                    }

                    /*
                     * notify if subscribed. States are notified only after resolution.
                     */
                    updateNotifications(observation);
                }

            }
        }

        return preexisting == null ? this.catalog.get(actuator.getName()) : preexisting;

    }

    /**
     * Make the link in the structure, sending the notifications we have established
     * 
     * @param child
     * @param parent
     */
    private void link(IArtifact child, IArtifact parent) {
        this.structure.link(child, parent);
    }

    private IArtifact getLinkTarget() {
        if (currentGroup != null && ((Actuator) actuator).getMode() == Mode.RESOLUTION) {
            if (this.targetSemantics.is(currentGroup.getObservable())) {
                return currentGroup;
            }
        }
        return contextSubject;
    }

    @Override
    public IObservation getParentArtifactOf(IObservation observation) {
        return (IObservation) structure.getArtifactParent(observation);
    }

    @Override
    public void updateNotifications(IObservation observation) {

        if (notificationMode == INotification.Mode.Silent) {
            return;
        }

        IObservation parent = this.getParentArtifactOf(observation);

        /*
         * if I am subscribed to the father and not to its father, send the number of children for
         * the father
         */
        if (parent == null || watchedObservations.contains(parent.getId())) {

            IObservation grandpa = parent == null ? null : getParentArtifactOf(parent);
            ISession session = monitor.getIdentity().getParentIdentity(ISession.class);

            if (parent == null || watchedObservations.contains(parent.getId())) {

                /*
                 * subscribed to parent or root level: send the child
                 */
                if (!notifiedObservations.contains(observation.getId())) {

                    /*
                     * first condition is for when the identity is a task that resolves an
                     * instantiated object. TODO should probably put the subject as the resolvable.
                     */
                    IResolvable resolvable = this.monitor.getIdentity() instanceof ITask
                            ? ((ITask<?>) this.monitor.getIdentity()).getResolvable()
                            : null;

                    boolean isMain = resolvable == null || resolvable.equals(observation.getObservable());

                    if (observation instanceof IState
                            && ((IState) observation).getValuePresentation() != ValuePresentation.VALUE) {

                        /*
                         * main artifact with a single table value as result causes the table to be
                         * sent to clients. TODO the same should happen with distributions,
                         * obviously with a different message. TODO decorations should also be
                         * linked to models and added to getCompiledView().
                         */
                        if (isMain && ((IState) observation).getValuePresentation() == ValuePresentation.TABLE
                                && observation.getScale().size() == 1) {

                            /*
                             * report a single table if this is the main observable and it only
                             * contains one table. This may need to be reviewed for generality but
                             * it seems OK for now.
                             */
                            Object table = ((IState) observation).get(scale.iterator().next());

                            if (table instanceof TableValue) {

                                KnowledgeViewReference descriptor = new KnowledgeViewReference();
                                descriptor.setContextId(monitor.getIdentity()
                                        .getParentIdentity(ITaskTree.class).getContextId());
                                descriptor
                                        .setBody(((TableValue) table).getCompiledView("text/html").getText());
                                descriptor.setViewClass(View.TABLES);
                                descriptor.setTitle(((TableValue) table).getTitle());
                                descriptor.setViewId(((TableValue) table).getId());
                                descriptor.getExportFormats().addAll(((TableValue) table).getExportFormats());
                                descriptor.setLabel(((TableValue) table).getLabel());

                                report.addView((TableValue) table, descriptor);
                                session.getMonitor()
                                        .send(Message.create(session.getId(),
                                                IMessage.MessageClass.UserInterface,
                                                IMessage.Type.ViewAvailable, descriptor));
                            }
                        }
                    }

                    IObservationReference descriptor = Observations.INSTANCE
                            .createArtifactDescriptor(
                                    observation/*
                                                * , getParentArtifactOf(observation)
                                                */, observation.getScale().initialization(), 0)
                            .withTaskId(monitor.getIdentity().getId())
                            .withContextId(
                                    monitor.getIdentity().getParentIdentity(ITaskTree.class).getContextId());

                    session.getMonitor()
                            .send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
                                    IMessage.Type.NewObservation, descriptor));

                    report.include(descriptor, observation);

                    session.getState().notifyObservation(observation);
                    notifiedObservations.add(observation.getId());
                }

                for (ObservationChange change : ((Observation) observation).getChangesAndReset()) {
                    change.setExportFormats(
                            Observations.INSTANCE.getExportFormats((IObservation) observation));
                    session.getMonitor()
                            .send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
                                    IMessage.Type.ModifiedObservation, change));
                }

            } else if (notificationMode == INotification.Mode.Verbose
                    || (grandpa != null && watchedObservations.contains(parent.getId()))) {

                // subscribed to grandparent and parent is closed: send change
                ObservationChange change = ((Observation) parent)
                        .createChangeEvent(ObservationChange.Type.StructureChange);
                change.setNewSize(getChildArtifactsOf(parent).size());
                change.setExportFormats(Observations.INSTANCE.getExportFormats((IObservation) parent));
                session.getMonitor()
                        .send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
                                IMessage.Type.ModifiedObservation, change));
            }
        }
    }

    @Override
    public IObservation getObservationGroupView(Observable observable, IObservation ret) {

        Pair<String, IArtifact> previous = findArtifact(observable);
        if (previous != null) {
            return (IObservation) previous.getSecond();
        }

        IConcept mainObservable = ret.getObservable().getType();
        if (!mainObservable.equals(observable.getType())) {
            final Set<IConcept> filtered = new HashSet<>();
            filtered.addAll(Traits.INSTANCE.getDirectTraits(observable.getType()));
            filtered.addAll(Roles.INSTANCE.getDirectRoles(observable.getType()));
            if (!filtered.isEmpty()) {

                ret = new ObservationGroupView((Observable) observable, (ObservationGroup) ret,
                        new Function<IArtifact, Boolean>(){
                            @Override
                            public Boolean apply(IArtifact t) {
                                return t instanceof DirectObservation
                                        && filtered.containsAll(((DirectObservation) t).getPredicates());
                            }
                        });

                /*
                 * register the view as a regular observation
                 */
                this.observations.put(ret.getId(), ret);
                this.catalog.put(observable.getName(), ret);
                this.structure.add(ret);
                if (contextSubject != null) {
                    link(ret, currentGroup == null ? contextSubject : currentGroup);
                }
            }
        }
        return ret;
    }

    @Override
    public IObservation getObservationGroup(IObservable observable, IScale scale) {
        IConcept mainObservable = Observables.INSTANCE.getBaseObservable(observable.getType());
        IObservation ret = groups.get(mainObservable);
        if (ret == null) {
            ret = new ObservationGroup(Observable.promote(mainObservable), (Scale) scale, this,
                    observable.getArtifactType());
            groups.put(mainObservable, (ObservationGroup) ret);
        }
        return ret;
    }

    public IArtifact createTarget(IObservable observable) {

        IObservation observation = null;

        if (observable.is(Type.COUNTABLE)) {
            observation = getObservationGroup(observable, Scale.copyForObservation(getResolutionScale()));
        } else if (observable.is(Type.TRAIT) || observable.is(Type.ROLE)) {
            /*
             * TODO this should happen when a predicate observation is made explicitly from a
             * root-level query, i.e. when 'dropping' attributes on individual observations is
             * enabled.
             */
            Logging.INSTANCE.warn("unexpected call to createTarget: check logics");
        } else {
            observation = DefaultRuntimeProvider.createObservation(observable,
                    Scale.copyForObservation(getResolutionScale()),
                    this);
        }

        if (getRootSubject() != null) {
            ((Observation) getRootSubject()).setLastUpdate(System.currentTimeMillis());
        }

        /*
         * register the obs and potentially the root subject
         */
        this.observations.put(observation.getId(), observation);
        if (this.rootSubject == null && observation instanceof ISubject) {
            this.rootSubject = (ISubject) observation;
        }
        this.catalog.put(observable.getReferenceName(), observation);
        this.structure.add(observation);
        if (contextSubject != null) {
            link(observation, currentGroup == null ? contextSubject : currentGroup);
        }
        if (observation instanceof ISubject) {
            this.network.addVertex((ISubject) observation);
        }

        return this.catalog.get(actuator.getName());

    }

    private RuntimeScope getParentWithTarget(IDirectObservation subject) {
        if (subject == null || subject.equals(this.target)) {
            return this;
        }
        RuntimeScope ret = parent == null ? null : parent.getParentWithTarget(subject);
        return ret == null ? (parent == null ? this : parent) : ret;
    }

    @Override
    public IDirectObservation getContextObservation() {
        return resolutionScope.getContext();
    }

    @Override
    public ISubject getRootSubject() {
        return rootSubject;
    }

    public RuntimeScope getRootScope() {
        RuntimeScope ret = this;
        while(ret.parent != null) {
            ret = ret.parent;
        }
        return ret;
    }

    @Override
    public IDirectObservation getContextSubject() {
        return contextSubject;
    }

    @Override
    public IObservation getObservation(String observationId) {
        return observations.get(observationId);
    }

    @Override
    public Structure getStructure() {
        return structure;
    }

    @Override
    public IScheduler getScheduler() {
        return getRootScope().scheduler;
    }

    @Override
    public Pair<String, IArtifact> findArtifact(IObservable observable) {

        if (catalog == null) {
            return null;
        }

        if (!observable.getAbstractPredicates().isEmpty()
                || observable.getDescriptionType() == Description.CHARACTERIZATION) {

            /*
             * create a folder of all artifacts that resolve the observable, if any, or return null.
             */
            ObservationGroup ret = null;
            for (String key : catalog.keySet()) {
                IArtifact artifact = catalog.get(key);
                if (artifact != null && artifact instanceof IObservation) {

                    boolean resolves = false;
                    if (observable.getDescriptionType() == Description.CHARACTERIZATION
                            && observable.getType().isAbstract()) {
                        for (IConcept predicate : Traits.INSTANCE
                                .getTraits(((IObservation) artifact).getObservable().getType())) {
                            if (cached_is(predicate, observable.getType())) {
                                resolves = true;
                                break;
                            }
                        }
                    } else {
                        resolves = observable.resolves(((IObservation) artifact).getObservable(), null);
                    }

                    if (!resolves) {
                        continue;
                    }

                    if (ret == null) {
                        ret = new ObservationGroup((Observable) observable,
                                (Scale) ((IObservation) artifact).getScale(), this,
                                ((IObservation) artifact).getType());
                    }
                    ret.chain(artifact);
                }
            }

            return ret == null ? null : new Pair<>(observable.getName(), ret);
        }

        for (String key : catalog.keySet()) {
            IArtifact artifact = catalog.get(key);
            if (artifact != null && artifact instanceof IObservation
                    && ((Observable) ((IObservation) artifact).getObservable())
                            .resolvesStrictly((Observable) observable)) {
                return new Pair<>(key, artifact);
            }
        }
        return null;
    }

    public IArtifact findArtifactByObservableName(String name) {
        // null if called on the root scope
        if (catalog != null) {
            for (String key : catalog.keySet()) {
                IArtifact artifact = catalog.get(key);
                if (artifact != null && artifact instanceof IObservation
                        && ((Observable) ((IObservation) artifact).getObservable()).getName().equals(name)) {
                    return artifact;
                }
            }
        }
        return null;
    }

    @Override
    public IReport getReport() {
        return report;
    }

    @Override
    public void setModel(Model model) {
        this.model = model;
        if (model != null) {
            ((Report) report).addModel(model);
        }
    }

    @Override
    public IModel getModel() {
        return model;
    }

    @Override
    public void removeArtifact(IArtifact object) {
        structure.removeArtifact(object);
    }

    @Override
    public Collection<IArtifact> getArtifact(IConcept observable) {
        List<IArtifact> ret = new ArrayList<>();
        for (IArtifact artifact : catalog.values()) {
            if (artifact instanceof IObservation
                    && ((IObservation) artifact).getObservable().getType().equals(observable)) {
                ret.add(artifact);
            }
        }
        return ret;
    }

    public Actuator getActuator() {
        return actuator;
    }

    @Override
    public IDataflow<?> getDataflow() {
        return actuator == null ? null : ((Actuator) actuator).getDataflow();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IArtifact> Map<String, T> getLocalCatalog(Class<T> cls) {

        Map<String, T> ret = new HashMap<>();
        for (Pair<String, ? extends IArtifact> artifact : getArtifacts(cls)) {

            String name = artifact.getFirst();
            if (artifact.getSecond() instanceof IState && actuator != null) {
                for (IActuator a : actuator.getActuators()) {
                    if (a.getName().equals(name) && a.getAlias() != null) {
                        name = a.getAlias();
                        break;
                    }
                }
            }
            ret.put(name, (T) artifact.getSecond());
        }

        return ret;

    }

    @Override
    public Scope getExpressionContext() {
        return ExpressionScope.create(this);
    }

    @Override
    public ExpressionScope getExpressionContext(IObservable targetObservable) {
        return ExpressionScope.create(this, targetObservable);
    }

    @Override
    public Collection<IArtifact> getChildArtifactsOf(IArtifact directObservation) {
        return structure.getArtifactChildren(directObservation);
    }

    @Override
    public Set<String> getNotifiedObservations() {
        return notifiedObservations;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IArtifact> T getArtifact(IConcept concept, Class<T> cls) {

        if (IObservationGroup.class.isAssignableFrom(cls)) {

            /*
             * must search base subject class and filter by predicates if any.
             */
            Builder builder = Observable.promote(concept).getBuilder(monitor).without(ObservableRole.TRAIT,
                    ObservableRole.ROLE);
            Pair<IConcept, Collection<IConcept>> query = new Pair<>(builder.buildConcept(),
                    builder.getRemoved());

            for (IArtifact artifact : catalog.values()) {
                if (artifact instanceof ObservationGroup
                        && (cached_is(((ObservationGroup) artifact).getObservable().getType(),
                                query.getFirst()))) {
                    return (T) ((ObservationGroup) artifact).queryPredicates(query.getSecond());
                }
            }
        }

        Set<IArtifact> ret = new HashSet<>();
        for (IArtifact artifact : catalog.values()) {
            if (artifact instanceof IObservation
                    && (cached_is(((IObservation) artifact).getObservable().getType(), concept))) {
                ret.add(artifact);
            }
        }

        Set<IArtifact> chosen = new HashSet<>();
        if (ret.size() > 1) {
            for (IArtifact artifact : ret) {
                if (cls.isAssignableFrom(artifact.getClass())) {
                    if (model != null && artifact instanceof IObservation) {
                        for (IObservable obs : model.getDependencies()) {
                            if (obs.getName().equals(((IObservation) artifact).getObservable().getName())) {
                                chosen.add(artifact);
                            }
                        }
                    } else {
                        chosen.add(artifact);
                    }
                }
            }
        }

        if (chosen.isEmpty() && !ret.isEmpty()) {
            // just take the first
            chosen.add(ret.iterator().next());
        }

        return (T) (chosen.isEmpty() ? null : chosen.iterator().next());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IArtifact> Collection<T> getArtifacts(IConcept concept, Class<T> cls) {

        Set<T> chosen = new HashSet<>();

        if (IObservationGroup.class.isAssignableFrom(cls)) {

            /*
             * must search base subject class and filter by predicates if any.
             */
            Builder builder = Observable.promote(concept).getBuilder(monitor).without(ObservableRole.TRAIT,
                    ObservableRole.ROLE);
            Pair<IConcept, Collection<IConcept>> query = new Pair<>(builder.buildConcept(),
                    builder.getRemoved());

            for (IArtifact artifact : catalog.values()) {
                if (artifact instanceof ObservationGroup
                        && (cached_is(((ObservationGroup) artifact).getObservable().getType(),
                                query.getFirst()))) {
                    chosen.add((T) ((ObservationGroup) artifact).queryPredicates(query.getSecond()));
                }
            }
        }

        Set<IArtifact> ret = new HashSet<>();
        for (IArtifact artifact : catalog.values()) {
            if (artifact instanceof IObservation
                    && (cached_is(((IObservation) artifact).getObservable().getType(), concept))) {
                ret.add((T)artifact);
            }
        }

        for (IArtifact artifact : ret) {
            if (cls.isAssignableFrom(artifact.getClass())) {
                if (model != null && artifact instanceof IObservation) {
                    for (IObservable obs : model.getDependencies()) {
                        if (cached_is(obs.getType(), concept)) {
                            chosen.add((T) artifact);
                        }
                    }
                    for (IObservable obs : model.getObservables()) {
                        if (cached_is(obs.getType(), concept)) {
                            chosen.add((T) artifact);
                        }
                    }
                } else {
                    chosen.add((T) artifact);
                }
            }
        }

        return chosen;
    }

    @Override
    public void scheduleActions(Observation observation, IBehavior behavior) {

        if (resolutionScope.getScale().getTime() == null) {
            return;
        }

        /*
         * lookup scheduled actions
         */
        for (IBehavior.Action action : behavior.getActions()) {
            for (IAnnotation aa : action.getAnnotations()) {
                if (aa.getName().equals("schedule")) {

                    RuntimeScope root = getRootScope();
                    if (root.scheduler == null) {
                        root.scheduler = new Scheduler(this, this.rootSubject.getId(), resolutionScope,
                                monitor);
                    }
                    root.occurrent = true;
                    ((Scheduler) root.scheduler).schedule(action, observation, Time.create(aa), this);

                }
            }
        }

    }

    @Override
    public void scheduleActions(Actuator actuator) {

        /*
         * We don't schedule merging observations as they should only merge what's behind them.
         * Currently attempting to do so will fail for lack of a knowable temporal context. When the
         * temporal merging logic is finished, we may remove this and allow merging actuators to
         * exist.
         */
        if (actuator.getObservable().isDereified()) {
            return;
        }

        /*
         * Only occurrents occur. FIXME yes, but they may affect continuants
         */
        boolean isOccurrent = actuator.getType().isOccurrent();
        IViewModel.Schedule viewSchedule = null;

        if (actuator.getModel() != null && actuator.getModel().getViewModel() != null
                && getResolutionScale().isTemporallyDistributed()) {
            /*
             * schedule according to the view's definition. For now views are the only thing that
             * can also be scheduled at termination, after contextualization. No schedule means we
             * get scheduled as if we adopted the main temporal schedule. Otherwise we can get
             * scheduled at start, end or (later) arbitrary resolutions. Init case is dealt with
             * directly as the contextualizer is always called, and only initializes if
             * schedule.isInit() or is null.
             */
            IViewModel.Schedule schedule = actuator.getModel().getViewModel().getSchedule();
            if (schedule == null || schedule.isTemporal()) {
                isOccurrent = true;
            } else {
                if (schedule.isEnd() || schedule.isStart()) {
                    // pass the schedule on to the scheduler in each computation.
                    isOccurrent = true;
                    viewSchedule = schedule;
                }
            }
        }

        if (isOccurrent) {

            List<Computation> schedule = new ArrayList<>();
            for (Computation computation : actuator.getContextualizers()) {

                // if not null, the scheduler will deal with it.
                computation.schedule = viewSchedule;

                /*
                 * nothing meant for initialization should be scheduled. Resource is null if this
                 * encodes an aux variable.
                 */
                if (computation.variable != null) {
                    schedule.add(computation);
                    continue;
                }

                if (computation.resource.getTrigger() == Trigger.DEFINITION) {
                    continue;
                }

                /*
                 * conditions for scheduling
                 */
                boolean isTransition = computation.resource.getTrigger() == Trigger.DEFINITION;

                /*
                 * Jeez FIXME this needs review and simplification
                 * 
                 * null target == aux variable, occur at will.
                 */
                boolean targetOccurs = computation.target == null
                        || computation.target.getType().isOccurrent()
                        || (isOccurrent && Observables.INSTANCE.isAffectedBy(computation.observable,
                                actuator.getObservable()))
                        || (computation.target != null
                                && computation.resource.getGeometry()
                                        .getDimension(Dimension.Type.TIME) != null)
                        // if model is derived, it was put here on purpose to represent change so we
                        // schedule it.
                        || (actuator.getModel() != null && actuator.getModel().isDerived());

                if (isTransition || targetOccurs) {
                    schedule.add(computation);
                }
            }

            if (schedule.isEmpty()) {
                return;
            }

            RuntimeScope root = getRootScope();
            root.occurrent = true;

            if (root.scheduler == null) {
                root.scheduler = new Scheduler(this, this.rootSubject.getId(), resolutionScope, monitor);
            }

            ((Scheduler) root.scheduler).schedule(actuator, schedule, this);
        }

    }

    @Override
    public IRuntimeScope locate(ILocator transitionScale, IMonitor monitor) {

        RuntimeScope ret = new RuntimeScope(this);
        ret.monitor = monitor;
        ret.scale = (Scale) transitionScale;

        /*
         * TODO wrap all temporal states into a temporal rescaling state - works both to subset and
         * to aggregate. This must apply also to event folders, which must only show the current
         * events.
         */
        for (String key : catalog.keySet()) {
            IArtifact artifact = catalog.get(key);
            if (artifact instanceof ObservationGroup && artifact.getType().isOccurrent()) {
                ret.catalog.put(key, ((IObservation) artifact).at(ret.scale.getTime()));
            }
        }

        return ret;
    }

    @Override
    public Map<String, IVariable> getVariables() {
        return symbolTable;
    }

    @Override
    public IState newNonsemanticState(String name, IArtifact.Type type, IScale scale) {

        IConcept concept = OWL.INSTANCE.getNonsemanticPeer(name, type);
        IObservable observable = Observable.promote(concept);

        IStorage<?> data = Klab.INSTANCE.getStorageProvider().createStorage(type, scale);
        IState ret = new State((Observable) observable, (Scale) scale, this, (IDataStorage<?>) data);

        semantics.put(observable.getName(), observable);
        structure.add(ret);
        this.link(ret, this.target);
        catalog.put(observable.getName(), ret);
        observations.put(ret.getId(), ret);

        return ret;
    }

    @Override
    public Collection<IObservable> getDependents(IObservable observable, Mode resolutionMode) {
        List<IObservable> ret = new ArrayList<>();
        return ret;
    }

    @Override
    public Collection<IObservable> getPrecursors(IObservable observable, Mode resolutionMode) {
        List<IObservable> ret = new ArrayList<>();
        return ret;
    }

    public void setDataflow(Dataflow dataflow) {
        this.dataflow = dataflow;
    }

    @Override
    public Map<IConcept, Pair<String, IKimExpression>> getBehaviorBindings() {
        return behaviorBindings;
    }

    public String toString() {
        return "{Scope of " + contextSubject + " [" + catalog.size() + " obs, " + network.edgeSet().size()
                + " links]}";
    }

    @Override
    public Set<String> getWatchedObservationIds() {
        return watchedObservations;
    }

    @Override
    public void swapArtifact(IArtifact original, IArtifact replacement) {
        // TODO see what else needs to be there
        structure.swap(original, replacement);
        observations.remove(original.getId());
        observations.put(replacement.getId(), (IObservation) replacement);
        // swap in catalog using dumb search for the object\
        String name = null;
        for (String key : catalog.keySet()) {
            if (catalog.get(key) == original) {
                name = key;
                break;
            }
        }
        if (name != null) {
            catalog.put(name, replacement);
        }
    }

    @Override
    public Collection<IObservation> getObservations(IConcept observable) {
        List<IObservation> ret = new ArrayList<>();
        IConcept artifactType = observable;
        if (observable.is(Type.COUNTABLE)) {
            artifactType = Observables.INSTANCE.getBaseObservable(artifactType);
        }
        IObservation artifact = getArtifact(artifactType, IObservation.class);
        if (artifact instanceof ObservationGroup) {
            for (IArtifact grouped : artifact) {
                if (cached_is(((IObservation) grouped).getObservable().getType(), observable)
                        || cached_is_related(
                                ((IObservation) grouped).getObservable().getType(),
                                observable)/*
                                            * ((IObservation)
                                            * grouped).getObservable().getType().resolves(
                                            * observable, null)
                                            */) {
                    ret.add((IObservation) grouped);
                }
            }
        } else {
            ret.add(artifact);
        }
        return ret;
    }

    @Override
    public void notifyListeners(IObservation object) {
        if (object.equals(rootSubject)) {
            monitor.getIdentity().getParentIdentity(Session.class).notifyNewContext((ISubject) object);
        } else {
            monitor.getIdentity().getParentIdentity(Session.class).notifyNewObservation(object, rootSubject);
        }
    }

    @Override
    public boolean isOccurrent() {
        // TODO Auto-generated method stub
        return ((RuntimeScope) getRootScope()).occurrent;
    }

    public void setOccurrent() {
        getRootScope().occurrent = true;
    }

    @Override
    public Map<IObservedConcept, IObservation> getCatalog() {
        Map<IObservedConcept, IObservation> ret = new HashMap<>();
        for (IArtifact artifact : catalog.values()) {
            if (artifact instanceof IObservation) {
                ret.put(new ObservedConcept(((IObservation) artifact).getObservable(),
                        artifact instanceof ObservationGroup ? Mode.INSTANTIATION : Mode.RESOLUTION),
                        (IObservation) artifact);
            }
        }
        return ret;
    }

    @Override
    public void addView(IKnowledgeView view) {

        this.viewsByUrn.put(view.getUrn(), view);
        this.views.put(view.getId(), view);

        IReport.View type = null;
        switch(view.getViewClass()) {
        case "table":
            type = View.TABLES;
            break;
        }

        /*
         * send directly to clients. If view can export, keep view and send URL to export service.
         */
        KnowledgeViewReference descriptor = new KnowledgeViewReference();
        descriptor.setContextId(monitor.getIdentity().getParentIdentity(ITaskTree.class).getContextId());
        descriptor.setBody(view.getCompiledView("text/html").getText());
        descriptor.setViewClass(type);
        descriptor.setTitle(view.getTitle());
        descriptor.setViewId(view.getId());
        descriptor.getExportFormats().addAll(view.getExportFormats());
        descriptor.setLabel(view.getLabel() == null
                ? (StringUtil.capitalize(view.getViewClass()) + " " + (views.size() + 1))
                : view.getLabel());

        report.addView(view, descriptor);

        ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
        session.getMonitor().send(
                Message.create(session.getId(), IMessage.MessageClass.UserInterface,
                        IMessage.Type.ViewAvailable, descriptor));

    }

    @Override
    public IRuntimeScope targetForChange() {

        RuntimeScope ret = this;
        if (this.artifactType == Type.PROCESS && this.targetSemantics.is(Type.CHANGE)) {
            if (this.target == null || !(this.target instanceof IState)) {
                IConcept changing = Observables.INSTANCE.getDescribedType(this.targetSemantics.getType());
                if (changing != null) {
                    ret = new RuntimeScope(this);
                    Collection<IArtifact> trg = getArtifact(changing);
                    if (!trg.isEmpty()) {
                        ret.target = trg.iterator().next();
                        ret.targetSemantics = ((IObservation) ret.target).getObservable();
                        ret.artifactType = Observables.INSTANCE.getObservableType(ret.targetSemantics, true);
                        ret.targetName = ret.targetSemantics.getName();
                    }
                }
            } else if (this.target instanceof IState) {
                ret = new RuntimeScope(this);
                ret.targetSemantics = ((IObservation) ret.target).getObservable();
                ret.artifactType = Observables.INSTANCE.getObservableType(ret.targetSemantics, true);
                ret.targetName = ret.targetSemantics.getName();
            }
        }

        return ret;
    }

    @Override
    public IRuntimeScope targetToObservation(IObservation target) {

        RuntimeScope ret = this;
        ret = new RuntimeScope(this);
        ret.targetSemantics = target.getObservable();
        ret.artifactType = Observables.INSTANCE.getObservableType(target.getObservable(), true);
        ret.targetName = ret.targetSemantics.getName();
        return ret;
    }

    public boolean cached_is(Object c1, Object c2) {
        if (c2 == null || c1 == null) {
            return false;
        }
        if (c1.equals(c2)) {
            return true;
        }
        try {
            return reasonerCache
                    .get(/* (c1 instanceof Concept ? ((Concept) c1).getConcept().toString() : */c1.toString()
                            /* ) */ + ";"
                            + /*
                               * (c2 instanceof Concept ? ((Concept) c2).getConcept().toString() :
                               */ c2.toString()/* ) */);
        } catch (ExecutionException e) {
            return false;
        }
    }

    public boolean cached_is_related(Object c1, Object c2) {
        if (c2 == null || c1 == null) {
            return false;
        }
        try {
            return relatedReasonerCache
                    .get(/* (c1 instanceof Concept ? ((Concept) c1).getConcept().toString() : */c1.toString()
                            /* ) */ + ";"
                            + /*
                               * (c2 instanceof Concept ? ((Concept) c2).getConcept().toString() :
                               */c2.toString())/* ) */;
        } catch (ExecutionException e) {
            return false;
        }
    }

    // @Override
    // public IParameters<String> localize(ILocator locator) {
    //
    // RuntimeScope ret = new RuntimeScope(this);
    // Collection<Pair<String, IDataArtifact>> variables = getArtifacts(IDataArtifact.class);
    // for (Pair<String, IDataArtifact> variable : variables) {
    // // this ensures that Groovy expressions are computable
    // Object value = variable.getSecond().get(locator);
    // if (value == null && variable.getSecond().getType() == IArtifact.Type.NUMBER) {
    // value = Double.NaN;
    // }
    // ret.set(variable.getFirst(), value);
    // }
    //
    // ret.setScale((IScale) locator);
    // return ret;
    // }

    @Override
    public String getArtifactName(IArtifact artifact) {
        for (String key : catalog.keySet()) {
            if (artifact.equals(catalog.get(key))) {
                return key;
            }
        }
        return null;
    }

    @Override
    public void setConcreteIdentities(IConcept abstractIdentity, List<IConcept> concreteIdentities) {
        this.concreteIdentities.put(abstractIdentity, concreteIdentities);
    }

    @Override
    public Collection<IConcept> getConcreteIdentities(IConcept predicate) {
        for (IConcept p : concreteIdentities.keySet()) {
            if (p.is(predicate)) {
                return concreteIdentities.get(p);
            }
        }
        return null;
    }

    @Override
    public IConcept localizePredicate(IConcept predicate) {
        IConcept ret = resolvedPredicates.get(predicate);
        return ret == null ? predicate : ret;
    }

    @Override
    public Collection<IKnowledgeView> getViews() {
        if (this.views == null) {
            return new ArrayList<IKnowledgeView>();
        }
        return this.views.values();
    }

    @Override
    public IRuntimeScope withCoverage(IScale scale) {
        this.scale = ((Scale) this.scale).contextualizeTo(scale);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IArtifact> Collection<T> getAffectedArtifacts(IConcept processType, Class<T> cls) {
        List<T> ret = new ArrayList<>();
        for (IArtifact artifact : catalog.values()) {
            if (artifact instanceof IObservation && cls.isAssignableFrom(artifact.getClass())) {
                if (Observables.INSTANCE.isAffectedBy(((IObservation) artifact).getObservable(),
                        processType)) {
                    ret.add((T) artifact);
                }
            }
        }
        return ret;
    }

    @Override
    public IState getState(String name, IValueMediator unit) {
        return getMediatingState(getArtifact(name, IState.class), unit);
    }

    @Override
    public IState getState(IConcept concept, IValueMediator unit) {
        return getMediatingState(getArtifact(concept, IState.class), unit);
    }

    private IState getMediatingState(IState state, IValueMediator unit) {

        if (state != null) {
            if (state.getObservable().getMediator() != null) {
                return MediatingState.mediateIfNecessary(state, unit);
            } else {
                return state;
            }
        }
        return null;
    }

    /**
     * See if the model we're working with is associated to an output style through a style
     * annotation; if so, find it and return it
     */
    public StyleDefinition getOutputStyle() {
        if (((Actuator) this.actuator).getModel() != null) {
            IAnnotation astyle = Annotations.INSTANCE.getAnnotation(((Actuator) this.actuator).getModel(),
                    "style");
            if (astyle != null) {
                Object o = Resources.INSTANCE
                        .getNamespaceObject(astyle.get(IServiceCall.DEFAULT_PARAMETER_NAME, String.class));
                if (o instanceof StyleDefinition) {
                    return (StyleDefinition) o;
                }
            }
        }

        return null;
    }

    @Override
    public ISession getSession() {
        return monitor.getIdentity().getParentIdentity(ISession.class);
    }

    @Override
    public Collection<String> getStateIdentifiers() {
        Set<String> ret = new HashSet<>();
        if (model != null) {
            for (IObservable obs : model.getDependencies()) {
                ret.add(obs.getName());
            }
        } else {
            for (Pair<String, IState> state : getArtifacts(IState.class)) {
                ret.add(state.getSecond().getObservable().getName());
            }
        }
        return ret;
    }

    public static IRuntimeScope rootScope(ResolutionScope resolutionScope, IObserver<?> observer) {
        RuntimeScope ret = new RuntimeScope(resolutionScope);
        resolutionScope.setRootContextualizationScope(ret);
        return ret;
    }

    @Override
    public String getElkGraph() {
        return getElkGraph(this);
    }

    public void addPrecontextualizationDataflow(Dataflow dataflow) {
        addPrecontextualizationDataflow((Dataflow) dataflow, this.dataflow);
    }

    @Override
    public IRuntimeScope getChild(IRuntimeIdentity identity) {
        RuntimeScope ret = copy();
        ret.monitor = identity.getMonitor();
        if (identity instanceof ITaskIdentity && ((ITaskIdentity) identity).getContext() != null) {
            ret.contextSubject = ((ITaskIdentity) identity).getContext();
        }
        return ret;
    }

    /*
     * one flowchart per scope but we don't know which scope
     */
    public Element findDataflowElement(String nodeId) {
        if (((SessionState) getSession().getState()).getFlowchart() != null) {
            return ((SessionState) getSession().getState()).getFlowchart().getElement(nodeId);
        }
        return null;
    }

    public String getNodeId(IContextualizable resource) {
        if (((SessionState) getSession().getState()).getFlowchart() != null) {
            return ((SessionState) getSession().getState()).getFlowchart().getResourceNodeId(resource);
        }
        return null;
    }

    @Override
    public void notifyInspector(Object... triggerArguments) {
        IInspector inspector = getSession().getState().getInspector();
        if (inspector != null) {
            inspector.trigger(this, triggerArguments);
        }
    }

    @Override
    public Map<String, Object> getContextData() {
        return contextData;
    }

    @Override
    public Map<String, Object> getGlobalData() {
        return globalData;
    }

    @Override
    public IObservation getObservation(IObservable observable) {
        return getCatalog().get(new ObservedConcept(observable));
    }

    @Override
    public IObserver<?> getObserver() {
        return this.observer;
    }

}
