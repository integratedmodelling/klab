package org.integratedmodelling.klab.ide.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.rest.CodelistReference;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class CodelistEditor extends Composite {

    private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
    private Table directMappingsTable;
    private Table inverseMappingsTable;
    private Text nameField;
    private Text agencyField;
    private Text descriptionField;
    private Text authorityNameField;
    private SashForm mappingSash;
    private CodelistReference codelist;

    BiMap<String, IArtifact.Type> typeDictionary = HashBiMap.create();
    private Button oneToOneButton;
    private Button manyToManyButton;

    // temporary holders for editable table rows
    List<Triple<String, String, String>> direct = new ArrayList<>();
    List<Triple<String, String, String>> reverse = new ArrayList<>();
    private TableViewer inverseTableViewer;
    private TableViewer directTableViewer;

    public static class AttributeContentProvider implements IStructuredContentProvider {

        @Override
        public Object[] getElements(Object inputElement) {
            if (inputElement instanceof Collection) {
                return ((Collection<?>) inputElement).toArray();
            }
            return new Object[]{};
        }

    }

    public static class AttributeLabelProvider extends LabelProvider implements ITableLabelProvider {

        @Override
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public String getColumnText(Object element, int columnIndex) {
            if (element instanceof Triple) {
                switch(columnIndex) {
                case 0:
                    return ((Triple<String, String, String>) element).getFirst();
                case 1:
                    return ((Triple<String, String, String>) element).getSecond() == null
                            ? "Unmapped"
                            : ((Triple<String, String, String>) element).getSecond();
                case 2:
                    return ((Triple<String, String, String>) element).getThird() == null
                            ? ""
                            : ((Triple<String, String, String>) element).getThird();
                }
            }
            return null;
        }
    }
    /**
     * Create the composite.
     * 
     * @param parent
     * @param style
     */
    public CodelistEditor(Composite parent, int style) {
        super(parent, style);

        typeDictionary.put("Text category", IArtifact.Type.TEXT);
        typeDictionary.put("Concept (identity)", IArtifact.Type.CONCEPT);
        typeDictionary.put("Number", IArtifact.Type.NUMBER);
        typeDictionary.put("Boolean (true/false)", IArtifact.Type.BOOLEAN);

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

        nameField = new Text(settingsArea, SWT.BORDER);
        GridData gd_nameField = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_nameField.widthHint = 170;
        nameField.setLayoutData(gd_nameField);
        toolkit.adapt(nameField, true, true);

        Label lblAgency = new Label(settingsArea, SWT.NONE);
        lblAgency.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(lblAgency, true, true);
        lblAgency.setText("Agency");

        agencyField = new Text(settingsArea, SWT.BORDER);
        agencyField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(agencyField, true, true);

        Label lblValueType = new Label(settingsArea, SWT.NONE);
        lblValueType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(lblValueType, true, true);
        lblValueType.setText("Value Type");

        Combo valueTypeCombo = new Combo(settingsArea, SWT.READ_ONLY);
        valueTypeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(valueTypeCombo);
        toolkit.paintBordersFor(valueTypeCombo);

        for (String text : typeDictionary.keySet()) {
            valueTypeCombo.add(text);
        }
        valueTypeCombo.select(0);

        Label lblDescription = new Label(settingsArea, SWT.NONE);
        lblDescription.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        toolkit.adapt(lblDescription, true, true);
        lblDescription.setText("Description");

        descriptionField = new Text(settingsArea, SWT.BORDER);
        descriptionField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
        toolkit.adapt(descriptionField, true, true);
        new Label(settingsArea, SWT.NONE);

        Composite composite_2 = new Composite(settingsArea, SWT.NONE);
        composite_2.setLayout(new GridLayout(2, false));
        composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(composite_2);
        toolkit.paintBordersFor(composite_2);

        oneToOneButton = new Button(composite_2, SWT.RADIO);
        oneToOneButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                mappingSash.setWeights(1, 0);
            }
        });
        oneToOneButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        oneToOneButton.setSelection(true);
        toolkit.adapt(oneToOneButton, true, true);
        oneToOneButton.setText("1-to-1");

        manyToManyButton = new Button(composite_2, SWT.RADIO);
        manyToManyButton.addSelectionListener(new SelectionAdapter(){
            @Override
            public void widgetSelected(SelectionEvent e) {
                mappingSash.setWeights(1, 1);
            }
        });
        manyToManyButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(manyToManyButton, true, true);
        manyToManyButton.setText("Many-to-many");

        Composite mappingArea = new Composite(this, SWT.NONE);
        mappingArea.setLayout(new GridLayout(1, false));
        mappingArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        toolkit.adapt(mappingArea);
        toolkit.paintBordersFor(mappingArea);

        mappingSash = new SashForm(mappingArea, SWT.NONE);
        mappingSash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        toolkit.adapt(mappingSash);
        toolkit.paintBordersFor(mappingSash);

        Composite composite = new Composite(mappingSash, SWT.NONE);
        toolkit.adapt(composite);
        toolkit.paintBordersFor(composite);
        composite.setLayout(new GridLayout(1, false));

        Label lblNewLabel = new Label(composite, SWT.NONE);
        lblNewLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(lblNewLabel, true, true);
        lblNewLabel.setText("Direct mappings");

        directTableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
        directMappingsTable = directTableViewer.getTable();
        directMappingsTable.setLinesVisible(true);
        directMappingsTable.setHeaderVisible(true);
        directMappingsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        toolkit.paintBordersFor(directMappingsTable);

        TableViewerColumn tableViewerColumn = new TableViewerColumn(directTableViewer, SWT.NONE);
        TableColumn tblclmnKey = tableViewerColumn.getColumn();
        tblclmnKey.setWidth(177);
        tblclmnKey.setText("Key");

        TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(directTableViewer, SWT.NONE);
        TableColumn tblclmnValue_2 = tableViewerColumn_4.getColumn();
        tblclmnValue_2.setWidth(100);
        tblclmnValue_2.setText("Value");

        TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(directTableViewer, SWT.NONE);
        TableColumn tblclmnValue = tableViewerColumn_1.getColumn();
        tblclmnValue.setWidth(610);
        tblclmnValue.setText("Description");

        Composite composite_1 = new Composite(mappingSash, SWT.NONE);
        toolkit.adapt(composite_1);
        toolkit.paintBordersFor(composite_1);
        composite_1.setLayout(new GridLayout(1, false));

        Label lblNewLabel_1 = new Label(composite_1, SWT.NONE);
        lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        toolkit.adapt(lblNewLabel_1, true, true);
        lblNewLabel_1.setText("Inverse mappings");

        inverseTableViewer = new TableViewer(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
        inverseMappingsTable = inverseTableViewer.getTable();
        inverseMappingsTable.setLinesVisible(true);
        inverseMappingsTable.setHeaderVisible(true);
        inverseMappingsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        toolkit.paintBordersFor(inverseMappingsTable);

        TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(inverseTableViewer, SWT.NONE);
        TableColumn tblclmnKey_1 = tableViewerColumn_2.getColumn();
        tblclmnKey_1.setWidth(179);
        tblclmnKey_1.setText("Value");

        TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(inverseTableViewer, SWT.NONE);
        TableColumn tblclmnValue_1 = tableViewerColumn_3.getColumn();
        tblclmnValue_1.setWidth(123);
        tblclmnValue_1.setText("Key");

        TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(inverseTableViewer, SWT.NONE);
        TableColumn tblclmnDescription = tableViewerColumn_5.getColumn();
        tblclmnDescription.setWidth(230);
        tblclmnDescription.setText("Description");
        mappingSash.setWeights(new int[]{1, 0});

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
        lblName_1.setText("Name (optional)");

        authorityNameField = new Text(actionArea, SWT.BORDER);
        GridData gd_authorityNameField = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
        gd_authorityNameField.widthHint = 234;
        authorityNameField.setLayoutData(gd_authorityNameField);
        toolkit.adapt(authorityNameField, true, true);

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

        directTableViewer.setContentProvider(new AttributeContentProvider());
        directTableViewer.setLabelProvider(new AttributeLabelProvider());
        inverseTableViewer.setContentProvider(new AttributeContentProvider());
        inverseTableViewer.setLabelProvider(new AttributeLabelProvider());

    }

    public void loadCodelist(CodelistReference ref) {
        this.codelist = ref;
        this.nameField.setText(ref.getName());
        this.descriptionField.setText(ref.getDescription());
        this.agencyField.setText(ref.getAgency());
        if (ref.isTwoWay()) {
            manyToManyButton.setSelection(true);
        } else {
            oneToOneButton.setSelection(true);
        }

        this.direct.clear();
        this.reverse.clear();

        /*
         * build support structures
         */
        Multimap<String, String> mapdirect = HashMultimap.create();
        Multimap<String, String> maprevers = HashMultimap.create();
        if (ref.getDirectMapping() != null) {
            for (Pair<String, String> zio : ref.getDirectMapping().getMappings()) {
                mapdirect.put(zio.getFirst(), zio.getSecond());
            }
        }
        if (ref.getInverseMapping() != null) {
            for (Pair<String, String> zio : ref.getDirectMapping().getMappings()) {
                maprevers.put(zio.getFirst(), zio.getSecond());
            }
        }
        for (String s : ref.getCodeDescriptions().keySet()) {
            Collection<String> values = mapdirect.get(s);
            if (values.size() == 0) {
                direct.add(new Triple<>(s, "", ref.getCodeDescriptions().get(s)));
            } else {
                for (String value : values) {
                    direct.add(new Triple<>(s, value, ref.getCodeDescriptions().get(s)));
                }
            }
            // values = maprevers.get(s);
            // if (values.size() == 0) {
            // reverse.add(new Triple<>(value, "", ref.getCodeDescriptions().get(s)));
            // } else {
            // for (String value : values) {
            // reverse.add(new Triple<>(s, value, ref.getCodeDescriptions().get(s)));
            // }
            // }
        }

        directTableViewer.setInput(this.direct);
        inverseTableViewer.setInput(this.reverse);

    }
}
