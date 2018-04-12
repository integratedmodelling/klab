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
package org.integratedmodelling.klab.api.auth;

/**
 * Any identity known to the IM semantic web. Since 0.10.0 identities are arranged in a parent/child
 * hierarchy through exposing their parent token, which is only null in top-level identities, i.e.
 * IServer. Identity objects are passed to the API in lieu of their raw tokens, to give quick access
 * to the identity's metadata and their lineage.
 * 
 * Identities also correspond to roles for Spring security in the kmodeler and klab-community
 * projects.
 * 
 * Identities for now have the following parent/child relationships:
 * 
 * <pre>
 * 	IIdentity (abstract)
 * 		IPartner [top-level: each server is owned by a partner]
 * 			INode [physically a server on the k.LAB network; access point for IUser] 
 * 				IUser (authenticated by IServer, directly or indirectly)
 * 					INetworkSession
 *              		IEngine (has a IUser (automatically promoted to IEngineUser) but can authenticate others as IEngineUser)
 *              			IEngineUser
 * 								ISession
 * 				     				IObservation
 * 									    ITask
 *                                  IScript
 * </pre>
 * 
 * @author Ferd
 *
 */
public abstract interface IIdentity {

  static enum Type {
    /**
     * Identified by an institutional certificate. Each server has a top-level partner.
     */
    IM_PARTNER,

    /**
     * Identified by a node token, owned by a partner.
     */
    NODE,

    /**
     * Identified by a user token authenticated by a server.
     */
    IM_USER,

    /**
     * Identified by a network session token owned by a server user.
     */
    NETWORK_SESSION,

    /**
     * Identified by an engine token using a user certificate
     */
    ENGINE,

    /**
     * Identified by an engine user token released by an engine after authentication. Default engine
     * user is the server user who owns the engine.
     */
    ENGINE_USER,

    /**
     * Identified by a session token owned by an engine user.
     */
    MODEL_SESSION,

    /**
     * Identified by an observation token owned by a session.
     */
    OBSERVATION,

    /**
     * Identifed by a task token owned by a context observation.
     */
    TASK,

    /**
     * A script identity identifies a script (namespace with run/test/observe annotations or
     * imperative code) running as a task within a session.
     */
    SCRIPT
  }

  Type TYPE = null;

  /**
   * Authorization token retrieved upon authentication. Assumed to expire at some sensible point in
   * time, if stored it should be validated before use and refreshed if necessary.
   * 
   * @return a token to use as authentication when dealing with the engine.
   */
  String getToken();

  /**
   * Return the parent identity. Null only in IM_PARTNER identities.
   * 
   * @return
   */
  IIdentity getParentIdentity();

  /**
   * True if the identity is of the passed type.
   * 
   * @param type
   * @return
   */
  boolean is(Type type);

  /**
   * Get the parent identity of the passed type.
   * 
   * @param type
   * 
   * @return the desired identity or null.
   */
  <T extends IIdentity> T getParentIdentity(Class<T> type);

  /*
   *  Provided to simplify implementing the getParentIdentity method.
   */
  @SuppressWarnings("unchecked")
  static <T extends IIdentity> T findParent(IIdentity child, Class<T> type) {
    return child == null ? null
        : (type.isAssignableFrom(child.getClass()) ? (T) child
            : findParent(child.getParentIdentity(), type));
  }
}
