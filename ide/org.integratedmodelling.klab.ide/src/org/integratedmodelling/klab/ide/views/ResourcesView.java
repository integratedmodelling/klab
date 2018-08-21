package org.integratedmodelling.klab.ide.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.rest.ResourceReference;

public class ResourcesView extends ViewPart {

	public static final String ID = "org.integratedmodelling.klab.ide.views.ResourcesView";
	private Table table;
	private TableViewer tableViewer;
	private Text searchField;
	private Label resultsLabel;
	private List<EResourceReference> currentMatches = new ArrayList<>();

	class LabelProvider implements ITableLabelProvider {

		@Override
		public void addListener(ILabelProviderListener listener) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return columnIndex == 0 && element instanceof EResourceReference
					? ResourceManager.getPluginImage(Activator.PLUGIN_ID,
							(((ResourceReference) element).getGeometry().startsWith("#") ? "icons/resources.gif"
									: "icons/resource.gif"))
					: null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof ResourceReference) {
				switch (columnIndex) {
				case 0:
					return ((EResourceReference) element).getLocalName();
				case 1:
					return ((EResourceReference) element).getProjectName();
				case 2:
					return ((EResourceReference) element).getAdapterType();
				case 3:
					return describeGeometry(((EResourceReference) element).getGeometry());
				}
			}
			return null;
		}

		private String describeGeometry(String geometry) {
			// TODO
			return geometry;
		}

	}

	class ContentProvider implements ITreeContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);

		}

		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof List) {
				return ((List<?>) parentElement).toArray();
			}
			return null;
		}

		@Override
		public Object getParent(Object element) {
			if (element instanceof ResourceReference) {
				return currentMatches;
			}
			return null;
		}

		@Override
		public boolean hasChildren(Object element) {
			return element instanceof List && ((List<?>) element).size() > 0;
		}

	}

	public ResourcesView() {
	}

	/**
	 * Create contents of the view part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {

		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(1, false));
		{
			searchField = new Text(container, SWT.BORDER);
			searchField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					String t = searchField.getText();
					search(t);
				}
			});
			searchField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		}
		{
			tableViewer = new TableViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
			table = tableViewer.getTable();
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			{
				TableColumn tblclmnUrn = new TableColumn(table, SWT.NONE);
				tblclmnUrn.setWidth(550);
				tblclmnUrn.setText("Local name");
			}
			{
				TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
				tblclmnNewColumn.setWidth(160);
				tblclmnNewColumn.setText("Project");
			}
			{
				TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
				tblclmnNewColumn.setWidth(70);
				tblclmnNewColumn.setText("Type");
			}
			{
				TableColumn tblclmnGeometry = new TableColumn(table, SWT.NONE);
				tblclmnGeometry.setWidth(100);
				tblclmnGeometry.setText("Geometry");
			}

			Menu menu = new Menu(table);
			table.setMenu(menu);

			MenuItem mntmCopyUrn = new MenuItem(menu, SWT.NONE);
			mntmCopyUrn.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					Object object = table.getSelection().length > 0 ? table.getSelection()[0].getData() : null;
					if (object instanceof EResourceReference) {
						Eclipse.INSTANCE.copyToClipboard(((EResourceReference) object).getUrn());
					}
				}
			});
			mntmCopyUrn.setText("Copy URN");

			MenuItem mntmOpenInEditor = new MenuItem(menu, SWT.NONE);
			mntmOpenInEditor.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					Object object = table.getSelection().length > 0 ? table.getSelection()[0].getData() : null;
					if (object instanceof EResourceReference) {
						try {
							IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
									.showView(ResourceEditor.ID);
							if (view != null) {
								((ResourceEditor) view).loadResource((EResourceReference) object);
							}
						} catch (PartInitException ex) {
							Eclipse.INSTANCE.handleException(ex);
						}
						// Activator.session().previewResource((EResourceReference)object);
					}
				}
			});
			mntmOpenInEditor.setText("Open in editor");

			tableViewer.setContentProvider(new ContentProvider());
			tableViewer.setLabelProvider(new LabelProvider());
			tableViewer.addDragSupport(DND.DROP_DEFAULT,
					new Transfer[] { TextTransfer.getInstance(), LocalSelectionTransfer.getTransfer() },
					new DragSourceListener() {

						@Override
						public void dragStart(DragSourceEvent event) {
							// TODO Auto-generated method stub
						}

						@Override
						public void dragSetData(DragSourceEvent event) {
							if (event.getSource() instanceof EResourceReference) {
								event.data = event.getSource();
							}
						}

						@Override
						public void dragFinished(DragSourceEvent event) {
							// TODO Auto-generated method stub
						}
					});
		}
		// tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
		// @Override
		// public void selectionChanged(SelectionChangedEvent event) {
		// Object object = event.getSelection() instanceof StructuredSelection
		// ? event.getStructuredSelection().getFirstElement()
		// : null;
		// if (object instanceof EResourceReference) {
		// try {
		// IViewPart view =
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
		// .showView(ResourceEditor.ID);
		// if (view != null) {
		// ((ResourceEditor) view).loadResource((EResourceReference) object);
		// }
		// } catch (PartInitException e) {
		// Eclipse.INSTANCE.handleException(e);
		// }
		// }
		// }
		// });
		tableViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				Object object = event.getSelection() instanceof StructuredSelection
						? ((StructuredSelection) event.getSelection()).getFirstElement()
						: null;
				if (object instanceof EResourceReference) {
					Activator.session().previewResource((EResourceReference) object);
				}
			}
		});
		{
			resultsLabel = new Label(container, SWT.NONE);
			resultsLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			resultsLabel.setText("No results");
		}
		// toolkit.paintBordersFor(container);

		createActions();
		initializeToolBar();
		initializeMenu();

	}

	protected void search(String text) {
		currentMatches.clear();
		if (text.length() > 1) {
			for (EResourceReference resource : Activator.klab().getProjectResources()) {
				if (resource.getLocalName().startsWith(text)) {
					currentMatches.add(0, resource);
				} else if (resource.getLocalName().contains(text)) {
					currentMatches.add(resource);
				}
			}
		}
		Display.getDefault().asyncExec(() -> tableViewer.setInput(currentMatches));

	}

	public void dispose() {
		// toolkit.dispose();
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
