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
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.wb.swt.ResourceManager;

public class ResourceEditor extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.ResourceEditor";
	
	private Label urnLabel;
	private Composite mapHolder;
	private Label geometryDefinition;
	private Label localName;
	private Group grpAdapterData;
	
//	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public ResourceEditor() {
	}
	
	public void loadResource(ResourceReference resource) {
		// TODO
		urnLabel.setText(resource.getUrn());
        geometryDefinition.setText(resource.getGeometry());
        localName.setText(resource.getLocalName());
        grpAdapterData.setText(resource.getAdapterType().toUpperCase() + " adapter data");
		Geometry geometry = Geometry.create(resource.getGeometry());
		WorldWidget worldWidget = new WorldWidget(geometry, mapHolder, SWT.NONE);
//		worldWidget.setLayout(new FillLayout());
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		Composite composite_1_1 = new Composite(parent, SWT.NONE);
		composite_1_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_1_1.setLayout(new GridLayout(2, false));
		
		Label lblNewLabel = new Label(composite_1_1, SWT.NONE);
		lblNewLabel.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/logo_white_64.jpg"));
		
		Composite composite_2 = new Composite(composite_1_1, SWT.NONE);
		RowLayout rl_composite_2 = new RowLayout(SWT.VERTICAL);
		rl_composite_2.fill = true;
		composite_2.setLayout(rl_composite_2);
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(composite_2, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel_1.setText("k.LAB Resource Editor");
		
		Label lblNewLabel_2 = new Label(composite_2, SWT.NONE);
		lblNewLabel_2.setText("Define all the properties of a resource, its geometry and its provenance information");
		
		Label lblNewLabel_3 = new Label(composite_2, SWT.NONE);
		lblNewLabel_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblNewLabel_3.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.BOLD));
		lblNewLabel_3.setText("LOCAL, UNPUBLISHED");
		
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtmResourceData = new TabItem(tabFolder, SWT.NONE);
		tbtmResourceData.setText("Resource data");
		Composite container = new Composite(tabFolder, SWT.NONE);
		tbtmResourceData.setControl(container);
		container.setLayout(new GridLayout(1, false));
		{
			Group grpResourceData = new Group(container, SWT.NONE);
			grpResourceData.setLayout(new GridLayout(4, false));
			grpResourceData.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			grpResourceData.setText("Resource data");
			{
				Label lblUrn = new Label(grpResourceData, SWT.NONE);
				lblUrn.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
				lblUrn.setText("Urn");
			}
			{
				urnLabel = new Label(grpResourceData, SWT.NONE);
				urnLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
				urnLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
				urnLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
				urnLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			}
			
			Label lblLocalName = new Label(grpResourceData, SWT.NONE);
			lblLocalName.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
			lblLocalName.setText("Local name:");
			
			localName = new Label(grpResourceData, SWT.NONE);
			localName.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			localName.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
			localName.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
			localName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			Group grpGeometry = new Group(container, SWT.NONE);
			grpGeometry.setLayout(new GridLayout(2, false));
			grpGeometry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			grpGeometry.setText("Geometry");
			
			mapHolder = new Composite(grpGeometry, SWT.NONE);
			GridData gd_mapHolder = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
			gd_mapHolder.heightHint = 181;
			gd_mapHolder.widthHint = 360;
			mapHolder.setLayoutData(gd_mapHolder);
			
			Composite composite_1 = new Composite(grpGeometry, SWT.NONE);
			composite_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
			{
			    geometryDefinition = new Label(grpGeometry, SWT.NONE);
			    geometryDefinition.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
			    geometryDefinition.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC));
			    geometryDefinition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			}
		}
		
		grpAdapterData = new Group(container, SWT.NONE);
		grpAdapterData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAdapterData.setText("Adapter data");
		
		TabItem tbtmProvenanceData = new TabItem(tabFolder, SWT.NONE);
		tbtmProvenanceData.setText("Documentation");
		
		TabItem tbtmPermissions = new TabItem(tabFolder, SWT.NONE);
		tbtmPermissions.setText("Permissions");
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.wrap = false;
		composite.setLayout(rl_composite);
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.setLayoutData(new RowData(90, SWT.DEFAULT));
		btnNewButton.setGrayed(true);
		btnNewButton.setText("Publish...");
		
		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.setLayoutData(new RowData(90, SWT.DEFAULT));
		btnNewButton_1.setText("Save");
		
		Button btnNewButton_2 = new Button(composite, SWT.NONE);
		btnNewButton_2.setLayoutData(new RowData(90, SWT.DEFAULT));
		btnNewButton_2.setText("Cancel");
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
