/*
 * generated by Xtext 2.9.1
 */
package org.integratedmodelling.kim


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class KimStandaloneSetup extends KimStandaloneSetupGenerated {

	def static void doSetup() {
		new KimStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
