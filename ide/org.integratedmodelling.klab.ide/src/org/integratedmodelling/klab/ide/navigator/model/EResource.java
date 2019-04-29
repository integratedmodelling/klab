package org.integratedmodelling.klab.ide.navigator.model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.ide.Activator;
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
        if (IFile.class.isAssignableFrom(adapter) ) {
            return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName())
                    .getFile(Path.getFrom(resource.getLocalPath(), 1, '/') + "/resource.json");
        } else if (IResource.class.isAssignableFrom(adapter) && !IProject.class.isAssignableFrom(adapter)) {
            return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName())
                    .getFolder(IKimProject.RESOURCE_FOLDER + "/"
                            + Path.getLast(resource.getLocalPath(), '/'));
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
    
    public String getUrn() {
    	return resource.getUrn();
    }
    
    public Map<String, String> getExportFormats() {
    	Map<String, String> ret = new LinkedHashMap<>();
    	if (resource.getExportFormats().isEmpty()) {
    		ret.putAll(Activator.klab().getResourceAdapter(resource.getAdapterType()).getExportCapabilities());
    	} else {
    		ret.putAll(resource.getExportFormats());
    	}
    	return ret;
    }
}
