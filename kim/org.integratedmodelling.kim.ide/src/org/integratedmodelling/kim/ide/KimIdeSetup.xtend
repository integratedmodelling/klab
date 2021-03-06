/*
 * Copyright (C) 2009-2016 integratedmodelling.org
 * generated by Xtext 2.12.0
 */
package org.integratedmodelling.kim.ide

import com.google.inject.Guice
import org.eclipse.xtext.util.Modules2
import org.integratedmodelling.kim.KimRuntimeModule
import org.integratedmodelling.kim.KimStandaloneSetup

/**
 * Initialization support for running Xtext languages as language servers.
 */
class KimIdeSetup extends KimStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new KimRuntimeModule, new KimIdeModule))
	}
	
}
