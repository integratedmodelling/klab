package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
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
	// if directory, children are the subdirectories and subfiles; if file, children
	// are the pages specified in the included JSON.
	File file;
	String name;

	public EDocumentationFolder(EProject parent, File file, String name) {
		super(parent.id + "#__DOCUMENTATION__", parent);
		this.project = parent;
		this.file = file;
		this.name = name;
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

	public String getName() {
		return name;
	}
	
	@Override
	public boolean hasEChildren() {
		return Activator.klab().getProjectResources(project).size() > 0;
	}

}
