package org.integratedmodelling.klab.ide;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;

public class KlabAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
    @Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		if (adaptableObject instanceof ENavigatorItem) {
		    if (IResource.class.isAssignableFrom(adapterType)) {
	            return ((ENavigatorItem)adaptableObject).getAdapter(adapterType);
		    } else if (EObject.class.isAssignableFrom(adapterType)) {
		        if (adaptableObject instanceof EKimObject) {
		            return (T)((EKimObject)adaptableObject).getEObject();
		        }
		    }
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] {
				IResource.class,
				ENavigatorItem.class,
				EObject.class
		};
	}

}
