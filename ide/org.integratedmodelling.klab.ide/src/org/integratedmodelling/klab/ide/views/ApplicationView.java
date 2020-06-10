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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.ui.AppView;
import org.integratedmodelling.klab.rest.Layout;

public class ApplicationView extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.ApplicationView";

	private KlabPeer klab;
	// behavior ID -> application view. Only one per behavior, i.e. reloading an app
	// stops the previous.
	Map<String, AppView> apps = new HashMap<>();
	Map<String, CTabItem> tabs = new HashMap<>();
	AppView appView;
	private Action action;
	private CTabFolder tabFolder;

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
				// TODO close the app
				
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
	}

	/**
	 * Initialize the toolbar.
	 */
	private void initializeToolBar() {
		IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
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
		case EngineDown:
			removeAll();
			break;
		case SetupInterface:
			Display.getDefault().asyncExec(() -> createAppLayout(message.getPayload(Layout.class)));
			break;
		case CreateViewComponent:
			addWidget(message);
			break;
		default:
			break;

		}
	}

	private void removeAll() {
		// TODO Auto-generated method stub

	}

	private void addWidget(IMessage message) {
		// TODO Auto-generated method stub

	}

	private void createAppLayout(Layout layout) {
		CTabItem tab = tabs.get(layout.getName());
		if (tab == null) {
			tab = new CTabItem(tabFolder, SWT.CLOSE);
			tab.setText(layout.getName());
			tabs.put(layout.getName(), tab);
			Composite composite = new Composite(tabFolder, SWT.NONE);
			composite.setLayout(new FillLayout());
			tab.setControl(composite);
			apps.put(layout.getName(), new AppView(composite, SWT.NONE));
		}
		apps.get(layout.getName()).setup(layout);
	}
}
