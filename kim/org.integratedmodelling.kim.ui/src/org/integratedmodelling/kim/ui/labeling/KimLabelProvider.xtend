/*
 * generated by Xtext 2.9.1
 */
package org.integratedmodelling.kim.ui.labeling

import com.google.inject.Inject
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider
import org.integratedmodelling.kim.kim.ModelStatement
import org.integratedmodelling.kim.kim.ObservableSemantics
import org.integratedmodelling.kim.model.Kim

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class KimLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	def text(ObservableSemantics obs) {
		Kim.INSTANCE.declareObservable(obs).toString()
	}

	def text(ModelStatement obs) {
		if (obs.body === null) "?" else obs.body.name
	}

//	def text(ConceptStatement model) {
//		'concept';
//	}
//	
//	def text(Dependency model) {
//		'dependency';
//	}
//	def text(Namespace namespace) {
//		namespace.name;
//	}
// Labels and icons can be computed like this:
//	def text(Greeting ele) {
//		'A greeting to ' + ele.name
//	}
//
//	def image(Greeting ele) {
//		'Greeting.gif'
//	}
}
