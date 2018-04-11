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
 * TODO to be restructured
 * 
 * @author ferdinando.villa
 *
 */
public interface API {

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

    public static final String CONNECT    = "/network/connect";

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
  }

  public interface RESOURCE {

    /**
     * Parameter: the URN being resolved in resource endpoints
     */
    public static final String P_URN       = "{urn}";

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

    public static final String AUTHORIZE = "/engine/authorize";

    public interface SESSION {
      public static final String OPEN  = "/engine/session/open";
      public static final String CLOSE = "/engine/session/close";
    }

    /**
     * Endpoints to access contexts, using context tokens for authentication.
     * 
     * @author ferdinando.villa
     *
     */
    public interface CONTEXT {
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

  }

}
