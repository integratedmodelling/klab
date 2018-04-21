package org.integratedmodelling.klab.data.rest.resources.requests

import org.integratedmodelling.klab.api.auth.IUserIdentity
import org.integratedmodelling.klab.data.rest.resources.NodeReference

/**
 * Group for permissions. Just a string for the time being.
 */
data class Group (val id: String)

/**
 * A reference to an identity (user, partner, ...) with admin data but no authentication.
 */
data class IdentityReference(
		val id: String,
		val email: String,
		// Parseable by Joda DateTime
		val lastLogin: String) {
	constructor() : this("", "", "")
	constructor(identity: IUserIdentity) : this(identity.username, identity.emailAddress, identity.lastLogin.toString())
}

/**
 * An identity with authentication data
 */
data class AuthenticatedIdentity (
		val identity: IdentityReference,
		val groups: List<Group>,
		// Parseable by Joda DateTime
		val expiry: String,
		val token: String) {
	constructor() : this(IdentityReference(), ArrayList(), "", "")
}
/**
 * Authentication request.
 */
data class AuthenticationRequest (
		val username: String,
		val userKey: String,
		val userType: String,
		val certificate: String)

/**
 * Authentication response.
 */
data class AuthenticationResponse (
		val userData: AuthenticatedIdentity,
		val nodes: List<NodeReference>,
		val authenticatingNodeId: String) {
	constructor() : this(AuthenticatedIdentity(), ArrayList(), "")
}

