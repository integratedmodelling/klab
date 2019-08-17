package org.integratedmodelling.klab.engine.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
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
import org.integratedmodelling.klab.api.observations.IConfiguration;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IEventBus;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Event;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.runtime.observations.Process;
import org.integratedmodelling.klab.components.runtime.observations.Relationship;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.ContextualizationStrategy;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Trivial context that will only build simple hierarchies and observations.
 * Meant for quick visualization of non-semantic structures so far. May need to
 * be completed with all its parts to load previously contextualized
 * observations with relationships and other complex interactions. Borrows
 * observations from the default runtime.
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

	// these are shared among all children, created in root only and passed around
	Map<String, IArtifact> artifacts;
	Map<String, IObservation> observations;
	Graph<IArtifact, DefaultEdge> structure;
	Graph<IArtifact, Relationship> network;
	ISubject rootSubject;
	Map<String, IObservable> semantics;

	public SimpleRuntimeScope(Actuator actuator) {
	    this.observable = actuator.getObservable();
	    this.scale = actuator.getDataflow().getScale();
        this.structure = new DefaultDirectedGraph<>(DefaultEdge.class);
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
		this.structure = new DefaultDirectedGraph<>(DefaultEdge.class);
		this.network = new DefaultDirectedGraph<>(Relationship.class);
		this.artifacts = new HashMap<>();
		this.observations = new HashMap<>();
		this.semantics = new HashMap<>();
		this.namespace = Namespaces.INSTANCE.getNamespace(observable.getType().getNamespace());
		this.monitor = monitor;
		this.target = this.rootSubject = new Subject(observable.getName(), (Observable) observable, (Scale) scale,
				this);
		this.structure.addVertex(this.target);
		this.artifacts.put(this.getTargetName(), this.target);
		this.observations.put(this.target.getId(), this.target);
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
	public IScheduler<?> getScheduler() {
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
	public IObjectArtifact newObservation(IObservable observable, String name, IScale scale, IMetadata metadata) throws KlabException {

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
			structure.addVertex(ret);
			if (parent != null && parent.target != null) {
				structure.addEdge(ret, parent.target);
			}
			if (metadata != null) {
				ret.getMetadata().putAll(metadata);
			}
		}

		return ret;
	}

	@Override
	public IObjectArtifact newRelationship(IObservable observable, String name, IScale scale, IObjectArtifact source,
			IObjectArtifact target, IMetadata metadata) {
		
		Relationship ret = new Relationship(name, (Observable) observable, (Scale) scale, this);

		if (ret != null) {

			observations.put(ret.getId(), ret);
			structure.addVertex(ret);
			if (parent != null && parent.target != null) {
				structure.addEdge(ret, parent.target);
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
		return semantics.get(getTargetName());
	}

	@Override
	public String getTargetName() {
		if (targetName != null) {
			return targetName;
		}
		return target instanceof IDirectObservation ? ((IDirectObservation) target).getName()
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
	public IRuntimeScope createChild(IScale scale, IActuator target, IResolutionScope scope, IMonitor monitor) {
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
	public IConfigurationDetector getConfigurationDetector() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRuntimeScope copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rename(String name, String alias) {
		// TODO Auto-generated method stub

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
	public void processAnnotation(IAnnotation annotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public Provenance getProvenance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Graph<? extends IArtifact, ?> getStructure() {
		return structure;
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

	@Override
	public void link(IArtifact parent, IArtifact child) {
		this.structure.addVertex(child);
		this.structure.addEdge(child, parent);
	}

	/**
	 * Return a child context that can be used to build the observation of the
	 * passed resource in our scale. If the observable is null, create a
	 * non-semantic observable.
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
		if (resource.getType() != IArtifact.Type.OBJECT) {
			IStorage<?> data = Klab.INSTANCE.getStorageProvider().createStorage(resource.getType(), getScale(), this);
			ret.target = new State((Observable) observable, (Scale) scale, this, (IDataStorage<?>)data);
		} else {
			ret.target = new ObservationGroup((Observable) observable, (Scale) scale, this, resource.getType());
		}

		this.observable = observable;
		semantics.put(observable.getName(), observable);

		structure.addVertex(ret.target);
		structure.addEdge(ret.target, this.target);
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

	@Override
	public void replaceTarget(IArtifact self) {
		// should never be called
		throw new IllegalStateException(
				"replaceTarget called on a simple context: this context should never be used in computations");
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
					&& ((Observable) ((IObservation) artifact).getObservable()).canResolve((Observable) observable)) {
				return new Pair<>(key, artifact);
			}
		}
		return null;
	}

	@Override
	public IRuntimeScope createContext(IScale scale, IActuator target, IResolutionScope scope, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IReport getReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextualizationStrategy getContextualizationStrategy() {
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
	
//	@Override
//	public ILocator getCurrentTimeLocator() {
//		return scale.getTime() == null ? Time.INITIALIZATION : scale.getTime();
//	}

	@Override
	public Collection<IArtifact> getArtifact(IConcept observable) {
		List<IArtifact> ret = new ArrayList<>();
		for (IArtifact artifact : artifacts.values()) {
			if (artifact instanceof IObservation && ((IObservation)artifact).getObservable().getType().is(observable)) {
				ret.add(artifact);
			}
		}
		return ret;
	}

	@Override
	public IConfiguration newConfiguration(IConcept configurationType, Collection<IObservation> targets,
			IMetadata metadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDataflow<?> getDataflow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context getExpressionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IArtifact> getChildArtifactsOf(DirectObservation directObservation) {
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
		return new ObservationGroup((Observable)observable, (Scale)scale, this, IArtifact.Type.OBJECT);
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


}
