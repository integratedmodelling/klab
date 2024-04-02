package org.integratedmodelling.kactors.api;

import java.io.File;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.api.IKActorsStatement.Fail;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.actors.IBehavior.ActionMatch;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage.Semaphore;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.TestStatistics;
import org.integratedmodelling.klab.rest.ViewComponent;

/**
 * The syntactic peer resulting from parsing a .kactor file. Specifies a {@link IBehavior} to be
 * used in the k.LAB network. According to the type of behavior execution may be concurrent (the
 * default, where sequential action is only possible using the sequence syntax) or synchronous,
 * where commas are ignored and all execution is serial. In synchronous mode, k.Actors behave like a
 * scripting language: each action must fire or complete before the next statement is evaluated, and
 * multiple-firing components may cause unpredictable behavior.
 * 
 * @author Ferd
 *
 */
public interface IKActorsBehavior extends IKActorsCodeStatement {

    enum Type {
        /**
         * The behavior defines an observed actor. Normally bound to observations through a k.IM
         * bind annotation.
         */
        BEHAVIOR,
        /**
         * The behavior will be incorporated in a session actor, creating a session-level
         * application. Apps can only be run directly through CLI, drag/drop (modeler) or URL
         * identification (explorer) and may access project resources (such as logos) from the apps
         * directory.
         */
        APP,
        /**
         * The behavior will be incorporated in a user actor, intercepting any calls that won't make
         * it to other actors. A user actor definition is the only k.Actors resource that must be
         * located outside of a project, typically in a user profile and saved to the k.LAB data
         * directory.
         */
        USER,
        /**
         * The behavior is a collection of actions to be incorporated in another actor definition as
         * a collection of traits ("personality"). In an app context it can simply be declared as a
         * "library". Traits are imported using the 'import' clause in the k.Actors preamble, or
         * explicitly with a system action; they cannot be bound to anything directly. Unit tests
         * run in synchronous mode.
         */
        TRAITS,

        /**
         * The behavior is an app defining a collection of annotated unit tests. Can only be run
         * directly and explicitly.
         */
        UNITTEST,

        /**
         * A component is an actor that should be created only by other actors and normally provides
         * a piece of behavior including UI elements, or anything else that makes it "composable".
         * The system will reject any bindings to components and only let this be created using the
         * 'new' verb in apps.
         */
        COMPONENT,

        /**
         * A script is a batch job that is always run in synchronous mode and can only be run from
         * the IDE, CLI or through an engine launched with the task URN as an option (which will run
         * the task and then exit).
         */
        SCRIPT,

        /**
         * A task is a script that runs in normal asynchronous mode. It must have a main and can
         * only be run from the IDE, CLI or through an engine launched with the task URN as an
         * option (which will run the task and then exit).
         */
        TASK
    }

    enum Platform {
        ANY, DESKTOP, WEB, MOBILE
    }

    /**
     * A serializable reference to an actor, to which we can send a message or ask a question that
     * expects a response. Generalizes interaction with an actor using the common frameworks.
     * 
     * @author Ferd
     *
     */
    interface Ref extends Serializable {

        /**
         * Empty may mean that the response is null or that some agreed no-op value was set,
         * according to implementation.
         * 
         * @return
         */
        boolean isEmpty();

        /**
         * Send a message asynchronously.
         * 
         * @param <T>
         * @param message
         */
        <T extends Serializable> void tell(T message);

        /**
         * Ask a question and wait for the response. Assumes a fast response or no response;
         * implementations should use a sensible timeout and behave as needed (null return or
         * unchecked exception) if it's exceeded.
         * 
         * @param <T>
         * @param <R>
         * @param message
         * @param responseClass
         * @return
         */
        <T extends Serializable, R extends Serializable> R ask(T message, Class<? extends R> responseClass);

        /**
         * Ask a question and wait for the response for at most the passed duration. In case of
         * timeout, this method should behave the same way as {@link #ask(Object, Class)}.
         * 
         * @param <T>
         * @param <R>
         * @param message
         * @param responseClass
         * @param timeout
         * @return
         */
        <T extends Serializable, R extends Serializable> R ask(T message, Class<? extends R> responseClass, Duration timeout);

    }

    /**
     * The scope for evaluating k.Actors statements. Not used in the API except for passing to
     * 
     * @author Ferd
     *
     */
    public interface Scope {

        IMonitor getMonitor();

        boolean isSynchronous();

        IActorIdentity<?> getIdentity();

        String localize(String string);

        boolean hasValue(String string);

        Object getValue(String string);

        IContextualizationScope getRuntimeScope();

        Map<String, Object> getMetadata();

        Scope functional(Object contextReceiver);

        Scope withReceiver(Object contextReceiver);

        Object getValueScope();

        Object getMatchValue();

        Long getListenerId();

        Scope withValues(Map<String, Object> scopeVars);

        // Scope withMatch(ActionMatch match, Object value, Scope scope);

        boolean isFunctional();

        Long getNotifyId();

        Scope fence(boolean synchronize);

        Map<String, Object> getSymbols(IActorIdentity<?> identity);

        Map<String, Object> getSymbolTable();

        Map<String, Object> getFrameSymbols();

        Map<String, Object> getGlobalSymbols();

        Semaphore getSemaphore();

        IBehavior getBehavior();

        ViewScope getViewScope();

        TestScope getTestScope();

        String getAppId();

        void tellSender(KlabMessage message);

        Scope getChild(IBehavior testcase);

        Scope getChild(ConcurrentGroup group);

        Scope getChild(String applicationId, Action action);

        Scope withMatch(ActionMatch first, Object value, Scope withValues);

        Scope withValue(String variable, Object o);

        Scope synchronous();

        Scope forComponent();

        void onException(Throwable e, String message);

        Map<String, String> getLocalizedSymbols();

    }

    /**
     * Scope for UI operations, part of the overall scope when applications with UIs are run.
     * 
     * @author Ferd
     *
     */
    interface ViewScope {

        ViewComponent getCurrentComponent();

        Layout getLayout();

        void setViewMetadata(ViewComponent viewComponent, IParameters<String> arguments, Scope scope);

    }

    /**
     * Scope for test cases. Part of the overall scope when test cases are run.
     * 
     * @author Ferd
     *
     */
    interface TestScope {

        TestScope getChild(Action action);

        void onException(Throwable e);

        TestScope getChild(IBehavior behavior);

        void fail(Fail code);

        void finalizeTest(Action action, Object valueScope);

        TestStatistics getTestStatistics();

        void finalizeTestRun();

        void notifyAssertion(Object target, IKActorsValue comparison, boolean ok, Assertion assertion);

    }

    /**
     * Visitor for inspection or refactoring.
     * 
     * @author Ferd
     *
     */
    interface Visitor {

        void visitPreamble(String variable, Object value);

        void visitAction(IKActorsAction action);

        void visitStatement(IKActorsAction action, IKActorsStatement statement);

        void visitValue(IKActorsValue value, IKActorsStatement statement, IKActorsAction action);

        void visitMetadata(IKActorsCodeStatement kActorCodeStatement, String key, Object o);
    }

    /**
     * The fully qualified namespace ID for the behavior.
     * 
     * @return
     */
    String getName();

    /**
     * Who this behavior is for
     * 
     * @return
     */
    Type getType();

    /**
     * If this is an app, return the platform this is specialized for, which may be ANY. For any
     * other behavior, return ANY.
     * 
     * @return
     */
    Platform getPlatform();

    /**
     * Where it comes from. Always a file resource for now.
     * 
     * @return
     */
    File getFile();

    /**
     * A string linked to the 'output' preamble statement. Used in test cases to identify the log
     * file or available for other purposes in scripts.
     * 
     * @return
     */
    String getOutput();

    /**
     * All behaviors imported, resolved and parsed. May refer to imported behaviors or to libraries,
     * both native and k.Actors.
     * 
     * @return
     */
    List<String> getImports();

    /**
     * All the actions declared in this behavior (not in the imported ones)
     * 
     * @return
     */
    List<IKActorsAction> getActions();

    /**
     * If a style is specified in the preamble, return it here.
     * 
     * @return
     */
    String getStyle();

    /**
     * Return the declared locales as strings. If multiple, the locale can be set on loading.
     * 
     * @return
     */
    List<String> getLocales();

    /**
     * Label (docstring). This should/could be in metadata but we still have some API weirdness with
     * IKimMetadata being cumbersome.
     * 
     * @return
     */
    String getLabel();

    /**
     * Description (as per preamble). This should/could be in metadata but we still have some API
     * weirdness with IKimMetadata being cumbersome.
     * 
     * @return
     */
    String getDescription();

    /**
     * If a logo pathname (relative to the application) has been specified, return it.
     * 
     * @return
     */
    String getLogo();

    /**
     * Name of project we are declared into. Null if not in a project.
     * 
     * @return
     */
    String getProjectId();

    /**
     * Optional map of style specification (CSS-like) encoded in k.Actors as a map after "style
     * [<name> with] #{ ... }", all values and keys converted to strings.
     * 
     * @return
     */
    Map<String, String> getStyleSpecs();

    /**
     * True if 'public' was specified in front of the declaration, which makes the behavior visible
     * to clients before it is run. Only behaviors with getType() == Type.APP can be public.
     * 
     * @return
     */
    boolean isPublic();

    /**
     * Any version for display set in the app file.
     * 
     * @return
     */
    String getVersionString();

    /**
     * Visit all actions and statements in a behavior.
     * 
     * @param visitor
     */
    void visit(Visitor visitor);

}
