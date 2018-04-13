package org.integratedmodelling.klab.data.rest.resources.responses

import com.github.reinert.jjschema.Attributes
import org.integratedmodelling.kim.model.KimServiceCall
import org.integratedmodelling.klab.data.rest.resources.AuthorityReference
import org.integratedmodelling.klab.data.rest.resources.ComponentReference
import org.integratedmodelling.klab.data.rest.resources.IdentityReference

data class Capabilities (

	@Attributes(required = true, description = "Unique name of the node")
	var name: String,
	
	var version: String,
	var build: String,
	
	var services: List<KimServiceCall>,
	var authorities: List<AuthorityReference>,
	var staticComponents: List<ComponentReference>,
	var dynamicComponents: List<ComponentReference>,
	var refreshFrequencyMillis: Long,
	var loadFactor: Int,
	var owner: IdentityReference
	// ... distributed computation etc...
)