package org.integratedmodelling.klab.data.rest.resources

import com.github.reinert.jjschema.Attributes

data class ObservationRequest (
		@Attributes(required = true, description = "Unique name of the node")
		var name: String? = null
)