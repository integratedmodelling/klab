package org.integratedmodelling.klab.components.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.ICountableObservation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.components.time.extents.Scheduler;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.ContextualizationStrategy;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.ConfigurationDetector;
import org.integratedmodelling.klab.engine.runtime.EventBus;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import edu.uci.ics.jung.graph.DirectedSparseMultigraph;

/**
 * A runtime context is installed in the root subject to keep track of what
 * happens during contextualization.
 * 
 * TODO Agent graphs, schedules etc should be here.
 * 
 * @author ferdinando.villa
 *
 */
public class RuntimeContext extends Parameters<String> implements IRuntimeContext {

	INamespace namespace;
	Provenance provenance;
	EventBus eventBus;
	ConfigurationDetector configurationDetector;
	DirectedSparseMultigraph<ISubject, IRelationship> network;
	Graph<IArtifact, DefaultEdge> structure;
	Map<String, IArtifact> catalog;
	IMonitor monitor;
	RuntimeContext parent;
	IArtifact target;
	IScale scale;
	IKimConcept.Type artifactType;
	Map<String, IObservable> semantics;
	IObservable targetSemantics;
	String targetName;
	ISubject rootSubject;
	Map<String, IObservation> observations;
	Scheduler<?> scheduler;
	IReport report;
	ContextualizationStrategy contextualizationStrategy;
	// set only by the actuator, relevant only in instantiators with attributes
	IModel model;

	// root scope of the entire dataflow, unchanging, for downstream resolutions
	ResolutionScope resolutionScope;

	public RuntimeContext(Actuator actuator, IResolutionScope scope, IScale scale, IMonitor monitor) {

		this.catalog = new HashMap<>();
		this.report = new Report(this, monitor.getIdentity().getParentIdentity(ISession.class).getId());
		this.observations = new HashMap<>();
		this.network = new DirectedSparseMultigraph<>();
		this.structure = new DefaultDirectedGraph<>(DefaultEdge.class);
		this.provenance = new Provenance();
		this.monitor = monitor;
		this.namespace = actuator.getNamespace();
		this.scale = scale;
		this.targetName = actuator.isPartition() ? actuator.getPartitionedTarget() : actuator.getName();

		this.targetSemantics = actuator.getObservable();
		this.artifactType = Observables.INSTANCE.getObservableType(actuator.getObservable(), true);

		// store and set up for further resolutions
		this.resolutionScope = (ResolutionScope) scope;

		this.semantics = new HashMap<>();
		this.semantics.put(actuator.getName(), this.targetSemantics);

		for (IActuator a : actuator.getActuators()) {
			if (!((Actuator) a).isExported()) {
				String id = a.getAlias() == null ? a.getName() : a.getAlias();
				this.semantics.put(id, ((Actuator) a).getObservable());
			}
		}
		for (IActuator a : actuator.getOutputs()) {
			String id = a.getAlias() == null ? a.getName() : a.getAlias();
			this.semantics.put(id, ((Actuator) a).getObservable());
		}
	}

	RuntimeContext(RuntimeContext context) {
		this.putAll(context);
		this.namespace = context.namespace;
		this.provenance = context.provenance;
		this.eventBus = context.eventBus;
		this.configurationDetector = context.configurationDetector;
		this.network = context.network;
		this.contextualizationStrategy = context.contextualizationStrategy;
		this.structure = context.structure;
		this.monitor = context.monitor;
		this.catalog = context.catalog;
		this.scale = context.scale;
		this.artifactType = context.artifactType;
		this.semantics = context.semantics;
		this.parent = context.parent;
		this.report = context.report;
		this.resolutionScope = context.resolutionScope;
		this.targetSemantics = context.targetSemantics;
		this.targetName = context.targetName;
		this.rootSubject = context.rootSubject;
		this.observations = context.observations;
	}

	@Override
	public IRuntimeContext createChild(IObservable indirectTarget) {

		RuntimeContext ret = new RuntimeContext(this);

		ret.parent = this;
		ret.targetName = indirectTarget.getLocalName();
		ret.artifactType = Observables.INSTANCE.getObservableType(indirectTarget, true);
		ret.semantics = new HashMap<>();
		ret.targetSemantics = indirectTarget;
		ret.monitor = monitor;
		ret.semantics.put(ret.targetName, ret.targetSemantics);
		/*
		 * if appropriate, the target has been created by the upstream createChild(...
		 * Actuator ...), so we just set it from the catalog.
		 */
		ret.target = catalog.get(ret.targetName);

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
	public ConfigurationDetector getConfigurationDetector() {
		return configurationDetector;
	}

	@Override
	public Collection<IRelationship> getOutgoingRelationships(ISubject observation) {
		return network.getOutEdges(observation);
	}

	@Override
	public Collection<IRelationship> getIncomingRelationships(ISubject observation) {
		return network.getInEdges(observation);
	}

	@Override
	public IDirectObservation getParentOf(IObservation observation) {
		for (DefaultEdge edge : this.structure.outgoingEdgesOf(observation)) {
			IArtifact source = this.structure.getEdgeTarget(edge);
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
	public ISubject getSourceSubject(IRelationship relationship) {
		return network.getSource(relationship);
	}

	@Override
	public ISubject getTargetSubject(IRelationship relationship) {
		return network.getDest(relationship);
	}

	@Override
	public void exportNetwork(String outFile) {
		// TODO export a GEFX file
	}

	@Override
	public INamespace getNamespace() {
		return namespace;
	}

	@Override
	public IArtifact getTargetArtifact() {
		return target == null ? this.catalog.get(this.targetName) : target;
	}

	@Override
	public IArtifact getArtifact(String localName) {
		return catalog.get(localName);
	}

	@Override
	public String getTargetName() {
		return targetName;
	}

	@Override
	public IRuntimeContext copy() {
		RuntimeContext ret = new RuntimeContext(this);
		// make a deep copy of all localizable info so we can rename elements
		ret.catalog = new HashMap<>(this.catalog);
		ret.semantics = new HashMap<>(this.semantics);
		return ret;
	}

	@Override
	public void rename(String name, String alias) {
		IArtifact obj = catalog.get(name);
		if (obj != null) {
			catalog.remove(name);
			catalog.put(alias, obj);
			IObservable obs = semantics.remove(name);
			if (obs != null) {
				semantics.put(alias, obs);
			}
		}
	}

	@Override
	public void setData(String name, IArtifact data) {
		catalog.put(name, data);
	}

	public void set(String name, Object value) {
		this.put(name, value);
	}

	@Override
	public IMonitor getMonitor() {
		return monitor;
	}

	@Override
	public ICountableObservation newObservation(IObservable observable, String name, IScale scale)
			throws KlabException {

		if (!observable.is(Type.COUNTABLE)) {
			throw new IllegalArgumentException(
					"RuntimeContext: cannot create a non-countable observation with newObservation()");
		}

		ICountableObservation ret = null;
		Observable obs = new Observable((Observable) observable);

		/*
		 * preload all the possible resolvers in the wider scope before specializing the
		 * scope to the child observation. Then leave it to the kbox to use the context
		 * with the preloaded cache.
		 */
		this.resolutionScope.preloadResolvers(observable);

		ResolutionScope scope = Resolver.INSTANCE.resolve(obs, this.resolutionScope, Mode.RESOLUTION, scale);
		if (scope.getCoverage().isRelevant()) {

			ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
			ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity()).createChild();

			Dataflow dataflow = Dataflows.INSTANCE
					.compile("local:task:" + session.getId() + ":" + subtask.getId(), scope).setPrimary(false);

			// instantiator may have observables that are defined from pre-existing
			// attributes in the context.
			dataflow.setModel((Model) model);

			ret = (ICountableObservation) dataflow.run(scale, ((Monitor) monitor).get(subtask));
			if (ret != null) {
				((DirectObservation) ret).setName(name);
			}
		}
		return ret;
	}

	@Override
	public IRelationship newRelationship(IObservable observable, String name, IScale scale, IObjectArtifact source,
			IObjectArtifact target) {

		if (!observable.is(Type.RELATIONSHIP)) {
			throw new IllegalArgumentException(
					"RuntimeContext: cannot create a relationship of type " + observable.getType());
		}

		IRelationship ret = null;
		Observable obs = new Observable((Observable) observable);
		obs.setName(name);

		/*
		 * preload all the possible resolvers in the wider scope before specializing the
		 * scope to the child observation. Then leave it to the kbox to use the context
		 * with the preloaded cache.
		 */
		this.resolutionScope.preloadResolvers(observable);
		ResolutionScope scope = Resolver.INSTANCE.resolve(obs, this.resolutionScope, (Subject) source, (Subject) target,
				scale);

		if (scope.getCoverage().isRelevant()) {

			ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
			ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity()).createChild();

			Dataflow dataflow = Dataflows.INSTANCE
					.compile("local:task:" + session.getId() + ":" + subtask.getId(), scope).setPrimary(false);
			;

			ret = (IRelationship) dataflow.run(scale, ((Monitor) monitor).get(subtask));
		}
		return ret;
	}

	@Override
	public IRuntimeContext createChild(IScale scale, IActuator actuator, IResolutionScope scope, IMonitor monitor) {

		RuntimeContext ret = new RuntimeContext(this);
		ret.parent = this;
		ret.namespace = ((Actuator) actuator).getNamespace();
		ret.targetName = ((Actuator) actuator).isPartition() ? ((Actuator) actuator).getPartitionedTarget()
				: actuator.getName();
		ret.resolutionScope = (ResolutionScope) scope;
		ret.artifactType = Observables.INSTANCE.getObservableType(((Actuator) actuator).getObservable(), true);
		ret.scale = scale;
		ret.semantics = new HashMap<>();
		ret.targetSemantics = ((Actuator) actuator).getObservable();
		ret.monitor = monitor;
		ret.semantics.put(actuator.getName(), ret.targetSemantics);

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
			ret.artifactType = Observables.INSTANCE.getObservableType(((Actuator) actuator).getObservable(), true);
		}

		return ret;
	}

	@Override
	public IRuntimeContext createContext(IScale scale, IActuator actuator, IResolutionScope scope, IMonitor monitor) {

		RuntimeContext ret = new RuntimeContext(this);
		ret.parent = this;
		ret.namespace = ((Actuator) actuator).getNamespace();
		ret.targetName = ((Actuator) actuator).isPartition() ? ((Actuator) actuator).getPartitionedTarget()
				: actuator.getName();
		ret.resolutionScope = (ResolutionScope) scope;
		ret.artifactType = Observables.INSTANCE.getObservableType(((Actuator) actuator).getObservable(), true);
		ret.scale = scale;
		ret.semantics = new HashMap<>();
		ret.catalog = new HashMap<>();
		ret.targetSemantics = ((Actuator) actuator).getObservable();
		ret.monitor = monitor;
		ret.semantics.put(actuator.getName(), ret.targetSemantics);

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
			ret.artifactType = Observables.INSTANCE.getObservableType(((Actuator) actuator).getObservable(), true);
		}

		return ret;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls) {
		List<T> ret = new ArrayList<>();
		for (DefaultEdge edge : this.structure.incomingEdgesOf(artifact)) {
			IArtifact source = this.structure.getEdgeSource(edge);
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
				ret.add(new Pair<>(s, (T) catalog.get(s)));
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
		return semantics.get(identifier);
	}

	@Override
	public void processAnnotation(IAnnotation annotation) {
		switch (annotation.getName()) {
		case "probe":
			addTargetToStructure(annotation);
			break;
		default:
			break;
		}
	}

	private void addTargetToStructure(IAnnotation probe) {

		IState state = null;
		if (probe.get("observable") == null) {
			// TODO check if collapsing is requested
			state = target instanceof IState ? (IState) target : null;
		} else {
			// TODO build requested observation
		}

		if (state != null && !structure.vertexSet().contains(state) && parent != null
				&& parent.target instanceof IDirectObservation) {
			structure.addVertex(state);
			structure.addEdge(state, parent.target);
		}

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
	public void link(IArtifact parent, IArtifact child) {
		this.structure.addVertex(child);
		this.structure.addEdge(child, parent);
	}

	/**
	 * Pre-fill the artifact catalog with the artifact relevant to the passed
	 * actuator and scope.
	 * 
	 * @param actuator
	 * @param scope
	 */
	public IArtifact createTarget(Actuator actuator, IScale scale, IResolutionScope scope,
			IDirectObservation rootSubject) {

		Map<String, Pair<Observable, Mode>> targetObservables = new HashMap<>();

		if (this.catalog.get(actuator.getName()) == null && !actuator.computesRescaledState()) {
			targetObservables.put(actuator.getName(), new Pair<>(actuator.getObservable(), actuator.getMode()));
		}

		/*
		 * add any target of indirect computations
		 */
		for (IComputableResource computation : actuator.getComputation()) {
			if (computation.getTarget() != null && this.catalog.get(computation.getTarget().getLocalName()) == null) {
				targetObservables.put(computation.getTarget().getLocalName(),
						new Pair<>((Observable) computation.getTarget(), computation.getComputationMode()));
			}
		}

		for (String name : targetObservables.keySet()) {

			Pair<Observable, Mode> op = targetObservables.get(name);
			Observable observable = op.getFirst();
			Mode mode = op.getSecond();

			IObservation observation = null;
			List<IState> predefinedStates = new ArrayList<>();

			if (observable.is(Type.COUNTABLE) && mode == Mode.INSTANTIATION) {
				observation = new ObservationGroup(observable, (Scale) scale, this, IArtifact.Type.OBJECT);
			} else if (observable.is(Type.RELATIONSHIP)) {
				observation = DefaultRuntimeProvider.createRelationship(observable, scale,
						scope.getRelationshipSource(), scope.getRelationshipTarget(), this);
				if (getRootSubject() != null) {
					((Observation) getRootSubject()).setLastUpdate(System.currentTimeMillis());
				}
			} else {
				observation = DefaultRuntimeProvider.createObservation(observable, scale, this);
				if (getRootSubject() != null) {
					((Observation) getRootSubject()).setLastUpdate(System.currentTimeMillis());
				}
				if (parent != null && actuator.getDataflow().getModel() != null) {
					for (String attr : actuator.getDataflow().getModel().getAttributeObservables().keySet()) {
						IArtifact artifact = parent.findArtifactByObservableName(attr);
						if (artifact instanceof IState) {
							// observable may be different or use data reduction traits
							IState stateView = Observations.INSTANCE.getStateViewAs(
									actuator.getDataflow().getModel().getAttributeObservables().get(attr),
									(IState) artifact, scale, this);
							predefinedStates.add(stateView);
						}
					}
				}
			}

			// transmit all annotations and any interpretation keys to the artifact
			actuator.notifyNewObservation(observation);

			/*
			 * register the obs and potentially the root subject
			 */
			this.observations.put(observation.getId(), observation);
			if (this.rootSubject == null && observation instanceof ISubject) {
				this.rootSubject = (ISubject) observation;
			}
			this.catalog.put(name, observation);
			if (!(observation instanceof ObservationGroup)) {
				this.structure.addVertex(observation);
				if (scope.getContext() != null) {
					this.structure.addEdge(observation, scope.getContext());
				}
			}
			if (observation instanceof ISubject) {
				this.network.addVertex((ISubject) observation);
			}

			/*
			 * add any predefined states to the structure
			 */
			for (IState state : predefinedStates) {
				link(observation, state);
			}
		}

		this.target = this.catalog.get(actuator.getName());

		return this.target;
	}

	@Override
	public IDirectObservation getContextObservation() {
		return resolutionScope.getContext();
	}

	@Override
	public ISubject getRootSubject() {
		return rootSubject;
	}

	@Override
	public IObservation getObservation(String observationId) {
		return observations.get(observationId);
	}

	@Override
	public Graph<? extends IArtifact, ?> getStructure() {
		return structure;
	}

	/**
	 * TODO the scheduler starts right away, so this is actually "run" if the
	 * scheduler is null.
	 */
	@Override
	public Scheduler<?> getScheduler() {
		if (rootSubject == null || rootSubject.getScale().getTime() == null) {
			throw new IllegalStateException("cannot create a scheduler for a non-temporal observation");
		}
		if (this.scheduler == null) {
			// TODO create and configure the scheduler
		}
		return this.scheduler;
	}

	@Override
	public void replaceTarget(IArtifact target) {
		this.target = target;
		if (target != null) {
			Map<String, IArtifact> newCatalog = new HashMap<>();
			newCatalog.putAll(this.catalog);
			newCatalog.put(targetName, target);
			this.catalog = newCatalog;
		}
	}

	@Override
	public Pair<String, IArtifact> findArtifact(IObservable observable) {
		for (String key : catalog.keySet()) {
			IArtifact artifact = catalog.get(key);
			if (artifact != null && artifact instanceof IObservation
					&& ((Observable) ((IObservation) artifact).getObservable()).canResolve((Observable) observable)) {
				return new Pair<>(key, artifact);
			}
		}
		return null;
	}

	public IArtifact findArtifactByObservableName(String name) {
		for (String key : catalog.keySet()) {
			IArtifact artifact = catalog.get(key);
			if (artifact != null && artifact instanceof IObservation
					&& ((Observable) ((IObservation) artifact).getObservable()).getLocalName().equals(name)) {
				return artifact;
			}
		}
		return null;
	}

	@Override
	public IReport getReport() {
		return report;
	}

	@Override
	public ContextualizationStrategy getContextualizationStrategy() {
		return contextualizationStrategy;
	}

	@Override
	public void setContextualizationStrategy(ContextualizationStrategy contextualizationStrategy) {
		this.contextualizationStrategy = contextualizationStrategy;
	}

	@Override
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public IModel getModel() {
		return model;
	}

}
