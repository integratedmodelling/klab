package org.integratedmodelling.klab;

/**
 * An attempt to systematize the API - tentative and possibly overkill. This is only the API
 * required to run models through a certificate: probably the API for interacting with a remote node
 * (using regular authentication), manage profiles and retrieve certificates could be separate, as
 * it shares no functional connection with k.LAB. This API is common to nodes and engines, although
 * nodes currently can be expected to get less requests on ENGINE endpoints and be the only one in
 * charge of answering RESOURCE requests.
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
 * TODO define and enforce conventions to add the allowed protocols, encodings, authentication type
 * and "see also" links to the beans that handle requests and responses.
 * <p>
 * 
 * @author ferdinando.villa
 * @author J. Luke Scott
 */
public interface API {

  /**
   * Parameter: the URN being resolved in any endpoints that access resources
   */
  public static final String P_URN = "{urn}";


  /**
   * Public capabilities endpoint. Anything that has an API has capabilities.
   * 
   */
  public static final String CAPABILITIES = "/capabilities";
  
  /**
   * Authority endpoints are public.
   * 
   * @author ferdinando.villa
   *
   */
  public interface AUTHORITY {
    /**
     * 
     */
    public static final String RESOLVE = "/engine/authority/resolve";

    /**
     * 
     */
    public static final String QUERY   = "/engine/authority/query";
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

    public static final String DISCONNECT   = "/network/disconnect";

    /**
     * Query endpoints, using network session authentication.
     * 
     * @author ferdinando.villa
     *
     */
    public interface QUERY {

      /**
       * 
       */
      public static final String MODELS       = "/network/query/models";

      /**
       * 
       */
      public static final String OBSERVATIONS = "/network/query/observations";

    }

    /**
     * Retrieve resources.
     * 
     * @author ferdinando.villa
     *
     */
    public interface RETRIEVE {

      /**
       * 
       */
      public static final String MODEL_URN       = "/network/retrieve/model/" + P_URN;

      /**
       * 
       */
      public static final String OBSERVATION_URN = "/network/retrieve/observation/" + P_URN;

      /**
       * 
       */
      public static final String COMPONENT_URN   = "/network/retrieve/component/" + P_URN;

    }

  }

  public interface RESOURCE {

    /**
     * Retrieve raw observation data for passed URN in passed scale. If resource has time geometry,
     * the response at initialization contains an individual token for repeated requests at
     * transitions.
     */
    public static final String GET_URN     = "/resource/get/" + P_URN;

    /**
     * Get URN data for passed URN. Includes expiration to control cacheing.
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

    /*
     * TODO flesh out - shutdown, reset/init, deploy/setup components, undeploy, import, submit, update/delete
     * namespaces, workspace management, lock/unlock. PUT endpoints for configuration. To be tied to
     * future configuration dashboard. Probably should have additional authentication.
     */
    public interface ADMIN {

    }

    public interface SESSION {

      /**
       * 
       */
      public static final String AUTHORIZE = "/engine/session/authorize";

      /**
       * 
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

      public static final String P_CONTEXT           = "{context}";

      /**
       * Create new context from the URN of its definition or remote computation. Return task descriptor.
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
