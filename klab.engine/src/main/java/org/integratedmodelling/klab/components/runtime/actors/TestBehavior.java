package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.EnumUtils;
import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsValue.Type;
import org.integratedmodelling.kactors.model.KActorsArguments;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerScope;
import org.integratedmodelling.klab.api.data.general.IExpression.Forcing;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.monitoring.IInspector;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.debugger.Inspector;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.SessionState;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabActorException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Parameters;
import org.joda.time.Period;

import akka.actor.typed.ActorRef;

@Behavior(id = "test", version = Version.CURRENT)
public class TestBehavior {

    @Action(id = "test", fires = {}, description = "Run all the test included in one or more projects, naming the project ID, "
            + "a URL or a Git URL (git:// or http....*.git")
    public static class Test extends KlabActionExecutor {

        public Test(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
            for (Object arg : arguments.values()) {
                runTest(evaluate(arg, scope), scope);
            }
        }
    }

    public static void runTest(Object arg, IKActorsBehavior.Scope scope) {
        /*
         * Object may be a project, a project name from the workspace, or a Git URL
         */
        IProject project = getProject(arg, scope.getRuntimeScope().getMonitor());
        if (project != null) {
            if (project != null) {
                scope.getMonitor().info("Test engine: running test cases from " + project.getName());
                final AtomicBoolean done = new AtomicBoolean(false);
                for (IBehavior testcase : project.getUnitTests()) {

                    if (scope.getIdentity() instanceof Session) {
                        ((Session) scope.getIdentity()).loadScript(testcase, scope.getChild(testcase), () -> done.set(true));
                    } else {
                        scope.getIdentity().load(testcase, scope.getRuntimeScope());
                    }

                    /*
                     * wait until test is done
                     */
                    while(!done.get()) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }

                }
            }
        } else {
            scope.getMonitor().error("Cannot open project " + arg);
        }
    }

    private static IProject getProject(Object arg, IMonitor monitor) {
        IProject ret = null;
        if (arg instanceof String) {
            if (arg.toString().startsWith("http") || arg.toString().startsWith("git:")) {
                IProject existing = Resources.INSTANCE.getLocalWorkspace()
                        .getProject(MiscUtilities.getURLBaseName(arg.toString()));
                if (existing != null) {
                    monitor.warn("Project " + existing.getName() + " is present in the local workspace: using local version");
                    return existing;
                }
                ret = Resources.INSTANCE.retrieveAndLoadProject(arg.toString());
            } else if (Resources.INSTANCE.getProject(arg.toString()) != null) {
                ret = Resources.INSTANCE.getProject(arg.toString());
            }
        } else if (arg instanceof IProject) {
            ret = (IProject) arg;
        }
        return ret;
    }

    /**
     * Used within KlabActor to compare a returned value with an expected one in a test scope. If
     * we're not in test scope, send an exception to the monitor on lack of match.
     * 
     * @param scope
     * @param comparison
     */
    public static void evaluateAssertion(Object target, Assertion assertion, IKActorsBehavior.Scope scope, IParameters<String> arguments) {

        if (target instanceof IKActorsValue) {
            target = KlabActor.evaluateInScope((KActorsValue) target, scope, scope.getIdentity());
        }

        IRuntimeScope runtimeScope = (IRuntimeScope)scope.getRuntimeScope();
        if (target instanceof IObservation) {
            runtimeScope = (IRuntimeScope) ((IObservation) target).getScope();
        }

        IKActorsValue comparison = assertion.getValue();

        // TODO Auto-generated method stub
        IKimExpression selector = null;
        IObservationGroup distribute = null;
        boolean ok = false;

        if (arguments.containsKey("select")) {
            Object sel = arguments.get("select");
            if (sel instanceof IKimExpression) {
                selector = (IKimExpression) sel;
            } else if (sel instanceof IKActorsValue && ((IKActorsValue) sel).getType() == Type.EXPRESSION) {
                selector = ((IKActorsValue) sel).as(IKimExpression.class);
            }
        }

        if (arguments.containsKey("foreach")) {

        }

        Object compareValue = null;
        Descriptor compareDescriptor;
        IExpression compareExpression = null;
        Descriptor selectDescriptor;
        IExpression selectExpression = null;
        Map<String, IState> states = new HashMap<>();

        if (comparison != null) {
            if (comparison.getType() == Type.EXPRESSION) {

                IKimExpression expr = comparison.as(IKimExpression.class);
                compareDescriptor = Extensions.INSTANCE.getLanguageProcessor(expr.getLanguage()).describe(expr.getCode(),
                        runtimeScope.getExpressionContext().scalar(expr.isForcedScalar() ? Forcing.Always : Forcing.AsNeeded));
                compareExpression = compareDescriptor.compile();
                for (String input : compareDescriptor.getIdentifiers()) {
                    if (compareDescriptor.isScalar(input) && runtimeScope.getArtifact(input, IState.class) != null) {
                        IState state = runtimeScope.getArtifact(input, IState.class);
                        if (state != null) {
                            states.put(state.getObservable().getName(), state);
                        }
                    }
                }
            } else {
                compareValue = comparison.evaluate(scope, scope.getIdentity(), true);
            }
        }

        if (selector != null) {
            selectDescriptor = Extensions.INSTANCE.getLanguageProcessor(selector.getLanguage())
                    // TODO parameter only if target is a state
                    .describe(selector.getCode(),
                            runtimeScope.getExpressionContext(null).withCompilerScope(CompilerScope.Scalar));
            selectExpression = selectDescriptor.compile();
            for (String input : selectDescriptor.getIdentifiers()) {
                if (selectDescriptor.isScalar(input) && runtimeScope.getArtifact(input, IState.class) != null) {
                    IState state = runtimeScope.getArtifact(input, IState.class);
                    if (state != null) {
                        states.put(state.getObservable().getName(), state);
                    }
                }
            }
        }

        IParameters<String> args = Parameters.create();
        long nErr = 0;

        if (target instanceof IState) {

            states.put("self", (IState) target);

            for (ILocator locator : runtimeScope.getScale()) {

                args.clear();
                for (String key : states.keySet()) {
                    args.put(key, states.get(key).get(locator));
                }
                if (selectExpression != null) {
                    Object selectValue = selectExpression.eval(runtimeScope, args);
                    if (selectValue instanceof Boolean && !((Boolean) selectValue)) {
                        continue;
                    }
                }

                if (compareExpression != null) {
                    compareValue = compareExpression.eval(runtimeScope, args);
                    ok = compareValue instanceof Boolean && (Boolean) compareValue;
                } else {
                    ok = args.get("self") == null && compareValue == null
                            || (args.get("self") != null && args.get("self").equals(compareValue));
                }

                if (!ok) {
                    nErr++;
                }
            }
        } else {

            if (assertion.getExpression() != null) {

                Object ook = KlabActor.evaluateInScope((KActorsValue) assertion.getExpression(), scope, scope.getIdentity());
                ok = ook instanceof Boolean && (Boolean) ook;

            } else if (comparison == null) {
                ok = target == null;
            } else {
                ok = Actors.INSTANCE.matches(comparison, target, scope);
            }

            if (!ok) {
                nErr++;
            }
        }

        if (scope.getTestScope() == null && nErr > 0) {
            throw new KlabActorException("assertion failed on '" + comparison + "' with " + nErr + " mismatches");
        }

        scope.getTestScope().notifyAssertion(target, comparison, ok, assertion);
    }

    @Action(id = "whitelist", fires = {})
    public static class Constrain extends KlabActionExecutor {

        public Constrain(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {

            List<Object> args = new ArrayList<>();
            for (Object o : arguments.getUnnamedArguments()) {
                if (o instanceof KActorsValue) {
                    o = ((KActorsValue) o).evaluate(scope, identity, true);
                }
                args.add(o);
            }

            ((SessionState) scope.getRuntimeScope().getSession().getState()).whitelist((Object[]) args.toArray());

        }

    }

    @Action(id = "require", fires = {}, description = "Checks accessibility of various elements in the k.LAB environment and disables a behavior if not. In test scope, will skip all tests and record the skipped actions.")
    public static class Require extends KlabActionExecutor {

        public Require(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {

        }
    }

    @Action(id = "blacklist", fires = {})
    public static class Exclude extends KlabActionExecutor {

        public Exclude(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object o : arguments.getUnnamedArguments()) {
                if (o instanceof KActorsValue) {
                    o = ((KActorsValue) o).evaluate(scope, identity, true);
                }
                args.add(o);
            }

            ((SessionState) scope.getRuntimeScope().getSession().getState()).whitelist((Object[]) args.toArray());
        }

    }

    @Action(id = "inspect", fires = {Type.ANYVALUE}, synchronize = false)
    public static class Inspect extends KlabActionExecutor {

        public Inspect(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, IKActorsBehavior.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        public void run(IKActorsBehavior.Scope scope) {

            List<Object> triggerArguments = new ArrayList<>();

            for (Object o : arguments.getUnnamedArguments()) {

                if (o instanceof KActorsValue) {

                    if (((KActorsValue) o).getType() == Type.CONSTANT) {

                        o = ((KActorsValue) o).evaluate(scope, identity, true);

                        /*
                         * check against all enums
                         */
                        if (EnumUtils.isValidEnum(IInspector.Asset.class, o.toString())) {
                            triggerArguments.add(IInspector.Asset.valueOf(o.toString()));
                        } else if (EnumUtils.isValidEnum(IInspector.Metric.class, o.toString())) {
                            triggerArguments.add(IInspector.Metric.valueOf(o.toString()));
                        } else if (EnumUtils.isValidEnum(IInspector.Event.class, o.toString())) {
                            triggerArguments.add(IInspector.Event.valueOf(o.toString()));
                        } else if (EnumUtils.isValidEnum(IInspector.Action.class, o.toString())) {
                            triggerArguments.add(IInspector.Action.valueOf(o.toString()));
                        } else if (EnumUtils.isValidEnum(ValueOperator.class, o.toString())) {
                            triggerArguments.add(ValueOperator.valueOf(o.toString()));
                        }
                    } else {
                        o = ((KActorsValue) o).evaluate(scope, identity, true);
                        triggerArguments.add(o);
                    }
                }
            }

            // inspector is reset to null after each test, but not at regular actions, so the first
            // inspect in a test action will pass through here
            if (scope.getRuntimeScope().getSession().getState().getInspector() == null) {
                ((SessionState) scope.getRuntimeScope().getSession().getState()).setInspector(new Inspector());
            }

            if (arguments.get("reset") != null) {
                Object reset = arguments.get("reset");
                if (reset instanceof KActorsValue) {
                    reset = ((KActorsValue) reset).evaluate(scope, scope.getIdentity(), true);
                }
                if (reset instanceof Boolean && (Boolean) reset) {
                    ((Inspector) scope.getRuntimeScope().getSession().getState().getInspector()).reset();
                }
            }

            ((SessionState) scope.getRuntimeScope().getSession().getState()).getInspector().setTrigger((trigger, sc) -> {
                // TODO set variables in scope for expressions and assertions
                fire(trigger.getSubject(), scope);
            }, (Object[]) triggerArguments.toArray());
        }

    }

}
