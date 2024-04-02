package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObserver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.ActorReference;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Spawn;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.debugger.Debugger;
import org.integratedmodelling.klab.engine.debugger.Debugger.Watcher;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IModificationListener;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.ITaskTree;
import org.integratedmodelling.klab.exceptions.KlabActorException;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.Utils;

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
	// used to store the "main" status from annotations or because of directly
	// observed. Should eventually
	// come from provenance.
	private boolean main;
	// only kept updated in the root observation; in others, 0 is expected default
	// and
	// the implementations redefine getLastUpdate() to report correctly
	private long lastUpdate = 0;
	// separately kept time of creation and exit, using timestamp if non-temporal
	private long creationTime;
	private long exitTime;
	private boolean dynamic;
	private String observationContextId;
	// just for clients
	private boolean contextualized;
	private View view;
	private Set<String> resourcesUsed = new HashSet<>();

	// /*
	// * If this is not null, the observation is being recontextualized by a
	// dataflow
	// * successive to the one that produced its change, and the getLastUpdate()
	// * method should respond a time relative to that instead of the very last one.
	// *
	// * FIXME this is a potentially dangerous approach but using a wrapper destroys
	// * the referential integrity of the context. Should externalize this info
	// within
	// * the scheduler.
	// */
	// protected ITime replayingTime = null;

	/*
	 * Any modification that needs to be reported to clients is recorded here
	 */
	protected List<ObservationChange> changeset = new ArrayList<>();
	protected List<IModificationListener> modificationListeners = new ArrayList<>();
	private ActorRef<KlabMessage> actor;
	// actor-scoped state, manipulated using "set" statements.
	private IParameters<String> globalState = Parameters.createSynchronized();
	protected List<Long> updateTimestamps = new ArrayList<>();

	// tracks the setting of the actor so we can avoid the ask pattern
	private AtomicBoolean actorSet = new AtomicBoolean(Boolean.FALSE);
	protected IObserver observer = null;

	/*
	 * these are for debugging. Watches in the root observation monitor the entire
	 * context.
	 */
	protected Map<String, Debugger.Watcher> watches = new HashMap<>();

	protected Observation(Observation other) {
		super(other);
		this.observable = other.observable;
		this.group = other.group;
		this.main = other.main;
		this.lastUpdate = other.lastUpdate;
		this.creationTime = other.creationTime;
		this.exitTime = other.exitTime;
		this.dynamic = other.dynamic;
		this.observationContextId = other.observationContextId;
		this.contextualized = other.contextualized;
		this.view = other.view;
		this.changeset.addAll(other.changeset);
		this.modificationListeners.addAll(other.modificationListeners);
		this.actor = other.actor;
		this.globalState.putAll(other.globalState);
		this.actorSet = other.actorSet;
		this.observer = other.observer;
	}

	public String getUrn() {
		return "local:observation:" + getParentIdentity(Session.class).getId() + ":" + getId();
	}

	public IObserver<?> getObserver() {
		return this.observer;
	}

	public void setObserver(IObserver<?> observer) {
		this.observer = observer;
	}

	public Collection<Watcher> getWatches() {
		return watches.values();
	}

	public String addWatch(Watcher watch) {
		String id = NameGenerator.shortUUID();
		this.watches.put(id, watch);
		return id;
	}

	public IIdentity.Type getIdentityType() {
		return IIdentity.Type.OBSERVATION;
	}

	public void removeWatch(String id) {
		this.watches.remove(id);
	}

	public static IObservation empty(IObservable observable, IContextualizationScope context) {
		return ((IRuntimeScope) context).getObservationGroup(observable, context.getScale());
	}

	protected Observation(Observable observable, Scale scale, IRuntimeScope scope) {
		super(scale, scope);
		this.observable = observable;
		this.observer = scope.getObserver();
		ITaskTree<?> creator = scope.getMonitor().getIdentity().getParentIdentity(ITaskTree.class);
		if (creator != null) {
			this.observationContextId = creator.getContextId();
		}
		this.setCreationTime(/*
								 * context.getScheduler() != null ? context.getScheduler().getTime() :
								 */ timestamp);
		this.setExitTime(-1);
	}

	protected void reportChange(ObservationChange change) {
		this.changeset.add(change);
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
		return (Monitor) getScope().getMonitor();
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
		return getScope().getProvenance();
	}

	public void setObservable(Observable observable) {
		this.observable = observable;
	}

	public Namespace getNamespace() {
		return (Namespace) getScope().getNamespace();
	}

	@Override
	public DirectObservation getContext() {
		return (DirectObservation) getScope().getParentOf(this);
	}

	@Override
	public DirectObservation getRootContext() {
		return (DirectObservation) getScope().getRootSubject();
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
		if (group == null || !(this instanceof ObservationGroup)) {
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

	@Override
	public IArtifact getGroupMember(int n) {
		return group == null ? null : (group.groupSize() > n ? group.getGroupMember(n) : null);
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	@Override
	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	// @Override
	// public ISubjectiveObservation reinterpret(IDirectObservation observer) {
	// throw new IllegalStateException("reinterpret() was called on an illegal or
	// unsupported
	// type");
	// }

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
		List<ObservationChange> ret = this.changeset;
		this.changeset = new ArrayList<>();
		return ret;
	}

	public void addModificationListener(IModificationListener listener) {
		this.modificationListeners.add(listener);
	}

	public Collection<IModificationListener> getModificationListeners() {
		return this.modificationListeners;
	}

	public void evaluateChanges() {
		// does nothing here; overridden in each final class
	}

	@Override
	public Reference getActor() {

		IActorIdentity<KlabMessage> parent = null;

		if (this.actor == null) {
			if (this.getScope().getParentOf(this) == null) {
				// I'm the context: get our actor from the session
				parent = getParentIdentity(Session.class);
			} else {
				parent = (Observation) getScope().getRootSubject();
			}

			// use the runtime actor, which is not running any actor code
			final ActorRef<KlabMessage> parentActor = ((ActorReference) parent.getActor()).actor;

			parentActor.tell(new Spawn(this, null));

			/*
			 * wait for instrumentation to succeed. Couldn't figure out the ask pattern.
			 * TODO when this has a chance to fail (e.g. in a cluster situation), add a
			 * timeout, or figure out the ask pattern.
			 */
			long time = System.currentTimeMillis();
			long timeout = 10000;
			while (!this.actorSet.get()) {
				try {
					Thread.sleep(50);
					if ((System.currentTimeMillis() - time) > timeout) {
						throw new KlabActorException(
								"internal error in actor system: timeout obtaining peer actor for " + this);
					}
				} catch (InterruptedException e) {
					break;
				}
			}
		}
		return new KlabActor.ActorReference(actor);
	}

	@Override
	public String load(IBehavior behavior, IContextualizationScope scope) {
		String behaviorId = "obh" + NameGenerator.shortUUID();
		getActor().tell(new Load(this, behavior.getId(), behaviorId, (IRuntimeScope) scope));
		return behaviorId;
	}

	@Override
	public boolean stop() {
		return true;
	}

	@Override
	public boolean stop(String appId) {
		return true;
	}

	@Override
	public void instrument(Reference actor) {
		this.actor = ((ActorReference) actor).actor;
		this.actorSet.set(Boolean.TRUE);
	}

	public boolean isAlive() {
		// TODO check if terminated (use ActorSelection apparently).
		return this.actor != null;
	}

	// @Override
	// public <V> V getState(String key, Class<V> cls) {
	// return this.globalState.get(key, cls);
	// }
	//
	// @Override
	// public void setState(String key, Object value) {
	// this.globalState.put(key, value);
	// for (BiConsumer<String, Object> listener : stateChangeListeners.values()) {
	// listener.accept(key, value);
	// }
	// }

	public List<ObservationChange> getChangeset() {
		return changeset;
	}

	public ObservationChange createChangeEvent(ObservationChange.Type type) {
		ObservationChange change = new ObservationChange();
		change.setContextId(getScope().getRootSubject().getId());
		change.setId(this.getId());
		change.setType(type);
		return change;
	}

	public ObservationChange requireStructureChangeEvent() {
		for (ObservationChange change : getChangeset()) {
			if (change.getType() == ObservationChange.Type.StructureChange) {
				return change;
			}
		}
		ObservationChange change = createChangeEvent(ObservationChange.Type.StructureChange);
		getChangeset().add(change);
		return change;
	}

	@Override
	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}

	public String getObservationContextId() {
		return observationContextId;
	}

	public boolean isContextualized() {
		return contextualized;
	}

	public void setContextualized(boolean contextualized) {
		this.contextualized = contextualized;
	}

	public void finalizeTransition(IScale scale) {
		// do nothing here
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public void setView(View layout) {
		this.view = layout;
	}

	@Override
	public long[] getUpdateTimestamps() {
		return Utils.toLongArray(updateTimestamps);
	}

	@Override
	public IParameters<String> getState() {
		return globalState;
	}

	/**
	 * Debug output with as much detail as possible on the internal structure.
	 * 
	 * @return
	 */
	public abstract String dump();

	@Override
	public IObservation at(ILocator locator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getProperty(String property) {
		switch (property) {
		case "displayLabel":
			return Observations.INSTANCE.getDisplayLabel(this);
		}
		return super.getProperty(property);
	}

	public void includeResource(String string) {
		this.resourcesUsed.add(string);
	}

	public boolean includesResource(String string) {
		return this.resourcesUsed.contains(string);
	}

}
