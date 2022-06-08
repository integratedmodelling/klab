package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.xviewer.XViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.ui.TestXViewerFactory;

public class TestView extends ViewPart {

    public static final String ID = "org.integratedmodelling.klab.ide.views.TestView"; //$NON-NLS-1$
    private KlabPeer klab;
    private XViewer treeViewer;

    public TestView() {
    }

    /**
     * Create contents of the view part.
     * 
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {

        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new GridLayout(1, false));

        try {
            treeViewer = new XViewer(container, SWT.BORDER, new TestXViewerFactory());
            Tree tree = treeViewer.getTree();
            tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
            klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));
        } catch (Throwable t) {
            // ignore a resource not properly disposed error, this seems to be a Nebula bug
            System.out.println("WARNING: Nebula bug persists on XViewer initialization");
        }

        createActions();
        // Uncomment if you wish to add code to initialize the toolbar
        // initializeToolBar();
        initializeMenu();
    }

    private void handleMessage(IMessage message) {
    }

    /**
     * Create the actions.
     */
    private void createActions() {
        // Create the actions
    }

    @Override
    public void dispose() {
        treeViewer.dispose();
        super.dispose();
    }

    /**
     * Initialize the toolbar.
     */
    private void initializeToolBar() {
        if (getViewSite() != null) {
            IToolBarManager toolbarManager = getViewSite().getActionBars().getToolBarManager();
        }
    }

    /**
     * Initialize the menu.
     */
    private void initializeMenu() {
        if (getViewSite() != null) {
            IMenuManager menuManager = getViewSite().getActionBars().getMenuManager();
        }
    }

    @Override
    public void setFocus() {
        // Set the focus
    }
}
