package org.integratedmodelling.klab.data.storage;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
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
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The state we wrap has the desired semantics but its values must be converted.
 * 
 * @author Ferd
 *
 */
public class MediatingState implements IState {

	IState delegate;
	IValueMediator from;
	IValueMediator to;
	
	public MediatingState(IState state, IValueMediator from, IValueMediator to) {
	  // FIXME this gets an already mediated state!
		this.delegate = state;
		this.from = from;
		this.to = to;
	}
	
	public Object get(ILocator index) {
		Object val = delegate.get(index);
		return val instanceof Number ? to.convert(((Number) val).doubleValue(), from) : val;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(ILocator index, Class<T> cls) {
		Object val = delegate.get(index, cls);
		return val instanceof Number && (Number.class.isAssignableFrom(cls))
				? (T) to.convert(((Number) val).doubleValue(), from)
				: (T) val;
	}

	public long set(ILocator index, Object value) {
		Object val = value instanceof Number ? from.convert(((Number)value).doubleValue(), to) : value;
		return delegate.set(index, val);
	}

	// Remaining functionality is delegated to original state

	public IObservable getObservable() {
		return delegate.getObservable();
	}

	public IEngineSessionIdentity getParentIdentity() {
		return delegate.getParentIdentity();
	}

	public IMonitor getMonitor() {
		return delegate.getMonitor();
	}

	public boolean isConstant() {
		return delegate.isConstant();
	}

	public boolean isDynamic() {
		return delegate.isDynamic();
	}

	public Optional<ISubject> getObserver() {
		return delegate.getObserver();
	}

	public IState as(IObservable observable) {
		return delegate.as(observable);
	}

	public IScale getScale() {
		return delegate.getScale();
	}

	public IDirectObservation getContext() {
		return delegate.getContext();
	}

	public IGeometry getGeometry() {
		return delegate.getGeometry();
	}

	public IMetadata getMetadata() {
		return delegate.getMetadata();
	}

	public String getUrn() {
		return delegate.getUrn();
	}

	public boolean isSpatiallyDistributed() {
		return delegate.isSpatiallyDistributed();
	}

	public IAgent getConsumer() {
		return delegate.getConsumer();
	}

	public String getToken() {
		return delegate.getToken();
	}

	public IAgent getOwner() {
		return delegate.getOwner();
	}

	public Collection<IArtifact> getAntecedents() {
		return delegate.getAntecedents();
	}

	public boolean isTemporallyDistributed() {
		return delegate.isTemporallyDistributed();
	}

	public Collection<IArtifact> getConsequents() {
		return delegate.getConsequents();
	}

	public IArtifact trace(IConcept concept) {
		return delegate.trace(concept);
	}

	public boolean isTemporal() {
		return delegate.isTemporal();
	}

	public boolean isSpatial() {
		return delegate.isSpatial();
	}

	public Collection<IArtifact> collect(IConcept concept) {
		return delegate.collect(concept);
	}

	public boolean is(Type type) {
		return delegate.is(type);
	}

	public ISpace getSpace() {
		return delegate.getSpace();
	}

	public long size() {
		return delegate.size();
	}

	public <T extends IIdentity> T getParentIdentity(Class<T> type) {
		return delegate.getParentIdentity(type);
	}

	public IArtifact trace(IConcept role, IDirectObservation roleContext) {
		return delegate.trace(role, roleContext);
	}

	public long getTimestamp() {
		return delegate.getTimestamp();
	}

	public Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext) {
		return delegate.collect(role, roleContext);
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public int groupSize() {
		return delegate.groupSize();
	}

	public IProvenance getProvenance() {
		return delegate.getProvenance();
	}

	public Iterator<IArtifact> iterator() {
		return delegate.iterator();
	}

}
