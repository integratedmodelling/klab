//package org.integratedmodelling.klab.data.rest.resources
//
//data class ComponentReference (val name: String)
//
//data class ServiceCall (val name: String, val parameters: Map<String, Any>);
//
//data class Capabilities (
//	var name: String? = null,
//	var version: String? = null,
//	var build: String? = null,
//	var services: List<ServiceCall> = ArrayList(),
//	var authorities: List<AuthorityReference> = ArrayList(),
//	var staticComponents: List<ComponentReference> = ArrayList(),
//	var dynamicComponents: List<ComponentReference> = ArrayList(),
//	var refreshFrequencyMillis: Long = 0,
//	var loadFactor: Int = 0,
//	var owner: IdentityReference? = null
//	// ... distributed computation etc...
//)