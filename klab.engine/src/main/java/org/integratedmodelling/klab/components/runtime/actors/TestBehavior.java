package org.integratedmodelling.klab.components.runtime.actors;

import java.util.concurrent.atomic.AtomicBoolean;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.Scope;
import org.integratedmodelling.klab.engine.runtime.Session;
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
            .appendSeparator(":").appendSeconds().minimumPrintedDigits(2).appendSeparator(".").appendMillis3Digit().
            toFormatter();


    @Action(id = "test", fires = {}, description = "Run all the test included in one or more projects, naming the project ID, "
            + "a URL or a Git URL (git:// or http....*.git")
    public static class Test extends KlabActionExecutor {

        public Test(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope,
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
                final  AtomicBoolean done = new AtomicBoolean(false);
                for (IBehavior testcase : project.getUnitTests()) {
                    
                    if (scope.identity instanceof Session) {
                        ((Session)scope.identity).loadScript(testcase, scope.getChild(testcase), () -> done.set(true));
                    } else {
                        scope.identity.load(testcase, scope.runtimeScope);
                    }
                    
                    /*
                     * wait until test is done
                     */
                    while (!done.get()) {
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
    public static void evaluateAssertion(Object returned, Assertion assertion, Scope scope) {

        IKActorsValue comparison = assertion.getValue();
        boolean ok = false;
        if (returned instanceof IKActorsValue) {
            returned = KlabActor.evaluateInScope((KActorsValue) returned, scope, scope.identity);
        }
        if (comparison == null) {
            ok = returned == null;
        } else {
            ok = Actors.INSTANCE.matches(comparison, returned, scope);
        }

        if (scope.testScope == null) {
            throw new KlabActorException("assert failed: '" + comparison + "' and '" + returned + "' differ");
        }

        scope.testScope.notifyAssertion(returned, comparison, ok, assertion);

    }
    
    public static String printPeriod(long ms) {
        Period period = new Period(ms);
        return periodFormat.print(period);
    }

}
