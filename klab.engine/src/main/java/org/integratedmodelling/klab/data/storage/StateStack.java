package org.integratedmodelling.klab.data.storage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;

/**
 * A substitute for a IState that can produce analogous states of different
 * types to store intermediate results. The stack can optionally made available
 * to the runtime as an artifact chain for debugging.
 * 
 * @author Ferd
 *
 */
public class StateStack implements IState {

	IState delegate;
	Map<IArtifact.Type, IState> stack = new HashMap<>();

	public StateStack(IState state, IArtifact.Type type) {
		this.delegate = state;
		stack.put(type, state);
	}

	public static IState get(IState state, IArtifact.Type type, IComputationContext context) {

		if (state.getType() == type) {
			return state;
		}

		if (state instanceof StateStack) {
			return ((StateStack) state).getOrCreate(type, context);
		}
		return new StateStack(state, type);
	}

	private IState getOrCreate(IArtifact.Type type, IComputationContext context) {

		IState ret = stack.get(type);
		if (ret == null) {
			IConcept concept = OWL.INSTANCE
					.getNonsemanticPeer(delegate.getObservable().getLocalName() + "_" + type.name(), type);
			IObservable observable = Observable.promote(concept);
			ret = Klab.INSTANCE.getRuntimeProvider().createState(observable, type, delegate.getScale(), context);
			stack.put(type, ret);
		}
		return ret;
	}

	public Iterator<IArtifact> iterator() {
		return delegate.iterator();
	}

	public IMonitor getMonitor() {
		return delegate.getMonitor();
	}

	public IEngineSessionIdentity getParentIdentity() {
		return delegate.getParentIdentity();
	}

	public Optional<ISubject> getObserver() {
		return delegate.getObserver();
	}

	public boolean isConstant() {
		return delegate.isConstant();
	}

	public boolean isDynamic() {
		return delegate.isDynamic();
	}

	public IState as(IObservable observable) {
		return delegate.as(observable);
	}

	public Object get(ILocator index) {
		return delegate.get(index);
	}

	public IObservable getObservable() {
		return delegate.getObservable();
	}

	public IScale getScale() {
		return delegate.getScale();
	}

	public <T> T get(ILocator index, Class<T> cls) {
		return delegate.get(index, cls);
	}

	public IGeometry getGeometry() {
		return delegate.getGeometry();
	}

	public long getTimestamp() {
		return delegate.getTimestamp();
	}

	public IDirectObservation getContext() {
		return delegate.getContext();
	}

	public IMetadata getMetadata() {
		return delegate.getMetadata();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public String getUrn() {
		return delegate.getUrn();
	}

	public IAgent getConsumer() {
		return delegate.getConsumer();
	}

	public long set(ILocator index, Object value) {
		return delegate.set(index, value);
	}

	public boolean isSpatiallyDistributed() {
		return delegate.isSpatiallyDistributed();
	}

	public IAgent getOwner() {
		return delegate.getOwner();
	}

	public boolean isTemporallyDistributed() {
		return delegate.isTemporallyDistributed();
	}

	public Collection<IArtifact> getAntecedents() {
		return delegate.getAntecedents();
	}

	public boolean isTemporal() {
		return delegate.isTemporal();
	}

	public Collection<IArtifact> getConsequents() {
		return delegate.getConsequents();
	}

	public boolean isSpatial() {
		return delegate.isSpatial();
	}

	public IArtifact trace(IConcept concept) {
		return delegate.trace(concept);
	}

	public ISpace getSpace() {
		return delegate.getSpace();
	}

	public long size() {
		return delegate.size();
	}

	public Collection<IArtifact> collect(IConcept concept) {
		return delegate.collect(concept);
	}

	public IArtifact trace(IConcept role, IDirectObservation roleContext) {
		return delegate.trace(role, roleContext);
	}

	public Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext) {
		return delegate.collect(role, roleContext);
	}

	public String getId() {
		return delegate.getId();
	}

	public int groupSize() {
		return delegate.groupSize();
	}

	public IProvenance getProvenance() {
		return delegate.getProvenance();
	}

	public boolean is(IIdentity.Type type) {
		return delegate.is(type);
	}

	public <T extends IIdentity> T getParentIdentity(Class<T> type) {
		return delegate.getParentIdentity(type);
	}

	@Override
	public IArtifact.Type getType() {
		return delegate.getType();
	}

}
