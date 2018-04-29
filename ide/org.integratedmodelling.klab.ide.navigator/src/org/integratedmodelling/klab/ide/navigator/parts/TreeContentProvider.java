package org.integratedmodelling.klab.ide.navigator.parts;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

class TreeContentProvider implements ITreeContentProvider {

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	public void dispose() {
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public Object[] getChildren(Object parent) {
		if (parent instanceof IWorkspaceRoot) {
			return ((IWorkspaceRoot)parent).getProjects();
		} else if (parent instanceof IProject) {
			
		}
		return new Object[] {};
	}

	public Object getParent(Object element) {
		if (element instanceof IProject) {
			return ResourcesPlugin.getWorkspace().getRoot();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}
}