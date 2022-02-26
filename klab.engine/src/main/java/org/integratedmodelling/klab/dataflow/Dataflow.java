package org.integratedmodelling.klab.dataflow;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IContextualizable.InteractiveParameter;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Interaction;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.AbstractTask;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.ResolvedArtifact;
import org.integratedmodelling.klab.rest.DataflowReference;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.TypeUtils;
import org.integratedmodelling.klab.utils.Utils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * The semantically aware implementation of {@link IDataflow}, built by the k.LAB runtime as a
 * result of a semantic resolution. Its {@link #run(IScale, IMonitor)} produces {@link IObservation
 * observations} unless the dataflow is {@link #isEmpty() empty}.
 * <p>
 * Each context has a single, hierarchically organized dataflow made up of sub-dataflow. The root
 * dataflow resolves the context (object resolvers are void as they do not produce observations, the
 * object type is reserved for instantiators). Each actuator that instantiates objects also has the
 * void dataflow(s) that resolve them, plus any other actuator run in the context of the
 * instantiated objects. Successive observations add dataflows to the parent actuator, according to
 * the scope of each resolution, contributing to one overall dataflow which is visualized to the
 * user as it changes.
 * <p>
 * A matching implementation may be provided to run non-semantic workflows in semantically unaware
 * computation engines, or a translator could be used to provide commodity semantics to use this one
 * so that k.LAB servers can serve indirectAdapters through URNs.
 * <p>
 * A dataflow is a void actuator in k.DL. A void actuator resolves the context it's run into. When
 * objects are created, the containing actuator may contain the dataflow(s) to resolve it and/or
 * specific qualities declared "within" the object.
 * 
 * @author Ferd
 *
 */
public class Dataflow extends Actuator implements IDataflow<IArtifact> {

    public static final String ACTUATOR = "ACTUATOR";

    private String description;

    // @Deprecated
    // private DirectObservation context;
    // @Deprecated
    // private ResolutionScope resolutionScope;
    // @Deprecated
    // private IDirectObservation relationshipSource;
    // @Deprecated
    // private IDirectObservation relationshipTarget;

    // /**
    // * Each dataflow used to resolve subjects within this one is recorded here with
    // * all the subjects it was used for.
    // */
    // @Deprecated
    // Map<Dataflow, List<IDirectObservation>> inherentResolutions = new HashMap<>();

    // /*
    // * if true, we observe occurrents and we may need to upgrade a generic T context
    // * to a specific one.
    // */
    // @Deprecated
    // boolean hasOccurrents = false;
    // // if true, we have one time step and occurrents, so we should autostart
    // boolean autoStartTransitions = false;

    // execution parameters for user modification if running interactively
    private List<InteractiveParameter> fields = new ArrayList<>();
    private List<Pair<IContextualizable, List<String>>> resources = new ArrayList<>();
    private List<Pair<IAnnotation, List<String>>> annotations = new ArrayList<>();
    // @Deprecated
    // private IMetadata metadata;
    // @Deprecated
    // private Collection<IObservation> configurationTargets;
    // @Deprecated
    // private String targetName;

    // // dependency structure, shared along the entire hierarchy
    // @Deprecated // should be in scope
    // Graph<ObservedConcept, DefaultEdge> dependencies;

    // /**
    // * This is available for inspection after dataflow.run() in case the dataflow is
    // * only run for side effects on the scope. This happens, for example, during
    // * in-resolution characterization of abstract identities.
    // */
    // @Deprecated
    // IRuntimeScope runtimeScope = null;

    class AnnotationParameterValue {

        String annotationId;
        String parameterName;
        String value;
        Type type;

        public AnnotationParameterValue(String id, String id2, String initialValue, Type type) {
            this.annotationId = id;
            this.parameterName = id2;
            this.value = initialValue;
            this.type = type;
        }
    }

    List<AnnotationParameterValue> annotationParameters = new ArrayList<>();
    // @Deprecated
    // private Scale resolutionScale;
    // @Deprecated
    // private boolean secondary;

    // /*
    // * keep the IDs of the dataflows already merged (during run()) so that we only
    // * merge each dataflow once when reusing them for multiple instances.
    // */
    // @Deprecated
    // Set<String> dataflowIds = new HashSet<>();
    // private ObservationGroup observationGroup;
    // @Deprecated
    // private Mode notificationMode = INotification.Mode.Normal;

    /*
     * primary dataflows are created by first-level observation tasks. They run temporal transitions
     * with a schedule that also includes their child dataflows.
     */
    private boolean primary;

    private Dataflow(Dataflow parent) {
        this.parentDataflow = parent;
    }

    public Dataflow(/* ISession session, */Actuator parent) {
        // this.session = session;
        this.parentDataflow = parent;
        if (this.parentDataflow != null) {
            this.parentDataflow.childDataflows.add(this);
        }
    }

    // /**
    // * If the dataflow is reused more than once, this must be called before any
    // * repeated execution.
    // */
    // public void reset() {
    // this.resolutionScale = null;
    // resetScales();
    // }

    @Override
    public IArtifact run(IScale scale, IContextualizationScope scope) throws KlabException {
        return run(scale, null, (IRuntimeScope) scope);
    }

    /**
     * Pass an actuator to use to register ourselves into. If the parent is not null, notify the
     * task through the monitor (top-level observation do it manually so that any resolution issue
     * also get reported as pertaining to the same observation task).
     * 
     * FIXME MUST USE SCOPE!
     * 
     * @param scale
     * @param parentComputation
     * @param scope
     * @return
     * @throws KlabException
     */
    public IArtifact run(IScale scale, Actuator parentComputation, IRuntimeScope scope) throws KlabException {

        // reset();

        /*
         * build the observable dependency hierarchy to put in the runtime context. The scheduler
         * will use this to determine which actuators need recomputation among those that have only
         * implicit change associated.
         */
        scope.setDependencyGraph(buildDependencies());

        /*
         * we need the initialization scale for the dataflow but we must create our targets with the
         * overall scale. Problem is, occurrent actuators must create their states using their own
         * resolution if any is specified.
         */
        if (actuators.size() == 0) {
            if (scope.getResolutionScope().getResolvedArtifact() != null) {
                return ((ResolvedArtifact) scope.getResolutionScope().getResolvedArtifact()).getArtifact();
            }
            return Observation.empty();
        }

        /*
         * a trivial dataflow is the one that won't do anything but create the target, and notifying
         * it would be a lot of notification if it's called for 3000 instantiated objects.
         */
        boolean trivial = isTrivial();

        if (!trivial && parentComputation != null
                && scope.getMonitor().getIdentity() instanceof AbstractTask) {
            ((AbstractTask<?>) scope.getMonitor().getIdentity()).notifyStart();
        }

        ISession session = scope.getSession();

        /*
         * Set the .partialScale field in the scope for all actuators that represent partitions of
         * the overall scale to reflect the portion of the actual scale they must cover.
         */
        definePartitions(scale, scope);

        if (session != null && session.isInteractive()) {
            /*
             * collect all computables with interaction switched on and wait for user response
             * before moving on.
             * 
             * TODO add annotation processing for models
             */
            this.fields = new ArrayList<>();
            this.resources = new ArrayList<>();
            for (Actuator actuator : collectActuators()) {

                // ?= in observable annotations
                if (actuator.getModel() != null) {
                    for (IObservable o : CollectionUtils.join(actuator.getModel().getObservables(),
                            actuator.getModel().getDependencies())) {
                        List<String> parameterIds = null;
                        for (IAnnotation annotation : o.getAnnotations()) {
                            for (InteractiveParameter parameter : Interaction.INSTANCE
                                    .getInteractiveParameters(annotation, o)) {
                                if (parameterIds == null) {
                                    parameterIds = new ArrayList<>();
                                }
                                fields.add(parameter);
                                parameterIds.add(parameter.getId());
                                annotationParameters.add(new AnnotationParameterValue(
                                        ((Annotation) annotation).getId(),
                                        parameter.getId(), parameter.getInitialValue(), parameter.getType()));
                            }
                            if (parameterIds != null) {
                                this.annotations.add(new Pair<>(annotation, parameterIds));
                            }
                        }
                    }

                    // interactive computations
                    for (IContextualizable computable : actuator.getComputation()) {
                        List<String> parameterIds = null;
                        for (InteractiveParameter parameter : Interaction.INSTANCE.getInteractiveParameters(
                                computable,
                                actuator.getModel())) {
                            if (parameterIds == null) {
                                parameterIds = new ArrayList<>();
                            }
                            fields.add(parameter);
                            parameterIds.add(parameter.getId());
                        }
                        if (parameterIds != null) {
                            this.resources.add(new Pair<>(computable, parameterIds));
                        }
                    }
                }
            }
            if (fields.size() > 0) {
                /*
                 * Issue request, wait for answer and reset parameters in the computation. Method
                 * returns all interactive observable annotation parameters for recording.
                 */
                Collection<Triple<String, String, String>> values = Interaction.INSTANCE
                        .submitParameters(this.resources, this.fields, session);

                if (values == null) {
                    return null;
                }

                for (Triple<String, String, String> annotationValue : values) {
                    AnnotationParameterValue aval = getAnnotationValueFor(annotationValue.getFirst(),
                            annotationValue.getSecond());
                    if (aval != null /* should never happen but implementation may change */) {
                        aval.value = annotationValue.getThird();
                    }
                }
            }
        }

        /*
         * Initialization run, which will also schedule and run any further temporal actions. This
         * is normally one initialization actuator plus any additional process scheduling enqueued
         * by resolving inferred change in occurrent contexts.
         */
        IArtifact ret = null;
        try {
            ret = Klab.INSTANCE.getRuntimeProvider()
                    .compute(this, scale.initialization(), scope.getResolutionScope(), scope.getMonitor())
                    .get();
        } catch (Throwable e) {
            if (scope.getMonitor().isInterrupted()) {
                return null;
            }
            if (!trivial && parentComputation != null
                    && scope.getMonitor().getIdentity() instanceof AbstractTask) {
                ((AbstractTask<?>) scope.getMonitor().getIdentity()).notifyAbort(e);
                return null;
            }
        }

        Set<String> dataflowIds = new HashSet<>();
        Dataflow rootDataflow = null;
        boolean added = false;
        if (parentComputation == null && ret != null) {
            rootDataflow = this;
            ((RuntimeScope) ((Observation) ret).getScope()).setDataflow(this);
        } else if (ret != null) {

            rootDataflow = (Dataflow) ((Observation) ret).getScope().getDataflow();

            if (!dataflowIds.contains(this.getName())) {

                added = true;
                dataflowIds.add(this.getName());

                Actuator parent = parentComputation;

                // again, this is currently just one actuator
                for (IActuator actuator : actuators) {

                    // I am the resolver
                    if (actuator.getType() == Type.VOID) {
                        parent.getActuators().add(actuator);
                    } else if (parent.getType() != Type.VOID) {

                        /*
                         * should be within an instantiator, which at this point has resolved its
                         * instances so it should have a void child with the same name
                         */
                        for (IActuator pc : parent.getActuators()) {
                            if (pc.getType() == Type.VOID && pc.getName().equals(parent.getName())) {
                                parent = (Actuator) pc;
                                break;
                            }
                        }
                        if (parent.getType() == Type.VOID) {
                            parent.getActuators().add(actuator);
                        }
                    }
                }
            }
        }

        if (added && !trivial && isPrimary()) {
            /*
             * send dataflow after execution is finished. TODO add style elements or flags to make
             * sure it's shown statically.
             */
            session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.TaskLifecycle,
                    IMessage.Type.DataflowCompiled,
                    new DataflowReference(session.getMonitor().getIdentity().getId(),
                            getKdlCode(), ContextualizationStrategy.getElkGraph(this, scope))));
            if (Configuration.INSTANCE.isEchoEnabled()) {
                System.out.println(rootDataflow.getKdlCode());
            }
        }

        if (!trivial && parentComputation != null
                && scope.getMonitor().getIdentity() instanceof AbstractTask) {
            ((AbstractTask<?>) scope.getMonitor().getIdentity()).notifyEnd();
        }

        return ret;
    }

    private void definePartitions(IScale scale, IRuntimeScope scope) {
        _definePartialScales(this, (Scale) scale, scope);
    }

    private Scale _definePartialScales(Actuator actuator, Scale current, IRuntimeScope scope) {

        if (actuator.getModel() != null) {

            Scale mcoverage = actuator.getModel().getCoverage(scope.getMonitor());
            if (!mcoverage.isEmpty() || actuator.isPartition()) {
                Scale coverage = mcoverage;
                if (actuator.isPartition()) {
                    coverage = current.merge(mcoverage, LogicalConnector.INTERSECTION);
                    scope.setMergedScale(actuator, coverage.merge(current));
                }

                /*
                 * merge in the current scale. The coverage of the current actuator defines the
                 * overall extents if any are set.
                 */

                if (actuator.isPartition()) {
                    /*
                     * remove the part we handled so that the next will not cover it.
                     */
                    current = current.merge(coverage, LogicalConnector.EXCLUSION);
                }
            }
        }

        for (IActuator child : actuator.getActuators()) {
            current = _definePartialScales((Actuator) child, current, scope);
        }

        return current;
    }

    private List<Actuator> collectActuators() {
        List<Actuator> ret = new ArrayList<>();
        _collectActuators(actuators, ret);
        return ret;
    }

    private void _collectActuators(List<IActuator> actuators, List<Actuator> ret) {
        for (IActuator actuator : actuators) {
            ret.add((Actuator) actuator);
            _collectActuators(actuator.getActuators(), ret);
        }
    }

    private Graph<IObservedConcept, DefaultEdge> buildDependencies() {
        Graph<IObservedConcept, DefaultEdge> ret = new DefaultDirectedGraph<>(DefaultEdge.class);
        boolean primary = true;
        for (IActuator actuator : getActuators()) {
            buildDependencies((Actuator) actuator, ret, primary);
            primary = false;
        }
        return ret;
    }

    private IObservedConcept buildDependencies(Actuator actuator, Graph<IObservedConcept, DefaultEdge> graph,
            boolean primary) {

        ObservedConcept observable = new ObservedConcept(actuator.getObservable(), actuator.getMode());
        observable.getData().put(ACTUATOR, actuator);

        graph.addVertex(observable);
        for (IActuator child : actuator.getActuators()) {
            graph.addEdge(buildDependencies((Actuator) child, graph, primary), observable);
        }
        return observable;
    }

    /**
     * If the parameters in a specified annotation have been changed by the user, return a new
     * annotation with the new parameters.
     * 
     * Called by an observable's getAnnotations() when a runtime context is passed for
     * contextualization of parameter.
     * 
     * @param annotation
     * @return a new annotation or the same if parameters haven't changed.
     */
    public IAnnotation parameterizeAnnotation(IAnnotation annotation) {
        boolean first = true;
        Annotation ret = (Annotation) annotation;
        for (AnnotationParameterValue av : getAnnotationValuesFor(((Annotation) annotation).getId())) {
            if (first) {
                ret = ret.copy();
            }
            ret.put(av.parameterName, TypeUtils.convert(av.value, Utils.getClassForType(av.type)));
        }
        return ret;
    }

    private Collection<AnnotationParameterValue> getAnnotationValuesFor(String annotationId) {
        List<AnnotationParameterValue> ret = new ArrayList<>();
        for (AnnotationParameterValue a : annotationParameters) {
            if (a.annotationId.equals(annotationId)) {
                ret.add(a);
            }
        }
        return ret;
    }

    private AnnotationParameterValue getAnnotationValueFor(String annotationId, String parameterId) {
        for (AnnotationParameterValue a : annotationParameters) {
            if (a.annotationId.equals(annotationId) && a.parameterName.equals(parameterId)) {
                return a;
            }
        }
        return null;
    }

    @Override
    protected String encode(int offset) {
        return encode(offset, true);
    }

    String encode(int offset, boolean encodePreamble) {

        String ret = "";

        if (offset == 0 && encodePreamble) {
            ret += "@klab " + Version.CURRENT + "\n";
            ret += "@dataflow " + getName() + "\n";
            ret += "@author 'k.LAB resolver " + creationTime + "'" + "\n";
            // if (getContext() != null) {
            // ret += "@context " + getContext().getUrn() + "\n";
            // }
            if (coverage != null && coverage.getExtentCount() > 0) {
                List<IServiceCall> scaleSpecs = ((Scale) coverage).getKimSpecification();
                if (!scaleSpecs.isEmpty()) {
                    ret += "@coverage load_me_from_some_sidecar_file()";
                    // TODO this can get huge and is transmitted over websockets, so can't put it
                    // here as is. Needs
                    // supplemental material and a ref instead.
                    // for (int i = 0; i < scaleSpecs.size(); i++) {
                    // if (scaleSpecs.get(i) != null) {
                    // ret += " " + scaleSpecs.get(i).getSourceCode()
                    // + ((i < scaleSpecs.size() - 1) ? (",\n" + " ") : "");
                    // }
                    // }
                    ret += "\n";
                }
            }
            ret += "\n";
        }

        for (IActuator actuator : actuators) {
            ret += ((Actuator) actuator).encode(offset) + "\n";
        }

        return ret;
    }

    /**
     * Return the source code of the dataflow.
     * 
     * @return the source code as a string.
     */
    @Override
    public String getKdlCode() {
        return encode(0);
    }

    // public DirectObservation getContext() {
    // return context;
    // }
    //
    // public void setContext(DirectObservation context) {
    // this.context = context;
    // }
    //
    // public void setResolutionScope(ResolutionScope scope) {
    // this.resolutionScope = scope;
    // }

    public static Dataflow empty(Dataflow parent) {
        return new Dataflow(parent);
    }

    public static Dataflow empty(ResolutionScope scope, Dataflow parent) {
        Dataflow ret = new Dataflow(parent);
        // ret.resolutionScope = scope;
        // ret.session = scope.getSession();
        return ret;
    }

    /**
     * Make a trivial dataflow with a single actuator that will create the passed observable target.
     * 
     * @param observable
     * @param scope
     * @return
     */
    public static Dataflow empty(IObservable observable, String name, ResolutionScope scope,
            Dataflow parent) {

        Dataflow ret = new Dataflow(parent);
        // ret.resolutionScope = scope;
        // ret.session = scope.getSession();

        Actuator actuator = Actuator.create(ret, scope.getMode());
        actuator.setObservable((Observable) observable);
        actuator.setType(observable.getArtifactType());
        actuator.setNamespace(((ResolutionScope) scope).getResolutionNamespace());
        actuator.setName(name);
        ret.getActuators().add(actuator);
        ret.setNamespace(actuator.getNamespace());

        return ret;
    }

    public boolean isTrivial() {
        return actuators.size() < 2 && (actuators.size() == 0 || (actuators.size() == 1
                && ((Actuator) actuators.get(0)).getObservable().is(IKimConcept.Type.COUNTABLE)
                && ((Actuator) actuators.get(0)).isTrivial()));
    }

    /**
     * True if the dataflow is handling an API observation request. False if the request is to
     * resolve an object instantiated by another dataflow.
     * 
     * @return
     */
    public boolean isPrimary() {
        return primary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // public Dataflow withMetadata(IMetadata metadata) {
    // this.metadata = metadata;
    // return this;
    // }
    //
    // public Dataflow connecting(IDirectObservation source, IDirectObservation target) {
    // this.relationshipSource = source;
    // this.relationshipTarget = target;
    // return this;
    // }

    // /**
    // * Metadata may be added to the dataflow before computation to resolve states
    // * and/or add to the target observation as specified by the model.
    // *
    // * @return
    // */
    // public IMetadata getMetadata() {
    // return metadata;
    // }
    //
    // public IDirectObservation getRelationshipSource() {
    // return relationshipSource;
    // }
    //
    // public IDirectObservation getRelationshipTarget() {
    // return relationshipTarget;
    // }
    //
    // @Deprecated
    // public Dataflow withConfigurationTargets(Collection<IObservation> targets) {
    // this.configurationTargets = targets;
    // return this;
    // }

    // @Deprecated
    // public Collection<IObservation> getConfigurationTargets() {
    // return this.configurationTargets;
    // }
    //
    // @Override
    // @Deprecated
    // public IScale getResolutionScale() {
    // if (this.resolutionScale == null && resolutionScope != null) {
    // this.resolutionScale = resolutionScope.getScale();
    // if (hasOccurrents && this.resolutionScale.getTime() != null) {
    // ITime time = this.resolutionScale.getTime();
    // if (time.isGeneric() || time.size() == 1) {
    //
    // if (time.getStart() == null || time.getEnd() == null) {
    // throw new KlabContextualizationException(
    // "cannot contextualize occurrents (processes and events) without a specified temporal
    // extent");
    // }
    //
    // // turn time into a 1-step grid (so size = 2)
    // this.resolutionScale = Scale.substituteExtent(this.resolutionScale,
    // ((Time) time).upgradeForOccurrents());
    // }
    //
    // // set the dataflow to autostart transitions if we only have one
    // if (this.resolutionScale.getTime().size() >= 2) {
    // autoStartTransitions = true;
    // }
    // }
    // }
    // return this.resolutionScale;
    // }

    // public void notifyOccurrents() {
    // this.hasOccurrents = true;
    // }
    //
    // public boolean isAutoStartTransitions() {
    // return this.autoStartTransitions;
    // }

    // /**
    // * TODO/FIXME if withScope is called, it must be called before this one.
    // *
    // * @param scale
    // * @return
    // */
    // @Deprecated
    // public Dataflow withScopeScale(IScale scale) {
    // if (this.resolutionScope != null) {
    // this.resolutionScope = this.resolutionScope.rescale(scale);
    // }
    // return this;
    // }

    // /**
    // * TODO this should create a new dataflow if we want concurrent execution of
    // * dataflows with different scopes. Also this MUST be called before
    // * withScopeScale if that is used.
    // *
    // * @param scope
    // * @return
    // */
    // @Deprecated
    // public Dataflow withScope(ResolutionScope scope) {
    // this.resolutionScope = scope;
    // return this;
    // }
    //
    // @Deprecated
    // public ResolutionScope getResolutionScope() {
    // return this.resolutionScope;
    // }
    //
    // /*
    // * FIXME this shouldn't be necessary - use the hierarchy
    // */
    // @Deprecated
    // public void setSecondary(boolean b) {
    // this.secondary = b;
    // }

    // /*
    // * FIXME this shouldn't be necessary - use the hierarchy. Also conflicts with
    // * isPrimary() in meaning.
    // */
    // @Deprecated
    // public boolean isSecondary() {
    // return this.secondary;
    // }

    // public void reattributeActuators() {
    // for (IActuator actuator : actuators) {
    // reattributeActuator(actuator);
    // }
    // }
    //
    // private void reattributeActuator(IActuator actuator) {
    // ((Actuator) actuator).setDataflow(this);
    // for (IActuator a : actuator.getActuators()) {
    // reattributeActuator(a);
    // }
    // }

    // @Deprecated
    // public Dataflow withContext(IDirectObservation contextSubject) {
    // this.context = (DirectObservation) contextSubject;
    // return this;
    // }
    //
    // @Deprecated
    // public Dataflow withinGroup(ObservationGroup group) {
    // this.observationGroup = group;
    // return this;
    // }
    //
    // @Deprecated
    // public ObservationGroup getObservationGroup() {
    // return this.observationGroup;
    // }
    //
    // @Deprecated
    // public String getTargetName() {
    // return targetName;
    // }
    //
    // @Deprecated
    // public void setTargetName(String targetName) {
    // this.targetName = targetName;
    // }

    // @Deprecated
    // public Dataflow withTargetName(String targetName) {
    // this.targetName = targetName;
    // return this;
    // }
    //
    // public Mode getNotificationMode() {
    // return this.notificationMode;
    // }
    //
    // public void setNotificationMode(INotification.Mode mode) {
    // this.notificationMode = mode;
    // }
    //
    // public Dataflow withNotificationMode(INotification.Mode mode) {
    // this.notificationMode = mode;
    // return this;
    // }
    //
    // public Graph<ObservedConcept, DefaultEdge> getDependencies() {
    // return this.dependencies;
    // }
    //
    // public Set<ObservedConcept> getImplicitlyChangingObservables() {
    // return resolutionScope.getImplicitlyChangingObservables();
    // }
    //
    // @Deprecated
    // public IRuntimeScope getRuntimeScope() {
    // return this.runtimeScope;
    // }

    // @Deprecated
    // public void setRuntimeScope(IRuntimeScope runtimeScope) {
    // this.runtimeScope = runtimeScope;
    // }

    /**
     * Fill in the localNames map in each actuator for independent relocation of names before
     * compute().
     */
    public void computeLocalNames() {

        for (IActuator actuator : this.actuators) {
            computeLocalNames((Actuator) actuator, new HashMap<>());
        }
    }

    private void computeLocalNames(Actuator actuator, HashMap<String, String> hashMap) {

        for (IActuator a : actuator.getSortedChildren(actuator, false)) {
            /*
             * TODO CHECK: unsure if the names should propagate downstream, but I think they're just
             * local to each actuator and re-imported at all levels if need be. If this changes, we
             * should pass hashMap instead.
             */
            computeLocalNames((Actuator) a, /* hashMap */ new HashMap<>());
            ((Actuator) a).getLocalNames().putAll(hashMap);
            if (a.getAlias() != null && !a.getAlias().equals(a.getName())) {
                hashMap.put(a.getName(), a.getAlias());
            }
        }
        actuator.getLocalNames().putAll(hashMap);
    }

    // /**
    // * Record that the passed observation was resolved using the passed dataflow.
    // * Scheduling will need to use this information.
    // *
    // * @param observation
    // * @param dataflow
    // */
    // @Deprecated
    // public void registerResolution(IDirectObservation observation, Dataflow dataflow) {
    // List<IDirectObservation> obs = inherentResolutions.get(dataflow);
    // if (obs == null) {
    // obs = new ArrayList<>();
    // inherentResolutions.put(dataflow, obs);
    // }
    // if (!obs.contains(observation)) {
    // obs.add(observation);
    // }
    // }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((_actuatorId == null) ? 0 : _actuatorId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dataflow other = (Dataflow) obj;
        if (_actuatorId == null) {
            if (other._actuatorId != null)
                return false;
        } else if (!_actuatorId.equals(other._actuatorId))
            return false;
        return true;
    }

    // /**
    // * Use to quickly compare different dataflows for equality of executable
    // * methods. Useful to group resolution dataflows for multiple objects into the
    // * minimum number of distinct ones. Does not compare preambles.
    // *
    // * TODO this may skip differences in lookup tables or other parameters that are
    // * currently not printed in full literal form in the code.
    // *
    // * @return an hex signature that will be equal if the actuator part is equal.
    // */
    // public String getSignature() {
    // return DigestUtils.md5Hex(encode(0, false));
    // }

    @Override
    public List<IDataflow<IArtifact>> getChildren() {
        return childDataflows;
    }

    public Dataflow setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    @Override
    public IDataflow<IArtifact> getRootDataflow() {
        Actuator ret = this;
        while(ret.parentDataflow != null) {
            ret = ret.parentDataflow;
        }
        return (Dataflow) ret;
    }

    @Override
    public String toString() {
        return getKdlCode();
    }

    @Override
    public void export(String baseName, File directory) {
        // TODO Auto-generated method stub

    }

    public boolean occurs() {
        // TODO Auto-generated method stub
        return false;
    }

}
