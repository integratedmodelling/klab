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
import java.util.function.Function;

import org.apache.commons.collections.IteratorUtils;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Roles;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression.Context;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.IVariable;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
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
import org.integratedmodelling.klab.data.storage.RescalingState;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Actuator.Computation;
import org.integratedmodelling.klab.dataflow.ContextualizationStrategy;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.documentation.Report;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.AbstractTask;
import org.integratedmodelling.klab.engine.runtime.ConfigurationDetector;
import org.integratedmodelling.klab.engine.runtime.EventBus;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.engine.runtime.code.ExpressionContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.IntelligentMap;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.owl.ObservableBuilder;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.Resolver;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Triple;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * A runtime scope is installed in the root subject to keep track of what
 * happens during contextualization. Children of the root scope are used across
 * the entire lifetime of a context and carry all the information about the
 * runtime environment during computation.
 * 
 * @author ferdinando.villa
 *
 */
public class RuntimeScope extends Parameters<String> implements IRuntimeScope {

	INamespace namespace;
	Provenance provenance;
	EventBus eventBus;
	ConfigurationDetector configurationDetector;
	Graph<IDirectObservation, IRelationship> network;
	Structure structure;
	Map<String, IArtifact> catalog;
	IMonitor monitor;
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
	ContextualizationStrategy contextualizationStrategy;
	// set only by the actuator, relevant only in instantiators with attributes
	IModel model;
	Set<String> notifiedObservations;
	Map<IConcept, ObservationGroup> groups;
	Map<String, IVariable> symbolTable = new HashMap<>();
	Dataflow dataflow;
	IntelligentMap<Pair<String, IKimExpression>> behaviorBindings;
	Map<String, ObservationListener> listeners = Collections.synchronizedMap(new LinkedHashMap<>());
	Set<String> watchedObservations = null;

	// not inherited, can be set from the outside to silence notifications
	boolean silent = false;

	// root scope of the entire dataflow, unchanging, for downstream resolutions
	ResolutionScope resolutionScope;

	// cache for repeated dataflow resolutions
	Map<ResolvedObservable, List<Pair<ICoverage, Dataflow>>> dataflowCache = new HashMap<>();
	private IActuator actuator;

	public RuntimeScope(Actuator actuator, IResolutionScope scope, IScale scale, IMonitor monitor) {

		this.catalog = new HashMap<>();
		this.behaviorBindings = new IntelligentMap<>();
		this.report = new Report(this, monitor.getIdentity().getParentIdentity(ISession.class).getId());
		this.observations = new HashMap<>();
		this.network = new DefaultDirectedGraph<>(IRelationship.class);
		this.structure = new Structure();
		this.provenance = new Provenance();
		this.notifiedObservations = new HashSet<>();
		this.groups = new HashMap<>();
		this.monitor = monitor;
		this.namespace = actuator.getNamespace();
		this.scale = scale;
		this.targetName = actuator.isPartition() ? actuator.getPartitionedTarget() : actuator.getName();
		this.actuator = actuator;
		this.targetSemantics = actuator.getObservable();
		this.artifactType = Observables.INSTANCE.getObservableType(actuator.getObservable(), true);
		this.dataflow = actuator.getDataflow();
		this.watchedObservations = Collections.synchronizedSet(new HashSet<>());

		/*
		 * Complex and convoluted, but there is no other way to get this which must be
		 * created by the task for the first context. Successive contextualizations will
		 * add to it.
		 */
		this.contextualizationStrategy = monitor.getIdentity().getParentIdentity(AbstractTask.class)
				.getContextualizationStrategy();

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

	RuntimeScope(RuntimeScope scope, Map<String, IVariable> variables) {
		this(scope);
		this.getVariables().putAll(variables);
	}

	RuntimeScope(RuntimeScope context) {
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
		this.dataflowCache.putAll(context.dataflowCache);
		this.actuator = context.actuator;
		this.target = context.target;
		this.notifiedObservations = context.notifiedObservations;
		this.dataflow = context.dataflow;
		this.behaviorBindings = context.behaviorBindings;
		this.listeners = context.listeners;
		this.watchedObservations = context.watchedObservations;
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

		/*
		 * if not within a partition, the target has been created by the upstream
		 * createChild(... Actuator ...), so we just set it from the catalog.
		 */
		if (catalog.get(ret.targetName) == null) {
			ret.target = createTarget(indirectTarget);
		}
		ret.target = catalog.get(ret.targetName);

		/*
		 * this is the only one where the symbol table is kept.
		 */
//		ret.symbolTable.get().putAll(symbolTable);

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
	public Collection<IRelationship> getOutgoingRelationships(IDirectObservation observation) {
		return network.outgoingEdgesOf(observation);
	}

	@Override
	public Collection<IRelationship> getIncomingRelationships(IDirectObservation observation) {
		return network.incomingEdgesOf(observation);
	}

	@Override
	public IDirectObservation getParentOf(IObservation observation) {
		if (observation instanceof ObservationGroup) {
			return structure.getGroupParent((ObservationGroup) observation);
		}
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

	@Override
	public IArtifact getArtifact(String localName) {
		return catalog.get(localName);
	}

	@Override
	public String getTargetName() {
		return targetName;
	}

	@Override
	public IRuntimeScope copy() {
		RuntimeScope ret = new RuntimeScope(this);
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
		ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity()).createChild();
		ResolutionScope scope = Resolver.create(this.dataflow).resolve(observable, this.resolutionScope,
				Mode.RESOLUTION, scale, model);
		if (scope.getCoverage().isRelevant()) {
			Dataflow dataflow = Dataflows.INSTANCE.compile("local:task:" + session.getId() + ":" + subtask.getId(),
					scope, this.dataflow)/*
											 * .setPrimary(false)
											 */;
			dataflow.setModel((Model) model);
			ret = (IConfiguration) dataflow.withMetadata(metadata).withConfigurationTargets(targets)
					.run(scale.initialization(), (Actuator) this.actuator, ((Monitor) monitor).get(subtask));
		}
		return ret;
	}

	//

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IArtifact> T resolve(IObservable observable, IDirectObservation observation, ITaskTree<?> task,
			Mode mode) {

		/*
		 * preload all the possible resolvers in the wider scope before specializing the
		 * scope to the child observation. Then leave it to the kbox to use the context
		 * with the preloaded cache.
		 */
		this.resolutionScope.preloadResolvers(observable);
		ISession session = monitor.getIdentity().getParentIdentity(ISession.class);

		Dataflow dataflow = null;

		List<Pair<ICoverage, Dataflow>> pairs = dataflowCache
				.get(new ResolvedObservable((Observable) observable, mode));

		if (pairs != null) {
			for (Pair<ICoverage, Dataflow> pair : pairs) {
				if (pair.getFirst() == null || pair.getFirst().contains(scale)) {
					dataflow = pair.getSecond();
					break;
				}
			}
		}
		if (dataflow == null) {

			if (pairs == null) {
				pairs = new ArrayList<>();
				dataflowCache.put(new ResolvedObservable((Observable) observable, mode), pairs);
			}

			ResolutionScope scope = Resolver.create(this.dataflow).resolve((Observable) observable,
					this.resolutionScope.getDeferredChildScope(observation, mode), mode, scale, model);

			if (scope.getCoverage().isRelevant()) {
				dataflow = Dataflows.INSTANCE.compile("local:task:" + session.getId() + ":" + task.getId(), scope,
						this.dataflow)/*
										 * .setPrimary(false)
										 */;
				pairs.add(new Pair<>(dataflow.getCoverage(), dataflow));
			}
		}

		IArtifact ret = null;
		if (dataflow == null) {
			if (observable.isOptional()) {
				monitor.warn("cannot resolve optional observable " + observable + " in " + observation);
			} else {
				monitor.error("cannot resolve mandatory observable " + observable + " in " + observation);
				// don't stop so we know which objects don't resolve, although >1 may be
				// annoying.
			}
		} else {
			ret = dataflow.withScope(this.resolutionScope.getDeferredChildScope(observation, mode))
					.withScopeScale(observation.getScale()).withMetadata(observation.getMetadata())
					.run(observation.getScale(), (Actuator) this.actuator, ((Monitor) monitor).get(task));
		}

		return (T) ret;
	}

	/**
	 * Resolve a new direct observation (which doesn't exist yet: the dataflow will
	 * create it) passing the observable and the name.
	 * 
	 * @param observable
	 * @param name
	 * @param scale
	 * @param subtask
	 * @return
	 */
	public Dataflow resolve(IObservable observable, String name, IScale scale, ITaskTree<?> subtask) {

		/*
		 * preload all the possible resolvers in the wider scope before specializing the
		 * scope to the child observation. Then leave it to the kbox to use the context
		 * with the preloaded cache.
		 */
		this.resolutionScope.preloadResolvers(observable);

		Dataflow dataflow = null;

		List<Pair<ICoverage, Dataflow>> pairs = dataflowCache
				.get(new ResolvedObservable((Observable) observable, Mode.RESOLUTION));

		ISession session = monitor.getIdentity().getParentIdentity(ISession.class);

		if (pairs != null) {
			for (Pair<ICoverage, Dataflow> pair : pairs) {
				if (pair.getFirst() == null || pair.getFirst().contains(scale)) {
					dataflow = pair.getSecond();
					break;
				}
			}
		}
		if (dataflow == null) {

			if (pairs == null) {
				pairs = new ArrayList<>();
				dataflowCache.put(new ResolvedObservable((Observable) observable, Mode.RESOLUTION), pairs);
			}

			ResolutionScope scope = Resolver.create(this.dataflow).resolve((Observable) observable,
					this.resolutionScope.getChildScope(observable, scale, name), Mode.RESOLUTION, scale, model);

			if (scope.getCoverage().isRelevant()) {

				dataflow = Dataflows.INSTANCE.compile("local:task:" + session.getId() + ":" + subtask.getId(), scope,
						this.dataflow)/*
										 * .setPrimary(false)
										 */;
				dataflow.setModel((Model) model);
				pairs.add(new Pair<>(dataflow.getCoverage(), dataflow));

			} else if (resolutionScope.getPreresolvedModels(observable) == null
					|| this.resolutionScope.getPreresolvedModels(observable).getSecond().size() == 0) {

				/*
				 * Add an empty dataflow to create the observation. This is only done if there
				 * are no preloaded resolvers in this scale, so we are certain that other
				 * subjects will encounter the same conditions.
				 */
				pairs.add(new Pair<>(null,
						dataflow = Dataflow.empty(observable, observable.getName(), scope, this.dataflow)));
				dataflowCache.put(new ResolvedObservable((Observable) observable, Mode.RESOLUTION), pairs);
			}
		}

		return dataflow;
	}

	/**
	 * TODO move the dataflow caching logics of all the new-.... functions into a
	 * DataflowPool object or something like that.
	 */
	@Override
	public IDirectObservation newObservation(IObservable observable, String name, IScale scale, IMetadata metadata)
			throws KlabException {

		if (!observable.is(Type.COUNTABLE)) {
			throw new IllegalArgumentException(
					"RuntimeContext: cannot create a non-countable observation with newObservation()");
		}

		IDirectObservation ret = null;
		Observable obs = new Observable((Observable) observable);
//		obs.setName(name);

		/*
		 * harmonize the scale according to what the model wants and the context's
		 */
		scale = Scale.contextualize(scale, contextSubject.getScale(), model == null ? null : model.getAnnotations(),
				monitor);

		ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity()).createChild();
		Dataflow dataflow = resolve(obs, name, scale, subtask);

		IArtifact observation = dataflow
				.withScope(this.resolutionScope.getChildScope(observable, contextSubject, scale))
				.withMetadata(metadata)
				.withinGroup(this.target instanceof ObservationGroup ? (ObservationGroup)this.target : null)
				.run(scale.initialization(), (Actuator) this.actuator, ((Monitor) monitor).get(subtask));

		if (observation instanceof IDirectObservation) {
			ret = (IDirectObservation) observation;
			((DirectObservation) ret).setName(name);
			for (ObservationListener listener : listeners.values()) {
				listener.newObservation(ret);
			}
		}

		return ret;
	}

	@Override
	public void newPredicate(IDirectObservation target, IConcept predicate) {

		if (predicate.isAbstract() || (!predicate.is(Type.TRAIT) && !predicate.is(Type.ROLE))) {
			throw new IllegalArgumentException(
					"RuntimeContext: cannot attribute predicate " + predicate + ": must be a concrete trait or role");
		}

		IObservable observable = new ObservableBuilder(predicate).of(target.getObservable().getType())
				.buildObservable();

		/*
		 * preload all the possible resolvers in the wider scope before specializing the
		 * scope to the child observation. Then leave it to the kbox to use the context
		 * with the preloaded cache.
		 */
		this.resolutionScope.preloadResolvers(observable);

		Dataflow dataflow = null;
		ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
		ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity()).createChild();
		ResolutionScope scope = this.resolutionScope.getChildScope(target, Mode.RESOLUTION);

		List<Pair<ICoverage, Dataflow>> pairs = dataflowCache
				.get(new ResolvedObservable((Observable) observable, Mode.RESOLUTION));

		if (pairs != null) {
			for (Pair<ICoverage, Dataflow> pair : pairs) {
				if (pair.getFirst() == null || pair.getFirst().contains(scale)) {
					dataflow = pair.getSecond();
					break;
				}
			}
		}
		if (dataflow == null) {

			if (pairs == null) {
				pairs = new ArrayList<>();
				dataflowCache.put(new ResolvedObservable((Observable) observable, Mode.RESOLUTION), pairs);
			}

			// TODO check model parameter
			scope = Resolver.create(this.dataflow).resolve((Observable) observable, scope, Mode.RESOLUTION,
					target.getScale(), model);

			if (scope.getCoverage().isRelevant()) {

				dataflow = Dataflows.INSTANCE.compile("local:task:" + session.getId() + ":" + subtask.getId(), scope,
						this.dataflow)/*
										 * .setPrimary(false)
										 */;
				dataflow.setModel((Model) model);

				// TODO this must be added to the computational strategy and linked to the
				// original context.
				pairs.add(new Pair<>(dataflow.getCoverage(), dataflow));

			} else if (resolutionScope.getPreresolvedModels(observable) == null
					|| resolutionScope.getPreresolvedModels(observable).getSecond().size() == 0) {

				/*
				 * Add an empty dataflow to create the predicate without further consequences.
				 * This is only done if there are no preloaded resolvers in this scale, so we
				 * are certain that other subjects will encounter the same conditions.
				 */
				pairs.add(new Pair<>(null, dataflow = Dataflow.empty(observable, null, scope, this.dataflow)));
				dataflowCache.put(new ResolvedObservable((Observable) observable, Mode.RESOLUTION), pairs);
			}
		}

		if (dataflow != null) {
			dataflow.run(scale.initialization(), (Actuator) this.actuator, ((Monitor) monitor).get(subtask));
		}

	}

	@Override
	public IRelationship newRelationship(IObservable observable, String name, IScale scale, IObjectArtifact source,
			IObjectArtifact target, IMetadata metadata) {

		if (!observable.is(Type.RELATIONSHIP)) {
			throw new IllegalArgumentException(
					"RuntimeContext: cannot create a relationship of type " + observable.getType());
		}

		Observable obs = new Observable((Observable) observable).withoutModel();
		obs.setName(name);
		scale = Scale.contextualize(scale, contextSubject.getScale(), model == null ? null : model.getAnnotations(),
				monitor);
		ITaskTree<?> subtask = ((ITaskTree<?>) monitor.getIdentity()).createChild();
		Dataflow dataflow = resolve(obs, name, scale, subtask);
		IRelationship ret = (IRelationship) dataflow.withMetadata(metadata)
				.withScope(this.resolutionScope.getChildScope(observable, contextSubject, scale))
				.connecting((IDirectObservation) source, (IDirectObservation) target)
				.run(scale.initialization(), (Actuator) this.actuator, ((Monitor) monitor).get(subtask));

		if (ret != null) {
			((DirectObservation) ret).setName(name);
			for (ObservationListener listener : listeners.values()) {
				listener.newObservation(ret);
			}
		}

		return ret;
	}

	@Override
	public IRuntimeScope createChild(IScale scale, IActuator act, IResolutionScope scope, IMonitor monitor) {

		Actuator actuator = (Actuator) act;

		RuntimeScope ret = new RuntimeScope(this);
		ret.parent = this;
		ret.namespace = actuator.getNamespace();
		ret.targetName = actuator.isPartition() ? actuator.getPartitionedTarget() : actuator.getName();
		ret.resolutionScope = (ResolutionScope) scope;
		ret.artifactType = actuator.getObservable().is(Type.CONFIGURATION) ? Type.CONFIGURATION
				: Observables.INSTANCE.getObservableType(actuator.getObservable(), true);
		ret.scale = scale;
		ret.semantics = new HashMap<>();
		ret.targetSemantics = actuator.getObservable();
		ret.monitor = monitor;
		ret.semantics.put(actuator.getName(), ret.targetSemantics);
		ret.actuator = actuator;
		if (this.target instanceof IDirectObservation) {
			ret.contextSubject = (IDirectObservation) this.target;
			if (ret.contextSubject instanceof IProcess) {
				ret.contextSubject = getParentOf(ret.contextSubject);
			}
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

		/*
		 * if we're subsetting the scale, finish up the partial scale we're using and
		 * set the computation to use it.
		 */
		if (actuator.getScale() != null) {

			/*
			 * the child will contribute to our own target, so just give it a view and let
			 * the computation happen in the subscale of interest.
			 */
			ret.scale = actuator.getScale();
			if (actuator.isPartition()) {

				/*
				 * the target to rescale hasn't been created yet. We find the corresponding
				 * actuator and call createTarget() on it using the overall scale, pre-building
				 * the observation in the catalog but letting its actuator notify it later after
				 * the merging is done.
				 */
				Actuator mergingActuator = actuator.getDataflow().getActuator(actuator.getPartitionedTarget());
				if (mergingActuator == null) {
					throw new IllegalStateException(
							"internal: cannot find merging actuator named " + actuator.getPartitionedTarget());
				}

				IArtifact merging = createTarget(mergingActuator, scale, scope, rootSubject);

				/*
				 * partition sub-state does not go in the catalog
				 */
				if (merging instanceof IState) {
					// complete the partial scale with the overall view of the context
					ret.target = Observations.INSTANCE.getStateView((IState) merging, actuator.getScale(), ret);
					if (ret.target instanceof RescalingState) {
						// for debugging
						((RescalingState) ret.target).setLocalId(actuator.getName());
					}
				} else {
					ret.target = merging;
				}

			}

		} else {

			// save existing target
			ret.target = ret.createTarget((Actuator) actuator, scale, scope, rootSubject);
			if (ret.target != null && this.target != null) {
				ret.semantics.put(actuator.getName(), ((Actuator) actuator).getObservable());
				// ret.artifactType = Observables.INSTANCE.getObservableType(((Actuator)
				// actuator).getObservable(), true);
			}
		}

		return ret;
	}

	/**
	 * Used in sub-resolution with switched context.
	 */
	@Override
	public IRuntimeScope createContext(IScale scale, IActuator actuator, IDataflow<?> dataflow, IResolutionScope scope, IMonitor monitor) {

		RuntimeScope ret = new RuntimeScope(this);
		ret.parent = this;
		ret.namespace = ((Actuator) actuator).getNamespace();
		ret.targetName = ((Actuator) actuator).isPartition() ? ((Actuator) actuator).getPartitionedTarget()
				: actuator.getName();
		ret.resolutionScope = (ResolutionScope) scope;
		ret.artifactType = Observables.INSTANCE.getObservableType(((Actuator) actuator).getObservable(), true);
		ret.scale = scale;
		ret.semantics = new HashMap<>();
		ret.catalog = new HashMap<>();
		ret.groups = new HashMap<>();
		ret.targetSemantics = ((Actuator) actuator).getObservable();
		ret.monitor = monitor;
		ret.semantics.put(actuator.getName(), ret.targetSemantics);
		ret.actuator = actuator;
		ret.contextSubject = scope.getContext();
		ret.dataflow = (Dataflow)dataflow;

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
		if (artifact instanceof ObservationGroup) {
			return IteratorUtils.toList(((ObservationGroup) artifact).iterator());
		}
		if (artifact instanceof ObservationGroupView) {
			return IteratorUtils.toList(((ObservationGroupView) artifact).iterator());
		}
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
			structure.addEdge(state,
					parent.target instanceof ObservationGroup ? ((ObservationGroup) parent.target).getContext()
							: parent.target);
			
//			if (!(parent.target instanceof ObservationGroup)) {
//				ObservationChange change = ((Observation)parent.target).requireStructureChangeEvent();
//				change.setNewSize(change.getNewSize() + 1);
//			}
			
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

		if (parent instanceof ObservationGroup) {
			parent = ((ObservationGroup) parent).getContext();
		} else if (parent instanceof IProcess) {
			parent = ((IProcess) parent).getContext();
		}

		this.structure.addVertex(child);
		this.structure.addEdge(child, parent);
	}

	@Override
	public IState addState(IDirectObservation target, IObservable observable, Object data) {
		return null;
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

		/*
		 * support map: the fields are observable, mode, and a boolean that is true if
		 * the actuator is void, meaning an archetype should be created instead of a
		 * true observation. So far this only happens with contextual quality learning
		 * models.
		 */
		Map<String, Triple<Observable, Mode, Boolean>> targetObservables = new HashMap<>();

		if (this.catalog.get(actuator.getName()) != null) {
			return this.catalog.get(actuator.getName());
		}

		if (!actuator.isPartition()) {
			targetObservables.put(actuator.getName(), new Triple<>(actuator.getObservable(), actuator.getMode(),
					actuator.getType() == IArtifact.Type.VOID));
		}

		/*
		 * add any target of indirect computations
		 */
		for (IContextualizable computation : actuator.getComputation()) {
			if (computation.getTarget() != null && this.catalog.get(computation.getTarget().getName()) == null) {
				targetObservables.put(computation.getTarget().getName(),
						new Triple<>((Observable) computation.getTarget(), computation.getComputationMode(), false));
			}
		}

		/*
		 * will be pre-existing only if: (i) it's an existing observation group and the
		 * observable does not determine a new view; or (ii) we're resolving an
		 * attribute through a dataflow, which will be added by this.
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

				if (observable.is(Type.RELATIONSHIP)) {
					observation = DefaultRuntimeProvider.createRelationship(observable, scale,
							actuator.getDataflow().getRelationshipSource(),
							actuator.getDataflow().getRelationshipTarget(), this);
				} else {
					observation = DefaultRuntimeProvider.createObservation(observable, scale, this, op.getThird());
				}

				if (getRootSubject() != null) {
					((Observation) getRootSubject()).setLastUpdate(System.currentTimeMillis());
				}

				/*
				 * When we get here the model is the resolver, or null for instantiated objects
				 * that found no resolver. If we are downstream of an instantiator, resolve
				 * pre-defined states before resolution is attempted. States are resolved from
				 * resource metadata only if no contextual states are present.
				 */
				IMetadata metadata = actuator.getDataflow().getMetadata();
				if (metadata != null) {
					observation.getMetadata().putAll(metadata);
				}

				if (parent != null && actuator.getDataflow().getModel() != null) {
					for (String attr : actuator.getDataflow().getModel().getAttributeObservables().keySet()) {

						boolean done = false;
						if (metadata != null) {
							/* state specs may be in metadata from resource attributes */
							Object obj = metadata.getCaseInsensitive(attr);
							IState state = (IState) DefaultRuntimeProvider.createObservation(
									actuator.getDataflow().getModel().getAttributeObservables().get(attr), scale, this);
							((State) state).distributeScalar(obj);
							predefinedStates.add(state);
							done = true;
						}

						if (!done) {
							// look up in the first context that has the root subject as a target, or get
							// the parent if none does.
							RuntimeScope p = getParentWithTarget(rootSubject);
							IArtifact artifact = p.findArtifactByObservableName(attr);
							if (artifact == null) {
								Pair<String, IArtifact> art = p.findArtifact(
										actuator.getDataflow().getModel().getAttributeObservables().get(attr));
								artifact = art == null ? null : art.getSecond();
							}
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
			}

			if (preexisting == null) {

				// transmit all annotations and any interpretation keys to the artifact
				actuator.notifyNewObservation(observation);

				/*
				 * register the obs and potentially the root subject
				 */
				this.observations.put(observation.getId(), observation);
				if (this.rootSubject == null && observation instanceof ISubject) {
					this.rootSubject = (ISubject) observation;
					this.eventBus = new EventBus((Subject) this.rootSubject);
					/*
					 * We register the root subject for updates by default. TODO This may be
					 * subjected to a view asking for it at the time of session establishment.
					 */
					watchedObservations.add(observation.getId());
				}
				this.catalog.put(name, observation);
				this.structure.addVertex(observation);
				if (contextSubject != null) {
					this.structure.addEdge(observation,
							contextSubject instanceof ObservationGroup ? contextSubject.getContext() : contextSubject);
				}
				if (observation instanceof ISubject) {
					this.network.addVertex((ISubject) observation);
				}

				/*
				 * set the target observations if this is a configuration
				 */
				if (observation instanceof IConfiguration) {
					((Configuration) observation).setTargets(actuator.getDataflow().getConfigurationTargets());
				}

				/*
				 * add any predefined states to the structure
				 */
				for (IState state : predefinedStates) {
					link(observation, state);
				}

				if (!(observation instanceof IState)) {

					/*
					 * chain to the group if we're in one
					 */
					if (this.dataflow.getObservationGroup() != null) {
						this.dataflow.getObservationGroup().chain(observation);
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

	@Override
	public IObservation getParentArtifactOf(IObservation observation) {
		IObservation ret = this.getParentOf(observation);

		if (observation instanceof IDirectObservation) {

			if (observation instanceof DirectObservation) {
				ret = ((DirectObservation) observation).getGroup() == null ? ret
						: ((DirectObservation) observation).getGroup();
			}
		}
		
		return ret;
	}

	/**
	 * Return whether an observation should be notified to clients. This implies: 1)
	 * that a client is listening and 2) that there are subscriptions from the
	 * client that require notification. This means either of: 1) The observation is
	 * a child of a parent that was subscribed to and was never notified, or 2) the
	 * observation has been changed (directly or in the number of children) after
	 * the last notification and it's part of a subscribed hierarchy (is subscribed
	 * itself and not root, or it's a direct child that a subscribed one).
	 * <p>
	 * Upon this returning true, check if the observation's ID is in
	 * {@link #notifiedObservations} and the observation's changeset to know what to
	 * do.
	 * 
	 * @param observation
	 * @return
	 */
	private boolean isNotifiable(IObservation observation) {

		IObservation parent = this.getParentArtifactOf(observation);
		if (parent != null) {
			return watchedObservations.contains(parent.getId()) || watchedObservations.contains(observation.getId());
		}
		// root context is always notifiable
		return true;
	}

	@Override
	public void updateNotifications(IObservation observation) {

		if (!this.silent && isNotifiable(observation)) {

			ISession session = monitor.getIdentity().getParentIdentity(ISession.class);

			if (!notifiedObservations.contains(observation.getId())) {

				IObservationReference descriptor = Observations.INSTANCE
						.createArtifactDescriptor(observation, getParentOf(observation),
								observation.getScale().initialization(), 0)
						.withTaskId(monitor.getIdentity().getId())
						.withContextId(monitor.getIdentity().getParentIdentity(ITaskTree.class).getContextId());

				session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
						IMessage.Type.NewObservation, descriptor));

				report.include(descriptor);

				notifiedObservations.add(observation.getId());
			}

			for (ObservationChange change : ((Observation) observation).getChangesAndReset()) {
				session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
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
						new Function<IArtifact, Boolean>() {
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
				this.structure.addVertex(ret);
				if (contextSubject != null) {
					this.structure.addEdge(ret,
							contextSubject instanceof ObservationGroup ? contextSubject.getContext() : contextSubject);
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
			observation = getObservationGroup(observable, getDataflow().getResolutionScale());
		} else if (observable.is(Type.TRAIT) || observable.is(Type.ROLE)) {
			/*
			 * TODO this should happen when a predicate observation is made explicitly from
			 * a root-level query, i.e. when 'dropping' attributes on individual
			 * observations is enabled.
			 */
			Logging.INSTANCE.warn("unexpected call to createTarget: check logics");
		} else {
			observation = DefaultRuntimeProvider.createObservation(observable, getDataflow().getResolutionScale(),
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
		this.catalog.put(observable.getName(), observation);
		this.structure.addVertex(observation);
		if (contextSubject != null) {
			this.structure.addEdge(observation,
					contextSubject instanceof ObservationGroup ? contextSubject.getContext() : contextSubject);
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

	private RuntimeScope getRootScope() {
		RuntimeScope ret = this;
		while (ret.parent != null) {
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
	public Graph<? extends IArtifact, ?> getStructure() {
		return structure;
	}

	@Override
	public IScheduler getScheduler() {
		return getRootScope().scheduler;
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
					&& ((Observable) ((IObservation) artifact).getObservable()).getName().equals(name)) {
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
	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public IModel getModel() {
		return model;
	}

	@Override
	public void removeArtifact(IArtifact object) {
		if (structure.containsVertex(object)) {
			structure.removeVertex(object);
		}
	}

	// wrapper for proper caching of sub-dataflows
	class ResolvedObservable {

		Observable observable;
		Mode resolutionMode;

		ResolvedObservable(Observable observable, Mode mode) {
			this.observable = observable;
			this.resolutionMode = mode;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((observable == null) ? 0 : observable.hashCode());
			result = prime * result + ((resolutionMode == null) ? 0 : resolutionMode.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ResolvedObservable other = (ResolvedObservable) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (observable == null) {
				if (other.observable != null)
					return false;
			} else if (!observable.equals(other.observable))
				return false;
			if (resolutionMode != other.resolutionMode)
				return false;
			return true;
		}

		private RuntimeScope getOuterType() {
			return RuntimeScope.this;
		}

	}

	@Override
	public Collection<IArtifact> getArtifact(IConcept observable) {
		List<IArtifact> ret = new ArrayList<>();
		for (IArtifact artifact : catalog.values()) {
			if (artifact instanceof IObservation
					&& ((IObservation) artifact).getObservable().getType().is(observable)) {
				ret.add(artifact);
			}
		}
		return ret;
	}

	public IActuator getActuator() {
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
	public Context getExpressionContext() {
		return ExpressionContext.create(this);
	}

	@Override
	public Collection<IArtifact> getChildArtifactsOf(DirectObservation directObservation) {
		return structure.getChildArtifacts(directObservation);
	}

	@Override
	public Set<String> getNotifiedObservations() {
		return notifiedObservations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IArtifact> T getArtifact(IConcept concept, Class<T> cls) {

		Set<IArtifact> ret = new HashSet<>();
		for (IArtifact artifact : catalog.values()) {
			if (artifact instanceof IObservation && ((IObservation) artifact).getObservable().getType().is(concept)) {
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
						root.scheduler = new Scheduler(this.rootSubject.getId(), resolutionScope.getScale().getTime(),
								monitor);
					}
					((Scheduler) root.scheduler).schedule(action, observation, Time.create(aa), this);

				}
			}
		}

	}

	@Override
	public void scheduleActions(Actuator actuator) {

		/*
		 * Only occurrents occur. FIXME yes, but they may affect continuants
		 */
		boolean isOccurrent = actuator.getType().isOccurrent();
		if (isOccurrent) {

			List<Computation> schedule = new ArrayList<>();
			for (Computation computation : actuator.getContextualizers()) {

				/*
				 * nothing meant for initialization should be scheduled. Resource is null if
				 * this encodes an aux variable.
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
				 * null target == aux variable, occur at will.
				 */
				boolean targetOccurs = computation.target == null || computation.target.getType().isOccurrent()
						|| (isOccurrent
								&& Observables.INSTANCE.isAffectedBy(computation.observable, actuator.getObservable()))
						|| (computation.target != null
								&& computation.resource.getGeometry().getDimension(Dimension.Type.TIME) != null);

				if (isTransition || targetOccurs) {
					schedule.add(computation);
				}
			}

			if (schedule.isEmpty()) {
				return;
			}

			RuntimeScope root = getRootScope();

			if (root.scheduler == null) {
				root.scheduler = new Scheduler(this.rootSubject.getId(), resolutionScope.getScale().getTime(), monitor);
			}

			((Scheduler) root.scheduler).schedule(actuator, schedule, this);
		}

	}

	@Override
	public IRuntimeScope locate(ILocator transitionScale) {

		RuntimeScope ret = new RuntimeScope(this);
		ret.scale = (Scale) transitionScale;

		/*
		 * TODO wrap all temporal states into a temporal rescaling state - works both to
		 * subset and to aggregate. This must apply also to event folders, which must
		 * only show the current events.
		 */

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

		IStorage<?> data = Klab.INSTANCE.getStorageProvider().createStorage(type, scale, this);
		IState ret = new State((Observable) observable, (Scale) scale, this, (IDataStorage<?>) data);

		semantics.put(observable.getName(), observable);
		structure.addVertex(ret);
		structure.addEdge(ret, this.target);
		catalog.put(observable.getName(), ret);
		observations.put(ret.getId(), ret);

		return ret;
	}

	@Override
	public Collection<IArtifact> getAdditionalOutputs() {
		List<IArtifact> ret = new ArrayList<>();
		if (this.model != null) {
			for (int i = 0; i < model.getObservables().size(); i++) {
				IArtifact out = findArtifact(model.getObservables().get(i)).getSecond();
				if (out != null) {
					ret.add(out);
				}
			}
		}
		return ret;
	}

	@Override
	public Collection<IObservable> getDependents(IObservable observable) {
		List<IObservable> ret = new ArrayList<>();
		return ret;
	}

	@Override
	public Collection<IObservable> getPrecursors(IObservable observable) {
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

	@Override
	public String addListener(ObservationListener listener) {
		String ret = NameGenerator.newName();
		listeners.put(ret, listener);
		return ret;
	}

	@Override
	public void removeListener(String listenerId) {
		listeners.remove(listenerId);
	}

	public String toString() {
		return "{Scope of " + contextSubject + " [" + catalog.size() + " obs, " + network.edgeSet().size() + " links]}";
	}

	@Override
	public Set<String> getWatchedObservationIds() {
		return watchedObservations;
	}

	@Override
	public void setSilent(boolean modelIsSilent) {
		this.silent = modelIsSilent;
	}

}
