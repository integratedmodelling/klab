/*
 * Copyright (C) 2009-2016 integratedmodelling.org
 * generated by Xtext 2.12.0
 */
package org.integratedmodelling.kim.tests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.util.ParseHelper
import org.integratedmodelling.kim.kim.Model
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(KimInjectorProvider)
class KimParsingTest {
	@Inject
	ParseHelper<Model> parseHelper
	
	@Test
	def void loadModel() {
//		val result = parseHelper.parse('''
//			Hello Xtext!
//		''')
//		Assert.assertNotNull(result)
//		Assert.assertTrue(result.eResource.errors.isEmpty)
	}
}
