package org.integratedmodelling.klab.data.rest.resources

import org.integratedmodelling.klab.api.auth.INodeIdentity

/**
 Node identity, reported for the authenticating node in auth response.
 */
data class NodeReference (
		val id: String,
		val permissions: Set<INodeIdentity.Permission>,
		val owningPartner: IdentityReference,
		val urls: List<String>,
        val online: Boolean,
		val retryPeriodMinutes: Int,
		val loadFactor: Int,
		val incomingConnections: List<String>,
		val outgoingConnections: List<String>) {
	constructor() : this("", HashSet(), IdentityReference(), ArrayList(), false, 0, 0, ArrayList(), ArrayList())
}