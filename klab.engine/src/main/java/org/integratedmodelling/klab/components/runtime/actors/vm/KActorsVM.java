package org.integratedmodelling.klab.components.runtime.actors.vm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.Assert;
import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.kactors.api.IKActorsStatement.Assignment;
import org.integratedmodelling.kactors.api.IKActorsStatement.Call;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.api.IKActorsStatement.Do;
import org.integratedmodelling.kactors.api.IKActorsStatement.Fail;
import org.integratedmodelling.kactors.api.IKActorsStatement.FireValue;
import org.integratedmodelling.kactors.api.IKActorsStatement.For;
import org.integratedmodelling.kactors.api.IKActorsStatement.If;
import org.integratedmodelling.kactors.api.IKActorsStatement.Instantiation;
import org.integratedmodelling.kactors.api.IKActorsStatement.Sequence;
import org.integratedmodelling.kactors.api.IKActorsStatement.TextBlock;
import org.integratedmodelling.kactors.api.IKActorsStatement.While;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsValue.ExpressionType;
import org.integratedmodelling.kactors.model.KActorsActionCall;
import org.integratedmodelling.kactors.model.KActorsArguments;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Actors.CallDescriptor;
import org.integratedmodelling.klab.Actors.Library;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage.Semaphore;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.components.runtime.actors.KlabActionExecutor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.ActorReference;
import org.integratedmodelling.klab.components.runtime.actors.ObservationActor;
import org.integratedmodelling.klab.components.runtime.actors.SessionActor;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.ComponentFire;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Fire;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.KActorsMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.TestBehavior;
import org.integratedmodelling.klab.components.runtime.actors.UserActor;
import org.integratedmodelling.klab.components.runtime.actors.ViewBehavior.GroupHandler;
import org.integratedmodelling.klab.components.runtime.actors.ViewBehavior.KlabWidgetActionExecutor;
import org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior.Match;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.ViewImpl;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.ObjectExpression;
import org.integratedmodelling.klab.exceptions.KlabActorException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.Triple;
import org.integratedmodelling.klab.utils.Utils;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;

/**
 * The basic k.Actors VM. Eventually to be used in place of the same code within KlabActor.
 * 
 * @author Ferd
 *
 */
public class KActorsVM {

    /**
     * Descriptor for actions to be taken when a firing is recorded with the ID used as key in
     * matchActions.
     * 
     * @author Ferd
     */
    class MatchActions {

        ActorRef<KlabMessage> caller;
        List<Pair<Match, IKActorsStatement>> matches = new ArrayList<>();
        IBehavior behavior;
        // this is the original calling scope, to use when the listening action is
        // executed upon a match.
        IKActorsBehavior.Scope scope;

        public void match(Object value, Map<String, Object> scopeVars) {

            for (Pair<Match, IKActorsStatement> match : matches) {

                if (match.getFirst().matches(value, scope)) {
                    IKActorsBehavior.Scope s = scope.withMatch(match.getFirst(), value, scope.withValues(scopeVars));
                    execute(match.getSecond(), behavior, s);
                    break;
                }
            }
        }

        public void match(Object value, IKActorsBehavior.Scope matchingScope) {

            for (Pair<Match, IKActorsStatement> match : matches) {

                if (match.getFirst().matches(value, scope)) {
                    ActorScope s = ((ActorScope) scope).withMatch(match.getFirst(), value, matchingScope);
                    execute(match.getSecond(), behavior, s);
                    break;
                }
            }
        }

        public MatchActions(IBehavior behavior, IKActorsBehavior.Scope scope) {
            this.scope = scope;
            this.behavior = behavior;
        }
    }

    public KActorsVM(ActorRef<KlabMessage> actor, IScope scope, Map<String, Object> globalState) {
        this.receiver = actor;
        this.globalState = globalState;
        this.observationScope = scope;
    }

    // protected IBehavior behavior;
    protected ActorRef<KlabMessage> receiver;
    @Deprecated
    IScope observationScope;

    /*
     * this is set when a behavior is loaded and used to create proper actor paths for application
     * components, so that user messages can be sent to the main application actor and directed to
     * the actor that implements them.
     */
    @Deprecated
    private String childActorPath = null;
    @Deprecated
    protected String appId;
    @Deprecated
    protected IActorIdentity<KlabMessage> identity;
    protected Map<Long, MatchActions> listeners = Collections.synchronizedMap(new HashMap<>());
    protected Map<String, MatchActions> componentFireListeners = Collections.synchronizedMap(new HashMap<>());
    private AtomicLong nextId = new AtomicLong(0);
    private Map<String, Long> actionBindings = Collections.synchronizedMap(new HashMap<>());
    private Map<String, ActorRef<KlabMessage>> receivers = Collections.synchronizedMap(new HashMap<>());
    private Map<String, List<ActorRef<KlabMessage>>> childInstances = Collections.synchronizedMap(new HashMap<>());
    // set to the environment that comes in with the Load message and never reset
    @Deprecated
    private Map<String, Object> globalState = null; // should be in the scope
    /*
     * Java objects created by calling a constructor in set statements. Messages will be sent using
     * reflection.
     */
    private Map<String, Object> javaReactors = Collections.synchronizedMap(new HashMap<>());
    @Deprecated
    private List<ActorRef<KlabMessage>> componentActors = Collections.synchronizedList(new ArrayList<>());
    private Layout layout;
    private Map<String, Library> libraries = new HashMap<>();
    private Map<String, Object> nativeLibraryInstances = new HashMap<>();

    /*
     * This is the parent that generated us through a 'new' instruction, if any. FIXME should be in
     * the scope
     */
    @Deprecated
    private ActorRef<KlabMessage> parentActor = null;

    /*
     * if we pre-build actions or we run repeatedly we cache them here. Important that their run()
     * method is reentrant.
     */
    protected Map<String, KlabActionExecutor> actionCache = Collections.synchronizedMap(new HashMap<>());

    /*
     * actions that were created from system actions rather than actual actors, here so we can talk
     * to them from k.Actors
     */
    private Map<String, KlabActionExecutor.Actor> localActionExecutors = Collections.synchronizedMap(new HashMap<>());

    /**
     * Top-level. TODO pass arguments and whatever else needs to be defined in the root scope.
     * 
     * @param behavior
     */
    public void runBehavior(IBehavior behavior, IParameters<String> arguments, IScope scope) {
        runBehavior(behavior, arguments, (ActorScope) null);
    }

    public void runBehavior(IBehavior behavior, IParameters<String> arguments, ActorScope scope) {

        this.globalState = scope.getGlobalSymbols();

        new Thread(){

            @Override
            public void run() {

                try {

                    boolean rootView = scope.getViewScope() == null ? false : (scope.getViewScope().getLayout() == null);

                    /*
                     * preload system actors. We don't add "self" which should be factored out by
                     * the interpreter.
                     */
                    if (!receivers.containsKey("user")) {
                        ActorRef<KlabMessage> sact = null;
                        ActorRef<KlabMessage> eact = null;
                        if ((ActorReference) identity.getParentIdentity(Session.class).getActor() != null) {
                            sact = ((ActorReference) identity.getParentIdentity(Session.class).getActor()).actor;
                        }
                        if (identity.getParentIdentity(EngineUser.class).getActor() != null) {
                            eact = ((ActorReference) identity.getParentIdentity(EngineUser.class).getActor()).actor;
                        }
                        // these three are the same. TODO check
                        receivers.put("session", sact);
                        receivers.put("view", sact);
                        receivers.put("system", sact);
                        // user actor
                        receivers.put("user", eact);
                        // TODO modeler actor - which can create and modify projects and code
                    }

                    // create a new behavior for each actor. TODO/FIXME this is potentially
                    // expensive. TODO ensure the localization gets there.
                    // KActorsVM.this.behavior = Actors.INSTANCE.newBehavior(message.getBehavior());
                    KActorsVM.this.listeners.clear();
                    KActorsVM.this.actionBindings.clear();
                    KActorsVM.this.actionCache.clear();
                    // KActorsVM.this.childActorPath = message.getChildActorPath();

                    /*
                     * load all imported and default libraries
                     */
                    KActorsVM.this.libraries
                            .putAll(Actors.INSTANCE.getLibraries(behavior.getStatement(), scope.getRuntimeScope().getMonitor()));

                    for (Library library : KActorsVM.this.libraries.values()) {
                        if (library.cls != null) {
                            KActorsVM.this.nativeLibraryInstances.put(library.name,
                                    library.cls.getDeclaredConstructor().newInstance());
                        }
                    }

                    // if (message.getApplicationId() != null) {
                    // // this only happens when we're spawning a component from a top application
                    // // using new; in that case, the appId is communicated here and the appId in
                    // // the message (which does not come from an application action) is null.
                    // // This ensures that all component actors have the same appId.
                    // KActorsVM.this.appId = message.getApplicationId();
                    // }

                    /*
                     * Init action called no matter what and before the behavior is set; the onLoad
                     * callback intervenes afterwards. Do not create UI (use raw scope).
                     */
                    for (IBehavior.Action action : behavior.getActions("init", "@init")) {

                        ActorScope initScope = scope.forInit();
                        initScope.setMetadata(new Parameters<>(scope.getMetadata()));
                        initScope.setLocalizedSymbols(behavior.getLocalization());
                        if (behavior.getDestination() == Type.SCRIPT || behavior.getDestination() == Type.UNITTEST) {
                            initScope = initScope.synchronous();
                        }

                        KActorsVM.this.run(action, behavior, initScope);

                        if (initScope.getMonitor().isInterrupted() || initScope.getMonitor().hasErrors()) {
                            /*
                             * TODO if testing and init fails, the test is skipped. If not testing
                             * and init fails, the rest of the behavior is not loaded.
                             */
                            if (initScope.getTestScope() != null) {
                                // TODO send message to notify skipped test case
                            }

                            initScope.getMonitor().warn("Initialization failed: skipping rest of behavior");

                            return;
                        }
                    }

                    /*
                     * run any main actions. This is the only action that may create a UI.
                     */
                    for (IBehavior.Action action : behavior.getActions("main", "@main")) {
                        ActorScope ascope = scope.getChild(KActorsVM.this.appId, action);
                        KActorsVM.this.layout = ascope.getViewScope() == null ? null : ascope.getViewScope().getLayout();
                        ascope.setMetadata(new Parameters<>(ascope.getMetadata()));
                        ascope.setLocalizedSymbols(behavior.getLocalization());
                        if (behavior.getDestination() == Type.SCRIPT || behavior.getDestination() == Type.UNITTEST) {
                            ascope = scope.synchronous();
                        }
                        if (arguments != null) {
                            ascope.getSymbolTable().putAll(arguments);
                        }
                        KActorsVM.this.run(action, behavior, ascope);
                    }

                    if (behavior.getDestination() == Type.UNITTEST) {
                        for (IBehavior.Action action : behavior.getActions("@test")) {

                            IAnnotation desc = Annotations.INSTANCE.getAnnotation(action.getAnnotations(), "test");

                            if (identity instanceof Session) {
                                ((Session) identity).notifyTestCaseStart(behavior, scope.getTestScope().getTestStatistics());
                            }

                            if (desc.get("enabled", Boolean.TRUE) && !desc.get("disabled", Boolean.FALSE)) {

                                ActorScope testScope = scope.forTest(action);
                                testScope.setMetadata(new Parameters<>(scope.getMetadata()));
                                testScope.setLocalizedSymbols(behavior.getLocalization());
                                testScope.getRuntimeScope().getMonitor()
                                        .info(behavior.getName() + ": running test " + action.getName());

                                KActorsVM.this.run(action, behavior, testScope);

                                if (identity instanceof Session) {
                                    ((Session) identity).resetAfterTest(action);
                                }
                                testScope.getTestScope().finalizeTest(action, testScope.getValueScope());
                            }

                        }
                        scope.getRuntimeScope().getMonitor().info(behavior.getName() + ": done running tests");
                    }

                    /*
                     * send the view AFTER running main and collecting all components that generate
                     * views.
                     */
                    if (rootView && scope.getViewScope().getLayout() != null) {
                        if (Configuration.INSTANCE.isEchoEnabled()) {
                            System.out.println(Actors.INSTANCE.dumpView(scope.getViewScope().getLayout()));
                        }
                        KActorsVM.this.identity.setView(new ViewImpl(scope.getViewScope().getLayout()));
                        KActorsVM.this.identity.getMonitor().send(IMessage.MessageClass.UserInterface,
                                IMessage.Type.SetupInterface, scope.getViewScope().getLayout());
                    } /*
                       * TODO else if we have been spawned by a new component inside a group, we
                       * should send the group update message
                       */
                    /*
                     * move on, you waiters FIXME where are these? for components?
                     */
                    // for (Semaphore semaphore : message.getSemaphores(Semaphore.Type.LOAD)) {
                    // Actors.INSTANCE.expire(semaphore);
                    // }

                } catch (Throwable e) {

                    scope.onException(e, null);

                } finally {

                    if (scope.getTestScope() != null) {
                        scope.getTestScope().finalizeTestRun();
                    }

                    if (scope.getIdentity() instanceof Session) {
                        if (KActorsVM.this.appId != null
                                && (behavior.getDestination() == Type.SCRIPT || behavior.getDestination() == Type.UNITTEST)) {
                            /*
                             * communicate end of script to session
                             */
                            ((Session) scope.getIdentity()).notifyScriptEnd(KActorsVM.this.appId);
                        }
                    }
                }

            }

        }.start();
    }

    protected void run(IBehavior.Action action, IBehavior behavior, IKActorsBehavior.Scope scope) {

        IAnnotation wspecs = Annotations.INSTANCE.getAnnotation(action, "modal");
        if (wspecs == null) {
            wspecs = Annotations.INSTANCE.getAnnotation(action, "window");
        }

        if (wspecs != null) {
            scope = ((ActorScope) scope).forWindow(wspecs, action.getName());
        }

        if (action.isFunction()) {
            scope = ((ActorScope) scope).functional();
        }

        try {

            execute(action.getStatement().getCode(), behavior, ((ActorScope) scope).forAction(action));

        } catch (Throwable t) {

            ((ActorScope) scope).onException(t, "action " + action.getBehavior() + " " + action.getName());

            if (((ActorScope) scope).getSender() != null) {
                ((ActorScope) scope).getSender().tell(new Fire(scope.getListenerId(), t, scope.getAppId(), scope.getSemaphore(),
                        scope.getSymbols(this.identity)));
            } else if (parentActor != null) {

                /*
                 * No sender = the fire is not coming from an internal action but goes out to the
                 * world, which in this case is the parent actor. Let our parent know we've fired
                 * with a message carrying the name it knows us by, so that the value can be matched
                 * to what is caught after the 'new' verb. Listener ID is the actor's name.
                 */
                parentActor.tell(new ComponentFire(receiver.path().name(), t, receiver));

            } else {

                /*
                 * Fart in space: nothing is listening from the behavior being executed. TODO - an
                 * actor firing with no action listening and no parent should just send to either
                 * the user actor or (maybe) its parent identity? TODO - the outer group may be
                 * listening.
                 */
            }
        }

        if (wspecs != null) {
            if (Configuration.INSTANCE.isEchoEnabled()) {
                System.out.println(Actors.INSTANCE.dumpView(scope.getViewScope().getLayout()));
            }
            KActorsVM.this.identity.setView(new ViewImpl(scope.getViewScope().getLayout()));
            KActorsVM.this.identity.getMonitor().send(IMessage.MessageClass.UserInterface,
                    "modal".equals(wspecs.getName()) ? IMessage.Type.CreateModalWindow : IMessage.Type.CreateWindow,
                    scope.getViewScope().getLayout());
        }
    }

    private boolean execute(IKActorsStatement code, IBehavior behavior, IKActorsBehavior.Scope scope) {

        if (scope.getMonitor().isInterrupted()) {
            return false;
        }

        try {
            switch(code.getType()) {
            case ACTION_CALL:
                executeCall((IKActorsStatement.Call) code, behavior, scope);
                break;
            case ASSIGNMENT:
                executeAssignment((IKActorsStatement.Assignment) code, scope);
                break;
            case DO_STATEMENT:
                executeDo((IKActorsStatement.Do) code, scope);
                break;
            case FIRE_VALUE:
                return executeFire((IKActorsStatement.FireValue) code, scope);
            case FOR_STATEMENT:
                executeFor((IKActorsStatement.For) code, behavior, scope);
                break;
            case IF_STATEMENT:
                executeIf((IKActorsStatement.If) code, behavior, scope);
                break;
            case CONCURRENT_GROUP:
                executeGroup((IKActorsStatement.ConcurrentGroup) code, behavior, scope);
                break;
            case SEQUENCE:
                executeSequence((IKActorsStatement.Sequence) code, behavior, scope);
                break;
            case TEXT_BLOCK:
                executeText((IKActorsStatement.TextBlock) code, behavior, scope);
                break;
            case WHILE_STATEMENT:
                executeWhile((IKActorsStatement.While) code, scope);
                break;
            case INSTANTIATION:
                executeInstantiation((IKActorsStatement.Instantiation) code, behavior, scope);
                break;
            case ASSERT_STATEMENT:
                executeAssert((IKActorsStatement.Assert) code, behavior, scope);
                break;
            case FAIL_STATEMENT:
                if (scope.getTestScope() != null) {
                    scope.getTestScope().fail((Fail) code);
                }
                // fall through
            case BREAK_STATEMENT:
                return false;
            default:
                break;
            }
        } catch (Throwable t) {
            if (scope.getTestScope() != null) {
                scope.getTestScope().onException(t);
            }
            Logging.INSTANCE.warn("Exception thrown in k.Actors interpreter: " + t.getMessage());
        }

        return true;
    }

    private void executeInstantiation(Instantiation code, IBehavior behavior, IKActorsBehavior.Scope scope) {

        Behavior<KlabMessage> child = null;
        if (this.identity instanceof Observation) {
            child = ObservationActor.create((Observation) this.identity, null);
        } else if (this.identity instanceof Session) {
            /**
             * TODO if the actor has a view, use a behavior can address enable/disable/hide messages
             * and the like.
             */
            child = SessionActor.create((Session) this.identity, null);
        } else if (this.identity instanceof EngineUser) {
            child = UserActor.create((EngineUser) this.identity);
        }

        // existing actors for this behavior
        List<ActorRef<KlabMessage>> actors = this.childInstances.get(code.getActorBaseName());
        String actorName = code.getActorBaseName() + (actors == null ? "" : ("_" + (actors.size() + 1)));

        /**
         * TODO substitute with specialized message with ask pattern
         */
        ActorRef<KlabMessage> actor = null; // getContext().spawn(child, actorName);

        /*
         * use the actor name to install a listener for any actions that may be connected to this
         * instance; it will be used as listener ID for the ComponentFire message sent when the
         * child fires.
         */
        if (code.getActions().size() > 0) {

            MatchActions actions = new MatchActions(behavior, scope);
            for (Triple<IKActorsValue, IKActorsStatement, String> adesc : code.getActions()) {
                actions.matches.add(
                        new Pair<Match, IKActorsStatement>(new Match(adesc.getFirst(), adesc.getThird()), adesc.getSecond()));
            }
            this.componentFireListeners.put(actorName, actions);
        }

        // remove the appId for the children, otherwise their messages will be rerouted
        Map<String, Object> arguments = new HashMap<>();
        Map<String, Object> metadata = new HashMap<>();
        if (code.getArguments() != null) {
            /*
             * TODO match the arguments to the correspondent names for the declaration of main()
             */
            IBehavior childBehavior = Actors.INSTANCE.getBehavior(code.getBehavior());
            if (childBehavior == null) {
                observationScope.error("unreferenced child behavior: " + code.getBehavior() + " when execute instantiation");
                return;
            }
            Action main = childBehavior.getAction("main");
            int n = 0;
            for (int i = 0; i < main.getStatement().getArgumentNames().size(); i++) {
                String arg = main.getStatement().getArgumentNames().get(i);
                Object value = code.getArguments().get(arg);
                if (value == null && code.getArguments().getUnnamedKeys().size() > n) {
                    value = code.getArguments().get(code.getArguments().getUnnamedKeys().get(n++));
                    if (value instanceof KActorsValue) {
                        value = ((KActorsValue) value).evaluate(scope, identity, false);
                    }
                }
                arguments.put(arg, value);
            }
            for (String arg : ((KActorsArguments) code.getArguments()).getMetadataKeys()) {
                Object value = code.getArguments().get(arg);
                if (value instanceof KActorsValue) {
                    value = ((KActorsValue) value).evaluate(scope, identity, false);
                }
                metadata.put(arg, value);
            }
        }

        IBehavior actorBehavior = Actors.INSTANCE.getBehavior(code.getBehavior());
        if (actorBehavior != null) {

            /*
             * AppID in message is null because this is run by the newly spawned actor; we
             * communicate the overall appID through the specific field below.
             */
            Load loadMessage = new Load(this.identity, code.getBehavior(), null, scope.forComponent())
                    .withChildActorPath(this.childActorPath == null ? actorName : (this.childActorPath + "." + actorName))
                    .withActorBaseName(code.getActorBaseName()).withMainArguments(arguments).withMetadata(metadata)
                    .withApplicationId(this.appId).withParent(receiver);

            Semaphore semaphore = null;
            if (actorBehavior.getDestination() == Type.COMPONENT) {
                /*
                 * synchronize by default
                 */
                semaphore = Actors.INSTANCE.createSemaphore(Semaphore.Type.LOAD);
                loadMessage.withSemaphore(semaphore);
                componentActors.add(actor);
            }

            actor.tell(loadMessage);

            if (semaphore != null) {
                waitForGreen(semaphore);
            }

            receivers.put(actorName, actor);

            if (actors == null) {
                actors = new ArrayList<>();
                this.childInstances.put(actorName, actors);
            }

            actors.add(actor);
        }
    }

    private void waitForGreen(Semaphore semaphore) {

        while(!Actors.INSTANCE.expired(semaphore)) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void executeWhile(While code, IKActorsBehavior.Scope scope) {
        // TODO Auto-generated method stub

    }

    private void executeText(TextBlock code, IBehavior behavior, IKActorsBehavior.Scope scope) {
        executeCall(new KActorsActionCall(code), behavior, scope);
    }

    private void executeSequence(Sequence code, IBehavior behavior, IKActorsBehavior.Scope scope) {
        if (code.getStatements().size() == 1) {
            execute(code.getStatements().get(0), behavior, scope);
        } else {
            for (IKActorsStatement statement : code.getStatements()) {
                if (!execute(statement, behavior, scope.synchronous())) {
                    break;
                }
                // TODO waitForCompletion(message);
            }
        }
    }

    private void executeGroup(ConcurrentGroup code, IBehavior behavior, IKActorsBehavior.Scope scope) {
        IKActorsBehavior.Scope groupScope = scope.getChild(code);
        if (code.getTag() != null) {
            /*
             * install executor for group actions
             */
            this.localActionExecutors.put(code.getTag(), new GroupHandler(this.identity, appId, groupScope, receiver, null));
        }
        for (IKActorsStatement statement : code.getStatements()) {
            if (!execute(statement, behavior, groupScope) || scope.getMonitor().isInterrupted()) {
                break;
            }
        }
    }

    private void executeIf(If code, IBehavior behavior, IKActorsBehavior.Scope scope) {

        Object check = ((KActorsValue) code.getCondition()).evaluate(scope, identity, true);
        if (KActorsValue.isTrue(check)) {
            if (code.getThen() != null) {
                execute(code.getThen(), behavior, scope);
            }
        } else {
            for (Pair<IKActorsValue, IKActorsStatement> conditions : code.getElseIfs()) {
                check = ((KActorsValue) conditions.getFirst()).evaluate(scope, identity, true);
                if (KActorsValue.isTrue(check)) {
                    execute(conditions.getSecond(), behavior, scope);
                    return;
                }
            }
            if (code.getElse() != null) {
                execute(code.getElse(), behavior, scope);
            }
        }

    }

    private void executeFor(For code, IBehavior behavior, IKActorsBehavior.Scope scope) {
        for (Object o : Actors.INSTANCE.getIterable(code.getIterable(), scope, identity)) {
            if (!execute(code.getBody(), behavior, scope.withValue(code.getVariable(), o))
                    || scope.getMonitor().isInterrupted()) {
                break;
            }
        }
    }

    private void executeAssert(Assert code, IBehavior behavior, IKActorsBehavior.Scope scope) {

        for (Assertion assertion : code.getAssertions()) {
            executeCallChain(assertion.getCalls(), behavior, scope);
            if (assertion.getValue() != null || assertion.getExpression() != null) {
                // target is the match if we come from a trigger, or the value scope.
                TestBehavior.evaluateAssertion(scope.getMatchValue() == null ? scope.getValueScope() : scope.getMatchValue(),
                        assertion, scope, code.getArguments());
            }
        }
    }

    private static Object executeFunctionChain(List<Call> functions, IBehavior behavior, IKActorsBehavior.Scope scope) {
        Object contextReceiver = null;
        for (int i = 0; i < functions.size(); i++) {
            if (scope.getMonitor().isInterrupted()) {
                break;
            }
            boolean last = (i == functions.size() - 1);
            IKActorsBehavior.Scope fscope = last ? scope.withReceiver(contextReceiver) : scope.functional(contextReceiver);
            callFunctionOrMethod(functions.get(i), fscope);
            contextReceiver = fscope.getValueScope();
        }
        return contextReceiver;
    }

    /**
     * If the call is a known function, call it and leave the value in the scope. Otherwise check if
     * it's a method of the valueScope receiver if we have it.
     * 
     * @param call
     * @param fscope
     */
    private static void callFunctionOrMethod(Call call, IKActorsBehavior.Scope fscope) {
        // TODO Auto-generated method stub

    }

    /**
     * A call sequence is a one or more calls to be executed in sequence. The last call is a
     * standard message call which will either fire or return according to the scope; the ones
     * preceding it, if any, are necessarily functional and the return value of the first provides
     * the execution context for the next.
     * 
     * @param calls
     * @param scope
     */
    private void executeCallChain(List<Call> calls, IBehavior behavior, IKActorsBehavior.Scope scope) {

        Object contextReceiver = null;
        for (int i = 0; i < calls.size(); i++) {
            boolean last = (i == calls.size() - 1);
            if (scope.getMonitor().isInterrupted()) {
                break;
            }
            IKActorsBehavior.Scope fscope = last ? scope.withReceiver(contextReceiver) : scope.functional(contextReceiver);
            executeCall(calls.get(i), behavior, fscope);
            contextReceiver = fscope.getValueScope();
        }
        ((ActorScope) scope).setValueScope(contextReceiver);
    }

    /**
     * TODO add handling of test cases - all fires (including exceptions) should be intercepted
     * 
     * @param code
     * @param scope\
     * @return false if the scope is functional and execution should stop.
     */
    private boolean executeFire(FireValue code, IKActorsBehavior.Scope scope) {

        if (scope.isFunctional()) {
            // ((ActorScope) scope).hasValueScope = true;
            ((ActorScope) scope).setValueScope(code.getValue().evaluate(scope, identity, false));
            return false;
        }

        if (scope.getNotifyId() != null) {
            // my fire, my action
            if (listeners.containsKey(scope.getNotifyId())) {
                MatchActions actions = listeners.get(scope.getNotifyId());
                if (actions != null) {
                    actions.match(code.getValue().evaluate(scope, identity, false), scope);
                }
            }
        }

        if (((ActorScope) scope).getSender() != null) {

            /*
             * this should happen when a non-main action executes the fire. Must be checked first.
             * Fire may happen if the action firing is called again, so don't remove the listener.
             */
            ((ActorScope) scope).getSender()
                    .tell(new Fire(scope.getListenerId(), code.getValue().evaluate(scope, identity, false), scope.getAppId(),
                            scope.getSemaphore(), scope.getSymbols(this.identity)));

        } else if (parentActor != null) {

            /*
             * No sender = the fire is not coming from an internal action but goes out to the world,
             * which in this case is the parent actor. Let our parent know we've fired with a
             * message carrying the name it knows us by, so that the value can be matched to what is
             * caught after the 'new' verb. Listener ID is the actor's name.
             */
            parentActor
                    .tell(new ComponentFire(receiver.path().name(), code.getValue().evaluate(scope, identity, false), receiver));

        } else {

            /*
             * Fart in space: nothing is listening from the behavior being executed. TODO - an actor
             * firing with no action listening and no parent should just send to either the user
             * actor or (maybe) its parent identity? TODO - the outer group may be listening.
             */

        }

        return true;
    }

    private void executeDo(Do code, IKActorsBehavior.Scope scope) {
        // TODO Auto-generated method stub

    }

    private void executeAssignment(Assignment code, IKActorsBehavior.Scope scope) {
        if (code.getRecipient() != null) {
            if ("self".equals(code.getRecipient())) {
                this.identity.getState().put(code.getVariable(),
                        ((KActorsValue) code.getValue()).evaluate(scope, identity, false));
            } else {
                // TODO find the actor reference and send it an internal message to set the
                // state. Should be subject to scope and authorization
                throw new KlabUnimplementedException("klab actor state setting is unimplemented");
            }
        } else if (((KActorsValue) code.getValue()).getConstructor() != null) {

            Object o = ((KActorsValue) code.getValue()).evaluate(scope, identity, false);
            this.javaReactors.put(code.getVariable(), o);
            switch(code.getScope()) {
            case ACTION:
                scope.getSymbolTable().put(code.getVariable(), o);
                break;
            case ACTOR:
                scope.getGlobalSymbols().put(code.getVariable(), o);
                break;
            case FRAME:
                scope.getFrameSymbols().put(code.getVariable(), o);
                break;
            }
        } else {
            Object o = ((KActorsValue) code.getValue()).evaluate(scope, identity, false);
            switch(code.getScope()) {
            case ACTION:
                scope.getSymbolTable().put(code.getVariable(), o);
                break;
            case ACTOR:
                scope.getGlobalSymbols().put(code.getVariable(), o);
                break;
            case FRAME:
                scope.getFrameSymbols().put(code.getVariable(), o);
                break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static Object evaluateInScope(KActorsValue arg, IKActorsBehavior.Scope scope, IActorIdentity<?> identity) {

        Object ret = null;

        switch(arg.getType()) {
        case OBJECT:
            ret = Actors.INSTANCE.createJavaObject(arg.getConstructor(), scope, identity);
            break;
        case COMPONENT:
            ret = arg.getConstructor();
            break;
        case QUANTITY:
            ret = arg.getStatedValue();
            break;
        case OBSERVABLE:
            if (arg.getData() instanceof IObservable) {
                ret = arg.getData();
            } else if (arg.getStatedValue() instanceof IKimObservable) {
                ret = Observables.INSTANCE.declare((IKimObservable) arg.getStatedValue(), identity.getMonitor());
                arg.setData(ret);
            }
            break;
        case ERROR:
            throw arg.getStatedValue() instanceof Throwable
                    ? new KlabException((Throwable) arg.getStatedValue())
                    : new KlabException(arg.getStatedValue() == null
                            ? "Unspecified actor error from error value"
                            : arg.getStatedValue().toString());

        case NUMBERED_PATTERN:

            if (!"$".equals(arg.getStatedValue().toString())) {
                // TODO
            } /* else fall through to IDENTIFIER */

        case IDENTIFIER:

            // TODO check for recipient in ID
            if (scope.hasValue(arg.getStatedValue().toString())) {
                ret = scope.getValue(arg.getStatedValue().toString());
            } else {
                ret = arg.getStatedValue().toString();
            }
            break;

        case EXPRESSION:

            if (arg.getData() == null) {

                Object val = arg.getStatedValue();
                if (val instanceof String) {
                    val = Extensions.INSTANCE.parse((String) val);
                }

                arg.setData(new ObjectExpression((IKimExpression) val, (IRuntimeScope) scope.getRuntimeScope()));
            }

            try {
                /*
                 * 'metadata' is bound to the actor metadata map, initialized in the call
                 */
                ret = ((ObjectExpression) arg.getData()).eval((IRuntimeScope) scope.getRuntimeScope(), identity,
                        Parameters.create(scope.getSymbols(identity), "metadata", scope.getMetadata(), "self", identity));
            } catch (Throwable t) {
                scope.getMonitor().error(t);
                return null;
            }

            break;

        case OBSERVATION:
            // TODO
            break;
        case SET:
            // TODO eval all args
            break;
        case LIST:
            ret = new ArrayList<Object>();
            for (Object o : (Collection<?>) arg.getStatedValue()) {
                ((List<Object>) ret).add(o instanceof KActorsValue ? evaluateInScope((KActorsValue) o, scope, identity) : o);
            }
            break;
        case TREE:
            // TODO eval all args
            break;
        case MAP:
            // TODO eval all args
            break;
        case TABLE:
            // TODO eval all args
            break;
        case URN:
            ret = new Urn(arg.getStatedValue().toString());
            break;
        case CALLCHAIN:
            ret = executeFunctionChain(arg.getCallChain(), scope.getBehavior(), scope);
            break;
        case LOCALIZED_KEY:

            if (scope.getLocalizedSymbols() != null) {
                ret = scope.getLocalizedSymbols().get(arg.getStatedValue());
            }
            if (ret == null) {
                // ensure invariance in copies of the behavior
                ret = "#" + arg.getStatedValue();
                // .capitalize(arg.getStatedValue().toString().toLowerCase().replace("__",
                // ":").replace("_", " "));
            }
            break;
        default:
            ret = arg.getStatedValue();
        }

        if (arg.getExpressionType() == ExpressionType.TERNARY_OPERATOR) {
            if (Actors.INSTANCE.asBooleanValue(ret)) {
                ret = arg.getTrueCase() == null ? null : evaluateInScope(arg.getTrueCase(), scope, identity);
            } else {
                ret = arg.getFalseCase() == null ? null : evaluateInScope(arg.getFalseCase(), scope, identity);
            }
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    private void executeCall(Call code, IBehavior behavior, IKActorsBehavior.Scope scope) {

        Long notifyId = scope.getListenerId();

        /**
         * Exec any calls that precede this one, so that the receiver is set
         */
        Object contextReceiver = null;
        for (Call chained : code.getChainedCalls()) {
            IKActorsBehavior.Scope fscope = scope.functional(contextReceiver);
            executeCall(code, behavior, fscope);
            contextReceiver = fscope.getValueScope();
        }

        boolean synchronize = false;

        if (code.getActions().size() > 0) {

            synchronize = scope.isSynchronous();

            notifyId = nextId.incrementAndGet();
            MatchActions actions = new MatchActions(behavior, scope);
            for (Triple<IKActorsValue, IKActorsStatement, String> adesc : code.getActions()) {
                actions.matches.add(
                        new Pair<Match, IKActorsStatement>(new Match(adesc.getFirst(), adesc.getThird()), adesc.getSecond()));
            }
            this.listeners.put(notifyId, actions);
        }

        if (code.getGroup() != null) {
            // TODO finish handling group actions
            execute(code.getGroup(), behavior, ((ActorScope) scope).withNotifyId(notifyId));
            return;
        }

        String receiverName = "self";
        String messageName = code.getMessage();
        if (messageName.contains(".")) {
            receiverName = Path.getLeading(messageName, '.');
            messageName = Path.getLast(messageName, '.');
        }

        if (!"self".equals(receiverName)) {

            /*
             * Check first if the recipient is a Java peer and in that case, use reflection to send
             * it the message and return.
             */
            if (this.javaReactors.containsKey(receiverName)
                    || scope.getFrameSymbols().containsKey(receiverName) && !Utils.isPOD(scope.getSymbolTable().get(receiverName))
                    || scope.getSymbolTable().containsKey(receiverName) && !Utils.isPOD(scope.getSymbolTable().get(receiverName))
                    || scope.getGlobalSymbols().containsKey(receiverName)
                            && !Utils.isPOD(scope.getGlobalSymbols().get(receiverName))) {

                Object reactor = this.javaReactors.get(receiverName);
                if (reactor == null) {
                    reactor = scope.getFrameSymbols().get(receiverName);
                }
                if (reactor == null) {
                    reactor = scope.getSymbolTable().get(receiverName);
                }
                if (reactor == null) {
                    reactor = scope.getGlobalSymbols().get(receiverName);
                }
                if (reactor != null) {
                    Actors.INSTANCE.invokeReactorMethod(reactor, messageName, code.getArguments(), scope, this.identity);
                }

                return;
            }

            /*
             * Check if the name corresponds to the tag of an executor created using new. If so, the
             * actor (or component) has priority over a possible actor of the same name or a
             * variable containing an actor.
             */
            if (this.localActionExecutors.containsKey(receiverName)) {
                KActorsMessage m = new KActorsMessage(receiver, messageName, code.getCallId(), code.getArguments(),
                        ((ActorScope) scope).withNotifyId(notifyId), appId);
                this.localActionExecutors.get(receiverName).onMessage(m, scope);
                ((ActorScope) scope).waitForGreen(code.getFirstLine());
                return;
            }

            /*
             * Otherwise, an actor reference with this local name may have been passed as a
             * parameter or otherwise set in the symbol table as a variable.
             */
            ActorRef<KlabMessage> recipient = null;
            Object potentialRecipient = scope.getFrameSymbols().get(receiverName);
            if (!(potentialRecipient instanceof IActorIdentity)) {
                potentialRecipient = scope.getSymbolTable().get(receiverName);
            }
            if (potentialRecipient instanceof IActorIdentity) {
                try {
                    recipient = ((ActorReference) ((IActorIdentity<KlabMessage>) potentialRecipient).getActor()).actor;
                } catch (Throwable t) {
                    // TODO do something with the failed call, the actor should probably remember
                    if (this.identity instanceof IRuntimeIdentity) {
                        ((IRuntimeIdentity) this.identity).getMonitor()
                                .error("error executing actor call " + messageName + ": " + t.getMessage());
                    }
                    return;
                }
            } /* TODO check if it's a library! */ else {
                /*
                 * Only remaining choice for an explicit actor name must be in the recipient table.
                 */
                recipient = receivers.get(receiverName);
            }

            if (recipient == null) {
                /*
                 * No recipient, we just set this to the user actor which will turn the message into
                 * whatever is set for unknown messages. This not returning null guarantees that the
                 * message will arrive.
                 */
                recipient = ((ActorReference) (identity.getParentIdentity(EngineUser.class).getActor())).actor;
            }

            if (synchronize) {
                scope.getRuntimeScope().getMonitor()
                        .warn("External actor calls are being made within a synchronous scope: this should "
                                + " never happen. The synchronization is being ignored.");
            }

            recipient.tell(new KActorsMessage(receiver, messageName, code.getCallId(), code.getArguments(),
                    ((ActorScope) scope).withNotifyId(notifyId), appId));

            return;

        }

        Action libraryActionCode = null;

        /*
         * check if the call is a method from the library and if it applies to the context receiver
         * in case we have one.
         */
        for (Library library : libraries.values()) {
            if (library.methods.containsKey(messageName)) {

                CallDescriptor method = library.methods.get(messageName);
                if (method.method != null) {

                    if (scope.getValueScope() != null) {

                        /*
                         * must be compatible with the same argument of the method; otherwise we
                         * continue on to receiver call.
                         */
                        boolean ok = method.method.getParameterCount() > 0
                                && scope.getValueScope().getClass().isAssignableFrom(method.method.getParameters()[0].getType());

                        if (!ok) {
                            continue;
                        }

                    }

                    /*
                     * run through reflection and set the value scope to the result
                     */
                    List<Object> args = new ArrayList<>();
                    for (Object arg : code.getArguments().getUnnamedArguments()) {
                        args.add(arg instanceof KActorsValue ? evaluateInScope((KActorsValue) arg, scope, identity) : arg);
                    }
                    try {
                        ((ActorScope) scope)
                                .setValueScope(method.method.invoke(nativeLibraryInstances.get(library.name), args.toArray()));
                        return;
                    } catch (Throwable e) {
                        throw new KlabActorException(e);
                    }

                } else {

                    /*
                     * TODO it's an action from a k.Actors-specified library - just set it as the
                     * value of actionCode. It may be functional or not.
                     */
                }
            }
        }

        /*
         * at this point if we have a valueScope, we are calling a method on it.
         */
        if (scope.getValueScope() != null) {
            ((ActorScope) scope).setValueScope(Actors.INSTANCE.invokeReactorMethod(scope.getValueScope(), messageName,
                    code.getArguments(), scope, identity));
            return;
        }

        /*
         * If we get here, the message is directed to self and it may specify an executor or a
         * k.Actors behavior action. A coded action takes preference over a system behavior
         * executor.
         * 
         * Now we're in the appropriate scope for synchronous execution if we have actions after
         * fire.
         */
        scope = scope.fence(synchronize);

        // TODO if libraryActionCode is not null, we should override only if the library
        // wasn't
        // explicitly
        // stated.
        Action actionCode = behavior.getAction(messageName);
        if (actionCode != null || libraryActionCode != null) {
            /*
             * local action overrides a library action
             */
            run(actionCode, behavior, ((ActorScope) scope)
                    .matchFormalArguments(code, (actionCode == null ? libraryActionCode : actionCode)).withNotifyId(notifyId));
            return;
        }

        String executorId = (this.childActorPath == null ? "" : (this.childActorPath + "_")) + code.getCallId();

        /*
         * Remaining option is a code action executor installed through a system behavior. The
         * executor cache is populated at every execution of the same call, so this will be
         * instantiated only if the call has been executed before (in a loop or upon repeated calls
         * of the same action).
         */
        KlabActionExecutor executor = actionCache.get(executorId);

        if (executor == null) {
            Class<? extends KlabActionExecutor> actionClass = Actors.INSTANCE.getActionClass(messageName);
            if (actionClass != null) {

                executor = Actors.INSTANCE.getSystemAction(messageName, (IActorIdentity<KlabMessage>) scope.getIdentity(),
                        code.getArguments(), ((ActorScope) scope).withNotifyId(notifyId), receiver, executorId);

                if (executor != null) {

                    if (!executor.isSynchronized()) {
                        // disable the fencing if it's there
                        ((ActorScope) scope).setSemaphore(null);
                    }

                    actionCache.put(executorId, executor);

                    if (executor instanceof KlabActionExecutor.Actor) {

                        /*
                         * if it has a tag, store for later reference.
                         */
                        if (code.getArguments().containsKey("tag")) {
                            Object t = code.getArguments().get("tag");
                            if (t instanceof KActorsValue) {
                                t = ((KActorsValue) t).evaluate(scope, identity, true);
                            }
                            ((KlabActionExecutor.Actor) executor).setName(t.toString());
                            this.localActionExecutors.put(((KlabActionExecutor.Actor) executor).getName(),
                                    (KlabActionExecutor.Actor) executor);
                        }
                    }

                    /*
                     * if there are actions, set the bindings
                     */
                    if (code.getActions().size() > 0) {
                        this.actionBindings.put(executorId, notifyId);
                    }
                }
            }
        }

        if (executor instanceof KlabWidgetActionExecutor) {

            /*
             * the run() method in these is never called: they act through their view components
             */
            ViewComponent viewComponent = ((KlabWidgetActionExecutor) executor).getViewComponent();

            // may be null if the addition of the component happens as the result of an
            // action
            // enqueued by the component on this actor, run and notified by the message
            // handler
            // after the call.
            if (viewComponent != null) {
                scope.getViewScope().setViewMetadata(viewComponent, executor.getArguments(), scope);
                viewComponent.setIdentity(this.identity.getId());
                viewComponent.setApplicationId(this.appId);
                viewComponent.setParentId(code.getCallId()); // check - seems
                                                             // wrong
                viewComponent.setId(executorId);
                viewComponent.setActorPath(this.childActorPath);
                ((KlabWidgetActionExecutor) executor).setInitializedComponent(viewComponent);
                scope.getViewScope().getCurrentComponent().getComponents().add(viewComponent);
            }

        } else if (executor != null) {
            executor.run(((ActorScope) scope).withNotifyId(notifyId).withSender(receiver, appId));
        }

        /*
         * if the scope was not synchronous, or there were no actions after a fire, this does
         * nothing. TODO In case of errors causing no fire, though, it will wait forever, so there
         * should be a way to break the wait.
         */
        ((ActorScope) scope).waitForGreen(code.getFirstLine());

    }
}
