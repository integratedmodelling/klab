package org.integratedmodelling.klab.ide;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.ide.IGotoMarker;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.ide.navigator.model.ENamespace;

public class KlabAdapterFactory implements IAdapterFactory {

	public KlabAdapterFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		// TODO Auto-generated method stub
//		System.out.println("trying to adapt " + adaptableObject + " to " + adapterType.getCanonicalName());
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		// TODO Auto-generated method stub
		return new Class[] {
				IFile.class,
				IGotoMarker.class,
				IProject.class,
				ENamespace.class,
				IKimProject.class
		};
	}

}
