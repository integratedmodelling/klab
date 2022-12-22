package org.integratedmodelling.klab.engine.runtime;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.integratedmodelling.kim.model.KimQuantity;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.ITaskIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IQuantity;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionConstraint;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.ISessionState;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.geocoding.Geocoder;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.UserAction;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.UserMenuAction;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.documentation.extensions.table.TableArtifact;
import org.integratedmodelling.klab.engine.debugger.Inspector;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.model.Acknowledgement;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.resolution.ResolutionConstraint;
import org.integratedmodelling.klab.rest.ContextualizationRequest;
import org.integratedmodelling.klab.rest.DataflowState;
import org.integratedmodelling.klab.rest.LoadApplicationRequest;
import org.integratedmodelling.klab.rest.MenuAction;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.rest.SettingChangeRequest;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.ibm.icu.text.NumberFormat;

/**
 * Recoverable session state. A generic map with specific fields and save/restore methods. Also
 * manages observation history and queue, scenarios, geometry of interest in time and space, and
 * application state and views.
 * <p>
 * TODO create also a WorkspaceState object that handles workspace modification requests. That could
 * be driven by a set of desktop-only actor messages to enable interactive coding.
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
    private Map<String, SessionActivity> historyByContext = new HashMap<>();
    long startTime = System.currentTimeMillis();
    private Set<String> scenarios = new HashSet<>();
    private Map<IConcept, Collection<IConcept>> roles = new HashMap<>();
    private AtomicBoolean lockSpace = new AtomicBoolean(false);
    private AtomicBoolean lockTime = new AtomicBoolean(false);
    Map<String, ListenerWrapper> listeners = Collections.synchronizedMap(new LinkedHashMap<>());
    Map<String, ListenerWrapper> globalListeners = Collections.synchronizedMap(new LinkedHashMap<>());
    private ScaleReference scaleOfInterest;
    private Stack<ISubject> context = new Stack<>();
    private String geocodingStrategy;
    private Map<String, File> stagingArea = Collections.synchronizedMap(new HashMap<>());
    private IScale forcedScale;
    private List<IResolutionConstraint> resolutionConstraints = new ArrayList<>();
    private Inspector inspector;

    // just for parking a flowchart in the root dataflow.
    private Flowchart flowchart;

    private class ListenerWrapper {

        Listener listener;
        String applicationId;

        public ListenerWrapper(Listener listener, String id) {
            this.listener = listener;
            this.applicationId = id;
        }

    }

    /*
     * this executor ensures that observation tasks sent through submit() are executed sequentially.
     * We submit tasks that proxy the creation and execution of an observation task.
     */
    private Executor executor = Executors.newSingleThreadExecutor();

    /*
     * The timer is used to ensure that requests for geolocation don't overlap beyond the rate set
     * by the geocoding service, but the latest extent arrived in a quick sequence always gets
     * through.
     */
    Timer extentTimer = new Timer();
    private String currentApplicationName;
    private String currentApplicationId;
    private SessionActivity currentActivity;

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
        this.scaleOfInterest.setTimeUnit(defaultTime.getResolution().getType());
        this.scaleOfInterest.setStart(defaultTime.getStart().getMilliseconds());
        this.scaleOfInterest.setEnd(defaultTime.getEnd().getMilliseconds());
        this.scaleOfInterest.setTimeScale(defaultTime.getScaleRank());
    }

    @Override
    public Future<IArtifact> submit(String urn) {
        return submit(urn, null, null);
    }

    public Future<IArtifact> submit(BiConsumer<ITaskIdentity, IArtifact> observationListener,
            BiConsumer<ITaskIdentity, Throwable> errorListener) {

        /*
         * TODO allow the urn to be null, meaning "just create the predefined context".
         */

        final SessionActivity activity = new SessionActivity();

        activity.setUser(session.getParentIdentity(IUserIdentity.class).getUsername());
        activity.setSessionId(session.getId());

        IGeometry geometry = getGeometry();

        if (geometry == null) {
            throw new KlabContextualizationException("Not enough predefined information to create a context");
        }

        /**
         * Submit all we know about the context. TODO metadata should contain provenance info about
         * the choices made to get here.
         */
        Acknowledgement observer = Observations.INSTANCE.makeROIObserver(scaleOfInterest.getName(), geometry, new Metadata());
        List<BiConsumer<ITask<?>, IArtifact>> oListeners = new ArrayList<>();
        List<BiConsumer<ITask<?>, Throwable>> eListeners = new ArrayList<>();

        oListeners.add((task, observation) -> {

            if (this.currentActivity != null) {
                activity.setContextId(this.currentActivity.getContextId());
            }

            if (observation == null) {
                /*
                 * task has started; record geometry (proxy to size). TODO tap into provenance to
                 * report resources used and results.
                 */
                IGeometry geom = getGeometry();
                activity.setStart(System.currentTimeMillis());
                if (this.currentActivity != null && this.currentActivity.getGeometrySet() == null && geom != null) {
                    this.currentActivity.setGeometrySet(geom.encode());
                }
                activity.setActivityId(task.getId());

            } else {
                /*
                 * activity ended successfully - TODO report actual computational load
                 */
                activity.setEnd(System.currentTimeMillis());
                activity.setStatus(DataflowState.Status.FINISHED);

                if (this.currentActivity != null && activity.getActivityId().equals(this.currentActivity.getActivityId())) {
                    this.currentActivity.setContextId(observation.getId());
                    this.historyByContext.put(observation.getId(), activity);
                }

                for (ListenerWrapper listener : listeners.values()) {
                    if (this.currentActivity != null) {
                        listener.listener.historyChanged(this.currentActivity,
                                activity.getActivityId().equals(this.currentActivity.getActivityId()) ? null : activity);
                    }
                }
            }
        });

        eListeners.add((task, error) -> {

            /*
             * activity ended with error - TODO report actual computational load
             */
            activity.setEnd(System.currentTimeMillis());
            activity.setStatus(DataflowState.Status.FINISHED);
            activity.setStackTrace(ExceptionUtils.getStackTrace(error));

            for (ListenerWrapper listener : listeners.values()) {
                if (this.currentActivity != null) {
                    listener.listener.historyChanged(this.currentActivity,
                            activity.getActivityId().equals(this.currentActivity.getActivityId()) ? null : activity);
                }
            }

        });

        /*
         * goes into executor; next one won't exec before this is finished. Only call the obs
         * listener at the beginning of the contextualization.
         */
        List<BiConsumer<ITask<?>, IArtifact>> ctxListeners = new ArrayList<>(oListeners);
        ctxListeners.add((tsk, obs) -> {
            if (obs == null && observationListener != null) {
                observationListener.accept(tsk, obs);
            }
        });

        Future<IArtifact> task = new ObserveContextTask(this.session, observer, session, scenarios, ctxListeners, eListeners, executor,
                activity, true);
        try {
            this.scaleOfInterest.setShape(null);
            this.context.push((ISubject) task.get());
        } catch (InterruptedException | ExecutionException e) {
            // just return
        }

        return task;

    }

    @Override
    public Future<IArtifact> submit(String urn, BiConsumer<ITask<?>, IArtifact> observationListener,
            BiConsumer<ITask<?>, Throwable> errorListener) {

        final SessionActivity activity = new SessionActivity();

        activity.setUrnObserved(urn);
        activity.setUser(session.getParentIdentity(IUserIdentity.class).getUsername());
        activity.setSessionId(session.getId());

        IResolvable resolvable = null;
        if (urn.contains(" ")) {
            resolvable = Observables.INSTANCE.declare(urn);
        } else {
            IKimObject object = Resources.INSTANCE.getModelObject(urn);
            if (object instanceof IResolvable) {
                resolvable = (IResolvable) object;
            }
        }

        if (this.currentActivity == null || resolvable instanceof Acknowledgement) {
            this.currentActivity = activity;
            history.add(activity);
        } else if (this.currentActivity != null) {
            activity.setParentActivityId(this.currentActivity.getActivityId());
        }

        List<BiConsumer<ITask<?>, IArtifact>> oListeners = new ArrayList<>();
        List<BiConsumer<ITask<?>, Throwable>> eListeners = new ArrayList<>();

        oListeners.add((task, observation) -> {

            if (this.currentActivity != null) {
                activity.setContextId(this.currentActivity.getContextId());
            }

            if (observation == null) {
                /*
                 * task has started; record geometry (proxy to size). TODO tap into provenance to
                 * report resources used and results.
                 */
                IGeometry geom = getGeometry();
                activity.setStart(System.currentTimeMillis());
                if (this.currentActivity.getGeometrySet() == null && geom != null) {
                    this.currentActivity.setGeometrySet(geom.encode());
                }
                activity.setActivityId(task.getId());

            } else {
                /*
                 * activity ended successfully - TODO report actual computational load
                 */
                activity.setEnd(System.currentTimeMillis());
                activity.setStatus(DataflowState.Status.FINISHED);

                if (this.currentActivity != null && activity.getActivityId().equals(this.currentActivity.getActivityId())) {
                    this.currentActivity.setContextId(observation.getId());
                    this.historyByContext.put(observation.getId(), activity);
                }

                for (ListenerWrapper listener : listeners.values()) {
                    if (this.currentActivity != null) {
                        listener.listener.historyChanged(this.currentActivity,
                                activity.getActivityId().equals(this.currentActivity.getActivityId()) ? null : activity);
                    }
                }
            }
        });

        eListeners.add((task, error) -> {

            /*
             * activity ended with error - TODO report actual computational load
             */
            activity.setEnd(System.currentTimeMillis());
            activity.setStatus(DataflowState.Status.FINISHED);
            activity.setStackTrace(ExceptionUtils.getStackTrace(error));

            for (ListenerWrapper listener : listeners.values()) {
                if (this.currentActivity != null) {
                    listener.listener.historyChanged(this.currentActivity,
                            activity.getActivityId().equals(this.currentActivity.getActivityId()) ? null : activity);
                }
            }

        });

        if (observationListener != null) {
            oListeners.add(observationListener);
        }
        if (errorListener != null) {
            eListeners.add(errorListener);
        }

        if (resolvable instanceof Acknowledgement) {
            return new ObserveContextTask(this.session, (Acknowledgement) resolvable, session, scenarios, oListeners, eListeners, executor,
                    activity, true);
        }

        if (this.context.isEmpty() && !(resolvable instanceof IAcknowledgement)) {

            IGeometry geometry = getGeometry();

            if (geometry == null) {
                throw new KlabContextualizationException("Cannot contextualize URN " + urn + " in an unspecified context");
            }

            /**
             * Submit all we know about the context. TODO metadata should contain provenance info
             * about the choices made to get here.
             */
            Acknowledgement observer = Observations.INSTANCE.makeROIObserver(scaleOfInterest.getName(), geometry, new Metadata());
            /*
             * goes into executor; next one won't exec before this is finished. Only call the obs
             * listener at the beginning of the contextualization.
             */
            List<BiConsumer<ITask<?>, IArtifact>> ctxListeners = new ArrayList<>(oListeners);
            ctxListeners.add((tsk, obs) -> {
                if (obs == null && observationListener != null) {
                    observationListener.accept(tsk, obs);
                }
            });

            Future<IArtifact> task = new ObserveContextTask(this.session, observer, session, scenarios, ctxListeners, eListeners, executor,
                    activity, true);
            try {
                this.scaleOfInterest.setShape(null);
                this.context.push((ISubject) task.get());
            } catch (InterruptedException | ExecutionException e) {
                return task;
            }
        }

        /**
         * Submit the actual resolvable
         */
        return new ObserveInContextTask((Subject) this.getCurrentContext(), urn, this.scenarios, oListeners, eListeners,
                this.executor, activity, true);
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
    public ISubject getCurrentContext() {
        return context.isEmpty() ? null : context.peek();
    }
    
    @Override
    public String getCurrentApplicationName() {
        return this.currentApplicationName;
    }

    @Override
    public IGeometry getGeometry() {

        if (this.forcedScale != null) {
            return this.forcedScale;
        }

        /*
         * If geometry does not contain enough info, return null to signal that we don't have a
         * completely specified ROI. Only possible situation is having no bounding box, as
         * everything else has defaults.
         */
        if (this.scaleOfInterest.getEast() == 0 && this.scaleOfInterest.getWest() == 0) {
            return null;
        }

        return Geometry.create(Geocoder.INSTANCE.finalizeShape(this.scaleOfInterest, session.getMonitor()));
    }

    @Override
    public Object put(String key, Object value) {

        switch(key) {
        case GEOCODING_STRATEGY_KEY:
            setGeocodingStrategy(value == null ? null : value.toString());
            this.scaleOfInterest.setShape(null);
            if (this.scaleOfInterest.getSpaceUnit() != null) {
                // don't call unless it was set at least once
                register(getCurrentExtent(), false);
            }
            break;
        case SPACE_RESOLUTION_KEY:
            IQuantity q = value instanceof IQuantity ? (IQuantity) value : KimQuantity.parse(value.toString());
            this.scaleOfInterest.setSpaceResolution(q.getValue().doubleValue());
            this.scaleOfInterest.setSpaceUnit(q.getUnit());
            this.scaleOfInterest.setSpaceResolutionConverted(Units.INSTANCE.METERS
                    .convert(this.scaleOfInterest.getSpaceResolution(), Unit.create(this.scaleOfInterest.getSpaceUnit()))
                    .doubleValue());
            this.scaleOfInterest
                    .setSpaceResolutionDescription(NumberFormat.getInstance().format(this.scaleOfInterest.getSpaceResolution())
                            + " " + this.scaleOfInterest.getSpaceUnit());
            break;
        case SPACE_RESOLUTION_MULTIPLIER_KEY:
            this.scaleOfInterest.setSpaceResolution(check(value, Number.class).doubleValue());
            this.scaleOfInterest.setSpaceResolutionConverted(Units.INSTANCE.METERS
                    .convert(this.scaleOfInterest.getSpaceResolution(), Unit.create(this.scaleOfInterest.getSpaceUnit()))
                    .doubleValue());
            this.scaleOfInterest
                    .setSpaceResolutionDescription(NumberFormat.getInstance().format(this.scaleOfInterest.getSpaceResolution())
                            + " " + this.scaleOfInterest.getSpaceUnit());
            break;
        case SPACE_RESOLUTION_UNIT_KEY:
            this.scaleOfInterest.setSpaceUnit(value.toString());
            this.scaleOfInterest.setSpaceResolutionConverted(Units.INSTANCE.METERS
                    .convert(this.scaleOfInterest.getSpaceResolution(), Unit.create(this.scaleOfInterest.getSpaceUnit()))
                    .doubleValue());
            this.scaleOfInterest
                    .setSpaceResolutionDescription(NumberFormat.getInstance().format(this.scaleOfInterest.getSpaceResolution())
                            + " " + this.scaleOfInterest.getSpaceUnit());
            break;
        case LOCK_SPACE_KEY:
            this.lockSpace.set(check(value, Boolean.class));
            break;
        case LOCK_TIME_KEY:
            this.lockTime.set(check(value, Boolean.class));
            break;
        case TIME_END_KEY:
            this.scaleOfInterest.setEnd(check(value, Long.class));
            break;
        case TIME_START_KEY:
            this.scaleOfInterest.setStart(check(value, Long.class));
            break;
        case TIME_TYPE_KEY:
            this.scaleOfInterest.setTimeType(value.toString());
            break;
        case TIME_RESOLUTION_MULTIPLIER_KEY:
            this.scaleOfInterest.setTimeResolutionMultiplier(check(value, Number.class).doubleValue());
            break;
        case TIME_RESOLUTION_UNIT_KEY:
            Resolution res = org.integratedmodelling.klab.components.time.extents.Time.resolution("1." + value.toString());
            this.scaleOfInterest.setTimeUnit(res.getType());
            break;
        case TIME_RESOLUTION_KEY:
            res = org.integratedmodelling.klab.components.time.extents.Time.resolution(value.toString());
            this.scaleOfInterest.setTimeResolutionMultiplier(res.getMultiplier());
            this.scaleOfInterest.setTimeUnit(res.getType());
            break;
        case TIME_YEAR_KEY:
            this.scaleOfInterest.setYear(check(value, Integer.class));
            break;
        case TIME_START_YEAR_KEY:
            this.scaleOfInterest.setStart(new DateTime(check(value, Integer.class), 1, 1, 0, 0, 0, DateTimeZone.UTC).getMillis());
            break;
        case TIME_END_YEAR_KEY:
            this.scaleOfInterest.setEnd(new DateTime(check(value, Integer.class), 1, 1, 0, 0, 0, DateTimeZone.UTC).getMillis());
            break;
        }
        return super.put(key, value);
    }

    private SpatialExtent getCurrentExtent() {
        SpatialExtent ret = new SpatialExtent();
        ret.setNorth(this.scaleOfInterest.getNorth());
        ret.setEast(this.scaleOfInterest.getEast());
        ret.setSouth(this.scaleOfInterest.getSouth());
        ret.setWest(this.scaleOfInterest.getWest());
        ret.setGridResolution(this.scaleOfInterest.getSpaceResolution());
        ret.setGridUnit(this.scaleOfInterest.getSpaceUnit());
        return ret;
    }

    private <T> T check(Object value, Class<T> cls) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            value = Utils.asPOD((String) value);
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
        Set<IConcept> roles = (Set<IConcept>) this.roles.get(role);
        if (roles == null) {
            roles = new HashSet<>();
            this.roles.put(role, roles);
        }
        roles.add(target);
    }

    @Override
    public void removeRole(IConcept role, IConcept target) {
        Set<IConcept> roles = (Set<IConcept>) this.roles.get(role);
        if (roles != null) {
            roles.remove(target);
        }
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
        // System.out.println("ZIO CAN " + request);
    }

    public void register(ViewAction action) {

        @SuppressWarnings("unchecked")
        IActorIdentity<KlabMessage> receiver = Authentication.INSTANCE.getIdentity(action.getComponent().getIdentity(),
                IActorIdentity.class);
        if (receiver != null) {
            receiver.getActor().tell(
                    // TODO consider having a scope in the state
                    new UserAction(action, action.getComponent().getApplicationId(), new SimpleRuntimeScope(this.session)));
        }
    }

    public void register(MenuAction action) {

        @SuppressWarnings("unchecked")
        IActorIdentity<KlabMessage> receiver = Authentication.INSTANCE.getIdentity(action.getIdentity(), IActorIdentity.class);
        if (receiver != null) {
            receiver.getActor().tell(
                    // TODO consider having a scope in the state
                    new UserMenuAction(action, action.getApplicationId(), new SimpleRuntimeScope(this.session)));
        }
    }

    @Override
    public void resetContext() {
        // roles and geocoding strategy remain as they are (? - application can choose
        // by listening to event).
        this.context.clear();
        this.currentActivity = null;
        for (ListenerWrapper listener : listeners.values()) {
            if (listener.applicationId == null || listener.applicationId.equals(this.currentApplicationId)) {
                listener.listener.newContext(this.getCurrentContext());
            }
        }
        session.getMonitor().send(IMessage.Type.ResetContext, IMessage.MessageClass.UserContextChange, "");
    }

    public void submitGeolocation(String urn) {

        Envelope envelope = Envelope.create(this.scaleOfInterest.getEast(), this.scaleOfInterest.getWest(),
                this.scaleOfInterest.getSouth(), this.scaleOfInterest.getNorth(), Projection.getLatLon());
        IKlabData data = Resources.INSTANCE.getResourceData(urn, new VisitingDataBuilder(), IArtifact.Type.OBJECT, "result",
                Scale.create(envelope.asGrid(this.scaleOfInterest.getSpaceResolution(), this.scaleOfInterest.getSpaceUnit())),
                session.getMonitor());
        if (data.getArtifact() != null) {
            IGeometry geometry = data.getArtifact().getGeometry();
            if (geometry != null) {
                IShape ret = geometry instanceof IScale
                        ? ((IScale) geometry).getSpace().getShape()
                        : Scale.create(geometry).getSpace().getShape();
                if (ret != null) {
                    ret.getMetadata().put(IMetadata.DC_DESCRIPTION, ((IObjectArtifact) data.getArtifact()).getName());
                    setShape(ret);
                }
            }
        }
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

        this.scaleOfInterest.setSpaceResolution(extent.getSpaceResolution());
        this.scaleOfInterest.setSpaceUnit(extent.getSpaceUnit());
        this.scaleOfInterest.setSpaceResolutionDescription(extent.getResolutionDescription());

        this.scaleOfInterest.setTimeResolutionMultiplier(extent.getTimeResolutionMultiplier());
        this.scaleOfInterest.setTimeUnit(extent.getTimeUnit());
        this.scaleOfInterest.setStart(extent.getStart());
        this.scaleOfInterest.setEnd(extent.getEnd());

        this.scaleOfInterest.setSpaceResolutionConverted(Units.INSTANCE.METERS
                .convert(this.scaleOfInterest.getSpaceResolution(), Unit.create(this.scaleOfInterest.getSpaceUnit()))
                .doubleValue());

        for (ListenerWrapper listener : listeners.values()) {
            if (listener.applicationId == null || listener.applicationId.equals(this.currentApplicationId)) {
                listener.listener.scaleChanged(scaleOfInterest);
            }
        }
    }

    public void setShape(IShape shape) {

        if (shape == null) {

            Envelope envelope = Envelope.create(this.scaleOfInterest.getEast(), this.scaleOfInterest.getWest(),
                    this.scaleOfInterest.getSouth(), this.scaleOfInterest.getNorth(), Projection.getLatLon());
            this.scaleOfInterest.setShape(null);
            this.scaleOfInterest.setName(Geocoder.INSTANCE.geocode(envelope, Geocoder.DEFAULT_GEOCODING_STRATEGY,
                    "Region of interest", session.getMonitor()));
        } else {

            IEnvelope envelope = shape.getEnvelope();

            this.scaleOfInterest.setEast(envelope.getMinX());
            this.scaleOfInterest.setNorth(envelope.getMaxY());
            this.scaleOfInterest.setSouth(envelope.getMinY());
            this.scaleOfInterest.setWest(envelope.getMaxX());

            if (!lockSpace.get() || this.scaleOfInterest.getSpaceUnit() == null) {
                Pair<Integer, String> rres = ((Envelope) envelope).getResolutionForZoomLevel();
                this.scaleOfInterest.setSpaceResolution((double) rres.getFirst());
                this.scaleOfInterest.setSpaceUnit(rres.getSecond());
                this.scaleOfInterest.setSpaceScale(envelope.getScaleRank());
                this.scaleOfInterest.setSpaceResolutionConverted(Units.INSTANCE.METERS
                        .convert(this.scaleOfInterest.getSpaceResolution(), Unit.create(this.scaleOfInterest.getSpaceUnit()))
                        .doubleValue());
            }

            String name = shape.getMetadata().get(IMetadata.DC_DESCRIPTION, String.class);
            if (name == null) {
                /*
                 * geocode using the standard geocoder
                 */
                name = Geocoder.INSTANCE.geocode(shape.getEnvelope(), Geocoder.DEFAULT_GEOCODING_STRATEGY, "Region of interest",
                        session.getMonitor());
            }
            this.scaleOfInterest.setName(name);
            if (!(shape.getMetadata().containsKey(IMetadata.IM_GEOGRAPHIC_AREA)
                    && !shape.getMetadata().get(IMetadata.IM_GEOGRAPHIC_AREA, Boolean.FALSE))) {
                this.scaleOfInterest.setShape(((Shape) shape).getJTSGeometry().toString());
            }
            this.scaleOfInterest
                    .setSpaceResolutionDescription(NumberFormat.getInstance().format(this.scaleOfInterest.getSpaceResolution())
                            + " " + this.scaleOfInterest.getSpaceUnit());
            this.scaleOfInterest
                    .setResolutionDescription(NumberFormat.getInstance().format(this.scaleOfInterest.getSpaceResolution()) + " "
                            + this.scaleOfInterest.getSpaceUnit());

        }

        session.getMonitor().send(IMessage.MessageClass.UserContextDefinition, IMessage.Type.ScaleDefined, scaleOfInterest);

        for (ListenerWrapper listener : listeners.values()) {
            if (listener.applicationId == null || listener.applicationId.equals(this.currentApplicationId)) {
                listener.listener.scaleChanged(scaleOfInterest);
            }
        }
    }

    public void register(SpatialExtent extent, boolean secondary) {

        if (this.geocodingStrategy != null && !secondary) {
            if (Geocoder.INSTANCE.getRateLimiter(this.geocodingStrategy).acquire() != 0) {
                extentTimer.cancel();
                extentTimer = new Timer();
                extentTimer.schedule(new TimerTask(){
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
            this.scaleOfInterest.setSpaceResolutionConverted(Units.INSTANCE.METERS
                    .convert(this.scaleOfInterest.getSpaceResolution(), Unit.create(this.scaleOfInterest.getSpaceUnit()))
                    .doubleValue());
        }

        if (this.geocodingStrategy != null) {
            IShape shape = Geocoder.INSTANCE.geocodeToShape(extent, this.geocodingStrategy, session.getMonitor());
            if (shape != null) {
                this.scaleOfInterest.setName(shape.getMetadata().get(IMetadata.DC_DESCRIPTION, String.class));
                if (shape.getMetadata().containsKey(IMetadata.IM_FEATURE_URN)) {
                    this.scaleOfInterest.setFeatureUrn(shape.getMetadata().get(IMetadata.IM_FEATURE_URN, String.class));
                }
                if (!(shape.getMetadata().containsKey(IMetadata.IM_GEOGRAPHIC_AREA)
                        && !shape.getMetadata().get(IMetadata.IM_GEOGRAPHIC_AREA, Boolean.FALSE))) {
                    this.scaleOfInterest.setShape(((Shape) shape).getJTSGeometry().toString());
                }
            }
        }

        this.scaleOfInterest
                .setSpaceResolutionDescription(NumberFormat.getInstance().format(this.scaleOfInterest.getSpaceResolution()) + " "
                        + this.scaleOfInterest.getSpaceUnit());
        this.scaleOfInterest.setResolutionDescription(NumberFormat.getInstance().format(this.scaleOfInterest.getSpaceResolution())
                + " " + this.scaleOfInterest.getSpaceUnit());

        session.getMonitor().send(IMessage.MessageClass.UserContextDefinition, IMessage.Type.ScaleDefined, scaleOfInterest);

        for (ListenerWrapper listener : listeners.values()) {
            if (listener.applicationId == null || listener.applicationId.equals(this.currentApplicationId)) {
                listener.listener.scaleChanged(scaleOfInterest);
            }
        }
    }

    public void setContext(IRuntimeScope runtimeContext) {
        if (this.context.size() <= 1) {
            this.context.clear();
            this.context.push(runtimeContext.getRootSubject());
            for (ListenerWrapper listener : listeners.values()) {
                if (listener.applicationId == null || listener.applicationId.equals(this.currentApplicationId)) {
                    listener.listener.newContext(this.getCurrentContext());
                }
            }
        }
    }

    @Override
    public List<SessionActivity> getHistory() {
        return history;
    }

    @Override
    public String addListener(Listener listener) {
        String id = "sls" + NameGenerator.shortUUID();
        this.listeners.put(id, new ListenerWrapper(listener, null));
        return id;
    }

    @Override
    public String addApplicationListener(Listener listener, String applicationId) {
        String id = "sls" + NameGenerator.shortUUID();
        this.listeners.put(id, new ListenerWrapper(listener, applicationId));
        return id;
    }

    @Override
    public String addApplicationGlobalListener(Listener listener, String applicationId) {
        String id = "sls" + NameGenerator.shortUUID();
        this.globalListeners.put(id, new ListenerWrapper(listener, applicationId));
        return id;
    }

    @Override
    public void removeListener(String listenerId) {
        this.listeners.remove(listenerId);
    }

    @Override
    public void removeGlobalListener(String listenerId) {
        this.globalListeners.remove(listenerId);
    }

    public String getGeocodingStrategy() {
        return geocodingStrategy;
    }

    public void setGeocodingStrategy(String geocodingStrategy) {

        boolean reset = this.geocodingStrategy != null && !this.geocodingStrategy.equals(geocodingStrategy);
        this.geocodingStrategy = geocodingStrategy;
        if (this.scaleOfInterest != null && reset) {

            this.scaleOfInterest.setShape(null);
            if (this.geocodingStrategy != null) {

                Envelope envelope = Envelope.create(this.scaleOfInterest.getEast(), this.scaleOfInterest.getWest(),
                        this.scaleOfInterest.getSouth(), this.scaleOfInterest.getNorth(), Projection.getLatLon());

                IShape shape = Geocoder.INSTANCE.geocodeToShape(envelope, this.geocodingStrategy, session.getMonitor());
                if (shape != null) {
                    this.scaleOfInterest.setName(shape.getMetadata().get(IMetadata.DC_DESCRIPTION, String.class));
                    if (!(shape.getMetadata().containsKey(IMetadata.IM_GEOGRAPHIC_AREA)
                            && !shape.getMetadata().get(IMetadata.IM_GEOGRAPHIC_AREA, Boolean.FALSE))) {
                        this.scaleOfInterest.setShape(((Shape) shape).getJTSGeometry().toString());
                    }
                }
            }

            session.getMonitor().send(IMessage.MessageClass.UserContextDefinition, IMessage.Type.ScaleDefined, scaleOfInterest);

            for (ListenerWrapper listener : listeners.values()) {
                if (listener.applicationId == null || listener.applicationId.equals(this.currentApplicationId)) {
                    listener.listener.scaleChanged(scaleOfInterest);
                }
            }
        }
    }

    public void register(SettingChangeRequest request) {

        switch(request.getSetting()) {
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

            Parameters<String> parameters = Parameters.createNotNull("start", start, "end", end, "step", step, "year", year);

            return (ITime) (new org.integratedmodelling.klab.components.time.services.Time()).eval(null, parameters);
        }

        return null;
    }

    /**
     * Update the view (in the session) to reflect the component as modified by a k.Actors action or
     * by the UI.
     * 
     * @param component
     */
    public void updateView(ViewComponent component) {
        // TODO Auto-generated method stub
        // System.out.println("UPDATE VIEW CALLED");
    }

    @Override
    public IObservation getObservation(IObservable observable) {
        if (!this.context.isEmpty()) {
            Pair<String, IArtifact> result = ((IRuntimeScope) ((Subject) getCurrentContext()).getScope())
                    .findArtifact(observable);
            if (result != null) {
                return (IObservation) result.getSecond();
            }
        }
        return null;
    }

    @Override
    public IArtifact getArtifact(String name) {
        if (!this.context.isEmpty()) {
            return ((IRuntimeScope) ((Subject) getCurrentContext()).getScope()).getArtifact(name);
        }
        return null;
    }

    /**
     * Register a new application and remove all listeners that have a different application ID.
     * 
     * @param ret
     */
    public void setApplicationId(String ret) {

        List<String> toRemove = new ArrayList<>();
        for (String key : listeners.keySet()) {
            if (listeners.get(key).applicationId != null) {
                toRemove.add(key);
            }
        }

        for (String key : toRemove) {
            listeners.remove(key);
        }

        this.currentApplicationId = ret;
    }

    public void setApplicationName(String appName) {
        this.currentApplicationName = appName;
    }
    
    public void notifyNewObservation(IObservation observation, ISubject context) {
        for (ListenerWrapper listener : listeners.values()) {
            if (listener.applicationId == null || listener.applicationId.equals(this.currentApplicationId)) {
                listener.listener.newObservation(observation, context);
            }
        }
    }

    public void notifyNewContext(ISubject object) {
        for (ListenerWrapper listener : listeners.values()) {
            if (listener.applicationId == null || listener.applicationId.equals(this.currentApplicationId)) {
                listener.listener.newContext(this.getCurrentContext());
            }
        }
    }

    @Override
    public void notifyObservation(IObservation observation) {

        /*
         * notify all global listeners
         */
        for (ListenerWrapper listener : globalListeners.values()) {
            if (listener.applicationId == null || listener.applicationId.equals(this.currentApplicationId)) {
                listener.listener.newObservation(observation, this.getCurrentContext());
            }
        }

    }

    @Override
    public void setActiveScenarios(Collection<String> scenarios) {
        this.scenarios.clear();
        this.scenarios.addAll(scenarios);
    }

    @Override
    public Map<IConcept, Collection<IConcept>> getRoles() {
        return this.roles;
    }

    /**
     * Return all the table artifacts produced in this state.
     * 
     * @return
     */
    public Collection<TableArtifact> getTables() {
        List<TableArtifact> ret = new ArrayList<>();
        if (!this.context.isEmpty()) {
            for (IKnowledgeView view : ((IRuntimeScope) ((Subject) getCurrentContext()).getScope()).getViews()) {
                if (view instanceof TableArtifact) {
                    ret.add((TableArtifact) view);
                }
            }
        }
        return ret;
    }

    @Override
    public String stageDownload(File file) {
        String id = NameGenerator.shortUUID();
        this.stagingArea.put(id, file);
        return id;
    }

    @Override
    public File getStagedFile(String id) {
        return this.stagingArea.remove(id);
    }

    public void recontextualize(ContextualizationRequest request) {
        IObservation obs = session.getObservation(request.getContextId());
        if (obs instanceof ISubject) {
            this.context.push((ISubject) obs);
        }
    }

    /**
     * Force a scale to substitute to the user-defined scale hints. Must be deactivated by passing
     * null before control is given back to the UI.
     * 
     * @param scale
     * @return
     */
    public ISessionState withScale(IScale scale) {
        this.forcedScale = scale;
        return this;
    }

    public List<IResolutionConstraint> getResolutionConstraints() {
        return resolutionConstraints;
    }

    public void setFlowchart(Flowchart flowchart) {
        this.flowchart = flowchart;
    }

    public Flowchart getFlowchart() {
        return this.flowchart;
    }

    /**
     * TODO this must be automatically removed at the end of an action
     * 
     * @param o
     */
    public void whitelist(Object... o) {
        this.resolutionConstraints.add(ResolutionConstraint.whitelist(o));
    }

    /**
     * TODO this must be automatically removed at the end of an action
     * 
     * @param o
     */
    public void blacklist(Object... o) {
        this.resolutionConstraints.add(ResolutionConstraint.blacklist(o));
    }

    public void resetConstraints() {
        this.resolutionConstraints.clear();
    }

    public void setInspector(Inspector inspector) {
        this.inspector = inspector;
    }

    @Override
    public Inspector getInspector() {
        return inspector;
    }

    public void resetInspector() {
        if (inspector != null) {
            inspector = null;
        }
    }

}
