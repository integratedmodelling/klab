package org.integratedmodelling.klab.ide.ui;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;

public class CodelistEditor extends Composite {

    private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
    private Table table;
    private Table table_1;
    private Text text;
    private Text text_1;
    private Text text_2;
    private Text text_3;

    /**
     * Create the composite.
     * @param parent
     * @param style
     */
    public CodelistEditor(Composite parent, int style) {
        super(parent, style);
        addDisposeListener(new DisposeListener(){
            public void widgetDisposed(DisposeEvent e) {
                toolkit.dispose();
            }
        });
        toolkit.adapt(this);
        toolkit.paintBordersFor(this);
        setLayout(new GridLayout(1, false));
        
        Composite settingsArea = new Composite(this, SWT.NONE);
        settingsArea.setLayout(new GridLayout(6, false));
        settingsArea.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(settingsArea);
        toolkit.paintBordersFor(settingsArea);
        
        Label lblName = new Label(settingsArea, SWT.NONE);
        lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(lblName, true, true);
        lblName.setText("Name");
        
        text = new Text(settingsArea, SWT.BORDER);
        GridData gd_text = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_text.widthHint = 170;
        text.setLayoutData(gd_text);
        toolkit.adapt(text, true, true);
        
        Label lblAgency = new Label(settingsArea, SWT.NONE);
        lblAgency.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(lblAgency, true, true);
        lblAgency.setText("Agency");
        
        text_1 = new Text(settingsArea, SWT.BORDER);
        text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(text_1, true, true);
        
        Label lblValueType = new Label(settingsArea, SWT.NONE);
        lblValueType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(lblValueType, true, true);
        lblValueType.setText("Value Type");
        
        Combo combo = new Combo(settingsArea, SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(combo);
        toolkit.paintBordersFor(combo);
        
        Label lblDescription = new Label(settingsArea, SWT.NONE);
        lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(lblDescription, true, true);
        lblDescription.setText("Description");
        
        text_2 = new Text(settingsArea, SWT.BORDER);
        text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        toolkit.adapt(text_2, true, true);
        new Label(settingsArea, SWT.NONE);
        
        Composite composite_2 = new Composite(settingsArea, SWT.NONE);
        composite_2.setLayout(new GridLayout(2, false));
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(composite_2);
        toolkit.paintBordersFor(composite_2);
        
        Button btnRadioButton = new Button(composite_2, SWT.RADIO);
        btnRadioButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        btnRadioButton.setSelection(true);
        toolkit.adapt(btnRadioButton, true, true);
        btnRadioButton.setText("1-to-1");
        
        Button btnRadioButton_1 = new Button(composite_2, SWT.RADIO);
        btnRadioButton_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(btnRadioButton_1, true, true);
        btnRadioButton_1.setText("Many-to-many");
        
        Composite mappingArea = new Composite(this, SWT.NONE);
        mappingArea.setLayout(new GridLayout(1, false));
        mappingArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        toolkit.adapt(mappingArea);
        toolkit.paintBordersFor(mappingArea);
        
        SashForm sashForm = new SashForm(mappingArea, SWT.NONE);
        sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        toolkit.adapt(sashForm);
        toolkit.paintBordersFor(sashForm);
        
        Composite composite = new Composite(sashForm, SWT.NONE);
        toolkit.adapt(composite);
        toolkit.paintBordersFor(composite);
        composite.setLayout(new GridLayout(1, false));
        
        Label lblNewLabel = new Label(composite, SWT.NONE);
        lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(lblNewLabel, true, true);
        lblNewLabel.setText("Direct mappings");
        
        TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        table = tableViewer.getTable();
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        toolkit.paintBordersFor(table);
        
        TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        TableColumn tblclmnKey = tableViewerColumn.getColumn();
        tblclmnKey.setWidth(208);
        tblclmnKey.setText("Key");
        
        TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
        TableColumn tblclmnValue = tableViewerColumn_1.getColumn();
        tblclmnValue.setWidth(192);
        tblclmnValue.setText("Value");
        
        Composite composite_1 = new Composite(sashForm, SWT.NONE);
        toolkit.adapt(composite_1);
        toolkit.paintBordersFor(composite_1);
        composite_1.setLayout(new GridLayout(1, false));
        
        Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
        lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(lblNewLabel_1, true, true);
        lblNewLabel_1.setText("Inverse mappings");
        
        TableViewer tableViewer_1 = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
        table_1 = tableViewer_1.getTable();
        table_1.setLinesVisible(true);
        table_1.setHeaderVisible(true);
        table_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        toolkit.paintBordersFor(table_1);
        
        TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer_1, SWT.NONE);
        TableColumn tblclmnKey_1 = tableViewerColumn_2.getColumn();
        tblclmnKey_1.setWidth(179);
        tblclmnKey_1.setText("Key");
        
        TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer_1, SWT.NONE);
        TableColumn tblclmnValue_1 = tableViewerColumn_3.getColumn();
        tblclmnValue_1.setWidth(215);
        tblclmnValue_1.setText("Value");
        sashForm.setWeights(new int[] {5, 5, 5, 5});
        
        Composite actionArea = new Composite(this, SWT.NONE);
        actionArea.setLayout(new GridLayout(4, false));
        actionArea.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(actionArea);
        toolkit.paintBordersFor(actionArea);
        
        Button btnExposeAsAuthority = new Button(actionArea, SWT.CHECK);
        toolkit.adapt(btnExposeAsAuthority, true, true);
        btnExposeAsAuthority.setText("Expose as authority");
        
        Label lblName_1 = new Label(actionArea, SWT.NONE);
        lblName_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(lblName_1, true, true);
        lblName_1.setText("Named");
        
        text_3 = new Text(actionArea, SWT.BORDER);
        GridData gd_text_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_text_3.widthHint = 234;
        text_3.setLayoutData(gd_text_3);
        toolkit.adapt(text_3, true, true);
        
        Composite composite_3 = new Composite(actionArea, SWT.NONE);
        composite_3.setLayout(new GridLayout(2, false));
        composite_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(composite_3);
        toolkit.paintBordersFor(composite_3);
        
        Button btnCancel = new Button(composite_3, SWT.NONE);
        GridData gd_btnCancel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnCancel.widthHint = 68;
        btnCancel.setLayoutData(gd_btnCancel);
        btnCancel.setBounds(0, 0, 90, 30);
        toolkit.adapt(btnCancel, true, true);
        btnCancel.setText("Cancel");
        
        Button btnNewButton = new Button(composite_3, SWT.NONE);
        GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnNewButton.widthHint = 68;
        btnNewButton.setLayoutData(gd_btnNewButton);
        btnNewButton.setBounds(0, 0, 90, 30);
        toolkit.adapt(btnNewButton, true, true);
        btnNewButton.setText("Save");

    }
}
