package org.integratedmodelling.klab.ide.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.model.KlabPeer;
import org.integratedmodelling.klab.ide.model.KlabPeer.Sender;
import org.integratedmodelling.klab.ide.ui.AuthorityEditor;
import org.integratedmodelling.klab.rest.AuthorityReference;

public class AuthorityView extends ViewPart {

    public static final String ID = "org.integratedmodelling.klab.ide.views.AuthorityView"; //$NON-NLS-1$

    private KlabPeer klab;

    private AuthorityEditor editor;

    public AuthorityView() {
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
        {
            this.editor = new AuthorityEditor(container, SWT.NONE);
            this.editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        }

        klab = new KlabPeer(Sender.ANY, (message) -> handleMessage(message));

        createActions();
        initializeToolBar();
        initializeMenu();

        refresh(Activator.engineMonitor().isRunning());

    }

    private void handleMessage(IMessage message) {

        switch(message.getType()) {
        case EngineDown:
        case EngineUp:
            refresh(message.getType() == Type.EngineUp);
            break;
        default:
            break;
        }
    }

    private void refresh(boolean up) {
        this.editor.setAuthorities(up ? Activator.engineMonitor().getCapabilities().getAuthorities() : null);
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
        // IToolBarManager tbm = getViewSite().getActionBars().getToolBarManager();
    }

    /**
     * Initialize the menu.
     */
    private void initializeMenu() {
        // IMenuManager manager = getViewSite().getActionBars().getMenuManager();
    }

    @Override
    public void setFocus() {
        // Set the focus
    }

}
