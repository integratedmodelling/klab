package org.integratedmodelling.klab.ide.navigator;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.Viewer;
import org.integratedmodelling.kim.ui.navigator.KimContentProvider;

public class KlabContentProvider extends KimContentProvider {

	public KlabContentProvider() {
		// TODO Auto-generated constructor stub
		System.out.println("MI HA CREATO! MI HA CREATO!");
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		super.inputChanged(viewer, oldInput, newInput);
	}

//	@Override
//	protected IWorkbenchAdapter getAdapter(Object element) {
//		// TODO Auto-generated method stub
//		return super.getAdapter(element);
//	}

	@Override
	public Object[] getChildren(Object element) {
		// TODO Auto-generated method stub
		System.out.println("SON QUA, PORCO DIO col " + element);
		if (element instanceof IWorkspaceRoot) {
			// here
		}
		return super.getChildren(element);
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		System.out.println("SON QUA, DIO PIERO col " + element);
		return super.getParent(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		System.out.println("SON QUA, ZOCCOLO DIO col " + element);
		return super.hasChildren(element);
	}

}
