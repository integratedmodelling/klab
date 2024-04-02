package org.integratedmodelling.klab.components.runtime.actors;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsStatement.Call;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.actors.IBehavior.ActionMatch;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage.Semaphore;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabActorException;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.utils.Parameters;

import akka.actor.typed.ActorRef;

public class ActorScope implements IKActorsBehavior.Scope {

    private boolean synchronous = false;
    private ActorScope parent = null;
    private IRuntimeScope runtimeScope;
    private Long listenerId;
    private IActorIdentity<KlabMessage> identity;
    private Object match;
    private String appId;
    private Map<String, String> localizedSymbols = null;

    // local symbol table, frame-specific, holds counters and matches only
    private Map<String, Object> frameSymbols = new HashMap<>();
    // symbol table is set using 'def' and is local to an action
    private Map<String, Object> symbolTable = new HashMap<>();
    // global symbols are set using 'set' and include the read-only state of the actor identity
    private Map<String, Object> globalSymbols;

    private ViewScope viewScope;
    private ActorRef<KlabMessage> sender;
    private boolean initializing;
    private Semaphore semaphore = null;
    // metadata come with the actor specification if created through instantiation
    // and don't
    // change.
    private Parameters<String> metadata;
    private IBehavior behavior;

    /**
     * The scope is functional if an action that is declared as 'function' is called, or if the
     * executing action is part of a contextual chain (a1().a2().a3: ...). In this case any "fire"
     * statement does not fire a value but "returns" it, setting it in the scope and breaking the
     * execution.
     */
    private boolean functional = false;

    /*
     * the following two support chaining of actions, with the ones before the last "returning"
     * values (may be defined using 'function' or be system actions) which end up in the scope
     * passed to the next. Because null is a legitimate value scope, we also use a boolean to check
     * if the scope contains a "context" value from a previous function.
     */
    // private boolean hasValueScope = false;
    private Object valueScope = null;

    /**
     * Only instantiated in tests.
     */
    private IKActorsBehavior.TestScope testScope;

    public ActorScope(IActorIdentity<KlabMessage> identity, String appId, IRuntimeScope scope, IBehavior behavior) {
        this.runtimeScope = scope;
        this.identity = identity;
        this.appId = appId;
        this.viewScope = new ViewScope(this);
        this.metadata = Parameters.create();
        this.behavior = behavior;
        this.globalSymbols = new HashMap<>();
        this.localizedSymbols = behavior.getLocalization();
        if (behavior.getDestination() == Type.UNITTEST && identity instanceof Session) {
            this.testScope = ((Session) identity).getRootTestScope().getChild(behavior);
        }
    }

    public String localize(String string) {
        if (string != null) {
            if (string.startsWith("#") && this.localizedSymbols.containsKey(string.substring(1))) {
                string = this.localizedSymbols.get(string.substring(1));
            }
        }
        return string;
    }

    public ActorScope withMatch(ActionMatch match, Object value, IKActorsBehavior.Scope matchingScope) {

        ActorScope ret = new ActorScope(this);

        ret.symbolTable.putAll(matchingScope.getSymbolTable());
        ret.globalSymbols.putAll(matchingScope.getGlobalSymbols());

        /*
         * if we have identifiers either as key or in list key, match them to the values. Otherwise
         * match to $, $1, ... #n
         */
        if (match.isIdentifier(ret)) {
            ret.frameSymbols.put(match.getIdentifier(), value);
        } else if (match.isImplicit()) {
            String matchId = match.getMatchName() == null ? "$" : match.getMatchName();
            ret.frameSymbols.put(matchId, value);
            if (value instanceof Collection) {
                int n = 1;
                for (Object o : ((Collection<?>) value)) {
                    ret.frameSymbols.put(matchId + (n++), o);
                }
            }
        }
        ret.match = value;
        return ret;
    }

    public ActorScope(ActorScope scope) {
        this.globalSymbols = scope.globalSymbols;
        this.synchronous = scope.synchronous;
        this.runtimeScope = scope.runtimeScope;
        this.parent = scope;
        this.listenerId = scope.listenerId;
        this.sender = scope.sender;
        this.symbolTable = scope.symbolTable;
        this.frameSymbols.putAll(scope.frameSymbols);
        this.identity = scope.identity;
        this.viewScope = scope.viewScope;
        this.appId = scope.appId;
        this.semaphore = scope.semaphore;
        this.metadata = scope.metadata;
        this.behavior = scope.behavior;
        this.localizedSymbols = scope.localizedSymbols;
        // TODO check if we need to make a child and pass this
        this.testScope = scope.testScope;
    }

    public String toString() {
        return "{S " + listenerId + "}";
    }

    public ActorScope synchronous() {
        ActorScope ret = new ActorScope(this);
        ret.synchronous = true;
        return ret;
    }

    public ActorScope concurrent() {
        ActorScope ret = new ActorScope(this);
        ret.synchronous = false;
        return ret;
    }

    public ActorScope withNotifyId(Long id) {
        ActorScope ret = new ActorScope(this);
        ret.listenerId = id;
        return ret;
    }

    @Override
    public IActorIdentity<KlabMessage> getIdentity() {
        return identity;
    }

    public Long getNotifyId() {
        return listenerId;
    }

    @Override
    public boolean isSynchronous() {
        return this.synchronous;
    }

    @Override
    public Map<String, Object> getSymbolTable() {
        return this.symbolTable;
    }

    @Override
    public IMonitor getMonitor() {
        return this.runtimeScope == null ? null : this.runtimeScope.getMonitor();
    }

    public ActorScope withSender(ActorRef<KlabMessage> sender, String appId) {
        ActorScope ret = new ActorScope(this);
        ret.sender = sender;
        ret.appId = appId;
        return ret;
    }

    public boolean hasValue(String string) {
        if (frameSymbols.containsKey(string)) {
            return true;
        } else if (symbolTable.containsKey(string)) {
            return true;
        } else if (globalSymbols != null && globalSymbols.containsKey(string)) {
            return true;
        }
        return false;
    }

    public Object getValue(String string) {
        if (frameSymbols.containsKey(string)) {
            return frameSymbols.get(string);
        } else if (symbolTable.containsKey(string)) {
            return symbolTable.get(string);
        } else if (globalSymbols != null && globalSymbols.containsKey(string)) {
            return globalSymbols.get(string);
        }
        return identity.getState().get(string, Object.class);
    }

    /**
     * Get a child scope for this action, which will create a panel viewscope if the action has a
     * view.
     * 
     * @param appId
     * @param action
     * @return
     */
    public ActorScope getChild(String appId, Action action) {
        ActorScope ret = forAction(action);
        ret.viewScope = this.viewScope.getChild(action, appId, identity, ret);
        return ret;
    }

    /**
     * Copy of scope with specialized variable values in frame table.
     * 
     * @param variable
     * @param value
     * @return
     */
    public ActorScope withValues(Map<String, Object> variables) {
        ActorScope ret = new ActorScope(this);
        ret.frameSymbols.putAll(variables);
        return ret;
    }

    /**
     * Same, one value at a time.
     * 
     * @param variable
     * @param value
     * @return
     */
    public ActorScope withValue(String variable, Object value) {
        ActorScope ret = new ActorScope(this);
        ret.frameSymbols.put(variable, value);
        return ret;
    }

    public ActorScope withComponent(ViewComponent component) {
        ActorScope ret = new ActorScope(this);
        ret.viewScope.setCurrentComponent(component);
        return ret;
    }

    public ActorScope getChild(ConcurrentGroup code) {
        ActorScope ret = new ActorScope(this);
        if (!initializing && this.viewScope != null) {
            ret.viewScope = this.viewScope.getChild(code, ret);
        }
        return ret;
    }

    public Map<String, Object> getSymbols(IActorIdentity<?> identity) {
        Map<String, Object> ret = new HashMap<>();
        ret.putAll(identity.getState());
        if (globalSymbols != null) {
            ret.putAll(globalSymbols);
        }
        ret.putAll(symbolTable);
        ret.putAll(frameSymbols);
        return ret;
    }

    public ActorScope forInit() {
        ActorScope ret = new ActorScope(this);
        ret.initializing = true;
        return ret;
    }

    public ActorScope forTest(Action action) {
        ActorScope ret = new ActorScope(this);
        ret.initializing = true;
        ret.synchronous = true;
        ret.testScope = ret.testScope.getChild(action);
        return ret;
    }

    public void waitForGreen(final int linenumber) {

        if (semaphore != null) {
            int cnt = 0;
            while(!Actors.INSTANCE.expired(semaphore)) {

                if (this.getMonitor().isInterrupted()) {
                    break;
                }

                try {
                    Thread.sleep(60);
                    cnt++;
                    if (cnt % 1000 == 0 && !semaphore.isWarned()) {
                        identity.getMonitor().warn("Blocking action is taking longer than 1 minute at " + getBehavior().getName()
                                + ":" + linenumber);
                        semaphore.setWarned();
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    public ActorScope fence(boolean synchronize) {
        ActorScope ret = this;
        if (synchronize) {
            ret = new ActorScope(this);
            ret.semaphore = Actors.INSTANCE.createSemaphore(Semaphore.Type.FIRE);
        }
        return ret;
    }

    public ActorScope forWindow(IAnnotation wspecs, String actionId) {
        ActorScope ret = new ActorScope(this);
        ret.viewScope = ret.viewScope.createLayout(wspecs, actionId, ret);
        return ret;
    }

    public ActorScope forAction(IBehavior.Action action) {
        ActorScope ret = action.getStatement().isFunction() ? new ActorScope(this) : functional();
        ret.symbolTable = new HashMap<>(this.symbolTable);
        return ret;
    }

    public ActorScope functional() {
        ActorScope ret = new ActorScope(this);
        ret.functional = true;
        return ret;
    }

    public ActorScope functional(Object valueScope) {
        ActorScope ret = new ActorScope(this);
        ret.functional = true;
        ret.valueScope = valueScope;
        return ret;
    }

    public ActorScope withReceiver(Object valueScope) {
        ActorScope ret = new ActorScope(this);
        ret.valueScope = valueScope;
        return ret;
    }

    public IBehavior getBehavior() {
        return this.behavior;
    }

    public ActorScope matchFormalArguments(Call code, Action actionCode) {

        ActorScope ret = this;

        if (!actionCode.getFormalArguments().isEmpty()) {
            ret = new ActorScope(this);
            int i = 0;
            for (String farg : actionCode.getFormalArguments()) {
                Object value = null;
                if (code.getArguments().getUnnamedArguments().size() > i) {
                    Object argument = code.getArguments().getUnnamedArguments().get(i);
                    value = argument instanceof KActorsValue
                            ? ((KActorsValue) argument).evaluate(this, identity, false)
                            : argument;
                }
                ret.symbolTable.put(farg, value);
                i++;
            }
        }

        return ret;
    }

    public ActorScope withLayout(Layout layout) {
        if (this.viewScope != null) {
            this.viewScope.setLayout(layout);
        }
        return this;
    }

    public void onException(Throwable e, String message) {

        runtimeScope.getMonitor().error("actor exception: " + (message == null ? "" : message) + e.getMessage());

        if (testScope != null) {
            testScope.onException(e);
        }

    }

    public ActorScope getChild(IBehavior behavior) {
        ActorScope ret = new ActorScope(this);
        ret.behavior = behavior;
        if (this.testScope != null) {
            ret.testScope = ret.testScope.getChild(behavior);
        }
        return ret;
    }

    /**
     * The scope for a child component detaches both local and global symbols to keep them local to
     * the child.
     * 
     * @return
     */
    public ActorScope forComponent() {
        ActorScope ret = new ActorScope(this);
        ret.globalSymbols = new HashMap<>(globalSymbols);
        ret.symbolTable = new HashMap<>();
        ret.frameSymbols.clear();
        return ret;
    }

    @Override
    public IContextualizationScope getRuntimeScope() {
        return runtimeScope;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return metadata;
    }

    @Override
    public Object getValueScope() {
        return valueScope;
    }

    @Override
    public Long getListenerId() {
        return listenerId;
    }

    @Override
    public boolean isFunctional() {
        return functional;
    }

    @Override
    public Map<String, Object> getGlobalSymbols() {
        return globalSymbols;
    }

    @Override
    public Semaphore getSemaphore() {
        return this.semaphore;
    }

    @Override
    public IKActorsBehavior.ViewScope getViewScope() {
        return this.viewScope;
    }

    @Override
    public String getAppId() {
        return appId;
    }

    @Override
    public IKActorsBehavior.TestScope getTestScope() {
        return testScope;
    }

    public void tellSender(KlabMessage message) {
        if (this.sender != null) {
            this.sender.tell(message);
        } else {
            throw new KlabActorException("message has no sender: " + message);
        }
    }

    @Override
    public Object getMatchValue() {
        return match;
    }

    @Override
    public Map<String, Object> getFrameSymbols() {
        return frameSymbols;
    }
    
    @Override
    public Map<String, String> getLocalizedSymbols() {
        return localizedSymbols;
    }

    public void setMetadata(Parameters<String> parameters) {
        this.metadata = parameters;
    }

    public void setLocalizedSymbols(Map<String, String> localization) {
        this.localizedSymbols = localization;
    }

    public void setValueScope(Object value) {
        this.valueScope = value;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void setGlobalSymbols(Map<String, Object> globalState) {
        this.globalSymbols = globalState;
    }

    public ActorRef<KlabMessage> getSender() {
        return sender;
    }

}