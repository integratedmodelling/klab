package org.integratedmodelling.klab.data.rest.resources.requests

/**
 * Authentication request.
 */
data class AuthenticationRequest (
		val username: String,
		val userKey: String,
		val userType: String,
		val certificate: String)