package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.monitoring.Message;

public class ResourcesView extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.ResourcesView"; //$NON-NLS-1$
//	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());

	public ResourcesView() {
	}

	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
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
