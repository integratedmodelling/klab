/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.ide.ui.wizards;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EProject;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
import org.integratedmodelling.klab.rest.ServicePrototype;
import org.integratedmodelling.klab.rest.ServicePrototype.Argument;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.UrlValidator;
import org.integratedmodelling.klab.utils.Utils;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.events.VerifyEvent;

public class NewResource extends WizardPage {

	private Text text;
	private Combo adapterCombo;
	private Table table;
	private StyledText descriptionText;
	private TableViewerColumn propertyNameColumn;
	private TableViewerColumn propertyValueColumn;
	private ResourceAdapterReference adapter = null;
	private TableViewer tableViewer;

	private Map<String, String> values = new HashMap<>();
	private EResourceFolder folder;

	public class ValueSupport extends EditingSupport {

		private final TableViewer viewer;
		private final CellEditor editor;

		public ValueSupport(TableViewer viewer) {
			super(viewer);
			this.viewer = viewer;
			this.editor = new TextCellEditor(viewer.getTable());
		}

		@Override
		protected CellEditor getCellEditor(Object element) {
			return editor;
		}

		@Override
		protected boolean canEdit(Object element) {
			// if (element instanceof Pair && Activator.engineMonitor().isRunning() &&
			// adapter != null) {
			// String parameter = ((Pair<?, ?>) element).getFirst().toString();
			// Argument arg = adapter.getParameters().findArgument(parameter);
			// if (arg == null || arg.isFinal()) {
			// return false;
			// }
			// return true;
			// }
			return true;
		}

		@Override
		protected Object getValue(Object element) {
			Object ret = null;
			if (element instanceof ServicePrototype.Argument) {
				ret = values.get(((ServicePrototype.Argument) element).getName());
			}
			return ret == null ? "" : ret.toString();
		}

		@Override
		protected void setValue(Object element, Object value) {
			if (element instanceof ServicePrototype.Argument) {
				setErrorMessage(null);
				if (value != null && !value.toString().isEmpty()) {
					if (!Utils.validateAs(value, ((ServicePrototype.Argument) element).getType())) {
						setErrorMessage("'" + value + "' is not a suitable value for type "
								+ ((ServicePrototype.Argument) element).getType().name().toLowerCase());
					}
					if (((ServicePrototype.Argument) element).getName().endsWith("Url")) {
						if (!UrlValidator.getInstance().isValid(value.toString())) {
							setErrorMessage("'" + value + "' is not a valid URL");
						}
					}
				}
				values.put(((ServicePrototype.Argument) element).getName(), value.toString());
			}
			getViewer().update(element, null);
		}

	}

	class PropertyContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof ResourceAdapterReference) {
				return ((ResourceAdapterReference) inputElement).getParameters().getArguments().toArray();
			}
			return new Object[] {};
		}

	}

	class PropertyLabelProvider extends LabelProvider implements ITableLabelProvider, IColorProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return columnIndex == 0
					? ResourceManager.getPluginImage("org.integratedmodelling.klab.ide", "icons/property.gif")
					: null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ServicePrototype.Argument) {
				ServicePrototype.Argument arg = (ServicePrototype.Argument) element;
				switch (columnIndex) {
				case 0:
					return arg.getName();
				case 1:
					return StringUtil.capitalize(arg.getType().name().toLowerCase());
				case 2:
					Object ret = values.get(((ServicePrototype.Argument) element).getName());
					return ret == null ? null : ret.toString();
				}
			}

			return null;
		}

		@Override
		public Color getForeground(Object element) {
			if (element instanceof ServicePrototype.Argument && (((ServicePrototype.Argument) element).isFinal()
					|| ((ServicePrototype.Argument) element).isRequired())) {
				return values.containsKey(((ServicePrototype.Argument) element).getName())
						&& !values.get(((ServicePrototype.Argument) element).getName()).trim().isEmpty()
								? SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN)
								: SWTResourceManager.getColor(SWT.COLOR_RED);
			}
			return null;
		}

		@Override
		public Color getBackground(Object element) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * Create the wizard.
	 * 
	 * @param folder
	 * 
	 * @param item
	 * @param page
	 */
	public NewResource(EResourceFolder folder) {
		super("wizardPage");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.jpg"));
		setTitle("New resource");
		setDescription(
				"Create a new k.LAB resource for a specified adapter. Use to specify all parameters manually when drag&drop or bulk import cannot be used.");
		this.folder = folder;
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		GridLayout gl_container = new GridLayout(3, false);
		gl_container.marginBottom = 12;
		gl_container.verticalSpacing = 10;
		gl_container.marginRight = 12;
		gl_container.marginLeft = 12;
		gl_container.marginTop = 12;
		container.setLayout(gl_container);

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("Resource ID: ");

		Label lblProjectName = new Label(container, SWT.NONE);
		lblProjectName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblProjectName.setAlignment(SWT.RIGHT);
		lblProjectName.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblProjectName.setText("local:" + Activator.user().getUsername().toLowerCase() + ":"
				+ folder.getEParent(EProject.class).getName() + ":");

		text = new Text(container, SWT.BORDER);
		text.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				if (!text.getText().trim().isEmpty()) {
					if (Activator.klab().getResource(getResourceURN()) != null) {
						setErrorMessage("A resource named " + text.getText() + " already exists in this project.");
					}
				}
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblTrigger = new Label(container, SWT.NONE);
		lblTrigger.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTrigger.setText("Adapter");

		adapterCombo = new Combo(container, SWT.READ_ONLY);
		adapterCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String description = "Adapters define the protocol with which the engine stores, communicates and publishes the resource.\n"
						+ "Choose the adapter that can handle the resource you are creating.";
				if (adapterCombo.getSelectionIndex() > 0) {
					adapter = Activator.klab().getResourceAdapter(getSelectedAdapter());
					description = adapter.getDescription();
					setValuesFromAdapter();
					tableViewer.setInput(adapter);
				}
				descriptionText.setText(description);
			}

		});
		adapterCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		adapterCombo.add("-- choose one --");
		for (ResourceAdapterReference adapter : Activator.klab().getResourceAdapters()) {
			if (!adapter.isFileBased()) {
				adapterCombo.add(adapter.getName() + (adapter.getLabel() == null ? "" : (" - " + adapter.getLabel())));
			}
		}
		adapterCombo.select(0);

		Label lblZoz = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblZoz.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		lblZoz.setText("");

		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// show the docs for the parameter that was clicked
				int narg = table.getSelectionIndex();
				if (narg >= 0 && narg < adapter.getParameters().getArguments().size()) {
					Argument arg = (Argument) table.getItem(narg).getData();
					String description = adapter.getDescription() + "\n\n";
					int start = description.length();
					description += arg.getName();
					description += "\n\n" + arg.getDescription();
					descriptionText.setText(description);
					descriptionText.setStyleRange(new StyleRange(start, arg.getName().length(), null, null, SWT.BOLD));
				}
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		propertyNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn propertyColumn = propertyNameColumn.getColumn();
		propertyColumn.setWidth(180);
		propertyColumn.setText("Adapter property");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn typeColumn = tableViewerColumn_1.getColumn();
		typeColumn.setWidth(100);
		typeColumn.setText("Type");

		propertyValueColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn valueColumn = propertyValueColumn.getColumn();
		valueColumn.setWidth(400);
		valueColumn.setText("Value");
		propertyValueColumn.setEditingSupport(new ValueSupport(tableViewer));

		tableViewer.setLabelProvider(new PropertyLabelProvider());
		tableViewer.setContentProvider(new PropertyContentProvider());
		descriptionText = new StyledText(container, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP);
		descriptionText.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		GridData gd_descriptionText = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_descriptionText.heightHint = 100;
		descriptionText.setLayoutData(gd_descriptionText);
	}

	private void setValuesFromAdapter() {
		for (Argument argument : adapter.getParameters().getArguments()) {
			if (!values.containsKey(argument.getName()) && argument.getDefaultValue() != null) {
				values.put(argument.getName(), argument.getDefaultValue());
			}
		}
	}

	protected String getSelectedAdapter() {
		String text = adapterCombo.getText();
		int ns = text.indexOf(' ');
		return ns > 0 ? text = text.substring(0, ns) : text;
	}

	public String getResourceURN() {
		return "local:" + Activator.user().getUsername().toLowerCase() + ":"
				+ folder.getEParent(EProject.class).getName() + ":" + text.getText();
	}

	public String getAdapter() {
		return adapter == null ? null : adapter.getName();
	}

	public Map<String, String> getParameters() {
		return values;
	}

	/**
	 * Return a message describing the first error encountered, or null if no error
	 * is found.
	 * 
	 * @return
	 */
	public String validate() {

		if (text.getText().trim().isEmpty()) {
			return "A resource ID must be supplied";
		}
		if (Activator.klab().getResource(getResourceURN()) != null) {
			return "A resource named " + text.getText() + " already exists in this project.";
		}

		for (Argument argument : adapter.getParameters().getArguments()) {
			String val = values.get(argument.getName());
			if ((argument.isFinal() || argument.isRequired()) && (val == null || val.trim().isEmpty())) {
				return "A value for " + argument.getName() + " must be supplied";
			}
			if (val != null && !val.trim().isEmpty()) {
				if (!Utils.validateAs(Utils.asPOD(val), argument.getType())) {
					return "'" + val + "' is not a valid value for " + argument.getName() + ": must be "
							+ argument.getType().name().toLowerCase();
				}
				// FIXME weak check but for now a URL kdl type is overkill
				if (argument.getName().endsWith("Url")) {
					if (!UrlValidator.getInstance().isValid(val)) {
						return "'" + val + "' is not a valid URL";
					}
				}
			}
		}

		return null;
	}

}
