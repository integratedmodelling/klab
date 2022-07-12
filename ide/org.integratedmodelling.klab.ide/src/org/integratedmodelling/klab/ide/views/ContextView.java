
package org.integratedmodelling.klab.ide.views;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.client.messaging.SessionMonitor;
import org.integratedmodelling.klab.client.messaging.SessionMonitor.ContextDescriptor;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.model.EActorBehavior;
import org.integratedmodelling.klab.ide.navigator.model.EConcept;
import org.integratedmodelling.klab.ide.navigator.model.EDefinition;
import org.integratedmodelling.klab.ide.navigator.model.EKimObject;
import org.integratedmodelling.klab.ide.navigator.model.EModel;
import org.integratedmodelling.klab.ide.navigator.model.EAcknowledgement;
import org.integratedmodelling.klab.ide.navigator.model.EResource;
import org.integratedmodelling.klab.ide.navigator.model.EScript;
import org.integratedmodelling.klab.ide.navigator.model.ETestCase;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.EngineAction;
import org.integratedmodelling.klab.rest.EngineEvent;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.RuntimeEvent;
import org.integratedmodelling.klab.rest.ScenarioSelection;
import org.integratedmodelling.klab.utils.BrowserUtils;

public class ContextView extends ViewPart {

    public ContextView() {
    }

    private Composite container;
    // private Button searchModeButton;
    private Label subjectLabel;
    private SashForm dropArea;
    private Label dropImage;
    private TableViewer tableViewer;
    private Table queryResults;
    private CLabel scenariosLabel;

    private KlabPeer klab;
    private Action openViewerAction;
    // private Action openSessionAction;
    private Action resetContextAction;

    /**
     * We keep them just in case, although the selector is in the context window.
     */
    private List<ContextDescriptor> rootContexts = new ArrayList<>();
    private ObservationReference currentContext;

    private enum Status {
        /**
         * Offline, no engine connected
         */
        EngineOffline,
        /**
         * Online, engine connected not computing or waiting, no context defined
         */
        EngineOnline,
        /**
         * Online, context defined, not computing or waiting
         */
        ContextDefined,
        /**
         * Online, computing a root-level task
         */
        Computing,
        /**
         * Online, engine not ready for observations
         */
        WaitingForEngine,
        /**
         * Online, last task computed caused an error
         */
        EngineError
    }

    /*
     * engine status is null unless the engine is reporting busy status, in which case it takes over
     * the state until it goes back to normal while stuff keeps happening.
     */
    AtomicReference<Status> state = new AtomicReference<>(Status.EngineOffline);
    AtomicBoolean engineBusy = new AtomicBoolean(false);
    private Action action;
    private IMenuManager manager;

    @Override
    public void createPartControl(Composite parent) {

        container = new Composite(parent, SWT.NONE);
        container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
        GridLayout gl_container = new GridLayout(1, false);
        gl_container.horizontalSpacing = 0;
        gl_container.verticalSpacing = 0;
        gl_container.marginHeight = 0;
        gl_container.marginWidth = 0;
        container.setLayout(gl_container);
        {
            Composite ccombo = new Composite(container, SWT.NONE);
            ccombo.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
            // ccombo.setBackground(SWTResourceManager
            // .getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
            ccombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
            GridLayout gl_ccombo = new GridLayout(1, false);
            gl_ccombo.marginWidth = 0;
            gl_ccombo.marginHeight = 0;
            ccombo.setLayout(gl_ccombo);
            //
            // searchModeButton = new Button(ccombo, SWT.TOGGLE);
            // searchModeButton.setToolTipText("Query the network for a context ");
            // searchModeButton.addMouseListener(new MouseAdapter() {
            // @Override
            // public void mouseUp(MouseEvent e) {
            //// Eclipse.INSTANCE.notification("Po dio", "Fucullazzaroppa");
            // // KlabNavigator.refresh();
            // // searchMode(searchModeButton.getSelection());
            // }
            // });
            // searchModeButton
            // .setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide",
            // "icons/Database.png"));
            // toolkit.adapt(searchModeButton, true, true);
            {
                subjectLabel = new Label(ccombo, SWT.CENTER);
                // subjectLabel.setEnabled(false);
                // subjectLabel.setEditable(false);
                subjectLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
                subjectLabel.setFont(SWTResourceManager.getFont("Segoe UI Semibold", 13, SWT.NORMAL));
                subjectLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
                // subjectLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
                // subjectLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
                subjectLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
                // toolkit.adapt(subjectLabel, true, true);
                subjectLabel.setText("No context");
                // subjectLabel.addListener(SWT.Traverse, new Listener() {
                // @Override
                // public void handleEvent(Event event) {
                // if (event.detail == SWT.TRAVERSE_RETURN) {
                // searchObservations(subjectLabel.getText());
                // }
                // }
                // });
            }
            // {
            // final Button btnNewButton = new Button(ccombo, SWT.NONE);
            // btnNewButton.setToolTipText("Choose target subject");
            // btnNewButton.addMouseListener(new MouseAdapter() {
            // @Override
            // public void mouseUp(MouseEvent e) {
            // // if (Environment.get().getContext() != null) {
            // // PopupTreeChooser ptc = new PopupTreeChooser(Eclipse
            // // .getShell(), new ContextLabelProvider(), new ContextContentProvider(),
            // // Environment
            // // .get().getContext()) {
            // //
            // // @Override
            // // protected void objectSelected(Object object) {
            // // if (object instanceof ISubject) {
            // // setObservationTarget((ISubject) object);
            // // }
            // // super.objectSelected(object);
            // // }
            // //
            // // };
            // // ptc.show(btnNewButton.toDisplay(new Point(e.x, e.y)));
            // // } else {
            // // Eclipse.beep();
            // // }
            // }
            // });
            // btnNewButton
            // .setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide",
            // "icons/Tree.png"));
            // // toolkit.adapt(btnNewButton, true, true);
            // }
        }
        {
            dropArea = new SashForm(container, SWT.NONE);
            dropArea.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
            dropArea.setLayout(new FillLayout(SWT.HORIZONTAL));
            GridData gd_dropArea = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
            gd_dropArea.widthHint = 168;
            gd_dropArea.heightHint = 168;
            dropArea.setLayoutData(gd_dropArea);
            // toolkit.adapt(dropArea);
            // toolkit.paintBordersFor(dropArea);
            dropImage = new Label(dropArea, SWT.SHADOW_NONE | SWT.CENTER);
            dropImage.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
            dropImage.addMouseListener(new MouseAdapter(){

                @Override
                public void mouseDown(MouseEvent e) {
                    /*
                     * TODO act as an interrupt button when the task is running
                     */
                    // if (_taskId >= 0) {
                    // showData(e.x, e.y);
                    // }
                }
            });
            dropImage.setToolTipText("Drop a subject to define the context.");
            dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID,
                    (Activator.engineMonitor().isRunning() ? "icons/odrop.png" : "icons/ndrop.png")));
            DropTarget dropTarget = new DropTarget(dropImage, DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
            dropTarget.setTransfer(new Transfer[]{TextTransfer.getInstance(), LocalSelectionTransfer.getTransfer()});
            DragSource dragSource = new DragSource(dropImage, DND.DROP_MOVE | DND.DROP_COPY);
            dragSource.setTransfer(new Transfer[]{TextTransfer.getInstance()});

            tableViewer = new TableViewer(dropArea, SWT.BORDER | SWT.FULL_SELECTION);
            queryResults = tableViewer.getTable();
            queryResults.setHeaderVisible(true);
            queryResults.setVisible(false);
            queryResults.setLinesVisible(true);
            queryResults.addMouseListener(new MouseListener(){

                @Override
                public void mouseUp(MouseEvent e) {
                }

                @Override
                public void mouseDown(MouseEvent e) {
                }

                @Override
                public void mouseDoubleClick(MouseEvent e) {
                    // observeFromDatabase((ObservationMetadata) queryResults
                    // .getSelection()[0].getData());
                    // searchMode(false);
                }
            });
            // toolkit.paintBordersFor(queryResults);

            TableColumn tblclmnNewColumn = new TableColumn(queryResults, SWT.NONE);
            tblclmnNewColumn.setWidth(200);
            tblclmnNewColumn.setText("Name");

            TableColumn tblclmnNewColumn_1 = new TableColumn(queryResults, SWT.NONE);
            tblclmnNewColumn_1.setWidth(160);
            tblclmnNewColumn_1.setText("Observable");

            TableColumn tblclmnNewColumn_2 = new TableColumn(queryResults, SWT.NONE);
            tblclmnNewColumn_2.setWidth(140);
            tblclmnNewColumn_2.setText("Namespace");

            TableColumn tblclmnNewColumn_3 = new TableColumn(queryResults, SWT.NONE);
            tblclmnNewColumn_3.setWidth(400);
            tblclmnNewColumn_3.setText("Description");
            dropArea.setWeights(new int[]{100, 0});
            tableViewer.setLabelProvider(new ResultLabelProvider());
            tableViewer.setContentProvider(new ResultContentProvider());

            Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
            label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
            // toolkit.adapt(label, true, true);
            {
                Composite labelContainer = new Composite(container, SWT.NONE);
                labelContainer.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
                GridLayout gl_labelContainer = new GridLayout(3, false);
                gl_labelContainer.verticalSpacing = 0;
                gl_labelContainer.marginWidth = 0;
                gl_labelContainer.marginHeight = 0;
                labelContainer.setLayout(gl_labelContainer);
                labelContainer.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                // toolkit.adapt(labelContainer);
                // toolkit.paintBordersFor(labelContainer);

                Label lblNewLabel = new Label(labelContainer, SWT.NONE);
                lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
                lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
                GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
                gd_lblNewLabel.widthHint = 80;
                lblNewLabel.setLayoutData(gd_lblNewLabel);
                lblNewLabel.setBounds(0, 0, 55, 15);
                // toolkit.adapt(lblNewLabel, true, true);
                lblNewLabel.setText("Scenarios");
                {
                    scenariosLabel = new CLabel(labelContainer, SWT.NONE);
                    scenariosLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
                    scenariosLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
                    scenariosLabel.setText("No scenarios active");
                    scenariosLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
                    scenariosLabel.setAlignment(SWT.LEFT);
                    // toolkit.adapt(scenariosLabel, true, true);
                }
                {
                    final Button btnNewButtonSC = new Button(labelContainer, SWT.NONE);
                    btnNewButtonSC.addSelectionListener(new SelectionAdapter(){
                        @Override
                        public void widgetSelected(SelectionEvent e) {
                            if (Activator.session() != null) {
                                Activator.session().send(MessageClass.UserInterface, IMessage.Type.ResetScenarios);
                            }
                        }
                    });
                    btnNewButtonSC.setToolTipText("Reset all scenarios");
                    btnNewButtonSC.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
                    btnNewButtonSC.addMouseListener(new MouseAdapter(){
                        @Override
                        public void mouseUp(MouseEvent e) {
                            // Eclipse.INSTANCE.openView(ScenarioView.ID, null);
                        }
                    });
                    btnNewButtonSC
                            .setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/globe.png"));
                    // toolkit.adapt(btnNewButtonSC, true, true);
                }
            }

            dropTarget.addDropListener(new DropTargetAdapter(){

                @Override
                public void drop(DropTargetEvent event) {

                    boolean addToContext = (event.detail & DND.DROP_COPY) == DND.DROP_COPY;

                    if (!Activator.engineMonitor().isRunning()) {
                        Eclipse.INSTANCE.alert("Please ensure the engine is running before making observations.");
                    } else {

                        Object dropped = event.data instanceof StructuredSelection
                                ? ((StructuredSelection) event.data).getFirstElement()
                                : (event.data instanceof String ? event.data : null);

                        if (dropped instanceof ETestCase || dropped instanceof EScript) {
                            File file = ((EKimObject) dropped).getPhysicalFile();
                            if (file != null) {
                                try {
                                    if (dropped instanceof ETestCase) {
                                        Activator.session().launchTest(file.toURI().toURL());
                                    } else {
                                        Activator.session().launchScript(file.toURI().toURL());
                                    }
                                } catch (MalformedURLException e) {
                                    Eclipse.INSTANCE.handleException(e);
                                }
                            }

                        }
                        if (dropped instanceof EActorBehavior) {
                            String behavior = ((EActorBehavior) dropped).getName();
                            IKActorsBehavior.Type type = ((EActorBehavior) dropped).getType();
                            if (type == IKActorsBehavior.Type.UNITTEST) {
                                Activator.session().launchTest(behavior);
                                Eclipse.INSTANCE.openView(TestView.ID, null);
                            } else if (type == IKActorsBehavior.Type.APP || type == IKActorsBehavior.Type.SCRIPT) {
                                Activator.session().launchApp(behavior);
                            }

                        } else if (dropped instanceof EModel || dropped instanceof EConcept) {
                            Activator.session().observe((EKimObject) dropped);
                        } else if (dropped instanceof EAcknowledgement) {
                            Activator.session().observe((EAcknowledgement) dropped, addToContext);
                        } else if (dropped instanceof EResource) {
                            Activator.session().previewResource(((EResource) dropped).getResource());
                        } else if (dropped instanceof String) {
                            // does not get triggered
                            EResourceReference resource = Activator.klab().getResource(dropped.toString());
                            if (resource != null) {
                                Activator.session().previewResource(resource);
                            }
                        } else if (dropped instanceof EResourceReference) {
                            Activator.session().previewResource((EResourceReference) dropped);
                        } else if (dropped instanceof EDefinition && ((EDefinition) dropped).getDefineClass() != null) {
                            Activator.session().observe(((EDefinition) dropped).getName());
                        }
                    }
                }
            });
        }

        klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));

        createActions();
        initializeToolBar();
        initializeMenu();
    }

    private void handleMessage(IMessage message) {

        switch(message.getType()) {
        case RuntimeEvent:
            refresh(getState(message.getPayload(RuntimeEvent.class)));
            break;
        case ResetContext:
            currentContext = null;
            refresh(Status.EngineOnline);
            break;
        case EngineDown:
            currentContext = null;
            rootContexts.clear();
            refresh(Status.EngineOffline);
            break;
        case EngineUp:
            // TODO read up previous contexts
            refresh(Status.EngineOnline);
            break;
        case EngineEvent:
            EngineEvent ee = message.getPayload(EngineEvent.class);
            switch(ee.getType()) {
            case ResourceValidation:
                engineBusy.set(ee.isStarted());
                refresh(Status.WaitingForEngine);
                break;
            default:
                break;
            }
            break;
        case ScenariosSelected:
            if (message.getMessageClass() == MessageClass.UserInterface) {

                final ScenarioSelection selection = message.getPayload(ScenarioSelection.class);

                Display.getDefault().asyncExec(() -> {
                    if (selection.getScenarios().size() == 0) {
                        scenariosLabel.setText("No scenarios active");
                        scenariosLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
                        scenariosLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
                        scenariosLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
                    } else {
                        if (selection.getScenarios().size() == 1) {
                            scenariosLabel.setText(selection.getScenarios().iterator().next());
                        } else {
                            scenariosLabel.setText(selection.getScenarios().size() + " scenarios are active");
                        }
                        scenariosLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
                        scenariosLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
                        scenariosLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
                    }
                });
            }
            break;
        default:
            break;
        }
    }

    private Status getState(RuntimeEvent event) {

        Status status = this.state.get();

        if (currentContext != null && event.getRootContext() != null
                && !currentContext.getId().equals(event.getRootContext().getId())) {
            // not tuned on the context of the event, no change
            return status;
        }

        switch(event.getType()) {
        case TaskAdded:
        case TaskStatusChanged:
            switch(event.getTask().getStatus()) {
            case Aborted:
                status = Status.EngineError;
                break;
            case Finished:
                if (event.getTask().getParentId() == null) {
                    status = Status.ContextDefined;
                }
                break;
            case Started:
                status = Status.Computing;
                break;
            }
            break;
        case ObservationAdded:
            if (event.getObservation().getId().equals(event.getRootContext().getId())) {
                this.rootContexts.add(sm().getContextDescriptor(event.getObservation()));
                currentContext = event.getObservation();
            }
            status = Status.Computing;
            break;
        case SystemNotification:
        case DataflowChanged:
        case NotificationAdded:
        default:
            break;
        }
        return status;
    }

    private SessionMonitor sm() {
        return Activator.session().getContextMonitor();
    }

    private void refresh(Status status) {

        boolean enableViewer = true;
        boolean enableReset = currentContext != null && !engineBusy.get();
        String image = "icons/odrop.png";
        String ttext = "";

        if (status == Status.WaitingForEngine && engineBusy.get()) {
            image = "icons/owait.png";
            ttext = "Engine is busy: please wait";
        } else if (status != Status.WaitingForEngine) {

            if (this.state.get() == status) {
                return;
            }

            this.state.set(status);

            switch(status) {
            case Computing:
                image = "icons/orun.png";
                ttext = "Contextualization is ongoing";
                enableViewer = true;
                enableReset = false;
                break;
            case ContextDefined:
                image = "icons/ocheck.png";
                ttext = "Drop models or concepts to observe in context";
                enableViewer = true;
                enableReset = true;
                break;
            case EngineError:
                image = "icons/estop.png";
                ttext = "The last task executed caused an error";
                enableViewer = true;
                enableReset = true;
                break;
            case EngineOffline:
                image = "icons/ndrop.png";
                ttext = "Engine is offline or not connected";
                enableViewer = false;
                enableReset = false;
                break;
            case EngineOnline:
                image = "icons/odrop.png";
                ttext = "Define a context using the Explorer or an observer";
                enableViewer = true;
                enableReset = false;
                break;
            default:
                break;
            }
        }

        final String icon = image;
        final String tooltip = ttext;
        final boolean eviewer = enableViewer;
        final boolean ereset = enableReset;

        Display.getDefault().asyncExec(() -> {
            dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, icon));
            dropImage.setToolTipText(tooltip);
            openViewerAction.setEnabled(eviewer);
            // openSessionAction.setEnabled(eviewer);
            resetContextAction.setEnabled(ereset);
            subjectLabel.setForeground(currentContext == null
                    ? SWTResourceManager.getColor(SWT.COLOR_GRAY)
                    : SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
            subjectLabel.setText(currentContext == null ? "No context" : currentContext.getLabel());
            this.manager.removeAll();
            for (ContextDescriptor cd : rootContexts) {
                this.manager.add(new Action(cd.getRoot().getLabel()){
                    // TODO
                });
            }
        });

    }

    protected void searchObservations(String text) {
        // TODO Auto-generated method stub

    }

    class ContextContentProvider implements ITreeContentProvider {

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        @Override
        public Object[] getElements(Object inputElement) {
            /*
             * if (inputElement instanceof IContext) { return new Object[] { ((IContext)
             * inputElement).getSubject() }; } else
             */ if (inputElement instanceof ISubject) {
                return ((ISubject) inputElement).getSubjects().toArray();
            }
            return null;
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            return getElements(parentElement);
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof ISubject) {
                // return ((ISubject) element).getContextObservation() == null
                // ? Environment.get().getContext()
                // : ((ISubject) element).getContextObservation();
            }
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            return false;
            // element instanceof IContext
            // || (element instanceof ISubject && ((ISubject) element).getSubjects().size()
            // > 0);
        }

    }

    class ContextLabelProvider extends BaseLabelProvider implements ILabelProvider {

        @Override
        public Image getImage(Object element) {
            if (element instanceof ISubject) {
                return ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/observer.gif");
            }
            return null;
        }

        @Override
        public String getText(Object element) {
            if (element instanceof ISubject) {
                return ((ISubject) element).getName();
            }
            return null;
        }

    }

    class ResultContentProvider implements ITreeContentProvider {

        // painful, but I really don't want to store this in the view. Cross
        // fingers.
        List<?> data = null;

        @Override
        public void dispose() {
        }

        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof List) {
                data = (List<?>) inputElement;
                return data.toArray();
            }
            return null;
        }

        @Override
        public Object[] getChildren(Object parentElement) {
            return getElements(parentElement);
        }

        @Override
        public Object getParent(Object element) {
            if (element instanceof List) {
                data = (List<?>) element;
            }
            // if (element instanceof ObservationMetadata) {
            // return data;
            // }
            return null;
        }

        @Override
        public boolean hasChildren(Object element) {
            if (element instanceof List) {
                data = (List<?>) element;
            }
            return element instanceof List && data != null && data.size() > 0;
        }
    }

    class ResultLabelProvider extends BaseLabelProvider implements ITableLabelProvider {

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            // if (columnIndex == 0 && element instanceof ObservationMetadata) {
            // return ResourceManager
            // .getPluginImage(Activator.PLUGIN_ID, "icons/observer.gif");
            // }
            return null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {
            // if (element instanceof ObservationMetadata) {
            // switch (columnIndex) {
            // case 0:
            // return ((ObservationMetadata) element).id;
            // case 1:
            // return ((ObservationMetadata) element).observableName;
            // case 2:
            // return ((ObservationMetadata) element).namespaceId.equals(KLAB.NAME)
            // ? "Local database"
            // : ((ObservationMetadata) element).namespaceId;
            // case 3:
            // return ((ObservationMetadata) element).description;
            // }
            // }
            return null;
        }

    }

    @Override
    public void setFocus() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        klab.dispose();
        super.dispose();
    }

    class OpenViewerAction extends Action implements IMenuCreator {

        private Menu fMenu;

        OpenViewerAction() {
            super("", IAction.AS_DROP_DOWN_MENU);
            setMenuCreator(this);
            setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide", "icons/browser.gif"));
        }

        @Override
        public void run() {
            if (Activator.engineMonitor().isRunning()) {
                BrowserUtils.startBrowser(
                        "http://localhost:8283/modeler/ui/viewer?session=" + Activator.engineMonitor().getSessionId());
            }
        }

        @Override
        public void dispose() {
            if (fMenu != null) {
                fMenu.dispose();
                fMenu = null;
            }
        }

        @Override
        public Menu getMenu(Control parent) {
            if (fMenu != null) {
                fMenu.dispose();
            }
            fMenu = new Menu(parent);
            int i = 0;

            Action filterAction = new Action("New session"){
                public void run() {
                }
            };

            Action gen = new Action("Rejoin session...", IAction.AS_DROP_DOWN_MENU){
                public void run() {
                }
            };

            Action debugger = new Action("Open debugger"){
                public void run() {
                    EngineAction request = new EngineAction();
                    request.setRequest("debugger");
                    Activator.post(IMessage.MessageClass.EngineLifecycle, IMessage.Type.ExecuteCommand, request);
                }
            };

            addActionToMenu(fMenu, filterAction);
            new MenuItem(fMenu, SWT.SEPARATOR);
            addActionToMenu(fMenu, gen);
            new MenuItem(fMenu, SWT.SEPARATOR);
            addActionToMenu(fMenu, debugger);

            return fMenu;
        }

        protected void addActionToMenu(Menu parent, Action action) {
            ActionContributionItem item = new ActionContributionItem(action);
            item.fill(parent, -1);
        }

        @Override
        public Menu getMenu(Menu parent) {
            return null;
        }
    }

    /**
     * Create the actions.
     */
    private void createActions() {

        // {
        // openViewerAction = new Action("Open new viewer") {
        //
        // @Override
        // public void run() {
        // if (Activator.engineMonitor().isRunning()) {
        // BrowserUtils.startBrowser("http://localhost:8283/modeler/ui/viewer?session="
        // + Activator.engineMonitor().getSessionId());
        // }
        // }
        //
        // };
        //
        // openViewerAction.setEnabled(Activator.engineMonitor().isRunning());
        // openViewerAction.setImageDescriptor(
        // ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide",
        // "icons/browser.gif"));
        // }
        {
            openViewerAction = new OpenViewerAction();
            openViewerAction.setEnabled(Activator.engineMonitor().isRunning());
        }
        {
            resetContextAction = new Action("Reset context"){
                @Override
                public void run() {
                    Activator.post(IMessage.MessageClass.UserContextChange, IMessage.Type.ResetContext, "");
                    currentContext = null;
                    refresh(Status.EngineOnline);
                }
            };
            resetContextAction.setEnabled(Activator.engineMonitor().isRunning());
            resetContextAction.setImageDescriptor(
                    ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide", "icons/target_red.png"));
            resetContextAction.setToolTipText("Reset context");
        }
    }

    /**
     * Initialize the toolbar.
     */
    private void initializeToolBar() {
        if (getViewSite() != null) {
            IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
            tbm.add(resetContextAction);
            tbm.add(openViewerAction);
            // tbm.add(openSessionAction);
        }
    }

    /**
     * Initialize the menu.
     */
    private void initializeMenu() {
        if (getViewSite() != null) {
            this.manager = getViewSite().getActionBars().getMenuManager();
            // this is for hierarchical menus - add the this to the manager and the actions
            // to this
            // MenuManager menuManager = new MenuManager("Previous contexts");
            // manager.add(action);
            // menuManager.add(action);
        }
    }
}