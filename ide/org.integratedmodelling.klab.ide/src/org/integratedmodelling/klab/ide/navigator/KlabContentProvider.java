package org.integratedmodelling.klab.ide.navigator;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.model.WorkbenchContentProvider;

public class KlabContentProvider extends WorkbenchContentProvider {

  public KlabContentProvider() {
    // TODO Auto-generated constructor stub
  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    // TODO Auto-generated method stub
    super.inputChanged(viewer, oldInput, newInput);
  }

  @Override
  protected IWorkbenchAdapter getAdapter(Object element) {
    // TODO Auto-generated method stub
    return super.getAdapter(element);
  }

  @Override
  public Object[] getChildren(Object element) {
    // TODO Auto-generated method stub
    if (element instanceof IWorkspaceRoot) {
      // here
      System.out.println("SON QUA, PO' DIO");
    }
    return super.getChildren(element);
  }

  @Override
  public Object getParent(Object element) {
    // TODO Auto-generated method stub
    return super.getParent(element);
  }

  @Override
  public boolean hasChildren(Object element) {
    // TODO Auto-generated method stub
    return super.hasChildren(element);
  }

}
