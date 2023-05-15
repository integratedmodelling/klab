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
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Interaction;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.model.IAnnotation;
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
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.AbstractTask;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.DependencyGraph;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.ResolvedArtifact;
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
 * The semantically aware implementation of {@link IDataflow}, as built by the k.LAB runtime as a
 * result of semantic resolution. Its {@link #run(IScale, IMonitor)} produces {@link IObservation
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
 * specific qualities declared "within" the object. The "clean" actuator hierarchy is built from
 * this by the scope, and it is the proper one for visualization, serialization and reuse.
 * 
 * @author Ferd
 *
 */
public class Dataflow extends Actuator implements IDataflow<IArtifact> {

    public static final String ACTUATOR = "ACTUATOR";

    private String description;
    /*
     * primary dataflows are created by first-level observation tasks. They run temporal transitions
     * with a schedule that also includes their child dataflows.
     */
    private boolean primary;
    private boolean isOccurrent;

    // execution parameters for user modification if running interactively
    private List<InteractiveParameter> fields = new ArrayList<>();
    private List<Pair<IContextualizable, List<String>>> resources = new ArrayList<>();
    private List<Pair<IAnnotation, List<String>>> annotations = new ArrayList<>();

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

    private Dataflow(Dataflow parent) {
        this.parentDataflow = parent;
        this.setType(Type.VOID);
    }

    public Dataflow(Actuator parent) {
        this.parentDataflow = parent;
        if (this.parentDataflow != null) {
            parent.actuators.add(this);
        }
        this.setType(Type.VOID);
    }

    @Override
    public IArtifact run(IScale scale, IContextualizationScope scope) throws KlabException {
        return run(scale, null, (IRuntimeScope) scope);
    }

    /**
     * Pass an actuator to use to register ourselves into. If the parent is not null, notify the
     * task through the monitor (top-level observation do it manually so that any resolution issue
     * also get reported as pertaining to the same observation task).
     * 
     * @param scale
     * @param parentComputation
     * @param scope
     * @return
     * @throws KlabException
     */
    public IArtifact run(IScale scale, Actuator parentComputation, IRuntimeScope scope) throws KlabException {

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

        if (!trivial && parentComputation != null && scope.getMonitor().getIdentity() instanceof AbstractTask) {
            ((AbstractTask<?>) scope.getMonitor().getIdentity()).notifyStart();
        }

        ISession session = scope.getSession();

        /*
         * Set the partial scale of each actuator in the scope for all actuators that represent
         * partitions of the overall scale to reflect the portion of the actual scale they must
         * cover.
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
                            for (InteractiveParameter parameter : Interaction.INSTANCE.getInteractiveParameters(annotation, o)) {
                                if (parameterIds == null) {
                                    parameterIds = new ArrayList<>();
                                }
                                fields.add(parameter);
                                parameterIds.add(parameter.getId());
                                annotationParameters.add(new AnnotationParameterValue(((Annotation) annotation).getId(),
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
                        for (InteractiveParameter parameter : Interaction.INSTANCE.getInteractiveParameters(computable,
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
                Collection<Triple<String, String, String>> values = Interaction.INSTANCE.submitParameters(this.resources,
                        this.fields, session);

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
            ret = Klab.INSTANCE.getRuntimeProvider().compute(this, scale.initialization(), scope).get();
        } catch (Throwable e) {
            if (scope.getMonitor().isInterrupted()) {
                return null;
            }
            if (!trivial && parentComputation != null && scope.getMonitor().getIdentity() instanceof AbstractTask) {
                ((AbstractTask<?>) scope.getMonitor().getIdentity()).notifyAbort(e);
                return null;
            }
        }

        if (!trivial && parentComputation != null && scope.getMonitor().getIdentity() instanceof AbstractTask) {
            ((AbstractTask<?>) scope.getMonitor().getIdentity()).notifyEnd();
        }

        return ret;
    }

    private void definePartitions(IScale scale, IRuntimeScope scope) {
        _definePartialScales(this, (Scale) scale.collapse(), scope);
    }

    private Scale _definePartialScales(Actuator actuator, Scale current, IRuntimeScope scope) {

        if (actuator.getModel() != null) {

            Scale mcoverage = actuator.getModel().getCoverage(scope.getMonitor()).collapse();
            if (!mcoverage.isEmpty() || actuator.isPartition()) {
                Scale coverage = mcoverage;
                if (actuator.isPartition()) {
                    coverage = current.merge(mcoverage, LogicalConnector.INTERSECTION);
                    scope.setMergedScale(actuator, coverage.mergeContext(current));
                }

                /*
                 * merge in the current scale. The coverage of the current actuator defines the
                 * overall extents if any are set.
                 */

                if ("false".equals(Configuration.INSTANCE.getProperty(Configuration.KLAB_FILL_COVERED_NODATA, "true"))
                        && actuator.isPartition()) {
                    /*
                     * remove the part we handled so that the next will not cover it. This must be
                     * explicitly enabled as it requires perfect coverage of masking polygons, or
                     * any nodata around the edges will show.
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

    /**
     * Build and return the dependency graph. Save externally if appropriate - caching does create
     * issues in contextualization and scheduling.
     * 
     * @return
     */
    public DependencyGraph getDependencyGraph() {
        return buildDependencies();
    }

    private DependencyGraph buildDependencies() {
        DependencyGraph ret = new DependencyGraph();
        boolean primary = true;
        // use the logical structure to only get true actuators and recurse
        // sub-dataflows
        for (IActuator actuator : getDataflowStructure().getSecond().vertexSet()) {
            buildDependencies((Actuator) actuator, ret, primary);
            primary = false;
        }
        return ret;
    }

    private IObservedConcept buildDependencies(Actuator actuator, DependencyGraph graph, boolean primary) {

        ObservedConcept observable = new ObservedConcept(actuator.getObservable(), actuator.getMode());
        observable.getData().put(ACTUATOR, actuator);
        // TODO properly handle partials
        graph.addVertex(observable);
        for (IActuator child : actuator.getActuators()) {
            if (!((Actuator) child).isPartition()) {
                // TODO we should add the structure but avoid the double counting of the observable
                graph.addEdge(buildDependencies((Actuator) child, graph, primary), observable);
            }
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

    /**
     * Preamble is output only at level zero and if the parameter is set to true. Dataflows are
     * explicitly wrapped in "resolve" only when 1) at root level (resolving the context) or 2) when
     * in the scope of an instantiator, to resolve the individual objects. Otherwise only their
     * contents are output. In a dataflow, the first actuator is always a void (resolves the
     * context) which is also output inline.
     * 
     * @param offset
     * @param encodePreamble
     * @return
     */
    String encode(int offset, Actuator parentActuator) {

        String ret = "";

        if (offset == 0 && parentActuator == null) {
            ret += "@klab " + Version.CURRENT + "\n";
            ret += "@author 'k.LAB resolver " + creationTime + "'" + "\n";
            // TODO should encode coverage after the resolver.
            // if (coverage != null && coverage.getExtentCount() > 0) {
            // List<IServiceCall> scaleSpecs = ((Scale) coverage).getKimSpecification();
            // if (!scaleSpecs.isEmpty()) {
            // ret += "@coverage load_me_from_some_sidecar_file()";
            // ret += "\n";
            // }
            // }
            ret += "\n";
        }

        Pair<IActuator, List<IActuator>> structure = getResolutionStructure();

        if (structure == null) {
            for (IActuator actuator : actuators) {
                ret += ((Actuator) actuator).encode(offset, null) + "\n";
            }
            return ret;
        }

        return ret + ((Actuator) structure.getFirst()).encode(0,
                structure.getSecond().isEmpty() ? (List<IActuator>) null : structure.getSecond());
    }

    /*
     * the root dataflow is guaranteed to have only one root node, the others do not.
     */
    public Pair<List<IActuator>, Graph<IActuator, DefaultEdge>> getDataflowStructure() {

        Graph<IActuator, DefaultEdge> ret = new DefaultDirectedGraph<>(DefaultEdge.class);

        List<IActuator> rootNodes = new ArrayList<>();
        Pair<IActuator, List<IActuator>> structure = getResolutionStructure();

        if (structure == null) {
            for (IActuator actuator : actuators) {
                rootNodes.add(((Actuator) actuator).makeDataflowStructure(null, null, ret));
            }
            return new Pair<>(rootNodes, ret);
        }

        rootNodes.add(((Actuator) structure.getFirst()).makeDataflowStructure(null, structure.getSecond(), ret));

        return new Pair<>(rootNodes, ret);

    }

    /**
     * If the dataflow is meant to resolve an instantiated object, it will have a void actuator as
     * its first one. In this case, this will return it, otherwise it will return null.
     * 
     * @return the resolver actuator (possibly trivial), or null.
     */
    @Override
    public IActuator getResolver() {
        if (this.getChildren().size() > 0 && !(this.getChildren().get(0) instanceof Dataflow)
                && this.getChildren().get(0).getType() == Type.RESOLVE) {
            return this.getChildren().get(0);
        }
        return null;
    }

    String getDataflowSubjectName() {
        String ret = "*";
        for (IActuator actuator : actuators) {
            ret = actuator.getName().replace(' ', '_');
            break;
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
        return encode(0, (Actuator) null);
    }

    public static Dataflow empty(Dataflow parent) {
        return new Dataflow(parent);
    }

    /**
     * Make a trivial dataflow with a single actuator that will create the passed observable target.
     * 
     * @param observable
     * @param scope
     * @return
     */
    public static Dataflow empty(IObservable observable, String name, ResolutionScope scope, Dataflow parent) {

        Dataflow ret = new Dataflow(parent);

        Actuator actuator = Actuator.create(ret, scope.getMode());
        actuator.setObservable((Observable) observable);
        actuator.setType(observable.getArtifactType());
        actuator.setNamespace(((ResolutionScope) scope).getResolutionNamespace());
        actuator.setName(name);
        ret.getChildren().add(actuator);
        ret.setNamespace(actuator.getNamespace());

        return ret;
    }

    public boolean isTrivial() {
        return actuators.size() < 2 && (actuators.size() == 0
                || (actuators.size() == 1 && ((Actuator) actuators.get(0)).getObservable().is(IKimConcept.Type.COUNTABLE)
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
    public void export(String baseName, File directory) {
        // TODO Auto-generated method stub
    }

    public boolean occurs() {
        return this.isOccurrent;
    }

    public void notifyOccurrents() {
        this.isOccurrent = true;
    }

    @Override
    public String toString() {
        return dump();
    }

    /**
     * If the dataflow contain a resolution actuator (void) and others for successive resolutions
     * (processes etc), return the first and the list of the others so they can be nested properly
     * when outputting the k.DL. Otherwise return null.
     * 
     * TODO make this a triple and add the coverage statement
     * 
     * @return
     */
    public Pair<IActuator, List<IActuator>> getResolutionStructure() {
        IActuator resolver = getResolver();
        if (resolver != null) {
            List<IActuator> second = new ArrayList<>();
            for (int i = 1; i < this.actuators.size(); i++) {
                second.add(this.actuators.get(i));
            }
            return new Pair<>(resolver, second);
        }
        return null;
    }

    public Set<IObservedConcept> getComputedObservables() {
        Set<IObservedConcept> ret = new HashSet<>();
        for (IActuator actuator : this.actuators) {
            ((Actuator) actuator).collectComputed(ret);
        }
        return ret;
    }

}
