package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.ide.ui.WorldWidget;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Display;

public class ResourceEditor extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.ResourceEditor";
	private Label urnLabel;
	private Composite mapHolder;
	
//	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public ResourceEditor() {
	}
	
	public void loadResource(ResourceReference resource) {
		// TODO
		urnLabel.setText(resource.getUrn());
		Geometry geometry = Geometry.create(resource.getGeometry());
		WorldWidget worldWidget = new WorldWidget(geometry, mapHolder, SWT.NONE);
		worldWidget.setLayout(new FillLayout());
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
		{
			Group grpGeometry = new Group(container, SWT.NONE);
			grpGeometry.setLayout(new GridLayout(2, false));
			grpGeometry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			grpGeometry.setText("Geometry");
			
			mapHolder = new Composite(grpGeometry, SWT.NONE);
			GridData gd_mapHolder = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_mapHolder.heightHint = 200;
			gd_mapHolder.widthHint = 360;
			mapHolder.setLayoutData(gd_mapHolder);
			
			Composite composite_1 = new Composite(grpGeometry, SWT.NONE);
			composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
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
