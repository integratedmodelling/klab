package org.integratedmodelling.klab.components.runtime.actors;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Future;

import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IQuantity;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITimePeriod;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ISessionState;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.AppReset;
import org.integratedmodelling.klab.components.runtime.actors.extensions.Artifact;
import org.integratedmodelling.klab.documentation.extensions.table.TableArtifact;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.SessionState;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.rest.DataflowState.Status;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.rest.SessionActivity;
import org.integratedmodelling.klab.scale.Scale;

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

        public Context(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {

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
                                fire(observation, false, scope.semaphore, scope.getSymbols(identity));
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

                        }, scope.appId);
            } else {

                Object arg = evaluateArgument(0, scope);
                if (arg instanceof Urn) {
                    try {
                        Future<IArtifact> future = ((Session) identity).getState().submit(((Urn) arg).getUrn());
                        fire(future.get(), true, scope.semaphore, scope.getSymbols(identity));
                    } catch (Throwable e) {
                        fail(scope.semaphore);
                    }
                } else {

                    /*
                     * TODO may have an Artifact wrapping an object artifact, a Scale, a Space
                     * extent/shape, an Envelope, and possibly a IKimQuantity for resolution, plus
                     * the same for time.
                     */
                    IObjectArtifact artifact = null;
                    IQuantity spaceResolution = null;
                    IQuantity timeResolution = null;
                    ITimePeriod time = null;
                    IObservable observable = null;
                    // more: shapes, time res, time spans, etc
                    for (Object o : arguments.getUnnamedArguments()) {
                        if (o instanceof KActorsValue) {
                            o = ((KActorsValue) o).evaluate(scope, identity, true);
                        }
                        if (o instanceof Artifact) {
                            artifact = ((Artifact) o).getObjectArtifact();
                        } else if (o instanceof IQuantity) {
                            if (Units.INSTANCE.METERS.isCompatible(Unit.create(((IQuantity) o).getUnit()))) {
                                spaceResolution = (IQuantity) o;
                            } else if (Units.INSTANCE.SECONDS.isCompatible(Unit.create(((IQuantity) o).getUnit()))) {
                                timeResolution = (IQuantity) o;
                            } // TODO
                        } else if (o instanceof IObservable) {
                            observable = (IObservable) o;
                        }

                        // TODO date, year - these should be keyed values
                    }

                    if (artifact != null) {

                        IScale scale = spaceResolution == null
                                ? Scale.create(artifact.getGeometry())
                                : Scale.create(artifact.getGeometry(), spaceResolution);

                        session.getState().resetContext();
                        if (scale.getSpace() != null) {
                            // avoid geocoding
                            if (!scale.getSpace().getShape().getMetadata().containsKey(IMetadata.DC_DESCRIPTION)) {
                                scale.getSpace().getShape().getMetadata().put(IMetadata.DC_DESCRIPTION, artifact.getName());
                            }
                            session.getState().setShape(scale.getSpace().getShape());
                        }
                        if (spaceResolution != null) {
                            session.getState().put(SessionState.SPACE_RESOLUTION_KEY, spaceResolution);
                        }
                    }

                    if (observable != null) {
                        try {
                            Future<IArtifact> future = ((Session) identity).getState().submit(observable.getDefinition());
                            IArtifact result = future.get();
                            fire(result, true, scope.semaphore, scope.getSymbols(identity));
                        } catch (Throwable e) {
                            fail();
                        }
                    } else {

                    }

                }
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
            + "queued, STARTED when it starts computing, and ABORTED in case of error.")

    public static class Submit extends KlabActionExecutor {

        String listenerId = null;

        public Submit(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {

            if (!arguments.getUnnamedKeys().isEmpty()) {
                fire(Status.WAITING, false, scope.semaphore, scope.getSymbols(identity));
                identity.getParentIdentity(Session.class).getState()
                        .submit(getUrnValue(arguments.get(arguments.getUnnamedKeys().get(0)), scope), (task, observation) -> {
                            if (observation == null) {
                                fire(Status.STARTED, false, scope.semaphore, scope.getSymbols(identity));
                            } else if (task.getMonitor().isInterrupted()) {
                                fire(Status.INTERRUPTED, false, scope.semaphore, scope.getSymbols(identity));
                            } else {
                                fire(observation, false, scope.semaphore, scope.getSymbols(identity));
                            }
                        }, (task, exception) -> {
                            fire(Status.ABORTED, false, scope.semaphore, scope.getSymbols(identity));
                        });
            }
        }

        private String getUrnValue(Object object, KlabActor.Scope scope) {
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

        public SetRole(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {

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
     */
    @Action(id = "scenarios", fires = Type.EMPTY, description = "Apply a session-specific role to one or more observables or observations.")
    public static class SetScenarios extends KlabActionExecutor {

        String listenerId = null;

        public SetScenarios(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {
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

        public Locate(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {

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
                                fire(ret, false, scope.semaphore, scope.getSymbols(identity));
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

                        }, scope.appId);
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

        public Maybe(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
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
        void run(KlabActor.Scope scope) {
            if (random.nextDouble() < probability) {
                fire(fired == null ? DEFAULT_FIRE : fired, true, scope.semaphore, scope.getSymbols(identity));
            } else {
                // fire anyway so that anything that's waiting can continue
                fire(false, true, scope.semaphore, scope.getSymbols(identity));
            }
        }
    }

    @Action(id = "reset", fires = {})
    public static class Reset extends KlabActionExecutor {

        public Reset(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {
            scope.sender.tell(new AppReset(scope, scope.appId));
        }
    }

    @Action(id = "info", fires = {})
    public static class Info extends KlabActionExecutor {

        public Info(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object arg : arguments.values()) {
                args.add(arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
            }
            scope.runtimeScope.getMonitor().info(args.toArray());
        }
    }

    @Action(id = "warning", fires = {})
    public static class Warning extends KlabActionExecutor {

        public Warning(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object arg : arguments.values()) {
                args.add(arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
            }
            scope.runtimeScope.getMonitor().warn(args.toArray());
        }
    }

    @Action(id = "error", fires = {})
    public static class Error extends KlabActionExecutor {

        public Error(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object arg : arguments.values()) {
                args.add(arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
            }
            scope.runtimeScope.getMonitor().error(args.toArray());
        }
    }

    @Action(id = "debug", fires = {})
    public static class Debug extends KlabActionExecutor {

        public Debug(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object arg : arguments.values()) {
                args.add(arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
            }
            scope.runtimeScope.getMonitor().debug(args.toArray());
        }
    }

    @Action(id = "pack", fires = IKActorsValue.Type.URN, description = "Prepares a downloadable payload and fires the URL to it when ready")
    public static class Pack extends KlabActionExecutor {

        public Pack(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(final KlabActor.Scope scope) {
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
                            fire(file, false, scope.semaphore, scope.getSymbols(identity));
                        } else {
                            fail();
                        }
                    } catch (Throwable t) {
                        fail(t);
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

        public When(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
            // TODO filters
        }

        @Override
        void run(KlabActor.Scope scope) {
            this.listener = scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState()
                    .addApplicationGlobalListener(new ISessionState.Listener(){
                        @Override
                        public void newObservation(IObservation observation, ISubject context) {
                            /*
                             * Needs to intercept observations in any context. Not sure this works.
                             */
                            fire(observation, false, scope.semaphore, scope.getSymbols(identity));
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

                    }, scope.appId);
        }

        @Override
        public void dispose() {
            scope.getMonitor().getIdentity().getParentIdentity(ISession.class).getState().removeGlobalListener(this.listener);
        }
    }

}
