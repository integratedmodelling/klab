package org.integratedmodelling.klab.api.engine;

import java.net.URL;

import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.runtime.IContext;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabException;

public interface IEngine extends IEngineIdentity, IServer {

    /**
     * 
     * @return
     */
    ICapabilities getCapabilities();

    /**
     * 
     * @param user
     * @return
     */
    ISession createSession(IEngineUserIdentity user);

    /**
     * Return the user that runs the engine. Other users may be authenticated by the engine in any way it
     * wishes, with the same privileges or less.
     * 
     * @return
     */
    IEngineUserIdentity getUser();

    /**
     * Engines have the ability to run a single-file, self-contained 'script' that can either be a test
     * namespace (containing a single observation with local resolvers and annotated with test annotations) or
     * a Groovy script with batch commands for the engine. The result is always the last context computed.
     * 
     * KDL dataflows should also be runnable through this one, although they need a context to be set before
     * (API to be finalized later).
     * 
     * A specialized test engine will be provided that will automatically compare the context with constraints
     * set through annotations.
     * 
     * FIXME this should (also?) return the IScriptIdentity, which is a Future<IContext> so the user does what
     * they want with it, passing it to an executor. We may want both calls, to allow the simplest usage and
     * the flexible one.
     * 
     * @param script
     *            a URL pointing to a self-contained script. Must have no imports.
     * @return the last context computed, possibly null.
     * @throws KlabException
     */
    IContext run(URL script) throws KlabException;

}
