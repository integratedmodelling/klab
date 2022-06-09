package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.kactors.utils.KActorsLocalizer;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.utils.StringUtil;

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
    private FileCatalog<Map<String, String>> localization;
    private KActorsLocalizer localizationHelper;
    private TableViewer tableViewer;
    private String currentLocale = "en";
    private boolean dirty;
    private Button saveButton;
    private Button rescanButton;
    private Combo combo;
    private Label unlocalizedStringsWarning;
    private Button btnCancel;
    protected IViewPart localizationView;
    int sortToggle = 0; // 0 = original order; 1 = ascending; 2 = descending
    private Composite composite;
    private Label languageLabel;

    public LocalizationEditor(IViewPart localizationView, Composite parent, int style) {
        super(parent, style);
        setLayout(new GridLayout(4, false));

        Label lblNewLabel_1 = new Label(this, SWT.NONE);
        lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        lblNewLabel_1.setText("Locale");

        composite = new Composite(this, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));

        combo = new Combo(composite, SWT.NONE);
        GridData gd_combo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_combo.widthHint = 32;
        combo.setLayoutData(gd_combo);
        combo.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                switchLanguage(combo.getText());
            }
        });
        combo.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
                    if (combo.getText().length() == 2) {
                        if (localization.containsKey(combo.getText().trim())) {
                            switchLanguage(combo.getText().trim());
                        } else {
                            if (Eclipse.INSTANCE
                                    .confirm("Do you wish to add the new language '" + combo.getText().trim()
                                            + "'?")) {
                                switchLanguage(combo.getText().trim());
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
        combo.select(0);

        languageLabel = new Label(composite, SWT.NONE);
        languageLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        languageLabel.setText("English");
        new Label(this, SWT.NONE);
        new Label(this, SWT.NONE);

        tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
        table = tableViewer.getTable();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));

        TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
        TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
        tblclmnNewColumn.setWidth(400);
        tblclmnNewColumn.setText("Key");

        tableViewerColumn.getColumn().addListener(SWT.Selection, new Listener(){
            @Override
            public void handleEvent(Event event) {
                sortToggle = (sortToggle + 1) % 3;
                refreshUI();
            }
        });

        TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
        TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
        tblclmnNewColumn_1.setWidth(600);
        tblclmnNewColumn_1.setText("Text to substitute");
        tableViewer.setLabelProvider(new TableLabelProvider(){
            @Override
            public String getColumnText(Object element, int columnIndex) {
                if (element instanceof Entry) {
                    Entry<?, ?> entry = (Entry<?, ?>) element;
                    return columnIndex == 0
                            ? entry.getKey().toString()
                            : (entry.getValue() == null ? "" : entry.getValue().toString());
                }
                return super.getColumnText(element, columnIndex);
            }
        });
        tableViewer.setContentProvider(new ContentProvider(){
            @Override
            public Object[] getElements(Object inputElement) {
                if (inputElement instanceof Map) {
                    return ((Map) inputElement).entrySet().toArray();
                }
                return new Object[0];
            }
        });

        final TableEditor editor = new TableEditor(table);
        editor.horizontalAlignment = SWT.LEFT;
        editor.grabHorizontal = true;

        table.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseUp(final MouseEvent e) {
                final Control oldEditor = editor.getEditor();
                if (oldEditor != null) {
                    oldEditor.dispose();
                }

                // Get the Point from the MouseEvent
                final Point p = new Point(e.x, e.y);
                // Get the TreeItem corresponding to that point
                final TableItem item = table.getItem(p);
                if (item == null) {
                    return;
                }
                // Now that we know the TreeItem, we can use the getBounds() method
                // to locate the corresponding column
                for (int i = 0; i < table.getColumnCount(); ++i) {
                    if (item.getBounds(i).contains(p)) {
                        final int columnIndex = i;
                        Entry<?, ?> data = (Entry<?, ?>) item.getData();
                        if (columnIndex == 1) {

                            final Text newEditor = new Text(table, SWT.NONE);
                            newEditor.setText(item.getText(columnIndex));
                            newEditor.addModifyListener(new ModifyListener(){
                                public void modifyText(final ModifyEvent e) {

                                    final Text text = (Text) editor.getEditor();
                                    editor.getItem().setText(columnIndex, text.getText());

                                    String value = text.getText();
                                    boolean changed = true;
                                    String current = localization.get(currentLocale).get(data.getKey());
                                    if (current != null && current.trim().isEmpty()) {
                                        current = null;
                                    }
                                    if (value != null && value.toString().isEmpty()) {
                                        value = null;
                                    }
                                    if ((value != null && current != null && value.equals(current))
                                            || (value == null && current == null)) {
                                        changed = false;
                                    }

                                    if (changed) {
                                        if (value == null) {
                                            value = "";
                                        }
                                        localization.get(currentLocale).put(data.getKey().toString(), value);
                                        setDirty(true);
                                    }
                                }
                            });
                            newEditor.selectAll();
                            newEditor.setFocus();
                            // Set the editor for the matching column
                            editor.setEditor(newEditor, item, columnIndex);
                        }
                    }
                }
            }
        });

        rescanButton = new Button(this, SWT.NONE);
        rescanButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        rescanButton.setText("Rescan");

        unlocalizedStringsWarning = new Label(this, SWT.NONE);
        unlocalizedStringsWarning.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        unlocalizedStringsWarning.setText("No unlocalized strings");

        btnCancel = new Button(this, SWT.NONE);
        btnCancel.setText("Cancel");
        btnCancel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseDown(MouseEvent e) {
                if (!dirty || Eclipse.INSTANCE.confirm("Abandon changes?")) {
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                            .hideView(localizationView);
                }
            }
        });

        saveButton = new Button(this, SWT.NONE);
        saveButton.setEnabled(false);
        saveButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                localization.write();
                saveButton.setEnabled(false);
            }
        });
        GridData gd_saveButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_saveButton.widthHint = 64;
        saveButton.setLayoutData(gd_saveButton);
        saveButton.setText("Save");
    }

    protected void switchLanguage(String locale) {
        Locale loc = Locale.forLanguageTag(locale);
        if (loc != null) {
            Display.getDefault().asyncExec(() -> {
                languageLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
                languageLabel.setText(loc.getDisplayName());
            });
        } else {
            Display.getDefault().asyncExec(() -> {
                languageLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
                languageLabel.setText("Unrecognized locale!");
            });
            return;
        }
        currentLocale = locale;
        setupLanguage();
    }

    private void setDirty(boolean b) {
        this.dirty = b;
        Display.getDefault().asyncExec(() -> {
            saveButton.setEnabled(b);
        });

    }

    public void initialize(String name, FileCatalog<Map<String, String>> existingLocalization,
            KActorsLocalizer localizationHelper) {

        this.localization = existingLocalization;
        this.localizationHelper = localizationHelper;

        if (!this.localization.keySet().isEmpty()) {
            currentLocale = null;
            combo.removeAll();
            for (String language : this.localization.keySet()) {
                combo.add(language);
                if (currentLocale == null) {
                    currentLocale = language;
                }
            }
            combo.select(0);
        }

        setupLanguage();

    }

    private void setupLanguage() {

        /*
         * checkout current language; if not there, add it with the existing content. If there is no
         * content, create default from the keys.
         */
        Map<String, String> language = this.localization.get(currentLocale);
        if (language == null) {
            language = new HashMap<>();
            this.localization.put(currentLocale, language);
            this.dirty = true;
        }

        for (String s : this.localizationHelper.getLocalizedKeys()) {
            if (!language.containsKey(s)) {
                language.put(s, getDefaultStringValue(s));
                this.dirty = true;
            }
        }

        refreshUI();
    }

    private void refreshUI() {
        Display.getDefault().asyncExec(() -> {
            this.unlocalizedStringsWarning
                    .setText((this.localizationHelper.getUnlocalizedStrings().size() == 0
                            ? "No"
                            : (this.localizationHelper.getUnlocalizedStrings().size() + ""))
                            + " unlocalized strings in behavior");
            tableViewer.setInput(sort(this.localization.get(currentLocale)));
            if (this.dirty) {
                saveButton.setEnabled(true);
            }
        });
    }

    private Map<String, String> sort(Map<String, String> map) {
        if (sortToggle == 1) {
            List<String> keys = new ArrayList<>(map.keySet());
            Collections.sort(keys);
            Map<String, String> ret = new LinkedHashMap<>();
            for (String key : keys) {
                ret.put(key, map.get(key));
            }
            return ret;
        } else if (sortToggle == 2) {
            List<String> keys = new ArrayList<>(map.keySet());
            Collections.sort(keys, new Comparator<String>(){
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1);
                }
            });
            Map<String, String> ret = new LinkedHashMap<>();
            for (String key : keys) {
                ret.put(key, map.get(key));
            }
            return ret;
        }
        return map;
    }

    private String getDefaultStringValue(String s) {
        return ("en".equals(currentLocale) ? "" : ("(" + currentLocale + ") "))
                + StringUtil.capitalize(s.toLowerCase().replace("__", ":").replace("_", " "));
    }

}
