package org.integratedmodelling.klab;

/**
 * An attempt to systematize the API - tentative and possibly overkill. This is only the API
 * required to run models through a certificate: probably the API for interacting with a remote node
 * (using regular authentication) and retrieve a certificate could be separate, as it shares no
 * functional connection with k.LAB.
 * <p>
 * Endpoints are organized in groups implemented in sub-interfaces. Any path with parameters has the
 * name of each parameter in its constant name, separated by an underscore. For each parameter
 * <code>xxx</code>, the same sub-interface must contain a corresponding <code>P_xxx</code>
 * constant, and the endpoint string must be built using it. Otherwise, constant names and endpoint
 * strings are identical and paths reflect the interface structure.
 * <p>
 * Each sub-interface should correspond to a controller with methods that match the endpoint names,
 * using the same authentication. If a group has an AUTHORIZE endpoint, that will be implemented in
 * the controller corresponding to the containing interface, and return the token needed to
 * authorize the subgroups.
 * <p>
 * TODO define and enforce conventions to add the allowed protocols, authentication type and "see
 * also" links to the beans that handle requests and responses.
 * <p>
 * 
 * @author ferdinando.villa
 *
 */
public interface API {

  /**
   * Parameter: the URN being resolved in resource endpoints
   */
  public static final String P_URN       = "{urn}";

  
  /**
   * Return the capabilities. Anything that has an API has capabilities.
   * 
   */
  public static final String CAPABILITIES = "/capabilities";

  /**
   * Network session endpoints.
   * 
   * @author ferdinando.villa
   *
   */
  public interface NETWORK {

    public static final String AUTHORIZE  = "/network/authorize";

    public static final String DISCONNECT = "/network/disconnect";

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

    public interface RETRIEVE {

      public static final String MODEL       = "/network/retrieve/model";

      public static final String OBSERVATION = "/network/retrieve/model";

    }

  }

  public interface RESOURCE {

    /**
     * 
     */
    public static final String GET_URN     = "/resource/get/" + P_URN;

    /**
     * 
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
    public static final String AUTHORIZE = "/engine/authorize";

    /*
     * TODO shutdown, reset/init, deploy/setup components, undeploy, import, submit, update/delete
     * namespaces, workspace management, lock/unlock.
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

      /**
       * Authorize a context established from a URN.
       */
      public static final String AUTHORIZE = "/engine/session/context/authorize/" + P_URN;

      /**
       * Observe URN in context. Return task token.
       */
      public static final String OBSERVE = "/engine/session/context/observe/" + P_URN;

      /**
       * Run temporal transitions according to scale, return task token.
       */
      public static final String RUN = "/engine/session/context/run";


      /**
       * Endpoints to retrieve data and visualizations from observations in context.
       * 
       * @author ferdinando.villa
       *
       */
      public interface VIEW {

      }
    }

    /**
     * Endpoints to access tasks, using task tokens for authentication.
     * 
     * @author ferdinando.villa
     *
     */
    public interface TASK {
    }

  }

  public interface AUTHORITY {
    public static final String RESOLVE = "/engine/authority/resolve";
    public static final String QUERY   = "/engine/authority/query";
  }

}
