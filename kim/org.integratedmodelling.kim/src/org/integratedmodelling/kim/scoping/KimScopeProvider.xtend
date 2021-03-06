/*
 * generated by Xtext 2.9.1
 */
package org.integratedmodelling.kim.scoping

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.scoping.IScope
import org.eclipse.xtext.scoping.Scopes
import org.integratedmodelling.kim.kim.Import
import org.integratedmodelling.kim.kim.KimPackage
import org.integratedmodelling.kim.kim.Model
import org.integratedmodelling.kim.kim.Namespace

/** 
 * This class contains custom scoping description. For now just make it namespace-aware 
 * for Kim files.
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#scoping
 * on how and when to use it.
 */
public class KimScopeProvider extends AbstractKimScopeProvider {

	override IScope getScope(EObject context, EReference reference) {
		if (context instanceof Import) {
//			if (reference == KimPackage.Literals.IMPORT__IMPORTED_NAMESPACE) {

//				// must give it all the existing namespaces to choose from based on name
//				val rootElement = EcoreUtil2.getContainerOfType(context, Model)
//				val resourceSet = rootElement.eResource.resourceSet
//				var namespaces = <Namespace>newArrayList
//				for (resource : resourceSet.resources) {
//					if (resource !== rootElement.eResource && resource.contents.size() > 0 &&
//						resource.contents.get(0) instanceof Model) {
//						namespaces += (resource.contents.get(0) as Model).namespace
//						println('   ' + (resource.contents.get(0) as Model).namespace.name)
//					}
//				}
//				return Scopes.scopeFor(namespaces)
//			}
		}
		return super.getScope(context, reference)
	}
}
