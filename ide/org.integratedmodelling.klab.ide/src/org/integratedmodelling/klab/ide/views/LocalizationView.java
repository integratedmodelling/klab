package org.integratedmodelling.klab.ide.views;

import java.io.File;
import java.util.Map;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.ViewPart;
import org.integratedmodelling.kactors.utils.KActorsLocalizer;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.ide.navigator.model.EActorBehavior;
import org.integratedmodelling.klab.ide.ui.LocalizationEditor;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class LocalizationView extends ViewPart {

    public static final String ID = "org.integratedmodelling.klab.ide.views.LocalizationView"; //$NON-NLS-1$

    /*
     * read from the companion JSON file <appname>.localization
     */
    FileCatalog<Map<String, String>> localization = null;
    LocalizationEditor editor;

    public LocalizationView() {
    }

    /**
     * Create contents of the view part.
     * 
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {
        Composite container = this.editor = new LocalizationEditor(parent, SWT.NONE);
        createActions();
        // Uncomment if you wish to add code to initialize the toolbar
        // initializeToolBar();
        initializeMenu();
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

    public void loadApplication(EActorBehavior script) {

        File file = MiscUtilities.changeExtension(script.getFile(), "localization");
        this.localization = new FileCatalog<>(file, Map.class, Map.class);
        KActorsLocalizer localizer = new KActorsLocalizer(script);
        editor.initialize(script.getName(), this.localization, localizer);
    }

}
