package org.integratedmodelling.klab.components.runtime.actors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.vm.ActorScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.rest.MenuAction;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.utils.Parameters;

import akka.actor.typed.ActorRef;

/**
 * Messages not accessible to users but necessary to enable the base runtime actor communication
 * protocols. Includes spawning appropriate child actors, loading behavior files, firing things and
 * sending k.Actors message calls. Their initialize() method won't be called.
 * 
 * @author Ferd
 *
 */
public class SystemBehavior {

    static AtomicLong nextId = new AtomicLong(1l);

    /**
     * Load a behavior
     * 
     * @author Ferd
     *
     */
    public static class Load extends AbstractKlabMessage {

        private String behavior;
        private IKActorsBehavior.Scope scope;
        // application ID for forwarding
        private String forwardApplicationId;
        // application ID to put in new actor
        private String applicationId;
        private IActorIdentity<KlabMessage> identity;
        // if not null, this is a child behavior from a 'new' instruction and it carries
        // a ref to the parent
        private ActorRef<KlabMessage> parent = null;
        private Map<String, Object> metadata;
        private Map<String, Object> arguments;
        // if not null, loading happens as a response to a 'new' and the base name
        // identifies the instantiation for all needed purposes
        private String instanceBaseName;
        private String childActorPath;

        /**
         * Called from actor identities, instantiates the actor scope
         * 
         * @param identity
         * @param behavior
         * @param appId
         * @param scope
         */
        public Load(IActorIdentity<KlabMessage> identity, String behavior, String appId, IRuntimeScope scope) {
            this.behavior = behavior;
            this.forwardApplicationId = appId;
            this.identity = identity;
            this.scope = new ActorScope(identity, appId, scope, Actors.INSTANCE.getBehavior(behavior));
        }

        /**
         * Called from instantiator in actors, uses the scope it's run into.
         * 
         * @param identity
         * @param behavior
         * @param appId
         * @param scope
         */
        public Load(IActorIdentity<KlabMessage> identity, String behavior, String appId, IKActorsBehavior.Scope scope) {
            this.behavior = behavior;
            this.forwardApplicationId = appId;
            this.identity = identity;
            this.scope = scope;
        }

        public Load(Load load) {
            this.behavior = load.behavior;
            this.forwardApplicationId = load.forwardApplicationId;
            this.identity = load.identity;
            this.scope = load.scope;
        }

        @Deprecated
        public String getForwardApplicationId() {
            return forwardApplicationId;
        }

        public void setForwardApplicationId(String forwardApplicationId) {
            this.forwardApplicationId = forwardApplicationId;
        }

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        public ActorRef<KlabMessage> getParent() {
            return parent;
        }

        public void setParent(ActorRef<KlabMessage> parent) {
            this.parent = parent;
        }

        public Map<String, Object> getArguments() {
            return arguments;
        }

        public void setArguments(Map<String, Object> arguments) {
            this.arguments = arguments;
        }

        public String getInstanceBaseName() {
            return instanceBaseName;
        }

        public void setInstanceBaseName(String instanceBaseName) {
            this.instanceBaseName = instanceBaseName;
        }

        public String getChildActorPath() {
            return childActorPath;
        }

        public void setChildActorPath(String childActorPath) {
            this.childActorPath = childActorPath;
        }

        /**
         * Pass when the main() function may have arguments, typically in components instantiated
         * through 'new'.
         * 
         * @param arguments
         * @return
         */
        public Load withMainArguments(Map<String, Object> arguments) {
            this.arguments = arguments;
            return this;
        }

        public Load withMetadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public Load withParent(ActorRef<KlabMessage> parent) {
            this.parent = parent;
            return this;
        }

        @Override
        public Load direct() {
            Load ret = new Load(this);
            ret.forwardApplicationId = null;
            return ret;
        }

        public Load withActorBaseName(String actorBaseName) {
            this.instanceBaseName = actorBaseName;
            return this;
        }

        public Load withChildActorPath(String parentActorPath) {
            this.childActorPath = parentActorPath;
            return this;
        }

        public Load withApplicationId(String appId) {
            this.applicationId = appId;
            return this;
        }

        public String getBehavior() {
            return behavior;
        }

        public void setBehavior(String behavior) {
            this.behavior = behavior;
        }

        public IKActorsBehavior.Scope getScope() {
            return scope;
        }

        public void setScope(IKActorsBehavior.Scope scope) {
            this.scope = scope;
        }

        public IActorIdentity<KlabMessage> getIdentity() {
            return identity;
        }

        public void setIdentity(IActorIdentity<KlabMessage> identity) {
            this.identity = identity;
        }

        public Map<String, Object> getMetadata() {
            return metadata;
        }

        public void setMetadata(Map<String, Object> metadata) {
            this.metadata = metadata;
        }

    }

    /**
     * Load a behavior
     * 
     * @author Ferd
     *
     */
    public static class Stop extends AbstractKlabMessage {

        private String appId;

        public Stop(String appId) {
            this.appId = appId;
        }

        @Override
        public Stop direct() {
            throw new KlabIllegalStateException("Actors shouldn't stop themselves.");
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }
    }

    /**
     * Notify a user action from a view to the actor that must process it as a message.
     * 
     * @author Ferd
     *
     */
    public static class UserAction extends AbstractKlabMessage {

        private ViewAction action;
        private IRuntimeScope scope;
        private String appId;

        public UserAction(ViewAction action, String appId, IRuntimeScope scope) {
            this.action = action;
            this.appId = appId;
            this.scope = scope;
        }

        @Override
        public UserAction direct() {
            return new UserAction(action, null, scope);
        }

        public ViewAction getAction() {
            return action;
        }

        public void setAction(ViewAction action) {
            this.action = action;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public IRuntimeScope getScope() {
            return scope;
        }

        public void setScope(IRuntimeScope scope) {
            this.scope = scope;
        }
    }

    /**
     * Notify a user action from a view to the actor that must process it as a message.
     * 
     * @author Ferd
     *
     */
    public static class UserMenuAction extends AbstractKlabMessage {

        private MenuAction action;
        private IRuntimeScope scope;
        private String appId;

        public UserMenuAction(MenuAction action, String appId, IRuntimeScope scope) {
            this.action = action;
            this.appId = appId;
            this.scope = scope;
        }

        @Override
        public UserMenuAction direct() {
            return new UserMenuAction(action, null, scope);
        }

        public MenuAction getAction() {
            return action;
        }

        public void setAction(MenuAction action) {
            this.action = action;
        }

        public IRuntimeScope getScope() {
            return scope;
        }

        public void setScope(IRuntimeScope scope) {
            this.scope = scope;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

    }

    /**
     * Bind an action's notification ID to the ID of a component in the associated view, so that
     * matches can be triggered from the view even if the view was built before the action existed.
     * 
     * @author Ferd
     *
     */
    public static class BindUserAction extends AbstractKlabMessage {

        private long notifyId;
        private String componentId;
        private String appId;

        public BindUserAction(long notifyId, String appId, String componentId) {
            this.notifyId = notifyId;
            this.componentId = componentId;
            this.appId = appId;
        }

        @Override
        public BindUserAction direct() {
            return new BindUserAction(notifyId, null, componentId);
        }

        public long getNotifyId() {
            return notifyId;
        }

        public void setNotifyId(long notifyId) {
            this.notifyId = notifyId;
        }

        public String getComponentId() {
            return componentId;
        }

        public void setComponentId(String componentId) {
            this.componentId = componentId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

    }

    /**
     * Sent before stopping to ensure that listeners are unregistered and any other cleanup
     * operations are performed.
     * 
     * @author Ferd
     *
     */
    public static class Cleanup extends AbstractKlabMessage {

        @Override
        public KlabMessage direct() {
            return null;
        }

    }

    /**
     * Report a temporal transition
     * 
     * @author Ferd
     *
     */
    public static class Transition extends AbstractKlabMessage {

        private IKActorsBehavior.Scope scope;
        private String appId;

        public Transition(String appId, IKActorsBehavior.Scope scope) {
            this.scope = scope;
            this.appId = appId;
        }

        @Override
        public Transition direct() {
            return new Transition(null, scope);
        }

        public IKActorsBehavior.Scope getScope() {
            return scope;
        }

        public void setScope(IKActorsBehavior.Scope scope) {
            this.scope = scope;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

    }

    /**
     * Spawn an appropriate child actor.
     * 
     * @author Ferd
     *
     */
    public static class Spawn extends AbstractKlabMessage {

        private IActorIdentity<KlabMessage> identity;
        private String appId;

        public Spawn(IActorIdentity<KlabMessage> identity, String appId) {
            this.identity = identity;
            this.appId = appId;
        }

        @Override
        public Spawn direct() {
            return new Spawn(identity, null);
        }

        public IActorIdentity<KlabMessage> getIdentity() {
            return identity;
        }

        public void setIdentity(IActorIdentity<KlabMessage> identity) {
            this.identity = identity;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

    }

    /**
     * The message sent back to a listening actor when an actor fires, triggering pattern matching.
     * If finalize == true, the listener in the actor must be removed as the sending actor won't
     * fire again.
     * 
     * @author Ferd
     *
     */
    public static class Fire extends AbstractKlabMessage {

        private Object value;
        private Long listenerId;
        private String appId;
        private Semaphore semaphore;
        private Map<String, Object> scopeVars;

        public Fire(Long listenerId, Object firedValue/* , boolean isFinal */, String appId, Semaphore semaphore,
                Map<String, Object> scopeVars) {
            this.value = firedValue;
            this.listenerId = listenerId;
            this.appId = appId;
            this.semaphore = semaphore;
            this.scopeVars = new HashMap<>(scopeVars);
        }

        @Override
        public String toString() {
            return "[FIRE" + value + " @" + listenerId + "]";
        }

        @Override
        public Fire direct() {
            return new Fire(listenerId, value/* , finalize */, null, semaphore, scopeVars);
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public Long getListenerId() {
            return listenerId;
        }

        public void setListenerId(Long listenerId) {
            this.listenerId = listenerId;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Map<String, Object> getScopeVars() {
            return scopeVars;
        }

        public void setScopeVars(Map<String, Object> scopeVars) {
            this.scopeVars = scopeVars;
        }

        public Semaphore getSemaphore() {
            return semaphore;
        }

        public void setSemaphore(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

    }

    /**
     * The message sent back to a listening actor when a child component fires, triggering pattern
     * matching on the actions installed after the 'new' action that created it.
     * 
     * @author Ferd
     *
     */
    public static class ComponentFire extends AbstractKlabMessage {

        private Object value;
        private boolean finalize;
        private String listenerId;
        private ActorRef<KlabMessage> child;

        public ComponentFire(String listenerId, Object firedValue, ActorRef<KlabMessage> child) {
            this.value = firedValue;
            this.listenerId = listenerId;
            this.child = child;
        }

        @Override
        public String toString() {
            return "[COMPONENT FIRE" + value + " @" + listenerId + "]";
        }

        @Override
        public ComponentFire direct() {
            return new ComponentFire(listenerId, value, child);
        }

        public String getListenerId() {
            return listenerId;
        }

        public void setListenerId(String listenerId) {
            this.listenerId = listenerId;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

    }

    /**
     * Invoked internally by the group action handler when components must be added to a group. The
     * handler prepares the message to send to the same actor that invoked it; the actor finishes
     * handling the action, then creates the new component, adds it to the group and notifies the
     * view of the changed group structure.
     * 
     * @author Ferd
     *
     */
    public static class AddComponentToGroup extends AbstractKlabMessage {

        private ViewComponent group;
        private String componentPath;
        private IParameters<String> arguments;
        private IKActorsBehavior.Scope scope;
        private long id = nextId.incrementAndGet();

        public AddComponentToGroup(ViewComponent group, String componentPath, IParameters<String> arguments,
                IKActorsBehavior.Scope scope) {
            this.group = group;
            this.componentPath = componentPath;
            this.arguments = arguments;
            this.scope = scope;
        }

        @Override
        public String toString() {
            return "[ADD COMPONENT " + componentPath + " to " + group.getId() + "]";
        }

        @Override
        public AddComponentToGroup direct() {
            return new AddComponentToGroup(group, componentPath, arguments, scope);
        }

        public ViewComponent getGroup() {
            return group;
        }

        public void setGroup(ViewComponent group) {
            this.group = group;
        }

        public String getComponentPath() {
            return componentPath;
        }

        public void setComponentPath(String componentPath) {
            this.componentPath = componentPath;
        }

        public IParameters<String> getArguments() {
            return arguments;
        }

        public void setArguments(IParameters<String> arguments) {
            this.arguments = arguments;
        }

        public IKActorsBehavior.Scope getScope() {
            return scope;
        }

        public void setScope(IKActorsBehavior.Scope scope) {
            this.scope = scope;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

    }

    public static class AppReset extends AbstractKlabMessage {

        private IKActorsBehavior.Scope scope;
        private String appId;

        public AppReset(IKActorsBehavior.Scope scope, String appId) {
            this.scope = scope;
            this.appId = appId;
        }

        @Override
        public String toString() {
            return "[RESET]";
        }

        @Override
        public AppReset direct() {
            return this;
        }

        public IKActorsBehavior.Scope getScope() {
            return scope;
        }

        public void setScope(IKActorsBehavior.Scope scope) {
            this.scope = scope;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

    }

    /**
     * The shuttle for a k.Actors message call. Always comes from a k.Actors behavior, sent by an
     * actor to another.
     * 
     * @author Ferd
     *
     */
    public static class KActorsMessage extends AbstractKlabMessage {

        private ActorRef<KlabMessage> sender;
        private String message;
        private IParameters<String> arguments = Parameters.create();
        private IKActorsBehavior.Scope scope;
        private String appId;
        // for caching
        private String actionInternalId;

        public KActorsMessage(ActorRef<KlabMessage> sender, String actionId, String actionInternalId,
                IParameters<String> arguments, IKActorsBehavior.Scope scope, String appId) {

            this.sender = sender;
            this.message = actionId;
            this.actionInternalId = actionInternalId;
            if (arguments != null) {
                this.arguments.putAll(arguments);
            }
            this.scope = scope;
            this.appId = appId;
        }

        @Override
        public String toString() {
            return "[" + message + " @" + scope + "]";
        }

        @Override
        public KActorsMessage direct() {
            return new KActorsMessage(sender, message, actionInternalId, arguments, scope, null);
        }

        public ActorRef<KlabMessage> getSender() {
            return sender;
        }

        public void setSender(ActorRef<KlabMessage> sender) {
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public IParameters<String> getArguments() {
            return arguments;
        }

        public void setArguments(IParameters<String> arguments) {
            this.arguments = arguments;
        }

        public IKActorsBehavior.Scope getScope() {
            return scope;
        }

        public void setScope(IKActorsBehavior.Scope scope) {
            this.scope = scope;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getActionInternalId() {
            return actionInternalId;
        }

        public void setActionInternalId(String actionInternalId) {
            this.actionInternalId = actionInternalId;
        }

    }

}
