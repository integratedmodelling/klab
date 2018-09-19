package org.integratedmodelling.klab.ide.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.ui.ResourceEditingSupport;
import org.integratedmodelling.klab.ide.ui.WorldWidget;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.ServicePrototype.Argument;
import org.integratedmodelling.klab.utils.Pair;

public class ResourceEditor extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.ResourceEditor";

	private Label urnLabel;
	private Composite mapHolder;
	private Label geometryDefinition;
	private Label localName;
	private Group grpAdapterData;

	private WorldWidget worldWidget;
	private Text unpublishableReason;
	private Label labelWhy;
	private Table table;
	private Table table_1;
	
	private Pair<String, String> parameterEdit = null;
	
	private ResourceReference resource;
	private ResourceAdapterReference adapter;
	private TableViewer attributeViewer;
	private TableViewer adapterPropertyViewer;
	private TableViewerColumn tableViewerColumn_3;
	private TableViewerColumn propertyNameColumn;
	private TableViewerColumn propertyValueColumn;

	class AttributeContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection) {
				return ((Collection<?>) inputElement).toArray();
			}
			return new Object[] {};
		}
	}

	class AttributeLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (element instanceof Attribute) {
			return columnIndex == 0
					? ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/property.gif")
					: null;
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof Attribute) {
				switch(columnIndex) {
				case 0:
					return ((Attribute)element).getName();
				case 1:
					return ((Attribute)element).getType() == null ? "NULL!" : ((Attribute)element).getType().name();
				case 2:
					// TODO
					return "";
				}
			}
			return null;
		}
	}

	class PropertyContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Map) {
				List<Pair<String, String>> ret = new ArrayList<>();
				for (Entry<?,?> entry : ((Map<?,?>)inputElement).entrySet()) {
					ret.add(new Pair<>(entry.getKey().toString(), entry.getValue().toString()));
				}
				if (parameterEdit != null) {
					ret.add(parameterEdit);
				}
				return ret.toArray();
			}
			return new Object[] {};
		}

	}

	class PropertyLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return columnIndex == 0
					? ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/property.gif")
					: null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof Pair) {
				switch(columnIndex) {
				case 0:
					return (String)((Pair<?,?>)element).getFirst();
				case 1:
					// TODO type
					return "TYPE";
				case 2:
					return (String)((Pair<?,?>)element).getSecond();
				}
			}
			return null;
		}
	}
	
	
	public ResourceEditor() {
	}

	public void loadResource(ResourceReference resource) {
		
		this.resource = resource;
		this.adapter = Activator.klab().getResourceAdapter(resource.getAdapterType());
		this.parameterEdit = null;
		this.urnLabel.setText(resource.getUrn());
		this.geometryDefinition.setText(resource.getGeometry());
		this.localName.setText(resource.getLocalName());
		this.grpAdapterData.setText(resource.getAdapterType().toUpperCase() + " adapter data");
		this.worldWidget.setExtent(resource.getSpatialExtent());
		this.adapterPropertyViewer.setInput(resource.getParameters());
		this.attributeViewer.setInput(resource.getAttributes());
		propertyNameColumn.setEditingSupport(new ResourceEditingSupport.PropertySupport(adapterPropertyViewer, getAdapterProperties()));

	}

	private String[] getAdapterProperties() {
		List<String> ret = new ArrayList<>();
		if (this.adapter != null) {
			for (Argument argument : adapter.getParameters().getArguments()) {
				ret.add(argument.getName());
			}
		}
		return ret.toArray(new String[ret.size()]);
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Composite composite_1_1 = new Composite(parent, SWT.NONE);
		composite_1_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_1_1.setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(composite_1_1, SWT.NONE);
		lblNewLabel.setImage(
				ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/logo_white_64.jpg"));

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
			this.worldWidget = new WorldWidget(mapHolder, SWT.NONE);

			Composite composite_1 = new Composite(grpGeometry, SWT.NONE);
			composite_1.setToolTipText("Help for operations appear here");
			composite_1.setLayout(new GridLayout(3, false));
			composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

			Button btnCheckButton = new Button(composite_1, SWT.CHECK);
			btnCheckButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					unpublishableReason.setEnabled(!btnCheckButton.getSelection());
					labelWhy.setEnabled(!btnCheckButton.getSelection());
				}
			});
			btnCheckButton.setSelection(true);
			btnCheckButton.setText("Publishable");

			labelWhy = new Label(composite_1, SWT.NONE);
			labelWhy.setEnabled(false);
			labelWhy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			labelWhy.setText("Why not:");

			unpublishableReason = new Text(composite_1, SWT.BORDER);
			unpublishableReason.setEnabled(false);
			unpublishableReason.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			Group grpTime = new Group(composite_1, SWT.NONE);
			grpTime.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
			grpTime.setText("Time");

			Group grpAttributes = new Group(composite_1, SWT.NONE);
			grpAttributes.setLayout(new GridLayout(1, false));
			grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			grpAttributes.setText("Attributes");

			Composite composite = new Composite(grpAttributes, SWT.NONE);
			composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			TableColumnLayout tcl_composite = new TableColumnLayout();
			composite.setLayout(tcl_composite);

			attributeViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
			table = attributeViewer.getTable();
			table.setLinesVisible(true);
			
			tableViewerColumn_3 = new TableViewerColumn(attributeViewer, SWT.NONE);
			TableColumn attributeName = tableViewerColumn_3.getColumn();
			tcl_composite.setColumnData(attributeName, new ColumnPixelData(150, true, true));
			attributeName.setText("New Column");
			
			TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(attributeViewer, SWT.NONE);
			TableColumn attributeType = tableViewerColumn_1.getColumn();
			tcl_composite.setColumnData(attributeType, new ColumnPixelData(150, true, true));
			attributeType.setText("New Column");
			
			TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(attributeViewer, SWT.NONE);
			TableColumn attributeExample = tableViewerColumn_2.getColumn();
			tcl_composite.setColumnData(attributeExample, new ColumnPixelData(150, true, true));
			attributeExample.setText("New Column");
			attributeViewer.setLabelProvider(new AttributeLabelProvider());
			attributeViewer.setContentProvider(new AttributeContentProvider());

			Composite composite_3 = new Composite(grpAttributes, SWT.NONE);
			composite_3.setLayout(new GridLayout(4, false));
			composite_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			Label lblOperations = new Label(composite_3, SWT.NONE);
			lblOperations.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblOperations.setText("Operations:");

			Combo combo = new Combo(composite_3, SWT.READ_ONLY);
			combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			Button btnNewButton_3 = new Button(composite_3, SWT.NONE);
			btnNewButton_3.setText("Execute");

			Label lblNewLabel_4 = new Label(composite_3, SWT.NONE);
			lblNewLabel_4
					.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));
			new Label(composite_1, SWT.NONE);
			new Label(composite_1, SWT.NONE);
			new Label(composite_1, SWT.NONE);
			{
				geometryDefinition = new Label(grpGeometry, SWT.NONE);
				geometryDefinition.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
				geometryDefinition.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC));
				geometryDefinition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			}
		}

		grpAdapterData = new Group(container, SWT.NONE);
		grpAdapterData.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAdapterData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAdapterData.setText("Adapter parameters");
		
		adapterPropertyViewer = new TableViewer(grpAdapterData, SWT.BORDER | SWT.FULL_SELECTION);
		table_1 = adapterPropertyViewer.getTable();
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		
		propertyNameColumn = new TableViewerColumn(adapterPropertyViewer, SWT.NONE);
		TableColumn propertyColumn = propertyNameColumn.getColumn();
		propertyColumn.setWidth(180);
		propertyColumn.setText("Adapter property");	
		propertyNameColumn.setEditingSupport(new ResourceEditingSupport.PropertySupport(adapterPropertyViewer, new String[] {}));
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(adapterPropertyViewer, SWT.NONE);
		TableColumn typeColumn = tableViewerColumn_1.getColumn();
		typeColumn.setWidth(100);
		typeColumn.setText("Type");
		
		propertyValueColumn = new TableViewerColumn(adapterPropertyViewer, SWT.NONE);
		TableColumn valueColumn = propertyValueColumn.getColumn();
		valueColumn.setWidth(400);
		valueColumn.setText("Value");
		propertyValueColumn.setEditingSupport(new ResourceEditingSupport.ValueSupport(adapterPropertyViewer));
		
		Menu menu = new Menu(table_1);
		table_1.setMenu(menu);
		
		MenuItem addProperty = new MenuItem(menu, SWT.NONE);
		addProperty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO add parameter for editing
				parameterEdit = new Pair<>("", "");
				adapterPropertyViewer.setInput(resource.getParameters());
			}
		});
		addProperty.setText("Add new parameter");
		
		MenuItem deleteProperty = new MenuItem(menu, SWT.NONE);
		deleteProperty.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO delete current selection
			}
		});
		deleteProperty.setText("Delete parameter");
		adapterPropertyViewer.setLabelProvider(new PropertyLabelProvider());
		adapterPropertyViewer.setContentProvider(new PropertyContentProvider());

		TabItem tbtmProvenanceData = new TabItem(tabFolder, SWT.NONE);
		tbtmProvenanceData.setText("Documentation");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmProvenanceData.setControl(composite_1);

		TabItem tbtmPermissions = new TabItem(tabFolder, SWT.NONE);
		tbtmPermissions.setText("Permissions");

		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmPermissions.setControl(composite_3);

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
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(ResourceEditor.this);
			}
		});
		btnNewButton_2.setLayoutData(new RowData(90, SWT.DEFAULT));
		btnNewButton_2.setText("Cancel");
		// toolkit.paintBordersFor(container);

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	public void dispose() {
		// toolkit.dispose();
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
