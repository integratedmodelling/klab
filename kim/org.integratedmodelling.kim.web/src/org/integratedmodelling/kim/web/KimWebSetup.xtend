/*
 * generated by Xtext 2.12.0
 */
package org.integratedmodelling.kim.web

import com.google.inject.Guice
import com.google.inject.Injector
import org.eclipse.xtext.util.Modules2
import org.integratedmodelling.kim.KimRuntimeModule
import org.integratedmodelling.kim.KimStandaloneSetup
import org.integratedmodelling.kim.ide.KimIdeModule

/**
 * Initialization support for running Xtext languages in web applications.
 */
class KimWebSetup extends KimStandaloneSetup {
	
	override Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new KimRuntimeModule, new KimIdeModule, new KimWebModule))
	}
	
}
