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
package org.integratedmodelling.klab.api;

import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.rest.ContextRequest;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.ObservationRequest;
import org.integratedmodelling.klab.rest.PingResponse;
import org.integratedmodelling.klab.rest.TicketRequest;

/**
 * This interface and its members describe the REST API of k.LAB. The API enables managing semantic
 * and non-semantic assets and building artifacts and observation dataflows, with certificate-based
 * autentication. The API for interacting with a remote node using regular (login) authentication,
 * manage profiles and certificates, and handle communication and cross-authentication among nodes
 * is separate, as it shares no functional connection with the k.LAB project. The only connection
 * point between the two APIs is the {@link #AUTHENTICATE} endpoint, which is used by clients to
 * obtain their network credentials using the certificate file contents.
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
 * @author Steven Wohl
 */
public interface API {

    /**
     * Base for many, but still not all, endpoints. TODO must use everywhere.
     */
    public static final String API_BASE = "/api/v2";

    /**
     * Use to simply substitute parameters in URLs:
     * <code>API.url(API.RESOURCE.RESOLVE_URN, API.P_URN, urn)</code>
     * 
     * @param template
     * @param kvp
     * @return
     */
    public static String url(String template, String... kvp) {
        String ret = template;
        if (kvp != null) {
            for (int i = 0; i < kvp.length; i++) {
                ret = template.replace(kvp[i], kvp[++i]);
            }
        }
        return ret;
    }

    /** Parameter: the URN being resolved in any endpoints that access resources. */
    public static final String P_URN = "{urn}";

    /** Parameter: query for any GET call used to search **/
    public static final String P_QUERY = "{query}";

    /**
     * Parameter: a codelist name for GET requests.
     */
    public static final String P_CODELIST = "{codelist}";

    /**
     * Ping service. Accepts HEAD requests to simply check for heartbeat, or GET to return
     * meaningful info on the engine's status. If the engine is local (i.e. the request comes from a
     * local IP) and accepts local connections, also adds an engine session ID so that it can be
     * connected to.
     * 
     * <p>
     * <b>Protocol:</b> GET, HEAD <br/>
     * <b>Response type:</b> {@link PingResponse} <br/>
     * <b>Authentication:</b>
     */
    public static final String PING = "/ping";

    /**
     * STOMP endpoint for client/server notifications. Handled through Websockets protocol.
     * 
     * <br/>
     * <b>Response type:</b> {@link Message}
     */
    public static final String MESSAGE = "/message";

    /**
     * Public capabilities endpoint. Anything that has an API has capabilities.
     * 
     * <p>
     * <b>Protocol:</b> GET <br/>
     * <b>Response type:</b> Json <br/>
     * <b>Response:</b> {@code org.integratedmodelling.klab.rest.Capabilities} <br/>
     * <b>Authentication:</b> open or {@link INetworkSessionIdentity} (response reflect access
     * levels)
     * 
     */
    public static final String CAPABILITIES = "/capabilities";

    public static interface KIM {

        /**
         * k.IM descriptor endppoint. Returns info on the language supported, including the version,
         * build, list of keywords and other useful statistics.
         * 
         * <p>
         * <b>Protocol:</b> GET <br/>
         * <b>Response type:</b> Json <br/>
         * <b>Response:</b> {@code org.integratedmodelling.klab.rest.KimCapabilities} <br/>
         * <b>Authentication:</b> open
         */
        public static final String CAPABILITIES = "/kim/capabilities";

        /**
         * Substitute keywords and concepts into the posted template and return it as text.
         * 
         */
        public static final String TEMPLATE = "/kim/template";

    }

    public static interface KACTORS {

        /**
         * k.Actors descriptor endppoint. Returns info on the language supported, including the
         * version, build, list of keywords and other useful statistics.
         * 
         * <p>
         * <b>Protocol:</b> GET <br/>
         * <b>Response type:</b> Json <br/>
         * <b>Response:</b> {@code org.integratedmodelling.klab.rest.KimCapabilities} <br/>
         * <b>Authentication:</b> open
         */
        public static final String CAPABILITIES = "/kactors/capabilities";

        /**
         * Substitute keywords and concepts into the posted template and return it as text.
         * 
         */
        public static final String TEMPLATE = "/kactors/template";

    }

    /**
     * Read-only ticket API, implemented in the Node and maybe later in the Hub. Engines also have
     * ticket management but only local Websockets clients can access it.
     * 
     * @author Ferd
     *
     */
    public static interface TICKET {

        public static final String P_TICKET = "{ticket}";

        /**
         * Retrieve the specific ticket with the passed ID. If ticket==all, get a list of all
         * tickets.
         * 
         * GET
         */
        public static final String INFO = "/ticket/info/" + P_TICKET;

        /**
         * Retrieve all tickets matching the field values in the query string.
         * 
         * POST with {@link TicketRequest} query data
         */
        public static final String QUERY = "/ticket/query";

        /**
         * Retrieve all tickets as an array of JSON objects. Requires ADMIN role.
         */
        public static final String LIST = "/ticket/list";

    }

    /**
     * API for the monitor server, which gathers and serves statistical information from engines to
     * inform machine learning for the future resolver and help the semantic server in validating
     * data. It also records data to inform suggested observables for a user query.
     * 
     * @author Ferd
     *
     */
    public static interface MONITOR {

        /**
         * Submit anonymized successful query data including its context, time elapsed in resolution
         * and contextualization, groups, computation and load metrics for indexing and analysis.
         * 
         * PUT
         */
        public static final String SUBMIT = "/monitor/submit";

        /**
         * Request suggestions for queries in context matching a query string and user permissions,
         * sorted by match score and (increasing) computational load.
         * 
         * GET
         */
        public static final String SUGGESTIONS = "/monitor/suggest";

    }

    /**
     * API for the semantic server, which assists users in semantic annotation and semantic model
     * validation of model structure and model output.
     * 
     * @author Ferd
     *
     */
    public static interface KNOWLEDGE {

    }

    public static interface HUB {

        public static final String API_BASE = "/api/v2";
        /**
         * Base URL path for node on the hub.
         */
        public static final String NODE_BASE = API_BASE + "/nodes";
        /**
         * Base URL path for user resources on the hub.
         */
        public static final String USER_BASE = API_BASE + "/users";
        /**
         * Base URL path for user resources on the hub.
         */
        public static final String GROUPS_BASE = API_BASE + "/groups";
        /**
         * Base URL path for lever resources on the hub.
         */
        public static final String LEVER_BASE = API_BASE + "/lever";
        /**
         * Base URL path for engine resources on the hub.
         */
        public static final String ENGINE_BASE = API_BASE + "/engines";
        /**
         * Base URL path for tasks resources on the hub.
         */
        public static final String TASK_BASE = API_BASE + "/tasks";
        /**
         * Base URL path for email resources and services on the hub.
         */
        public static final String EMAIL_BASE = API_BASE + "/emails";
        /**
         * Base URL path for authenticating resources.
         */
        public static final String AUTH_BASE = "/auth-cert";

        /**
         * Returns authenticated user details and network status with all nodes (including offline
         * if applicable) with refresh rate and unique network access token.
         * 
         * <p>
         * <b>Protocol:</b> POST <br/>
         * <b>Response type:</b> Json <br/>
         * <b>Request:</b>
         * {@code org.integratedmodelling.klab.rest.resources.requests.AuthenticationRequest} <br/>
         * <b>Response:</b>
         * {@code org.integratedmodelling.klab.rest.resources.responses.AuthenticationResponse}
         * <br/>
         * <b>Authentication:</b> open
         */
        public static final String AUTHENTICATE_ENGINE = ENGINE_BASE + AUTH_BASE;

        public static final String LEGACY_AUTHENTICATE_ENGINE = "/api/auth-cert/engine";
        /**
         * Called by nodes on hubs when authenticating with them. Parameters like the engine
         * version.
         */
        public static final String AUTHENTICATE_NODE = NODE_BASE + AUTH_BASE;
        /**
         * Called by levers on hubs when authenticating with them. Parameters like the engine
         * version.
         */
        public static final String AUTHENTICATE_LEVER = LEVER_BASE + AUTH_BASE;
        /**
         * Called by users to log into the hub and recieve an authentication token.
         */
        public static final String AUTHENTICATE_USER = USER_BASE + "/log-in";
        /**
         * Called by users to log into the hub and recieve an authentication token.
         */
        public static final String DEAUTHENTICATE_USER = USER_BASE + "/log-out";
        /**
         * Base URL path for node resources on the hub.
         */
        public static final String NODE_BASE_ID = NODE_BASE + "/{id}";
        /**
         * Base URL path for user resources on the hub.
         */
        public static final String USER_BASE_ID = USER_BASE + "/{id}";
        /**
         * Base URL path for user resources on the hub.
         */
        public static final String GROUPS_BASE_ID = GROUPS_BASE + "/{id}";
        /**
         * Base URL path for lever resources on the hub.
         */
        public static final String LEVER_BASE_ID = LEVER_BASE + "/{id}";
        /**
         * Base URL path for task resource id on the hub.
         */
        public static final String TASK_BASE_ID = TASK_BASE + "/{id}";
        /**
         * Base URL path for email templated by id on the hub.
         */
        public static final String EMAIL_BASE_ID = EMAIL_BASE + "/{id}";
        /**
         * Base URL path application logs.
         */
        public static final String LOGS = API_BASE + "/system/logs";
        /**
         * Base URL path for deleted users.
         */
        public static final String DELETED_USERS = USER_BASE + "/deleted-users";
        /**
         * URL path for deleted users by id.
         */
        public static final String DELETED_USER_ID = DELETED_USERS + "/{id}";
        /**
         * URL path for current user profile, based on Authentication Token parsing.
         */
        public static final String CURRENT_PROFILE = USER_BASE + "/me";

        public static interface PARAMETERS {
            /**
             * URL PARAMETER for user activation tokens.
             */
            public static final String USER_ACTIVATION = "activate";

            /**
             * URL PARAMETER for user requesting a lost password email.
             */
            public static final String USER_LOST_PASSWORD = "lost-password";
            /**
             * URL PARAMETER for user setting a new password.
             */
            public static final String USER_REQUEST_PASSWORD = "new-password";
            /**
             * URL PARAMETER for user setting a password from set password token.
             */
            public static final String USER_SET_PASSWORD = "set-password";
            /**
             * URL PARAMETER for user to verify account.
             */
            public static final String USER_VERIFICATION = "verify";
            /**
             * URL PARAMETER for user to request a new certificate.
             */
            public static final String USER_CERTIFICATE = "certificate";
            /**
             * URL PARAMETER for requesting the names of groups.
             */
            public static final String GROUP_NAMES = "names";
            /**
             * URL PARAMETER for a user to request groups as a task.
             */
            public static final String USER_REQUEST_GROUPS = "request-groups";
            /**
             * URL PARAMETER for a user to remove groups as a task
             */
            public static final String USER_REMOVE_GROUPS = "remove-groups";
            /**
             * URL PARAMETER for a task to accepted or denied
             */
            public static final String ACCEPT = "accept";
            /**
             * URL PARAMETER for a create group task
             */
            public static final String CREATE_GROUP = "create-group";
            /**
             * URL PARAMETER for template in the email service
             */
            public static final String TEMPLATES = "templates";
            /**
             * URL PARAMETER for user group entry service, request a particular group by name
             */
            public static final String REQUEST_GROUPS = "request-groups";
            /**
             * URL PARAMETER for user group entry service, set a particular group by name
             */
            public static final String SET_GROUPS = "set-groups";
            /**
             * URL PARAMETER for user group entry service, remove a particular group by name
             */
            public static final String REMOVE_GROUPS = "remove-groups";
            /**
             * URL PARAMETER for user group entry service, find users with a particular group
             */
            public static final String HAS_GROUP = "has-group";
            /**
             * URL PARAMETER for user role entry service, sets a role for a given user
             */
            public static final String SET_ROLES = "set-roles";
            /**
             * URL PARAMETER for user role entry service, removes a role for a given user
             */
            public static final String REMOVE_ROLES = "remove-roles";
            /**
             * URL PARAMETER for user role entry service, lists all the users with a particular role
             */
            public static final String HAS_ROLES = "has-roles";
        }

    }

    public static interface NODE {

        /**
         * Returns info about self and (if admin) users served between dates, eventually with short
         * and verbose formats listing login data and URN access.
         */
        public static final String WHO = "who";

        /**
         * Protected admin endpoints for configuration and component setup.
         * 
         * @author Ferd
         *
         */
        public static interface ADMIN {

            public static final String P_COMPONENT = "{component}";
            public static final String P_PROPERTY = "{property}";
            public static final String P_LINES = "{lines}";

            /**
             * 
             */
            public static final String COMPONENT_SETUP = "/component/setup/" + P_COMPONENT;

            /**
             * 
             */
            public static final String COMPONENT_GET_STATUS = "/component/getstatus/" + P_COMPONENT;

            /**
             * 
             */
            public static final String SET_COMPONENT_PROPERTY = "/component/properties/set/" + P_COMPONENT
                    + "/"
                    + P_PROPERTY;

            /**
             * 
             */
            public static final String GET_COMPONENT_PROPERTY = "/component/properties/get/" + P_COMPONENT
                    + "/"
                    + P_PROPERTY;

            /**
             * 
             */
            public static final String SET_PROPERTY = "/properties/set/" + P_PROPERTY;

            /**
             * 
             */
            public static final String GET_PROPERTY = "/properties/get/" + P_PROPERTY;

            /**
             * 
             */
            public static final String GET_LOG = "/logs/get/" + P_LINES;

        }

        public static interface RESOURCE {

            /**
             * Return resources matching a query string. Admits the "maxResults", "bbox", "timespan"
             * and "verbose" arguments.
             * 
             * GET
             */
            public static final String SEARCH = "/resource/search/" + P_QUERY;

            /**
             * Add a resource to the public catalog by uploading zipped contents from a valid local
             * resource. Return URN after validation.
             * 
             * POST
             */
            public static final String SUBMIT_FILES = "/resource/submitfiles";

            /**
             * Like the above but used when the resource only contains a single resource.json
             * metadata file, whose contents are sent directly in a POST message.
             * 
             * POST
             */
            public static final String SUBMIT_DESCRIPTOR = "/resource/submitdescriptor";

            /**
             * Publish a local resource to the public catalog of this node. Return the final URN and
             * status (which may be 'ongoing', i.e. the resource may not be available yet).
             * 
             * POST
             */
            public static final String PUBLISH_URN = "/resource/publish/" + P_URN;

            /**
             * Modify resource data. Triggers revalidation. May be sent as PUT with the entire data
             * for the new resource as payload, or as POST with a ResourceOperationRequest, in which
             * case it returns a ticket. Both could be PATCH.
             * 
             * PUT, POST
             */
            public static final String UPDATE_URN = "/resource/update/" + P_URN;

            /**
             * Delete resource.
             * 
             * DELETE
             */
            public static final String DELETE_URN = "/resource/delete/" + P_URN;

            /**
             * Retrieve raw observation data for passed URN in passed geometry.
             * 
             * TODO rename to something different than contextualize when the whole stack is
             * updated.
             * 
             * POST
             */
            public static final String GET_DATA = "/resource/contextualize";

            /**
             * Return a contextualized version of the passed resource as ResourceReference data,
             * after ensuring that the context is appropriate and setting any needed adapter
             * processing in motion for the getdata request that will follow. Optionally insert
             * metadata about expected failure or wait time for the client to use.
             * 
             * TODO rename to contextualize when the previous one is renamed.
             */
            public static final String CONTEXTUALIZE = "/resource/getcontextualized";

            /**
             * Upload a resource.
             * <p>
             * <b>Protocol:</b> POST <br/>
             * <b>Response type:</b> No response <br/>
             * <b>Authentication:</b> session
             */
            public static final String UPLOAD_URN = "/resource/put";

            /**
             * Export a resource.
             * <p>
             * <b>Protocol:</b> GET <br/>
             * <b>Response type:</b> No response <br/>
             * <b>Authentication:</b> session
             */
            public static final String EXPORT_URN = "/resource/export/" + P_URN;

            /**
             * Get URN data for passed URN. Includes expiration to control cacheing.
             * 
             * GET
             */
            public static final String RESOLVE_URN = "/resource/resolve/" + P_URN;

            /**
             * List all resources available to the requesting engine. Parameterize for verbose or
             * short return, or add a query parameter to search for URN and metadata.
             * 
             * GET
             */
            public static final String LIST = "/resource/list";

            /**
             * List detailed information about the passed resource, including the online status and
             * anything related to the associated storage.
             * 
             * GET
             */
            public static final String INFO = "/resource/info/" + P_URN;

        }
    }

    /**
     * Retrieve the public key for this node
     * 
     * <p>
     * <b>Protocol:</b> GET <br/>
     * <b>Response type:</b> text <br/>
     * <b>Authentication:</b> open
     */
    public static final String PUBLICKEY = "/publickey";

    /**
     * Return the JSON schema for all the resources understood by this API. Used for validating
     * beans in connected web applications.
     * 
     * <p>
     * <b>Protocol:</b> GET <br/>
     * <b>Response type:</b> Json schema <br/>
     * <b>Authentication:</b> open
     */
    public static final String SCHEMA = "/schema";

    /**
     * The <em>public</em> k.LAB engine and authentication API is the only part of the API where a
     * commitment to stability (guaranteed from version 1.0 onwards) will be made. The endpoints in
     * this may duplicate others in specific sub-components. All k.LAB clients should <em>only</em>
     * use endpoints from the public API.
     * 
     * @author Ferd
     *
     */
    public interface PUBLIC {

        /**
         * Platform segment for User-Agent header in API requests.
         */
        public static final String USER_AGENT_PLATFORM = "client:klab-api";

        /**
         * Values for the P_EXPORT path variable (names are case-insensitive when used in URLs).
         * Content negotiation through Accept header does the rest.
         */
        public enum Export {
            /**
             * JSON bean for structure of the target
             */
            STRUCTURE,
            /**
             * Data can be JSON (e.g. GeoJSON for geo observation groups), TIFF for raster data, PNG
             * for rendered images (requires viewport parameter), CSV or Excel for tabular views.
             */
            DATA,
            /**
             * Views are tabular data that represent a structured view over a contextualized
             * observation. They can be specified with graph semantics, so they admit image/png
             * media type along with the basic tabular options.
             */
            VIEW,
            /**
             * Legend for a multiple-state observation, either in JSON or as an image. If
             * observation is not a state the call will fail.
             */
            LEGEND,
            /**
             * Report can be PDF or MS Word
             */
            REPORT,
            /**
             * Dataflow and provenance can be text (k.DL source), JSON (Elk diagram), or PNG (image,
             * requires viewport parameter)
             */
            DATAFLOW,
            /**
             * Full provenance includes any provenance records inherited from resources plus
             * process, agent, tasks and plan nodes. Same Accept headers as dataflow, if text is
             * requested the output will be in k.IM entities with RDF mappings.
             */
            PROVENANCE_FULL,
            /**
             * Simplified provenance only includes artifacts with scale-annotated wasDerivedBy
             * relationships.
             */
            PROVENANCE_SIMPLIFIED
        }

        public static final String P_EXPORT = "{export}";
        public static final String P_CONTEXT = "{context}";
        public static final String P_OBSERVATION = "{observation}";
        public static final String P_TICKET = "{ticket}";
        public static final String P_ESTIMATE = "{estimate}";

        /*
         * options to encode URL parameters in the export call
         */
        public static final String O_VIEWPORT = "viewport";
        public static final String O_LOCATOR = "locator";

        public static final String PUBLIC_BASE = HUB.API_BASE + "/public";

        /**
         * Called by users to log in and receive an authentication token for a remote engine.
         * Duplicate from HUB. POST with username and password in data.
         */
        public static final String AUTHENTICATE_USER = HUB.AUTHENTICATE_USER;

        /**
         * Called by users to log off from a remote engine. Duplicate from HUB.
         */
        public static final String DEAUTHENTICATE_USER = HUB.DEAUTHENTICATE_USER;

        /**
         * Post a {@link ContextRequest} to create a context or get an estimate for it. Returns a
         * ticket to poll and retrieve the outcome when done.
         */
        public static final String CREATE_CONTEXT = PUBLIC_BASE + "/submit/context";

        /**
         * Post a {@link ObservationRequest} to make an observation in an existing context or get an
         * estimate for it. Returns a a ticket to poll and retrieve the outcome when done.
         */
        public static final String OBSERVE_IN_CONTEXT = PUBLIC_BASE + "/submit/observation/" + P_CONTEXT;

        /**
         * Call as GET with an estimate ID to accept the estimate and start an observation (context
         * or observation) for which an estimation was previously made. Returns the ticket
         * corresponding to the running task.
         */
        public static final String SUBMIT_ESTIMATE = PUBLIC_BASE + "/submit/estimate/" + P_ESTIMATE;

        /**
         * Retrieve any of the exportable items in the {@link Export} enum. The Observation path
         * variable should contain the context ID for those request that apply to the entire
         * context, like report, dataflows etc. The Accept header selects the format, which must be
         * appropriate for the content requested.
         * <p>
         * Admits, when appropriate, a viewport URL parameter and a locator parameter: TODO explain
         */
        public static final String EXPORT_DATA = PUBLIC_BASE + "/export/" + P_EXPORT + "/" + P_OBSERVATION;

        /**
         * Check the status of the passed ticket. Same as the one in API.TICKET but only accessing
         * tickets created by calls in the public API and requesting the session as a parameter. GET
         * request returns the entire ticket for inspection; asking for a ticket not created in the
         * same session is an error.
         */
        public static final String TICKET_INFO = PUBLIC_BASE + "/ticket/info/" + P_TICKET;

    }

    /**
     * All authority endpoints are public, although some may limit access according to the
     * principal.
     *
     * @author ferdinando.villa
     */
    public interface AUTHORITY {

        /**
         * Authority ID
         */
        public static final String P_AUTHORITY = "{authority}";

        /**
         * Identifier according to authority
         */
        public static final String P_IDENTIFIER = "{identifier}";

        /**
         * Authority capabilities.
         * 
         * GET JSON
         */
        public static final String CAPABILITIES = "/public/authority/" + P_AUTHORITY + "/capabilities";

        /**
         * Resolve the identity and return all related data or errors.
         * 
         * GET JSON
         */
        public static final String RESOLVE = "/public/authority/" + P_AUTHORITY + "/resolve/" + P_IDENTIFIER;

        /**
         * Setup and/or reset caches for an authority. This is the only non-public endpoint.
         */
        public static final String SETUP = "/authority/" + P_AUTHORITY + "/setup";

        /**
         * The Constant QUERY.
         *
         * POST
         */
        public static final String QUERY = "/public/authority/" + P_AUTHORITY + "/query";
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
        public static final String SHUTDOWN = "/admin/shutdown";

        /**
         * Endpoints to remotely deploy/undeploy projects and components.
         * 
         * @author ferdinando.villa
         *
         */
        public interface COMPONENT {

            /** Parameter: project/component ID. */
            public static final String P_COMPONENT = "{component}";

            /**
             * A component can be deployed as a file attachment or from a Git repository.
             * 
             * PUT as attachment POST as Git URL
             */
            public static final String DEPLOY = "/admin/component/deploy";

            /**
             * The Constant UNDEPLOY_COMPONENT.
             *
             * DELETE
             */
            public static final String UNDEPLOY_COMPONENT = "/admin/component/undeploy/" + P_COMPONENT;

            /**
             * The Constant SETUP_COMPONENT.
             *
             * POST
             */
            public static final String SETUP_COMPONENT = "/admin/component/setup/" + P_COMPONENT;
        }

        /**
         * Set one or more properties to the specified values.
         * 
         * POST
         */
        public static final String CONFIGURE = "/admin/configure";

        /**
         * Get the configuration properties and their current values.
         * 
         * GET
         */
        public static final String CONFIGURATION = "/admin/configuration";
    }

    /**
     * Network session endpoints.
     * 
     * @author ferdinando.villa
     *
     */
    public interface NETWORK {

        /** The Constant DISCONNECT. */
        public static final String DISCONNECT = "/network/disconnect";

        /**
         * Query endpoints, using network session authentication.
         * 
         * @author ferdinando.villa
         *
         */
        public interface QUERY {

            /** The Constant MODELS. */
            public static final String MODELS = "/network/query/models";

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
            public static final String MODEL_URN = "/network/retrieve/model/" + P_URN;

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
            public static final String COMPONENT_URN = "/network/retrieve/component/" + P_URN;

        }
    }

    /**
     * Endpoints to open sessions and control observation tasks in them.
     * 
     * @author ferdinando.villa
     *
     */
    public interface ENGINE {

        /**
         * Authorize an engine user. This may use the standard IM authentication (filtering
         * privileges through the engine owner's) or the engine may have its own user directory.
         * Localhost connections to a running engine are automatically authorized with the user that
         * owns it, without a need to going through authentication.
         */
        public static final String AUTHENTICATE = "/engine/authenticate";

        /**
         * Retrieve the full status of the engine, including full data of each session that is
         * currently accessible to the asking host. This will only return the sessions owned by the
         * local engine user, or unless the request comes from an authorized administrator.
         *
         * GET
         */
        public static final String STATUS = "/engine/status";

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
             * Get info on the authenticated session.
             *
             * GET
             */
            public static final String INFO = "/engine/session/info";

            /**
             * The Constant CLOSE.
             *
             * DELETE
             */
            public static final String CLOSE = "/engine/session/close";

            /**
             * Observe URN as a new context. Return task ID.
             */
            public static final String OBSERVE_CONTEXT_URN = "/engine/session/observe/" + P_URN;
        }

        /**
         * Handle engine-local non-semantic assets - import of resources or multiple resource
         * sources, inquiry, and CRUD requests that can't be handled via websockets.
         * 
         * @author ferdinando.villa
         *
         */
        public interface RESOURCE {

            public static final String P_PROJECT = "{project}";
            // public static final String P_RESOURCEPATH = "{resourcepath}";

            /**
             * Get a project resource as is (image, file or otherwise) by passing the path as the
             * trailing end of the URL. Also accepts : as path separator.
             */
            public static final String GET_PROJECT_RESOURCE = "/engine/project/resource/get/" + P_PROJECT
                    + "/**";

            /**
             * For visualization, create an image of the spatial coverage of the resource and return
             * it.
             */
            public static final String GET_RESOURCE_SPATIAL_IMAGE = "/engine/project/resource/spaceimg/"
                    + P_URN;

            /**
             * Update or submit a codelist exposed by a resource. This is used on a local engine
             * only and should be part of the IDE/Engine Websocket communication protocol, but
             * codelists can be very large and cause communication overflow. It should not be
             * supported on remote resources in nodes, which would only be updated atomically
             * through publication.
             * 
             * PUT
             */
            public static final String UPDATE_CODELIST = "/resource/updatecodelist/" + P_URN;

            /**
             * Retrieve a given codelist from a given resource.
             * 
             * GET
             */
            public static final String GET_CODELIST = "/resource/getcodelist/" + P_URN + "/" + P_CODELIST;

            /**
             * Create a codelist from an existing attribute and return the serialized contents.
             * 
             * GET
             */
            public static final String CREATE_CODELIST = "/resource/createcodelist/" + P_URN + "/"
                    + P_CODELIST;

        }

        /**
         * Endpoints to access contexts, using context tokens for authentication.
         * 
         * @author ferdinando.villa
         *
         */
        public interface OBSERVATION {

            /** The Constant P_CONTEXT. */
            public static final String P_CONTEXT = "{context}";

            public static final String P_VIEW = "{view}";

            /**
             * Create new context from the URN of its definition or remote computation. Return task
             * descriptor.
             */
            public static final String CREATE_URN = "/engine/session/observation/create/" + P_URN;

            /**
             * Observe URN in pre-authorized context. Return task ID.
             */
            public static final String OBSERVE_CONTEXT_URN = "/engine/session/observation/observe/"
                    + P_CONTEXT + "/"
                    + P_URN;

            /**
             * Run temporal transitions in pre-authorized context. Return task descriptor.
             */
            public static final String RUN_CONTEXT = "/engine/session/observation/run/" + P_CONTEXT;

            // /**
            // * Retrieve dataflow for passed root context.
            // */
            // public static final String RETRIEVE_DATAFLOW =
            // "/engine/session/observation/dataflow/" + P_CONTEXT;

            /**
             * Format contextualization report as per request and return it.
             */
            public static final String REPORT_CONTEXT = "/engine/session/observation/report/" + P_CONTEXT;

            /**
             * Return structured documentation view (with view being one of report, figures, tables,
             * resources, models or provenance)
             */
            public static final String DOCUMENTATION_VIEW_CONTEXT = "/engine/session/observation/documentation/"
                    + P_VIEW + "/" + P_CONTEXT;

            /**
             * Endpoints to access tasks.
             * 
             * @author ferdinando.villa
             *
             */
            public interface TASK {

                /** The Constant P_TASK. */
                public static final String P_TASK = "{task}";

                public static final String INTERRUPT = "/engine/session/task/interrupt/" + P_TASK;

            }

            /**
             * Endpoints to retrieve data and visualizations from "live" observations in context.
             * All GET, all require session authentication.
             * 
             * @author ferdinando.villa
             *
             */
            public interface VIEW {

                public static final String P_OBSERVATION = "{observation}";

                /**
                 * Get the observation structure and description
                 */
                public static final String DESCRIBE_OBSERVATION = "/engine/session/view/describe/"
                        + P_OBSERVATION;

                /**
                 * Get a summary of the observation state, either globally or at location (through a
                 * parameter)
                 */
                public static final String SUMMARIZE_OBSERVATION = "/engine/session/view/summary/"
                        + P_OBSERVATION;

                /**
                 * Get one or more siblings of an artifact, potentially with offsets and number
                 */
                public static final String GET_CHILDREN_OBSERVATION = "/engine/session/view/children/"
                        + P_OBSERVATION;

                /**
                 * Get the data for a state in directly useable form, as values or images
                 */
                public static final String GET_DATA_OBSERVATION = "/engine/session/view/data/"
                        + P_OBSERVATION;

            }

        }

    }

    /**
     * Endpoints for the stats server.
     * 
     * @author steven wohl
     *
     */
    public interface STATS {

        public static final String STATS_BASE = API_BASE + "/stats";

        public static final String STATS_ADD = STATS_BASE + "/add";

        public static final String STATS_REPORT = STATS_BASE + "/report";

        /*
         * public anonymous endpoints for web sites or quick monitoring
         */
        public static final String GEOJSON_EVENTS = "/public/stats/geojson/events";
        
        public interface PARAMETERS {
            public static final String TYPE = "type";

            public static final String PAGE = "page";

            public static final String LIMIT = "limit";

        }

    }

    /**
     * Semantic server - suggestions, search and concept/data validation
     * 
     * @author Ferd
     *
     */
    public interface SEMANTICS {

        public static final String SEMANTICS_BASE = API_BASE + "/semantics";

        public interface VALIDATION {

        }

        public interface SUGGESTION {

        }

    }

}
