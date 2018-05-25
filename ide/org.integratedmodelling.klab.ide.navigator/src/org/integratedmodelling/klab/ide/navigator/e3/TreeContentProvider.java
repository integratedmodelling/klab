package org.integratedmodelling.klab.ide.navigator.e3;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.model.Kim;

class TreeContentProvider extends WorkbenchContentProvider {

	public TreeContentProvider() {}
	
    File wsRoot = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile();

    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

    public void dispose() {
    }

    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    public Object[] getChildren(Object parent) {
        if (parent instanceof IWorkspaceRoot) {
            return ((IWorkspaceRoot) parent).getProjects();
        } else if (parent instanceof IProject) {
            return getNamespaces(((IProject) parent).getName());
        } else if (parent instanceof IKimNamespace) {
            return ((IKimNamespace)parent).getChildren().toArray();
        }
        return new Object[] {};
    }

    private Object[] getNamespaces(String name) {
        IKimProject project = Kim.INSTANCE.getProject(name, wsRoot);
        return project == null ? new Object[] {} : project.getNamespaces().toArray();
    }

    public Object getParent(Object element) {
        if (element instanceof IProject) {
            return ResourcesPlugin.getWorkspace().getRoot();
        } else if (element instanceof IKimNamespace) {
            return ResourcesPlugin.getWorkspace().getRoot().getProject(((IKimNamespace) element).getName());
        } else if (element instanceof IKimStatement) {
            return ((IKimStatement)element).getParent();
        }
        return null;
    }

    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }
}
