package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;

public class RuntimeView extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.RuntimeView"; //$NON-NLS-1$
	// private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	private KlabPeer klab;

	public RuntimeView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		// toolkit.paintBordersFor(container);

		createActions();
		initializeToolBar();
		initializeMenu();

		klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));
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

	private void handleMessage(IMessage message) {
		System.out.println("DIOCAN " + message);
	}

}
