package org.integratedmodelling.klab.ide.navigator.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

public class KlabNavigator {

	@PostConstruct
	public void createPartControl(Composite parent) {
		
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new TreeColumnLayout());
		
		TreeViewer treeViewer = new TreeViewer(composite, SWT.NONE);

		Tree tree = treeViewer.getTree();
		tree.setHeaderVisible(false);
		tree.setLinesVisible(false);
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new ViewerLabelProvider());
		
		System.out.println("Enter in SampleE4View postConstruct");
		treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
	}

	@Focus
	public void setFocus() {
//		myLabelInView.setFocus();

	}

	/**
	 * This method is kept for E3 compatiblity. You can remove it if you do not
	 * mix E3 and E4 code. <br/>
	 * With E4 code you will set directly the selection in ESelectionService and
	 * you do not receive a ISelection
	 * 
	 * @param s
	 *            the selection received from JFace (E3 mode)
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) ISelection s) {
		if (s==null || s.isEmpty())
			return;

		if (s instanceof IStructuredSelection) {
			IStructuredSelection iss = (IStructuredSelection) s;
			if (iss.size() == 1)
				setSelection(iss.getFirstElement());
			else
				setSelection(iss.toArray());
		}
	}

	/**
	 * This method manages the selection of your current object. In this example
	 * we listen to a single Object (even the ISelection already captured in E3
	 * mode). <br/>
	 * You should change the parameter type of your received Object to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current object received
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object o) {

		System.out.println("ZELECTION " + o);
		
		// Remove the 2 following lines in pure E4 mode, keep them in mixed mode
		if (o instanceof ISelection) // Already captured
			return;

		// Test if label exists (inject methods are called before PostConstruct)
//		if (myLabelInView != null)
//			myLabelInView.setText("Current single selection class is : " + o.getClass());
	}

	/**
	 * This method manages the multiple selection of your current objects. <br/>
	 * You should change the parameter type of your array of Objects to manage
	 * your specific selection
	 * 
	 * @param o
	 *            : the current array of objects received in case of multiple selection
	 */
	@Inject
	@Optional
	public void setSelection(@Named(IServiceConstants.ACTIVE_SELECTION) Object[] selectedObjects) {

		// Test if label exists (inject methods are called before PostConstruct)
//		if (myLabelInView != null)
//			myLabelInView.setText("This is a multiple selection of " + selectedObjects.length + " objects");
	}
}
