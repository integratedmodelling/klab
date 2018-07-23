package org.integratedmodelling.klab.ide.navigator.model;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kim.api.IKimProject;

public class EResourceFolder extends ENavigatorItem {

	IKimProject project;
	
	public EResourceFolder(EProject parent) {
        super(parent.id + "#__RESOURCES__", parent);
        this.project = parent.delegate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
        if (IResource.class.isAssignableFrom(adapter)) {
            return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName()).getFolder(IKimProject.RESOURCE_FOLDER);
        }
        return null;
	}

    @Override
    public ENavigatorItem[] getEChildren() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasEChildren() {
        // TODO Auto-generated method stub
        return false;
    }

}
