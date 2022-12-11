package org.integratedmodelling.klab.engine.runtime;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.observations.IPattern;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IEventBus;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.IVariable;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.Structure;
import org.integratedmodelling.klab.components.runtime.observations.Event;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.runtime.observations.Process;
import org.integratedmodelling.klab.components.runtime.observations.Relationship;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Actuator.Status;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.engine.runtime.code.ExpressionScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.resolution.DependencyGraph;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * Trivial context that will only build simple hierarchies and observations. Meant for quick
 * visualization of non-semantic structures so far. May need to be completed with all its parts to
 * load previously contextualized observations with relationships and other complex interactions.
 * Borrows observations from the default runtime.
 * 
 * @author ferdinando.villa
 *
 */
public class SimpleRuntimeScope extends Parameters<String> implements IRuntimeScope {

    INamespace namespace = null;
    IObservable observable = null;
    IScale scale = null;
    IObservation target = null;
    IMonitor monitor;
    SimpleRuntimeScope parent;
    String targetName;
    ActivityBuilder statistics;

    // these are shared among all children, created in root only and passed around
    Map<String, IArtifact> artifacts;
    Map<String, IObservation> observations;
    Structure structure;
    Graph<IArtifact, Relationship> network;
    ISubject rootSubject;
    Map<String, IObservable> semantics;
    Map<String, IVariable> symbolTable = new HashMap<>();

    public SimpleRuntimeScope(Actuator actuator) {
        this.observable = actuator.getObservable();
        this.scale = /* actuator.getDataflow(). */getMergedScale(actuator);
        this.structure = new Structure();
        this.network = new DefaultDirectedGraph<>(Relationship.class);
        this.artifacts = new HashMap<>();
        this.observations = new HashMap<>();
        this.semantics = new HashMap<>();
        this.namespace = Namespaces.INSTANCE.getNamespace(observable.getType().getNamespace());
    }

    /**
     * Root context. Don't use this to build a child.
     * 
     * @param observable
     * @param scale
     * @param monitor
     */
    public SimpleRuntimeScope(IObservable observable, IScale scale, IMonitor monitor) {
        this.observable = observable;
        this.scale = scale;
        this.structure = new Structure();
        this.network = new DefaultDirectedGraph<>(Relationship.class);
        this.artifacts = new HashMap<>();
        this.observations = new HashMap<>();
        this.semantics = new HashMap<>();
        this.monitor = monitor;
        if (observable != null) {
            this.namespace = Namespaces.INSTANCE.getNamespace(observable.getType().getNamespace());
            this.target = this.rootSubject = new Subject(observable.getName(), (Observable) observable,
                    (Scale) scale, this);
            this.structure.add(this.target);
            this.artifacts.put(this.getTargetName(), this.target);
            this.observations.put(this.target.getId(), this.target);
        }
    }

    /**
     * Just meant to carry around a monitor and identity for actors. Useless and likely to break
     * when used in contextualization!
     * 
     * @param session
     */
    public SimpleRuntimeScope(ISession session) {
        this.structure = new Structure();
        this.network = new DefaultDirectedGraph<>(Relationship.class);
        this.artifacts = new HashMap<>();
        this.observations = new HashMap<>();
        this.semantics = new HashMap<>();
        // get a new monitor to avoid carrying over any previous session errors (e.g.
        // coming from
        // websockets)
        this.monitor = ((Monitor) session.getMonitor()).get(session);
    }

    public SimpleRuntimeScope(IMonitor monitor) {
        this.structure = new Structure();
        this.network = new DefaultDirectedGraph<>(Relationship.class);
        this.artifacts = new HashMap<>();
        this.observations = new HashMap<>();
        this.semantics = new HashMap<>();
        this.monitor = monitor;
    }

    public SimpleRuntimeScope(SimpleRuntimeScope parent) {
        this.scale = parent.scale;
        this.structure = parent.structure;
        this.network = parent.network;
        this.monitor = parent.monitor;
        this.namespace = parent.namespace;
        this.artifacts = parent.artifacts;
        this.observations = parent.observations;
        this.rootSubject = parent.rootSubject;
        this.semantics = parent.semantics;
    }

    @Override
    public INamespace getNamespace() {
        return namespace;
    }

    @Override
    public IEventBus getEventBus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IScheduler getScheduler() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IRelationship> getOutgoingRelationships(IDirectObservation observation) {
        return new ArrayList<>();
    }

    @Override
    public Collection<IRelationship> getIncomingRelationships(IDirectObservation observation) {
        return new ArrayList<>();
    }

    @Override
    public IArtifact getTargetArtifact() {
        return target;
    }

    @Override
    public IArtifact getArtifact(String localName) {
        return artifacts.get(localName);
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
    public <T extends IArtifact> Collection<Pair<String, T>> getArtifacts(Class<T> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IMonitor getMonitor() {
        return monitor;
    }

    @Override
    public Type getArtifactType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IScale getScale() {
        return scale;
    }

    @Override
    public IObservable getSemantics(String identifier) {
        return semantics.get(identifier);
    }

    @Override
    public IObjectArtifact newObservation(IObservable observable, String name, IScale scale,
            IMetadata metadata)
            throws KlabException {

        IDirectObservation ret = null;
        if (observable.is(Type.SUBJECT)) {
            ret = new Subject(name, (Observable) observable, (Scale) scale, this);
        } else if (observable.is(Type.EVENT)) {
            ret = new Event(name, (Observable) observable, (Scale) scale, this);
        } else if (observable.is(Type.PROCESS)) {
            ret = new Process(name, (Observable) observable, (Scale) scale, this);
        }

        if (ret != null) {

            observations.put(ret.getId(), ret);
            structure.add(ret);
            if (parent != null && parent.target != null) {
                structure.link(ret, parent.target);
            }
            if (metadata != null) {
                ret.getMetadata().putAll(metadata);
            }
        }

        return ret;
    }

    @Override
    public IObjectArtifact newRelationship(IObservable observable, String name, IScale scale,
            IObjectArtifact source,
            IObjectArtifact target, IMetadata metadata) {

        Relationship ret = new Relationship(name, (Observable) observable, (Scale) scale, this);

        if (ret != null) {

            observations.put(ret.getId(), ret);
            structure.add(ret);
            if (parent != null && parent.target != null) {
                structure.link(ret, parent.target);
            }

            network.addVertex(source);
            network.addVertex(target);
            network.addEdge(source, target, ret);

            if (metadata != null) {
                ret.getMetadata().putAll(metadata);
            }
        }

        return ret;
    }

    @Override
    public IObservable getTargetSemantics() {
        if (semantics == null)
            return null;
        String name = getTargetName();
        return name == null ? null : semantics.get(name);
    }

    @Override
    public String getTargetName() {
        if (targetName != null) {
            return targetName;
        }
        if (target == null) {
            return null;
        }
        return target instanceof IDirectObservation
                ? ((IDirectObservation) target).getName()
                : target.getObservable().getName();
    }

    @Override
    public ISubject getSourceSubject(IRelationship relationship) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ISubject getTargetSubject(IRelationship relationship) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IDirectObservation getContextObservation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IDirectObservation getParentOf(IObservation observation) {
        for (IArtifact source : this.structure.getLogicalParent(observation)) {
            if (source instanceof IDirectObservation) {
                return (IDirectObservation) source;
            }
        }
        return null;
    }

    @Override
    public Collection<IObservation> getChildrenOf(IObservation observation) {
        return getChildren(observation, IObservation.class);
    }

    @Override
    public IRuntimeScope createChild(IScale scale, IActuator target, IResolutionScope scope, IMonitor monitor,
            IPattern pattern) {
        throw new IllegalStateException(
                "Context is meant for testing of individual resources and cannot support child observations");
    }

    @Override
    public void setData(String name, IArtifact data) {
        // TODO Auto-generated method stub

    }

    @Override
    public void set(String name, Object value) {
        // TODO Auto-generated method stub

    }

    @Override
    public IRuntimeScope copy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTarget(IArtifact target) {
        this.target = (IObservation) target;
    }

    @Override
    public void setScale(IScale geometry) {
        this.scale = geometry;
    }

    @Override
    public Provenance getProvenance() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IArtifact.Structure getStructure() {
        return structure;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls) {
        List<T> ret = new ArrayList<>();
        for (IArtifact source : this.structure.getLogicalChildren(artifact)) {
            if (cls.isAssignableFrom(source.getClass())) {
                ret.add((T) source);
            }
        }
        return ret;
    }

    /**
     * Return a child context that can be used to build the observation of the passed resource in
     * our scale. If the observable is null, create a non-semantic observable.
     * 
     * @param resource
     * @return
     */
    public SimpleRuntimeScope getChild(IObservable observable, IResource resource) {

        if (observable == null) {
            IConcept concept = OWL.INSTANCE.getNonsemanticPeer(
                    CamelCase.toUpperCamelCase(resource.getLocalName().replaceAll("\\-", "_"), '_'),
                    resource.getType());
            observable = Observable.promote(concept);
        }

        SimpleRuntimeScope ret = new SimpleRuntimeScope(this);
        if (!resource.getType().isCountable()) {
            IStorage<?> data = Klab.INSTANCE.getStorageProvider().createStorage(resource.getType(),
                    getScale());
            ret.target = new State((Observable) observable, (Scale) scale, this, (IDataStorage<?>) data);
        } else {
            ret.target = new ObservationGroup((Observable) observable, (Scale) scale, this,
                    resource.getType());
        }

        this.observable = observable;
        semantics.put(observable.getName(), observable);

        structure.add(ret.target);
        structure.link(ret.target, this.target);
        artifacts.put(observable.getName(), ret.target);
        observations.put(ret.target.getId(), ret.target);
        ret.targetName = observable.getName();
        ret.parent = this;

        return ret;
    }

    @Override
    public ISubject getRootSubject() {
        return rootSubject;
    }

    @Override
    public IObservation getObservation(String observationId) {
        return observations.get(observationId);
    }

    /**
     * This must be called explicitly before the builder is called upon.
     * 
     * @param artifactId
     * @param observable
     */
    public void setSemantics(String artifactId, IObservable observable) {
        this.semantics.put(artifactId, observable);
    }

    @Override
    public IRuntimeScope createChild(IObservable indirectTarget) {
        SimpleRuntimeScope ret = new SimpleRuntimeScope(this);
        ret.observable = indirectTarget;
        ret.targetName = indirectTarget.getName();
        ret.target = (IObservation) getArtifact(ret.targetName);
        ret.setSemantics(ret.targetName, indirectTarget);
        return ret;
    }

    @Override
    public Pair<String, IArtifact> findArtifact(IObservable observable) {
        for (String key : artifacts.keySet()) {
            IArtifact artifact = artifacts.get(key);
            if (artifact != null && artifact instanceof IObservation
                    && ((Observable) ((IObservation) artifact).getObservable())
                            .resolvesStrictly((Observable) observable)) {
                return new Pair<>(key, artifact);
            }
        }
        return null;
    }

    @Override
    public IReport getReport() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setModel(Model model) {
        // TODO Auto-generated method stub

    }

    @Override
    public IModel getModel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeArtifact(IArtifact object) {
        // TODO Auto-generated method stub

    }

    @Override
    public IDirectObservation getContextSubject() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IArtifact> getArtifact(IConcept observable) {
        List<IArtifact> ret = new ArrayList<>();
        for (IArtifact artifact : artifacts.values()) {
            if (artifact instanceof IObservation
                    && ((IObservation) artifact).getObservable().getType().is(observable)) {
                ret.add(artifact);
            }
        }
        return ret;
    }

    @Override
    public IConfiguration newConfiguration(IConcept configurationType, IPattern pattern, IMetadata metadata) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IDataflow<?> getDataflow() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IArtifact> getChildArtifactsOf(IArtifact directObservation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getNotifiedObservations() {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IArtifact> T getArtifact(IConcept concept, Class<T> cls) {

        Set<IArtifact> ret = new HashSet<>();
        for (IArtifact artifact : artifacts.values()) {
            if (artifact instanceof IObservation
                    && ((IObservation) artifact).getObservable().getType().is(concept)) {
                ret.add(artifact);
            }
        }

        Set<IArtifact> chosen = new HashSet<>();
        if (ret.size() > 1) {
            for (IArtifact artifact : ret) {
                if (cls.isAssignableFrom(artifact.getClass())) {
                    chosen.add(artifact);
                }
            }
        }

        return (T) (chosen.isEmpty() ? null : chosen.iterator().next());
    }

    @Override
    public ObservationGroup getObservationGroup(IObservable observable, IScale scale) {
        // TODO implement the same mechanism as RuntimeContext
        return new ObservationGroup((Observable) observable, (Scale) scale, this,
                observable.getArtifactType());
    }

    @Override
    public void newPredicate(IDirectObservation target, IConcept c) {
        // TODO Auto-generated method stub

    }

    @Override
    public IObservation getObservationGroupView(Observable observable, IObservation ret) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void scheduleActions(Actuator active) {
        // TODO Auto-generated method stub

    }

    @Override
    public IRuntimeScope locate(ILocator transitionScale, IMonitor monitor) {
        // TODO Auto-generated method stub
        return this;
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
        structure.link(ret, this.target);
        artifacts.put(observable.getName(), ret);
        observations.put(ret.getId(), ret);

        return ret;
    }

    @Override
    public <T extends IArtifact> Map<String, T> getLocalCatalog(Class<T> cls) {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    // public Collection<IArtifact> getAdditionalOutputs() {
    // // TODO Auto-generated method stub
    // return null;
    // }

    @Override
    public IState addState(IDirectObservation target, IObservable observable, Object data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T extends IArtifact> T resolve(IObservable observable, IDirectObservation context,
            ITaskTree<?> task, Mode mode,
            IActuator parentDataflow) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void scheduleActions(Observation observation, IBehavior behavior) {
        // TODO Auto-generated method stub

    }

    @Override
    public Map<IConcept, Pair<String, IKimExpression>> getBehaviorBindings() {
        return null;
    }

    @Override
    public Set<String> getWatchedObservationIds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateNotifications(IObservation observation) {
        // TODO Auto-generated method stub

    }

    @Override
    public IObservation getParentArtifactOf(IObservation observation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void swapArtifact(IArtifact original, IArtifact replacement) {
        // TODO Auto-generated method stub
        structure.swap(original, replacement);
    }

    @Override
    public Collection<IObservation> getObservations(IConcept observable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void notifyListeners(IObservation object) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isOccurrent() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<IObservable> getDependents(IObservable observable, Mode resolutionMode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IObservable> getPrecursors(IObservable observable, Mode resolutionMode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<IObservedConcept, IObservation> getCatalog() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addView(IKnowledgeView view) {
        // TODO Auto-generated method stub

    }

    @Override
    public IRuntimeScope targetForChange() {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public IRuntimeScope targetToObservation(IObservation target) {
        // TODO Auto-generated method stub
        return this;
    }

    // @Override
    // public IParameters<String> localize(ILocator locator) {
    // // TODO Auto-generated method stub
    // return this;
    // }

    @Override
    public String getArtifactName(IArtifact previous) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setConcreteIdentities(IConcept abstractIdentity, List<IConcept> concreteIdentities) {
        // TODO Auto-generated method stub

    }

    @Override
    public Collection<IConcept> getConcreteIdentities(IConcept predicate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IConcept localizePredicate(IConcept predicate) {
        // TODO Auto-generated method stub
        return predicate;
    }

    @Override
    public Collection<IKnowledgeView> getViews() {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IArtifact> Collection<T> getArtifacts(IConcept concept, Class<T> cls) {

        Set<T> ret = new HashSet<>();
        for (IArtifact artifact : artifacts.values()) {
            if (artifact instanceof IObservation
                    && ((IObservation) artifact).getObservable().getType().is(concept)) {
                ret.add((T) artifact);
            }
        }

        Set<T> chosen = new HashSet<>();
        if (ret.size() > 1) {
            for (IArtifact artifact : ret) {
                if (cls.isAssignableFrom(artifact.getClass())) {
                    chosen.add((T) artifact);
                }
            }
        }

        return chosen;
    }

    @Override
    public IRuntimeScope withCoverage(IScale scale) {
        this.scale = ((Scale) this.scale).contextualizeTo(scale);
        return this;
    }

    @Override
    public <T extends IArtifact> Collection<T> getAffectedArtifacts(IConcept processType, Class<T> cls) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IState getState(IConcept concept, IValueMediator unit) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IState getState(String name, IValueMediator unit) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ISession getSession() {
        return monitor.getIdentity().getParentIdentity(ISession.class);
    }

    @Override
    public Collection<String> getStateIdentifiers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Actuator getActuator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IResolutionScope getResolutionScope() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DependencyGraph getDependencyGraph() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMergedScale(IActuator actuator, IScale merge) {
        // TODO Auto-generated method stub

    }

    @Override
    public IScale getMergedScale(IActuator actuator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IScale getResolutionScale() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IObservation> getActuatorProducts(IActuator actuator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Status getStatus(IActuator actuator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRuntimeScope createContext(IScale scale, IActuator target, IDataflow<?> dataflow,
            IResolutionScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRuntimeScope getContextScope(Actuator actuator, IResolutionScope scope, IScale scale,
            IDataflow<?> dataflow,
            IMonitor monitor) {
        // TODO Auto-generated method stub
        return null;
    }
    //
    // @Override
    // public void setRuntimeScale(IActuator actuator, IScale scale) {
    // // TODO Auto-generated method stub
    //
    // }
    //
    // @Override
    // public IScale getRuntimeScale(IActuator actuator) {
    // // TODO Auto-generated method stub
    // return null;
    // }

    @Override
    public String getElkGraph() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getKdl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void exportDataflow(String baseName, File directory) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<IObservedConcept> getImplicitlyChangingObservables() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IRuntimeScope getChild(IRuntimeIdentity identity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void notifyInspector(Object... triggerArguments) {
        // TODO Auto-generated method stub

    }

    @Override
    public Map<String, Object> getContextData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getGlobalData() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ActuatorData getActuatorData(IActuator observable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IScale getScale(IActuator actuator) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ExpressionScope getExpressionContext() {
        return ExpressionScope.empty(Klab.INSTANCE.getRootMonitor());
    }

    @Override
    public ExpressionScope getExpressionContext(IObservable targetObservable) {
        return ExpressionScope.empty(Klab.INSTANCE.getRootMonitor());
    }

    @Override
    public IObservation getObservation(IObservable observable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IObserver<?> getObserver() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IObservation incarnatePattern(IConcept concept, IPattern pattern) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPattern getPattern() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ActivityBuilder getStatistics() {
        return statistics;
    }

}
