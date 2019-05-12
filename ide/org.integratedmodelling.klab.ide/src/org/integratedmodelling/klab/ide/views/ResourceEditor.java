package org.integratedmodelling.klab.ide.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
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
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.rest.ServicePrototype.Argument;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.UrlValidator;
import org.integratedmodelling.klab.utils.Utils;

public class ResourceEditor extends ViewPart {

    public static final String       ID       = "org.integratedmodelling.klab.ide.views.ResourceEditor";

    private Label                    urnLabel;
    private Composite                mapHolder;
    private Label                    geometryDefinition;
    private Label                    localName;
    private Group                    grpAdapterData;

    private WorldWidget              worldWidget;
    private Text                     unpublishableReason;
    private Label                    labelWhy;
    private Table                    table;
    private Table                    dependencyTable;
    private Tree                     propertyTable;
    private Map<String, String>      values   = new LinkedHashMap<>();
    private Map<String, String>      metadata = new LinkedHashMap<>();
    private ResourceReference        resource;
    private ResourceAdapterReference adapter;
    private TableViewer              attributeViewer;
    private TableViewer              dependencyViewer;
    private TreeViewer               adapterPropertyViewer;
    private TableViewerColumn        tableViewerColumn_3;
    private TreeViewerColumn         propertyNameColumn;
    private TreeViewerColumn         propertyValueColumn;
    private Text                     title;
    private Text                     urlDoi;
    private Text                     keywords;
    private Button                   isPublishable;
    private boolean                  dirty    = false;
    private Button                   saveButton;
    private Button                   publishButton;
    private Button                   cancelButton;
    private StyledText               description;
    private StyledText               originatingInstitution;
    private StyledText               authors;
    private Combo                    theme;
    private Combo                    geoRegion;
    private StyledText               references;
    private StyledText               notes;

    private TableViewerColumn        tableViewerColumn_3D;
    private Label                    messageLabel;
    private Table                    outputTable;
    private TableViewer              outputViewer;

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
                        ? ResourceManager
                                .getPluginImage("org.integratedmodelling.klab.ide", "icons/property.gif")
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
                    return ((Attribute) element).getType() == null ? "NULL!"
                            : ((Attribute) element).getType().name();
                case 2:
                    // TODO
                    return "";
                }
            }
            return null;
        }
    }

    /**
     * These describe hierarchical parameters (of the type a.b.c) so that they can be
     * easily shown.
     * 
     * @author Ferd
     *
     */
    public class ResourceParameter {

        // Descriptor as per prototype. May be null. If null, type is VALUE.
        // Those with descriptors are shown first.
        Argument            descriptor;
        // fully qualified name of the parameter
        String              parameter;
        // value of the parameter
        String              value;
        // non-hierarchical name of the parameter (according to level)
        String              label;
        // if true, this is just a header node for the tree
        boolean             nonexisting = false;
        // parent if any
        ResourceParameter   parent;
        // children are cached here
        ResourceParameter[] children    = null;

        ResourceParameter(String parameter, String value, Argument descriptor) {
            this.parameter = this.label = parameter;
            this.value = value;
            this.descriptor = descriptor;
        }

        ResourceParameter(String parameter, String value, ResourceParameter parent) {
            this.parameter = parameter;
            this.label = Path.getLast(parameter, '.');
            this.value = value;
            this.parent = parent;
        }

        ResourceParameter[] getChildren() {
            if (this.children == null) {
                List<ResourceParameter> ret = new ArrayList<>();
                Set<String> added = new HashSet<>();
                for (String s : resource.getParameters().keySet()) {
                    if (s.startsWith(this.parameter + ".")) {
                        // one down
                        String rest = s.substring(this.parameter.length() + 1);
                        int npt = rest.indexOf('.');
                        if (npt >= 0) {
                            rest = rest.substring(0, npt);
                        }
                        String ps = this.parameter + "." + rest;
                        if (added.contains(ps)) {
                            continue;
                        }
                        String value = resource.getParameters().get(ps) != null
                                ? resource.getParameters().get(ps).toString()
                                : null;
                        ResourceParameter param = new ResourceParameter(ps, value, this);
                        if (!resource.getParameters().containsKey(ps)) {
                            param.nonexisting();
                        }
                        ret.add(param);
                        added.add(ps);
                    }
                }
                this.children = ret.toArray(new ResourceParameter[ret.size()]);
            }
            return this.children;
        }

        boolean hasChildren() {
            return getChildren().length > 0;
        }

        public ResourceParameter nonexisting() {
            this.nonexisting = true;
            return this;
        }

        public Object getParent() {
            return this.parent;
        }
    }

    private List<ResourceParameter> getParameters() {

        if (resource == null) {
            return new ArrayList<>();
        }

        Set<String> added = new HashSet<>();
        List<ResourceParameter> known = new ArrayList<>();
        List<ResourceParameter> other = new ArrayList<>();
        if (adapter != null) {
            for (Argument argument : adapter.getParameters().getArguments()) {
                Object value = resource.getParameters().get(argument.getName());
                known.add(new ResourceParameter(argument.getName(), value != null ? value.toString()
                        : null, argument));
                added.add(argument.getName());
            }
        }

        Set<String> toAdd = new HashSet<>();
        for (String pid : resource.getParameters().keySet()) {
            if (!pid.contains(".") && !added.contains(pid)) {
                Object value = resource.getParameters().get(pid);
                other.add(new ResourceParameter(pid, value != null ? value.toString()
                        : null, (Argument) null));
                added.add(pid);
            } else {
                toAdd.add(Path.getFirst(pid, "."));
            }
        }

        for (String toadd : toAdd) {
            if (!added.contains(toadd)) {
                other.add(new ResourceParameter(toadd, null, (Argument) null).nonexisting());
            }
        }

        Collections.sort(other, new Comparator<ResourceParameter>() {

            @Override
            public int compare(ResourceParameter o1, ResourceParameter o2) {
                return o1.parameter.compareTo(o2.parameter);
            }

        });
        known.addAll(other);

        return known;
    }

    class PropertyContentProvider implements ITreeContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof Collection) {
                return ((Collection<?>) inputElement).toArray();
            }
            return new Object[] {};
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            return parentElement instanceof ResourceParameter
                    ? ((ResourceParameter) parentElement).getChildren()
                    : null;
        }

        @Override
        public Object getParent(Object element) {
            return element instanceof ResourceParameter ? ((ResourceParameter) element).getParent() : null;
        }

        @Override
        public boolean hasChildren(Object element) {
            return element instanceof ResourceParameter ? ((ResourceParameter) element).hasChildren() : false;
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
            if (element instanceof ResourceParameter) {
                ResourceParameter arg = (ResourceParameter) element;
                switch (columnIndex) {
                case 0:
                    return arg.label;
                case 1:
                    return arg.descriptor == null ? "Metadata"
                            : StringUtil.capitalize(arg.descriptor.getType().name().toLowerCase());
                case 2:
                    return arg.value;
                }
            }

            return null;
        }

        @Override
        public Color getForeground(Object element) {
            if (element instanceof ResourceParameter
                    && ((ResourceParameter) element).descriptor != null
                    && (((ResourceParameter) element).descriptor.isFinal() ||
                            ((ResourceParameter) element).descriptor.isRequired())) {
                return ((ResourceParameter) element).value != null
                        && !((ResourceParameter) element).value.isEmpty()
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

    public void setMessage(String string, Level level) {

        if (level.equals(Level.SEVERE)) {
            messageLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
        } else if (level.equals(Level.WARNING)) {
            messageLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
        } else {
            messageLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
        }

        messageLabel.setText(string == null ? "" : string);
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

        // TODO errors! They are not contained in the resource.
        // this.isPublishable.setSelection(false);
        // this.unpublishableReason.setText(string == null ? "" : string);

        this.metadata.clear();
        this.metadata.putAll(resource.getMetadata());
        this.urnLabel.setText(resource.getUrn());
        this.urnLabel.setForeground(hasErrors(resource) ? SWTResourceManager.getColor(SWT.COLOR_RED)
                : SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
        this.geometryDefinition.setText(resource.getGeometry() == null ? "" : resource.getGeometry());
        this.localName.setText(resource.getLocalName());
        this.grpAdapterData.setText(resource.getAdapterType().toUpperCase() + " adapter data");
        this.worldWidget.setExtent(resource.getSpatialExtent());
        this.adapterPropertyViewer.setInput(getParameters());
        this.attributeViewer.setInput(resource.getAttributes());
        this.dependencyViewer
                .setInput(resource.getDependencies() == null ? new ArrayList<Attribute>()
                        : resource.getDependencies());
        this.outputViewer
                .setInput(resource.getOutputs() == null ? new ArrayList<Attribute>()
                        : resource.getOutputs());

        this.title
                .setText(this.metadata.containsKey(IMetadata.DC_TITLE) ? this.metadata.get(IMetadata.DC_TITLE)
                        : "");
        this.keywords.setText(this.metadata.containsKey(IMetadata.IM_KEYWORDS)
                ? this.metadata.get(IMetadata.IM_KEYWORDS)
                : "");
        this.urlDoi.setText(this.metadata.containsKey(IMetadata.DC_URL) ? this.metadata.get(IMetadata.DC_URL)
                : "");
        this.description.setText(this.metadata.containsKey(IMetadata.DC_COMMENT)
                ? this.metadata.get(IMetadata.DC_COMMENT)
                : "");
        this.originatingInstitution.setText(this.metadata.containsKey(IMetadata.DC_ORIGINATOR)
                ? this.metadata.get(IMetadata.DC_ORIGINATOR)
                : "");
        this.authors.setText(this.metadata.containsKey(IMetadata.DC_CREATOR)
                ? this.metadata.get(IMetadata.DC_CREATOR)
                : "");
        this.theme.setText(this.metadata.containsKey(IMetadata.IM_THEMATIC_AREA)
                ? this.metadata.get(IMetadata.IM_THEMATIC_AREA)
                : "");
        this.geoRegion.setText(this.metadata.containsKey(IMetadata.IM_GEOGRAPHIC_AREA)
                ? this.metadata.get(IMetadata.IM_GEOGRAPHIC_AREA)
                : "");
        this.references
                .setText(this.metadata.containsKey(IMetadata.DC_SOURCE)
                        ? this.metadata.get(IMetadata.DC_SOURCE)
                        : "");
        this.notes
                .setText(this.metadata.containsKey(IMetadata.IM_NOTES) ? this.metadata.get(IMetadata.IM_NOTES)
                        : "");
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
        lblNewLabel.setImage(ResourceManager
                .getPluginImage("org.integratedmodelling.klab.ide", "icons/logo_white_64.jpg"));

        Composite composite_2 = new Composite(composite_1_1, SWT.NONE);
        RowLayout rl_composite_2 = new RowLayout(SWT.VERTICAL);
        rl_composite_2.fill = true;
        composite_2.setLayout(rl_composite_2);
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Label lblNewLabel_1 = new Label(composite_2, SWT.NONE);
        lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
        lblNewLabel_1.setText("k.LAB Resource Editor");

        Label lblNewLabel_2 = new Label(composite_2, SWT.NONE);
        lblNewLabel_2
                .setText("Define all the properties of a resource, its geometry and its provenance information");

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

        SashForm sashForm = new SashForm(container, SWT.BORDER | SWT.VERTICAL);
        sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        {
            Group grpGeometry = new Group(sashForm, SWT.NONE);
            // grpGeometry.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
            grpGeometry.setLayout(new GridLayout(2, false));
            grpGeometry.setText("Geometry");

            mapHolder = new Composite(grpGeometry, SWT.NONE);
            mapHolder.setLayout(new GridLayout(1, false));
            GridData gd_mapHolder = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
            gd_mapHolder.heightHint = 181;
            gd_mapHolder.widthHint = 360;
            mapHolder.setLayoutData(gd_mapHolder);

            Group grpSpaceclickTo = new Group(mapHolder, SWT.NONE);
            grpSpaceclickTo.setText("Space (click to edit)");
            GridData gd_grpSpaceclickTo = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
            gd_grpSpaceclickTo.heightHint = 181;
            grpSpaceclickTo.setLayoutData(gd_grpSpaceclickTo);
            grpSpaceclickTo.setLayout(new GridLayout(1, false));
            this.worldWidget = new WorldWidget(grpSpaceclickTo, SWT.NONE);
            worldWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

            Group grpTime = new Group(mapHolder, SWT.NONE);
            grpTime.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
            grpTime.setSize(238, 352);
            grpTime.setLayout(new GridLayout(1, false));
            grpTime.setText("Time");

            TimeEditor timeEditor = new TimeEditor(grpTime, SWT.NONE);
            timeEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

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

            Composite grpAttributes = new Composite(composite_1, SWT.NONE);
            grpAttributes.setLayout(new GridLayout(1, false));
            grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

            TabFolder tabFolder_1 = new TabFolder(grpAttributes, SWT.NONE);
            tabFolder_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

            TabItem tbtmAttributes = new TabItem(tabFolder_1, SWT.NONE);
            tbtmAttributes.setText("Attributes");

            Composite composite = new Composite(tabFolder_1, SWT.NONE);
            tbtmAttributes.setControl(composite);
            TableColumnLayout tcl_composite = new TableColumnLayout();
            composite.setLayout(tcl_composite);

            attributeViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
            table = attributeViewer.getTable();
            table.setHeaderVisible(true);
            table.setLinesVisible(true);

            tableViewerColumn_3 = new TableViewerColumn(attributeViewer, SWT.NONE);
            TableColumn attributeName = tableViewerColumn_3.getColumn();
            tcl_composite.setColumnData(attributeName, new ColumnPixelData(150, true, true));
            attributeName.setText("Name");

            TableViewerColumn tableViewerColumn_1_1 = new TableViewerColumn(attributeViewer, SWT.NONE);
            TableColumn attributeType = tableViewerColumn_1_1.getColumn();
            tcl_composite.setColumnData(attributeType, new ColumnPixelData(150, true, true));
            attributeType.setText("Type");

            TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(attributeViewer, SWT.NONE);
            TableColumn attributeExample = tableViewerColumn_2.getColumn();
            tcl_composite.setColumnData(attributeExample, new ColumnPixelData(150, true, true));

            attributeViewer.setLabelProvider(new AttributeLabelProvider());
            attributeViewer.setContentProvider(new AttributeContentProvider());

            TabItem tbtmNewItem = new TabItem(tabFolder_1, SWT.NONE);
            tbtmNewItem.setText("Inputs");

            Composite composite_4 = new Composite(tabFolder_1, SWT.NONE);
            tbtmNewItem.setControl(composite_4);
            TableColumnLayout tcl_compositeD = new TableColumnLayout();
            composite_4.setLayout(tcl_compositeD);

            // ----------------------------------------
            dependencyViewer = new TableViewer(composite_4, SWT.BORDER | SWT.FULL_SELECTION);
            dependencyTable = dependencyViewer.getTable();
            dependencyTable.setHeaderVisible(true);
            dependencyTable.setLinesVisible(true);

            tableViewerColumn_3D = new TableViewerColumn(dependencyViewer, SWT.NONE);
            TableColumn attributeNameD = tableViewerColumn_3D.getColumn();
            tcl_compositeD.setColumnData(attributeNameD, new ColumnPixelData(150, true, true));
            attributeNameD.setText("Name");

            TableViewerColumn tableViewerColumn_1_1D = new TableViewerColumn(dependencyViewer, SWT.NONE);
            TableColumn attributeTypeD = tableViewerColumn_1_1D.getColumn();
            tcl_compositeD.setColumnData(attributeTypeD, new ColumnPixelData(150, true, true));
            attributeTypeD.setText("Type");

            TableViewerColumn tableViewerColumn_2D = new TableViewerColumn(dependencyViewer, SWT.NONE);
            TableColumn attributeExampleD = tableViewerColumn_2D.getColumn();
            tcl_compositeD.setColumnData(attributeExampleD, new ColumnPixelData(150, true, true));
            attributeExampleD.setText("Required");

            TabItem tbtmNewItem_1 = new TabItem(tabFolder_1, SWT.NONE);
            tbtmNewItem_1.setText("Outputs");

            outputViewer = new TableViewer(tabFolder_1, SWT.BORDER | SWT.FULL_SELECTION);
            outputTable = outputViewer.getTable();
            outputTable.setHeaderVisible(true);
            outputTable.setLinesVisible(true);
            tbtmNewItem_1.setControl(outputTable);

            TableViewerColumn tableViewerColumn = new TableViewerColumn(outputViewer, SWT.NONE);
            TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
            tblclmnNewColumn.setWidth(100);
            tblclmnNewColumn.setText("Name");

            TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(outputViewer, SWT.NONE);
            TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
            tblclmnNewColumn_1.setWidth(100);
            tblclmnNewColumn_1.setText("Type");

            TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(outputViewer, SWT.NONE);
            TableColumn tblclmnNewColumn_2 = tableViewerColumn_4.getColumn();
            tblclmnNewColumn_2.setWidth(100);
            tblclmnNewColumn_2.setText("Required");

            dependencyViewer.setLabelProvider(new AttributeLabelProvider());
            dependencyViewer.setContentProvider(new AttributeContentProvider());

            outputViewer.setLabelProvider(new AttributeLabelProvider());
            outputViewer.setContentProvider(new AttributeContentProvider());

            // ---------------------------

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
                    .setImage(ResourceManager
                            .getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));
            // new Label(composite_1, SWT.NONE);
            // new Label(composite_1, SWT.NONE);
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

        adapterPropertyViewer = new TreeViewer(grpAdapterData, SWT.BORDER | SWT.FULL_SELECTION);
        propertyTable = adapterPropertyViewer.getTree();
        propertyTable.setLinesVisible(true);
        propertyTable.setHeaderVisible(true);

        final TreeEditor editor = new TreeEditor(propertyTable);
        editor.horizontalAlignment = SWT.LEFT;
        editor.grabHorizontal = true;

        propertyTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseUp(final MouseEvent e) {
                final Control oldEditor = editor.getEditor();
                if (oldEditor != null) {
                    oldEditor.dispose();
                }

                // Get the Point from the MouseEvent
                final Point p = new Point(e.x, e.y);
                // Get the TreeItem corresponding to that point
                final TreeItem item = propertyTable.getItem(p);
                if (item == null) {
                    return;
                }
                // Now that we know the TreeItem, we can use the getBounds() method
                // to locate the corresponding column
                for (int i = 0; i < propertyTable.getColumnCount(); ++i) {
                    if (item.getBounds(i).contains(p)) {
                        final int columnIndex = i;
                        ResourceParameter data = (ResourceParameter) item.getData();
                        if (columnIndex == 2 && !data.nonexisting) {

                            final Text newEditor = new Text(propertyTable, SWT.NONE);
                            newEditor.setText(item.getText(columnIndex));
                            newEditor.addModifyListener(new ModifyListener() {
                                public void modifyText(final ModifyEvent e) {

                                    final Text text = (Text) editor.getEditor();
                                    editor.getItem().setText(columnIndex, text.getText());

                                    String value = text.getText();
                                    boolean changed = true;
                                    Argument descriptor = data.descriptor;

                                    String current = values.get(data.parameter);
                                    if (current != null && current.trim().isEmpty()) {
                                        current = null;
                                    }
                                    if (value != null && value.toString().isEmpty()) {
                                        value = null;
                                    }
                                    if ((value != null && current != null && value.equals(current))
                                            || (value == null && current == null)) {
                                        changed = false;
                                    }

                                    if (changed) {
                                        setMessage(null, Level.INFO);
                                        if (value != null && descriptor != null) {
                                            if (!Utils.validateAs(value, descriptor.getType())) {
                                                setMessage("'" + value
                                                        + "' is not a suitable value for type "
                                                        + descriptor.getType().name()
                                                                .toLowerCase(), Level.SEVERE);
                                            }
                                            if (data.parameter.endsWith("Url")) {
                                                if (!UrlValidator.getInstance().isValid(value.toString())) {
                                                    setMessage("'" + value
                                                            + "' is not a valid URL", Level.SEVERE);
                                                }
                                            }
                                        }
                                        if (value == null) {
                                            values.remove(data.parameter);
                                            data.value = null;
                                        } else {
                                            values.put(data.parameter, value);
                                            data.value = value;
                                        }
                                        setDirty(true);
                                    }
                                    adapterPropertyViewer.update(data, null);
                                }
                            });
                            newEditor.selectAll();
                            newEditor.setFocus();
                            // Set the editor for the matching column
                            editor.setEditor(newEditor, item, columnIndex);
                        }
                    }
                }
            }

        });

        propertyNameColumn = new TreeViewerColumn(adapterPropertyViewer, SWT.NONE);
        TreeColumn propertyColumn = propertyNameColumn.getColumn();
        propertyColumn.setWidth(180);
        propertyColumn.setText("Adapter property");

        TreeViewerColumn tableViewerColumn_1 = new TreeViewerColumn(adapterPropertyViewer, SWT.NONE);
        TreeColumn typeColumn = tableViewerColumn_1.getColumn();
        typeColumn.setWidth(100);
        typeColumn.setText("Type");

        propertyValueColumn = new TreeViewerColumn(adapterPropertyViewer, SWT.NONE);
        TreeColumn valueColumn = propertyValueColumn.getColumn();
        valueColumn.setWidth(400);
        valueColumn.setText("Value");
        // propertyValueColumn.setEditingSupport(new ValueSupport(adapterPropertyViewer));

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
        sashForm.setWeights(new int[] { 4, 2 });

        TabItem tbtmProvenanceData = new TabItem(tabFolder, SWT.NONE);
        tbtmProvenanceData.setText("Documentation");
        GridData gd_styledText1 = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
        gd_styledText1.heightHint = 140;

        TabItem tbtmPermissions = new TabItem(tabFolder, SWT.NONE);
        tbtmPermissions.setText("Permissions");

        Composite composite_3 = new Composite(tabFolder, SWT.NONE);
        tbtmPermissions.setControl(composite_3);

        // TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
        // tabItem.setText("New Item");

        ScrolledComposite scrolledComposite = new ScrolledComposite(tabFolder, SWT.BORDER | SWT.H_SCROLL
                | SWT.V_SCROLL);
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
        theme.setItems(new String[] {
                "Agriculture",
                "Behavior and social",
                "Biology",
                "Chemistry",
                "Conservation",
                "Demography",
                "Earth",
                "Ecology",
                "Economics",
                "Engineering",
                "Geography",
                "Geology",
                "Hydrology",
                "Infrastructure",
                "Landcover",
                "Physical and climatic",
                "Policy",
                "Socio-ecological",
                "Soil" });
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
        lblNewLabel_5
                .setToolTipText("These fields are open for new entries, but please endeavor to reuse existing keywords.\n"
                        + "In the geographic location, please start at the continental level and if more specific tags are needed,\n"
                        + "separate them with forward slashes: e.g. Europe/France/Gascogne");
        lblNewLabel_5.setImage(ResourceManager
                .getPluginImage("org.integratedmodelling.klab.ide", "icons/help.gif"));

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
        GridLayout gl_composite = new GridLayout(4, false);
        gl_composite.marginLeft = 4;
        composite.setLayout(gl_composite);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        messageLabel = new Label(composite, SWT.NONE);
        messageLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        publishButton = new Button(composite, SWT.NONE);
        GridData gd_publishButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_publishButton.widthHint = 72;
        publishButton.setLayoutData(gd_publishButton);
        publishButton.setGrayed(true);
        publishButton.setText("Publish...");
        publishButton.setEnabled(false);

        saveButton = new Button(composite, SWT.NONE);
        GridData gd_saveButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_saveButton.widthHint = 72;
        saveButton.setLayoutData(gd_saveButton);
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                save();
            }
        });
        saveButton.setText("Save");
        saveButton.setEnabled(false);

        cancelButton = new Button(composite, SWT.NONE);
        GridData gd_cancelButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_cancelButton.widthHint = 72;
        cancelButton.setLayoutData(gd_cancelButton);
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                if (!dirty || Eclipse.INSTANCE.confirm("Abandon changes?")) {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                            .hideView(ResourceEditor.this);
                }
            }
        });
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
        saveButton
                .setEnabled(b && Activator.engineMonitor() != null && Activator.engineMonitor().isRunning());
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
