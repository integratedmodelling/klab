package org.integratedmodelling.klab.ide.navigator.model;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;

public class EResource extends ENavigatorItem {

	private EResourceReference resource;
	
	protected EResource(EResourceReference resource, ENavigatorItem parent) {
		super(resource.getUrn(), parent);
		this.resource = resource;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		EProject project = getEParent(EProject.class);
		if (IResource.class.isAssignableFrom(adapter) && adapter != IProject.class) {
			return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName())
					.getFolder(IKimProject.RESOURCE_FOLDER + resource.getLocalPath());
		}
		return null;
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		return null;
	}

	@Override
	public boolean hasEChildren() {
		return false;
	}
	
	public EResourceReference getResource() {
		return resource;
	}
}
