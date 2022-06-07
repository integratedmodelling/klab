package org.integratedmodelling.klab.ide.ui;

import java.util.Map;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.kactors.utils.KActorsLocalizer;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.ide.utils.Eclipse;

public class LocalizationEditor extends Composite {

    private class TableLabelProvider extends LabelProvider implements ITableLabelProvider {
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }
        public String getColumnText(Object element, int columnIndex) {
            return element.toString();
        }
    }
    private static class ContentProvider implements IStructuredContentProvider {
        public Object[] getElements(Object inputElement) {
            return new Object[0];
        }
        public void dispose() {
        }
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }

    private Table table;
    private Label applicationNameLabel;
    private FileCatalog<Map<String, String>> localization;
    private KActorsLocalizer localizationHelper;
    private TableViewer tableViewer;
    private String currentLocale = "en";
    public LocalizationEditor(Composite parent, int style) {
        super(parent, style);
        setLayout(new GridLayout(4, false));

        Label lblNewLabel = new Label(this, SWT.NONE);
        lblNewLabel.setText("Application:");

        applicationNameLabel = new Label(this, SWT.NONE);
        applicationNameLabel.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
        applicationNameLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        applicationNameLabel.setText("No application");

        Label lblNewLabel_1 = new Label(this, SWT.NONE);
        lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_1.setText("Locale");

        Combo combo = new Combo(this, SWT.NONE);
        combo.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
                    if (combo.getText().length() == 2) {
                        if (localization.containsKey(combo.getText().trim())) {
                            switchLanguage(combo.getText().trim());
                        } else {
                            if (Eclipse.INSTANCE
                                    .confirm("Do you wish to add the new language '" + combo.getText().trim() + "'?")) {
                                newLanguage(combo.getText().trim());
                            } else {
                                combo.setText(currentLocale);
                            }
                        }
                    } else {
                        Eclipse.INSTANCE.beep();
                        combo.setText(currentLocale);
                    }
                }
            }
        });

        combo.setItems(new String[]{currentLocale});
        GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_combo.widthHint = 24;
        combo.setLayoutData(gd_combo);
        combo.select(0);

        tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
        table = tableViewer.getTable();
        table.setLinesVisible(true);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

        TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
        tblclmnNewColumn.setWidth(400);
        tblclmnNewColumn.setText("Key");

        TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
        TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
        tblclmnNewColumn_1.setWidth(600);
        tblclmnNewColumn_1.setText("Value");
        tableViewer.setLabelProvider(new TableLabelProvider(){
            @Override
            public String getColumnText(Object element, int columnIndex) {
                return super.getColumnText(element, columnIndex);
            }
        });
        tableViewer.setContentProvider(new ContentProvider(){
            @Override
            public Object[] getElements(Object inputElement) {
                // TODO Auto-generated method stub
                return super.getElements(inputElement);
            }
        });

        new Label(this, SWT.NONE);
        new Label(this, SWT.NONE);

        Button btnNewButton_1 = new Button(this, SWT.NONE);
        btnNewButton_1.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        btnNewButton_1.setText("Rescan");

        Button btnNewButton = new Button(this, SWT.NONE);
        btnNewButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        GridData gd_btnNewButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_btnNewButton.widthHint = 64;
        btnNewButton.setLayoutData(gd_btnNewButton);
        btnNewButton.setText("Save");
    }

    protected void newLanguage(String trim) {
    }

    protected void switchLanguage(String text) {
    }

    public void initialize(String name, FileCatalog<Map<String, String>> existingLocalization,
            KActorsLocalizer localizationHelper) {

        this.localization = existingLocalization;
        this.localizationHelper = localizationHelper;

        Display.getDefault().asyncExec(() -> {
            this.applicationNameLabel.setText(name);
        });

        setupLanguage();

    }

    private void setupLanguage() {

        /*
         * checkout current language; if not there, add it with the existing content. If there is no
         * content, create default from the keys.
         */
        
//        Map<String, String> language = this.localization.
        

    }

}
