package org.integratedmodelling.klab.data.rest.resources.responses

import org.integratedmodelling.kim.model.KimServiceCall
import org.integratedmodelling.klab.data.rest.resources.AuthorityReference
import org.integratedmodelling.klab.data.rest.resources.requests.IdentityReference

data class ComponentReference (val name: String)

data class Capabilities (
	var name: String? = null,
	var version: String? = null,
	var build: String? = null,
	var services: List<KimServiceCall> = ArrayList(),
	var authorities: List<AuthorityReference> = ArrayList(),
	var staticComponents: List<ComponentReference> = ArrayList(),
	var dynamicComponents: List<ComponentReference> = ArrayList(),
	var refreshFrequencyMillis: Long = 0,
	var loadFactor: Int = 0,
	var owner: IdentityReference? = null
	// ... distributed computation etc...
)