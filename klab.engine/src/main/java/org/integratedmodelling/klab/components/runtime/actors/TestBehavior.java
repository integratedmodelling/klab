package org.integratedmodelling.klab.components.runtime.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.EnumUtils;
import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsValue.Type;
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
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.runtime.monitoring.IInspector;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
import org.integratedmodelling.klab.engine.debugger.Inspector;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.SessionState;
import org.integratedmodelling.klab.exceptions.KlabActorException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import akka.actor.typed.ActorRef;

@Behavior(id = "test", version = Version.CURRENT)
public class TestBehavior {

    private static final PeriodFormatter periodFormat = new PeriodFormatterBuilder().appendDays()
            .appendSuffix(" day", " days").appendSeparator(" ").printZeroIfSupported().minimumPrintedDigits(2)
            .appendHours().appendSeparator(":").appendMinutes().printZeroIfSupported().minimumPrintedDigits(2)
            .appendSeparator(":").appendSeconds().minimumPrintedDigits(2).appendSeparator(".")
            .appendMillis3Digit().toFormatter();

    @Action(id = "test", fires = {}, description = "Run all the test included in one or more projects, naming the project ID, "
            + "a URL or a Git URL (git:// or http....*.git")
    public static class Test extends KlabActionExecutor {

        public Test(IActorIdentity<KlabMessage> identity, IParameters<String> arguments,
                KlabActor.Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(KlabActor.Scope scope) {
            for (Object arg : arguments.values()) {
                runTest(evaluate(arg, scope), scope);
            }
        }
    }

    public static void runTest(Object arg, KlabActor.Scope scope) {
        /*
         * Object may be a project, a project name from the workspace, or a Git URL
         */
        IProject project = getProject(arg, scope.runtimeScope.getMonitor());
        if (project != null) {
            if (project != null) {
                scope.getMonitor().info("Test engine: running test cases from " + project.getName());
                final AtomicBoolean done = new AtomicBoolean(false);
                for (IBehavior testcase : project.getUnitTests()) {

                    if (scope.identity instanceof Session) {
                        ((Session) scope.identity).loadScript(testcase, scope.getChild(testcase),
                                () -> done.set(true));
                    } else {
                        scope.identity.load(testcase, scope.runtimeScope);
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
                    monitor.warn("Project " + existing.getName()
                            + " is present in the local workspace: using local version");
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
    public static void evaluateAssertion(Object target, Assertion assertion, Scope scope,
            IParameters<String> arguments) {

        if (target instanceof IKActorsValue) {
            target = KlabActor.evaluateInScope((KActorsValue) target, scope, scope.identity);
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
        
        
        IExpression select = selector == null
                ? null
                : Extensions.INSTANCE.compileExpression(selector.getCode(),
                        scope.runtimeScope.getExpressionContext(), selector.getLanguage());

        Object compareValue = null;
        IExpression compareExpression = null;

        if (comparison.getType() == Type.EXPRESSION) {
            IKimExpression expr = comparison.as(IKimExpression.class);
            compareExpression = Extensions.INSTANCE.compileExpression(expr.getCode(),
                    scope.runtimeScope.getExpressionContext(), expr.getLanguage());
        } else {
            compareValue = comparison.evaluate(scope, scope.identity, true);
        }

        if (target instanceof IState) {
            for (ILocator locator : scope.runtimeScope.getScale()) {
                if (select != null) {
                    
                }
                
                Object value = ((IState)target).get(locator);
                if (compareExpression == null) {
                    /*
                     * compare with comparison value
                     */
                } else {
                    /*
                     * eval expression
                     */
                }
            }
        }

        if (comparison == null) {
            ok = target == null;
        } else {
            ok = Actors.INSTANCE.matches(comparison, target, scope);
        }

        if (scope.testScope == null) {
            throw new KlabActorException(
                    "assert failed: '" + comparison + "' and '" + target + "' differ");
        }
        scope.testScope.notifyAssertion(target, comparison, ok, assertion);
    }

    public static String printPeriod(long ms) {
        Period period = new Period(ms);
        return periodFormat.print(period);
    }

    @Action(id = "whitelist", fires = {})
    public static class Constrain extends KlabActionExecutor {

        public Constrain(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(Scope scope) {

            List<Object> args = new ArrayList<>();
            for (Object o : arguments.getUnnamedArguments()) {
                if (o instanceof KActorsValue) {
                    o = ((KActorsValue) o).evaluate(scope, identity, true);
                }
                args.add(o);
            }

            ((SessionState) scope.runtimeScope.getSession().getState()).whitelist((Object[]) args.toArray());

        }

    }

    @Action(id = "blacklist", fires = {})
    public static class Exclude extends KlabActionExecutor {

        public Exclude(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(Scope scope) {
            List<Object> args = new ArrayList<>();
            for (Object o : arguments.getUnnamedArguments()) {
                if (o instanceof KActorsValue) {
                    o = ((KActorsValue) o).evaluate(scope, identity, true);
                }
                args.add(o);
            }

            ((SessionState) scope.runtimeScope.getSession().getState()).whitelist((Object[]) args.toArray());
        }

    }

    @Action(id = "inspect", fires = {Type.ANYVALUE}, synchronize = false)
    public static class Inspect extends KlabActionExecutor {

        public Inspect(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, Scope scope,
                ActorRef<KlabMessage> sender, String callId) {
            super(identity, arguments, scope, sender, callId);
        }

        @Override
        void run(Scope scope) {

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
                    }
                }
            }

            if (scope.runtimeScope.getSession().getState().getInspector() == null) {
                ((SessionState) scope.runtimeScope.getSession().getState()).setInspector(new Inspector());
            }

            ((SessionState) scope.runtimeScope.getSession().getState()).getInspector()
                    .setTrigger((trigger, sc) -> {
                        // TODO set variables in scope for expressions and assertions
                        fire(trigger.getSubject(), scope);
                    }, (Object[]) triggerArguments.toArray());
        }

    }

}
