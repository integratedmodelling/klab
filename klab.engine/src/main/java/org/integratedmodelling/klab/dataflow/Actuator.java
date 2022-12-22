package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.lang3.StringUtils;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.model.contextualization.IPredicateClassifier;
import org.integratedmodelling.klab.api.model.contextualization.IPredicateResolver;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IAssociation;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.IVariable;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservedArtifact;
import org.integratedmodelling.klab.components.runtime.observations.StateLayer;
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.data.table.LookupTable;
import org.integratedmodelling.klab.documentation.DocumentationItem;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.documentation.extensions.DocumentationExtensions;
import org.integratedmodelling.klab.documentation.extensions.DocumentationExtensions.Annotation;
import org.integratedmodelling.klab.engine.debugger.Debug;
import org.integratedmodelling.klab.engine.runtime.ActivityBuilder;
import org.integratedmodelling.klab.engine.runtime.api.IKeyHolder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.RankedModel;
import org.integratedmodelling.klab.rest.DataflowState;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.Triple;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class Actuator implements IActuator {

    /**
     * The computation is populated with these at initialization; the computations can be recalled
     * at transitions. When scheduled in the scheduler, the target cannot be null.
     */
    public class Computation {

        public IContextualizer contextualizer;
        public IContextualizable resource;
        public IArtifact target;
        public IObservable observable;
        public String targetId;
        public IVariable variable;

        /*
         * views are non-semantic and may carry special scheduling requirements which are passed to
         * the scheduler through this field if not null.
         */
        public IViewModel.Schedule schedule;

        public Computation() {
        }

        public Computation(String targetId, Variable variable) {
            this.targetId = targetId;
            this.variable = variable;
        }

    }

    /**
     * Scope keeps status for all actuators during contextualization, so that clients can inquire.
     * 
     * @author Ferd
     *
     */
    public static class Status {

        private DataflowState.Status status = DataflowState.Status.WAITING;
        private long start;
        private long end;

        public DataflowState.Status getStatus() {
            return status;
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }

        public void start() {
            this.start = System.currentTimeMillis();
            status = DataflowState.Status.STARTED;
        }

        public void finish() {
            this.end = System.currentTimeMillis();
            status = DataflowState.Status.FINISHED;
        }

        public void abort() {
            this.end = System.currentTimeMillis();
            status = DataflowState.Status.ABORTED;
        }

        public void interrupt() {
            this.end = System.currentTimeMillis();
            status = DataflowState.Status.INTERRUPTED;
        }
    }

    // these are part of graphs so they should behave wrt. equality. Adding an ID
    // for comparison just to ensure that future changes upstream do not affect the
    // logics.
    protected String _actuatorId = NameGenerator.shortUUID();

    /**
     * The catalog of reference name -> local name for all observables referenced in this actuator
     */
    BidiMap<String, String> observableLegend = new DualHashBidiMap<>();

    List<Computation> computation = null;

    protected String name;
    private String alias;
    private INamespace namespace;
    private Observable observable;

    /*
     * The coverage is generic and is set to the coverage of the generating models. The dataflow
     * will set the actual scale of computation into the runtime scope just before calling
     * compute().
     */
    protected Coverage coverage;
    private IArtifact.Type type;
    private Dataflow dataflow;
    List<IActuator> actuators = new ArrayList<>();
    Date creationTime = new Date();
    protected Actuator parentDataflow;

    /**
     * Filled in after the dataflow is compiled, contains the name mappings that apply to this
     * actuator only when the aliased name of a referenced artifact is different from its stable
     * name in the catalog. Used to remap the names in each actuator before computation. These are
     * kept here rather than collected while computing, because actuator are topologically sorted
     * into one flat list before execution.
     * 
     */
    Map<String, String> localNames = new HashMap<>();

    // reference means that this actuator is a stand-in for another in the same
    // dataflow
    private boolean reference;
    // input means that this actuator retrieves a pre-computed artifact from the
    // context
    private boolean input;
    // export is currently unused but can be used to tag some artifacts for an
    // output port
    private boolean exported;

    // this is only for the API
    private List<IContextualizable> computedResources = new ArrayList<>();
    // we store the annotations from the model to enable probes or other
    // non-semantic options
    private List<IAnnotation> annotations = new ArrayList<>();

    /*
     * this gets a copy of the original model resource, so we can do things to it.
     */
    public void addComputation(IContextualizable resource, ISession session) {
        ((ComputableResource) resource).setOriginalObservable(this.observable);
        computedResources.add(resource);
        // the call is null if we are just building a variable to use downstream.
        IServiceCall serviceCall = null;
        if (!resource.isVariable()) {
            serviceCall = Klab.INSTANCE.getRuntimeProvider().getServiceCall(resource, observable, session);
        }
        computationStrategy.add(new Pair<>(serviceCall, resource));
    }

    public void addMediation(IContextualizable resource, Actuator target, ISession session) {
        ((ComputableResource) resource).setTargetId(target.getAlias() == null ? target.getName() : target.getAlias());
        ((ComputableResource) resource).setMediation(true);
        computedResources.add(resource);
        IServiceCall serviceCall = Klab.INSTANCE.getRuntimeProvider().getServiceCall(resource, target.observable, session);
        mediationStrategy.add(new Pair<>(serviceCall, resource));
    }

    public List<Computation> getContextualizers() {
        return computation == null ? new ArrayList<>() : computation;
    }

    public void addDocumentation(IDocumentation documentation) {
        this.getDocumentation().add(documentation);
    }

    /**
     * the specs from which the contextualizers are built: first the computation, then the
     * mediation. We keep them separated because the compiler needs to rearrange mediators and
     * references as needed. Then both get executed to produce the final list of contextualizers.
     * Each list contains a service call and its local target name, null for the main observable.
     */
    List<Pair<IServiceCall, IContextualizable>> computationStrategy = new ArrayList<>();
    List<Pair<IServiceCall, IContextualizable>> mediationStrategy = new ArrayList<>();

    /**
     * Documentation extracted from the models and other objects used and compiled at end of
     * computation
     */
    private List<IDocumentation> documentation = new ArrayList<>();

    // if this is non-null, coverage is also non-null and the actuator defines a
    // partition of the named target artifact, covering our coverage only.
    private String partitionedTarget;

    /*
     * when this is a partition, the priority reflects the ranking so that the highest ranked
     * partial can be applied last
     */
    private int priority = 0;
    private Mode mode;
    private Model model;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<IActuator> getChildren() {
        return actuators;
    }

    @Override
    public List<IActuator> getInputs() {
        List<IActuator> ret = new ArrayList<>();
        for (IActuator actuator : actuators) {
            if (((Actuator) actuator).isReference()) {
                ret.add(actuator);
            }
        }
        return ret;
    }

    @Override
    public List<IActuator> getOutputs() {
        List<IActuator> ret = new ArrayList<>();
        for (IActuator actuator : actuators) {
            if (((Actuator) actuator).exported) {
                ret.add(actuator);
            }
        }
        return ret;
    }

    /**
     * Compute the actuator. This only gets called once at initialization. Builds the
     * contextualization sequence and, if they contextualize perdurants, runs it to initialize them.
     * 
     * @param target the final artifact being computed. If this actuator handles an instantiation,
     *        the passed target is null and will be set to the first object in the result chain, or
     *        to the empty artifact if no instances are created. In the end the method will always
     *        return a non-null artifact.
     * @param scope the runtime context
     * @return the finalized observation data. TODO when an instantiator returns no instances,
     *         should return an empty observation. Currently it returns null.
     * @throws KlabException
     */
    public IArtifact compute(IArtifact target, IRuntimeScope scope) throws KlabException {

        // this.currentContext = scope;
        scope.getStatus(this).start();

        ActivityBuilder statistics = scope.getStatistics().forTarget(this);

        /*
         * poor-man attempt at reentrancy in case this has to get called more than once for any
         * reason later.
         */
        boolean definition = false;
        if (this.computation == null) {
            definition = true;
            this.computation = new ArrayList<>();
        }

        /*
         * The contextualizer chain that implements the computation is specified by service calls,
         * so it can survive dataflow serialization/deserialization. The second element is the
         * contextualized resource, the third is the original, which is the one that must end up in
         * the schedule.
         */
        List<Triple<IContextualizer, IContextualizable, IContextualizable>> computation = new ArrayList<>();
        Set<IArtifact> artifacts = new HashSet<>();

        /*
         * this localizes the names in the context to those understood by this actuator and applies
         * any requested mediation to the inputs. Target may be swapped for a mediator.
         */
        IRuntimeScope ctx = setupScope(target, scope, statistics);

        if (target == null && ctx.getTargetArtifact() != null) {
            // contextualization redefined the target, which happens in change processes
            target = ctx.getTargetArtifact();
        }

        Set<String> blockedTargets = new HashSet<>();

        for (Pair<IServiceCall, IContextualizable> service : computationStrategy) {

            if (scope.getMonitor().isInterrupted()) {
                return Observation.empty(getObservable(), scope);
            }

            String targetId = service.getSecond().getTargetId() == null ? getName() : service.getSecond().getTargetId();

            if (blockedTargets.contains(targetId)) {
                continue;
            }

            IServiceCall function = service.getFirst();

            if (function == null) {
                // aux variable: just keep for later
                computation.add(new Triple<>(null, service.getSecond(), service.getSecond()));
                continue;
            }

            if (((ComputableResource) service.getSecond()).getModifiedParameters() != null) {
                function.getParameters().putAll(((ComputableResource) service.getSecond()).getModifiedParameters());
            }

            if (service.getSecond().getTargetId() != null) {
                IObservable observable = ctx.getSemantics(service.getSecond().getTargetId());
                if (observable != null) {
                    function.getParameters().put(Extensions.TARGET_OBSERVABLE_PARAMETER, observable);
                }
            }

            Object contextualizer = Extensions.INSTANCE.callFunction(function, ctx);
            if (contextualizer == null) {
                // this happens when a condition isn't met, so it's legal.
                continue;
            }
            if (!(contextualizer instanceof IContextualizer)) {
                // this isn't
                throw new KlabValidationException(
                        "function " + service.getFirst().getName() + " does not produce a contextualizer");
            } /* TODO else call contextualize() and swap the contextualizable in the pair */

            /*
             * ensure the resource matches if needed. Don't do anything if we're scheduling an
             * occurrent, as the contextualization won't be used.
             */
            IContextualizable resource = service.getSecond();
            IContextualizable originalResource = resource;
            if (!getType().isOccurrent() && resource != null) {
                resource = resource.contextualize(target, ctx);
                if (resource.isEmpty()) {
                    // block the flow for the same target
                    blockedTargets.add(targetId);
                    continue;
                }
                ((IContextualizer) contextualizer).notifyContextualizedResource(resource, target, ctx);
            }

            computation.add(new Triple<>((IContextualizer) contextualizer, resource, originalResource));
        }

        IArtifact ret = target;

        /*
         * Keep the latest layer for all artifacts involved here, indexed by name.
         */
        Map<String, IArtifact> artifactTable = new HashMap<>();
        artifactTable.put(getName(), target);

        Set<String> knownVariables = new HashSet<>();
        Set<IArtifact> changed = new HashSet<>();

        /*
         * run the contextualization strategy with the localized context. Each contextualizer may
         * produce/require something else than the actuator's target (in case of explicit
         * retargeting) or an intermediate version requiring a different type. We use the context's
         * artifact table to keep track.
         */
        for (Triple<IContextualizer, IContextualizable, IContextualizable> contextualizer : computation) {

            /*
             * Aux variables: record to allow lazy evaluation driven by contextualizers downstream.
             */
            if (contextualizer.getFirst() == null) {

                Variable variable = Variable.create(contextualizer.getSecond(), knownVariables, this);
                knownVariables.add(contextualizer.getSecond().getTargetId());
                ctx.getVariables().put(contextualizer.getSecond().getTargetId(), variable);
                this.computation.add(new Computation(contextualizer.getThird().getTargetId(), variable));
                continue;
            }

            IObservable indirectTarget = null;

            if (contextualizer.getSecond().getTargetId() != null) {
                IArtifact indirect = ctx.getArtifact(contextualizer.getThird().getTargetId());
                if (indirect instanceof IObservation) {
                    indirectTarget = ((IObservation) indirect).getObservable();
                } else {
                    throw new IllegalStateException(
                            "cannot find indirect target observation " + contextualizer.getThird().getTargetId());
                }
            }
            String targetId = /* partitionedTarget == null ? */getName() /* : partitionedTarget */;
            IRuntimeScope context = ctx;

            if (indirectTarget != null) {
                targetId = indirectTarget.getName();
                context = context.createChild(indirectTarget);
            }

            if (!artifactTable.containsKey(targetId)) {
                artifactTable.put(targetId, context.getArtifact(targetId));
            }

            /*
             * run the contextualizer on its contextualized target (second element), unless we're
             * contextualizing an occurrent. This may get a null and instantiate a new target
             * artifact.
             */
            if (!getType().isOccurrent()) {
                artifactTable.put(targetId,
                        runContextualizer(contextualizer.getFirst(), indirectTarget == null ? this.observable : indirectTarget,
                                contextualizer.getSecond(), artifactTable.get(targetId), context, context.getScale(), changed,
                                statistics));
            }

            /*
             * define the computation for any future use.
             */
            if (definition) {
                Computation step = new Computation();
                step.contextualizer = contextualizer.getFirst();
                step.observable = indirectTarget == null ? this.observable : indirectTarget;
                step.target = target;
                step.targetId = targetId;
                step.resource = contextualizer.getThird();
                this.computation.add(step);
            }

            /*
             * if we have produced the artifact (through an instantiator), set it in the context.
             */
            if (indirectTarget == null) {
                ret = artifactTable.get(targetId);
            } else if (!scope.getMonitor().isInterrupted()) {
                context.setData(indirectTarget.getName(), artifactTable.get(targetId));
            }

            if (ret != null && model != null && !input && !artifacts.contains(ret) && !ret.isArchetype()) {
                artifacts.add(ret);
                if (ret instanceof IObservation && !(ret instanceof StateLayer)) {
                    // ACH creates problems later
                    int i = 0;
                    int toRemove = -1;
                    for (IObservation o : scope.getActuatorProducts(this)) {
                        if (o.getObservable().getName().equals(((IObservation) ret).getObservable().getName())) {
                            // added before: can only happen if this computation transformed it, so
                            // remove
                            // it.
                            toRemove = i;
                            break;
                        }
                        i++;
                    }
                    if (toRemove >= 0) {
                        List<IObservation> sortedSet = new ArrayList<>(scope.getActuatorProducts(this));
                        scope.getActuatorProducts(this).remove(sortedSet.get(toRemove));
                    }
                    scope.getActuatorProducts(this).add((IObservation) ret);
                }
            }

            /*
             * include the computed resource in the report. Empty resources will be automatically
             * ignored.
             */
            ((Report) context.getReport()).include(contextualizer.getSecond(), this);
        }

        if (scope.getMonitor().isInterrupted()) {
            scope.getStatus(this).interrupt();
            statistics.interrupt();
            return ret;
        }

        if (ret != null && ctx.getTargetArtifact() != null) {
            if (!ctx.getTargetArtifact().equals(ret)) {

                /*
                 * Computation has changed the artifact: reset into catalog unless it's a proxy
                 * artifact.
                 */
                if (!isProxy(target)) {
                    scope.setData(((IObservation) target).getObservable().getName(), ret);
                }
            }

            // FIXME the original context does not get the indirect artifacts
            if (scope.getTargetArtifact() == null || !scope.getTargetArtifact().equals(ret)) {
                ((IRuntimeScope) scope).setTarget(ret);
            }
        }

        if (model != null) {
            /*
             * notify to the report all model annotations that will create documentation items.
             */
            for (IAnnotation annotation : model.getAnnotations()) {
                Annotation ctype = DocumentationExtensions.INSTANCE.validate(annotation, scope);
                if (ctype != null) {
                    ((Report) scope.getReport()).addTaggedText(new DocumentationItem(ctype, annotation, scope, this.observable));
                }
            }
        }

        /*
         * If we're not importing a previously computed result, put outputs in product list and
         * create configurations if any.
         */
        if (!input) {

            // check out any that we escaped (built directly by actuators using the context)
            List<IObservation> secondary = new ArrayList<>();
            if (model != null) {
                for (int i = 0; i < model.getObservables().size(); i++) {
                    IArtifact artifact = ctx.getArtifact(model.getObservables().get(i).getName());
                    if (!artifacts.contains(artifact) && artifact instanceof IObservation
                            && ctx.getStructure().contains(artifact)) {
                        secondary.add((IObservation) artifact);
                    }
                }
            }

            // consolidate the lists, secondary first.
            if (!secondary.isEmpty()) {
                List<IObservation> primary = new ArrayList<>(scope.getActuatorProducts(this));
                scope.getActuatorProducts(this).clear();
                scope.getActuatorProducts(this).addAll(secondary);
                scope.getActuatorProducts(this).addAll(primary);
            }

            statistics.success();

            // IConfiguration configuration = null;
            // if (ret != null && !ret.isEmpty() && (mode == Mode.INSTANTIATION || ret instanceof
            // IState)) {
            // /*
            // * check for configuration triggered, only if we just resolved a state or
            // * instantiated 1+ objects
            // */
            // Pair<IConcept, Set<IObservation>> confdesc =
            // Observables.INSTANCE.detectConfigurations(
            // (IObservation) ret,
            // ctx.getContextObservation());
            //
            // if (confdesc != null) {
            //
            // ctx.getMonitor().info(
            // "emergent configuration " + Concepts.INSTANCE.getDisplayName(confdesc.getFirst())
            // + " detected");
            //
            // configuration = ctx.newConfiguration(confdesc.getFirst(), confdesc.getSecond(),
            // /* TODO metadata */ new Metadata());
            //
            // if (configuration != null) {
            // scope.getActuatorProducts(this).add(configuration);
            // }
            // }
            // }
        }

        /*
         * complete the provenance info with the "uses" links with dependencies
         */
        for (IActuator child : this.getActuators()) {
            IArtifact input = scope.getArtifact((((Actuator) child).observable).getReferenceName());
            if (input != null) {
                scope.getProvenance().add(ret, input, coverage, this, ctx, IAssociation.Type.uses);
            }
        }

        scope.getStatus(this).finish();

        return ret;
    }

    private boolean isProxy(IArtifact target) {
        return target instanceof RescalingState || target instanceof StateLayer;
    }

    /**
     * This is called by compute() at initialization and whenever needed by the scheduler, after
     * that.
     * 
     * @param contextualizer
     * @param observable
     * @param resource
     * @param artifact
     * @param scope
     * @param scale
     * @return
     */
    @SuppressWarnings("unchecked")
    public IArtifact runContextualizer(IContextualizer contextualizer, IObservable observable, IContextualizable resource,
            IArtifact artifact, IRuntimeScope scope, IScale scale, Set<IArtifact> changed, ActivityBuilder statistics) {

        if (scope.getMonitor().isInterrupted()) {
            return Observation.empty(getObservable(), scope);
        }

        ActivityBuilder stats = statistics.forTarget(contextualizer);

        if (contextualizer instanceof AbstractContextualizer) {
            ((AbstractContextualizer) contextualizer).setStatistics(stats);
        }

        try {

            long timer = 0;
            if (Configuration.INSTANCE.getProperty(IConfigurationService.KLAB_SHOWTIMES_PROPERTY, null) != null) {
                Debug.INSTANCE.startTimer("Contextualizing " + getObservable(), null);
            }

            /*
             * record the recontextualized resource in provenance. Artifact may be null in views or
             * other void models.
             */
            if (resource.isFinal()) {
                scope.getProvenance().add(artifact, resource, scale, this, scope, IAssociation.Type.wasDerivedFrom);
            }

            /*
             * candidates for change: if this is a process, we add all the linked suspects to check
             * later.
             */
            Map<IObservation, Long> lastUpdates = new HashMap<>();
            if (artifact instanceof IObservation) {
                lastUpdates.put((IObservation) artifact, ((IObservation) artifact).getLastUpdate());
            }

            if (artifact instanceof IProcess) {
                for (IConcept affected : Observables.INSTANCE.getAffected(observable)) {
                    for (IObservation candidate : scope.getArtifacts(affected, IObservation.class))
                        lastUpdates.put(candidate, candidate.getLastUpdate());
                }
            }

            ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
            DataflowState state = new DataflowState();
            state.setNodeId(((RuntimeScope) scope).getNodeId(resource));
            state.setStatus(DataflowState.Status.STARTED);
            state.setMonitorable(false); // for now
            session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
                    IMessage.Type.DataflowStateChanged, state));

            /*
             * This is what we get as the original content of self, which may be null or an empty
             * state, or contain the result of the previous computation, including those that create
             * state layers of a different type. This is the one to use as INPUT for computations
             * involving self. The contextualizer type may be null in resources that adapt to the
             * type requested in contextualization.
             */
            IArtifact self = artifact;

            if (artifact instanceof IState && contextualizer.getType() != null && contextualizer.getType().isState()) {
                /*
                 * Establish the container for the output: switch the storage in the state to the
                 * type needed in the compute chain, creating a layer if necessary. This is the
                 * layer to WRITE INTO. If we're looking at a secondary output of a process or other
                 * non-state contextualizer, we don't go through here.
                 */
                artifact = ((IState) artifact).as(contextualizer.getType());
            }

            if (contextualizer instanceof AbstractContextualizer) {
                // FIXME potential issue with parallel execution!
                ((AbstractContextualizer) contextualizer).setScope((RuntimeScope) scope);
            }

            if (contextualizer instanceof IStateResolver) {

                /*
                 * pass the distributed computation to the runtime provider for possible
                 * parallelization instead of hard-coding a loop here.
                 */
                IArtifact result = Klab.INSTANCE.getRuntimeProvider().distributeComputation((IStateResolver) contextualizer,
                        (IObservation) artifact, resource, addParameters(scope, self, resource), scale);

                if (result != artifact) {
                    scope.swapArtifact(artifact, result);
                }
                artifact = result;

                if (this.model != null && artifact instanceof Observation) {
                    if (scale.getTime() != null && scale.getTime().is(ITime.Type.INITIALIZATION)) {
                        Actors.INSTANCE.instrument(this.model.getAnnotations(), (Observation) artifact, scope);
                    }
                    /*
                     * tell the scope to notify internal listeners (for actors and the like)
                     */
                    scope.notifyListeners((IObservation) artifact);
                }

            } else if (contextualizer instanceof IResolver) {

                IArtifact result = ((IResolver<IArtifact>) contextualizer).resolve(artifact,
                        addParameters(scope, artifact, resource));

                if (result == null) {
                    return result;
                }

                if (result != artifact && result != null && artifact instanceof IObservation) {
                    scope.swapArtifact(artifact, result);
                }
                artifact = result;

                if (this.model != null && artifact instanceof Observation && scale.getTime() != null
                        && scale.getTime().is(ITime.Type.INITIALIZATION)) {
                    Actors.INSTANCE.instrument(this.model.getAnnotations(), (Observation) artifact, scope);
                }

            } else if (contextualizer instanceof IInstantiator) {

                List<IObjectArtifact> objects = ((IInstantiator) contextualizer).instantiate(observable,
                        addParameters(scope, self, resource));

                /*
                 * Instantiators that act as sorters or filters will return a legitimate null,
                 * meaning "I've done my job, just ignore my output".
                 */
                if (objects != null) {

                    INotification.Mode notificationMode = INotification.Mode.Normal;
                    for (IAnnotation annotation : getAnnotations()) {
                        if ("verbose".equals(annotation.getName())) {
                            notificationMode = INotification.Mode.Verbose;
                        } else if ("silent".equals(annotation.getName())) {
                            notificationMode = INotification.Mode.Silent;
                        }
                    }

                    boolean first = true;
                    for (IObjectArtifact object : objects) {

                        /*
                         * if artifact has been filtered out, remove from structure (if there) and
                         * continue
                         */
                        if (object instanceof ObservedArtifact && ((ObservedArtifact) object).isMarkedForDeletion()) {
                            scope.removeArtifact(object);
                            continue;
                        }

                        /*
                         * resolve and compute any distributed observables
                         */
                        ITaskTree<?> task = null;
                        for (Observable deferred : this.observable.getDeferredObservables()) {

                            if (task == null) {
                                task = ((ITaskTree<?>) scope.getMonitor().getIdentity()).createChild("Resolution of "
                                        + Observables.INSTANCE.getDisplayName(deferred) + " within " + object.getName());
                            }

                            /*
                             * choose the resolver linked to this actuator as parent for the
                             * resolution dataflow
                             */
                            IActuator resolver = this.getResolver();
                            if (resolver == null) {
                                resolver = this;
                            }

                            scope.resolve(deferred, (IDirectObservation) object, task,
                                    deferred.is(Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION, resolver);
                        }

                        if (notificationMode == INotification.Mode.Verbose) {
                            // just notify once to allow subscription
                            if (first) {
                                scope.updateNotifications((IObservation) artifact);
                                first = false;
                            }

                            // if it was expanded its children were asked for, presumably equivalent
                            // to
                            // notification
                            if (scope.getNotifiedObservations().contains(object.getId())
                                    && !scope.getWatchedObservationIds().contains(object.getId())) {

                                ObservationChange change = ((Observation) object)
                                        .createChangeEvent(ObservationChange.Type.StructureChange);
                                change.setExportFormats(Observations.INSTANCE.getExportFormats((IObservation) object));
                                change.setNewSize(scope.getChildArtifactsOf(object).size());
                                session.getMonitor().send(Message.create(session.getId(),
                                        IMessage.MessageClass.ObservationLifecycle, IMessage.Type.ModifiedObservation, change));
                            }
                        }

                        /*
                         * tell the scope to notify internal listeners (for actors and the like).
                         * Null object happens when contextualization wasn't successful because of
                         * resource error or other runtime condition.
                         */
                        if (object != null) {

                            scope.notifyListeners((IObservation) object);

                            /*
                             * notify end of contextualization if we're subscribed to the parent
                             */
                            if (scope.getWatchedObservationIds().contains(artifact.getId())) {

                                ((Observation) object).setContextualized(true);

                                ObservationChange change = ((Observation) object)
                                        .createChangeEvent(ObservationChange.Type.ContextualizationCompleted);
                                change.setNewSize(scope.getChildArtifactsOf(object).size());
                                change.setExportFormats(Observations.INSTANCE.getExportFormats((IObservation) object));
                                session.getMonitor().send(Message.create(session.getId(),
                                        IMessage.MessageClass.ObservationLifecycle, IMessage.Type.ModifiedObservation, change));
                            }
                        }

                        /*
                         * everything is resolved, now add any behaviors specified in annotations
                         */
                        if (object instanceof Observation) {
                            Actors.INSTANCE.instrument(getAnnotations(), (Observation) object, scope);
                        }
                    }
                }
            } else if (contextualizer instanceof IPredicateClassifier) {

                /*
                 * these are filters, so ret must be filled in already
                 */
                IConcept abstractPredicate = Observables.INSTANCE
                        .getBaseObservable(((ComputableResource) resource).getOriginalObservable().getType());
                IConcept targetPredicate = ((Observable) ((ComputableResource) resource).getOriginalObservable())
                        .getTargetPredicate();

                boolean ok = ((IPredicateClassifier<?>) contextualizer).initialize((IObjectArtifact) artifact, abstractPredicate,
                        targetPredicate, scope);

                if (ok) {

                    for (IArtifact target : artifact) {

                        @SuppressWarnings("rawtypes")
                        IConcept c = ((IPredicateClassifier) contextualizer).classify(abstractPredicate,
                                (IDirectObservation) target, scope);
                        if (c != null) {
                            // attribute and resolve
                            scope.newPredicate((IDirectObservation) target, c);
                        }
                    }
                }

                /*
                 * Tell the observation group to revise its situation and enqueue any modification
                 * messages.
                 */
                ((Observation) artifact).evaluateChanges();

                /*
                 * ensure we return a view if that's necessary
                 */
                artifact = scope.getObservationGroupView((Observable) observable, (IObservation) artifact);

            } else if (contextualizer instanceof IPredicateResolver) {

                /*
                 * This is called from a dataflow meant to resolve the attribute, so ret is the
                 * observation being characterized and the attribute is there because createTarget()
                 * has added it.
                 */
                IConcept predicate = Observables.INSTANCE.getBaseObservable(observable.getType());
                if (!((IPredicateResolver<IDirectObservation>) contextualizer).resolve(predicate, (IDirectObservation) artifact,
                        scope)) {
                    // strip the attribute that the classifier added
                    ((DirectObservation) artifact).removePredicate(predicate);
                }
                ((Observation) artifact).evaluateChanges();
            }

            /*
             * record any changes. This is used after initialization by the scheduler.
             */

            if (!scope.getMonitor().isInterrupted()) {
                for (IObservation candidate : lastUpdates.keySet()) {
                    if (candidate.getLastUpdate() > lastUpdates.get(candidate)) {
                        changed.add(candidate);
                    }
                }
            }

            /**
             * Insert any text part that the contextualizer makes available for the documentation.
             */
            if (contextualizer instanceof IDocumentationProvider) {
                for (IDocumentationProvider.Item item : ((IDocumentationProvider) contextualizer).getDocumentation()) {
                    ((Report) scope.getReport()).addTaggedText(item);
                }
            }

            // pre-compute before notification to speed up visualization
            if (artifact instanceof Observation && (scale.getTime() == null || scale.getTime().is(ITime.Type.INITIALIZATION))) {
                /*
                 * May be null for void contextualizers
                 */
                ((Observation) artifact).finalizeTransition(scope.getScale().initialization());
                ((Observation) artifact).setContextualized(true);
            }

            if (!scope.getMonitor().isInterrupted()) {
                stats.success();
                state.setStatus(DataflowState.Status.FINISHED);
            } else {
                state.setStatus(DataflowState.Status.INTERRUPTED);
                stats.interrupt();
            }

            session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
                    IMessage.Type.DataflowStateChanged, state));

            if (Configuration.INSTANCE.getProperty(IConfigurationService.KLAB_SHOWTIMES_PROPERTY, null) != null) {
                Debug.INSTANCE.endTimer(timer);
            }

        } catch (Throwable t) {
            stats.exception(t);
            throw t;
        }

        return artifact;
    }

    /**
     * Set the call parameters, if any, into the context data so that they can be found by the
     * contextualizer.
     * 
     * @param ctx
     * @param self the current artifact which will be set as "self" in the context. May be the
     *        target or a layer of the target.
     * @param second
     * @return
     */
    private IRuntimeScope addParameters(IRuntimeScope ctx, IArtifact self, IContextualizable resource) {

        IRuntimeScope ret = ctx.copy();
        if (self instanceof IProcess && resource.getTargetId() != null && ctx.getArtifact(resource.getTargetId()) != null) {
            self = ctx.getArtifact(resource.getTargetId());
        }
        if (self != null) {
            // ret.replaceTarget(self);
            ret.set("self", self);
        } else {
            ret.remove("self");
        }
        ret.setModel(model);
        ret.getVariables().putAll(ctx.getVariables());
        for (String name : resource.getParameters().keySet()) {
            ret.set(name, resource.getParameters().get(name));
        }
        return ret;
    }

    public IRuntimeScope setupScope(IArtifact target, final IRuntimeScope scope, ActivityBuilder statistics)
            throws KlabException {

        IRuntimeScope ret = scope.copy();
        IScale coverage = scope.getMergedScale(this);
        if (coverage != null) {
            ret = ret.withCoverage(coverage);
        }

        /*
         * Needed to infer formal parameters and the like when expressions are used
         */
        ret.setModel(this.model);

        // compile mediators
        List<Pair<IContextualizer, IContextualizable>> mediation = new ArrayList<>();
        for (Pair<IServiceCall, IContextualizable> service : mediationStrategy) {
            Object contextualizer = Extensions.INSTANCE.callFunction(service.getFirst(), scope);
            if (!(contextualizer instanceof IContextualizer)) {
                throw new KlabValidationException(
                        "function " + service.getFirst().getName() + " does not produce a contextualizer");
            }
            mediation.add(new Pair<>((IContextualizer) contextualizer, service.getSecond()));
        }

        Set<IArtifact> changed = new HashSet<>();

        for (IActuator input : getActuators()) {

            /*
             * TODO check: is this ever right?
             */
            if (ret.getArtifact(input.getName()) != null) {

                /*
                 * scan mediations and apply them as needed
                 */
                for (Pair<IContextualizer, IContextualizable> mediator : mediation) {

                    String targetArtifactId = mediator.getSecond().getMediationTargetId() == null
                            ? null
                            : mediator.getSecond().getMediationTargetId();

                    if (targetArtifactId.equals(input.getAlias())) {
                        IArtifact artifact = ret.getArtifact(targetArtifactId);
                        /*
                         * TODO (I think): if we have own coverage, must reinterpret the artifact
                         * through the new scale.
                         */
                        IArtifact mediated = runContextualizer(mediator.getFirst(), this.observable, mediator.getSecond(),
                                artifact, ret, ret.getScale(), changed, statistics);

                        ret.setData(targetArtifactId, mediated);
                    }
                }
            }
        }

        if (this.getType() == IArtifact.Type.PROCESS) {
            ret = ret.targetForChange();
        }

        return ret;
    }

    public String toString() {
        return "<" + getName() + ((getAlias() != null && !getAlias().equals(getName())) ? " as " + getAlias() : "") + " ["
                + (computationStrategy.size() + mediationStrategy.size()) + "]>";
    }

    /**
     * Reconstruct or return the source code for this actuator.
     * 
     * @param offset
     * @return
     */
    protected String encode(int offset, List<IActuator> children) {
        String ofs = StringUtils.repeat(" ", offset);
        String ret = "";
        if (!isPartition() && getObservable() != null) {
            ret = ofs + "@semantics(type='" + getObservable().getDeclaration() + "'" + encodePredicates(observable) + ")\n";
        }
        if (getModel() != null) {
            ret += ofs + "@model(" + getModel().getName() + ")\n";
        }
        return ret + ofs + (input ? "import " : "") + (exported ? "export " : "")
                + (isPartition() ? "partition" : getKdlActorType()) + " " + getKdlName() + encodeBody(offset, ofs, children);
    }

    private String getKdlName() {
        String ret = getName();
        if (ret.contains(" ") || StringUtils.containsWhitespace(ret) || StringUtil.containsUppercase(ret)) {
            ret = "'" + ret + "'";
        }
        return ret;
    }

    private String getKdlActorType() {
        return getType().name().toLowerCase();
    }

    protected String encodePredicates(Observable observable) {
        String ret = "";
        if (!observable.getResolvedPredicates().isEmpty()) {
            for (IConcept key : observable.getResolvedPredicates().keySet()) {
                ret += (ret.isEmpty() ? "" : ", ") + "'" + key.getDefinition() + "': '"
                        + observable.getResolvedPredicates().get(key).getDefinition() + "'";
            }
        }
        return ret.isEmpty() ? ret : (", with={" + ret + "}");
    }

    public boolean isPartition() {
        return partitionedTarget != null;
    }

    public void setPartitionedTarget(String targetId) {
        this.partitionedTarget = targetId;
    }

    public String getPartitionedTarget() {
        return this.partitionedTarget;
    }

    public List<Pair<IServiceCall, IContextualizable>> getMediationStrategy() {
        return mediationStrategy;
    }

    public List<Pair<IServiceCall, IContextualizable>> getComputationStrategy() {
        return computationStrategy;
    }

    protected Actuator makeDataflowStructure(IActuator parent, List<IActuator> children, Graph<IActuator, DefaultEdge> graph) {

        graph.addVertex(this);
        for (IActuator actuator : (children == null || children.isEmpty()) ? getSortedChildren(this, false) : children) {
            if (actuator instanceof Dataflow) {
                Pair<IActuator, List<IActuator>> structure = ((Dataflow) actuator).getResolutionStructure();
                if (structure == null) {
                    for (IActuator act : actuator.getChildren()) {
                        ((Actuator) act).makeDataflowStructure(this, null, graph);
                    }
                } else {
                    ((Actuator) structure.getFirst()).makeDataflowStructure(this, structure.getSecond(), graph);
                }
            } else {
                ((Actuator) actuator).makeDataflowStructure(this, null, graph);
            }
        }

        if (parent != null) {
            graph.addEdge(this, parent);
        }

        return this;
    }

    protected String encodeBody(int offset, String ofs, List<IActuator> children) {

        boolean hasBody = actuators.size() > 0 || computationStrategy.size() > 0 || mediationStrategy.size() > 0
                || mode == Mode.RESOLUTION;

        String ret = "";

        if (hasBody) {

            ret = " {\n";

            for (IActuator actuator : (children == null || children.isEmpty()) ? getSortedChildren(this, false) : children) {

                if (actuator instanceof Dataflow) {
                    Pair<IActuator, List<IActuator>> structure = ((Dataflow) actuator).getResolutionStructure();
                    if (structure == null) {
                        for (IActuator act : actuator.getChildren()) {
                            ret += ((Actuator) act).encode(offset + 3, null) + "\n";
                        }
                    } else {
                        ret += ((Actuator) structure.getFirst()).encode(offset + 3, structure.getSecond()) + "\n";
                    }
                } else {
                    ret += ((Actuator) actuator).encode(offset + 3, null) + "\n";
                }
            }

            int cout = mediationStrategy.size() + computationStrategy.size();
            int nout = 0;
            for (int i = 0; i < mediationStrategy.size(); i++) {
                ret += (nout == 0 ? (ofs + "   compute" + (cout < 2 ? " " : ("\n" + ofs + "     "))) : ofs + "     ")
                        + (mediationStrategy.get(i).getSecond().getMediationTargetId() == null
                                ? ""
                                : (mediationStrategy.get(i).getSecond().getMediationTargetId() + " >> "))
                        + mediationStrategy.get(i).getFirst().getSourceCode()
                        + (nout < mediationStrategy.size() - 1 || computationStrategy.size() > 0 ? "," : "") + "\n";
                nout++;
            }

            for (int i = 0; i < computationStrategy.size(); i++) {
                ret += (nout == 0 ? (ofs + "   compute" + (cout < 2 ? " " : ("\n" + ofs + "     "))) : ofs + "     ")
                        + (computationStrategy.get(i).getSecond().isVariable()
                                ? (computationStrategy.get(i).getSecond().getTargetId() + " <- ")
                                : "")
                        + (computationStrategy.get(i).getSecond().isVariable()
                                ? computationStrategy.get(i).getSecond().getSourceCode()
                                : computationStrategy.get(i).getFirst().getSourceCode())
                        + ((computationStrategy.get(i).getSecond().getTarget() == null
                                || computationStrategy.get(i).getSecond().isVariable()
                                || computationStrategy.get(i).getSecond().getTarget().equals(observable))
                                        ? ""
                                        : (" >> " + computationStrategy.get(i).getSecond().getTarget().getName()))
                        + (nout < computationStrategy.size() - 1 ? "," : "") + "\n";
                nout++;
            }

            ret += ofs + "}";
        }

        if (getAlias() != null && !getAlias().equals(getName())) {
            ret += " as " + getAlias();
        }

        if (coverage != null && !coverage.isEmpty()) {
            List<IServiceCall> scaleSpecs = ((Scale) coverage).getKimSpecification();
            if (!scaleSpecs.isEmpty()) {
                // TODO just for debugging
                ret += " " + ((Scale) coverage).getSpace().getEnvelope(); // " over
                                                                          // scale_specifications_to_be_externalized()";
                // for (int i = 0; i < scaleSpecs.size(); i++) {
                // ret += " " + scaleSpecs.get(i).getSourceCode()
                // + ((i < scaleSpecs.size() - 1) ? (",\n" + ofs + " ") : "");
                // }
            }
        }

        return ret;
    }

    protected String dump() {
        return dump(this, 0);
    }

    protected String dump(Actuator actuator, int offset) {

        String ret = "";
        String spacer = StringUtil.repeat('.', offset);
        String ofs = StringUtil.repeat('.', offset + 3);

        ret += spacer + ((actuator instanceof Dataflow) ? "DATAFLOW " : "ACTUATOR ") + (actuator.getType() + " ")
                + ((actuator instanceof Dataflow) ? ((Dataflow) actuator).getDataflowSubjectName() : actuator.getName())
                + ((actuator instanceof Dataflow) ? (" (" + ((Dataflow) actuator).getDescription() + ")") : "")
                + (actuator.getAlias() == null ? "" : (" as " + actuator.getAlias())) + "\n";

        for (IActuator act : actuator.actuators) {
            ret += dump((Actuator) act, offset + 3);
        }

        // int cout = actuator.mediationStrategy.size() +
        // actuator.computationStrategy.size();
        int nout = 0;
        for (int i = 0; i < actuator.mediationStrategy.size(); i++) {
            ret += ofs + "MEDIATE "
                    + (actuator.mediationStrategy.get(i).getSecond().getMediationTargetId() == null
                            ? ""
                            : (actuator.mediationStrategy.get(i).getSecond().getMediationTargetId() + " >> "))
                    + actuator.mediationStrategy.get(i).getFirst().getSourceCode()
                    + (nout < actuator.mediationStrategy.size() - 1 || actuator.computationStrategy.size() > 0 ? "," : "") + "\n";
            nout++;
        }

        for (int i = 0; i < actuator.computationStrategy.size(); i++) {
            ret += ofs + "COMPUTE "
                    + (actuator.computationStrategy.get(i).getSecond().isVariable()
                            ? (actuator.computationStrategy.get(i).getSecond().getTargetId() + " <- ")
                            : "")
                    + (actuator.computationStrategy.get(i).getSecond().isVariable()
                            ? actuator.computationStrategy.get(i).getSecond().getSourceCode()
                            : actuator.computationStrategy.get(i).getFirst().getSourceCode())
                    + ((actuator.computationStrategy.get(i).getSecond().getTarget() == null
                            || actuator.computationStrategy.get(i).getSecond().isVariable()
                            || actuator.computationStrategy.get(i).getSecond().getTarget().equals(actuator.getObservable()))
                                    ? ""
                                    : (" >> " + actuator.computationStrategy.get(i).getSecond().getTarget().getName()))
                    + (nout < actuator.computationStrategy.size() - 1 ? "," : "") + "\n";
            nout++;
        }

        return ret;

    }

    public static Actuator create(Dataflow dataflow, IResolutionScope.Mode mode) {
        Actuator ret = new Actuator();
        ret.mode = mode;
        ret.dataflow = dataflow;
        return ret;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Observable getObservable() {
        return observable;
    }

    public void setObservable(Observable observable) {
        this.observable = observable;
        if (observable.getArtifactType().isOccurrent()) {
            this.dataflow.notifyOccurrents();
        }
    }

    public INamespace getNamespace() {
        return namespace;
    }

    public void setNamespace(INamespace namespace) {
        this.namespace = namespace;
    }

    public IArtifact.Type getType() {
        return type;
    }

    public void setType(IArtifact.Type type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReference(boolean reference) {
        this.reference = reference;
    }

    public boolean isReference() {
        return reference;
    }

    @Override
    public boolean isComputed() {
        return computationStrategy.size() > 0;
    }

    public boolean isMerging() {
        for (IActuator child : getActuators()) {
            if (((Actuator) child).isPartition()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<IContextualizable> getComputation() {
        return computedResources;
    }

    public boolean isExported() {
        return exported;
    }

    public List<IAnnotation> getAnnotations() {
        return annotations;
    }

    /**
     * coverage in an actuator is only set when it covers a sub-scale compared to that of
     * resolution. The same field is used in a dataflow to define the overall coverage.
     */
    public void setCoverage(Coverage coverage) {
        this.coverage = coverage;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /*
     * Called after target was created. Not meant for listeners but to complete the observation with
     * its model-dependent information before the model goes away.
     * 
     * @param observation
     */
    public void notifyNewObservation(IObservation observation) {

        /*
         * transmit all annotations so we can use those that affect the runtime
         */
        observation.getAnnotations().addAll(annotations);

        /*
         * Assess if the computation implies having a datakey and if so, transmit it to the state.
         */
        if (observation instanceof IState && computationStrategy.size() > 0) {
            IDataKey dataKey = findDataKey();
            if (dataKey != null && observation instanceof IKeyHolder) {
                ((IKeyHolder) observation).setDataKey(dataKey);
            }
        }

    }

    /*
     * Observations have a datakey if their last resource applied to the main observable is a lookup
     * table producing concepts or a classification. They also have it if the main observable is a
     * transformation of a dependency that does.
     */
    private IDataKey findDataKey() {

        if (computationStrategy.size() > 0) {
            IContextualizable lastResource = computationStrategy.get(computationStrategy.size() - 1).getSecond();
            if (lastResource.getClassification() != null || lastResource.getAccordingTo() != null) {
                return ((ComputableResource) lastResource).getValidatedResource(IClassification.class);
            } else if (lastResource.getLookupTable() != null) {
                if (((ComputableResource) lastResource).getValidatedResource(LookupTable.class).isKey()) {
                    return ((ComputableResource) lastResource).getValidatedResource(ILookupTable.class);
                }
            }
        }
        return null;
    }

    public void setInput(boolean b) {
        this.input = b;
    }

    @Override
    public boolean isInput() {
        return input;
    }

    public String getId() {
        return _actuatorId;
    }

    public String getDataflowId() {
        return getDataflow().getId();
    }

    public IResolutionScope.Mode getMode() {
        return this.mode;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return this.model instanceof RankedModel ? ((RankedModel) this.model).getDelegate() : this.model;
    }

    public Dataflow getDataflow() {
        return this.dataflow;
    }

    /**
     * Find the actuator with the given name. Call it on the dataflow for the full experience.
     * 
     * @param name
     * @return
     */
    public Actuator getActuator(String name) {
        if (this.name.equals(name)) {
            return this;
        }
        for (IActuator actuator : getActuators()) {
            Actuator ret = ((Actuator) actuator).getActuator(name);
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

    public List<Actuator> dependencyOrder() {
        List<Actuator> ret = new ArrayList<>();
        _dependencyOrder(this, ret, new HashSet<Actuator>(), buildCatalog());
        return ret;
    }

    /*
     * build a catalog of all the non-reference actuators descending from this one
     */
    private Map<String, Actuator> buildCatalog() {
        Map<String, Actuator> catalog = new HashMap<>();
        _buildCatalog(this, catalog);
        return catalog;
    }

    private void _buildCatalog(Actuator actuator, Map<String, Actuator> catalog) {

        if (!actuator.isReference()) {
            catalog.put(actuator.getName(), actuator);
        }
        for (IActuator child : actuator.getActuators()) {
            _buildCatalog((Actuator) child, catalog);
        }
    }

    private void _dependencyOrder(Actuator actuator, List<Actuator> ret, Set<Actuator> added, Map<String, Actuator> catalog) {

        if (actuator.isReference()) {
            actuator = catalog.get(actuator.getName());
            if (actuator == null) {
                return;
            }
        }

        boolean add = !added.contains(actuator);

        for (IActuator child : getSortedChildren(actuator, true)) {
            _dependencyOrder((Actuator) child, ret, added, catalog);
        }

        if (add && !added.contains(actuator)) {
            ret.add(actuator);
            added.add(actuator);
        }
    }

    /*
     * Return our children in the original order, except any actuator with deferred observable is
     * put last; if they're partitions, sort them by increasing priority (the opposite of their
     * natural order) so that the highest-priority computes last, just in case overlaps happen.
     * Note: All partitions of the same observable must go after the dependencies
     * 
     * @param actuator
     * 
     * @return
     */
    protected List<IActuator> getSortedChildren(Actuator actuator, boolean skipSubdataflows) {

        List<IActuator> ret = new ArrayList<>();
        List<IActuator> partitions = new ArrayList<>();
        List<IActuator> deferred = new ArrayList<>();
        for (IActuator act : actuator.getChildren()) {

            // these are sub-dataflow that are run after instantiation
            if (skipSubdataflows && (act instanceof Dataflow || act.getType() == IArtifact.Type.RESOLVE)) {
                continue;
            }

            if (!(act instanceof Dataflow) && ((Actuator) act).observable.getDeferredObservables().size() > 0) {
                deferred.add(act);
            } else if (!(act instanceof Dataflow) && ((Actuator) act).observable.equals(actuator.observable)) {
                partitions.add(act);
            } else {
                ret.add(act);
            }
        }

        ret.addAll(deferred);

        if (partitions.size() > 1) {
            partitions.sort(new Comparator<IActuator>(){

                @Override
                public int compare(IActuator o1, IActuator o2) {
                    int o1priority = ((Actuator) o1).priority;
                    int o2priority = ((Actuator) o2).priority;
                    return Integer.compare(o2priority, o1priority);
                }
            });
        }

        ret.addAll(partitions);

        return ret;
    }

    /*
     * Notify all computed artifacts on the message bus. Compute() may not have been called if there
     * is no computation, so if we have no artifacts check first if we have a product with the same
     * name (happens with countables when no resolver was found). TODO ensure that @probe
     * annotations are honored.
     * 
     * @param isMainObservable
     */
    public void notifyArtifacts(boolean isMainObservable, IRuntimeScope scope) {

        if (Klab.INSTANCE.getMessageBus() == null || isPartition()) {
            return;
        }

        if (scope.getActuatorProducts(this).isEmpty()) {
            if (scope.getArtifact(this.name) != null && !scope.getArtifact(this.name).isArchetype()) {
                scope.getActuatorProducts(this).add((IObservation) scope.getArtifact(this.name));
            }
        }

        boolean isMain = isMainObservable;
        if (!isMain) {
            for (IAnnotation annotation : annotations) {
                if (annotation.getName().equals("main")) {
                    isMain = true;
                    break;
                }
            }
        }

        for (IObservation product : scope.getActuatorProducts(this)) {

            if (product.isArchetype()) {
                continue;
            }

            if (isMain) {
                ((Observation) product).getChangeset().add(ObservationChange.main(product, scope));
            }

            /*
             * only notify states (which are not notified on creation) or anything that has changed,
             * such as groups with new children.
             */
            if (product instanceof IState || ((Observation) product).getChangeset().size() > 0) {
                scope.updateNotifications(product);
            }
        }

        /*
         * when all is computed, reuse the context to render the documentation templates.
         */
        for (IDocumentation doc : getDocumentation()) {
            for (IDocumentation.Template template : doc.get(Trigger.DEFINITION)) {
                if (doc.instrumentReport(scope.getReport(), template, Trigger.DEFINITION, this, scope)) {
                    ((Report) scope.getReport()).include(template, scope, doc);
                }
            }
        }

    }

    public boolean isFilter() {
        return observable.getDescriptionType() == IActivity.Description.CHARACTERIZATION
                || observable.getDescriptionType() == IActivity.Description.CLASSIFICATION;
    }

    boolean hasDependency(IActuator dependency) {
        for (IActuator actuator : actuators) {
            if (((Actuator) actuator).getObservable().resolvesStrictly(((Actuator) dependency).observable)) {
                return true;
            }
        }
        return false;
    }

    KimServiceCall setFilteredArgument(IServiceCall function, String filteredArgument) {

        IPrototype p = Extensions.INSTANCE.getPrototype(function.getName());
        if (p != null && p.isFilter()) {
            String artifactArg = null;
            for (Argument argument : p.listImports()) {
                artifactArg = argument.getName();
                break; // yes, break
            }
            if (artifactArg != null) {
                function = ((KimServiceCall) function).copy();
                function.getParameters().put(artifactArg, filteredArgument);
            }
        }
        return (KimServiceCall) function;
    }

    IContextualizable setFilteredArgument(IContextualizable resource, String filteredArgument) {
        if (resource.getServiceCall() != null) {
            resource = ((ComputableResource) resource).copy();
            ((ComputableResource) resource).setServiceCall(setFilteredArgument(resource.getServiceCall(), filteredArgument));
        }
        return resource;
    }

    public Actuator getReference() {
        Actuator ret = new Actuator();
        ret.name = this.name;
        ret.alias = this.alias;
        ret.reference = true;
        ret.type = this.type;
        ret.observable = this.observable;
        ret.namespace = this.namespace;
        // ret.session = this.session;
        ret.mode = this.mode;
        return ret;
    }

    public Actuator withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public ObservedConcept getObservedConcept() {
        return new ObservedConcept(this.observable, this.mode);
    }

    // public void setDataflow(Dataflow dataflow) {
    // this.dataflow = dataflow;
    // }

    public void setExport(boolean b) {
        this.exported = true;
    }

    public boolean isTrivial() {
        return actuators.isEmpty() && computationStrategy.isEmpty() && mediationStrategy.isEmpty();
    }

    public Map<String, String> getLocalNames() {
        return this.localNames;
    }

    public List<IDocumentation> getDocumentation() {
        return documentation;
    }

    @Override
    public String getAlias(IObservable observable) {
        return observableLegend.get(observable.getReferenceName());
    }

    @Override
    public ICoverage getCoverage() {
        return coverage;
    }

    @Override
    public long getTimestamp() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IProvenance getProvenance() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return actuators.size() == 0;
    }

    @Override
    public List<IActuator> getActuators() {
        List<IActuator> ret = new ArrayList<>();
        for (IActuator actuator : actuators) {
            if (!(actuator instanceof IDataflow)) {
                ret.add(actuator);
            }
        }
        return ret;
    }

    @Override
    public List<IDataflow<?>> getDataflows() {
        List<IDataflow<?>> ret = new ArrayList<>();
        for (IActuator actuator : actuators) {
            if (actuator instanceof IDataflow) {
                ret.add((IDataflow<?>) actuator);
            }
        }
        return ret;
    }

    /**
     * If this actuator is a resolver (type == void), return self. Otherwise, check if it contains a
     * resolution dataflow (which is mandatorily the first) and if so, return its resolver actuator.
     * Otherwise, return null.
     * 
     * @return
     */
    public IActuator getResolver() {
        if (this.getType() == IArtifact.Type.RESOLVE) {
            return this;
        }
        if (this.actuators.size() > 0 && this.actuators.get(0) instanceof Dataflow) {
            return ((Dataflow) this.actuators.get(0)).getResolver();
        }
        return null;
    }

    void collectComputed(Set<IObservedConcept> ret) {
        if (this.isComputed() && observable.getArtifactType() != IArtifact.Type.VOID) {
            ret.add(getObservedConcept());
        }
        for (IActuator act : this.actuators) {
            ((Actuator) act).collectComputed(ret);
        }
    }

}
