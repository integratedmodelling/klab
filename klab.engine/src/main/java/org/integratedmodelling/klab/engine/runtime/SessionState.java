package org.integratedmodelling.klab.engine.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.model.KimQuantity;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.ISessionState;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.geocoding.Geocoder;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.UserAction;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.LoadApplicationRequest;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.rest.SettingChangeRequest;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.ibm.icu.text.NumberFormat;

/**
 * Recoverable session state. A generic map with specific fields and
 * save/restore methods. Also manages observation history and queue, scenarios,
 * geometry of interest in time and space, and application state and views.
 * <p>
 * TODO create also a WorkspaceState object that handles workspace modification
 * requests. That could be driven by a set of desktop-only actor messages to
 * enable interactive coding.
 * 
 * @author Ferd
 *
 */
public class SessionState extends Parameters<String> implements ISessionState {

	/*
	 * these variable names trigger special handling and scale setting
	 */
	public final static String GEOCODING_STRATEGY_KEY = "geocodingstrategy";
	public final static String LOCK_SPACE_KEY = "lockspace";
	public final static String LOCK_TIME_KEY = "locktime";
	public final static String SPACE_RESOLUTION_UNIT_KEY = "spaceresolutionunit";
	public final static String SPACE_RESOLUTION_MULTIPLIER_KEY = "spacemultiplier";
	public final static String SPACE_RESOLUTION_KEY = "spaceresolution";
	public final static String TIME_START_KEY = "timestart";
	public final static String TIME_END_KEY = "timeend";
	public final static String TIME_TYPE_KEY = "timetype";
	public final static String TIME_START_YEAR_KEY = "startyear";
	public final static String TIME_END_YEAR_KEY = "endyear";
	public final static String TIME_YEAR_KEY = "year";
	public final static String TIME_RESOLUTION_KEY = "timeresolution";
	public final static String TIME_RESOLUTION_MULTIPLIER_KEY = "timemultiplier";
	public final static String TIME_RESOLUTION_UNIT_KEY = "timeunit";

	private Session session;
	private List<SessionActivity> history = new ArrayList<>();
	long startTime = System.currentTimeMillis();
	private Set<String> scenarios = new HashSet<>();
	private Map<IConcept, Set<IConcept>> roles = new HashMap<>();
	private AtomicBoolean lockSpace = new AtomicBoolean(false);
	private AtomicBoolean lockTime = new AtomicBoolean(false);
	Map<String, Listener> listeners = Collections.synchronizedMap(new LinkedHashMap<>());
	private ScaleReference scaleOfInterest;
	private ISubject context;
	private String geocodingStrategy;

	/*
	 * this executor ensures that observation tasks sent through submit() are
	 * executed sequentially. We submit tasks that proxy the creation and execution
	 * of an observation task.
	 */
	private Executor executor = Executors.newSingleThreadExecutor();

	/*
	 * The timer is used to ensure that requests for geolocation don't overlap
	 * beyond the rate set by the geocoding service, but the latest extent arrived
	 * in a quick sequence always gets through.
	 */
	Timer extentTimer = new Timer();

	public SessionState(Session session) {
		this.session = session;
		this.scaleOfInterest = new ScaleReference();

		/*
		 * ROI defaults. TODO may be linked to configuration.
		 */
		this.scaleOfInterest.setName("Region of interest");
		ITime defaultTime = Time.INSTANCE.getGenericCurrentExtent(Resolution.Type.YEAR);
		this.scaleOfInterest.setTimeResolutionDescription(defaultTime.getResolution().toString());
		this.scaleOfInterest.setTimeResolutionMultiplier(defaultTime.getResolution().getMultiplier());
		this.scaleOfInterest.setStart(defaultTime.getStart().getMilliseconds());
		this.scaleOfInterest.setEnd(defaultTime.getEnd().getMilliseconds());
		this.scaleOfInterest.setTimeScale(defaultTime.getScaleRank());
		this.scaleOfInterest
				.setTimeGeometry(((org.integratedmodelling.klab.components.time.extents.Time) defaultTime).encode());
	}

	@Override
	public Future<IArtifact> submit(String urn) {
		return submit(urn, (observation) -> {
			// TODO stats, history
		}, (error) -> {
			// TODO stats, history
		});
	}

	public Future<IArtifact> submit(String urn, Consumer<IArtifact> observationListener,
			Consumer<Throwable> errorListener) {

		IResolvable resolvable = null;
		if (urn.contains(" ")) {
			resolvable = Observables.INSTANCE.declare(urn);
		} else {
			IKimObject object = Resources.INSTANCE.getModelObject(urn);
			if (object instanceof IResolvable) {
				resolvable = (IResolvable) object;
			}
		}

		if (this.context == null && !(resolvable instanceof IObserver)) {

//			this.context = null;
//
			IGeometry geometry = getGeometry();

			if (geometry == null) {
				throw new KlabContextualizationException(
						"Cannot contextualize URN " + urn + " in an unspecified context");
			}

			/**
			 * Submit all we know about the context. TODO metadata should contain provenance
			 * info about the choices made to get here.
			 */
			Observer observer = Observations.INSTANCE.makeROIObserver(scaleOfInterest.getName(), geometry,
					new Metadata());
			/*
			 * goes into executor; next one won't exec before this is finished. Only call
			 * the obs listener at the beginning of the contextualization.
			 */
			Future<IArtifact> task = new ObserveContextTask(this.session, observer, scenarios, (obs) -> {
				if (obs == null) {
					observationListener.accept(obs);
				}
			}, errorListener, executor);
			try {
				this.context = (ISubject) task.get();
			} catch (InterruptedException | ExecutionException e) {
				return task;
			}
		}

		/**
		 * Submit the actual resolvable
		 */
		return new ObserveInContextTask((Subject) this.context, urn, this.scenarios, observationListener, errorListener,
				this.executor);
	}

	@Override
	public boolean activateScenario(String scenario) {
		return this.scenarios.add(scenario);
	}

	@Override
	public boolean deactivateScenario(String scenario) {
		return this.scenarios.remove(scenario);
	}

	@Override
	public IGeometry getGeometry() {
		/*
		 * If geometry does not contain enough info, return null to signal that we don't
		 * have a completely specified ROI. Only possible situation is having no
		 * bounding box, as everything else has defaults.
		 */
		if (this.scaleOfInterest.getEast() == 0 && this.scaleOfInterest.getWest() == 0) {
			return null;
		}
		return Geometry.create(this.scaleOfInterest);
	}

	@Override
	public Object put(String key, Object value) {
		switch (key) {
		case GEOCODING_STRATEGY_KEY:
			this.setGeocodingStrategy(value.toString());
			break;
		case SPACE_RESOLUTION_KEY:
			IKimQuantity q = KimQuantity.parse(value.toString());
			this.scaleOfInterest.setSpaceResolution(q.getValue().doubleValue());
			this.scaleOfInterest.setSpaceUnit(q.getUnit());
			break;
		case SPACE_RESOLUTION_MULTIPLIER_KEY:
			this.scaleOfInterest.setSpaceResolution(check(value, Number.class).doubleValue());
			break;
		case SPACE_RESOLUTION_UNIT_KEY:
			this.scaleOfInterest.setSpaceUnit(value.toString());
			break;
		case LOCK_SPACE_KEY:
			this.lockSpace.set(check(value, Boolean.class));
			break;
		case LOCK_TIME_KEY:
			this.lockTime.set(check(value, Boolean.class));
			break;
		case TIME_END_KEY:
			this.scaleOfInterest.setTimeGeometry(null);
			this.scaleOfInterest.setEnd(check(value, Long.class));
			break;
		case TIME_START_KEY:
			this.scaleOfInterest.setTimeGeometry(null);
			this.scaleOfInterest.setStart(check(value, Long.class));
			break;
		case TIME_TYPE_KEY:
			this.scaleOfInterest.setTimeGeometry(null);
			this.scaleOfInterest.setTimeType(value.toString());
			break;
		case TIME_RESOLUTION_MULTIPLIER_KEY:
			this.scaleOfInterest.setTimeGeometry(null);
			this.scaleOfInterest.setTimeResolutionMultiplier(check(value, Number.class).doubleValue());
			break;
		case TIME_RESOLUTION_UNIT_KEY:
			this.scaleOfInterest.setTimeGeometry(null);
			Resolution res = org.integratedmodelling.klab.components.time.extents.Time
					.resolution("1." + value.toString());
			this.scaleOfInterest.setTimeUnit(res.getType());
			break;
		case TIME_RESOLUTION_KEY:
			this.scaleOfInterest.setTimeGeometry(null);
			res = org.integratedmodelling.klab.components.time.extents.Time.resolution(value.toString());
			this.scaleOfInterest.setTimeResolutionMultiplier(res.getMultiplier());
			this.scaleOfInterest.setTimeUnit(res.getType());
			break;
		case TIME_YEAR_KEY:
			this.scaleOfInterest.setTimeGeometry(null);
			this.scaleOfInterest.setYear(check(value, Integer.class));
			break;
		case TIME_START_YEAR_KEY:
			this.scaleOfInterest.setTimeGeometry(null);
			this.scaleOfInterest
					.setStart(new DateTime(check(value, Integer.class), 1, 1, 0, 0, 0, DateTimeZone.UTC).getMillis());
			break;
		case TIME_END_YEAR_KEY:
			this.scaleOfInterest.setTimeGeometry(null);
			this.scaleOfInterest
					.setEnd(new DateTime(check(value, Integer.class), 1, 1, 0, 0, 0, DateTimeZone.UTC).getMillis());
			break;
		}
		return super.put(key, value);
	}

	private <T> T check(Object value, Class<T> cls) {
		if (value == null) {
			return null;
		}
		if (!cls.isAssignableFrom(value.getClass())) {
			this.session.getMonitor().warn("internal error: session state assigned value " + value + " where a "
					+ cls.getCanonicalName() + " was expected");
		}

		return Utils.asType(value, cls);
	}

	@Override
	public Set<String> getActiveScenarios() {
		return scenarios;
	}

	/**
	 * Set and activate the passed application. All UI actions will automatically
	 * reflect on the view state. Only one application can be active in a session at
	 * any time, although the state of previous applications will be saved until
	 * session ends or deactivation.
	 * 
	 * @param applicationName
	 * @param view
	 */
	public void setApplication(String applicationName, Layout view) {

	}

	/**
	 * Make the current application the current one and reload its view.
	 * 
	 * @param applicationName
	 */
	@Override
	public void activateApplication(String applicationName) {

	}

	/**
	 * Deactivate the application and
	 * 
	 * @param applicationName
	 */
	@Override
	public void deactivateApplication(String applicationName) {

	}

	@Override
	public void addRole(IConcept role, IConcept target) {
	}

	@Override
	public void removeRole(IConcept role, IConcept target) {

	}

	@Override
	public void resetRoles() {
		this.roles.clear();
	}

	/**
	 * 
	 */
	@Override
	public String save() {
		return null;
	}

	/**
	 * 
	 */
	@Override
	public void restore(String stateId) {

	}

	public void register(LoadApplicationRequest request) {
		// TODO Auto-generated method stub
		System.out.println("ZIO CAN " + request);
	}

	public void register(ViewAction action) {

		@SuppressWarnings("unchecked")
		IActorIdentity<KlabMessage> receiver = Authentication.INSTANCE.getIdentity(action.getComponent().getIdentity(),
				IActorIdentity.class);
		if (receiver != null) {
			receiver.getActor().tell(
					// TODO consider having a scope in the state
					new UserAction(action, action.getComponent().getApplicationId(),
							new SimpleRuntimeScope(this.session)));
		}
	}

	@Override
	public void resetContext() {

		// roles and geocoding strategy remain as they are

		this.context = null;
//		ScaleReference scale = new ScaleReference();
//		if (lockSpace.get()) {
//			scale.setSpaceScale(this.scaleOfInterest.getSpaceScale());
//			scale.setSpaceUnit(this.scaleOfInterest.getSpaceUnit());
//			scale.setSpaceResolutionConverted(this.scaleOfInterest.getSpaceResolutionConverted());
//			scale.setSpaceResolution(this.scaleOfInterest.getSpaceResolution());
//			scale.setResolutionDescription(this.scaleOfInterest.getResolutionDescription());
//		}
//		if (lockTime.get()) {
//			scale.setTimeResolutionDescription(this.scaleOfInterest.getTimeResolutionDescription());
//			scale.setTimeResolutionMultiplier(this.scaleOfInterest.getTimeResolutionMultiplier());
//			scale.setTimeScale(this.scaleOfInterest.getTimeScale());
//			scale.setTimeUnit(this.scaleOfInterest.getTimeUnit());
//		}
//		this.scaleOfInterest = scale;
		session.getMonitor().send(IMessage.Type.ResetContext, IMessage.MessageClass.UserContextChange, "");

	}

	public void register(ObservationRequest request) {

		if (request.getContextId() != null) {

			IObservation subject = session.getObservation(request.getContextId());
			if (!(subject instanceof ISubject)) {
				throw new IllegalArgumentException("cannot use a state as the context for an observation");
			}

			if (!OWL.INSTANCE.isSemantic(subject.getObservable())) {
				throw new IllegalArgumentException("context has no semantics and cannot support further observations");
			}

			setContext(((Subject) subject).getScope());
		}

		this.scenarios.addAll(request.getScenarios());
		submit(request.getUrn());
	}

	public void register(ScaleReference extent) {
		// TODO Auto-generated method stub
		System.out.println("TODO - sent when user changes scale through explorer's default switcher" + extent + "?");
//		this.spatialGridSize = Units.INSTANCE.METERS
//				.convert(scaleRef.getSpaceResolutionConverted(), Unit.create(scaleRef.getSpaceUnit())).doubleValue();
//		this.spatialGridUnits = scaleRef.getSpaceUnit();
//		this.temporalResolution = Time
//				.resolution(scaleRef.getTimeResolutionMultiplier() + "." + scaleRef.getTimeUnit());
//		this.timeStart = scaleRef.getStart() == 0 ? null : Long.valueOf(scaleRef.getStart());
//		this.timeEnd = scaleRef.getEnd() == 0 ? null : Long.valueOf(scaleRef.getEnd());

	}

	public void register(SpatialExtent extent, boolean secondary) {

		if (this.geocodingStrategy != null && !secondary) {
			if (Geocoder.INSTANCE.getRateLimiter(this.geocodingStrategy).acquire() != 0) {
				extentTimer.cancel();
				extentTimer = new Timer();
				extentTimer.schedule(new TimerTask() {
					@Override
					public void run() {
						register(extent, true);
					}
				}, 600);
				return;
			}
		}

		extentTimer.cancel();
		extentTimer = new Timer();

		this.scaleOfInterest.setEast(extent.getEast());
		this.scaleOfInterest.setNorth(extent.getNorth());
		this.scaleOfInterest.setSouth(extent.getSouth());
		this.scaleOfInterest.setWest(extent.getWest());

		Envelope envelope = Envelope.create(extent.getEast(), extent.getWest(), extent.getSouth(), extent.getNorth(),
				Projection.getLatLon());

		if (!lockSpace.get() || this.scaleOfInterest.getSpaceUnit() == null) {
			Pair<Integer, String> rres = envelope.getResolutionForZoomLevel();
			this.scaleOfInterest.setSpaceResolution((double) rres.getFirst());
			this.scaleOfInterest.setSpaceUnit(rres.getSecond());
			this.scaleOfInterest.setSpaceScale(envelope.getScaleRank());
		}

		if (this.geocodingStrategy != null) {
			IShape shape = Geocoder.INSTANCE.geocodeToShape(extent, this.geocodingStrategy, session.getMonitor());
			if (shape != null) {
				this.scaleOfInterest.setName(shape.getMetadata().get(IMetadata.DC_DESCRIPTION, String.class));
				if (!(shape.getMetadata().containsKey(IMetadata.IM_GEOGRAPHIC_AREA)
						&& !shape.getMetadata().get(IMetadata.IM_GEOGRAPHIC_AREA, Boolean.FALSE))) {
					this.scaleOfInterest.setShape(((Shape) shape).getJTSGeometry().toString());
				}
			}
		}

		Unit sunit = Unit.create(scaleOfInterest.getSpaceUnit());
		this.scaleOfInterest.setSpaceResolutionConverted(
				sunit.convert(scaleOfInterest.getSpaceResolution(), Units.INSTANCE.METERS).doubleValue());
		this.scaleOfInterest.setSpaceResolutionDescription(
				NumberFormat.getInstance().format(this.scaleOfInterest.getSpaceResolutionConverted()) + " "
						+ this.scaleOfInterest.getSpaceUnit());
		this.scaleOfInterest.setResolutionDescription(
				NumberFormat.getInstance().format(this.scaleOfInterest.getSpaceResolutionConverted()) + " "
						+ this.scaleOfInterest.getSpaceUnit());

		session.getMonitor().send(IMessage.MessageClass.UserContextDefinition, IMessage.Type.ScaleDefined,
				scaleOfInterest);

		for (Listener listener : listeners.values()) {
			listener.scaleChanged(scaleOfInterest);
		}
	}

	public void setContext(IRuntimeScope runtimeContext) {
		this.context = runtimeContext.getRootSubject();
	}

	@Override
	public List<SessionActivity> getHistory() {
		return history;
	}

	@Override
	public String addListener(Listener listener) {
		String id = "sls" + NameGenerator.shortUUID();
		this.listeners.put(id, listener);
		return id;
	}

	@Override
	public void removeListener(String listenerId) {
		this.listeners.remove(listenerId);
	}

	public String getGeocodingStrategy() {
		return geocodingStrategy;
	}

	public void setGeocodingStrategy(String geocodingStrategy) {
		this.geocodingStrategy = geocodingStrategy;
	}

	public void register(SettingChangeRequest request) {

		switch (request.getSetting()) {
		case LockSpace:
			this.lockSpace.set(Boolean.parseBoolean(request.getNewValue()));
			session.getMonitor().info("spatial resolution " + (lockSpace.get() ? "" : "un") + "locked");
			break;
		case LockTime:
			this.lockTime.set(Boolean.parseBoolean(request.getNewValue()));
			session.getMonitor().info("temporal resolution " + (lockSpace.get() ? "" : "un") + "locked");
			break;
		default:
			break;
		}
	}

	@Override
	public String getRegionOfInterestName() {
		return this.scaleOfInterest.getName();
	}

	ITime getTimeOfInterest() {

		if (this.containsAnyKey("startyear", "endyear", "year", "timestep", "start", "end", "step")) {

			Object start = Utils.asType(this.getAny("startyear", "start"), Integer.class);
			Object end = Utils.asType(this.getAny("endyear", "end"), Integer.class);
			Object step = this.getAny("timestep", "step");
			Object year = Utils.asType(this.get("year"), Integer.class);

			Parameters<String> parameters = Parameters.createNotNull("start", start, "end", end, "step", step, "year",
					year);

			return (ITime) (new org.integratedmodelling.klab.components.time.services.Time()).eval(parameters, null);
		}

		return null;
	}

	/**
	 * Update the view to reflect the component as modified by a k.Actors action or
	 * by the UI.
	 * 
	 * @param component
	 */
	public void updateView(ViewComponent component) {
		// TODO Auto-generated method stub

	}

	@Override
	public IObservation getObservation(IObservable observable) {
		if (this.context != null) {
			Pair<String, IArtifact> result = ((IRuntimeScope) ((Subject) context).getScope()).findArtifact(observable);
			if (result != null) {
				return (IObservation) result.getSecond();
			}
		}
		return null;
	}

	@Override
	public IArtifact getArtifact(String name) {
		if (this.context != null) {
			return ((IRuntimeScope) ((Subject) context).getScope()).getArtifact(name);
		}
		return null;
	}

}
