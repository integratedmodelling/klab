package org.integratedmodelling.klab.components.runtime.actors;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.data.IQuantity;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ISessionState;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.AppReset;
import org.integratedmodelling.klab.components.runtime.actors.extensions.Artifact;
import org.integratedmodelling.klab.components.runtime.actors.extensions.Grid;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.documentation.extensions.table.TableArtifact;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.resources.Worldview;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.DataflowState.Status;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;

import akka.actor.typed.ActorRef;

/**
 * 
 * Messages:
 * <ul>
 * <li><b>maybe[(probability, value)]</b> fire value (default TRUE) with probability (default
 * 0.5)</li>
 * <li><b>choose(v1, v2, ..., vn)</b> fire one of the values with same probability; if first value
 * is a distribution and vals are same number, use that to choose</li>
 * </ul>
 * <p>
 * All these messages must be quick to execute, as all observations will queue them here!
 * 
 * @author Ferd
 *
 */
@Behavior(id = "session", version = Version.CURRENT)
public class RuntimeBehavior {

    /**
     * Set the root context
     */
    @Action(id = "context", fires = Type.OBSERVATION, description = "Used with a URN as parameter, creates the context from an observe statement. If used without"
            + " parameters, fire the observation when a new context is established")
    public static class Context extends KlabActionExecutor {

        String listenerId = null;

        public Context(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {

            if (arguments.get("interrupt") instanceof IKActorsValue) {
                KActorsValue interrupt = arguments.get("interrupt", KActorsValue.class);
                if (Actors.INSTANCE.asBooleanValue(interrupt.evaluate(scope, identity, true))) {
                    scope.getMonitor().getIdentity().getParentIdentity(ISession.class).interruptAllTasks();
                }
            }

            if (arguments.get("reset") instanceof IKActorsValue) {
                KActorsValue reset = arguments.get("reset", KActorsValue.class);
                if (Actors.INSTANCE.asBooleanValue(reset.evaluate(scope, identity, true))) {
                    scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState().resetContext();
                }
            } else if (arguments.getUnnamedKeys().isEmpty()) {

                this.listenerId = scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState()
                        .addApplicationListener(new ISessionState.Listener(){
                            @Override
                            public void newContext(ISubject observation) {
                                fire(observation, scope);
                            }

                            @Override
                            public void newObservation(IObservation observation, ISubject context) {
                            }

                            @Override
                            public void scaleChanged(ScaleReference scale) {
                            }

                            @Override
                            public void historyChanged(SessionActivity rootActivity, SessionActivity currentActivity) {
                            }

                        }, scope.getAppId());

            } else {

                /*
                 * context instruction with parameters will reset the context mandatorily if one is
                 * active.
                 */
                if (scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState().getCurrentContext() != null) {
                    scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState().resetContext();
                }

                Pair<Map<String, Object>, List<String>> args = separateObservationArguments(arguments, scope, identity);
                Map<String, Object> contextDef = args.getFirst();
                Object toFire = null;
                if (contextDef.containsKey("scale")) {
                    try {
                        toFire = (IObservation) ((Session) identity).getState().withScale((IScale) contextDef.get("scale"))
                                .submit(((IObservable) contextDef.get("observable")).getDefinition()).get();
                    } catch (Throwable e) {
                        fail(scope);
                    }
                } else if (contextDef.containsKey("observer")) {
                    try {
                        toFire = (IObservation) ((Session) identity).getState()
                                .submit(((IAcknowledgement) contextDef.get("observer")).getName()).get();
                    } catch (Throwable e) {
                        fail(scope);
                    }
                } else if (contextDef.containsKey("observation")) {
                    toFire = (IObservation) contextDef.get("observation");
                }

                for (String observable : args.getSecond()) {
                    try {
                        Future<IArtifact> future = ((Session) identity).getState().submit(observable);
                        toFire = (IObservation) future.get();
                        if (toFire != null
                                && ((IObservation) toFire).getObservable().getType().equals(Concepts.c(NS.CORE_VOID))) {
                            /*
                             * return artifact is a view
                             */
                            for (IKnowledgeView view : ((IRuntimeScope) ((IObservation) toFire).getScope()).getViews()) {
                                if (observable.equals(view.getUrn())) {
                                    toFire = view;
                                    break;
                                }
                            }
                        }
                    } catch (Throwable e) {
                        fail(scope);
                    }
                }

                fire(toFire, scope);

            }

        }

        @Override
        public void dispose() {
            if (this.listenerId != null) {
                scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState().removeListener(this.listenerId);
            }
        }
    }

    /**
     * Make an observation, setting the context according to current preferences and session state,
     * or set the context itself if the observation is a subject and the current context is not set.
     */
    @Action(id = "submit", fires = Type.OBSERVATION, description = "Submit a URN for observation, either in the current context or creating one from the "
            + " current preferences. The session will add it to the observation queue and make the observation when possible. "
            + "When done, the correspondent artifact (or an error) will be fired. Before then, the action will fire WAITING when the task is "
            + "queued, STARTED when it starts computing, and ABORTED in case of error. Submit without arguments will set the context according to what"
            + "is specified in the session state.")

    public static class Submit extends KlabActionExecutor {

        String listenerId = null;

        public Submit(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {

            if (!arguments.getUnnamedKeys().isEmpty()) {
                fire(Status.WAITING, scope);
                identity.getParentIdentity(Session.class).getState()
                        .submit(getUrnValue(arguments.get(arguments.getUnnamedKeys().get(0)), scope), (task, observation) -> {
                            if (observation == null) {
                                fire(Status.STARTED, scope);
                            } else if (task.getMonitor().isInterrupted()) {
                                fire(Status.INTERRUPTED, scope);
                            } else {
                                fire(observation, scope);
                            }
                        }, (task, exception) -> {
                            fire(Status.ABORTED, scope);
                        });
            } else {
                fire(Status.WAITING, scope);
                identity.getParentIdentity(Session.class).getState().submit((task, observation) -> {
                    if (observation == null) {
                        fire(Status.STARTED, scope);
                    } else if (task.getMonitor().isInterrupted()) {
                        fire(Status.INTERRUPTED, scope);
                    } else {
                        fire(observation, scope);
                    }
                }, (task, exception) -> {
                    fire(Status.ABORTED, scope);
                });
            }
        }

        private String getUrnValue(Object object, IKActorsBehavior.Scope scope) {
            if (object instanceof KActorsValue) {
                object = ((KActorsValue) object).evaluate(scope, identity, true);
            }
            if (object instanceof IConcept) {
                return ((IConcept) object).getDefinition();
            } else if (object instanceof IObservable) {
                return ((IObservable) object).getDefinition();
            }
            // TODO other situations?
            return object.toString();
        }

        @Override
        public void dispose() {
        }
    }

    /**
     * Reset roles to the passed arguments. Pass individual observables/roles or collections
     * thereof.
     */
    @Action(id = "roles", fires = Type.EMPTY, description = "Apply a set of roles to one or more observables or observations. Any previously set roles are deactivated.")
    public static class SetRole extends KlabActionExecutor {

        String listenerId = null;

        public SetRole(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {

            Set<IConcept> roles = new HashSet<>();
            Set<IConcept> observables = new HashSet<>();

            for (Object arg : arguments.getUnnamedArguments()) {
                Object value = arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg;
                if (value instanceof IObservable) {
                    IConcept c = ((IObservable) value).getType();
                    if (c.is(IKimConcept.Type.ROLE)) {
                        roles.add(c);
                    } else {
                        observables.add(c);
                    }
                } else if (value instanceof Collection) {
                    for (Object cc : ((Collection<?>) value)) {
                        if (cc instanceof IObservable) {
                            IConcept c = ((IObservable) cc).getType();
                            if (c.is(IKimConcept.Type.ROLE)) {
                                roles.add(c);
                            } else {
                                observables.add(c);
                            }
                        }
                    }
                }
            }

            session.getState().resetRoles();
            for (IConcept role : roles) {
                for (IConcept target : observables) {
                    session.getState().addRole(role, target);
                }
            }
        }

        @Override
        public void dispose() {
        }
    }

    /**
     * Make an observation, setting the context according to current preferences and session state,
     * or set the context itself if the observation is a subject and the current context is not set.
     * 
     * TODO needs fleshing out - also call with :on, :off, :reset
     */
    @Action(id = "scenarios", fires = Type.EMPTY, description = "Apply a session-specific role to one or more observables or observations.")
    public static class SetScenarios extends KlabActionExecutor {

        String listenerId = null;

        public SetScenarios(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
            Set<String> scenarios = new HashSet<>();
            for (String key : arguments.getUnnamedKeys()) {
                scenarios.add(((IKActorsValue) arguments.get(key)).evaluate(scope, identity, true).toString());
            }
            session.getState().setActiveScenarios(scenarios);
        }

        @Override
        public void dispose() {
            session.getState().getActiveScenarios().clear();
        }
    }

    /**
     * Set the root context
     */
    @Action(id = "locate", fires = Type.MAP, description = "Listens to setting of spatial extent outside of a context")
    public static class Locate extends KlabActionExecutor {

        String listenerId = null;

        public Locate(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {

            if (arguments == null || arguments.getUnnamedKeys().isEmpty()) {
                this.listenerId = scope.getMonitor().getIdentity().getParentIdentity(Session.class).getState()
                        .addApplicationListener(new ISessionState.Listener(){

                            @Override
                            public void scaleChanged(ScaleReference scale) {
                                Map<String, Object> ret = new HashMap<>();
                                ret.put("description", scale.getName());
                                ret.put("resolution", scale.getSpaceResolution());
                                ret.put("unit", scale.getSpaceUnit());
                                ret.put("envelope",
                                        new double[]{scale.getWest(), scale.getSouth(), scale.getEast(), scale.getNorth()});
                                fire(ret, scope);
                            }

                            @Override
                            public void newContext(ISubject context) {
                            }

                            @Override
                            public void newObservation(IObservation observation, ISubject context) {
                            }

                            @Override
                            public void historyChanged(SessionActivity rootActivity, SessionActivity currentActivity) {
                            }

                        }, scope.getAppId());
            } else {
                // TODO set from a previously saved map
            }
        }

        @Override
        public void dispose() {
            if (this.listenerId != null) {
                scope.getMonitor().getIdentity().getParentIdentity(Session.class).getState().removeListener(this.listenerId);
            }
        }
    }

    @Action(id = "maybe", fires = Type.BOOLEAN)
    public static class Maybe extends KlabActionExecutor {

        Random random = new Random();

        double probability = 0.5;
        Object fired = null;

        public Maybe(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
            boolean pdef = false;
            for (String key : arguments.getUnnamedKeys()) {
                Object o = arguments.get(key);
                if (o instanceof Double && !pdef && ((Double) o) <= 1 && ((Double) o) >= 0) {
                    probability = (Double) o;
                    pdef = true;
                } else {
                    fired = o;
                }
            }
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
            if (random.nextDouble() < probability) {
                fire(fired == null ? DEFAULT_FIRE : fired, scope);
            } else {
                // fire anyway so that anything that's waiting can continue
                fire(false, scope);
            }
        }
    }

    @Action(id = "reset", fires = {})
    public static class Reset extends KlabActionExecutor {

        public Reset(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
//            ISession session = scope.getRuntimeScope().getSession();
            scope.tellSender(new AppReset(scope, scope.getAppId()));
        }
    }

    @Action(id = "info", fires = {})
    public static class Info extends KlabActionExecutor {

        public Info(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object arg : arguments.values()) {
                args.add(arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
            }
            scope.getRuntimeScope().getMonitor().info(args.toArray());
        }
    }

    @Action(id = "warning", fires = {})
    public static class Warning extends KlabActionExecutor {

        public Warning(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object arg : arguments.values()) {
                args.add(arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
            }
            scope.getRuntimeScope().getMonitor().warn(args.toArray());
        }
    }

    @Action(id = "error", fires = {})
    public static class Error extends KlabActionExecutor {

        public Error(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object arg : arguments.values()) {
                args.add(arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
            }
            scope.getRuntimeScope().getMonitor().error(args.toArray());
        }
    }

    @Action(id = "debug", fires = {})
    public static class Debug extends KlabActionExecutor {

        public Debug(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object arg : arguments.values()) {
                args.add(arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
            }
            scope.getRuntimeScope().getMonitor().debug(args.toArray());
        }
    }

    @Action(id = "pack", fires = IKActorsValue.Type.URN, description = "Prepares a downloadable payload and fires the URL to it when ready")
    public static class Pack extends KlabActionExecutor {

        public Pack(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(final IKActorsBehavior.Scope scope) {
            final List<Object> args = new ArrayList<>();
            boolean tables = false;
            if (arguments.containsKey("tables")) {
                // jeez
                Object tab = arguments.get("tables") instanceof KActorsValue
                        ? ((KActorsValue) arguments.get("tables")).evaluate(scope, identity, true)
                        : arguments.get("tables");
                tables = tab instanceof Boolean && ((Boolean) tab);
            }
            final boolean dtabs = tables;
            if (!tables) {
                for (Object arg : arguments.values()) {
                    args.add(arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
                }
            }
            new Thread(){

                @Override
                public void run() {

                    try {
                        File file = null;
                        if (dtabs) {
                            file = TableArtifact.exportMultiple(identity.getParentIdentity(Session.class).getState().getTables(),
                                    file);
                        } else {
                            file = Observations.INSTANCE.packObservations(args, identity.getMonitor());
                        }

                        if (file != null) {
                            fire(file, scope);
                        } else {
                            fail(scope);
                        }
                    } catch (Throwable t) {
                        fail(scope, t);
                    }
                }

            }.start();
        }
    }

    /**
     * Install a listener in a context that will fire an object to the sender whenever it is
     * resolved, optionally matching a type.
     * 
     * @author Ferd
     *
     */
    @Action(id = "when", fires = Type.OBSERVATION)
    public static class When extends KlabActionExecutor {

        String listener;

        public When(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
            // TODO filters
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
            this.listener = scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState()
                    .addApplicationGlobalListener(new ISessionState.Listener(){
                        @Override
                        public void newObservation(IObservation observation, ISubject context) {
                            /*
                             * Needs to intercept observations in any context. Not sure this works.
                             */
                            fire(observation, scope);
                        }

                        @Override
                        public void newContext(ISubject context) {
                        }

                        @Override
                        public void scaleChanged(ScaleReference scale) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void historyChanged(SessionActivity rootActivity, SessionActivity currentActivity) {
                        }

                    }, scope.getAppId());
        }

        @Override
        public void dispose() {
            scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState().removeGlobalListener(this.listener);
        }
    }

    /**
     * Separate out an argument list expected to specify a context plus other optional observations
     * into those that pertain to a context and those that pertain to further observations. Each
     * list may contain other lists or individual arguments, according to what they pertain to (the
     * context list only pertains to one observation, so it will only be individual objects). Any
     * <em>compatible</em> and <em>reasonable</em> combination of extents, extent specifiers and
     * observations passed will be used to merge/override the previous ones, so that shape contexts
     * can be adapted to resolutions and the like.
     * <p>
     * The keys in the context map will be processed as "urn", "observer", "observable", "space",
     * "time", "spaceunit", "timeunit", "artifact", "observation". The returned context map may only
     * contain one of (observable + scale), observer, or observation. Inconsistencies and
     * duplications cause a KlabIllegalArgumentException. The observable list can only contain
     * observables.
     * <p>
     * A "slightly" painful method to write.
     * 
     * @param arguments
     * @param scope
     * @param identity
     * @return
     */
    public static Pair<Map<String, Object>, List<String>> separateObservationArguments(IParameters<String> arguments, IKActorsBehavior.Scope scope,
            IActorIdentity<?> identity) {

        Map<String, Object> contextDefinition = new HashMap<>();
        List<String> observationArguments = new ArrayList<>();

        for (int i = 0; i < arguments.getUnnamedArguments().size(); i++) {
            Object o = arguments.getUnnamedArguments().get(i);
            if (o instanceof KActorsValue) {
                o = ((KActorsValue) o).evaluate(scope, identity, true);
            }

            String key = null;
            if (o instanceof IObservation) {
                if (o instanceof IDirectObservation && !contextDefinition.containsKey("observation")) {
                    key = "observation";
                    contextDefinition.put(key, o);
                } else {
                    throw new KlabIllegalArgumentException("cannot use observation " + o + " as a context parameter");
                }
            } else if (o instanceof Artifact) {
                if (((Artifact) o).getObjectArtifact() != null && !contextDefinition.containsKey("artifact")) {
                    key = "artifact";
                    contextDefinition.put(key, ((Artifact) o).getObjectArtifact());
                } else {
                    throw new KlabIllegalArgumentException("cannot use artifact " + o + " as a context parameter");
                }
            } else if (o instanceof IQuantity) {
                if (Units.INSTANCE.METERS.isCompatible(Unit.create(((IQuantity) o).getUnit()))) {
                    if (!contextDefinition.containsKey("spaceunit")) {
                        key = "spaceunit";
                        contextDefinition.put(key, o);
                    } else {
                        throw new KlabIllegalArgumentException(
                                "cannot use additional space unit " + o + " as a context parameter");
                    }
                } else if (Units.INSTANCE.SECONDS.isCompatible(Unit.create(((IQuantity) o).getUnit()))) {
                    if (!contextDefinition.containsKey("timeunit")) {
                        key = "timeunit";
                        contextDefinition.put(key, o);
                    } else {
                        throw new KlabIllegalArgumentException(
                                "cannot use additional time unit " + o + " as a context parameter");
                    }
                }
            } else if (o instanceof Quantity) {
                if (Units.INSTANCE.METERS.isCompatible(((Quantity) o).getUnit())) {
                    if (!contextDefinition.containsKey("spaceunit")) {
                        key = "spaceunit";
                        contextDefinition.put(key, ((Quantity) o).getUnitStatement());
                    } else {
                        throw new KlabIllegalArgumentException(
                                "cannot use additional space unit " + o + " as a context parameter");
                    }
                } else if (Units.INSTANCE.SECONDS.isCompatible(Unit.create(((IQuantity) o).getUnit()))) {
                    if (!contextDefinition.containsKey("timeunit")) {
                        key = "timeunit";
                        contextDefinition.put(key, ((Quantity) o).getUnitStatement());
                    } else {
                        throw new KlabIllegalArgumentException(
                                "cannot use additional time unit " + o + " as a context parameter");
                    }
                }
            } else if (o instanceof Grid) {
                if (!contextDefinition.containsKey("gridspecs")) {
                    key = "gridspecs";
                    contextDefinition.put(key, o);
                } else {
                    throw new KlabIllegalArgumentException("cannot use more than one grid specification as a context parameter");
                }
            } else if (o instanceof IObservable) {
                key = "observable";
                if (!contextDefinition.containsKey(key) && ((IObservable) o).is(IKimConcept.Type.SUBJECT)) {
                    contextDefinition.put(key, o);
                } else {
                    observationArguments.add(((IObservable) o).getDefinition());
                }
            } else if (o instanceof IKimObservable) {
                key = "observable";
                o = Observables.INSTANCE.declare((IKimObservable) o, identity.getMonitor());
                if (!contextDefinition.containsKey(key) && ((IObservable) o).is(IKimConcept.Type.SUBJECT)) {
                    contextDefinition.put(key, o);
                } else {
                    observationArguments.add(((IObservable) o).getDefinition());
                }
            } else if (o instanceof ISpace) {
                if (!contextDefinition.containsKey("space")) {
                    key = "space";
                    contextDefinition.put(key, o);
                } else {
                    throw new KlabIllegalArgumentException("cannot use additional space extent " + o + " as a context parameter");
                }
            } else if (o instanceof ITime) {
                if (!contextDefinition.containsKey("time")) {
                    key = "time";
                    contextDefinition.put(key, o);
                } else {
                    throw new KlabIllegalArgumentException("cannot use additional time extent " + o + " as a context parameter");
                }
            } else if (o instanceof Urn) {
                if (!contextDefinition.containsKey("urn")) {
                    String urn = o.toString();
                    if (Urns.INSTANCE.isUrn(urn)) {
                        if (!contextDefinition.containsKey("urn")) {
                            key = "urn";
                            contextDefinition.put(key, urn);
                            o = urn;
                        } else {
                            throw new KlabIllegalArgumentException("cannot use additional URN " + o + " as a context parameter");
                        }
                    } else {
                        IKimObject mo = Resources.INSTANCE.getModelObject(urn);
                        if (mo instanceof IAcknowledgement) {
                            if (!contextDefinition.containsKey("observer")) {
                                key = "observer";
                                o = mo;
                                contextDefinition.put(key, mo);
                            } else {
                                throw new KlabIllegalArgumentException(
                                        "cannot use additional observer " + o + " as a context parameter");
                            }
                        } else if (!contextDefinition.isEmpty() && (mo instanceof IModel || mo instanceof IViewModel)) {
                            observationArguments.add(((IKimObject) mo).getName());
                        } else {
                            throw new KlabIllegalArgumentException("cannot use argument " + o + " as a context parameter");
                        }
                    }
                } else {
                    throw new KlabIllegalArgumentException("cannot use additional URN " + o + " as a context parameter");
                }
            } else {
                if (o != null) {
                    String urn = o.toString();
                    if (Urns.INSTANCE.isUrn(urn)) {
                        if (!contextDefinition.containsKey("urn")) {
                            key = "urn";
                            contextDefinition.put(key, urn);
                            o = urn;
                        } else {
                            throw new KlabIllegalArgumentException("cannot use additional URN " + o + " as a context parameter");
                        }
                    } else {
                        IKimObject mo = Resources.INSTANCE.getModelObject(urn);
                        if (mo instanceof IAcknowledgement) {
                            if (!contextDefinition.containsKey("observer")) {
                                key = "observer";
                                o = mo;
                                contextDefinition.put(key, mo);
                            } else {
                                throw new KlabIllegalArgumentException(
                                        "cannot use additional observer " + o + " as a context parameter");
                            }
                        } else if (mo instanceof INamespace) {

                            /*
                             * TODO if regular namespace, should set the namespace of resolution; if
                             * scenario, set the scenario.
                             */

                        } else {
                            throw new KlabIllegalArgumentException("cannot use argument " + o + " as a context parameter");
                        }
                    }
                }
            }
        }

        IObservable observable = (IObservable) contextDefinition.get("observable");
        if (observable == null && contextDefinition.containsKey("observation")) {
            observable = ((IDirectObservation) contextDefinition.get("observation")).getObservable();
        }

        /*
         * simplify extents and resolutions into a scale, if any
         */
        ISpace space = (ISpace) contextDefinition.get("space");
        ITime time = (ITime) contextDefinition.get("time");
        if (contextDefinition.containsKey("urn")) {
            if (contextDefinition.containsKey("artifact")) {
                throw new KlabIllegalArgumentException("cannot have an artifact along with a URN in context specifications");
            }
            IObjectArtifact artifact = null;
            Iterator<Object> it = Actors.INSTANCE
                    .iterateResource(contextDefinition.get("urn").toString(), Klab.INSTANCE.getRootMonitor()).iterator();
            while(it.hasNext()) {
                Object o = it.next();
                if (o instanceof IObjectArtifact) {
                    artifact = (IObjectArtifact) o;
                }
                break;
            }
            if (artifact == null) {
                throw new KlabIllegalArgumentException("specified URN does not resolve to one or more object artifacts");
            }
            contextDefinition.put("artifact", artifact);
        }

        if (contextDefinition.containsKey("artifact")) {
            IScale scale = Scale.create(((IObjectArtifact) contextDefinition.get("artifact")).getGeometry());
            if (scale.getSpace() != null) {
                if (space == null) {
                    space = scale.getSpace();
                }
            }
            if (scale.getTime() != null) {
                if (time == null) {
                    time = scale.getTime();
                }
            }
        }
        if (contextDefinition.containsKey("gridspecs")) {
            if (space == null) {
                throw new KlabIllegalArgumentException(
                        "cannot apply a spatial grid without a spatial extent or a spatial artifact");
            } else {
                /*
                 * apply or re-apply the grid to the shape
                 */
                space = Space.create(space.getShape(), (Grid)contextDefinition.get("gridspecs"));
            }
        } else if (contextDefinition.containsKey("spaceunit")) {
            if (space == null) {
                throw new KlabIllegalArgumentException(
                        "cannot apply a spatial resolution without a spatial extent or a spatial artifact");
            } else {
                /*
                 * apply or re-apply the grid to the shape
                 */
                space = Space.create(space.getShape(), (IQuantity) contextDefinition.get("spaceunit"));
            }
        }
        if (contextDefinition.containsKey("timeunit")) {
            if (time != null) {
                time = Time.create(time.getStart(), time.getEnd(),
                        Time.resolution((IQuantity) contextDefinition.get("timeunit")));
            } else {
                ITimeInstant start = TimeInstant.create(new TimeInstant().getYear() - 1);
                ITimeInstant end = start.plus(1, Time.resolution(1, ITime.Resolution.Type.YEAR));
                time = Time.create(start, end, Time.resolution((IQuantity) contextDefinition.get("timeunit")));
            }
        }

        Map<String, Object> context = new HashMap<>();
        if (time != null || space != null) {
            if (observable == null) {
                observable = Observable.promote(Worldview.getGeoregionConcept());
            }
            context.put("observable", observable);
            if (time == null) {
                time = Time.create(new TimeInstant().getYear() - 1);
            }
            context.put("scale", Scale.create(time, space));
        } else if (contextDefinition.containsKey("observation")) {
            context.put("observation", contextDefinition.get("observation"));
        } else if (contextDefinition.containsKey("observer")) {
            if (contextDefinition.containsKey("observation") || time != null || space != null
                    || contextDefinition.containsKey("timeresolution") || contextDefinition.containsKey("spaceresolution")) {
                throw new KlabIllegalArgumentException("observers cannot be specified along with other context specifiers");

            }
            context.put("observer", contextDefinition.get("observer"));
        }

        /*
         * add the default observable if we have a single object artifact and no observable
         */

        return new Pair<>(context, observationArguments);
    }

}
