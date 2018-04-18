package org.integratedmodelling.klab.data.rest.resources.responses

import org.integratedmodelling.klab.data.rest.resources.AuthenticatedIdentity
import org.integratedmodelling.klab.data.rest.resources.NodeReference

/**
 * Auth response.
 */
data class AuthenticationResponse (
		val userData: AuthenticatedIdentity,
		val nodes: List<NodeReference>,
		val authenticatingNodeId: String) {
	constructor() : this(AuthenticatedIdentity(), ArrayList(), "")
}