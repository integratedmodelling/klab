package org.integratedmodelling.klab.components.runtime.observations;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.ISubjectiveState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IAgent;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;

/**
 * This one wraps an existing state into a subjective set of 1 or more,
 * duplicating the state each time another of the observers is selected. Should
 * be created with an empty (not yet filled in) state, whose observable will be
 * modified by adding the observer and replicated to the other states in the
 * set.
 * 
 * @author ferdinando.villa
 *
 */
public class SubjectiveState implements ISubjectiveState {

	IState current;
	Map<String, IState> cache = new HashMap<>();

	transient IDirectObservation observer;

	@Override
	public void setObserver(IDirectObservation observer) {
		if (!observer.equals(this.observer)) {
			IState state = cache.get(observer.getId());
			if (state == null) {
				Klab.INSTANCE.getRuntimeProvider().createState(
						((Observable) current.getObservable()).subjectify(observer), current.getType(),
						current.getScale(), ((Observation) current).getRuntimeContext());
			}
			this.current = state;
			this.observer = observer;
		}
	}

	public SubjectiveState(IState state, IDirectObservation observer) {
		this.current = state;
		this.observer = observer;
		this.cache.put(this.observer.getId(), state);
	}

	public Iterator<IArtifact> iterator() {
		return current.iterator();
	}

	public IMonitor getMonitor() {
		return current.getMonitor();
	}

	public IEngineSessionIdentity getParentIdentity() {
		return current.getParentIdentity();
	}

	public Optional<ISubject> getObserver() {
		return current.getObserver();
	}

	public boolean isConstant() {
		return current.isConstant();
	}

	public IState as(IArtifact.Type type) {
		return current.as(type);
	}

	public Object get(ILocator index) {
		return current.get(index);
	}

	public IObservable getObservable() {
		return current.getObservable();
	}

	public IScale getScale() {
		return current.getScale();
	}

	public <T> Iterator<T> iterator(ILocator index, Class<? extends T> cls) {
		return current.iterator(index, cls);
	}

	public <T> T get(ILocator index, Class<T> cls) {
		return current.get(index, cls);
	}

	public IState in(IValueMediator mediator) {
		return current.in(mediator);
	}

	public IState at(ILocator locator) {
		return current.at(locator);
	}

	public ITable<Number> getTable() {
		return current.getTable();
	}

	public IDirectObservation getContext() {
		return current.getContext();
	}

	public long set(ILocator index, Object value) {
		return current.set(index, value);
	}

	public ISubjectiveState reinterpret(IDirectObservation observer) {
		setObserver(observer);
		return this;
	}

	public boolean isSpatiallyDistributed() {
		return current.isSpatiallyDistributed();
	}

	public boolean isTemporallyDistributed() {
		return current.isTemporallyDistributed();
	}

	public boolean isTemporal() {
		return current.isTemporal();
	}

	public boolean isSpatial() {
		return current.isSpatial();
	}

	public long size() {
		return current.size();
	}

	public ISpace getSpace() {
		return current.getSpace();
	}

	public IDataKey getDataKey() {
		return current.getDataKey();
	}

	public long getTimestamp() {
		return current.getTimestamp();
	}

	public String getId() {
		return current.getId();
	}

	public IGeometry getGeometry() {
		return current.getGeometry();
	}

	public boolean isEmpty() {
		return current.isEmpty();
	}

	public IMetadata getMetadata() {
		return current.getMetadata();
	}

	public String getUrn() {
		return current.getUrn();
	}

	public Collection<IAnnotation> getAnnotations() {
		return current.getAnnotations();
	}

	public boolean is(org.integratedmodelling.klab.api.auth.IIdentity.Type type) {
		return current.is(type);
	}

	public <T extends IIdentity> T getParentIdentity(Class<T> type) {
		return current.getParentIdentity(type);
	}

	public IAgent getConsumer() {
		return current.getConsumer();
	}

	public IAgent getOwner() {
		return current.getOwner();
	}

	public IActivity getGenerator() {
		return current.getGenerator();
	}

	public Collection<IArtifact> getAntecedents() {
		return current.getAntecedents();
	}

	public Collection<IArtifact> getConsequents() {
		return current.getConsequents();
	}

	public IArtifact trace(IConcept concept) {
		return current.trace(concept);
	}

	public Collection<IArtifact> collect(IConcept concept) {
		return current.collect(concept);
	}

	public IArtifact trace(IConcept role, IDirectObservation roleContext) {
		return current.trace(role, roleContext);
	}

	public Collection<IArtifact> collect(IConcept role, IDirectObservation roleContext) {
		return current.collect(role, roleContext);
	}

	public int groupSize() {
		return current.groupSize();
	}

	public IProvenance getProvenance() {
		return current.getProvenance();
	}

	public IArtifact.Type getType() {
		return current.getType();
	}

	public void release() {
		current.release();
	}

    @Override
    public <T> T aggregate(IGeometry geometry, Class<? extends T> cls) {
        return current.aggregate(geometry, cls);
    }

}
