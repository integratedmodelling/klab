package org.integratedmodelling.klab;

/**
 * All the API endpoints supported in the k.LAB engine.
 * 
 * @author ferdinando.villa
 *
 */
public interface API_OLD {

	
	
	
    /**
     * construct the JSON-requesting endpoint to get the passed resource URN as the passed
     * service
     * 
     * @param service
     * @param urn
     * @return the /get/... URL fragment
     */
    public static String getResource(String service, String urn) {
        return API_OLD.GET_RESOURCE.replace("{service}", service).replace("{urn}", urn)
                + ".json";
    }

    /*
     * services for all engines.
     */

    /**
     * 
     */
    public static final String CAPABILITIES              = "/capabilities";

    /*
     * These are called only on nodes.
     */

    /**
     * Called by modeling engines on the primary server, passing the user's certfile, to
     * retrieve the user profile and the network, as seen from the primary server and
     * filtered for authorizations.
     */
    public static final String CONNECT                   = "/connect";

    /**
     * Called in modeling engines to obtain the current status of the k.LAB network
     * potentially filtered according to user. Requires session authorization.
     */
    public static final String GET_NETWORK               = "/get-network";

    /**
     * Called in modeling engines to set the network status. Requires session
     * authorization on local engine only. Status parameter can be "online" or "offline".
     */
    public static final String SET_NETWORK_STATUS        = "/set-network-status/{status}";

    /**
     * This is used in nodes, passing around a user's groups and connecting IP, so that
     * they can respond with what they can offer to such a user and provide an access
     * token for resources. The call is recursive so that the full network can be
     * returned.
     */
    public static final String IDENTIFY                  = "/identify";

    /**
     * Called periodically on nodes to get a quick status update and maintain an updated
     * network topology.
     */
    public static final String STATUS                    = "/status";

    /**
     * Retrieve the metadata to reconstruct a named observation.
     */
    public static final String RETRIEVE_OBSERVATION      = "/retrieve-observation/{id}";

    /*
     * services for personal engines only (any engine as long as locked for personal use)
     */

    /**
     * Observe a primary direct observer. Requires session authentication.
     */
    public static final String OBSERVE                   = "/engine/observe";

    /*
     * get something about a specific observation in a context; return what the type
     * extension in the URL requires. The what keyword should be one of value, values,
     * histogram, boundaries, image, graph; the extension can be json, html (eventually),
     * xml (eventually, for xgraph and such) and image extensions such as png. Encoded
     * parameters can specify viewports and other option. These need to be unsecured as
     * they get called inside vendor APIs and it's very difficult to pass headers, so they
     * contain both the context id and the observation path within it.
     */

    /**
     * 
     */
    public static final String CONTEXT_GET_REPORT        = "/engine/context/get/report/{context}";

    /**
     * 
     */
    public static final String CONTEXT_GET_SUMMARY       = "/engine/context/get/summary/{context}/{observation}";

    /**
     * 
     */
    public static final String CONTEXT_GET_PROVENANCE    = "/engine/context/get/provenance/{context}";

    /**
     * 
     */
    public static final String CONTEXT_GET_DATA          = "/engine/context/get/data/{context}/{observation}";

    /**
     * FIXME Should be a DELETE endpoint and use task authentication.
     */
    public static final String CONTEXT_ABORT_TASK        = "/engine/context/abort/{context}/{task}";

    /**
     * Export request producing files on local filesystem. POST with context
     * authentication.
     */
    public static final String CONTEXT_EXPORT_DATA_LOCAL = "/engine/context/export-local";

    /**
     * Store an observation in the local kbox and return the permanent URN for it. GET
     * with context authentication.
     */
    public static final String CONTEXT_STORE_OBSERVATION = "/engine/context/store/{observation}";

    /**
     * Load an observation from a URN (local or remote) into the current context. GET with
     * session authentication.
     */
    public static final String CONTEXT_LOAD_OBSERVATION  = "/engine/context/load/{urn}";

    /**
     * Edit request producing actions (such as the opening of an editor). Limited to local
     * engine.
     */
    public static final String CONTEXT_EDIT_LOCAL        = "/engine/context/edit-local";

    /**
     * 
     */
    public static final String CONTEXT_GET_MEDIA         = "/engine/context/get/media/{context}/{observation}";

    /**
     * 
     */
    public static final String CONTEXT_GET_VALUE         = "/engine/context/get/value/{context}/{observation}";

    /**
     * get all timeseries values for a state
     */
    public static final String CONTEXT_GET_VALUES        = "/engine/context/get/values/{context}/{observation}";

    /**
     * Observe an observable within an established context. Requires context
     * authentication.
     */
    public static final String OBSERVE_IN_CONTEXT        = "/engine/observe-in";

    /**
     * Create a session. Requires user authentication. POST a SessionRequest bean.
     */
    public static final String OPEN_SESSION              = "/engine/open-session";

    /**
     * Run temporal transitions on the authenticating context.
     */
    public static final String OBSERVE_RUN               = "/engine/observe-run";

    /**
     * Obtain a permanent lock on the engine before maintenance or use for model
     * development. In normal operations, users with lock authority will automatically
     * obtain and release locks around maintenance operations.
     */
    public static final String LOCK_ENGINE               = "/engine/lock";

    /**
     * Release the lock we had previously obtained.
     */
    public static final String UNLOCK_ENGINE             = "/engine/unlock";

    /**
     * Run a Groovy script, optionally within an observation's context. Requires session
     * authentication.
     */
    public static final String RUN_SCRIPT                = "/engine/run-script";

    /**
     * Shutdown engine. Requires admin privileges, admin key or same-IP rule.
     */
    public static final String SHUTDOWN                  = "/admin/shutdown";

    /**
     * Update any projects remotely synchronized through Git URIs. Requires admin
     * privileges, admin key or same-host.
     */
    public static final String UPDATE                    = "/admin/update";

    /*
     * ------------------------------------------------------------------- project
     * management, used on both modeling engines and nodes, with different authorization.
     * These require exclusive lock on engines and admin permissions on nodes.
     * -------------------------------------------------------------------
     */

    /**
     * Updating a namespace's contents can also create a new one, so we need to pass the
     * project.
     */
    public static final String UPDATE_NAMESPACE          = "/project/update/{project}/{id}";

    /**
     * Delete namespace. Requires lock on engine, admin privileges, admin key or same-IP
     * rule on node.
     */
    public static final String DELETE_NAMESPACE          = "/project/delete/{id}";

    /**
     * Clear the workspace at the remote end. Requires lock on engine, admin privileges,
     * admin key or same-IP rule on node.
     */
    public static final String CLEAR_WORKSPACE           = "/project/clear-workspace";

    /**
     * Requires lock on engine, admin privileges, admin key or same-IP rule on node.
     */
    public static final String RELOAD                    = "/project/reload";

    /**
     * Requires lock on engine, admin privileges, admin key or same-IP rule on node.
     */
    public static final String DEPLOY                    = "/project/deploy/{id}";

    /**
     * Requires admin privileges, admin key or same-IP rule.
     */
    public static final String UNDEPLOY                  = "/project/undeploy/{id}";

    /**
     * Deploy project from a passed local directory. Used for refresh on nodes and for
     * local filesystem deployment on same-host engines. Only for same-IP or admin key.
     */
    public static final String DEPLOY_LOCAL              = "/project/deploy-local";

    /**
     * List all projects we can access and return URNs for their retrieval.
     */
    public static final String LIST_PROJECTS             = "/project/list";

    /**
     * Endpoints that perform differently scoped actions in engines and nodes
     */
    public static final String QUERY_MODELS              = "/query-models";

    /**
     * 
     */
    public static final String QUERY_OBSERVATIONS        = "/query-observations";

    /**
     * 
     */
    public static final String IMPORT_OBSERVATIONS       = "/import-observations";

    /**
     * 
     */
    public static final String SUBMIT_OBSERVATION        = "/submit-observation";

    /**
     * 
     */
    public static final String RETRIEVE_MODEL            = "/retrieve-model/{id}";

    /*
     * --------------------------------------------------------------------- endpoints for
     * authority management
     * ---------------------------------------------------------------------
     */

    /**
     * Resolve authority concept. This is a GET public endpoint - GET is strategically
     * chosen to allow simple demonstrations, although the string can be messy to encode.
     */
    public static final String RESOLVE_AUTHORITY         = "/authority/resolve/{authority}/{id}";

    /**
     * Query authority for query string. This is a GET public endpoint - GET is
     * strategically chosen to allow simple demonstrations, although the string can be
     * messy to encode.
     */
    public static final String QUERY_AUTHORITY           = "/authority/query/{authority}/{query}";

    /*
     * --------------------------------------------------------------------- endpoints for
     * collaboration codebase. FIXME these duplicate the Endpoints interface in
     * collaboration, which is not on the classpath. Should split node endpoints from
     * engine and have collaboration see the node endpoints, adding user management etc.
     * ---------------------------------------------------------------------
     */

    /**
     * 
     */
    public static final String AUTH_GET_ANNOUNCEMENTS    = "/engine/announcements";
    /**
     * 
     */
    public static final String AUTH_GET_ANNOUNCEMENT     = "/engine/announcement";

    /**
     * Services for nodes - those shared with the current collaboration server define the
     * eventual merge. It's important that these names change in sync - after merge, the
     * collaboration server should inherit its Endpoints interface from this.
     */
    public static final String AUTHENTICATION            = "/authentication";

    /**
     * 
     */
    public static final String AUTHENTICATION_CERT_FILE  = "/authentication/cert-file";

    /**
     * Gives access to all resources mediated by a k.LAB server. The service part
     * identifiers the type of service being accessed. A URN identifies the actual
     * resources. According to the service, GET arguments may be added. If the service
     * proxies to another REST service (such as wfs/wcs), the result will be the output of
     * the actual service after URN resolution, with the same arguments as in the original
     * request. Engines by default implement only the 'file', 'directory', 'project' and
     * 'component' services. Other services can be added using the ResourceServiceHandler
     * annotation on an object implementing IResourceServiceHandler.
     */
    public static final String GET_RESOURCE              = "/get/{service}/{urn}";

    public static final String GET_RESOURCE_INFO         = "/get-resource-info/{urn}";

    /*
     * ---------------------------------------------------------------------- STOMP
     * endpoints for websockets communication
     * ----------------------------------------------------------------------
     */

    /**
     * The REST (PUT) endpoint to send commands to the web-based viewer managed by the
     * engine.
     */
    public static final String VIEW                      = "/view";

    /**
     * The STOMP endpoint for communication between the engine and the Javascript in the
     * web-based viewer. Not a REST endpoint.
     */
    public static final String VIEWER_ENDPOINT           = "/viewer";

    /**
     * Authentication header. Will be matched to roles in secured endpoints.
     */
    public static final String AUTHENTICATION_HEADER     = "Authentication";

    /**
     * Version header for requests, used for validation of client calls and to allow
     * experimental features in production nodes.
     */
    public static final String KLAB_VERSION_HEADER       = "KlabVersion";

    /*
     * --- predefined URNs that are hard-wired to the engines.
     */

    /**
     * URN that gets the directory of core knowledge (use as
     * /get/directory/klab:im:core.knowledge)
     */
    public static final String CORE_KNOWLEDGE_URN        = "klab:im:core:knowledge";

    /**
     * URN that gets the public keyring from the distribution (use as
     * /get/file/klab:im:security.pubkey)
     */
    public static final String CORE_PUBKEY_URN           = "klab:im:security:pubkey";
}
