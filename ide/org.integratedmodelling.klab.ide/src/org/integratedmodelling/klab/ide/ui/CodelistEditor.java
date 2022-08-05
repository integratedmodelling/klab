package org.integratedmodelling.klab.ide.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.client.utils.JsonUtils;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.CodelistReference;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class CodelistEditor extends Composite {
    
    private static Logger logger = LoggerFactory.getLogger(CodelistEditor.class);

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
	private Button codesOnlyButton;
	private Button oneToOneButton;

	// temporary holders for editable table rows
	List<Triple<String, String, String>> direct = new ArrayList<>();
	List<Triple<String, String, String>> reverse = new ArrayList<>();
	private TableViewer inverseTableViewer;
	private TableViewer directTableViewer;
	private boolean dirty;
	private Text text_1;
	private Text rootConcept;
	private String resourceUrn;
	private Button btnExposeAsAuthority;
	private Button saveCodelistButton;

	public static class AttributeContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection) {
				return ((Collection<?>) inputElement).toArray();
			}
			return new Object[] {};
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
				switch (columnIndex) {
				case 0:
					return ((Triple<String, String, String>) element).getFirst();
				case 1:
					return ((Triple<String, String, String>) element).getSecond() == null ? ""
							: ((Triple<String, String, String>) element).getSecond();
				case 3:
					return ((Triple<String, String, String>) element).getThird() == null ? ""
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
		typeDictionary.put("Time extent", IArtifact.Type.TEMPORALEXTENT);
		typeDictionary.put("Spatial extent", IArtifact.Type.SPATIALEXTENT);

		addDisposeListener(new DisposeListener() {
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
		GridData gd_nameField = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_nameField.widthHint = 160;
		nameField.setLayoutData(gd_nameField);
		toolkit.adapt(nameField, true, true);

		Label lblAgency = new Label(settingsArea, SWT.NONE);
		lblAgency.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblAgency, true, true);
		lblAgency.setText("Agency");

		agencyField = new Text(settingsArea, SWT.BORDER);
		toolkit.adapt(agencyField, true, true);

		Label lblValueType = new Label(settingsArea, SWT.NONE);
		lblValueType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblValueType, true, true);
		lblValueType.setText("Value Type");

		Combo valueTypeCombo = new Combo(settingsArea, SWT.READ_ONLY);
		GridData gd_valueTypeCombo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_valueTypeCombo.widthHint = 60;
		valueTypeCombo.setLayoutData(gd_valueTypeCombo);
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
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_2.setLayout(new GridLayout(5, false));
		toolkit.adapt(composite_2);
		toolkit.paintBordersFor(composite_2);

		codesOnlyButton = new Button(composite_2, SWT.RADIO);
		codesOnlyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mappingSash.setWeights(1, 0);
			}
		});
		GridData gd_codesOnlyButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_codesOnlyButton.widthHint = 60;
		codesOnlyButton.setLayoutData(gd_codesOnlyButton);
		codesOnlyButton.setSelection(true);
		toolkit.adapt(codesOnlyButton, true, true);
		codesOnlyButton.setText("Codes");

		oneToOneButton = new Button(composite_2, SWT.RADIO);

		GridData gd_oneToOneButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_oneToOneButton.widthHint = 60;
		oneToOneButton.setLayoutData(gd_oneToOneButton);
		toolkit.adapt(oneToOneButton, true, true);
		oneToOneButton.setText("1->1");
		oneToOneButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mappingSash.setWeights(1, 0);
			}
		});

		Button oneToManyButton = new Button(composite_2, SWT.RADIO);
		GridData gd_oneToManyButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_oneToManyButton.widthHint = 60;
		oneToManyButton.setLayoutData(gd_oneToManyButton);
		toolkit.adapt(oneToManyButton, true, true);
		oneToManyButton.setText("1->n");

		Label lblRootConcept = new Label(composite_2, SWT.NONE);
		lblRootConcept.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblRootConcept, true, true);
		lblRootConcept.setText("Root concept");

		rootConcept = new Text(composite_2, SWT.BORDER);
		rootConcept.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (codelist != null) {
					codelist.setRootConceptId(rootConcept.getText());
					setDirty(true);
				}
			}
		});
		rootConcept.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(rootConcept, true, true);
		oneToManyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mappingSash.setWeights(1, 1);
			}
		});

		Composite mappingArea = new Composite(this, SWT.NONE);
		mappingArea.setLayout(new GridLayout(1, false));
		mappingArea.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(mappingArea);
		toolkit.paintBordersFor(mappingArea);

		mappingSash = new SashForm(mappingArea, SWT.NONE);
		mappingSash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(mappingSash);
		toolkit.paintBordersFor(mappingSash);

		Composite composite = new Composite(mappingSash, SWT.BORDER);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(1, false));

		Composite composite_4 = new Composite(composite, SWT.NONE);
		composite_4.setLayout(new GridLayout(2, false));
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		toolkit.adapt(composite_4);
		toolkit.paintBordersFor(composite_4);

		Label lblNewLabel = new Label(composite_4, SWT.NONE);
		toolkit.adapt(lblNewLabel, true, true);
		lblNewLabel.setText("Direct mappings");

		Composite composite_5 = new Composite(composite_4, SWT.NONE);
		composite_5.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(composite_5);
		toolkit.paintBordersFor(composite_5);
		GridLayout gl_composite_5 = new GridLayout(2, false);
		gl_composite_5.marginRight = 2;
		gl_composite_5.marginWidth = 0;
		gl_composite_5.marginHeight = 0;
		gl_composite_5.horizontalSpacing = 3;
		composite_5.setLayout(gl_composite_5);

		Label lblRegexPatternFor = new Label(composite_5, SWT.NONE);
		lblRegexPatternFor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblRegexPatternFor, true, true);
		lblRegexPatternFor.setText("Regex pattern for unmapped codes ");

		text_1 = new Text(composite_5, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_1.widthHint = 120;
		text_1.setLayoutData(gd_text_1);
		toolkit.adapt(text_1, true, true);

		directTableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION);
		directMappingsTable = directTableViewer.getTable();
		directMappingsTable.setLinesVisible(true);
		directMappingsTable.setHeaderVisible(true);
		directMappingsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.paintBordersFor(directMappingsTable);

		final TableEditor editor = new TableEditor(directMappingsTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		directMappingsTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(final MouseEvent e) {
				final Control oldEditor = editor.getEditor();
				if (oldEditor != null) {
					oldEditor.dispose();
				}

				// Get the Point from the MouseEvent
				final Point p = new Point(e.x, e.y);
				// Get the TreeItem corresponding to that point
				final TableItem item = directMappingsTable.getItem(p);
				if (item == null) {
					return;
				}
				// Now that we know the TreeItem, we can use the getBounds() method
				// to locate the corresponding column
				for (int i = 0; i < directMappingsTable.getColumnCount(); ++i) {
					if (item.getBounds(i).contains(p)) {
						final int columnIndex = i;
						@SuppressWarnings("unchecked")
						Triple<String, String, String> data = (Triple<String, String, String>) item.getData();
						if (columnIndex > 0/* && !data.nonexisting */) {

							final Text newEditor = new Text(directMappingsTable, SWT.NONE);
							newEditor.setText(item.getText(columnIndex));
							newEditor.addModifyListener(new ModifyListener() {
								public void modifyText(final ModifyEvent e) {

									final Text text = (Text) editor.getEditor();
									editor.getItem().setText(columnIndex, text.getText());

									String value = text.getText();
									boolean changed = true;
									String current = (String) data.get(columnIndex);
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
									//
									if (changed) {

										if (columnIndex == 1) {
											logger.debug("VALIDATE THIS FUCKA " + current);
										}

										// setMessage(null, Level.INFO);
										// if (value != null && descriptor != null) {
										// if (!Utils.validateAs(value, descriptor.getType())) {
										// setMessage("'" + value + "' is not a suitable value for
										// type "
										// + descriptor.getType().name().toLowerCase(),
										// Level.SEVERE);
										// }
										// if (data.parameter.endsWith("Url")) {
										// if
										// (!UrlValidator.getInstance().isValid(value.toString())) {
										// setMessage("'" + value + "' is not a valid URL",
										// Level.SEVERE);
										// }
										// }
										// }
										// if (value == null) {
										// values.remove(data.parameter);
										// data.value = null;
										// } else {
										// values.put(data.parameter, value);
										// data.value = value;
										// }
										setDirty(true);
									}
									// adapterPropertyViewer.update(data, null);
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

		TableViewerColumn tableViewerColumn = new TableViewerColumn(directTableViewer, SWT.NONE);
		TableColumn tblclmnKey = tableViewerColumn.getColumn();
		tblclmnKey.setWidth(90);
		tblclmnKey.setText("Key");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(directTableViewer, SWT.NONE);
		TableColumn tblclmnValue_2 = tableViewerColumn_4.getColumn();
		tblclmnValue_2.setWidth(140);
		tblclmnValue_2.setText("Value");

		TableColumn tblclmnLabelColumn = new TableColumn(directMappingsTable, SWT.NONE);
		tblclmnLabelColumn.setWidth(140);
		tblclmnLabelColumn.setText("Label");

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
		mappingSash.setWeights(new int[] { 1, 0 });

		Composite actionArea = new Composite(this, SWT.NONE);
		actionArea.setLayout(new GridLayout(6, false));
		actionArea.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(actionArea);
		toolkit.paintBordersFor(actionArea);

		btnExposeAsAuthority = new Button(actionArea, SWT.CHECK);
		btnExposeAsAuthority.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (btnExposeAsAuthority.getSelection() && authorityNameField.getText().trim().isEmpty()
						&& codelist != null) {
					authorityNameField.setText(codelist.getAgency() + "." + codelist.getId());
					codelist.setAuthorityId(authorityNameField.getText());
					codelist.setAuthority(true);
					setDirty(true);
				}
				if (!btnExposeAsAuthority.getSelection()) {
					if (codelist.getAuthorityId() != null && !codelist.getAuthorityId().isEmpty()) {
						setDirty(true);
					}
					codelist.setAuthorityId(null);
					codelist.setAuthority(false);
					authorityNameField.setText("");
				}
			}
		});
		toolkit.adapt(btnExposeAsAuthority, true, true);
		btnExposeAsAuthority.setText("Expose as authority");

		Label lblName_1 = new Label(actionArea, SWT.NONE);
		lblName_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblName_1, true, true);
		lblName_1.setText("named ");

		authorityNameField = new Text(actionArea, SWT.BORDER);
		authorityNameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (codelist != null) {
					codelist.setAuthorityId(authorityNameField.getText());
					codelist.setAuthority(!codelist.getAuthorityId().trim().isEmpty());
					setDirty(true);
				}
			}
		});
		GridData gd_authorityNameField = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_authorityNameField.widthHint = 234;
		authorityNameField.setLayoutData(gd_authorityNameField);
		toolkit.adapt(authorityNameField, true, true);

		Label lblInResource = new Label(actionArea, SWT.NONE);
		lblInResource.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblInResource, true, true);
		lblInResource.setText("in resource");

		Combo combo = new Combo(actionArea, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(combo);
		toolkit.paintBordersFor(combo);

		Composite composite_3 = new Composite(actionArea, SWT.NONE);
		composite_3.setLayout(new GridLayout(2, false));
		composite_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(composite_3);
		toolkit.paintBordersFor(composite_3);

		Button btnNewButton = new Button(composite_3, SWT.NONE);
		btnNewButton.setEnabled(false);
		btnNewButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("Synchronize");

		saveCodelistButton = new Button(composite_3, SWT.NONE);
		saveCodelistButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveCodelist();
			}
		});
		saveCodelistButton.setEnabled(false);

		GridData gd_saveCodelistButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_saveCodelistButton.widthHint = 68;
		saveCodelistButton.setLayoutData(gd_saveCodelistButton);
		saveCodelistButton.setBounds(0, 0, 90, 30);
		toolkit.adapt(saveCodelistButton, true, true);
		saveCodelistButton.setText("Save");

		directTableViewer.setContentProvider(new AttributeContentProvider());
		directTableViewer.setLabelProvider(new AttributeLabelProvider());
		inverseTableViewer.setContentProvider(new AttributeContentProvider());
		inverseTableViewer.setLabelProvider(new AttributeLabelProvider());

	}

	protected void saveCodelist() {

		if (dirty && resourceUrn != null && Activator.engineMonitor().isRunning()) {
			try {
				// use REST calls or a big codelist will kill the websocket bus
				File codelistFile = File.createTempFile("cls", ".json");
				JsonUtils.save(this.codelist, codelistFile);
				Activator.client().upload(API.ENGINE.RESOURCE.UPDATE_CODELIST.replace(API.P_URN, resourceUrn),
						codelistFile);
			} catch (IOException e) {
				Eclipse.INSTANCE.handleException(e);
			}
			// Original WS code, still working but dangerous
//			ResourceCRUDRequest request = new ResourceCRUDRequest();
//			request.setResourceUrns(Sets.newHashSet(resourceUrn));
//			request.setCodelist(this.codelist);
//			Activator.post(IMessage.MessageClass.ResourceLifecycle, IMessage.Type.UpdateCodelist, request);
		}
	}

	public void loadCodelist(CodelistReference ref, String resourceUrn) {
		Display.getDefault().asyncExec(() -> {

			this.codelist = ref;
			this.resourceUrn = resourceUrn;
			this.nameField.setText(ref.getName());
			this.descriptionField.setText(ref.getDescription() == null ? "" : ref.getDescription());
			this.agencyField.setText(ref.getAgency() == null ? "" : ref.getAgency());
			this.authorityNameField.setText(ref.getAuthorityId() == null ? "" : ref.getAuthorityId());
			this.btnExposeAsAuthority.setSelection(!this.authorityNameField.getText().isEmpty());
			this.rootConcept.setText(ref.getRootConceptId() == null ? "" : ref.getRootConceptId());

			if (ref.isTwoWay()) {
				oneToOneButton.setSelection(true);
			} else {
				codesOnlyButton.setSelection(true);
			}

			this.direct.clear();
			this.reverse.clear();

			/*
			 * build support structures
			 */
			Multimap<String, String> mapdirect = HashMultimap.create();
			Map<String, String> maprevers = new HashMap<>();

			if (ref.getDirectMapping() != null) {
				for (Pair<String, String> zio : ref.getDirectMapping().getMappings()) {
					mapdirect.put(zio.getFirst(), zio.getSecond());
				}
			}
			if (ref.getInverseMapping() != null) {
				for (Pair<String, String> zio : ref.getInverseMapping().getMappings()) {
					maprevers.put(zio.getSecond(), zio.getFirst());
				}
			}

			List<String> skeys = new ArrayList<>(ref.getCodeDescriptions().keySet());
			Collections.sort(skeys);

			for (String s : skeys) {
				Collection<String> values = mapdirect.get(s);
				if (values.size() == 0) {
					direct.add(new Triple<>(s, "", ref.getCodeDescriptions().get(s)));
				} else {
					for (String value : values) {
						direct.add(new Triple<>(s, value, ref.getCodeDescriptions().get(s)));
					}
				}
				for (String value : maprevers.keySet()) {
					reverse.add(new Triple<>(value, maprevers.get(value),
							ref.getCodeDescriptions().get(maprevers.get(value))));
				}
			}

			directTableViewer.setInput(this.direct);
			inverseTableViewer.setInput(this.reverse);
			setDirty(false);

		});
	}

	private void setDirty(boolean b) {
		this.dirty = b;
		saveCodelistButton.setEnabled(b);
	}

}
