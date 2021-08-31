package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IProject;

import akka.actor.typed.ActorRef;

@Behavior(id = "test", version = Version.CURRENT)
public class TestBehavior {

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
        IProject project = getProject(arg);
        if (project != null) {
            if (project != null) {
                for (IBehavior testcase : project.getUnitTests()) {
                    scope.identity.load(testcase, scope.runtimeScope);
                }
            }
        } else {
            scope.getMonitor().error("Cannot open project " + arg);
        }
    }

    private static IProject getProject(Object arg) {
        IProject ret = null;
        if (arg instanceof String) {
            if (arg.toString().startsWith("http") || arg.toString().startsWith("git:")) {
                ret = Resources.INSTANCE.retrieveAndLoadProject(arg.toString());
            } else if (Resources.INSTANCE.getProject(arg.toString()) != null) {
                ret = Resources.INSTANCE.getProject(arg.toString());
            }
        } else if (arg instanceof IProject) {
            ret = (IProject) arg;
        }
        return ret;
    }

}
