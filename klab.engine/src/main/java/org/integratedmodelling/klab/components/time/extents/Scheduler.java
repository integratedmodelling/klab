package org.integratedmodelling.klab.components.time.extents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.IScheduler;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.ActorReference;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.debugger.Debug;
import org.integratedmodelling.klab.engine.debugger.Debugger.Watcher;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.rest.SchedulerNotification;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.Graph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.joda.time.DateTime;

import akka.actor.typed.ActorRef;

/**
 * Scheduler for actors in either real or mock time. Akka does not allow the latter so we need to
 * roll up our own.
 * 
 * @author ferdinando.villa
 *
 */
public class Scheduler implements IScheduler {

    private Type type;
    private long startTime = -1;
    private long endTime = -1;
    private Synchronicity synchronicity = Synchronicity.SYNCHRONOUS;
    private AtomicBoolean stopped = new AtomicBoolean(false);
    private int cursor = 0;
//    private ExecutorService executor;
    private WaitStrategy waitStrategy;
    private boolean finished = false;

    /*
     * used to track change in states that don't have processes connected
     */
    class Dependencies {
        public ObservedConcept observable;
        public Set<ObservedConcept> precursors = new HashSet<>();
        public Actuator actuator;

        @Override
        public String toString() {
            return "[D " + observable + " (" + precursors + ")]";
        }

    }

    /*
     * Period of all subscribed actuators, to compute resolution.
     */
    Set<Long> periods = new HashSet<>();

    class Registration {

        Actuator actuator;
        List<Actuator.Computation> computations;
        IDirectObservation target;
        IScale scale;
        Function<IMonitor, Collection<ObservedConcept>> action;
        long tIndex = 0;
        long endTime;

        /*
         * this is set by tagExpirations() just before running each set of registrations in a slot.
         * True means that this is the last active (rounds == 0) registration for its specified time
         * in the slot.
         */
        boolean endsPeriod = false;

        IRuntimeScope scope;
        // rounds around the wheel; ready to fire when rounds == 0
        int rounds;

        /*
         * Used to sort by milliseconds from beginning of slot time so that temporal sequence is
         * respected.
         */
        long delayInSlot;
        private Observation recipient;
        private int dependencyOrder;
        public ITime time;

        /**
         * Register a scheduled action from a behavior.
         * 
         * @param observation
         * @param scheduled
         * @param scale
         * @param scope
         * @param endtime
         */
        public Registration(Observation observation, IBehavior.Action scheduled, IScale scale,
                IRuntimeScope scope,
                long endtime) {

            this.scale = scale;
            this.endTime = endtime;
            this.scope = scope;
            this.recipient = observation;

            action = new Function<IMonitor, Collection<ObservedConcept>>(){

                @Override
                public Collection<ObservedConcept> apply(IMonitor monitor) {

                    Set<ObservedConcept> ret = new HashSet<>();

                    /*
                     * If target is dead, return
                     */
                    if (recipient instanceof IDirectObservation
                            && !((IDirectObservation) recipient).isActive()) {
                        return ret;
                    }

                    /*
                     * 2. Set the context at() the current time. This will also need to expose any
                     * affected outputs that move at a different (context) speed through a rescaling
                     * wrapper. Done within the context, which uses its current target to establish
                     * the specific view of the context.
                     */
                    ILocator transitionScale = scale.at(time);
                    IRuntimeScope transitionContext = scope.locate(transitionScale, monitor);
                    Set<IObservation> changed = new HashSet<>();
                    ActorRef<KlabMessage> sender = ((ActorReference) ((Observation) observation.getScope()
                            .getRootSubject()).getActor()).actor;

                    /*
                     * RUN THE ACTION
                     */
                    String appId = null /* TODO! */;
                    recipient.getActor().tell(new KActorsMessage(sender, scheduled.getId(), null, null,
                            new KlabActor.Scope(observation, appId, transitionContext, null), appId));

                    recipient.finalizeTransition((IScale) transitionScale);

                    /*
                     * 4. TODO this will always be empty - notify whatever has changed.
                     */
                    if (recipient instanceof IDirectObservation
                            && !((IDirectObservation) recipient).isActive()) {
                        // TODO target went MIA - notify relatives
                    } else {

                        for (IObservation observation : changed) {

                            ret.add(new ObservedConcept(observation.getObservable(),
                                    observation instanceof IObservationGroup
                                            ? Mode.INSTANTIATION
                                            : Mode.RESOLUTION));

                            IObservation parent = scope.getParentArtifactOf(observation);
                            if (parent != null && scope.getWatchedObservationIds().contains(parent.getId())) {

                                ObservationChange change = new ObservationChange();
                                change.setContextId(scope.getRootSubject().getId());
                                change.setId(observation.getId());
                                change.setTimestamp(time.getEnd().getMilliseconds());

                                if (observation instanceof IState) {
                                    change.setType(ObservationChange.Type.ValueChange);
                                } else if (observation instanceof IDirectObservation
                                        && !((IDirectObservation) observation).isActive()) {
                                    change.setType(ObservationChange.Type.Termination);
                                }

                                ISession session = scope.getMonitor().getIdentity()
                                        .getParentIdentity(ISession.class);
                                session.getMonitor()
                                        .send(Message.create(session.getId(),
                                                IMessage.MessageClass.ObservationLifecycle,
                                                IMessage.Type.ModifiedObservation, change));
                            } else {
                                ((Observation) observation).setDynamic(true);
                            }
                        }
                    }

                    // TODO determine what has changed

                    return ret;
                }
            };
        }

        /**
         * Register a computation from an actuator
         * 
         * @param actuator
         * @param computation
         * @param target
         * @param scale
         * @param scope
         * @param endtime
         */
        public Registration(Actuator actuator, List<Actuator.Computation> computation,
                IDirectObservation target,
                IScale scale, IRuntimeScope scope, long endtime) {

            this.actuator = actuator;
            this.scale = scale;
            this.target = target;
            this.endTime = endtime;
            this.scope = scope;
            this.computations = computation;

            action = new Function<IMonitor, Collection<ObservedConcept>>(){

                @Override
                public Collection<ObservedConcept> apply(IMonitor monitor) {

                    Set<ObservedConcept> ret = new HashSet<>();
                    List<IObservation> changed = new ArrayList<>();

                    /*
                     * If target is dead, return
                     */
                    if (target != null && !target.isActive()) {
                        return ret;
                    }

                    /*
                     * 2. Set the context at() the current time. This will also need to expose any
                     * affected outputs that move at a different (context) speed through a rescaling
                     * wrapper. Done within the context, which uses its current target to establish
                     * the specific view of the context.
                     * 
                     * Time is null when the registration is scheduled at termination
                     */
                    ILocator transitionScale = time == null
                            ? Registration.this.scale.termination()
                            : Registration.this.scale.at(time);

                    IRuntimeScope transitionContext = scope;
                    transitionContext = scope.locate(transitionScale, monitor);

                    /*
                     * TODO if the target is a group of events, it has been filtered to only contain
                     * those that occur in this transition. They must now be resolved in the
                     * localized scope (either using a cached dataset or from scratch) and be
                     * notified as they appear.
                     */
                    if (target instanceof IObservationGroup
                            && target.getObservable().is(IKimConcept.Type.EVENT)) {
                        // TODO
                    }

                    // // ensure we have the names we expect
                    // transitionContext = actuator.localizeNames(transitionContext);

                    monitor.debug("running " + actuator
                            + (time == null
                                    ? " at termination"
                                    : (" at [" + new Date(time.getStart().getMilliseconds()) + " - "
                                            + new Date(time.getEnd().getMilliseconds()) + "]")));

                    IArtifact artifact = null;
//                    IArtifact ctarget = null;

                    /*
                     * 3. Run all contextualizers in the context that react to transitions; check
                     * for signs of life at each step. Anything enqueued here is active so no
                     * further check is necessary.
                     */
                    for (Actuator.Computation computation : computations) {

                        if (computation.variable != null) {
                            transitionContext.getVariables().put(computation.targetId, computation.variable);
                            continue;
                        }

                        if (artifact == null) {
                            artifact = computation.target;
//                            ctarget = computation.target;
                        }

                        /*
                         * substitute the target for the next computation if we're using layers
                         */
                        artifact = actuator.runContextualizer(computation.contextualizer,
                                computation.observable,
                                computation.resource, artifact, actuator.setupScope(artifact, transitionContext), (IScale) transitionScale);

                        if (computation.target instanceof IDirectObservation
                                && !((IDirectObservation) computation.target).isActive()) {
                            changed.add((IObservation) computation.target);
                            ret.add(new ObservedConcept(((IObservation) computation.target).getObservable(),
                                    ((IObservation) computation.target) instanceof ObservationGroup
                                            ? Mode.INSTANTIATION
                                            : Mode.RESOLUTION));
                            // TODO if in group, group has changed too
                            break;
                        } else if (computation.target instanceof IProcess) {
                            // report all changed states that were affected or created.
                            for (IState state : scope.getAffectedArtifacts(
                                    ((IProcess) computation.target).getObservable().getType(),
                                    IState.class)) {
                                if (state.getLastUpdate() > transitionContext.getScale().getTime().getStart()
                                        .getMilliseconds()) {
                                    changed.add(state);
                                    ret.add(new ObservedConcept(state.getObservable(), Mode.RESOLUTION));
                                }
                            }
                        } else if (computation.target instanceof IState
                                && actuator.getObservable().is(IKimConcept.Type.CHANGE)) {
                            if (computation.target.getLastUpdate() > transitionContext.getScale().getTime()
                                    .getStart()
                                    .getMilliseconds()) {
                                changed.add((IObservation) computation.target);
                                ret.add(new ObservedConcept(
                                        ((IObservation) computation.target).getObservable(),
                                        actuator.getMode()));
                            }
                        }

                    }

                    // /*
                    // * report only states for now - must become discriminating and intelligent. If
                    // * in folder...
                    // *
                    // */
                    // if (artifact instanceof IState /*
                    // * TODO check if changes happened independent of type
                    // */) {
                    // changed.add((IObservation) ctarget);
                    // ret.add(new ObservedConcept(((IObservation) ctarget).getObservable(),
                    // ((IObservation) ctarget) instanceof ObservationGroup ? Mode.INSTANTIATION
                    // : Mode.RESOLUTION));
                    // }

                    /*
                     * 4. Notify whatever has changed.
                     */
                    if (target instanceof IDirectObservation && !((IDirectObservation) target).isActive()) {
                        // TODO target went MIA - notify relatives
                    } else {

                        for (IObservation observation : changed) {

                            ((Observation) observation).finalizeTransition((IScale) transitionScale);

                            IObservation parent = scope.getParentArtifactOf(observation);
                            if (parent != null && scope.getWatchedObservationIds().contains(parent.getId())) {

                                ObservationChange change = new ObservationChange();
                                change.setContextId(scope.getRootSubject().getId());
                                change.setId(observation.getId());
                                change.setTimestamp(time.getEnd().getMilliseconds());

                                if (observation instanceof IState) {
                                    change.setType(ObservationChange.Type.ValueChange);
                                } else if (observation instanceof IDirectObservation
                                        && !((IDirectObservation) observation).isActive()) {
                                    change.setType(ObservationChange.Type.Termination);
                                }

                                ISession session = scope.getMonitor().getIdentity()
                                        .getParentIdentity(ISession.class);
                                session.getMonitor()
                                        .send(Message.create(session.getId(),
                                                IMessage.MessageClass.ObservationLifecycle,
                                                IMessage.Type.ModifiedObservation, change));
                            } else {
                                ((Observation) observation).setDynamic(true);
                            }
                        }
                    }

                    return ret;
                }
            };
        }

        public Collection<ObservedConcept> run(IMonitor monitor) {

            if (Debug.INSTANCE.isDebugging()) {
                for (Watcher watch : ((Observation) runtimeScope.getRootSubject()).getWatches()) {
                    watch.newTransition(time, this.target);
                }
            }

            if (synchronicity == Synchronicity.SYNCHRONOUS) {
                /*
                 * run in current thread, return when finished
                 */
                return action.apply(monitor);
            } else if (synchronicity == Synchronicity.ASYNCHRONOUS) {
                throw new KlabUnimplementedException("asynchronous scheduling not implemented");
            } else if (synchronicity == Synchronicity.TIME_SYNCHRONOUS) {
                throw new KlabUnimplementedException("time-synchronous scheduling not implemented");
            }

            return null;

        }

        public int compare(Registration o) {

            if (o == this) {
                return 0;
            }

            /*
             * Simultaneous actions happen in order of dependency
             */
            if (delayInSlot == o.delayInSlot) {
                return Integer.compare(dependencyOrder, o.dependencyOrder);
            }

            return Long.compare(delayInSlot, o.delayInSlot);
        }

        public void setDependencyOrder(int i) {
            this.dependencyOrder = i;
        }

        public boolean runsAtTermination() {
            for (Actuator.Computation computation : this.computations) {
                if (computation.schedule != null && computation.schedule.isEnd()) {
                    return true;
                }
            }
            return false;
        }

//        private Scheduler getEnclosingInstance() {
//            return Scheduler.this;
//        }
    }

    private List<Registration> registrations = new ArrayList<>();

    public static final long DEFAULT_RESOLUTION = TimeUnit.NANOSECONDS.convert(10, TimeUnit.MILLISECONDS);
    public static final int DEFAULT_WHEEL_SIZE = 512;
    public static final int MAX_HASH_WHEEL_SIZE = 2048;

    private List<Registration>[] wheel;
    private int wheelSize = 0;
    private IMonitor monitor;
    private long resolution;
    private String contextId;
    private ISession session;
    private int activeRegistrations;
    // registrations scheduled to be run after the last transition. So far only
    // views can do this.
    private Map<Pair<IDirectObservation, Dataflow>, List<Registration>> terminationRegistrations = new HashMap<>();
    private IRuntimeScope runtimeScope;

    Map<Pair<IDirectObservation, Dataflow>, List<Registration>> dataflows;

    public Scheduler(IRuntimeScope runtimeScope, String contextId, IResolutionScope scope, IMonitor monitor) {
        this.contextId = contextId;
        this.runtimeScope = runtimeScope;
        this.session = monitor.getIdentity().getParentIdentity(ISession.class);
        Date now = new Date();
        this.monitor = monitor;
        ITime time = scope.getScale().getTime();
        this.type = time.is(ITime.Type.REAL) ? Type.REAL_TIME : Type.MOCK_TIME;
        this.startTime = time.getStart() == null ? 0 : time.getStart().getMilliseconds();
        if (time.getEnd() != null) {
            this.endTime = time.getEnd().getMilliseconds();
        }
        if (this.type == Type.REAL_TIME) {
            this.waitStrategy = new WaitStrategy.YieldingWait();
            if (this.startTime > 0 && now.getTime() > this.startTime) {
                this.startTime = 0;
            }
        }
    }

    @Override
    public Synchronicity getSynchronicity() {
        return synchronicity;
    }

    public void schedule(Action action, Observation targetObservation, Time time, RuntimeScope runtimeScope) {

        /*
         * make a scale from the scheduling specs and merge
         */
        final IScale overall = runtimeScope.getResolutionScale();
        final IScale actionScale = Scale.substituteExtent(overall, time);
        IScale scale = actionScale.merge(overall);

        /*
         * proceed as with an actuator
         */
        ITimeInstant start = scale.getTime().getStart();
        ITimeInstant end = scale.getTime().getStart();
        ITimeDuration step = scale.getTime().getStep();

        if (start == null
                || (overall.getTime().getStart() != null && start.isBefore(overall.getTime().getStart()))) {
            start = overall.getTime().getStart();
        }
        if (end == null
                || (overall.getTime().getEnd() != null && start.isAfter(overall.getTime().getEnd()))) {
            end = overall.getTime().getEnd();
        }
        if (step == null) {
            step = overall.getTime().getStep();
        }

        if (step /* still */ == null) {
            /*
             * nothing can occur, nothing to do (TODO: except maybe finalization)
             */
            return;
        }

        final long endTime = overall.getTime().getEnd() == null
                ? -1
                : overall.getTime().getEnd().getMilliseconds();

        registrations.add(new Registration(targetObservation, action, scale, runtimeScope, endTime));
    }

    public void schedule(final Actuator actuator, final List<Actuator.Computation> computations,
            IRuntimeScope scope) {

        scope = scope.targetForChange();

        /*
         * overall scale fills in any missing info.
         */
        final IScale overall = scope.getResolutionScale();

        /*
         * model and individual computables determine the temporal aspects of the geometry. By now
         * that should be entirely captured in the model coverage. View model use the overall scale
         * as they have none of their own.
         */
        IScale modelScale = actuator.getModel() == null
                ? null
                : actuator.getModel().getCoverage(scope.getMonitor());
        if (actuator.getModel() != null && actuator.getModel().getViewModel() != null) {
            modelScale = overall;
        }

        /*
         * should not be the case if we get here at all, but who knows. TODO: if there is no
         * explicit temporal nature, a contextualizer should check if any of the dependencies have
         * changed OR has changed data at T (even if computed before) and exec again only if so.
         * This should be done by using the provenance graph for each artifact and checking those,
         * rather than using a value change resulting from the actual update.
         */
        if (overall.getTime() == null || modelScale.getTime() == null) {
            return;
        }

        /*
         * complete the actuator's scale with the overall one, adding extent boundaries and the like
         * but keeping the resolution and representation.
         */
        IScale scale = modelScale.merge(overall);

        ITimeInstant start = scale.getTime().getStart();
        ITimeInstant end = scale.getTime().getStart();

        if (start == null
                || (overall.getTime().getStart() != null && start.isBefore(overall.getTime().getStart()))) {
            start = overall.getTime().getStart();
        }
        if (end == null
                || (overall.getTime().getEnd() != null && start.isAfter(overall.getTime().getEnd()))) {
            end = overall.getTime().getEnd();
        }

        IObservation targetObservation = (IObservation) scope.getTargetArtifact();
        // can be null with void actuators
        if (targetObservation != null && (targetObservation.getObservable().is(IKimConcept.Type.PROCESS)
                || targetObservation.getObservable().is(IKimConcept.Type.EVENT)
                || targetObservation.getObservable().is(IKimConcept.Type.QUALITY))) {
            targetObservation = scope.getContextObservation();
        }

        final IDirectObservation target = (IDirectObservation) targetObservation;
        final long endTime = overall.getTime().getEnd() == null
                ? -1
                : overall.getTime().getEnd().getMilliseconds();

        registrations.add(new Registration(actuator, computations, target, scale, scope, endTime));
    }

    private void schedule() {

        /*
         * 1. Collect all dataflows from actuators and pair them to their actuators. Preserve order
         * of registration
         */
        dataflows = new LinkedHashMap<>();

        for (Registration registration : registrations) {
            Dataflow dataflow = registration.actuator.getDataflow();
            Pair<IDirectObservation, Dataflow> key = new Pair<>(registration.target, dataflow);
            if (!dataflows.containsKey(key)) {
                dataflows.put(key, new ArrayList<>());
            }
            dataflows.get(key).add(registration);
        }

        /*
         * 2. Schedule each dataflow independently to align internal dependencies and change
         * detection.
         */
        for (Pair<IDirectObservation, Dataflow> dataflow : dataflows.keySet()) {
            schedule(dataflow, dataflows.get(dataflow));
        }
    }

    /*
     * one-shot scheduling, re-entrant
     */
    @SuppressWarnings("unchecked")
    private void schedule(Pair<IDirectObservation, Dataflow> dataflow, List<Registration> actions) {

        long longest = 0;

        List<Number> periods = new ArrayList<>();
        List<Registration> regs = separateTerminationRegistrations(dataflow, actions);
        IRuntimeScope scope = actions.get(0).scope;

        /*
         * all registrations should be scheduled in order of dependency. As long as there is only
         * one resolution this is also the order of registration, but if there are successive
         * resolutions for change this no longer holds.
         */
        if (dataflow != null) {
            regs = computeDynamicDependencyOrder(regs, scope.getDependencyGraph());
        }

        if (this.wheel == null) {

            /*
             * Size the wheel properly
             */
            for (Registration registration : this.registrations) {
                long interval = registration.scale.getTime().getResolution() == null
                        ? (registration.scale.getTime().getEnd().getMilliseconds()
                                - registration.scale.getTime().getStart().getMilliseconds())
                        : registration.scale.getTime().getResolution().getSpan();
                periods.add(interval);
                if (longest < interval) {
                    longest = interval;
                }
            }

            if (!periods.isEmpty()) {
                long resolution = NumberUtils.gcd(NumberUtils.longArrayFromCollection(periods));
                if (this.resolution < resolution) {
                    this.resolution = resolution;
                }
            }

            /*
             * wheel size should accommodate the longest interval @the chosen resolution, but we cap
             * it at MAX_HASH_WHEEL_SIZE. Keep it in powers of 2 for predictable performance and
             * geek factor.
             * 
             * TODO review to choose a power of two between 2^(4-15) based on how small the
             * resolution is compared to the scale
             */
            this.wheelSize = DEFAULT_WHEEL_SIZE; // Math.min(NumberUtils.nextPowerOf2((int) (longest
                                                 // /
                                                 // resolution)),
                                                 // MAX_HASH_WHEEL_SIZE);
            monitor.debug(
                    "created scheduler hash wheel of size " + this.wheelSize + " for resolution = "
                            + this.resolution);
            this.wheel = new List[this.wheelSize];
        }

        /*
         * now for the actual scheduling of the first step of each registration
         */
        int i = 0;
        for (Registration registration : regs) {
            registration.setDependencyOrder(i++);
            this.activeRegistrations++;
            reschedule(registration, true);
        }

    }

    private List<Registration> separateTerminationRegistrations(Pair<IDirectObservation, Dataflow> dataflow,
            List<Registration> registrations) {

        List<Registration> ret = new ArrayList<>();
        this.terminationRegistrations.put(dataflow, new ArrayList<>());

        for (Registration registration : registrations) {
            if (registration.runsAtTermination()) {
                this.terminationRegistrations.get(dataflow).add(registration);
            } else {
                ret.add(registration);
            }
        }

        return ret;
    }

    private List<Registration> computeDynamicDependencyOrder(List<Registration> registrations,
            Graph<IObservedConcept, DefaultEdge> dependencies) {

        /*
         * Create a new dependency graph taking the dependency relationships from the original
         * dataflow, considering only the actuators involved in the schedule.
         */
        Graph<IObservedConcept, DefaultEdge> dynamicDependencies = new DefaultDirectedGraph<>(
                DefaultEdge.class);
        for (Registration r : registrations) {
            if (r.actuator != null) {
                dynamicDependencies
                        .addVertex(new ObservedConcept(r.actuator.getObservable(), r.actuator.getMode()));
            }
        }

        for (IObservedConcept oc : dynamicDependencies.vertexSet()) {
            // if (dependencies.vertexSet().contains(oc)) {
            for (DefaultEdge dc : dependencies.incomingEdgesOf(oc)) {
                if (dynamicDependencies.containsVertex(dependencies.getEdgeSource(dc))) {
                    dynamicDependencies.addEdge(dependencies.getEdgeSource(dc), oc);
                }
            }
            // }
        }

        /*
         * For any observable coming from a secondary dataflow, add any dependency coming from the
         * rule: if change in X depends on Y and we have change in Y, then change in X depends on
         * change in Y. If the dependency creates a cycle, don't add it and cross fingers.
         */
        List<IObservedConcept> ccs = new ArrayList<>(dynamicDependencies.vertexSet());
        for (IObservedConcept oc : dynamicDependencies.vertexSet()) {

            /*
             * may not have all the dependencies. Check if: 1) it's change in y; 2) we have y in the
             * original dependencies; and 3) we have any scheduled observable X that depend on y. If
             * so, try adding the dependency between change in X and change in Y. If the dependency
             * screws things up, warn and don't add it.
             */
            if (oc.getObservable().is(IKimConcept.Type.CHANGE)) {

                /*
                 * the concept another scheduled observable may depend on.
                 */
                ObservedConcept changing = new ObservedConcept(
                        Observable
                                .promote(Observables.INSTANCE.getDescribedType(oc.getObservable().getType())),
                        Mode.RESOLUTION);

                for (IObservedConcept dc : ccs) {

                    if (dc.equals(oc)) {
                        continue;
                    }

                    if (dependencies.containsVertex(changing)
                            && Graphs.dependsOn(dc, changing, dependencies)) {

                        /*
                         * try adding the dependency on a clone. If that creates cycles, warn and
                         * move on.
                         */
                        Graph<IObservedConcept, DefaultEdge> clone = Graphs.copy(dynamicDependencies,
                                new DefaultDirectedGraph<>(DefaultEdge.class));
                        clone.addEdge(oc, dc);
                        CycleDetector<IObservedConcept, DefaultEdge> cycles = new CycleDetector<>(clone);
                        if (cycles.detectCycles()) {
                            monitor.warn("Implicit temporal dependency of "
                                    + Concepts.INSTANCE.getDisplayLabel(oc.getObservable().getType()) + " on "
                                    + Concepts.INSTANCE.getDisplayLabel(dc.getObservable().getType())
                                    + " ignored because of circular dependencies: consider review of model logics");
                            continue;
                        }
                        dynamicDependencies = clone;
                    }
                }
            }
        }

        Map<ObservedConcept, Registration> index = new HashMap<>();

        for (Registration r : registrations) {
            if (r.actuator != null) {
                index.put(new ObservedConcept(r.actuator.getObservable(), r.actuator.getMode()), r);
            }
        }

        List<Registration> ret = new ArrayList<>();
        TopologicalOrderIterator<IObservedConcept, DefaultEdge> order = new TopologicalOrderIterator<IObservedConcept, DefaultEdge>(
                dynamicDependencies);

        while(order.hasNext()) {
            ret.add(index.get(order.next()));
        }

        return ret;
    }

    @Override
    public void run(IMonitor monitor) {

        if (registrations.size() < 1) {
            return;
        }

        schedule();

        if (startTime == 0 && type == Type.REAL_TIME) {
            startTime = DateTime.now().getMillis();
        }

        /*
         * notify start
         */
        SchedulerNotification notification = new SchedulerNotification();
        notification.setContextId(contextId);
        notification.setType(SchedulerNotification.Type.STARTED);
        notification.setResolution(resolution);

        monitor.info("Temporal transitions starting");

        monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
                IMessage.Type.SchedulingStarted, notification));

        long time = startTime;
        long lastAdvanced = startTime;

        while(this.activeRegistrations > 0) {

            if (monitor.isInterrupted() || monitor.getWaitTime() > Configuration.INSTANCE.getMaxWaitTime()) {
                this.registrations.clear();
                this.dataflows.clear();
                this.finished = true;
                return;
            }

            if (this.wheel[cursor] != null && !this.wheel[cursor].isEmpty()) {

                List<Registration> regs = tagExpirations(this.wheel[cursor]);

                this.wheel[cursor].clear();

                Set<IObservedConcept> changed = new HashSet<>();

                long delay = 0;

                for (Registration registration : regs) {

                    if (monitor.isInterrupted()) {
                        this.registrations.clear();
                        this.finished = true;
                        return;
                    }

                    if (registration.rounds == 0) {

                        if (type == Type.REAL_TIME && registration.delayInSlot > delay) {
                            // FIXME NO! Must run in batches all those starting at the same time,
                            // then wait
                            waitStrategy.waitUntil(time + delay);
                            delay += registration.delayInSlot;
                        }

                        changed.addAll(registration.run(monitor));
                        if (lastAdvanced < registration.time.getEnd().getMilliseconds()) {
                            lastAdvanced = registration.time.getEnd().getMilliseconds();
                        }

                        ITime toRun = registration.time;
                        
                        Map<IObservedConcept, IObservation> catalog = registration.scope.getCatalog();

                        reschedule(registration, false);

                        /*
                         * check for implicitly affected actuators. This must be done when the last
                         * registration with each particular delay has finished.
                         */
                        if (toRun != null && registration.endsPeriod && changed.size() > 0) {

                            Set<IObservedConcept> computed = new HashSet<>();
                            for (IObservedConcept tracked : registration.scope.getImplicitlyChangingObservables()) {
                                computeImplicitDependents(tracked, changed, computed, toRun,
                                        registration.scope,
                                        registration.scope.getDependencyGraph(), catalog,
                                        registration.actuator.getDataflow());

                                if (monitor.isInterrupted()) {
                                    this.registrations.clear();
                                    this.dataflows.clear();
                                    this.finished = true;
                                    return;
                                }

                            }

                            changed.clear();
                        }

                    } else {
                        registration.rounds--;
                        this.wheel[cursor].add(registration);
                        if (this.wheel[cursor].size() > 1) {
                            Collections.sort(this.wheel[cursor], new Comparator<Registration>(){
                                @Override
                                public int compare(Registration o1, Registration o2) {
                                    return o1.compare(o2);
                                }
                            });
                        }
                    }
                }

                SchedulerNotification passed = new SchedulerNotification();
                passed.setType(SchedulerNotification.Type.TIME_ADVANCED);
                passed.setContextId(contextId);
                passed.setCurrentTime(lastAdvanced);
                monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
                        IMessage.Type.ScheduleAdvanced, passed));

            }

            cursor = (cursor + 1) % wheelSize;
            time += resolution;

            if (endTime > 0 && lastAdvanced >= endTime) {
                break;
            }

            if (waitStrategy != null) {
                waitStrategy.waitUntil(time);
            }
        }

        /*
         * run anything registered after termination. We don't do anything else as these have access
         * to the entire context.
         */
        for (Pair<IDirectObservation, Dataflow> dataflow : dataflows.keySet()) {
            for (Registration registration : terminationRegistrations.get(dataflow)) {
                registration.run(monitor);
            }
        }

        // don't do this again if we make further observations later
        this.registrations.clear();
        this.dataflows.clear();

        this.finished = true;

        /*
         * notify end
         */
        notification.setType(SchedulerNotification.Type.FINISHED);
        notification.setCurrentTime(time);
        monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
                IMessage.Type.SchedulingFinished, notification));
    }

    private List<Registration> tagExpirations(List<Registration> set) {
        List<Registration> ret = new ArrayList<>(set);
        Registration lastActive = null;
        for (int i = 0; i < ret.size(); i++) {
            Registration registration = ret.get(i);
            registration.endsPeriod = false;
            if (registration.rounds == 0) {
                if (lastActive != null && registration.delayInSlot > lastActive.delayInSlot) {
                    lastActive.endsPeriod = true;
                }
                lastActive = registration;
            }
        }
        if (lastActive != null) {
            lastActive.endsPeriod = true;
        }
        return ret;
    }

    /**
     * Analyze the precursors of the tracked observable and recursively recompute (depth-first) it
     * or any precursors that is in the tracked list and depends on any of the changed observations,
     * unless it's in computed. Add any newly recomputed to the computed set for reentrancy.
     * 
     * @param tracked
     * @param changed
     * @param computed
     */
    private void computeImplicitDependents(IObservedConcept observable, Set<IObservedConcept> changed,
            Set<IObservedConcept> computed, ITime time, IRuntimeScope runtimeScope,
            Graph<IObservedConcept, DefaultEdge> dependencies, Map<IObservedConcept, IObservation> catalog,
            IDataflow<?> dataflow) {

        if (monitor.isInterrupted()) {
            return;
        }

        if (!computed.contains(observable)) {
            boolean recompute = false;
            for (IObservedConcept precursor : getPrecursors(dependencies, observable)) {
                computed.add(observable);
                computeImplicitDependents(precursor, changed, computed, time, runtimeScope, dependencies,
                        catalog,
                        dataflow);
                if (changed.contains(precursor) && !changed.contains(observable)) {
                    IObservation pre = catalog.get(precursor);
                    IObservation post = catalog.get(observable);
                    if (pre != null && post != null && pre.getLastUpdate() > post.getLastUpdate()) {
                        recompute = true;
                    }
                }
            }
            if (recompute) {
                if (Configuration.INSTANCE.isEchoEnabled()) {
                    System.out.println("RECOMPUTING " + observable);
                }
                reinitializeObservation(observable.getObservable(), getActuator(observable, dependencies),
                        time,
                        runtimeScope, dataflow);
                changed.add(observable);
            }
        }
    }

    /**
     * ACHTUNG: the passed runtime scope is for the original dependent and must be retargeted to the
     * target.
     * 
     * @param observable
     * @param actuator
     * @param time
     * @param runtimeScope
     */
    private void reinitializeObservation(IObservable observable, Actuator actuator, ITime time,
            IRuntimeScope runtimeScope, IDataflow<?> dataflow) {

        Pair<String, IArtifact> targetd = runtimeScope.findArtifact(observable);
        if (targetd != null) {

            IObservation target = (IObservation) targetd.getSecond();
            ILocator transitionScale = runtimeScope.getResolutionScale().at(time);
            IRuntimeScope transitionContext = runtimeScope.targetToObservation(target).locate(transitionScale,
                    monitor);
            long lastUpdate = target.getLastUpdate();

            /*
             * TODO if the target is a group of events, it has been filtered to only contain those
             * that occur in this transition. They must now be resolved in the localized scope
             * (either using a cached dataset or from scratch) and be notified as they appear.
             */
            if (target instanceof IObservationGroup && target.getObservable().is(IKimConcept.Type.EVENT)) {
                // TODO
            }

            // transitionContext = actuator.localizeNames(transitionContext);
            IArtifact artifact = null;

            // we re-run the entire initialization sequence.
            for (Actuator.Computation computation : actuator.getContextualizers()) {

                if (computation.variable != null) {
                    transitionContext.getVariables().put(computation.targetId, computation.variable);
                    continue;
                }

                if (artifact == null) {
                    artifact = computation.target;
                }

                /*
                 * substitute the target for the next computation if we're using layers
                 */
                artifact = actuator.runContextualizer(computation.contextualizer, computation.observable,
                        computation.resource, artifact, transitionContext, (IScale) transitionScale);

                if (monitor.isInterrupted()) {
                    return;
                }

                // ((Observation) artifact).finalizeTransition((IScale) transitionScale);

            }

            if (target.getLastUpdate() > lastUpdate && !monitor.isInterrupted()) {

                ((Observation) target).finalizeTransition((IScale) transitionScale);

                IObservation parent = runtimeScope.getParentArtifactOf(target);
                if (parent != null && runtimeScope.getWatchedObservationIds().contains(parent.getId())) {

                    ObservationChange change = new ObservationChange();
                    change.setContextId(runtimeScope.getRootSubject().getId());
                    change.setId(target.getId());
                    change.setTimestamp(time.getEnd().getMilliseconds());

                    if (target instanceof IState) {
                        change.setType(ObservationChange.Type.ValueChange);
                    } else if (target instanceof IDirectObservation
                            && !((IDirectObservation) target).isActive()) {
                        change.setType(ObservationChange.Type.Termination);
                    }

                    ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
                    session.getMonitor().send(Message.create(session.getId(),
                            IMessage.MessageClass.ObservationLifecycle, IMessage.Type.ModifiedObservation,
                            change));
                } else {
                    ((Observation) target).setDynamic(true);
                }
            }

        }
    }

    /*
     * crazy, but because observable is guaranteed to have at least one precursor when this is
     * called, we can retrieve the linked data this way.
     */
    private Actuator getActuator(IObservedConcept observable,
            Graph<IObservedConcept, DefaultEdge> dependencies) {
        for (DefaultEdge edge : dependencies.incomingEdgesOf(observable)) {
            return (Actuator) dependencies.getEdgeTarget(edge).getData().get(Dataflow.ACTUATOR);
        }
        return null;
    }

    private Set<IObservedConcept> getPrecursors(Graph<IObservedConcept, DefaultEdge> dependencies,
            IObservedConcept observable) {
        Set<IObservedConcept> ret = new HashSet<>();
        if (dependencies.containsVertex(observable)) {
            for (DefaultEdge edge : dependencies.incomingEdgesOf(observable)) {
                ret.add(dependencies.getEdgeSource(edge));
            }
        }
        return ret;
    }

    /**
     * Reschedule returns the time of the <b>previous</b> run so that we can reuse it to run
     * dependencies that were not scheduled after it.
     * 
     * @param registration
     * @param first
     * @return
     */
    private ITime reschedule(Registration registration, boolean first) {

        this.activeRegistrations--;

        if (stopped.get()) {
            return null;
        }

        ITime ret = registration.time;

        /*
         * first time interval in registration, skip initialization
         */
        if (first) {
            registration.time = registration.scale.getTime().earliest();
        } else {
            registration.time = registration.time.getNext();
        }

        if (registration.time == null || registration.time.getEnd() == null) {
            return null;
        }

        long executionTime = registration.time.getEnd().getMilliseconds();

        if (registration.scale.getTime().size() != IGeometry.INFINITE_SIZE
                && executionTime > registration.scale.getTime().getEnd().getMilliseconds()) {
            return ret;
        }

        long stepSize = executionTime - registration.time.getStart().getMilliseconds();

        // how many slots we span in the wheel
        int span = (int) (stepSize / resolution);
        // how many spins of the wheel we need to take before it's our turn
        registration.rounds = span / wheelSize;
        // milliseconds left to wait once our slot is being computed
        registration.delayInSlot = executionTime % resolution;

        int slot = (cursor + span + (first ? 0 : 1)) % wheelSize;

        if (this.wheel[slot] == null) {
            this.wheel[slot] = new ArrayList<Registration>();
        }

        this.wheel[slot].add(registration);

        if (this.wheel[slot].size() > 1) {
            Collections.sort(this.wheel[slot], new Comparator<Registration>(){
                @Override
                public int compare(Registration o1, Registration o2) {
                    return o1.compare(o2);
                }
            });
        }

        this.activeRegistrations++;

        return ret;

    }

    @Override
    public boolean isEmpty() {
        return registrations.size() < 1;
    }

    @Override
    public boolean isFinished() {
        return this.finished;
    }

}
