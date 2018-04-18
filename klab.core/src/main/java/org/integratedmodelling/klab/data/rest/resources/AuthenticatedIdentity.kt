package org.integratedmodelling.klab.data.rest.resources

import java.util.Date

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