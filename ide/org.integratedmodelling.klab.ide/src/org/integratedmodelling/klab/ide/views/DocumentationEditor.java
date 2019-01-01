package org.integratedmodelling.klab.ide.views;

import java.util.Arrays;

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
import org.eclipse.swt.custom.SashForm;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.client.documentation.ProjectDocumentation;
import org.integratedmodelling.klab.client.documentation.ProjectReferences;
import org.integratedmodelling.klab.documentation.BibTexFields;
import org.integratedmodelling.klab.documentation.ModelDocumentation;
import org.integratedmodelling.klab.documentation.Reference;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.navigator.model.EDocumentable;
import org.integratedmodelling.klab.ide.navigator.model.EDocumentationItem;
import org.integratedmodelling.klab.ide.navigator.model.EModel;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.ui.UndoRedoImpl;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.ide.utils.StringUtils;
import org.integratedmodelling.klab.rest.DocumentationReference;

public class DocumentationEditor extends ViewPart {

    public static final String ID             = "org.integratedmodelling.klab.ide.views.DocumentationEditor";
    private EDocumentable      item;
    private String             docId;
    private Table              table;
    private Text               text;
    private Label              itemIdLabel;

    ProjectDocumentation       documentation;
    ProjectReferences          references;
    protected boolean          dirty;
    private EProject           project;
    protected String           currentEvent   = "Definition";
    protected String           currentSection = "Methods";
    private StyledText         editor;
    private TableViewer        tableViewer;
    private Combo              sectionCombo;
    private Combo              triggerCombo;
	private StyledText sourceCodeViewer;
    private static String[]           triggers       = new String[] {
            "Initialization",
            "Definition",
            "Termination",
            "Instantiation",
            "Transition",
            "Event" };
    private static String[]           sections       = new String[] {
            "Introduction",
            "Methods",
            "Results",
            "Discussion",
            "Conclusions",
            "Appendix" };
    private ExpandableComposite sourceCodeBar;
	private UndoRedoImpl undoManager;

    public DocumentationEditor() {
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
                    ? ResourceManager
                            .getPluginImage("org.integratedmodelling.klab.ide", "icons/documentation.png")
                    : null;
        }

        @Override
        public String getColumnText(Object element, int columnIndex) {
            return columnIndex == 0 ? ((Reference) element).get(BibTexFields.KEY).toString()
                    : ((Reference) element).get(BibTexFields.EXAMPLE_CITATION).toString();
        }

    }

    public void setTarget(String docId, EDocumentable item) {
        this.item = item;
        this.docId = docId;
        this.project = ((ENavigatorItem) item).getEParent(EProject.class);
        // load docs for this project
        this.documentation = new ProjectDocumentation(docId, this.project.getProject());
        this.references = new ProjectReferences(this.project.getProject());
        this.sourceCodeBar.setVisible(true);
        loadReferences();
        loadItem();
    }

    public void setTarget(EDocumentationItem item) {
        this.docId = item.getItem();
        this.project = item.getEParent(EProject.class);
        this.documentation = item.getDocumentation();
        this.references = new ProjectReferences(this.project.getProject());
        this.currentEvent = StringUtils.capitalize(item.getTrigger().name().toLowerCase());
        this.currentSection = item.getName();
        this.sourceCodeBar.setVisible(false);
        loadReferences();
        Display.getDefault().asyncExec(() -> {
            itemIdLabel.setText(docId);
            ModelDocumentation template = documentation.get(getCurrentKey());
            editor.setText(template == null ? "" : template.getTemplate());
            sectionCombo.select(Arrays.binarySearch(sections, item.getName()));
            triggerCombo.select(Arrays.binarySearch(triggers, item.getTrigger().name().toLowerCase()));
        });
    }

    private void loadReferences() {
        tableViewer.setInput(references);
    }

    private void loadItem() {
        Display.getDefault().asyncExec(() -> {
            itemIdLabel.setText(docId);
            ModelDocumentation template = documentation.get(getCurrentKey());
            editor.setText(template == null ? "" : template.getTemplate());
            if (item instanceof EModel) {
            	this.sourceCodeViewer.setText(((EModel)item).getSourceCode());
            }
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

        sourceCodeBar = new ExpandableComposite(parent, SWT.NONE);
        sourceCodeBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
        sourceCodeBar.setText("View source code");
        sourceCodeBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

        sourceCodeViewer = new StyledText(sourceCodeBar, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
        sourceCodeViewer.setEditable(false);
        GridData gd_styledText = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_styledText.heightHint = 140;
        sourceCodeViewer.setLayoutData(gd_styledText);
        sourceCodeBar.setClient(sourceCodeViewer);
        
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        composite.setLayout(new GridLayout(5, false));

        Label lblEvent = new Label(composite, SWT.NONE);
        lblEvent.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblEvent.setText("Trigger:");

        triggerCombo = new Combo(composite, SWT.READ_ONLY);
        triggerCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (dirty) {
                    save();
                }
                currentEvent = triggerCombo.getText();
                loadItem();
            }
        });
        triggerCombo.setItems(triggers);
        triggerCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        triggerCombo.select(1);

        Label lblSection = new Label(composite, SWT.NONE);
        lblSection.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblSection.setText("Section:");

        sectionCombo = new Combo(composite, SWT.READ_ONLY);
        sectionCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (dirty) {
                    save();
                }
                currentSection = sectionCombo.getText();
                loadItem();
            }
        });
        sectionCombo.setItems(sections);
        sectionCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        sectionCombo.select(1);

        Button lblNewLabel = new Button(composite, SWT.NONE);
        lblNewLabel.setToolTipText("Add a custom section");
        lblNewLabel.setImage(ResourceManager
                .getPluginImage("org.integratedmodelling.klab.ide", "icons/add.png"));

        SashForm sashForm = new SashForm(parent, SWT.VERTICAL);
        sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        editor = new StyledText(sashForm, SWT.BORDER | SWT.WRAP | /* SWT.H_SCROLL | */SWT.V_SCROLL);
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
        undoManager = new UndoRedoImpl(editor);
        
        Group grpCrossreferences = new Group(sashForm, SWT.NONE);
        grpCrossreferences.setLayout(new GridLayout(1, false));
        grpCrossreferences.setText("Cross-references");

        tableViewer = new TableViewer(grpCrossreferences, SWT.BORDER | SWT.FULL_SELECTION);
        tableViewer.addDoubleClickListener(new IDoubleClickListener() {
            public void doubleClick(DoubleClickEvent event) {
                Object o = ((StructuredSelection) (event.getSelection())).getFirstElement();
                if (o instanceof Reference) {
                    String key = ((Reference) o).get(BibTexFields.KEY);
                    editor.insert("@cite(" + key + ")");
                    editor.setFocus();
                }
            }
        });
        table = tableViewer.getTable();
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        table.setLinesVisible(true);

        TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn.setWidth(160);
        tblclmnNewColumn.setText("New Column");

        TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
        tblclmnNewColumn_1.setWidth(720);
        tblclmnNewColumn_1.setText("Citation");

        tableViewer.setContentProvider(new ReferencesContentProvider());
        tableViewer.setLabelProvider(new ReferencesLabelProvider());

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
                            .hideView(DocumentationEditor.this);
                }
            }
        });
        button_2.setLayoutData(new RowData(90, -1));
        button_2.setText("Cancel");
        sashForm.setWeights(new int[] { 1, 1 });

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
            template.setTrigger(Trigger.valueOf(currentEvent.toUpperCase()));
            template.setSectionType(IReport.Section.Type.BODY);
            template.setSectionRole(SectionRole.valueOf(currentSection.toUpperCase()));

            documentation.put(getCurrentKey(), template);
        }
        if (item != null) {
            template.getDocumentedUrns().add(((ENavigatorItem) item).getId());
        }
        template.setTemplate(editor.getText());
        documentation.write();
        dirty = false;
        if (getTitle().startsWith("*")) {
            setPartName(getTitle().substring(2));
        }
        if (Activator.engineMonitor().isRunning()) {
            Activator.session()
                    .send(IMessage.MessageClass.ProjectLifecycle, IMessage.Type.DocumentationModified, new DocumentationReference(docId, project
                            .getName()));
        }

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
