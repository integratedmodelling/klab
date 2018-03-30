package org.integratedmodelling.kim.scoping;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.DefaultGlobalScopeProvider;
import org.integratedmodelling.kim.kim.KimPackage;
import com.google.common.base.Predicate;

/**
 * Global scope provider adds visibility of external imported projects, such as
 * the worldview.
 * 
 * @author Ferd
 *
 */
public class KimGlobalScopeProvider extends DefaultGlobalScopeProvider {

	@Override
	public IScope getScope(Resource resource, EReference reference, Predicate<IEObjectDescription> filter) {
		EClass referenceType = reference.getEReferenceType();
		if (EcoreUtil2.isAssignableFrom(KimPackage.Literals.NAMESPACE, referenceType)) {
			IResourceDescriptions descriptions = getResourceDescriptions(resource);
			System.out.println(descriptions);
//			return typeScopeProvider.getScope(resource, reference, filter);
		}
		return super.getScope(resource, reference, filter);
	}
}
