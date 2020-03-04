package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubjectiveObservation;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Spawn;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.api.IModificationListener;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Path;

import akka.actor.typed.ActorRef;

/**
 * The base class for all observations can only be instantiated as the empty
 * observation.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class Observation extends ObservedArtifact implements IObservation, IActorIdentity<KlabMessage> {

	private Observable observable;
	private ObservationGroup group = null;
	// last modification. Must be correct for any cache to work.
	private long timestamp = System.currentTimeMillis();
	// used to store the "main" status from annotations or because of directly
	// observed. Should eventually
	// come from provenance.
	private boolean main;
	// only kept updated in the root observation
	private long lastUpdate;
	// separately kept time of creation and exit, using timestamp if non-temporal
	private long creationTime;
	private long exitTime;

	/*
	 * Any modification that needs to be reported to clients is recorded here
	 */
	protected List<ObservationChange> modificationsToReport = new ArrayList<>();
	protected List<IModificationListener> modificationListeners = new ArrayList<>();
	private ActorRef<KlabMessage> actor;

	// tracks the setting of the actor so we can avoid the ask pattern
	private AtomicBoolean actorSet = new AtomicBoolean(Boolean.FALSE);

	public String getUrn() {
		return "local:observation:" + getParentIdentity(Session.class).getId() + ":" + getId();
	}

	public static IObservation empty(IObservable observable, IContextualizationScope context) {
		return ((IRuntimeScope) context).getObservationGroup(observable, context.getScale());
	}

	protected Observation(Observable observable, Scale scale, IRuntimeScope context) {
		super(scale, context);
		this.observable = observable;
		this.setCreationTime(/* context.getScheduler() != null ? context.getScheduler().getTime() : */ timestamp);
		this.setExitTime(-1);
	}

	protected void reportChange(ObservationChange change) {
		this.modificationsToReport.add(change);
	}

	protected void touch() {
		this.timestamp = System.currentTimeMillis();
	}

	public long getTimestamp() {
		return timestamp;
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
		return (Monitor) getRuntimeScope().getMonitor();
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
		return getRuntimeScope().getProvenance();
	}

	public void setObservable(Observable observable) {
		this.observable = observable;
	}

	public Namespace getNamespace() {
		return (Namespace) getRuntimeScope().getNamespace();
	}

	@Override
	public DirectObservation getContext() {
		return (DirectObservation) getRuntimeScope().getParentOf(this);
	}

	public String toString() {
		return "{" + Path.getLast(this.getClass().getCanonicalName(), '.') + " " + getId() + ": " + getObservable()
				+ "}";
	}

	void setGroup(ObservationGroup group) {
		this.group = group;
	}

	/**
	 * Get the group this is part of, if any.
	 * 
	 * @return
	 */
	public ObservationGroup getGroup() {
		return group;
	}

	@Override
	public IArtifact.Type getType() {
		return observable.getArtifactType();
	}

	@Override
	public Iterator<IArtifact> iterator() {
		if (group == null) {
			List<IArtifact> list = new ArrayList<>();
			list.add(this);
			return list.iterator();
		}
		return group.iterator();
	}

	@Override
	public int groupSize() {
		return group == null ? 1 : group.groupSize();
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public ISubjectiveObservation reinterpret(IDirectObservation observer) {
		throw new IllegalStateException("reinterpret() was called on an illegal or unsupported type");
	}

	@Override
	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public long getExitTime() {
		return exitTime;
	}

	public void setExitTime(long exitTime) {
		this.exitTime = exitTime;
	}

	/**
	 * Get any modification that still needs to be reported and reset our list to
	 * empty.
	 * 
	 * @return
	 */
	public List<ObservationChange> getChangesAndReset() {
		List<ObservationChange> ret = this.modificationsToReport;
		this.modificationsToReport = new ArrayList<>();
		return ret;
	}

	public void addModificationListener(IModificationListener listener) {
		this.modificationListeners.add(listener);
	}

	public void evaluateChanges() {
		// does nothing here; overridden in each final class
	}

	@Override
	public ActorRef<KlabMessage> getActor() {

		IActorIdentity<KlabMessage> parent = null;

		if (this.actor == null) {
			if (this.getRuntimeScope().getParentOf(this) == null) {
				// I'm the context: get our actor from the session
				parent = getParentIdentity(Session.class);
			} else {
				parent = (Observation) getRuntimeScope().getRootSubject();
			}

			final ActorRef<KlabMessage> parentActor = parent.getActor();

			parentActor.tell(new Spawn(this));

			/*
			 * wait for instrumentation to succeed. Couldn't figure out the ask pattern. TODO when this
			 * has a chance to fail (e.g. in a cluster situation), add a timeout, or figure
			 * out the ask pattern.
			 */
			while (!this.actorSet.get()) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
		return this.actor;
	}

	@Override
	public void load(IBehavior behavior) {
		getActor().tell(new Load(behavior));
	}

	@Override
	public void instrument(ActorRef<KlabMessage> actor) {
		this.actor = actor;
		this.actorSet.set(Boolean.TRUE);
	}

	public boolean isAlive() {
		// TODO check if terminated (use ActorSelection apparently).
		return this.actor != null;
	}
}
