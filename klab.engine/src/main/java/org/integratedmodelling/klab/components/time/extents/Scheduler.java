package org.integratedmodelling.klab.components.time.extents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeDuration;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IAssociation;
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
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.debugger.Debug;
import org.integratedmodelling.klab.engine.debugger.Debugger.Watcher;
import org.integratedmodelling.klab.engine.runtime.ActivityBuilder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.DependencyGraph;
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
 * Scheduler for actors and actuators, in either real or mock time.
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
    private WaitStrategy waitStrategy;
    private boolean finished = false;

    /*
     * keep changed observations here so we can replay the change at each replay of the schedule in
     * successive resolutions. This is needed so that any new dependents can be updated.
     */
    private Set<IObservation> changedObservations = new LinkedHashSet<>();

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
        IScale scale; // only used with actions: scale with actuators is in the runtime scope
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

        // if this is not null, the action simply reassesses the dependencies and calls
        // the actuators of other dependent observations that depend on the change.
        private IObservation replayedObservation;
        private int changeIndex = 0;
        private ITime[] timeChanges;

        /**
         * Register a scheduled action from a behavior.
         * 
         * @param observation
         * @param scheduled
         * @param scale
         * @param scope
         * @param endtime
         */
        public Registration(Observation observation, IBehavior.Action scheduled, IScale scale, IRuntimeScope scope,
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
                    if (recipient instanceof IDirectObservation && !((IDirectObservation) recipient).isActive()) {
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
                    ActorRef<KlabMessage> sender = ((ActorReference) ((Observation) observation.getScope().getRootSubject())
                            .getActor()).actor;

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
                    if (recipient instanceof IDirectObservation && !((IDirectObservation) recipient).isActive()) {
                        // TODO target went MIA - notify relatives
                    } else {

                        for (IObservation observation : changed) {

                            ret.add(new ObservedConcept(observation.getObservable(),
                                    observation instanceof IObservationGroup ? Mode.INSTANTIATION : Mode.RESOLUTION));

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

                                ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
                                session.getMonitor().send(Message.create(session.getId(),
                                        IMessage.MessageClass.ObservationLifecycle, IMessage.Type.ModifiedObservation, change));
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
         * Special case registration that replays previous change. Used only in contextualizations
         * that may depend on change that has already been recorded.
         * 
         * @param replayedObservation
         * @param scope
         * @param catalog
         * @param changes
         */
        public Registration(IObservation replayedObservation, IRuntimeScope scope, ITime[] changes) {

            this.replayedObservation = replayedObservation;
            this.scope = scope;
            this.timeChanges = changes;
            this.changeIndex = 0;
            // this.scale = replayedObservation.getScale();
            this.action = new Function<IMonitor, Collection<ObservedConcept>>(){

                @Override
                public Collection<ObservedConcept> apply(IMonitor monitor) {

                    IRuntimeScope runtimeScope = (IRuntimeScope) replayedObservation.getScope();
                    DependencyGraph dependencyGraph = runtimeScope.getDependencyGraph();
                    Collection<ObservedConcept> ret = new ArrayList<>();
                    Map<IObservedConcept, IObservation> catalog = runtimeScope.getCatalog();

                    for (IObservedConcept tracked : dependencyGraph) {

                        if (runtimeScope.getImplicitlyChangingObservables().contains(tracked)) {
                            if (monitor.isInterrupted()) {
                                return ret;
                            }
                            Actuator actuator = getActuator(tracked, dependencyGraph);
                            if (actuator != null) {
                                reinitializeObservation(tracked, catalog.get(tracked), actuator, time, runtimeScope,
                                        runtimeScope.getDataflow());
                            }
                        }
                    }

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
        public Registration(Actuator actuator, List<Actuator.Computation> computation, IDirectObservation target, IScale scale,
                IRuntimeScope scope, long endtime) {

            this.actuator = actuator;
            this.scale = scale;
            this.target = target;
            this.endTime = endtime;
            this.scope = scope;
            this.computations = computation;

            this.action = new Function<IMonitor, Collection<ObservedConcept>>(){

                @Override
                public Collection<ObservedConcept> apply(IMonitor monitor) {

                    Set<ObservedConcept> ret = new HashSet<>();
                    Set<IArtifact> changedArtifacts = new HashSet<>();

                    /*
                     * target is dead, return
                     */
                    if (target != null && !target.isActive()) {
                        return ret;
                    }

                    ActivityBuilder statistics = scope.getStatistics().forTarget(actuator);
                    
                    /*
                     * 2. Set the context at() the current time. This will also need to expose any
                     * affected outputs that move at a different (context) speed through a rescaling
                     * wrapper. Done within the context, which uses its current target to establish
                     * the specific view of the context.
                     * 
                     * Time is null when the registration is scheduled at termination
                     */
                    ILocator transitionScale = getScale().at(time);

                    IRuntimeScope transitionContext = scope;
                    transitionContext = scope.locate(transitionScale, monitor);

                    /*
                     * TODO if the target is a group of events, it has been filtered to only contain
                     * those that occur in this transition. They must now be resolved in the
                     * localized scope (either using a cached dataset or from scratch) and be
                     * notified as they appear.
                     */
                    if (target instanceof IObservationGroup && target.getObservable().is(IKimConcept.Type.EVENT)) {
                        // TODO
                    }

                    monitor.debug("running " + actuator
                            + (time == null
                                    ? " at termination"
                                    : (" at [" + new Date(time.getStart().getMilliseconds()) + " - "
                                            + new Date(time.getEnd().getMilliseconds()) + "]")));

                    IArtifact artifact = null;

                    Set<String> interruptedTargets = new HashSet<>();

                    /*
                     * 3. Run all contextualizers in the context that react to transitions; check
                     * for signs of life at each step. Anything enqueued here is active so no
                     * further check is necessary.
                     */
                    for (Actuator.Computation computation : computations) {

                        if (interruptedTargets.contains(computation.targetId)) {
                            continue;
                        }

                        if (computation.variable != null) {
                            transitionContext.getVariables().put(computation.targetId, computation.variable);
                            continue;
                        }

                        if (artifact == null) {
                            artifact = computation.target;
                        }

                        IRuntimeScope transitionScope = actuator.setupScope(artifact, transitionContext, statistics);

                        IContextualizable resource = computation.resource;
                        if (resource != null) {
                            resource = resource.contextualize(artifact, transitionScope);
                            if (resource.isEmpty()) {
                                interruptedTargets.add(computation.targetId);
                                continue;
                            }
                            computation.contextualizer.notifyContextualizedResource(resource, target, transitionScope);
                        }

                        if (resource.isFinal()) {
                            scope.getProvenance().add(artifact, resource, (IScale) transitionScale, actuator, transitionScope,
                                    IAssociation.Type.wasDerivedFrom);
                        }

                        /*
                         * substitute the target for the next computation if we're using layers.
                         * Whatever changes is reported by the actuator.
                         */
                        artifact = actuator.runContextualizer(computation.contextualizer, computation.observable, resource,
                                artifact, transitionScope, (IScale) transitionScale, changedArtifacts, statistics);
                        
                        if (artifact == null) {
                            interruptedTargets.add(computation.targetId);
                            continue;
                        }

                        for (IArtifact modified : changedArtifacts) {
                            if (modified instanceof IObservation) {
                                // state layers and rescalers have the same observable so this works
                                ret.add(new ObservedConcept(((IObservation) modified).getObservable()));
                            }
                        }

                    }

                    /*
                     * 4. Notify whatever has changed.
                     */
                    if (target instanceof IDirectObservation && !((IDirectObservation) target).isActive()) {

                        ObservationChange change = new ObservationChange();
                        change.setContextId(scope.getRootSubject().getId());
                        change.setId(target.getId());
                        change.setTimestamp(time.getEnd().getMilliseconds());
                        change.setType(ObservationChange.Type.Termination);
                        ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
                        session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
                                IMessage.Type.ModifiedObservation, change));

                    } else {

                        for (ObservedConcept observable : ret) {

                            IObservation observation = catalog.get(observable);

                            /**
                             * keep a memory of the change for future contextualizations
                             */
                            Scheduler.this.changedObservations.add(observation);

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

                                ISession session = scope.getMonitor().getIdentity().getParentIdentity(ISession.class);
                                session.getMonitor().send(Message.create(session.getId(),
                                        IMessage.MessageClass.ObservationLifecycle, IMessage.Type.ModifiedObservation, change));
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

        public Scale getScale() {
            return (Scale) scale;
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
            if (this.computations != null) {
                for (Actuator.Computation computation : this.computations) {
                    if (computation.schedule != null && computation.schedule.isEnd()) {
                        return true;
                    }
                }
            }
            return false;
        }
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
    private Map<IObservedConcept, IObservation> catalog; // only for the registrations that replay
                                                         // change

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
        IScale scale = actionScale.mergeContext(overall);

        /*
         * proceed as with an actuator
         */
        ITimeInstant start = scale.getTime().getStart();
        ITimeInstant end = scale.getTime().getStart();
        ITimeDuration step = scale.getTime().getStep();

        if (start == null || (overall.getTime().getStart() != null && start.isBefore(overall.getTime().getStart()))) {
            start = overall.getTime().getStart();
        }
        if (end == null || (overall.getTime().getEnd() != null && start.isAfter(overall.getTime().getEnd()))) {
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

        final long endTime = overall.getTime().getEnd() == null ? -1 : overall.getTime().getEnd().getMilliseconds();

        registrations.add(new Registration(targetObservation, action, scale, runtimeScope, endTime));
    }

    public void schedule(final Actuator actuator, final List<Actuator.Computation> computations, IRuntimeScope scope) {

        scope = scope.targetForChange();

        /*
         * overall scale sets boundaries, model scale may change temporal resolution.
         */
        IScale overall = scope.getResolutionScale();
        IScale modelScale = actuator.getModel().getCoverage(scope.getMonitor());
        if (modelScale != null) {
            overall = overall.mergeContext(modelScale, Dimension.Type.TIME);
        }
        IScale actuatorScale = scope.getScale(actuator);

        /*
         * complete the actuator's scale with the overall one, adding extent boundaries and the like
         * but keeping the resolution and representation.
         */
        IScale scale = overall.mergeContext(actuatorScale, Dimension.Type.TIME);
        IObservation targetObservation = scope.getActuatorData(actuator).getTarget();
        ITimeInstant end = scope.getScale(actuator).getTime().getEnd();

        // can be null with void actuators
        if (targetObservation != null && (targetObservation.getObservable().is(IKimConcept.Type.PROCESS)
                || targetObservation.getObservable().is(IKimConcept.Type.EVENT)
                || targetObservation.getObservable().is(IKimConcept.Type.QUALITY))) {
            targetObservation = scope.getContextObservation();
        }

        registrations.add(new Registration(actuator, computations, (IDirectObservation) targetObservation, scale, scope,
                end == null ? -1 : end.getMilliseconds()));
    }

    private void schedule(Set<IObservedConcept> relevant) {

        /*
         * 1. Collect all dataflows from actuators and pair them to their actuators. Preserve order
         * of registration
         */
        dataflows = new LinkedHashMap<>();

        for (Registration registration : registrations) {
            Dataflow dataflow = registration.actuator == null
                    ? (Dataflow) ((RuntimeScope) registration.scope).getDataflow()
                    : registration.actuator.getDataflow();
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
            schedule(dataflow, dataflows.get(dataflow), relevant);
        }
    }

    /*
     * one-shot scheduling, re-entrant
     */
    @SuppressWarnings("unchecked")
    private void schedule(Pair<IDirectObservation, Dataflow> dataflow, List<Registration> actions,
            Set<IObservedConcept> relevant) {

        long longest = 0;

        List<Number> periods = new ArrayList<>();
        List<Registration> regs = separateTerminationRegistrations(dataflow, actions, relevant);
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
                long interval = registration.getScale().getTime().getResolution() == null
                        ? (registration.getScale().getTime().getEnd().getMilliseconds()
                                - registration.getScale().getTime().getStart().getMilliseconds())
                        : registration.getScale().getTime().getResolution().getSpan();
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
            this.wheelSize = DEFAULT_WHEEL_SIZE;
            monitor.debug("created scheduler hash wheel of size " + this.wheelSize + " for resolution = " + this.resolution);
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
            List<Registration> registrations, Set<IObservedConcept> relevant) {

        List<Registration> ret = new ArrayList<>();
        this.terminationRegistrations.put(dataflow, new ArrayList<>());

        for (Registration registration : registrations) {
            if (registration.runsAtTermination()) {
                this.terminationRegistrations.get(dataflow).add(registration);
            } else {
                if (registration.replayedObservation == null || relevant == null
                        || relevant.contains(new ObservedConcept(registration.replayedObservation.getObservable())))
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
        Graph<IObservedConcept, DefaultEdge> dynamicDependencies = new DefaultDirectedGraph<>(DefaultEdge.class);
        for (Registration r : registrations) {
            if (r.actuator != null) {
                dynamicDependencies.addVertex(new ObservedConcept(r.actuator.getObservable(), r.actuator.getMode()));
            } else if (r.replayedObservation != null) {
                dynamicDependencies.addVertex(new ObservedConcept(r.replayedObservation.getObservable(), Mode.RESOLUTION));
            }
        }

        for (IObservedConcept oc : dynamicDependencies.vertexSet()) {
            // if (dependencies.vertexSet().contains(oc)) {
            for (DefaultEdge dc : dependencies.incomingEdgesOf(oc)) {
                IObservedConcept source = dependencies.getEdgeSource(dc);
                if (!source.equals(oc) && dynamicDependencies.containsVertex(source)) {
                    dynamicDependencies.addEdge(source, oc);
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
                        Observable.promote(Observables.INSTANCE.getDescribedType(oc.getObservable().getType())), Mode.RESOLUTION);

                for (IObservedConcept dc : ccs) {

                    if (dc.equals(oc)) {
                        continue;
                    }

                    if (dependencies.containsVertex(changing) && Graphs.dependsOn(dc, changing, dependencies)) {

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

        Map<ObservedConcept, List<Registration>> index = new HashMap<>();

        for (Registration r : registrations) {
            if (r.actuator != null) {
                ObservedConcept oob = new ObservedConcept(r.actuator.getObservable(), r.actuator.getMode());
                List<Registration> rgs = index.get(oob);
                if (rgs == null) {
                    rgs = new ArrayList<>();
                    index.put(oob, rgs);
                }
                rgs.add(r);
            } else if (r.replayedObservation != null) {
                ObservedConcept oob = new ObservedConcept(r.replayedObservation.getObservable(), Mode.RESOLUTION);
                List<Registration> rgs = index.get(oob);
                if (rgs == null) {
                    rgs = new ArrayList<>();
                    index.put(oob, rgs);
                }
                rgs.add(r);
            }
        }

        List<Registration> ret = new ArrayList<>();
        TopologicalOrderIterator<IObservedConcept, DefaultEdge> order = new TopologicalOrderIterator<IObservedConcept, DefaultEdge>(
                dynamicDependencies);

        while(order.hasNext()) {
            ret.addAll(index.get(order.next()));
        }

        return ret;
    }

    @Override
    public void run(IMonitor monitor) {
        run(monitor, null);
    }

    /**
     * Use the dependency graph to filter replayed registrations.
     * 
     * @param monitor
     * @param dependencies
     */
    public void run(IMonitor monitor, Set<IObservedConcept> computed) {

        if (registrations.size() < 1) {
            return;
        }

        schedule(computed);

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

        monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle, IMessage.Type.SchedulingStarted,
                notification));

        /**
         * This is only for the benefit of replayed registrations. The catalog changes during
         * transitions so most functions that need it should keep extracting it when needed.
         */
        this.catalog = runtimeScope.getCatalog();

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

                            // must be here as each registration may be for a different object with
                            // a
                            // different scope.
                            DependencyGraph dependencyGraph = registration.scope.getDependencyGraph();

                            for (IObservedConcept tracked : dependencyGraph) {

                                if (changed.contains(tracked)) {
                                    continue;
                                }
                                
                                if (registration.scope.getImplicitlyChangingObservables().contains(tracked)
                                        && dependencyGraph.dependsOn(tracked, changed)) {

                                    if (monitor.isInterrupted()) {
                                        this.registrations.clear();
                                        this.dataflows.clear();
                                        this.finished = true;
                                        return;
                                    }

                                    if (Configuration.INSTANCE.isEchoEnabled()) {
                                        System.out.println("RECOMPUTING " + tracked);
                                    }
                                    reinitializeObservation(tracked, catalog.get(tracked), getActuator(tracked, dependencyGraph),
                                            toRun, runtimeScope, registration.actuator.getDataflow());

                                    changed.add(tracked);
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
        resetAfterRun();

        this.finished = true;

        /*
         * notify end
         */
        notification.setType(SchedulerNotification.Type.FINISHED);
        notification.setCurrentTime(time);
        monitor.send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle, IMessage.Type.SchedulingFinished,
                notification));
    }

    private void resetAfterRun() {

        this.registrations.clear();
        this.dataflows.clear();

        /*
         * enqueue registrations to replay change for the next pass reusing this info. Dependent
         * variables will need to do that automatically.
         */
        for (IObservation changing : this.changedObservations) {
            this.registrations
                    .add(new Registration(changing, runtimeScope, ((Time) changing.getScale().getTime()).getChangedExtents()));
        }
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
     * ACHTUNG: the passed runtime scope is for the original dependent and must be retargeted to the
     * target.
     * 
     * @param observable
     * @param actuator
     * @param time
     * @param runtimeScope
     */
    private void reinitializeObservation(IObservedConcept observable, IObservation target, Actuator actuator, ITime time,
            IRuntimeScope runtimeScope, IDataflow<?> dataflow) {

        if (target != null) {

            ILocator transitionScale = runtimeScope.getResolutionScale().at(time);
            IRuntimeScope transitionContext = runtimeScope.targetToObservation(target).locate(transitionScale, monitor);
            Set<IArtifact> changedArtifacts = new HashSet<>();

            /*
             * FIXME FIXME check correctly for resources that were contextualized beyond this
             * transition in previous contextualizations
             */
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

            ActivityBuilder statistics = runtimeScope.getStatistics().forTarget(actuator);
            
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
                artifact = actuator.runContextualizer(computation.contextualizer, computation.observable, computation.resource,
                        artifact, transitionContext, (IScale) transitionScale, changedArtifacts, statistics);

                if (monitor.isInterrupted()) {
                    return;
                }

                ((Observation) artifact).finalizeTransition((IScale) transitionScale);

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
                    } else if (target instanceof IDirectObservation && !((IDirectObservation) target).isActive()) {
                        change.setType(ObservationChange.Type.Termination);
                    }

                    ISession session = monitor.getIdentity().getParentIdentity(ISession.class);
                    session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.ObservationLifecycle,
                            IMessage.Type.ModifiedObservation, change));

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
    private Actuator getActuator(IObservedConcept observable, Graph<IObservedConcept, DefaultEdge> dependencies) {
        for (DefaultEdge edge : dependencies.incomingEdgesOf(observable)) {
            return (Actuator) dependencies.getEdgeTarget(edge).getData().get(Dataflow.ACTUATOR);
        }
        return null;
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
        if (registration.timeChanges != null && registration.changeIndex < registration.timeChanges.length - 1) {
            registration.time = registration.changeIndex == 0
                    ? registration.getScale().getTime().earliest()
                    : registration.timeChanges[registration.changeIndex - 1];
            registration.changeIndex++;
        } else if (first) {
            registration.time = registration.getScale().getTime().earliest();
        } else {
            registration.time = registration.time.getNext();
        }

        if (registration.time == null || registration.time.getEnd() == null) {
            return null;
        }

        long executionTime = registration.time.getEnd().getMilliseconds();

        if (registration.getScale().getTime().size() != IGeometry.INFINITE_SIZE
                && executionTime > registration.getScale().getTime().getEnd().getMilliseconds()) {
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
