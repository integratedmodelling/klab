package org.integratedmodelling.klab.ide.navigator.e3;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EWorkspace;

public class TreeContentProvider extends WorkbenchContentProvider {

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (KlabNavigator._viewer == null) {
			KlabNavigator._viewer = viewer;
		}
		super.inputChanged(viewer, oldInput, newInput);
	}

	public void dispose() {
		super.dispose();
	}

	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	public Object[] getChildren(Object parent) {
		return parent instanceof EWorkspace ? EWorkspace.INSTANCE.getEChildren()
				: ((ENavigatorItem) parent).getEChildren();
	}

	public Object getParent(Object element) {
		return element instanceof EProject ? EWorkspace.INSTANCE : ((ENavigatorItem) element).getEParent();
	}

	public boolean hasChildren(Object element) {
		return element instanceof IWorkspaceRoot ? EWorkspace.INSTANCE.hasEChildren()
				: (element instanceof ENavigatorItem ? ((ENavigatorItem) element).hasEChildren() : false);
	}
}
