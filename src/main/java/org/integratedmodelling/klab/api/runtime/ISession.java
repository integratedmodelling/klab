package org.integratedmodelling.klab.api.runtime;

import java.io.Closeable;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.auth.IEngineSessionIdentity;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * <p>
 * Any observation made in k.LAB must be done within a valid user session. Sessions are obtained from a
 * running {@link IEngine} using {@link IEngine#createSession()} or
 * {@link IEngine#createSession(IEngineUserIdentity)}.
 * </p>
 * 
 * <p>
 * Sessions must be properly closed when not needed anymore. A ISession is a {@link Closeable}, so a typical usage is
 * </p>
 * 
 * <pre>
 * try (ISession session = engine.createSession()) {
 *     // do things
 * } catch (KlabException e) {
 *     // complain
 * }
 * </pre>
 * 
 * <p>
 * A session is also an {@link IIdentity}, and its token must authenticate those engine API calls that are
 * session-aware.
 * </p>
 * 
 * @author ferdinando.villa
 *
 */
public interface ISession extends IEngineSessionIdentity, Closeable {

    /**
     * The observation action called on ISession always creates a new root subject. The URN must specify a
     * {@link IObserver}.
     * 
     * @param urn
     *            specifying a (local or remote) observer
     * @return a Future that is observing the URN.
     * @throws KlabException
     */
    Future<ISubject> observe(String urn) throws KlabException;

}
