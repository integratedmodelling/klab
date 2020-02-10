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

/**
 * This interface and its members describe the REST API of k.LAB. The API
 * enables managing semantic and non-semantic assets and building artifacts and
 * observation dataflows, with certificate-based autentication. The API for
 * interacting with a remote node using regular (login) authentication, manage
 * profiles and certificates, and handle communication and cross-authentication
 * among nodes is separate, as it shares no functional connection with the k.LAB
 * project. The only connection point between the two APIs is the
 * {@link #AUTHENTICATE} endpoint, which is used by clients to obtain their
 * network credentials using the certificate file contents.
 * <p>
 * This API is common to nodes and engines, although nodes currently can be
 * expected to get less requests on ENGINE endpoints and be the only one in
 * charge of answering RESOURCE requests.
 * <p>
 * Endpoints are organized in groups implemented in sub-interfaces. Any path
 * with parameters has the name of each parameter in its constant name,
 * separated by an underscore. For each parameter <code>xxx</code>, the same
 * sub-interface must contain a corresponding <code>P_xxx</code> constant, and
 * the endpoint string must be built using it. Otherwise, constant names and
 * endpoint strings are identical and paths reflect the interface structure.
 * <p>
 * Each sub-interface should correspond to a controller with methods that match
 * the endpoint names, using the same authentication. If a group has an
 * AUTHENTICATE endpoint, that will be implemented in the controller
 * corresponding to the containing interface, and return the token needed to
 * authorize the subgroups so that the same authorization can be applied to the
 * controller.
 * <p>
 * The endpoint corresponding to a sub-interface should bring up an appropriate
 * UI when accessed with HTML response type. This applies at least to the VIEW
 * endpoints (k.EXPLORER) and to the ADMIN endpoints (administration dashboard).
 * The root context path should redirect to a login page if connection is from a
 * remote host and to the admin dashboard if connected to from localhost.
 * <p>
 *
 * @author ferdinando.villa
 * @author J. Luke Scott
 * @version $Id: $Id
 */
public interface API {

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

	/**
	 * Ping service. Simply returns the number of milliseconds since engine boot, or
	 * 0 if engine is not running. Accepts HEAD requests to simply check for
	 * heartbeat.
	 * 
	 * <p>
	 * <b>Protocol:</b> GET, HEAD <br/>
	 * <b>Response type:</b> long (if used with GET) <br/>
	 * <b>Authentication:</b>
	 */
	public static final String PING = "/ping";

	/**
	 * STOMP endpoint for client/server notifications. Handled through Websockets
	 * protocol.
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
	 * <b>Authentication:</b> open or {@link INetworkSessionIdentity} (response
	 * reflect access levels)
	 * 
	 */
	public static final String CAPABILITIES = "/capabilities";

	/**
	 * k.IM descriptor endppoint. Returns info on the language supported, including
	 * the version, build, list of keywords and other useful statistics.
	 * 
	 * <p>
	 * <b>Protocol:</b> GET <br/>
	 * <b>Response type:</b> Json <br/>
	 * <b>Response:</b> {@code org.integratedmodelling.klab.rest.KimCapabilities}
	 * <br/>
	 * <b>Authentication:</b> open
	 */
	public static final String KIM = "/kim";

	/**
	 * Read-only ticket API, implemented in the Node and maybe later in the Hub.
	 * Engines have ticket management but only Websockets clients can access it.
	 * 
	 * @author Ferd
	 *
	 */
	public static interface TICKET {

		/**
		 * Retrieve the specific ticket with the passed ID.
		 * 
		 * GET
		 */
		public static final String INFO = "/ticket/info/{ticket}";

		/**
		 * Retrieve all tickets matching the field values in the query string.
		 * 
		 * GET
		 */
		public static final String QUERY = "/ticket/query";

	}

	public static interface HUB {

		/**
		 * Returns authenticated user details and network status with all nodes
		 * (including offline if applicable) with refresh rate and unique network access
		 * token. Should be the only authentication call necessary in this API.
		 * 
		 * <p>
		 * <b>Protocol:</b> POST <br/>
		 * <b>Response type:</b> Json <br/>
		 * <b>Request:</b>
		 * {@code org.integratedmodelling.klab.rest.resources.requests.AuthenticationRequest}
		 * <br/>
		 * <b>Response:</b>
		 * {@code org.integratedmodelling.klab.rest.resources.responses.AuthenticationResponse}
		 * <br/>
		 * <b>Authentication:</b> open
		 */
		public static final String AUTHENTICATE_ENGINE = "/api/auth-cert/engine";

		/**
		 * Called by nodes on hubs when authenticating with them. Parameters like the
		 * engine version.
		 */
		public static final String AUTHENTICATE_NODE = "/api/auth-cert/node";

		public static interface INDEXING {

			/**
			 * Submit anonymized successful query data including its context, time elapsed
			 * in resolution and contextualization, groups, computation and load metrics for
			 * indexing and analysis.
			 * 
			 * PUT
			 */
			public static final String SUBMIT = "/indexing/submit";

			/**
			 * Request suggestions for queries in context matching a query string and user
			 * permissions, sorted by match score and (increasing) computational load.
			 * 
			 * POST
			 */
			public static final String SUGGESTIONS = "/indexing/suggestions";

		}

	}

	public static interface NODE {

		public static interface RESOURCE {

			/**
			 * Add a resource to the public catalog by uploading zipped contents from a
			 * valid local resource. Return URN after validation.
			 * 
			 * PUT
			 */
			public static final String SUBMIT_FILES = "/resource/submitfiles";

			/**
			 * Like the above but used when the resource only contains a single
			 * resource.json metadata file, whose contents are sent directly in a POST
			 * message.
			 * 
			 * POST
			 */
			public static final String SUBMIT_DESCRIPTOR = "/resource/submitdescriptor";

			/**
			 * Publish a local resource to the public catalog of this node. Return the final
			 * URN and status (which may be 'ongoing', i.e. the resource may not be
			 * available yet).
			 * 
			 * POST
			 */
			public static final String PUBLISH_URN = "/resource/publish/" + P_URN;

			/**
			 * Modify resource data. Triggers revalidation.
			 * 
			 * PATCH
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
			 * POST
			 */
			public static final String CONTEXTUALIZE = "/resource/contextualize";

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
			 * List all resources available to the requesting engine. Parameterize for
			 * verbose or short return.
			 * 
			 * GET
			 */
			public static final String LIST = "/resource/list";

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
	 * Return the JSON schema for all the resources understood by this API. Used for
	 * validating beans in connected web applications.
	 * 
	 * <p>
	 * <b>Protocol:</b> GET <br/>
	 * <b>Response type:</b> Json schema <br/>
	 * <b>Authentication:</b> open
	 */
	public static final String SCHEMA = "/schema";

	/**
	 * Authority endpoints are public.
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
		public static final String CAPABILITIES = "/authority/" + P_AUTHORITY + "/capabilities";

		/**
		 * The Constant RESOLVE.
		 *
		 * GET JSON
		 */
		public static final String RESOLVE = "/authority/" + P_AUTHORITY + "/resolve/" + P_IDENTIFIER;

		/**
		 * The Constant QUERY.
		 *
		 * POST
		 */
		public static final String QUERY = "/authority/" + P_AUTHORITY + "/query";
	}

	/**
	 * TODO flesh out - shutdown, reset/init, deploy/setup components, undeploy,
	 * import, submit, update/delete namespaces, workspace management, lock/unlock.
	 * PUT endpoints for configuration. To be tied to future configuration
	 * dashboard.
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
		 * Authorize an engine user. This may use the standard IM authentication
		 * (filtering privileges through the engine owner's) or the engine may have its
		 * own user directory. Localhost connections to a running engine are
		 * automatically authorized with the user that owns it, without a need to going
		 * through authentication.
		 */
		public static final String AUTHENTICATE = "/engine/authenticate";

		/**
		 * Retrieve the full status of the engine, including full data of each session
		 * that is currently accessible to the asking host. This will only return the
		 * sessions owned by the local engine user, or unless the request comes from an
		 * authorized administrator.
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
		 * Endpoints to access contexts, using context tokens for authentication.
		 * 
		 * @author ferdinando.villa
		 *
		 */
		public interface OBSERVATION {

			/** The Constant P_CONTEXT. */
			public static final String P_CONTEXT = "{context}";

			/**
			 * Create new context from the URN of its definition or remote computation.
			 * Return task descriptor.
			 */
			public static final String CREATE_URN = "/engine/session/observation/create/" + P_URN;

			/**
			 * Observe URN in pre-authorized context. Return task ID.
			 */
			public static final String OBSERVE_CONTEXT_URN = "/engine/session/observation/observe/" + P_CONTEXT + "/"
					+ P_URN;

			/**
			 * Run temporal transitions in pre-authorized context. Return task descriptor.
			 */
			public static final String RUN_CONTEXT = "/engine/session/observation/run/" + P_CONTEXT;

			/**
			 * Retrieve dataflow for passed root context.
			 */
			public static final String RETRIEVE_DATAFLOW = "/engine/session/observation/dataflow/" + P_CONTEXT;

			/**
			 * Format contextualization report as per request and return it.
			 */
			public static final String REPORT_CONTEXT = "/engine/session/observation/report/" + P_CONTEXT;

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
			 * Endpoints to retrieve data and visualizations from "live" observations in
			 * context. All GET, all require session authentication.
			 * 
			 * @author ferdinando.villa
			 *
			 */
			public interface VIEW {

				public static final String P_OBSERVATION = "{observation}";

				/**
				 * Get the observation structure and description
				 */
				public static final String DESCRIBE_OBSERVATION = "/engine/session/view/describe/" + P_OBSERVATION;

				/**
				 * Get a summary of the observation state, either globally or at location
				 * (through a parameter)
				 */
				public static final String SUMMARIZE_OBSERVATION = "/engine/session/view/summary/" + P_OBSERVATION;

				/**
				 * Get one or more siblings of an artifact, potentially with offsets and number
				 */
				public static final String GET_CHILDREN_OBSERVATION = "/engine/session/view/children/" + P_OBSERVATION;

				/**
				 * Get the data for a state in directly useable form, as values or images
				 */
				public static final String GET_DATA_OBSERVATION = "/engine/session/view/data/" + P_OBSERVATION;

			}

			/**
			 * Handle engine-local non-semantic assets - import of resources or multiple
			 * resource sources, inquiry.
			 * 
			 * @author ferdinando.villa
			 *
			 */
			public interface RESOURCE {

			}
		}

	}

}
