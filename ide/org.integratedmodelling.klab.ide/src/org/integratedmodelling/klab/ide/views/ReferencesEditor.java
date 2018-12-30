package org.integratedmodelling.klab.ide.views;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.client.documentation.ProjectReferences;
import org.integratedmodelling.klab.documentation.BibTexFields;
import org.integratedmodelling.klab.documentation.Reference;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class ReferencesEditor extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.ReferencesEditor"; //$NON-NLS-1$
	private String referenceId;
	private Table table;
	private Text text;
	private Label itemIdLabel;

	ProjectReferences references;
	protected boolean dirty;
	private EProject project;
	private StyledText editor;
	private Text tag;
	private TableViewer tableViewer;

	public ReferencesEditor() {
	}

	class ReferencesContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ProjectReferences) {
				return ((ProjectReferences) inputElement).sortedValues();
			}
			return new Object[] {};
		}

	}

	class ReferencesLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return columnIndex == 0
					? ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/documentation.png")
					: null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return columnIndex == 0 ? ((Reference) element).get(BibTexFields.KEY).toString()
					: ((Reference) element).get(BibTexFields.EXAMPLE_CITATION).toString();
		}

	}

	public void setTarget(EProject project) {
		this.project = project;
		this.references = new ProjectReferences(project.getProject());
		this.itemIdLabel.setText(project.getName());
		refreshReferences();
	}
	
    public void setTarget(EReference reference) {
        this.project = reference.getEParent(EProject.class);
        this.references = new ProjectReferences(project.getProject());
        this.referenceId = reference.getName();
        refreshReferences();
        loadItem();
    }

	private void loadItem() {
		Display.getDefault().asyncExec(() -> {
			itemIdLabel.setText(referenceId);
			Reference template = references.get(getCurrentKey());
			tag.setText(template.get(BibTexFields.KEY));
            editor.setText(template.get(BibTexFields.EXAMPLE_CITATION));
		});
	}

	private String getCurrentKey() {
		return referenceId;
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
		lblNewLabel_1.setText("Project references: ");

		itemIdLabel = new Label(titleArea, SWT.NONE);
		itemIdLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		itemIdLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridData gd_styledText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_styledText.heightHint = 140;

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		GridLayout gl_composite = new GridLayout(4, false);
		gl_composite.marginWidth = 0;
		gl_composite.marginHeight = 0;
		composite.setLayout(gl_composite);

		Label lblEvent = new Label(composite, SWT.NONE);
		lblEvent.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEvent.setText("Tag (must be unique):");

		tag = new Text(composite, SWT.BORDER);
		tag.setEditable(true);
		tag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button lblNewLabel = new Button(composite, SWT.NONE);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				addCurrent();
			}
		});
		lblNewLabel.setToolTipText("Add the current reference");
		lblNewLabel.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/add.png"));

		Button lblNewLabel2 = new Button(composite, SWT.NONE);
		lblNewLabel2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				editor.setText("");
				tag.setText("");
				dirty = false;
				if (getTitle().startsWith("*")) {
					setPartName(getTitle());
				}
			}
		});
		lblNewLabel2.setToolTipText("Clear fields");
		lblNewLabel2.setImage(ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/Player Record.png"));


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
		editor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		Group grpCrossreferences = new Group(parent, SWT.NONE);
		grpCrossreferences.setLayout(new GridLayout(1, false));
		grpCrossreferences.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpCrossreferences.setText("Existing references (double click to edit)");

		tableViewer = new TableViewer(grpCrossreferences, SWT.BORDER | SWT.FULL_SELECTION);
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				Object o = ((StructuredSelection) (event.getSelection())).getFirstElement();
				if (o instanceof Reference) {
					tag.setText(((Reference) o).get(BibTexFields.KEY));
					editor.setText(((Reference) o).get(BibTexFields.EXAMPLE_CITATION));
				}
			}
		});
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setLinesVisible(true);
		tableViewer.setContentProvider(new ReferencesContentProvider());
		tableViewer.setLabelProvider(new ReferencesLabelProvider());

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("Tag");

		TableColumn tblclmnReference = new TableColumn(table, SWT.NONE);
		tblclmnReference.setWidth(740);
		tblclmnReference.setText("Citation");

		Menu menu = new Menu(table);
		table.setMenu(menu);

		MenuItem mntmDeleteReference = new MenuItem(menu, SWT.NONE);
		mntmDeleteReference.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Object o = ((StructuredSelection) (tableViewer.getSelection())).getFirstElement();
				if (o instanceof Reference) {
					if (Eclipse.INSTANCE.confirm("Confirm deletion of " + ((Reference) o).get(BibTexFields.KEY) + "?")) {
						references.remove(((Reference) o).get(BibTexFields.KEY).toString());
						references.write();
						refreshReferences();
					}
				}
			}
		});
		mntmDeleteReference.setText("Delete reference");

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
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				boolean ok = true;
				if (dirty) {
					ok = Eclipse.INSTANCE.confirm("Abandon changes?");
				}
				if (ok) {
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
							.hideView(ReferencesEditor.this);
				}
			}
		});
		button_2.setLayoutData(new RowData(90, -1));
		button_2.setText("Cancel");

		createActions();
		initializeToolBar();
		initializeMenu();
	}

	protected void addCurrent() {

		String tag = this.tag.getText();
		String txt = this.editor.getText();

		if (tag.trim().isEmpty() || txt.trim().isEmpty()) {
			Eclipse.INSTANCE.beep();
		} else {
			referenceId = tag;
			Reference reference = new Reference();
			reference.put(BibTexFields.EXAMPLE_CITATION, txt.trim());
			reference.put(BibTexFields.KEY, tag.trim());
			this.references.put(referenceId, reference);
			this.references.write();
			dirty = false;
			dirty = false;
			if (getTitle().startsWith("*")) {
				setPartName(getTitle().substring(2));
			}
			editor.setText("");
			ReferencesEditor.this.tag.setText("");
			refreshReferences();
		}
	}

	private void refreshReferences() {
		Display.getDefault().asyncExec(() -> tableViewer.setInput(this.references));
	}

	private synchronized void save() {
		addCurrent();
		KlabNavigator.refresh();
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
