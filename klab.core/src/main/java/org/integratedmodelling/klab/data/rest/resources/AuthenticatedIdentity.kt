package org.integratedmodelling.klab.data.rest.resources

import java.util.Date

/**
 * An identity with authentication data
 */
data class AuthenticatedIdentity (
		val identity: IdentityReference,
		val groups: List<Group>,
		val expiry: Date,
		val token: String)