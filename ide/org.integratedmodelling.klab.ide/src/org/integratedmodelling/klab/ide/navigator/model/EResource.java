package org.integratedmodelling.klab.ide.navigator.model;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.utils.Path;

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
        if (IFolder.class.isAssignableFrom(adapter)) {
            return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName())
                    .getFolder(IKimProject.RESOURCE_FOLDER + "/"
                            + Path.getLast(resource.getLocalPath(), '/'));
        } else if (IFile.class.isAssignableFrom(adapter) && !IProject.class.isAssignableFrom(adapter)) {
            return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName())
                    .getFile(Path.getFrom(resource.getLocalPath(), 1, '/') + "/resource.json");
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
