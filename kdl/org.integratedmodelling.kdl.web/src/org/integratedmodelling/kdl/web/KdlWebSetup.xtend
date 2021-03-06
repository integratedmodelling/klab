/*
 * generated by Xtext 2.12.0
 */
package org.integratedmodelling.kdl.web

import com.google.inject.Guice
import com.google.inject.Injector
import org.eclipse.xtext.util.Modules2
import org.integratedmodelling.kdl.KdlRuntimeModule
import org.integratedmodelling.kdl.KdlStandaloneSetup
import org.integratedmodelling.kdl.ide.KdlIdeModule

/**
 * Initialization support for running Xtext languages in web applications.
 */
class KdlWebSetup extends KdlStandaloneSetup {
	
	override Injector createInjector() {
		return Guice.createInjector(Modules2.mixin(new KdlRuntimeModule, new KdlIdeModule, new KdlWebModule))
	}
	
}
