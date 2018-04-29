package org.integratedmodelling.kim.ui.navigator;

import org.eclipse.xtext.ui.editor.outline.impl.OutlineNodeContentProvider;
import org.eclipse.xtext.ui.editor.outline.impl.OutlineNodeFactory;

import com.google.inject.Inject;

// for later
public class KimContentProvider extends OutlineNodeContentProvider {

	@Inject
	OutlineNodeFactory nodeFactory;
	
	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		return super.getChildren(parentElement);
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
