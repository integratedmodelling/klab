package org.integratedmodelling.klab.ide.views;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.api.data.CRUDOperation;
import org.integratedmodelling.klab.api.data.IResource.Attribute;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.ui.TimeEditor;
import org.integratedmodelling.klab.ide.ui.WorldWidget;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.ide.utils.StringUtils;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.ServicePrototype;
import org.integratedmodelling.klab.rest.ServicePrototype.Argument;
import org.integratedmodelling.klab.utils.UrlValidator;
import org.integratedmodelling.klab.utils.Utils;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.custom.SashForm;

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
	private Table propertyTable;
	private Map<String, String> values = new LinkedHashMap<>();
	private Map<String, String> metadata = new LinkedHashMap<>();
	private ResourceReference resource;
	private ResourceAdapterReference adapter;
	private TableViewer attributeViewer;
	private TableViewer adapterPropertyViewer;
	private TableViewerColumn tableViewerColumn_3;
	private TableViewerColumn propertyNameColumn;
	private TableViewerColumn propertyValueColumn;
	private Text title;
	private Text urlDoi;
	private Text keywords;
	private Button isPublishable;
	private boolean dirty = false;
	private Button saveButton;
	private Button publishButton;
	private Button cancelButton;
	private StyledText description;
	private StyledText originatingInstitution;
	private StyledText authors;
	private Combo theme;
	private Combo geoRegion;
	private StyledText references;
	private StyledText notes;

	public static class AttributeContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection) {
				return ((Collection<?>) inputElement).toArray();
			}
			return new Object[] {};
		}
	}

	public static class AttributeLabelProvider extends LabelProvider implements ITableLabelProvider {

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
				switch (columnIndex) {
				case 0:
					return ((Attribute) element).getName();
				case 1:
					return ((Attribute) element).getType() == null ? "NULL!" : ((Attribute) element).getType().name();
				case 2:
					// TODO
					return "";
				}
			}
			return null;
		}
	}

	public class ValueSupport extends EditingSupport {

		private final CellEditor editor;

		public ValueSupport(TableViewer viewer) {
			super(viewer);
			this.editor = new TextCellEditor(viewer.getTable());
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(Object element) {
			// if (element instanceof Pair && Activator.engineMonitor().isRunning() &&
			// adapter != null) {
			// String parameter = ((Pair<?, ?>) element).getFirst().toString();
			// Argument arg = adapter.getParameters().findArgument(parameter);
			// if (arg == null || arg.isFinal()) {
			// return false;
			// }
			// return true;
			// }
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			Object ret = null;
			if (element instanceof ServicePrototype.Argument) {
				ret = values.get(((ServicePrototype.Argument) element).getName());
			}
			return ret == null ? "" : ret.toString();
		}

		@Override
		protected void setValue(Object element, Object value) {

			if (element instanceof ServicePrototype.Argument) {

				boolean changed = true;

				String current = values.get(((ServicePrototype.Argument) element).getName());
				if (current != null && current.trim().isEmpty()) {
					current = null;
				}
				if (value != null && value.toString().isEmpty()) {
					value = null;
				}
				if ((value != null && current != null && value.equals(current)) || (value == null && current == null)) {
					changed = false;
				}

				if (changed) {
					setErrorMessage(null);
					if (value != null) {
						if (!Utils.validateAs(value, ((ServicePrototype.Argument) element).getType())) {
							setErrorMessage("'" + value + "' is not a suitable value for type "
									+ ((ServicePrototype.Argument) element).getType().name().toLowerCase());
						}
						if (((ServicePrototype.Argument) element).getName().endsWith("Url")) {
							if (!UrlValidator.getInstance().isValid(value.toString())) {
								setErrorMessage("'" + value + "' is not a valid URL");
							}
						}
					}
					if (value == null) {
						values.remove(((ServicePrototype.Argument) element).getName());
					} else {
						values.put(((ServicePrototype.Argument) element).getName(), value.toString());
					}
					setDirty(true);
				}
			}
			getViewer().update(element, null);
		}

	}

	class PropertyContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ResourceAdapterReference) {
				return ((ResourceAdapterReference) inputElement).getParameters().getArguments().toArray();
			}
			return new Object[] {};
		}

	}

	class PropertyLabelProvider extends LabelProvider implements ITableLabelProvider, IColorProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return columnIndex == 0
					? ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/property.gif")
					: null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ServicePrototype.Argument) {
				ServicePrototype.Argument arg = (ServicePrototype.Argument) element;
				switch (columnIndex) {
				case 0:
					return arg.getName();
				case 1:
					return StringUtils.capitalize(arg.getType().name().toLowerCase());
				case 2:
					Object ret = values.get(((ServicePrototype.Argument) element).getName());
					return ret == null ? null : ret.toString();
				}
			}

			return null;
		}

		@Override
		public Color getForeground(Object element) {
			if (element instanceof ServicePrototype.Argument && (((ServicePrototype.Argument) element).isFinal()
					|| ((ServicePrototype.Argument) element).isRequired())) {
				return values.containsKey(((ServicePrototype.Argument) element).getName())
						&& !values.get(((ServicePrototype.Argument) element).getName()).trim().isEmpty()
								? SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN)
								: SWTResourceManager.getColor(SWT.COLOR_RED);
			}
			return null;
		}

		@Override
		public Color getBackground(Object element) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public ResourceEditor() {
	}

	public void setErrorMessage(String string) {
		this.isPublishable.setSelection(false);
		this.unpublishableReason.setText(string == null ? "" : string);
	}

	public void loadResource(ResourceReference resource) {

		this.resource = resource;
		this.adapter = Activator.klab().getResourceAdapter(resource.getAdapterType());
		this.values.clear();
		if (adapter != null) {
			for (Argument argument : adapter.getParameters().getArguments()) {
				if (resource.getParameters().containsKey(argument.getName())) {
					this.values.put(argument.getName(), resource.getParameters().get(argument.getName()));
				}
			}
		}
		this.metadata.clear();
		this.metadata.putAll(resource.getMetadata());
		this.urnLabel.setText(resource.getUrn());
		this.urnLabel.setForeground(hasErrors(resource) ? SWTResourceManager.getColor(SWT.COLOR_RED)
				: SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		this.geometryDefinition.setText(resource.getGeometry() == null ? "" : resource.getGeometry());
		this.localName.setText(resource.getLocalName());
		this.grpAdapterData.setText(resource.getAdapterType().toUpperCase() + " adapter data");
		this.worldWidget.setExtent(resource.getSpatialExtent());
		this.adapterPropertyViewer.setInput(this.adapter);
		this.attributeViewer.setInput(resource.getAttributes());
		this.title.setText(this.metadata.containsKey(IMetadata.DC_TITLE) ? this.metadata.get(IMetadata.DC_TITLE) : "");
		this.keywords
				.setText(this.metadata.containsKey(IMetadata.IM_KEYWORDS) ? this.metadata.get(IMetadata.IM_KEYWORDS) : "");
		this.urlDoi.setText(this.metadata.containsKey(IMetadata.DC_URL) ? this.metadata.get(IMetadata.DC_URL) : "");
		this.description.setText(this.metadata.containsKey(IMetadata.DC_COMMENT) ? this.metadata.get(IMetadata.DC_COMMENT) : "");
		this.originatingInstitution.setText(this.metadata.containsKey(IMetadata.DC_ORIGINATOR) ? this.metadata.get(IMetadata.DC_ORIGINATOR) : "");
		this.authors.setText(this.metadata.containsKey(IMetadata.DC_CREATOR) ? this.metadata.get(IMetadata.DC_CREATOR) : "");
		this.theme.setText(this.metadata.containsKey(IMetadata.IM_THEMATIC_AREA) ? this.metadata.get(IMetadata.IM_THEMATIC_AREA) : "");
		this.geoRegion.setText(this.metadata.containsKey(IMetadata.IM_GEOGRAPHIC_AREA) ? this.metadata.get(IMetadata.IM_GEOGRAPHIC_AREA) : "");
		this.references.setText(this.metadata.containsKey(IMetadata.DC_SOURCE) ? this.metadata.get(IMetadata.DC_SOURCE) : "");
		this.notes.setText(this.metadata.containsKey(IMetadata.IM_NOTES) ? this.metadata.get(IMetadata.IM_NOTES) : "");
	}

	private boolean hasErrors(ResourceReference resource) {
		this.unpublishableReason.setText("");
		this.isPublishable.setSelection(true);
		for (Notification not : resource.getNotifications()) {
			if (not.getLevel().equals(Level.SEVERE.getName())) {
				this.unpublishableReason.setText(not.getMessage());
				this.isPublishable.setSelection(false);
				return true;
			}
		}
		return false;
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

		SashForm sashForm = new SashForm(container, SWT.VERTICAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		{
			Group grpGeometry = new Group(sashForm, SWT.NONE);
			// grpGeometry.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
			grpGeometry.setLayout(new GridLayout(2, false));
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

			isPublishable = new Button(composite_1, SWT.CHECK);
			isPublishable.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					unpublishableReason.setEnabled(!isPublishable.getSelection());
					labelWhy.setEnabled(!isPublishable.getSelection());
				}
			});
			isPublishable.setSelection(true);
			isPublishable.setText("Publishable");

			labelWhy = new Label(composite_1, SWT.NONE);
			labelWhy.setEnabled(false);
			labelWhy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			labelWhy.setText("Why not:");

			unpublishableReason = new Text(composite_1, SWT.BORDER);
			unpublishableReason.setEnabled(false);
			unpublishableReason.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

			Group grpTime = new Group(composite_1, SWT.NONE);
			grpTime.setLayout(new GridLayout(1, false));
			grpTime.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
			grpTime.setText("Time");

			TimeEditor timeEditor = new TimeEditor(grpTime, SWT.NONE);
			timeEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

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

			TableViewerColumn tableViewerColumn_1_1 = new TableViewerColumn(attributeViewer, SWT.NONE);
			TableColumn attributeType = tableViewerColumn_1_1.getColumn();
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
			{
				geometryDefinition = new Label(grpGeometry, SWT.NONE);
				geometryDefinition.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
				geometryDefinition.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC));
				geometryDefinition.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			}
		}

		grpAdapterData = new Group(sashForm, SWT.NONE);
		grpAdapterData.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAdapterData.setText("Adapter parameters");

		adapterPropertyViewer = new TableViewer(grpAdapterData, SWT.BORDER | SWT.FULL_SELECTION);
		propertyTable = adapterPropertyViewer.getTable();
		propertyTable.setLinesVisible(true);
		propertyTable.setHeaderVisible(true);

		propertyNameColumn = new TableViewerColumn(adapterPropertyViewer, SWT.NONE);
		TableColumn propertyColumn = propertyNameColumn.getColumn();
		propertyColumn.setWidth(180);
		propertyColumn.setText("Adapter property");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(adapterPropertyViewer, SWT.NONE);
		TableColumn typeColumn = tableViewerColumn_1.getColumn();
		typeColumn.setWidth(100);
		typeColumn.setText("Type");

		propertyValueColumn = new TableViewerColumn(adapterPropertyViewer, SWT.NONE);
		TableColumn valueColumn = propertyValueColumn.getColumn();
		valueColumn.setWidth(400);
		valueColumn.setText("Value");
		propertyValueColumn.setEditingSupport(new ValueSupport(adapterPropertyViewer));

		Menu menu = new Menu(propertyTable);
		propertyTable.setMenu(menu);
		// MenuItem addProperty = new MenuItem(menu, SWT.NONE);
		// addProperty.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// // TODO add parameter for editing
		// parameterEdit = new Pair<>("", "");
		// adapterPropertyViewer.setInput(resource.getParameters());
		// }
		// });
		// addProperty.setText("Add new parameter");
		//
		// MenuItem deleteProperty = new MenuItem(menu, SWT.NONE);
		// deleteProperty.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// // TODO delete current selection
		// }
		// });
		// deleteProperty.setText("Delete parameter");
		adapterPropertyViewer.setLabelProvider(new PropertyLabelProvider());
		adapterPropertyViewer.setContentProvider(new PropertyContentProvider());
		sashForm.setWeights(new int[] { 1, 1 });

		TabItem tbtmProvenanceData = new TabItem(tabFolder, SWT.NONE);
		tbtmProvenanceData.setText("Documentation");
		GridData gd_styledText1 = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_styledText1.heightHint = 140;

		TabItem tbtmPermissions = new TabItem(tabFolder, SWT.NONE);
		tbtmPermissions.setText("Permissions");

		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmPermissions.setControl(composite_3);

//		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
//		tabItem.setText("New Item");

		ScrolledComposite scrolledComposite = new ScrolledComposite(tabFolder,
				SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tbtmProvenanceData.setControl(scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));

		Label lblTitle = new Label(composite_1, SWT.NONE);
		lblTitle.setBounds(0, 0, 55, 15);
		lblTitle.setText("Title");

		title = new Text(composite_1, SWT.BORDER);
		title.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				metadata.put(IMetadata.DC_TITLE, title.getText());
				setDirty(true);
			}
		});
		title.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		title.setBounds(0, 0, 76, 21);

		Label lblDescriptionmarkdownAccepted = new Label(composite_1, SWT.NONE);
		lblDescriptionmarkdownAccepted.setText("Description (Markdown accepted)");

		description = new StyledText(composite_1, SWT.BORDER);
		description.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				metadata.put(IMetadata.DC_COMMENT, description.getText());
				setDirty(true);
			}
		});
		GridData gd_description = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_description.heightHint = 80;
		description.setLayoutData(gd_description);

		Label lblOriginators = new Label(composite_1, SWT.NONE);
		lblOriginators.setText("Originating institution");

		originatingInstitution = new StyledText(composite_1, SWT.BORDER);
		originatingInstitution.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				metadata.put(IMetadata.DC_ORIGINATOR, originatingInstitution.getText());
				setDirty(true);
			}
		});
		GridData gd_originatingInstitution = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_originatingInstitution.heightHint = 40;
		originatingInstitution.setLayoutData(gd_originatingInstitution);

		Label lblUrl = new Label(composite_1, SWT.NONE);
		lblUrl.setText("URL/DOI");

		urlDoi = new Text(composite_1, SWT.BORDER);
		urlDoi.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				metadata.put(IMetadata.DC_URL, urlDoi.getText());
				setDirty(true);
			}
		});
		urlDoi.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblAuthors = new Label(composite_1, SWT.NONE);
		lblAuthors.setText("Authors (one per line)");

		authors = new StyledText(composite_1, SWT.BORDER);
		authors.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				metadata.put(IMetadata.DC_CREATOR, authors.getText());
				setDirty(true);
			}
		});
		GridData gd_authors = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_authors.heightHint = 40;
		authors.setLayoutData(gd_authors);

		Group grpThematicLocators = new Group(composite_1, SWT.NONE);
		grpThematicLocators.setLayout(new GridLayout(5, false));
		grpThematicLocators.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		grpThematicLocators.setText("Thematic locators");

		Label lblTheme = new Label(grpThematicLocators, SWT.NONE);
		lblTheme.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTheme.setText("Theme");

		theme = new Combo(grpThematicLocators, SWT.NONE);
		theme.setItems(new String[] { "Agriculture", "Behavior and social", "Biology", "Chemistry", "Conservation",
				"Demography", "Earth", "Ecology", "Economics", "Engineering", "Geography", "Geology", "Hydrology",
				"Infrastructure", "Landcover", "Physical and climatic", "Policy", "Socio-ecological", "Soil" });
		theme.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		theme.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				metadata.put(IMetadata.IM_THEMATIC_AREA, theme.getText());
				setDirty(true);
			}
		});

		Label lblGeographicRegion = new Label(grpThematicLocators, SWT.NONE);
		lblGeographicRegion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblGeographicRegion.setText("Geographical region");

		geoRegion = new Combo(grpThematicLocators, SWT.NONE);
		geoRegion.setItems(new String[] { "Non-spatial", "Global" });
		geoRegion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		geoRegion.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				metadata.put(IMetadata.IM_GEOGRAPHIC_AREA, geoRegion.getText());
				setDirty(true);
			}
		});

		Label lblNewLabel_5 = new Label(grpThematicLocators, SWT.NONE);
		lblNewLabel_5.setToolTipText(
				"These fields are open for new entries, but please endeavor to reuse existing keywords.\n"
						+ "In the geographic location, please start at the continental level and if more specific tags are needed,\n"
						+ "separate them with forward slashes: e.g. Europe/France/Gascogne");
		lblNewLabel_5.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));

		Label lblKeywords = new Label(composite_1, SWT.NONE);
		lblKeywords.setText("Keywords");

		keywords = new Text(composite_1, SWT.BORDER);
		keywords.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				metadata.put(IMetadata.IM_KEYWORDS, keywords.getText());
				setDirty(true);
			}
		});
		keywords.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite_1, SWT.NONE);

		Label lblReferences = new Label(composite_1, SWT.NONE);
		lblReferences.setText("References");

		references = new StyledText(composite_1, SWT.BORDER);
		references.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				metadata.put(IMetadata.DC_SOURCE, references.getText());
				setDirty(true);
			}
		});
		GridData gd_references = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
		gd_references.heightHint = 40;
		references.setLayoutData(gd_references);

		Label lblNotes = new Label(composite_1, SWT.NONE);
		lblNotes.setText("Notes");

		notes = new StyledText(composite_1, SWT.BORDER);
		notes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setContent(composite_1);
		scrolledComposite.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		notes.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                metadata.put(IMetadata.IM_NOTES, notes.getText());
                setDirty(true);
            }
        });
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		RowLayout rl_composite = new RowLayout(SWT.HORIZONTAL);
		rl_composite.wrap = false;
		composite.setLayout(rl_composite);

		publishButton = new Button(composite, SWT.NONE);
		publishButton.setLayoutData(new RowData(90, SWT.DEFAULT));
		publishButton.setGrayed(true);
		publishButton.setText("Publish...");
		publishButton.setEnabled(false);

		saveButton = new Button(composite, SWT.NONE);
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				save();
			}
		});
		saveButton.setLayoutData(new RowData(90, SWT.DEFAULT));
		saveButton.setText("Save");
		saveButton.setEnabled(false);

		cancelButton = new Button(composite, SWT.NONE);
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (!dirty || Eclipse.INSTANCE.confirm("Abandon changes?")) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(ResourceEditor.this);
				}
			}
		});
		cancelButton.setLayoutData(new RowData(90, SWT.DEFAULT));
		cancelButton.setText("Cancel");

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	private void save() {

		ResourceCRUDRequest request = new ResourceCRUDRequest();
		request.setOperation(CRUDOperation.UPDATE);
		request.getParameters().putAll(values);
		request.getMetadata().putAll(metadata);
		request.getResourceUrns().add(resource.getUrn());

		Activator.post(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.UpdateResource, request);

		setDirty(false);
	}

	protected void setDirty(boolean b) {
		if (b) {
			if (!getTitle().startsWith("*")) {
				setPartName("* " + getTitle());
			}
		} else {
			if (getTitle().startsWith("*")) {
				setPartName(getTitle().substring(2));
			}
		}
		saveButton.setEnabled(b && Activator.engineMonitor() != null && Activator.engineMonitor().isRunning());
		dirty = b;
	}

	public void dispose() {
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
