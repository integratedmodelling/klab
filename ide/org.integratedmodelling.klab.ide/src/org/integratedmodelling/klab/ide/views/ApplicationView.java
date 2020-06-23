package org.integratedmodelling.klab.ide.views;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Platform;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.ui.AppView;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.LoadApplicationRequest;
import org.integratedmodelling.klab.rest.ViewAction;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.utils.BrowserUtils;

public class ApplicationView extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.ApplicationView";

	private KlabPeer klab;
	// behavior ID -> application view. Only one per behavior, i.e. reloading an app
	// stops the previous.
	Map<String, AppView> apps = new HashMap<>();
	Map<String, CTabItem> tabs = new HashMap<>();
	private Action action;
	private CTabFolder tabFolder;

	private Action openViewerAction;

	private Action resetContextAction;

	public ApplicationView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		tabFolder = new CTabFolder(container, SWT.CLOSE);
		tabFolder.addCTabFolder2Listener(new CTabFolder2Listener() {

			@Override
			public void showList(CTabFolderEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void restore(CTabFolderEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void minimize(CTabFolderEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void maximize(CTabFolderEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void close(CTabFolderEvent event) {
				// stop the app
				Layout layout = (Layout) ((CTabItem) event.item).getData();
				apps.remove(layout.getName());
				tabs.remove(layout.getName());
				Activator.post(IMessage.MessageClass.Run, IMessage.Type.RunApp,
						new LoadApplicationRequest(layout.getApplicationId(), false, true));
			}
		});

		klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
		{
			action = new Action("") {

			};
			action.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide", "icons/define.gif"));
		}

		{
			openViewerAction = new Action("Open new viewer") {

				@Override
				public void run() {
					if (Activator.engineMonitor().isRunning()) {
						BrowserUtils.startBrowser("http://localhost:8283/modeler/ui/viewer?session="
								+ Activator.engineMonitor().getSessionId());
					}
				}
			};
			openViewerAction.setEnabled(Activator.engineMonitor().isRunning());
			openViewerAction.setImageDescriptor(
					ResourceManager.getPluginImageDescriptor("org.integratedmodelling.klab.ide", "icons/browser.gif"));
		}
		{
			resetContextAction = new Action("Reset context") {
				@Override
				public void run() {
					Activator.post(IMessage.MessageClass.UserContextChange, IMessage.Type.ResetContext, "");
				}
			};
			resetContextAction.setEnabled(Activator.engineMonitor().isRunning());
			resetContextAction.setImageDescriptor(ResourceManager
					.getPluginImageDescriptor("org.integratedmodelling.klab.ide", "icons/target_red.png"));
			resetContextAction.setToolTipText("Reset context");
		}

	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
		toolbarManager.add(resetContextAction);
		toolbarManager.add(openViewerAction);
	}

	/**
	 * Initialize the menu.
	 */
	private void initializeMenu() {
		IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();

		MenuManager appMenu = new MenuManager("New MenuManager");
		menuManager.add(appMenu);
	}

	@Override
	public void setFocus() {
		// Set the focus
	}

	private void handleMessage(IMessage message) {

		switch (message.getType()) {
		case EngineUp:
			Display.getDefault().asyncExec(() -> {
				openViewerAction.setEnabled(true);
				resetContextAction.setEnabled(true);
			});
			removeAll();
			break;
		case EngineDown:
			Display.getDefault().asyncExec(() -> {
				openViewerAction.setEnabled(false);
				resetContextAction.setEnabled(false);
			});
			removeAll();
			break;
		case SetupInterface:
			Layout layout = message.getPayload(Layout.class);
			if (layout.getDestination() == IKActorsBehavior.Type.APP
					&& (layout.getPlatform() == Platform.ANY || layout.getPlatform() == Platform.DESKTOP)) {
				Display.getDefault().asyncExec(() -> createAppLayout(layout));
			}
			break;
		case CreateViewComponent:
			addWidget(message);
			break;
		case ViewAction:
			Display.getDefault().asyncExec(() -> updateWidget(message));
			break;
		default:
			break;

		}
	}

	private void removeAll() {
		for (CTabItem tab : tabs.values()) {
			Layout layout = (Layout) tab.getData();
			Activator.post(IMessage.MessageClass.Run, IMessage.Type.RunApp,
					new LoadApplicationRequest(layout.getApplicationId(), false, true));
			Display.getDefault().asyncExec(() -> tab.dispose());
		}
		apps.clear();
		tabs.clear();
	}

	private void addWidget(IMessage message) {
		ViewComponent component = message.getPayload(ViewComponent.class);
		for (String behavior : tabs.keySet()) {
			CTabItem tab = tabs.get(behavior);
			Layout layout = (Layout) tab.getData();
			if (component.getApplicationId() != null
					&& component.getApplicationId().equals(layout.getApplicationId())) {
				tabFolder.setSelection(tab);
				Display.getDefault().asyncExec(() -> apps.get(behavior).addWidget(message));
				break;
			}
		}
	}
	
	private void updateWidget(IMessage message) {
		ViewAction component = message.getPayload(ViewAction.class);
		for (String behavior : tabs.keySet()) {
			CTabItem tab = tabs.get(behavior);
			Layout layout = (Layout) tab.getData();
			if (component.getApplicationId() != null
					&& component.getApplicationId().equals(layout.getApplicationId())) {
				tabFolder.setSelection(tab);
				Display.getDefault().asyncExec(() -> apps.get(behavior).updateWidget(component));
				break;
			}
		}
	}

	private void createAppLayout(Layout layout) {

		CTabItem tab = tabs.get(layout.getName());
		if (tab == null) {
			layout.setIndex(tabFolder.getItemCount());
			tab = new CTabItem(tabFolder, SWT.CLOSE);
			tab.setData(layout);
			tab.setText(layout.getName());
			tabs.put(layout.getName(), tab);
			Composite composite = new Composite(tabFolder, SWT.NONE);
			composite.setLayout(new FillLayout());
			tab.setControl(composite);
			apps.put(layout.getName(), new AppView(composite, SWT.NONE));
		} else {
			layout.setIndex(((Layout) tab.getData()).getIndex());
		}

		apps.get(layout.getName()).setup(layout);
		tabFolder.setSelection(layout.getIndex());
	}
}
