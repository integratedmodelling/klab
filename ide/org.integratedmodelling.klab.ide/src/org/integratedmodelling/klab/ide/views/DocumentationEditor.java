package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.ide.navigator.model.EDocumentable;
import org.eclipse.wb.swt.SWTResourceManager;

public class DocumentationEditor extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.DocumentationEditor";
	private EDocumentable item;
	private String docId;
	private Table table;
	private Text text;

	public DocumentationEditor() {
	}

	public void setTarget(String docId, EDocumentable item) {
		this.item = item;
		this.docId = docId;
	}
	
	
	/**
	 * Create contents of the view part.
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		parent.setLayout(new GridLayout(1, false));
		
		Composite titleArea = new Composite(parent, SWT.NONE);
		titleArea.setLayout(new GridLayout(1, false));
		titleArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(titleArea, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblNewLabel_1.setBounds(0, 0, 55, 15);
		lblNewLabel_1.setText("Documentation editor");
		
		ExpandableComposite expandBar = new ExpandableComposite(parent, SWT.NONE);
		expandBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		expandBar.setText("View source code");
		expandBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		StyledText styledText = new StyledText(expandBar, SWT.BORDER);
		styledText.setEditable(false);
		GridData gd_styledText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_styledText.heightHint = 140;
		styledText.setLayoutData(gd_styledText);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(new GridLayout(5, false));
		
		Label lblEvent = new Label(composite, SWT.NONE);
		lblEvent.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEvent.setText("Event:");
		
		Combo combo = new Combo(composite, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblSection = new Label(composite, SWT.NONE);
		lblSection.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSection.setText("Section:");
		
		Combo combo_1 = new Combo(composite, SWT.READ_ONLY);
		combo_1.setItems(new String[] {"Introduction", "Methods", "Results", "Discussion", "Conclusions", "Appendix"});
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo_1.select(1);
		
		Button lblNewLabel = new Button(composite, SWT.NONE);
		lblNewLabel.setToolTipText("Add a custom section");
		lblNewLabel.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/add.png"));
		
		StyledText styledText_1 = new StyledText(parent, SWT.BORDER);
		styledText_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpCrossreferences = new Group(parent, SWT.NONE);
		grpCrossreferences.setLayout(new GridLayout(1, false));
		grpCrossreferences.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpCrossreferences.setText("Cross-references");
		
		TableViewer tableViewer = new TableViewer(grpCrossreferences, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setLinesVisible(true);
		
		text = new Text(grpCrossreferences, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		createActions();
		initializeToolBar();
		initializeMenu();
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
