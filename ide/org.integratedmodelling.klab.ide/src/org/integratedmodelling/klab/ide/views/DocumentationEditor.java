package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.client.documentation.ProjectDocumentation;
import org.integratedmodelling.klab.client.documentation.ProjectReferences;
import org.integratedmodelling.klab.documentation.ModelDocumentation;
import org.integratedmodelling.klab.ide.navigator.model.EDocumentable;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;

public class DocumentationEditor extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.DocumentationEditor";
	private EDocumentable item;
	private String docId;
	private Table table;
	private Text text;
	private Label itemIdLabel;

	ProjectDocumentation documentation;
	ProjectReferences references;
	protected boolean dirty;
	private EProject project;
	protected String currentEvent = "Definition";
	protected String currentSection = "Methods";
	private StyledText editor;

	public DocumentationEditor() {
	}

	public void setTarget(String docId, EDocumentable item) {
		this.item = item;
		this.docId = docId;
		this.project = ((ENavigatorItem)item).getEParent(EProject.class);
		// load docs for this project
		this.documentation = new ProjectDocumentation(this.project.getProject());
		this.references = new ProjectReferences(this.project.getProject());
		loadReferences();
		loadItem();
	}

	private void loadReferences() {
		// TODO item-specific refs first
		// TODO all project refs next
		// TODO refs from all linked projects
	}

	private void loadItem() {
		Display.getDefault().asyncExec(() -> {
			itemIdLabel.setText(docId);
			ModelDocumentation template = documentation.get(getCurrentKey());
			editor.setText(template == null ? "" : template.getTemplate());
		});
	}

	private String getCurrentKey() {
		return docId + "#" + currentEvent + "#" + currentSection;
	}
	
	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		parent.setLayout(new GridLayout(1, false));

		Composite titleArea = new Composite(parent, SWT.NONE);
		titleArea.setLayout(new GridLayout(2, false));
		titleArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

		Label lblNewLabel_1 = new Label(titleArea, SWT.NONE);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblNewLabel_1.setBounds(0, 0, 55, 15);
		lblNewLabel_1.setText("Documentation editor:");

		itemIdLabel = new Label(titleArea, SWT.NONE);
		itemIdLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		itemIdLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

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
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// SAVE any changes before switching
				currentEvent = combo.getText();
			}
		});
		combo.setItems(new String[] { "Initialization", "Definition", "Termination", "Instantiation", "Transition",
				"Event type..." });
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo.select(1);

		Label lblSection = new Label(composite, SWT.NONE);
		lblSection.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSection.setText("Section:");

		Combo combo_1 = new Combo(composite, SWT.READ_ONLY);
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// SAVE any changes before switching
				currentSection = combo_1.getText();
			}
		});
		combo_1.setItems(
				new String[] { "Introduction", "Methods", "Results", "Discussion", "Conclusions", "Appendix" });
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		combo_1.select(1);

		Button lblNewLabel = new Button(composite, SWT.NONE);
		lblNewLabel.setToolTipText("Add a custom section");
		lblNewLabel.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/add.png"));

		editor = new StyledText(parent, SWT.BORDER | SWT.WRAP | /* SWT.H_SCROLL | */SWT.V_SCROLL);
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.keyCode == 'S' || e.keyCode == 's') && (e.stateMask & SWT.CTRL) != 0) {
					save();
				} else {
					dirty = true;
					if (!getTitle().startsWith("*")) {
						setPartName("* " + getTitle());
					}
				}
			}
		});
		editor.setAlwaysShowScrollBars(false);
		editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

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

		Composite composite_1 = new Composite(grpCrossreferences, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_1.wrap = false;
		composite_1.setLayout(rl_composite_1);

		Button button_1 = new Button(composite_1, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});
		button_1.setLayoutData(new RowData(90, -1));
		button_1.setText("Save");

		Button button_2 = new Button(composite_1, SWT.NONE);
		button_2.setLayoutData(new RowData(90, -1));
		button_2.setText("Cancel");

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	private synchronized void save() {

		ModelDocumentation template = documentation.get(getCurrentKey());
		
		if (template == null) {
			template = new ModelDocumentation();
			template.setDocumentedId(docId);
			template.setSection(currentSection);
			template.setTrigger(currentEvent);
			documentation.put(getCurrentKey(), template);
		}
		template.setTemplate(editor.getText());
		documentation.write();
		dirty = false;
		if (getTitle().startsWith("*")) {
			setPartName(getTitle().substring(2));
		}
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
