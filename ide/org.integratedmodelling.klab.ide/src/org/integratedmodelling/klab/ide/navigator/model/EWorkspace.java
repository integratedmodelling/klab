package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;

public class EWorkspace extends ENavigatorItem {

	public static EWorkspace INSTANCE = new EWorkspace();

	private EWorkspace() {
		super("__WORKSPACE__", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (IWorkspaceRoot.class.isAssignableFrom(adapter)) {
			return (T) ResourcesPlugin.getWorkspace().getRoot();
		}
		return null;
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		List<ENavigatorItem> ret = new ArrayList<>();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (!project.isAccessible() || !project.isOpen()) {
				continue;
			}
			IKimProject p = Kim.INSTANCE.getProject(project.getName());
			if (p != null) {
				ret.add(new EProject(p, this));
			}
		}
		return ret.toArray(new ENavigatorItem[ret.size()]);
	}

	@Override
	public boolean hasEChildren() {
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			if (Kim.INSTANCE.getProject(project.getName()) != null) {
				return true;
			}
		}
		return false;
	}

}
