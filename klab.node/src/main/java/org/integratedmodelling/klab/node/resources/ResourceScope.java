package org.integratedmodelling.klab.node.resources;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression.Scope;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.model.IAnnotation;
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
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IEventBus;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.IVariable;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Actuator.Status;
import org.integratedmodelling.klab.engine.runtime.ActivityBuilder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.engine.runtime.code.ExpressionScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.provenance.Provenance;
import org.integratedmodelling.klab.resolution.DependencyGraph;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * The scope to contextualize a resource outside of a semantic environment. TODO
 * this will need to be fleshed out to contextualize resources that contain
 * dataflows.
 * 
 * @author Ferd
 *
 */
public class ResourceScope extends Parameters<String> implements IRuntimeScope {

	IMonitor monitor;
	IScale scale = null;
	IResource resource;

	/**
	 * Use when called from a JSON post request without additional inputs. May build
	 * dummy artifacts for any additional outputs.
	 * 
	 * @param resource
	 * @param geometry
	 */
	public ResourceScope(IResource resource, IGeometry geometry, IMonitor monitor) {
		this.monitor = monitor;
		if (geometry != null) {
			this.scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);
		}
		this.resource = resource;
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
		return null; // resource.getType();
	}

	@Override
	public IScale getScale() {
		return scale;
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
	public ExpressionScope getExpressionContext(IObservable targetObservable) {
		return ExpressionScope.empty(Klab.INSTANCE.getRootMonitor());
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
	public IRuntimeScope createChild(IScale scale, IActuator target, IResolutionScope scope, IMonitor monitor, IPattern pattern) {
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
	public IRuntimeScope copy() {
		// TODO Auto-generated method stub
		return null;
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
	public Provenance getProvenance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifact.Structure getStructure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<String, IArtifact> findArtifact(IObservable observable) {
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
	public IConfiguration newConfiguration(IConcept configurationType, IPattern pattern, IMetadata metadata) {
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
	public Collection<IArtifact> getChildArtifactsOf(IArtifact directObservation) {
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
	public IRuntimeScope locate(ILocator transitionScale, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> Map<String, T> getLocalCatalog(Class<T> cls) {
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
		public ValuePresentation getValuePresentation() {
			return ValuePresentation.VALUE;
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

		// @Override
		// public IAgent getConsumer() {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public IAgent getOwner() {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public IActivity getGenerator() {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public Collection<IArtifact> getAntecedents() {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public Collection<IArtifact> getConsequents() {
		// // TODO Auto-generated method stub
		// return null;
		// }
		//
		// @Override
		// public IArtifact trace(IConcept concept) {
		// // TODO Auto-generated method stub
		// return null;
		// }

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
		public long getLastUpdate() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasChangedDuring(ITime time) {
			// TODO Auto-generated method stub
			return false;
		}

	}

	@Override
	public IState addState(IDirectObservation target, IObservable observable, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IArtifact> T resolve(IObservable observable, IDirectObservation context, ITaskTree<?> task,
			Mode mode, IActuator dataflow) {
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
	public void swapArtifact(IArtifact ret, IArtifact result) {
		// TODO Auto-generated method stub

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

//    @Override
//    public IParameters<String> localize(ILocator locator) {
//        // TODO Auto-generated method stub
//        return this;
//    }

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

	@Override
	public <T extends IArtifact> Collection<T> getArtifacts(IConcept concept, Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
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
	public IRuntimeScope createContext(IScale scale, IActuator target, IDataflow<?> dataflow, IResolutionScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRuntimeScope getContextScope(Actuator actuator, IResolutionScope scope, IScale scale, IDataflow<?> dataflow,
			IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

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
	public IObservation getObservation(IObservable observable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Scope getExpressionContext() {
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
        return null;
    }

}
