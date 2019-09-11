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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.EResourceFolder;
import org.integratedmodelling.klab.rest.ResourceAdapterReference;
import org.integratedmodelling.klab.utils.TemplateUtil;

public class BulkImportResource extends WizardPage {

	private Text text;
	private Combo combo;

	private static String NO_CHOICE = "All applicable (may result in errors)";
	private Table table;
	private Label templateLabel;
	private TableViewer tableViewer;
	private Map<String, String> templateVars = new HashMap<>();

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
//			if (element instanceof ServicePrototype.Argument) {
//				ret = values.get(((ServicePrototype.Argument) element).getName());
//			}
			return ret == null ? "" : ret.toString();
		}

		@Override
		protected void setValue(Object element, Object value) {
//			if (element instanceof ServicePrototype.Argument) {
//				setErrorMessage(null);
//				if (value != null && !value.toString().isEmpty()) {
//					if (!Utils.validateAs(value, ((ServicePrototype.Argument) element).getType())) {
//						setErrorMessage("'" + value + "' is not a suitable value for type "
//								+ ((ServicePrototype.Argument) element).getType().name().toLowerCase());
//					}
//					if (((ServicePrototype.Argument) element).getName().endsWith("Url")) {
//						if (!UrlValidator.getInstance().isValid(value.toString())) {
//							setErrorMessage("'" + value + "' is not a valid URL");
//						}
//					}
//				}
//				values.put(((ServicePrototype.Argument) element).getName(), value.toString());
//			}
			getViewer().update(element, null);
		}

	}

	class PropertyContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection) {
				return ((Collection<?>) inputElement).toArray();
			}
			return new Object[] {};
		}

	}

	class PropertyLabelProvider extends LabelProvider implements ITableLabelProvider, IColorProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof String) {
				if (columnIndex == 0) {
					return (String)element;
				} else if (columnIndex == 1) {
					return templateVars.get((String)element);
				}
			}
//			if (element instanceof ServicePrototype.Argument) {
//				ServicePrototype.Argument arg = (ServicePrototype.Argument) element;
//				switch (columnIndex) {
//				case 0:
//					return arg.getName();
//				case 1:
//					return StringUtil.capitalize(arg.getType().name().toLowerCase());
//				case 2:
//					Object ret = values.get(((ServicePrototype.Argument) element).getName());
//					return ret == null ? null : ret.toString();
//				}
//			}

			return null;
		}

		@Override
		public Color getForeground(Object element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Color getBackground(Object element) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public BulkImportResource(EResourceFolder folder) {
		super("wizardPage");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator.PLUGIN_ID, "icons/logo_white_64.jpg"));
		setTitle("Bulk import");
		setDescription("Specify a local folder or an external URL to import resources from");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(3, false));

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("Adapter type");

		combo = new Combo(container, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblNewLabel_1.setText("URL or folder");

		combo.add(NO_CHOICE);
		for (ResourceAdapterReference adapter : Activator.klab().getResourceAdapters()) {
			combo.add(adapter.getName());
		}
		combo.select(0);

		text = new Text(container, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				List<String> vars = TemplateUtil.getTemplateVariables(text.getText().trim());
				if (vars.size() > 0) {
					templateLabel.setEnabled(true);
					table.setEnabled(true);
					tableViewer.setInput(vars);
				} else {
					templateLabel.setEnabled(false);
					table.setEnabled(false);
				}
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnChooseFolder = new Button(container, SWT.NONE);
		btnChooseFolder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				String filePath = dialog.open();
				if (filePath != null) {
					text.setText(filePath);
				}
			}
		});
		btnChooseFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnChooseFolder.setText("Choose folder");

		templateLabel = new Label(container, SWT.NONE);
		templateLabel.setEnabled(false);
		templateLabel.setText("Template variables");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
//		tableViewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		table = tableViewer.getTable();
		table.setEnabled(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				// show the docs for the parameter that was clicked
				int narg = table.getSelectionIndex();
//				if (narg >= 0 && narg < adapter.getParameters().getArguments().size()) {
//					Argument arg = (Argument) table.getItem(narg).getData();
//					String description = adapter.getDescription() + "\n\n";
//					int start = description.length();
//					description += arg.getName();
//					description += "\n\n" + arg.getDescription();
//					descriptionText.setText(description);
//					descriptionText.setStyleRange(new StyleRange(start, arg.getName().length(), null, null, SWT.BOLD));
//				}
			}
		});
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		table.setLinesVisible(true);

		TableViewerColumn propertyNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn propertyColumn = propertyNameColumn.getColumn();
		propertyColumn.setWidth(180);
		propertyColumn.setText("Adapter property");

		TableViewerColumn propertyValueColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn valueColumn = propertyValueColumn.getColumn();
		valueColumn.setWidth(400);
		valueColumn.setText("Value");
		propertyValueColumn.setEditingSupport(new ValueSupport(tableViewer));

		tableViewer.setLabelProvider(new PropertyLabelProvider());
		tableViewer.setContentProvider(new PropertyContentProvider());

	}

	public String getAdapter() {
		return combo.getText().equals(NO_CHOICE) ? null : combo.getText();
	}

	public String getChoice() {
		return text.getText();
	}
}
