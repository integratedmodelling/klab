package org.integratedmodelling.klab.ide.views;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.kdl.ui.internal.KdlActivator;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.model.beans.DisplayPriority;
import org.integratedmodelling.klab.ide.navigator.model.beans.EDataflowReference;
import org.integratedmodelling.klab.ide.navigator.model.beans.ENotification;
import org.integratedmodelling.klab.ide.navigator.model.beans.EObservationReference;
import org.integratedmodelling.klab.ide.navigator.model.beans.ERuntimeObject;
import org.integratedmodelling.klab.ide.navigator.model.beans.ETaskReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.Capabilities;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.layout.RowLayout;

public class RuntimeView extends ViewPart {

    public static final String   ID               = "org.integratedmodelling.klab.ide.views.RuntimeView"; //$NON-NLS-1$

    private KlabPeer             klab;

    private Label                verLabel;

    private Label                memLabel;

    private Label                upLabel;

    private SashForm             sashForm;

    private SashForm             taskArea;

    private TreeViewer           taskViewer;

    private Tree                 taskTree;

    private Group                grpMessages;

    private TableViewer          tableViewer;

    private Table                tableMessages;

    private TableViewerColumn    tableViewerColumn;

    private TableColumn          notificationTime;

    private TableViewerColumn    tableViewerColumn_1;

    private TableColumn          notificationText;
    private Composite            composite;
    private Label                engineStatusIcon;
    private Label                engineStatusLabel;
    private Label                networkStatusIcon;
    private Label                label;
    private Composite            composite_1;
    private Button               toggleTasks;
    private Button               toggleArtifacts;
    private Label                lblNewLabel;

    private DisplayPriority      currentPriority  = DisplayPriority.TASK_FIRST;
    private List<ENotification>  notifications;
    private List<ERuntimeObject> history;
    private Composite            composite_2;
    private Group                grpSessionEvents;
    private Table                table;
    private TableViewer          tableViewer_1;

    private ERuntimeObject       lastFocus;
    private Level                systemLogLevel = Level.INFO;
    private Level                currentLogLevel  = Level.INFO;
    private ERuntimeObject       currentDetail;
    private Composite composite_4;
    private Label label_1;
    private Combo combo;

    public RuntimeView() {
    }

    class NotificationLabelProvider extends LabelProvider implements ITableLabelProvider {

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            if (element instanceof ENotification && columnIndex == 0) {
                if (((ENotification) element).getLevel().equals(Level.INFO.getName())) {
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/message_info.gif");
                } else if (((ENotification) element).getLevel().equals(Level.WARNING.getName())) {
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/message_warning.gif");
                } else if (((ENotification) element).getLevel().equals(Level.SEVERE.getName())) {
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/message_error.gif");
                } else if (((ENotification) element).getLevel().equals(Level.FINE.getName())) {
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/message_debug.gif");
                }
            }
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof ENotification) {
                if (columnIndex == 0) {
                } else if (columnIndex == 1) {
                    return ((ENotification) element).getMessage();
                }
            }
            return null;
        }

    }

    class TaskLabelProvider extends LabelProvider implements IColorProvider, IFontProvider {

        @Override
        public Image getImage(Object element) {
            if (element instanceof ERuntimeObject) {
                if (element instanceof EObservationReference) {
                    if (((EObservationReference) element).getSiblingCount() == 0) {
                        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/emptycontent.gif");
                    } else if (((EObservationReference) element).getSemantics()
                            .contains(IKimConcept.Type.QUALITY)) {
                        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/datagrid.gif");
                    } else if (((EObservationReference) element).getSiblingCount() == 1) {
                        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/resource.gif");
                    } else if (((EObservationReference) element).getSiblingCount() > 1) {
                        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/resources.gif");
                    }
                } else if (element instanceof ETaskReference) {
                    Image baseImage = ResourceManager
                            .getPluginImage(Activator.PLUGIN_ID, "icons/task.gif");
                    if (((ETaskReference) element).getStatus() == Type.TaskStarted) {
                        return ResourceManager.decorateImage(baseImage, ResourceManager
                                .getPluginImage(Activator.PLUGIN_ID, "icons/waiting_ovr.gif"), SWTResourceManager.TOP_LEFT);
                    } else if (((ETaskReference) element).getStatus() == Type.TaskFinished) {
                        return ResourceManager.decorateImage(baseImage, ResourceManager
                                .getPluginImage(Activator.PLUGIN_ID, "icons/ok_ovr.gif"), SWTResourceManager.TOP_LEFT);
                    } else if (((ETaskReference) element).getStatus() == Type.TaskAborted) {
                        return ResourceManager.decorateImage(baseImage, ResourceManager
                                .getPluginImage(Activator.PLUGIN_ID, "icons/error_ovr.gif"), SWTResourceManager.TOP_LEFT);
                    }
                } else if (element instanceof ENotification) {
                    if (((ENotification) element).getLevel().equals(Level.INFO.getName())) {
                        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/message_info.gif");
                    } else if (((ENotification) element).getLevel().equals(Level.WARNING.getName())) {
                        return ResourceManager
                                .getPluginImage(Activator.PLUGIN_ID, "icons/message_warning.gif");
                    } else if (((ENotification) element).getLevel().equals(Level.SEVERE.getName())) {
                        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/message_error.gif");
                    } else if (((ENotification) element).getLevel().equals(Level.FINE.getName())) {
                        return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/message_debug.gif");
                    }
                } else if (element instanceof EDataflowReference) {
                    return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/dataflow.gif");
                }
            }
            return null;
        }

        @Override
        public String getText(Object element) {
            if (element instanceof ERuntimeObject) {
                if (element instanceof EObservationReference) {
                    return ((EObservationReference) element).getLabel();
                } else if (element instanceof ETaskReference) {
                    return ((ETaskReference) element).getDescription();
                } else if (element instanceof ENotification) {
                    return ((ENotification) element).getMessage();
                } else if (element instanceof EDataflowReference) {
                    return "Dataflow computed";
                }
            }
            return null;
        }

        @Override
        public Font getFont(Object element) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Color getForeground(Object element) {
            if (element instanceof ETaskReference
                    && ((ETaskReference) element).getStatus() == Type.TaskStarted) {
                return ResourceManager.getColor(SWT.COLOR_GRAY);
            } else if (element instanceof ENotification) {
                if (((ENotification) element).getLevel().equals(Level.INFO.getName())) {
                    return ResourceManager.getColor(SWT.COLOR_DARK_BLUE);
                } else if (((ENotification) element).getLevel().equals(Level.WARNING.getName())) {
                    return ResourceManager.getColor(SWT.COLOR_DARK_YELLOW);
                } else if (((ENotification) element).getLevel().equals(Level.SEVERE.getName())) {
                    return ResourceManager.getColor(SWT.COLOR_RED);
                } else if (((ENotification) element).getLevel().equals(Level.FINE.getName())) {
                    return ResourceManager.getColor(SWT.COLOR_DARK_GRAY);
                }
            }
            return null;
        }

        @Override
        public Color getBackground(Object element) {
            return null;
        }

    }

    class TaskContentProvider implements ITreeContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement);
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof Collection) {
                return ((Collection<?>) parentElement).toArray();
            } else if (parentElement instanceof ERuntimeObject) {
                return ((ERuntimeObject) parentElement).getEChildren(currentPriority, currentLogLevel);
            }
            return new Object[] {};
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof ERuntimeObject) {
                return ((ERuntimeObject) element).getEParent(currentPriority) == null ? history
                        : ((ERuntimeObject) element).getEParent(currentPriority);
            }
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof Collection) {
                return ((Collection<?>) element).size() > 0;
            } else if (element instanceof ERuntimeObject) {
                return ((ERuntimeObject) element).getEChildren(currentPriority, currentLogLevel).length > 0;
            }
            return false;
        }

    }

    class NotificationContentProvider implements ITreeContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            return getChildren(inputElement);
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            if (parentElement instanceof Collection) {
                return ((Collection<?>) parentElement).toArray();
            }
            return new Object[] {};
        }

        @Override
        public Object getParent(Object element) {
            return element instanceof ENotification ? notifications : null;
        }

        @Override
        public boolean hasChildren(Object element) {
            return element instanceof Collection && ((Collection<?>) element).size() > 0;
        }

    }

    /**
     * Create contents of the view part.
     * 
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {
        parent.setBackground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

        parent.setLayout(new GridLayout(1, false));

        GridLayout gl_grpServers = new GridLayout(2, false);
        gl_grpServers.marginWidth = 0;
        {
            GC gc = new GC(parent);
            gc.setFont(parent.getFont());
            // FontMetrics fm = gc.getFontMetrics();
            // Point extent = gc.textExtent("M");
            //
            // int hb = extent.y + 8;
            // int hm = extent.y + 2;
            // int wm = extent.x + 2;
            // int ht = extent.y + 6;

            composite = new Composite(parent, SWT.NONE);
            composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            composite.setBackground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
            GridLayout gl_composite = new GridLayout(3, false);
            gl_composite.marginWidth = 0;
            gl_composite.marginHeight = 0;
            gl_composite.horizontalSpacing = 6;
            composite.setLayout(gl_composite);

            engineStatusIcon = new Label(composite, SWT.NONE);
            engineStatusIcon
                    .setImage(org.eclipse.wb.swt.ResourceManager
                            .getPluginImage("org.integratedmodelling.klab.ide", (Activator.engineMonitor()
                                    .isRunning() ? "icons/green24.png" : "icons/grey24.png")));

            engineStatusIcon.setBackground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
            GridData gd_engineStatusIcon = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
            gd_engineStatusIcon.heightHint = 24;
            gd_engineStatusIcon.widthHint = 24;
            engineStatusIcon.setLayoutData(gd_engineStatusIcon);
            engineStatusIcon.setToolTipText("Engine is offline");

            engineStatusLabel = new Label(composite, SWT.NONE);
            engineStatusLabel.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_GRAY));
            engineStatusLabel
                    .setFont(org.eclipse.wb.swt.SWTResourceManager
                            .getFont("Segoe UI Semibold", 13, SWT.NORMAL));
            engineStatusLabel.setBackground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
            engineStatusLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
            engineStatusLabel.setText("Engine is off");

            networkStatusIcon = new Label(composite, SWT.NONE);
            networkStatusIcon.setToolTipText("Not connected to the k.LAB network");
            networkStatusIcon.setImage(org.eclipse.wb.swt.ResourceManager
                    .getPluginImage("org.integratedmodelling.klab.ide", "icons/worldgrey24.png"));
            networkStatusIcon.setBackground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_WHITE));
            GridData gd_networkStatusIcon = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
            gd_networkStatusIcon.heightHint = 24;
            gd_networkStatusIcon.widthHint = 24;
            networkStatusIcon.setLayoutData(gd_networkStatusIcon);

            label = new Label(parent, SWT.SEPARATOR | SWT.HORIZONTAL);
            label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

            Composite grpStatus = new Composite(parent, SWT.NONE);
            GridLayout gl_grpStatus = new GridLayout(7, false);
            gl_grpStatus.marginTop = 8;
            gl_grpStatus.verticalSpacing = 3;
            gl_grpStatus.marginWidth = 2;
            gl_grpStatus.marginHeight = 0;
            gl_grpStatus.horizontalSpacing = 2;
            grpStatus.setLayout(gl_grpStatus);
            GridData gd_grpStatus = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
            gd_grpStatus.heightHint = 36;
            grpStatus.setLayoutData(gd_grpStatus);
            new Label(grpStatus, SWT.NONE);

            Label lblVersion = new Label(grpStatus, SWT.NONE);
            lblVersion.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
            lblVersion.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
            lblVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
            lblVersion.setText("Version:");

            verLabel = new Label(grpStatus, SWT.NONE);
            verLabel.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
            GridData gd_verLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
            gd_verLabel.widthHint = 96;
            verLabel.setLayoutData(gd_verLabel);

            Label lblMemory = new Label(grpStatus, SWT.NONE);
            lblMemory.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
            lblMemory.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
            lblMemory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
            lblMemory.setText("Memory:");

            memLabel = new Label(grpStatus, SWT.NONE);
            memLabel.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
            GridData gd_memLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
            gd_memLabel.widthHint = 85;
            memLabel.setLayoutData(gd_memLabel);

            Label lblSince = new Label(grpStatus, SWT.NONE);
            lblSince.setForeground(org.eclipse.wb.swt.SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
            lblSince.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
            lblSince.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
            lblSince.setText("Up:");

            upLabel = new Label(grpStatus, SWT.NONE);
            upLabel.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
            GridData gd_upLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
            gd_upLabel.widthHint = 96;
            upLabel.setLayoutData(gd_upLabel);

        }
        sashForm = new SashForm(parent, SWT.VERTICAL);
        sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        grpSessionEvents = new Group(sashForm, SWT.NONE);
        grpSessionEvents.setText("Session events");
        GridLayout gl_grpSessionEvents = new GridLayout(1, false);
        gl_grpSessionEvents.verticalSpacing = 2;
        gl_grpSessionEvents.marginHeight = 0;
        gl_grpSessionEvents.horizontalSpacing = 0;
        gl_grpSessionEvents.marginWidth = 0;
        grpSessionEvents.setLayout(gl_grpSessionEvents);

        composite_1 = new Composite(grpSessionEvents, SWT.NONE);
        composite_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        GridLayout gl_composite_1 = new GridLayout(4, false);
        gl_composite_1.verticalSpacing = 0;
        gl_composite_1.marginWidth = 0;
        gl_composite_1.marginHeight = 0;
        gl_composite_1.horizontalSpacing = 1;
        composite_1.setLayout(gl_composite_1);

        lblNewLabel = new Label(composite_1, SWT.NONE);
        lblNewLabel.setText("By ");

        toggleTasks = new Button(composite_1, SWT.RADIO);
        toggleTasks.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (toggleTasks.getSelection()) {
                    currentPriority = DisplayPriority.TASK_FIRST;
                } else {
                    currentPriority = DisplayPriority.ARTIFACTS_FIRST;
                }
                refreshTaskViewer();
            }
        });

        toggleTasks.setSelection(true);
        toggleTasks.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
        toggleTasks.setText("task");

        toggleArtifacts = new Button(composite_1, SWT.RADIO);
        toggleArtifacts.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
        toggleArtifacts.setText("artifact");

        composite_2 = new Composite(composite_1, SWT.NONE);
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        GridLayout gl_composite_2 = new GridLayout(2, false);
        gl_composite_2.horizontalSpacing = 3;
        gl_composite_2.marginWidth = 0;
        gl_composite_2.marginHeight = 0;
        composite_2.setLayout(gl_composite_2);

        Label lblNewLabel_1 = new Label(composite_2, SWT.NONE);
        lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
        lblNewLabel_1.setText("Report level");
        lblNewLabel_1.setAlignment(SWT.RIGHT);
        lblNewLabel_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

        Combo btnCheckButton = new Combo(composite_2, SWT.READ_ONLY);
        btnCheckButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                switch (btnCheckButton.getText()) {
                case "Info":
                    currentLogLevel = Level.INFO;
                    break;
                case "Error":
                    currentLogLevel = Level.SEVERE;
                    break;
                case "Warning":
                    currentLogLevel = Level.WARNING;
                    break;
                case "Debug":
                    currentLogLevel = Level.FINE;
                    break;
                }
                refreshTaskViewer();
            }
        });
        btnCheckButton.setItems(new String[] { "Error", "Warning", "Info", "Debug" });
        btnCheckButton.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
        btnCheckButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnCheckButton.select(2);

        taskArea = new SashForm(grpSessionEvents, SWT.VERTICAL);
        taskArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        GridLayout gl_taskArea = new GridLayout(1, false);
        gl_taskArea.verticalSpacing = 2;
        gl_taskArea.horizontalSpacing = 0;
        gl_taskArea.marginHeight = 0;
        gl_taskArea.marginWidth = 0;
        taskArea.setLayout(gl_taskArea);

        taskViewer = new TreeViewer(taskArea, SWT.FULL_SELECTION);
        taskViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                Object o = ((StructuredSelection) (event.getSelection())).getFirstElement();
                if (o instanceof ERuntimeObject) {
                    showDetail((ERuntimeObject) o);
                }
            }
        });
        taskTree = taskViewer.getTree();
        taskTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        tableViewer_1 = new TableViewer(taskArea, SWT.BORDER | SWT.FULL_SELECTION);
        table = tableViewer_1.getTable();
        table.setVisible(true);
        table.setLinesVisible(true);
        GridData gd_table = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_table.heightHint = 180;
//        gd_table.exclude = true;
        table.setLayoutData(gd_table);
        taskArea.setWeights(new int[] { 72, 28 });
        taskArea.setMaximizedControl(taskTree);

        taskViewer.setContentProvider(new TaskContentProvider());
        taskViewer.setLabelProvider(new TaskLabelProvider());
        taskViewer.addDoubleClickListener(new IDoubleClickListener() {

            @Override
            public void doubleClick(DoubleClickEvent event) {
                Object o = ((StructuredSelection) (event.getSelection())).getFirstElement();
                if (o != null) {
                    handleSelection(o);
                }
            }
        });

        toggleArtifacts.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (toggleArtifacts.getSelection()) {
                    currentPriority = DisplayPriority.ARTIFACTS_FIRST;
                } else {
                    currentPriority = DisplayPriority.TASK_FIRST;
                }
                if (lastFocus != null) {
                    lastFocus = currentPriority == DisplayPriority.ARTIFACTS_FIRST
                            ? Activator.session().getCurrentContext()
                            : Activator.session().getCurrentTask();
                }
                refreshTaskViewer();
                refreshSystemLog();
            }
        });

        grpMessages = new Group(sashForm, SWT.NONE);
        grpMessages.setText("System Log");
        grpMessages.setLayout(new GridLayout(1, false));
        
        composite_4 = new Composite(grpMessages, SWT.NONE);
        composite_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
        GridLayout gl_composite_4 = new GridLayout(2, false);
        gl_composite_4.marginWidth = 0;
        gl_composite_4.marginHeight = 0;
        gl_composite_4.horizontalSpacing = 3;
        composite_4.setLayout(gl_composite_4);
        
        label_1 = new Label(composite_4, SWT.NONE);
        label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
        label_1.setText("Report level");
        label_1.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
        label_1.setAlignment(SWT.RIGHT);
        
        combo = new Combo(composite_4, SWT.READ_ONLY);
        combo.setItems(new String[] {"Error", "Warning", "Info", "Debug"});
        combo.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
        combo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        combo.select(2);
        combo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                switch (combo.getText()) {
                case "Info":
                    systemLogLevel = Level.INFO;
                    break;
                case "Error":
                    systemLogLevel = Level.SEVERE;
                    break;
                case "Warning":
                    systemLogLevel = Level.WARNING;
                    break;
                case "Debug":
                    systemLogLevel = Level.FINE;
                    break;
                }
                refreshSystemLog();
            }
        });
        tableViewer = new TableViewer(grpMessages, SWT.BORDER | SWT.FULL_SELECTION);
        tableMessages = tableViewer.getTable();
        tableMessages.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        tableMessages.setLinesVisible(true);

        tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        notificationTime = tableViewerColumn.getColumn();
        notificationTime.setAlignment(SWT.CENTER);
        notificationTime.setResizable(false);
        notificationTime.setWidth(42);

        tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
        notificationText = tableViewerColumn_1.getColumn();
        notificationText.setWidth(500);
        sashForm.setWeights(new int[] { 188, 72 });

        tableViewer.setContentProvider(new NotificationContentProvider());
        tableViewer.setLabelProvider(new NotificationLabelProvider());

        createActions();
        initializeToolBar();
        initializeMenu();

        klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));
    }

    protected void showDetail(ERuntimeObject o) {
        
        if (o instanceof ENotification) {
            return;
        }

        System.out.println("SHOWING DETAIL " + o);

        if (currentDetail != null && o.equals(currentDetail)) {
            currentDetail = null;
            Display.getDefault().asyncExec(() -> taskArea.setMaximizedControl(taskTree));
        } else {
            currentDetail = o;
            Display.getDefault().asyncExec(() -> {
                // TODO load properties
                taskArea.setMaximizedControl(null);
            });
        }

    }

    protected void handleSelection(Object o) {
        if (o instanceof EDataflowReference) {
            Eclipse.INSTANCE.edit(((EDataflowReference)o).getKdlCode(), KdlActivator.ORG_INTEGRATEDMODELLING_KDL_KDL, false);
        }
    }

    public void dispose() {
        klab.dispose();
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

    private void handleMessage(IMessage message) {

        switch (message.getType()) {
        case TaskStarted:
            Display.getDefault().asyncExec(() -> {
                taskArea.setMaximizedControl(taskTree);
            });
            break;
        case ResetContext:
            lastFocus = null;
            Display.getDefault().asyncExec(() -> {
                taskViewer.collapseAll();
                taskArea.setMaximizedControl(taskTree);
            });
            break;
        case FocusChanged:
            if (currentPriority == DisplayPriority.ARTIFACTS_FIRST) {
                lastFocus = message.getPayload(EObservationReference.class);
                refreshTaskViewer();
            }
            break;
        case HistoryChanged:
            Object payload = message.getPayload();
            if (((payload instanceof ETaskReference && currentPriority == DisplayPriority.TASK_FIRST)
                    || (payload instanceof EObservationReference
                            && currentPriority == DisplayPriority.ARTIFACTS_FIRST))) {
                lastFocus = (ERuntimeObject) payload;
            } else {
                payload = null;
            }
            refreshTaskViewer();
            break;
        case Notification:
            refreshSystemLog();
            break;
        case EngineDown:
            Display.getDefault().asyncExec(() -> {
                engineStatusIcon
                        .setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/grey24.png"));
                networkStatusIcon
                        .setImage(ResourceManager
                                .getPluginImage(Activator.PLUGIN_ID, "icons/worldgrey24.png"));
                engineStatusLabel.setText("Engine is offline");
            });
            break;
        case EngineUp:
            final Capabilities capabilities = message.getPayload(Capabilities.class);
            Display.getDefault().asyncExec(() -> {
                engineStatusIcon
                        .setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/green24.png"));
                if (capabilities.isOnline()) {
                    networkStatusIcon
                            .setImage(ResourceManager
                                    .getPluginImage(Activator.PLUGIN_ID, "icons/world24.png"));
                } else {
                    networkStatusIcon
                            .setImage(ResourceManager
                                    .getPluginImage(Activator.PLUGIN_ID, "icons/worldgrey24.png"));
                }
                engineStatusLabel.setText("User " + capabilities.getOwner().getId() + " logged in");
            });
            break;
        default:
            break;

        }
    }

    private void refreshSystemLog() {
        Display.getDefault()
                .asyncExec(() -> tableViewer
                        .setInput(notifications = Activator.session()
                                .getSystemNotifications(systemLogLevel)));
    }

    public void refreshTaskViewer() {

        Display.getDefault()
                .asyncExec(() -> {
                    if (lastFocus != null) {
                        taskViewer
                                .setInput(history = Activator.session()
                                        .getSessionHistory(currentPriority, currentLogLevel));
                        taskViewer.collapseAll();
                        taskViewer.expandToLevel(lastFocus, TreeViewer.ALL_LEVELS);
                    }
                });
    }
}
