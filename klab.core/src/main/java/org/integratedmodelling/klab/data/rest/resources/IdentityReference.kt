package org.integratedmodelling.klab.data.rest.resources

import java.util.Date

/**
 * A reference to an identity (user, partner, ...) with admin data but no authentication.
 */
data class IdentityReference (
		val id: String,
		val email: String,
		val lastLogin: Date)