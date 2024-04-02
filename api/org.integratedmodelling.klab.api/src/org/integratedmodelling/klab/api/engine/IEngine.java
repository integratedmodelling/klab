/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.engine;

import java.net.URL;

import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The Interface IEngine.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 * @deprecated should be obsoleted by IEngineService and the new observation API
 */
public interface IEngine extends IEngineIdentity, IServer {

  /**
   * Create a new session for the user who owns the engine.
   *
   * @return a new session
   */
  ISession createSession();

  /**
   * Create a new session for the named user.
   *
   * @param user a {@link org.integratedmodelling.klab.api.auth.IEngineUserIdentity} object.
   * @return a new session
   */
  ISession createSession(IUserIdentity user);

  /**
   * Engines have the ability to run a single-file, self-contained 'script' that can either be a
   * test namespace (containing a single observation with local resolvers and annotated with test
   * annotations) or a Groovy script with batch commands for the engine. The result is always the
   * last context computed.
   *
   * KDL dataflows should also be runnable through this one, although they need a context to be set
   * before (API to be finalized later).
   *
   * A specialized test engine will be provided that will automatically compare the context with
   * constraints set through annotations.
   *
   * FIXME this should (also?) return the IScriptIdentity, which is a Future<IContext> so the user
   * does what they want with it, passing it to an executor. We may want both calls, to allow the
   * simplest usage and the flexible one.
   *
   * @param script a URL pointing to a self-contained script. Must have no imports.
   * @return the running script (a {@link java.util.concurrent.Future}).
   * @throws org.integratedmodelling.klab.exceptions.KlabException
   */
  IScript run(URL script) throws KlabException;

}
