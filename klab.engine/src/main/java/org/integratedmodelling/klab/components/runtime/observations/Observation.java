package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Path;

/**
 * The base class for all observations can only be instantiated as the empty
 * observation.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class Observation extends ObservedArtifact implements IObservation {

	private Observable observable;
	private Subject observer;
	private ObservationGroup group = null;

	public String getUrn() {
		return "local:observation:" + getParentIdentity(Session.class).getId() + ":" + getId();
	}

	public static IObservation empty(IObservable observable, IComputationContext context) {
		return new ObservationGroup((Observable) observable, (Scale) context.getScale(), (IRuntimeContext) context,
				observable.getArtifactType());
	}

	protected Observation(Observable observable, Scale scale, IRuntimeContext context) {
		super(scale, context);
		this.observable = observable;
	}

	@Override
	public Optional<ISubject> getObserver() {
		return observer == null ? Optional.empty() : Optional.of(observer);
	}

	@Override
	public Observable getObservable() {
		return observable;
	}

	@Override
	public Scale getScale() {
		return (Scale) getGeometry();
	}

	@Override
	public boolean isSpatiallyDistributed() {
		return getScale().isSpatiallyDistributed();
	}

	@Override
	public boolean isTemporallyDistributed() {
		return getScale().isTemporallyDistributed();
	}

	@Override
	public boolean isTemporal() {
		return getScale().getTime() != null;
	}

	@Override
	public boolean isSpatial() {
		return getScale().getSpace() != null;
	}

	@Override
	public ISpace getSpace() {
		return getScale().getSpace();
	}

	@Override
	public IEngineSessionIdentity getParentIdentity() {
		return getMonitor().getIdentity().getParentIdentity(IEngineSessionIdentity.class);
	}

	@Override
	public Monitor getMonitor() {
		return (Monitor) getRuntimeContext().getMonitor();
	}

	@Override
	public String getId() {
		return super.getId();
	}

	@Override
	public boolean is(IIdentity.Type type) {
		return type == IIdentity.Type.OBSERVATION;
	}

	@Override
	public <T extends IIdentity> T getParentIdentity(Class<T> type) {
		return IIdentity.findParent(this, type);
	}

	@Override
	public IProvenance getProvenance() {
		return getRuntimeContext().getProvenance();
	}

	public void setObservable(Observable observable) {
		this.observable = observable;
	}

	public void setObserver(Subject observer) {
		this.observer = observer;
	}

	public Namespace getNamespace() {
		return (Namespace) getRuntimeContext().getNamespace();
	}

	@Override
	public DirectObservation getContext() {
		return (DirectObservation) getRuntimeContext().getParentOf(this);
	}

	public String toString() {
		return "{" + Path.getLast(this.getClass().getCanonicalName(), '.') + " " + getId() + ": " + getObservable()
				+ "}";
	}

	void setGroup(ObservationGroup group) {
		this.group = group;
	}

	@Override
	public IArtifact.Type getType() {
		return observable.getArtifactType();
	}

	@Override
	public Iterator<IArtifact> iterator() {
		return group == null ? new ArrayList<IArtifact>().iterator() : group.iterator();
	}

	@Override
	public int groupSize() {
		return group == null ? 1 : group.groupSize();
	}
	
	

}
