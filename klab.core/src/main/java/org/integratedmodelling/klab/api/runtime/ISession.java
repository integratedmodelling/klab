/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
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
 * Any observation made in k.LAB must be done within a valid user session. Sessions are obtained
 * from a running {@link IEngine} using {@link IEngine#createSession()} or
 * {@link IEngine#createSession(IEngineUserIdentity)}.
 * </p>
 * 
 * <p>
 * Sessions must be properly closed when not needed anymore. A ISession is a {@link Closeable}, so a
 * typical usage is
 * </p>
 * 
 * <pre>
 * try (ISession session = engine.createSession()) {
 *   // do things
 * } catch (KlabException e) {
 *   // complain
 * }
 * </pre>
 * 
 * <p>
 * A session is also an {@link IIdentity}, and its token must authenticate those engine API calls
 * that are session-aware.
 * </p>
 * 
 * @author ferdinando.villa
 *
 */
public interface ISession extends IEngineSessionIdentity, Closeable {

  /**
   * The observation action called on ISession always creates a new root subject. The URN must
   * specify a {@link IObserver}.
   * 
   * @param urn specifying a (local or remote) observer
   * @param scenarios names of any scenario namespaces to use in resolution
   * @return a Future that is observing the URN.
   * @throws KlabException
   */
  Future<ISubject> observe(String urn, String... scenarios) throws KlabException;

}
