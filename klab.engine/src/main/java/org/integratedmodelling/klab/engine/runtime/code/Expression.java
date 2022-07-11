package org.integratedmodelling.klab.engine.runtime.code;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.IEventBus;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.IVariable;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

public abstract class Expression implements IExpression {

	// A dummy context to use when we don't have a context to pass
	public static class SimpleScope extends Parameters<String> implements IContextualizationScope {

		private IMonitor monitor;
		private INamespace namespace;
		private IScale scale;

		public SimpleScope(IMonitor monitor) {
			this.monitor = monitor;
		}

		SimpleScope(IMonitor monitor, INamespace namespace) {
			this.monitor = monitor;
			this.namespace = namespace;
		}

		SimpleScope(IGeometry geometry, IMonitor monitor) {
			this.monitor = monitor;
			this.scale = geometry instanceof IScale ? (IScale) geometry : Scale.create(geometry);
		}

		@Override
		public INamespace getNamespace() {
			return this.namespace;
		}

		@Override
		public IProvenance getProvenance() {
			return null;
		}

		@Override
		public IEventBus getEventBus() {
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
		public IArtifact getArtifact(String localName) {
			return null;
		}

		@Override
		public IMonitor getMonitor() {
			return monitor;
		}

		@Override
		public IObjectArtifact newObservation(IObservable observable, String name, IScale scale, IMetadata metadata) {
			return null;
		}

		@Override
		public IObjectArtifact newRelationship(IObservable observable, String name, IScale scale,
				IObjectArtifact source, IObjectArtifact target, IMetadata metadata) {
			return null;
		}

		@Override
		public Type getArtifactType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends IArtifact> Collection<Pair<String, T>> getArtifacts(Class<T> type) {
			return new ArrayList<>();
		}

		@Override
		public IScale getScale() {
			return scale;
		}

		@Override
		public IObservable getSemantics(String identifier) {
			return null;
		}

		@Override
		public IArtifact getTargetArtifact() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends IArtifact> T getArtifact(String localName, Class<T> cls) {
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
			return "target";
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
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<IObservation> getChildrenOf(IObservation observation) {
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
		public IModel getModel() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<IArtifact> getArtifact(IConcept observable) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public org.integratedmodelling.klab.api.data.general.IExpression.Scope getExpressionContext(IObservable targetObservable) {
	        return ExpressionScope.empty(Klab.INSTANCE.getRootMonitor());
		}

		@Override
		public <T extends IArtifact> T getArtifact(IConcept concept, Class<T> cls) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IDataflow<?> getDataflow() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, IVariable> getVariables() {
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
		public IObservation getParentArtifactOf(IObservation observation) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<IObservation> getObservations(IConcept observable) {
			// TODO Auto-generated method stub
			return null;
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
		public IConcept localizePredicate(IConcept predicate) {
			// TODO Auto-generated method stub
			return predicate;
		}

        @Override
        public <T extends IArtifact> Collection<T> getArtifacts(IConcept concept, Class<T> cls) {
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
        public IScale getResolutionScale() {
            // TODO Auto-generated method stub
            return scale;
        }

        @Override
        public void notifyInspector(Object... triggerArguments) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public Scope getExpressionContext() {
            return ExpressionScope.empty(Klab.INSTANCE.getRootMonitor());
        }

	}

	public static IContextualizationScope emptyContext(IMonitor monitor) {
		return new SimpleScope(monitor);
	}

	public static IContextualizationScope emptyContext(IMonitor monitor, INamespace namespace) {
		return new SimpleScope(monitor, namespace);
	}

	public static IContextualizationScope emptyContext(IGeometry geometry, IMonitor monitor) {
		return new SimpleScope(geometry, monitor);
	}
}
