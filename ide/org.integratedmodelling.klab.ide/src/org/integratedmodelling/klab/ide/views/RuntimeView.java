package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.navigator.utils.ResourceManager;
import org.integratedmodelling.klab.ide.navigator.utils.SWTResourceManager;

public class RuntimeView extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.RuntimeView"; //$NON-NLS-1$

	private KlabPeer klab;

	private Label serverLabel;

	private Label verLabel;

	private Label memLabel;

	private Label upLabel;

	private Label userLabel;

	private SashForm sashForm;

	private Group taskArea;

	private TableViewer taskViewer;

	private Table tableTasks;

	private Group grpMessages;

	private TableViewer tableViewer;

	private Table tableMessages;

	private TableViewerColumn tableViewerColumn;

	private TableColumn tblclmnNewColumn;

	private TableViewerColumn tableViewerColumn_1;

	private TableColumn tblclmnNewColumn_1;

	public RuntimeView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		parent.setLayout(new GridLayout(1, false));

		GridLayout gl_grpServers = new GridLayout(2, false);
		gl_grpServers.marginWidth = 0;
		{
			GC gc = new GC(parent);
			gc.setFont(parent.getFont());
			FontMetrics fm = gc.getFontMetrics();
			Point extent = gc.textExtent("M");

			int hb = extent.y + 8;
			int hm = extent.y + 2;
			int wm = extent.x + 2;
			int ht = extent.y + 6;

			Group grpStatus = new Group(parent, SWT.NONE);
			GridLayout gl_grpStatus = new GridLayout(7, false);
			gl_grpStatus.verticalSpacing = 3;
			gl_grpStatus.marginWidth = 2;
			gl_grpStatus.marginHeight = 0;
			gl_grpStatus.horizontalSpacing = 2;
			grpStatus.setLayout(gl_grpStatus);
			GridData gd_grpStatus = new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1);
			gd_grpStatus.heightHint = hm * 3 + 4;
			grpStatus.setLayoutData(gd_grpStatus);
			grpStatus.setText("Engine Status");

			new Label(grpStatus, SWT.NONE);
			serverLabel = new Label(grpStatus, SWT.NONE);
			serverLabel.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.ITALIC));
			GridData gd_serverLabel = new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1);
			gd_serverLabel.heightHint = 16;
			serverLabel.setLayoutData(gd_serverLabel);
			serverLabel.setText("Offline");
			new Label(grpStatus, SWT.NONE);

			Label lblVersion = new Label(grpStatus, SWT.NONE);
			lblVersion.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
			lblVersion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblVersion.setText("Version:");

			verLabel = new Label(grpStatus, SWT.NONE);
			verLabel.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
			GridData gd_verLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_verLabel.widthHint = 96;
			verLabel.setLayoutData(gd_verLabel);

			Label lblMemory = new Label(grpStatus, SWT.NONE);
			lblMemory.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
			lblMemory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblMemory.setText("Memory:");

			memLabel = new Label(grpStatus, SWT.NONE);
			memLabel.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
			GridData gd_memLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_memLabel.widthHint = 85;
			memLabel.setLayoutData(gd_memLabel);

			Label lblSince = new Label(grpStatus, SWT.NONE);
			lblSince.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
			lblSince.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblSince.setText("Up:");

			upLabel = new Label(grpStatus, SWT.NONE);
			upLabel.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
			GridData gd_upLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
			gd_upLabel.widthHint = 96;
			upLabel.setLayoutData(gd_upLabel);
			new Label(grpStatus, SWT.NONE);

			userLabel = new Label(grpStatus, SWT.NONE);
			userLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1));

		}

		/*
		 * I would actually prefer a single click here, but OK
		 */

		sashForm = new SashForm(parent, SWT.VERTICAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		taskArea = new Group(sashForm, SWT.NONE);
		taskArea.setText("Tasks");
		taskArea.setLayout(new FillLayout(SWT.HORIZONTAL));

		taskViewer = new TableViewer(taskArea, SWT.BORDER | SWT.FULL_SELECTION);
		tableTasks = taskViewer.getTable();
		tableTasks.setHeaderVisible(true);
		tableTasks.setLinesVisible(true);

		TableColumn taskStatus = new TableColumn(tableTasks, SWT.NONE);
		taskStatus.setResizable(false);
		taskStatus.setWidth(29);

		TableColumn taskCommand = new TableColumn(tableTasks, SWT.LEFT);
		taskCommand.setWidth(255);
		taskCommand.setText("Observable");

		TableColumn taskInterrupt = new TableColumn(tableTasks, SWT.NONE);
		taskInterrupt.setResizable(false);
		taskInterrupt.setWidth(48);

		grpMessages = new Group(sashForm, SWT.NONE);
		grpMessages.setText("Log");
		grpMessages.setLayout(new FillLayout(SWT.HORIZONTAL));

		tableViewer = new TableViewer(grpMessages, SWT.BORDER | SWT.FULL_SELECTION);
		tableMessages = tableViewer.getTable();
		tableMessages.setLinesVisible(true);

		tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setAlignment(SWT.CENTER);
		tblclmnNewColumn.setResizable(false);
		tblclmnNewColumn.setWidth(22);

		tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setWidth(500);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
//		tableViewer.setLabelProvider(new ServerLabelProvider());

		taskViewer.setContentProvider(ArrayContentProvider.getInstance());
//		taskViewer.setLabelProvider(new TaskLabelProvider());
		taskViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object o = ((StructuredSelection) (event.getSelection())).getFirstElement();
//				Activator.getDefault().fireEvent(new TaskEvent((ITask) o, TaskEvent.FOCUS));
			}
		});
		sashForm.setWeights(new int[] { 1, 1 });

		createActions();
		initializeToolBar();
		initializeMenu();

		klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));
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

		System.out.println("RECEIVED " + message);

		switch (message.getType()) {
		case DataflowCompiled:
			break;
		case Debug:
			break;
		case DebugScript:
			break;
		case DebugTest:
			break;
		case EngineDown:
//			Display.getDefault().asyncExec(
//					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/ndrop.png")));
			break;
		case EngineUp:
//			Display.getDefault().asyncExec(
//					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/odrop.png")));
			break;
		case MatchAction:
			break;
		case ModifiedObservation:
			break;
		case NewObservation:
			break;
		case PeriodOfInterest:
			break;
		case QueryResult:
			break;
		case RegionOfInterest:
			break;
		case RequestObservation:
			break;
		case RunScript:
			break;
		case RunTest:
			break;
		case ScriptStarted:
//			Display.getDefault().asyncExec(
//					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/orun.png")));
			break;
		case SubmitSearch:
			break;
		case TaskAborted:
//			Display.getDefault().asyncExec(
//					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/ostop.png")));
			break;
		case TaskFinished:
//			Display.getDefault().asyncExec(
//					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/odrop.png")));
			break;
		case TaskStarted:
//			Display.getDefault().asyncExec(
//					() -> dropImage.setImage(ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/orun.png")));
			break;
		case UserProjectDeleted:
			break;
		case UserProjectModified:
			break;
		case UserProjectOpened:
			break;
		default:
			break;

		}
	}
}
