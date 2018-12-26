package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;

public class EDocumentationFolder extends ENavigatorItem {
	
	EProject project;

	public EDocumentationFolder(EProject parent) {
		super(parent.id + "#__DOCUMENTATION__", parent);
		this.project = parent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (IResource.class.isAssignableFrom(adapter) && adapter != IProject.class) {
			return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName())
					.getFolder(IKimProject.DOCUMENTATION_FOLDER);
		}
		return null;
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		List<ENavigatorItem> ret = new ArrayList<>();
		for (EResourceReference resource : Activator.klab().getProjectResources(project)) {
			ret.add(new EResource(resource, this));
		}
		return ret.toArray(new ENavigatorItem[ret.size()]);
	}

	@Override
	public boolean hasEChildren() {
		return Activator.klab().getProjectResources(project).size() > 0;
	}

}
