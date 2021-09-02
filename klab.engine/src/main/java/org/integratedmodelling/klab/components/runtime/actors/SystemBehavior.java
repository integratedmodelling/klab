package org.integratedmodelling.klab.components.runtime.actors;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
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

        String behavior;
        Scope scope;
        // application ID for forwarding
        String forwardApplicationId;
        // application ID to put in new actor
        String applicationId;
        IActorIdentity<KlabMessage> identity;
        // if not null, this is a child behavior from a 'new' instruction and it carries
        // a ref to the parent
        ActorRef<KlabMessage> parent = null;
        Map<String, Object> metadata;
        Map<String, Object> arguments;
        // if not null, loading happens as a response to a 'new' and the base name
        // identifies the instantiation for all needed purposes
        String instanceBaseName;
        String childActorPath;

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
            this.scope = new Scope(identity, appId, scope, Actors.INSTANCE.getBehavior(behavior));
        }
        
        /**
         * Called from instantiator in actors, uses the scope it's run into.
         * 
         * @param identity
         * @param behavior
         * @param appId
         * @param scope
         */
        public Load(IActorIdentity<KlabMessage> identity, String behavior, String appId, Scope scope) {
            this.behavior = behavior;
            this.forwardApplicationId = appId;
            this.identity = identity;
            this.scope = scope;
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
            return new Load(identity, behavior, null, scope.runtimeScope);
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
    }

    /**
     * Load a behavior
     * 
     * @author Ferd
     *
     */
    public static class Stop extends AbstractKlabMessage {

        String appId;

        public Stop(String appId) {
            this.appId = appId;
        }

        @Override
        public Stop direct() {
            throw new KlabIllegalStateException("Actors shouldn't stop themselves.");
        }
    }

    /**
     * Notify a user action from a view to the actor that must process it as a message.
     * 
     * @author Ferd
     *
     */
    public static class UserAction extends AbstractKlabMessage {

        ViewAction action;
        IRuntimeScope scope;
        String appId;

        public UserAction(ViewAction action, String appId, IRuntimeScope scope) {
            this.action = action;
            this.appId = appId;
            this.scope = scope;
        }

        @Override
        public UserAction direct() {
            return new UserAction(action, null, scope);
        }
    }

    /**
     * Notify a user action from a view to the actor that must process it as a message.
     * 
     * @author Ferd
     *
     */
    public static class UserMenuAction extends AbstractKlabMessage {

        MenuAction action;
        IRuntimeScope scope;
        String appId;

        public UserMenuAction(MenuAction action, String appId, IRuntimeScope scope) {
            this.action = action;
            this.appId = appId;
            this.scope = scope;
        }

        @Override
        public UserMenuAction direct() {
            return new UserMenuAction(action, null, scope);
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

        long notifyId;
        String componentId;
        String appId;

        public BindUserAction(long notifyId, String appId, String componentId) {
            this.notifyId = notifyId;
            this.componentId = componentId;
            this.appId = appId;
        }

        @Override
        public BindUserAction direct() {
            return new BindUserAction(notifyId, null, componentId);
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

        KlabActor.Scope scope;
        String appId;

        public Transition(String appId, KlabActor.Scope scope) {
            this.scope = scope;
            this.appId = appId;
        }

        @Override
        public Transition direct() {
            return new Transition(null, scope);
        }
    }

    /**
     * Spawn an appropriate child actor.
     * 
     * @author Ferd
     *
     */
    public static class Spawn extends AbstractKlabMessage {

        IActorIdentity<KlabMessage> identity;
        String appId;

        public Spawn(IActorIdentity<KlabMessage> identity, String appId) {
            this.identity = identity;
            this.appId = appId;
        }

        @Override
        public Spawn direct() {
            return new Spawn(identity, null);
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

        Object value;
//        boolean finalize;
        Long listenerId;
        String appId;
        Semaphore semaphore;
        Map<String, Object> scopeVars;

        public Fire(Long listenerId, Object firedValue/* , boolean isFinal */, String appId, Semaphore semaphore,
                Map<String, Object> scopeVars) {
            this.value = firedValue;
//            this.finalize = isFinal;
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

    }

    /**
     * The message sent back to a listening actor when a child component fires, triggering pattern
     * matching on the actions installed after the 'new' action that created it.
     * 
     * @author Ferd
     *
     */
    public static class ComponentFire extends AbstractKlabMessage {

        Object value;
        boolean finalize;
        String listenerId;
        ActorRef<KlabMessage> child;

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

        ViewComponent group;
        String componentPath;
        IParameters<String> arguments;
        Scope scope;
        long id = nextId.incrementAndGet();
        
        public AddComponentToGroup(ViewComponent group, String componentPath, IParameters<String> arguments, Scope scope) {
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

    }

    public static class AppReset extends AbstractKlabMessage {

        Scope scope;
        String appId;

        public AppReset(Scope scope, String appId) {
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

    }

    /**
     * The shuttle for a k.Actors message call. Always comes from a k.Actors behavior, sent by an
     * actor to another.
     * 
     * @author Ferd
     *
     */
    public static class KActorsMessage extends AbstractKlabMessage {

        ActorRef<KlabMessage> sender;
        String message;
        IParameters<String> arguments = Parameters.create();
        KlabActor.Scope scope;
        String appId;
        // for caching
        String actionInternalId;

        public KActorsMessage(ActorRef<KlabMessage> sender, String actionId, String actionInternalId,
                IParameters<String> arguments, KlabActor.Scope scope, String appId) {

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

    }

}
