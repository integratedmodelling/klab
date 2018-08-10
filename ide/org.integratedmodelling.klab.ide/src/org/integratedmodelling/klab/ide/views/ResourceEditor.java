package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.rest.ResourceReference;

public class ResourceEditor extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.ResourceEditor";
	private Label urnLabel;
	
//	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public ResourceEditor() {
	}
	
	public void loadResource(ResourceReference resource) {
		// TODO
		urnLabel.setText(resource.getUrn());
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		{
			Group grpResourceData = new Group(container, SWT.NONE);
			grpResourceData.setLayout(new GridLayout(2, false));
			grpResourceData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			grpResourceData.setText("Resource data");
			{
				Label lblUrn = new Label(grpResourceData, SWT.NONE);
				lblUrn.setText("Urn");
			}
			{
				urnLabel = new Label(grpResourceData, SWT.NONE);
				urnLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			}
		}
//		toolkit.paintBordersFor(container);

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	public void dispose() {
//		toolkit.dispose();
		super.dispose();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager manager = getViewSite().getActionBars().getMenuManager();
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

}
