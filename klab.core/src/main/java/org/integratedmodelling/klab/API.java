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
package org.integratedmodelling.klab;

// TODO: Auto-generated Javadoc
/**
 * This interface and its members describe the REST API of k.LAB. The API enables managing semantic
 * and non-semantic assets and building artifacts and observation dataflows, with certificate-based
 * autentication. The API for interacting with a remote node using regular (login) authentication,
 * manage profiles and certificates, and handle communication and cross-authentication among nodes
 * is separate, as it shares no functional connection with the k.LAB project. The only connection
 * point between the two APIs is the {@link NETWORK#AUTHENTICATE} endpoint, which is used by clients
 * to obtain their network credentials using the certificate file contents.
 * <p>
 * This API is common to nodes and engines, although nodes currently can be expected to get less
 * requests on ENGINE endpoints and be the only one in charge of answering RESOURCE requests.
 * <p>
 * Endpoints are organized in groups implemented in sub-interfaces. Any path with parameters has the
 * name of each parameter in its constant name, separated by an underscore. For each parameter
 * <code>xxx</code>, the same sub-interface must contain a corresponding <code>P_xxx</code>
 * constant, and the endpoint string must be built using it. Otherwise, constant names and endpoint
 * strings are identical and paths reflect the interface structure.
 * <p>
 * Each sub-interface should correspond to a controller with methods that match the endpoint names,
 * using the same authentication. If a group has an AUTHENTICATE endpoint, that will be implemented
 * in the controller corresponding to the containing interface, and return the token needed to
 * authorize the subgroups so that the same authorization can be applied to the controller.
 * <p>
 * The endpoint corresponding to a sub-interface should bring up an appropriate UI when accessed
 * with HTML response type. This applies at least to the VIEW endpoints (k.EXPLORER) and to the
 * ADMIN endpoints (administration dashboard). The root context path should redirect to a login page
 * if connection is from a remote host and to the admin dashboard if connected to from localhost.
 * <p>
 *
 * @author ferdinando.villa
 * @author J. Luke Scott
 * @version $Id: $Id
 */
public interface API {

  // Doc template for each endpoint - uncomment, cut, paste, comment again, edit
  // *<h4>Protocol</h4>
  // *<ul>
  // *<li><b>GET</b> description
  // *</ul>
  // *<h4>Response type</h4>
  // *<ul>
  // *<li><b>html</b>
  // *</ul>
  // *<p><b>Request</b> (link to bean)
  // *<p><b>Response</b> (link to bean)
  // *<p><b>Authentication</b> (description or link to identity)

  /** Parameter: the URN being resolved in any endpoints that access resources. */
  public static final String P_URN        = "{urn}";


  /**
   * Public capabilities endpoint. Anything that has an API has capabilities.
   * 
   */
  public static final String CAPABILITIES = "/capabilities";

  /**
   * Authority endpoints are public.
   *
   * @author ferdinando.villa HTML authority dashboard
   */
  public interface AUTHORITY {

    /**
     * The Constant RESOLVE.
     *
     * GET JSON
     */
    public static final String RESOLVE = "/engine/authority/resolve";

    /**
     * The Constant QUERY.
     *
     * POST
     */
    public static final String QUERY   = "/engine/authority/query";
  }

  /**
   * TODO flesh out - shutdown, reset/init, deploy/setup components, undeploy, import, submit,
   * update/delete namespaces, workspace management, lock/unlock. PUT endpoints for configuration.
   * To be tied to future configuration dashboard.
   * <p>
   * Authentication should be ROLE_ADMIN and be preauthorized for the local IP.
   * <p>
   * With HTML encoding, this should produce the administration dashboard.
   * <p>
   */
  public interface ADMIN {

    /**
     * Shutdown the server.
     * 
     * GET
     */
    public static final String SHUTDOWN = "/engine/admin/shutdown";

    /**
     * Endpoints to remotely deploy/undeploy projects and components.
     * 
     * @author ferdinando.villa
     *
     */
    public interface COMPONENT {

      /** Parameter: project/component ID. */
      public static final String P_COMPONENT        = "{component}";

      /**
       * A component can be deployed as a file attachment or from a Git repository.
       * 
       * PUT as attachment POST as Git URL
       */
      public static final String DEPLOY             = "/engine/admin/component/deploy";

      /**
       * The Constant UNDEPLOY_COMPONENT.
       *
       * DELETE
       */
      public static final String UNDEPLOY_COMPONENT =
          "/engine/admin/component/undeploy/" + P_COMPONENT;

      /**
       * The Constant SETUP_COMPONENT.
       *
       * POST
       */
      public static final String SETUP_COMPONENT    =
          "/engine/admin/component/setup/" + P_COMPONENT;
    }

  }

  /**
   * Network session endpoints.
   * 
   * @author ferdinando.villa
   *
   */
  public interface NETWORK {

    /**
     * Returns network status with all nodes (including offline if applicable) with refresh rate and
     * unique network access token. Should be the only authentication call necessary in this API.
     */
    public static final String AUTHENTICATE = "/network/authenticate";

    /** The Constant DISCONNECT. */
    public static final String DISCONNECT   = "/network/disconnect";

    /**
     * Query endpoints, using network session authentication.
     * 
     * @author ferdinando.villa
     *
     */
    public interface QUERY {

      /** The Constant MODELS. */
      public static final String MODELS       = "/network/query/models";

      /** The Constant OBSERVATIONS. */
      public static final String OBSERVATIONS = "/network/query/observations";

    }

    /**
     * Retrieve semantic assets - models, observations and components/projects.
     * 
     * @author ferdinando.villa
     *
     */
    public interface RETRIEVE {

      /**
       * The Constant MODEL_URN.
       *
       * GET
       */
      public static final String MODEL_URN       = "/network/retrieve/model/" + P_URN;

      /**
       * The Constant OBSERVATION_URN.
       *
       * GET
       */
      public static final String OBSERVATION_URN = "/network/retrieve/observation/" + P_URN;

      /**
       * The Constant COMPONENT_URN.
       *
       * GET
       */
      public static final String COMPONENT_URN   = "/network/retrieve/component/" + P_URN;

    }

  }

  /**
   * Handle non-semantic assets - data, data services and remote computations.
   * 
   * @author ferdinando.villa
   *
   */
  public interface RESOURCE {

    /**
     * Add a resource to the local catalog passing a local file URL and/or resource properties.
     * Return URN after validation.
     * 
     * ProtocolsPUT
     */
    public static final String ADD         = "/resource/add";

    /**
     * Publish a local resource to the public catalog of this or another server.
     * 
     * POST
     */
    public static final String PUBLISH_URN = "/resource/publish/" + P_URN;

    /**
     * Modify resource data. Triggers revalidation.
     * 
     * PATCH
     */
    public static final String UPDATE_URN  = "/resource/update/" + P_URN;

    /**
     * Delete resource data.
     * 
     * DELETE
     */
    public static final String DELETE_URN  = "/resource/delete/" + P_URN;

    /**
     * Retrieve raw observation data for passed URN in passed scale. If resource has time geometry,
     * the response at initialization contains an individual token for repeated requests at
     * transitions.
     * 
     * GET
     */
    public static final String GET_URN     = "/resource/get/" + P_URN;

    /**
     * Get URN data for passed URN. Includes expiration to control cacheing.
     * 
     * GET
     */
    public static final String RESOLVE_URN = "/resource/resolve/" + P_URN;
  }

  /**
   * Endpoints to open sessions and control observation tasks in them.
   * 
   * @author ferdinando.villa
   *
   */
  public interface ENGINE {

    /**
     * Authorize an engine user. This may use the standard IM authentication (filtering privileges
     * through the engine owner's) or the engine may have its own user directory. Localhost
     * connections to a running engine are automatically authorized with the user that owns it,
     * without a need to going through authentication.
     */
    public static final String AUTHENTICATE = "/engine/authenticate";

    /**
     * The Interface SESSION.
     */
    public interface SESSION {

      /**
       * The Constant AUTHORIZE.
       *
       * POST
       */
      public static final String AUTHORIZE = "/engine/session/authorize";

      /**
       * The Constant CLOSE.
       *
       * DELETE
       */
      public static final String CLOSE     = "/engine/session/close";
    }

    /**
     * Endpoints to access contexts, using context tokens for authentication.
     * 
     * @author ferdinando.villa
     *
     */
    public interface CONTEXT {

      /** The Constant P_CONTEXT. */
      public static final String P_CONTEXT           = "{context}";

      /**
       * Create new context from the URN of its definition or remote computation. Return task
       * descriptor.
       */
      public static final String CREATE_URN          = "/engine/session/context/create/" + P_URN;

      /**
       * Observe URN in pre-authorized context. Return task ID.
       */
      public static final String OBSERVE_CONTEXT_URN =
          "/engine/session/context/observe/" + P_CONTEXT + "/" + P_URN;

      /**
       * Run temporal transitions in pre-authorized context. Return task descriptor.
       */
      public static final String RUN_CONTEXT         = "/engine/session/context/run/" + P_CONTEXT;


      /**
       * Endpoints to access tasks.
       * 
       * @author ferdinando.villa
       *
       */
      public interface TASK {

        /** The Constant P_TASK. */
        public static final String P_TASK = "{task}";

      }

      /**
       * Endpoints to retrieve data and visualizations from "live" observations in context.
       * 
       * @author ferdinando.villa
       *
       */
      public interface VIEW {

      }
    }


  }


}
