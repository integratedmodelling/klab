/**
 * 
 */
package org.integratedmodelling.klab.engine.test;

import java.util.function.Consumer;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.api.engine.ISessionScope;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.engine.services.engine.EngineService;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Runners for all the k.IM test cases in /kim.
 * 
 * @author ferdinando.villa
 *
 */
public class ObservationTests {

    static EngineService engine;

    @BeforeClass
    public static void setUp() throws Exception {
        engine = EngineService.start();
        // ensure errors cause exc
        Logging.INSTANCE.setErrorWriter(new Consumer<String>(){

            @Override
            public void accept(String t) {
                throw new KlabException(t);
            }
        });
    }

    @AfterClass
    public static void tearDown() throws Exception {
        if (engine != null) {
            engine.stop();
        }
    }

    @Test
    public void basicObservationWorkflow() throws Exception {

        /*
         * get an authenticated user from the default certificate
         */
        IUserIdentity user = Authentication.INSTANCE.authenticate(KlabCertificate.createDefault());

        /*
         * obtain a root scope for this user in the engine. This creates the user actor and
         * potentially runs any associated k.Actors code.
         */
        IScope scope = engine.login(user);

        // run an application, script or raw session. If we run a script, we only need to wait until
        // the script is done.
        ISessionScope sessionScope = scope.runSession(
                "testsession" /*
                               * TODO pass the kind of instrumentation we want (raw, API, Explorer)
                               */);

        /*
         * Scope is now user-wide. We need a context to start geometry automatically maintained. If
         * a specific application or script is requested, use run() to obtain a different scope.
         */
        IContextScope context = sessionScope.createContext("cagada");

        System.out.println(sessionScope);

        // IObservation france = context.observe();
    }
}
