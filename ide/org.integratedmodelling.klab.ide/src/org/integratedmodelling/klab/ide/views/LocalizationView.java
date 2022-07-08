package org.integratedmodelling.klab.ide.views;

import java.io.File;
import java.util.Map;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
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
    private FileCatalog<Map<String, String>> localization = null;
    private LocalizationEditor editor;
    private Label applicationNameLabel;

    public LocalizationView() {
    }

    /**
     * Create contents of the view part.
     * 
     * @param parent
     */
    @Override
    public void createPartControl(Composite parent) {
        parent.setLayout(new GridLayout(1, false));
        
        Composite composite_1_1 = new Composite(parent, SWT.NONE);
        composite_1_1.setLayout(new GridLayout(2, false));
        
        Label lblNewLabel = new Label(composite_1_1, SWT.NONE);
        lblNewLabel.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/logo_white_64.png"));
        
        Composite composite_2 = new Composite(composite_1_1, SWT.NONE);
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        RowLayout rl_composite_2 = new RowLayout(SWT.VERTICAL);
        rl_composite_2.fill = true;
        composite_2.setLayout(rl_composite_2);
        
        Label lblNewLabel_1 = new Label(composite_2, SWT.NONE);
        lblNewLabel_1.setText("k.LAB Internationalization Editor");
        lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
        
        Label lblNewLabel_2 = new Label(composite_2, SWT.NONE);
        lblNewLabel_2.setText("Define different language versions of all localized keys in a k.Actors behavior and add new languages. Only accepts valid ISO 2-character codes.");
        
        applicationNameLabel = new Label(composite_2, SWT.NONE);
        applicationNameLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
        applicationNameLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
        applicationNameLabel.setText("No application");
        
        Composite container = this.editor = new LocalizationEditor(this, parent, SWT.NONE);
        editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
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
        Display.getDefault().asyncExec(() -> {
            this.applicationNameLabel.setText(script.getName());
        });
        editor.initialize(script.getName(), this.localization, localizer);
    }

}
