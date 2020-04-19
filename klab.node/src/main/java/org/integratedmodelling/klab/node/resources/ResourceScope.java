package org.integratedmodelling.klab.node.resources;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
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
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IEventBus;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.IVariable;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.ContextualizationStrategy;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.utils.Pair;
import org.jgrapht.Graph;

/**
 * The scope to contextualize a resource outside of a semantic environment.
 * 
 * @author Ferd
 *
 */
public class ResourceScope implements IRuntimeScope {

	IMonitor monitor;

	/**
	 * Use when called from a JSON post request without additional inputs. May build
	 * dummy artifacts for any additional outputs.
	 * 
	 * @param resource
	 * @param geometry
	 */
	public ResourceScope(IResource resource, IGeometry geometry, IMonitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * Use when called from a post request carrying input and everything else in a
	 * KlabData object. This will build artifacts for the inputs and possibly for
	 * the outputs.
	 * 
	 * @param request
	 */
	public ResourceScope(KlabData request) {

	}

	@Override
	public INamespace getNamespace() {
		// TODO Auto-generated method stub
		return null;
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
	public IReport getReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRelationship> getOutgoingRelationships(IDirectObservation observation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IRelationship> getIncomingRelationships(IDirectObservation observation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifact getTargetArtifact() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifact getArtifact(String localName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> T getArtifact(String localName, Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> T getArtifact(IConcept concept, Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> Collection<Pair<String, T>> getArtifacts(Class<T> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IArtifact> getArtifact(IConcept observable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IModel getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IMonitor getMonitor() {
		return this.monitor;
	}

	@Override
	public Type getArtifactType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IScale getScale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObservable getSemantics(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObjectArtifact newObservation(IObservable observable, String name, IScale scale, IMetadata metadata)
			throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObjectArtifact newRelationship(IObservable observable, String name, IScale scale, IObjectArtifact source,
			IObjectArtifact target, IMetadata metadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObservable getTargetSemantics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTargetName() {
		// TODO Auto-generated method stub
		return "result";
	}

	@Override
	public IDirectObservation getSourceSubject(IRelationship relationship) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDirectObservation getTargetSubject(IRelationship relationship) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IObservation> getChildrenOf(IObservation observation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, IVariable> getVariables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context getExpressionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> K get(String name, Class<? extends K> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> K get(String name, K defaultValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(String key, Class<?> cls) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object put(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Object> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISubject getRootSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDirectObservation getContextSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IObservation getObservation(String observationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRuntimeScope createContext(IScale scale, IActuator target, IDataflow<?> dataflow, IResolutionScope scope,
			IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRuntimeScope createChild(IScale scale, IActuator target, IResolutionScope scope, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRuntimeScope createChild(IObservable indirectTarget) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setScale(IScale geometry) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void link(IArtifact parent, IArtifact child) {
		// TODO Auto-generated method stub

	}

	@Override
	public void replaceTarget(IArtifact self) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pair<String, IArtifact> findArtifact(IObservable observable) {
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
	public void removeArtifact(IArtifact object) {
		// TODO Auto-generated method stub

	}

	@Override
	public IConfiguration newConfiguration(IConcept configurationType, Collection<IObservation> targets,
			IMetadata metadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IState newNonsemanticState(String name, org.integratedmodelling.klab.api.provenance.IArtifact.Type type,
			IScale scale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDataflow<?> getDataflow() {
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

	@Override
	public IObservation getObservationGroup(IObservable observable, IScale scale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newPredicate(IDirectObservation target, IConcept predicate) {
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
	public IRuntimeScope locate(ILocator transitionScale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> Map<String, T> getLocalCatalog(Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IArtifact> getAdditionalOutputs() {
		// TODO Auto-generated method stub
		return null;
	}

	class DummyArtifact implements IArtifact {

		@Override
		public String getId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getTimestamp() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public Iterator<IArtifact> iterator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IGeometry getGeometry() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IMetadata getMetadata() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getUrn() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<IAnnotation> getAnnotations() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAgent getConsumer() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IAgent getOwner() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IActivity getGenerator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<IArtifact> getAntecedents() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<IArtifact> getConsequents() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IArtifact trace(IConcept concept) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<IArtifact> collect(IConcept concept) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IArtifact trace(IConcept role, IDirectObservation roleContext) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<IArtifact> getChildArtifacts() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int groupSize() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public IProvenance getProvenance() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Type getType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void release() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean is(Class<?> cls) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T as(Class<?> cls) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isArchetype() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public List<IActivity> getActions() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	@Override
	public Collection<IObservable> getDependents(IObservable observable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IObservable> getPrecursors(IObservable observable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IState addState(IDirectObservation target, IObservable observable, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <K> K getNotNull(String name, Class<? extends K> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getUnnamedKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> T resolve(IObservable observable, IDirectObservation context, ITaskTree<?> task,
			Mode mode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void scheduleActions(Observation observation, IBehavior behavior) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<IConcept, Pair<String, IKimExpression>> getBehaviorBindings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addListener(ObservationListener listener) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeListener(String listenerId) {
		// TODO Auto-generated method stub

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

}
